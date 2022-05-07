package com.imjustdoom.crust;

import com.imjustdoom.crust.network.PacketHandler;
import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CrustServer {

    private final String version = "Crust v0.1 1.18.2";
    private final String description = "Crust is a Minecraft server software written in Java.";
    private final int protocolVersion = 758;
    private final int port = 25565;
    private final String host = "0.0.0.0";
    private final int maxPlayers = 5;

    private ServerSocket serverSocket;
    private final List<PacketHandler> connections = new ArrayList<>();

    public void start() {
        System.out.println("Starting server...");

        InetAddress address;
        try {
            address = InetAddress.getByName(this.host);
            this.serverSocket = new ServerSocket(this.port, 0, address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server started!");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                PacketHandler packetHandler = new PacketHandler(socket);
                packetHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
