package com.imjustdoom.crust.network.packet.in;

import com.imjustdoom.crust.network.packet.PacketIn;

import java.io.DataInputStream;
import java.io.IOException;

public class PingInPacket extends PacketIn {
    private final long payload;

    public PingInPacket(long payload) {
        super("PingInPacket", 0x01);

        this.payload = payload;
    }

    public PingInPacket(DataInputStream in) throws IOException {
        super("PingInPacket", 0x01);

        this.payload = in.readLong();
    }

    public long getPayload() {
        return this.payload;
    }

}
