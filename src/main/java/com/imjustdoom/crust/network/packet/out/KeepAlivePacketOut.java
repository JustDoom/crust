package com.imjustdoom.crust.network.packet.out;

import com.imjustdoom.crust.network.packet.PacketOut;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class KeepAlivePacketOut extends PacketOut {

    private long payload;

    public KeepAlivePacketOut(long payload) {
        super("KeepAlivePacketOut", 0x21);

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
