import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.ServerPacket;
import packet.server.handshake.ResponsePacket;

public class ServerHandler  extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        System.out.println("e");
        ResponsePacket responsePacket = new ResponsePacket();
        ByteBuf buffer = ctx.alloc().buffer();
        ((ServerPacket) responsePacket).write(buffer);
        ctx.writeAndFlush(((ServerPacket) responsePacket).getId());
        //ctx.writeAndFlush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
