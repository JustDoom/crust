package com.imjustdoom.crust.packet.out;

import com.imjustdoom.crust.packet.PlayerPacket;
import com.imjustdoom.crust.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessagePacketOut extends PlayerPacket {

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
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
