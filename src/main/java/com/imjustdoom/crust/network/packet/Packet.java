package com.imjustdoom.crust.network.packet;

public class Packet {

    private final String name;
    private final int id;

    public Packet(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
