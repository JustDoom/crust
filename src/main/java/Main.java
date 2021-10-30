import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import packet.server.handshake.TestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Main {

    private int port;

    public static ServerSocket serverSocket;

    public Main(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        InetAddress listenInterface;
        try {
            listenInterface = InetAddress.getByName(properties.getProperty("server-ip", "0.0.0.0"));
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("server-port", "25565")), 50, listenInterface);
        } catch (IOException e) {
            System.out.println("Failed to create server socket:");
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                // Accept the socket.
                Socket socket = serverSocket.accept();
                TestController ch = new TestController(socket);
                ch.start();
            } catch (IOException e) {
                if (serverSocket.isClosed()) return;
                System.out.println("Failed to accept connection:");
            }
        }

        //int port = 25565;
        //if (args.length > 0) {
            //port = Integer.parseInt(args[0]);
        //}

        //new Main(port).run();
    }
}