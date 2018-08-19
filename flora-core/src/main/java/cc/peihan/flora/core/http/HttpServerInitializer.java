package cc.peihan.flora.core.http;

import cc.peihan.flora.core.protocol.ProtocolProcesserFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline channelPipeline = ch.pipeline();
        //集合了编码器和解码器
        channelPipeline.addLast(new HttpServerCodec());
        //消息聚合器
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        //自定义处理器
        //根据应用配置的不同解析器来自动选择适应的解析器
        channelPipeline.addLast(new FloraHttpHandler());
    }
}
