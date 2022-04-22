package com.imjustdoom.crust;

import com.imjustdoom.crust.net.PacketHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static ServerSocket serverSocket;
    public static List<PacketHandler> connections;
    public static SharedObjectCacher sharedObjectCacher;

    public static void main(String[] args) {

        File resourcesFolder = new File("./resources/");
        // Make resources folder if it doesn't exist yet.
        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdir();
        }

        String[] resourcesToCopy = new String[]{"dimensions.nbt", "server.properties"};
        for (String resource : resourcesToCopy) {
            File resourceFile = new File(resourcesFolder.getPath() + File.separator + resource);
            if (resource.equals("server.properties")) {
                resourceFile = new File(resourcesFolder.getParent() + File.separator + resource);
            }

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

        try {
            sharedObjectCacher = new SharedObjectCacher();
        } catch (IOException e) {
            System.out.println("Failed to cache shared objects:");
            e.printStackTrace();
            return;
        }

        connections = new ArrayList<>();

        KeepAliver keepAliver = new KeepAliver();
        keepAliver.start();

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

    public static List<PacketHandler> getConnections() {
        return connections;
    }
}