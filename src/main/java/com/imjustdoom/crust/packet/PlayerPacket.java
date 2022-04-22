package com.imjustdoom.crust.packet;

import com.imjustdoom.crust.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerPacket extends Packet {

    public PlayerPacket(String name, int id) {
        super(name, id);
    }

    public byte[] serialize() throws IOException {
        return null;
    }

    public void send(DataOutputStream out) throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);
        // Include id in buffer so it's calculated in size.
        DataUtil.writeVarInt(buffer, getId());
        buffer.write(serialize());

        // Write size first.
        DataUtil.writeVarInt(out, buffer.size());

        // Then the rest.
        out.write(bufferArray.toByteArray());

        System.out.println("Sending " + getName() + " with id 0x" + Integer.toHexString(getId()) + " and size " + buffer.size());
    }
}
