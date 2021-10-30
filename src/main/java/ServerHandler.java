import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import packet.ServerPacket;
import packet.server.handshake.ResponsePacket;

import java.nio.charset.StandardCharsets;

public class ServerHandler  extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        System.out.println(msg);

        System.out.println("e");
        ResponsePacket responsePacket = new ResponsePacket();
        ByteBuf buffer = ctx.alloc().buffer();
        ((ServerPacket) responsePacket).write(buffer);
        ctx.channel().writeAndFlush(responsePacket);
        buffer.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
