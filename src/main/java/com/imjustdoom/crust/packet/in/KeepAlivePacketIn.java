package com.imjustdoom.crust.packet.in;

import com.imjustdoom.crust.packet.ServerPacket;

import java.io.DataInputStream;
import java.io.IOException;

public class KeepAlivePacketIn extends ServerPacket {

    private final long payload;

    public KeepAlivePacketIn(long payload) {
        super("KeepAlivePacketIn", 0x0F);

        this.payload = payload;
    }

    public KeepAlivePacketIn(DataInputStream in) throws IOException {
        super("KeepAlivePacketIn", 0x0F);

        this.payload = in.readLong();
    }

    public long getPayload() {
        return this.payload;
    }

}
