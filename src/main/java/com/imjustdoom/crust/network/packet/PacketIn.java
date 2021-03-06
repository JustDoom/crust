package com.imjustdoom.crust.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketIn extends Packet {

    public PacketIn(String name, int id) {
        super(name, id);
    }

    public byte[] serialize() throws IOException {
        return null;
    }

    public void send(DataOutputStream out) throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        writeVarInt(buffer, getId());
        buffer.write(serialize());

        writeVarInt(out, buffer.size());

        out.write(bufferArray.toByteArray());

        System.out.println("Sending " + getName() + " with id 0x" + Integer.toHexString(getId()) + " and size " + buffer.size());
    }

    public static void writeVarInt(DataOutputStream out, int intValue) throws IOException {
        int value = intValue;
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            out.writeByte(temp);
        } while (value != 0);
    }
}
