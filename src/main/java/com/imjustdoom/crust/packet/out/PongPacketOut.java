package com.imjustdoom.crust.packet.out;

import com.imjustdoom.crust.packet.PlayerPacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PongPacketOut extends PlayerPacket {

    private long payload;

    public PongPacketOut(long payload) {
        super("PongPacketOut", 0x01);

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
        return this.payload;
    }

    public void setPayload(long payload) {
        this.payload = payload;
    }
}
