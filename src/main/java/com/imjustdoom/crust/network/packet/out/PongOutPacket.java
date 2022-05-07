package com.imjustdoom.crust.network.packet.out;

import com.imjustdoom.crust.network.packet.PacketOut;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PongOutPacket extends PacketOut {

    private long payload;

    public PongOutPacket(long payload) {
        super("PongOutPacket", 0x01);

        this.payload = payload;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        buffer.writeLong(payload);

        return bufferArray.toByteArray();
    }

    public long getPayload() {
        return payload;
    }
}
