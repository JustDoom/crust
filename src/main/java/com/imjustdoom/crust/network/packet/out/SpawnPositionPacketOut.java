package com.imjustdoom.crust.network.packet.out;

import com.imjustdoom.crust.network.packet.PacketOut;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SpawnPositionPacketOut extends PacketOut {

    private byte x;
    private byte y;
    private byte z;
    private float angle;

    public SpawnPositionPacketOut(byte x, byte y, byte z, float angle) {
        super("SpawnPositionPacketOut", 0x48);

        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        buffer.writeLong(((long) (x & 0x3FFFFFFF) << 38) | ((long) (y & 0x3FFFFFFF) << 12) | (z & 0x3FFF));
        buffer.writeFloat(angle);

        return bufferArray.toByteArray();
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public byte getZ() {
        return z;
    }

    public float getAngle() {
        return angle;
    }
}
