package cc.peihan.flora.core.http;

import cc.peihan.flora.core.protocol.ProtocolProcesser;
import cc.peihan.flora.core.protocol.ProtocolProcesserFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloraHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(FloraHttpHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        ProtocolProcesser processer = ProtocolProcesserFactory.newProcesser();
        if (processer.actRequset(request)) {
            processer.actInvoke();
        }
        doResponse(processer, request, ctx);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.info("channelReadComplete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("exceptionCaught");
        if (cause != null) {
            cause.printStackTrace();
        }
        if (ctx != null) {
            ctx.close();
        }
    }

    private void doResponse(ProtocolProcesser processer, FullHttpRequest request, ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        processer.actResponse(response);
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(response);
        }
    }


}
