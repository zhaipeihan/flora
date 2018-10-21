package cc.peihan.flora.core.protocol.fcp;

import cc.peihan.flora.core.helper.InjectorHelper;
import cc.peihan.flora.core.protocol.AbstractProtocolProcesser;
import cc.peihan.flora.core.protocol.payload.ErrorPayload;
import cc.peihan.flora.core.protocol.payload.InvokePayload;
import cc.peihan.flora.core.util.JsonUtil;
import cc.peihan.flora.core.util.ReflexUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import io.netty.handler.codec.http.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class FcpProtocolProcesser extends AbstractProtocolProcesser {

    public static final Set<String> INVOKE_PATH = ImmutableSet.of("/invoke", "/invoke/");

    //每次接受一个请求
    private InvokePayload invokePayload;

    @Override
    public boolean actRequset(FullHttpRequest fullHttpRequest) {
        this.setFullHttpRequest(fullHttpRequest);
        this.invokePayload = new InvokePayload();

        //检查请求
        if (!checkRequest()) {
            return false;
        }

        //准备请求方法
        FcpRequestPayload requestPayload = JsonUtil.read(getBody(), FcpRequestPayload.class);

        Method method = FcpMethodMapping.mappingMethod(requestPayload.getService(), requestPayload.getMethod());

        if (method == null) {
            return invalid("service:[%s] has no method [%s]", requestPayload.getService(), requestPayload.getMethod());
        }

        this.invokePayload.setClazz(FcpMethodMapping.mappingImplClass(requestPayload.getService()));
        this.invokePayload.setMethod(method);
        this.invokePayload.setParameters(requestPayload.getParams());
        return true;
    }


    @Override
    public void actInvoke() {

        Class clazz = this.invokePayload.getClazz();
        Method method = this.invokePayload.getMethod();
        Object newInstance = InjectorHelper.INSTANCE.getObject(clazz);

        Object result = null;

        if (MapUtils.isEmpty(this.invokePayload.getParameters())) {
            try {
                result = method.invoke(newInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Object[] objects = ReflexUtil.orderMethodParam(method, this.invokePayload.getParameters());
            try {
                result = method.invoke(newInstance, objects);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        this.invokePayload.setResult(result);
    }

    @Override
    public void actResponse(FullHttpResponse fullHttpResponse) {
        FcpResponsePayload responsePayload = new FcpResponsePayload();

        if (invokePayload.getException() == null) {
            responsePayload.setResult(invokePayload.getResult());
        } else {
            responsePayload.setError(ErrorPayload.make(invokePayload.getException()));
        }

        String responseJson = JsonUtil.writeAsString(responsePayload);

        byte[] responseBytes = responseJson.getBytes(Charsets.UTF_8);
        fullHttpResponse.setStatus(HttpResponseStatus.OK);
        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, responseBytes.length);
        fullHttpResponse.content().writeBytes(responseBytes);
    }


    private boolean checkRequest() {
        //非法请求方法
        HttpMethod method = getMethod();
        if (!HttpMethod.POST.equals(method)) {
            return invalid("Unsupported request method [%s]", method.name());
        }
        String path = getPath();
        //非法请求路径
        if (!INVOKE_PATH.contains(path)) {
            return invalid("Unsupported request path [%s]", path);
        }
        String contentType = getHeaderValue(HttpHeaderNames.CONTENT_TYPE.toString());
        if (StringUtils.isEmpty(contentType) || !HttpHeaderValues.APPLICATION_JSON.toString().equals(contentType.toLowerCase())) {
            return invalid("Unsupported request header content-type [%s]", contentType);
        }

        String body = getBody();
        if (StringUtils.isEmpty(body)) {
            return invalid("request body can not be empty");
        }

        FcpRequestPayload requestPayload = JsonUtil.read(body, FcpRequestPayload.class);

        if (requestPayload == null) {
            return invalid("request body can not be empty");
        }

        if (StringUtils.isEmpty(requestPayload.getMethod())) {
            return invalid("request body method can not be empty");
        }

        if (StringUtils.isEmpty(requestPayload.getService())) {
            return invalid("request body service can not be empty");
        }

        return true;
    }

    private boolean invalid(String template, Object... args) {
        String message = String.format(template, args);
        this.invokePayload.setException(new IllegalStateException(message));
        return false;
    }


}
