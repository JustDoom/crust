package com.imjustdoom.crust;

import com.imjustdoom.crust.network.PacketHandler;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
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
    private SharedObjectCacher sharedObjectCacher;

    public void start() {
        System.out.println("Starting server...");

        File resourcesFolder = new File("./resources/");
        // Make resources folder if it doesn't exist yet.
        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdir();
        }

        String[] resourcesToCopy = new String[]{"dimensions.nbt"};
        for (String resource : resourcesToCopy) {
            File resourceFile = new File(resourcesFolder.getPath() + File.separator + resource);

            // Check if the file exists.
            if (resourceFile.exists()) continue;

            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(resource);

            System.out.println("Copying resource " + resource + "...");

            try {
                Files.copy(inputStream, resourceFile.toPath());
            } catch (IOException e) {
                System.out.println("Failed to copy resource " + resource + ":");
                e.printStackTrace();
                return;
            }
            System.out.println("Copied resource " + resource + "!");
        }

        InetAddress address;
        try {
            address = InetAddress.getByName(this.host);
            this.serverSocket = new ServerSocket(this.port, 0, address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sharedObjectCacher = new SharedObjectCacher();
        } catch (IOException e) {
            System.out.println("Failed to cache shared objects:");
            e.printStackTrace();
            return;
        }

        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();

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
