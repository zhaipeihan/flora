package cc.peihan.flora.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractProtocolProcesser implements ProtocolProcesser {

    @Getter
    @Setter
    private FullHttpRequest fullHttpRequest;

    //获取请求的路径
    public String getPath() {
        String path = this.fullHttpRequest.uri();
        if (path.indexOf('?') != -1) {
            path = path.substring(0, path.indexOf('?'));
        }
        return path;
    }

    //获取header里的值
    public String getHeaderValue(String key) {
        return this.fullHttpRequest.headers().get(key);
    }

    //获取请求方法
    public HttpMethod getMethod() {
        return fullHttpRequest.method();
    }

    //获取请求体中的body
    public String getBody() {
        ByteBuf byteBuf = fullHttpRequest.content();
        byte[] contentBytes = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(byteBuf.readerIndex(),contentBytes);
        return new String(contentBytes);
    }

}
