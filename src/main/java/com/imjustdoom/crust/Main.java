package com.imjustdoom.crust;

public class Main {

    private static CrustServer server;

    public static void main(String[] args) {

        server = new CrustServer();
        server.start();
    }

    public static CrustServer getServer() {
        return server;
    }
}