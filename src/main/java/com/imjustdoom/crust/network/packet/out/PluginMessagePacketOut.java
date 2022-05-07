package com.imjustdoom.crust.network.packet.out;

import com.imjustdoom.crust.network.packet.PacketOut;
import com.imjustdoom.crust.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessagePacketOut extends PacketOut {

    private String identifier;
    private byte[] payload;

    public PluginMessagePacketOut(String identifier, byte[] payload) {
        super("PluginMessagePacketOut", 0x18);

        this.identifier = identifier;
        this.payload = payload;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        DataUtil.writeString(buffer, identifier);
        buffer.write(payload);

        return bufferArray.toByteArray();
    }

    public String getIdentifier() {
        return identifier;
    }

    public byte[] getPayload() {
        return payload;
    }
}
