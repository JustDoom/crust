package com.imjustdoom.crust.packet.in;

import com.imjustdoom.crust.packet.ServerPacket;

import java.io.DataInputStream;
import java.io.IOException;

public class PingPacketIn extends ServerPacket {
    private final long payload;

    public PingPacketIn(long payload) {
        super("PingPacketIn", 0x00);

        this.payload = payload;
    }

    public PingPacketIn(DataInputStream in) throws IOException {
        super("PingPacketIn", 0x00);

        this.payload = in.readLong();
    }

    public long getPayload() {
        return this.payload;
    }
}
