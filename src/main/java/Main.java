import net.PacketHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Main {

    public static ServerSocket serverSocket;

    public static void main(String[] args) {

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
                PacketHandler ph = new PacketHandler(socket);
                ph.start();
            } catch (IOException e) {
                if (serverSocket.isClosed()) return;
                System.out.println("Failed to accept connection:");
            }
        }
    }
}