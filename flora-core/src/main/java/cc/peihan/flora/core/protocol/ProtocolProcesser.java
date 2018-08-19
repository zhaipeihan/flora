package cc.peihan.flora.core.protocol;


import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface ProtocolProcesser {

    /**
     * 接受请求，对请求进行处理
     *
     * @param fullHttpRequest
     */
    boolean actRequset(FullHttpRequest fullHttpRequest);


    /**
     * 执行mapping到方法
     */
    void actInvoke();


    /**
     * 填充响应
     *
     * @param fullHttpResponse
     */
    void actResponse(FullHttpResponse fullHttpResponse);


}
