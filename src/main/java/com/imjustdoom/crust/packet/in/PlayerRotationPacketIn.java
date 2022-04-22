package com.imjustdoom.crust.packet.in;

import com.imjustdoom.crust.packet.ServerPacket;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerRotationPacketIn extends ServerPacket {

    private final float yaw;
    private final float pitch;
    private final boolean onGround;

    public PlayerRotationPacketIn(float yaw, float pitch, boolean onGround) {
        super("PlayerRotationPacketIn", 0x13);

        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public PlayerRotationPacketIn(DataInputStream in) throws IOException {
        super("PlayerRotationPacketIn", 0x13);

        this.yaw = in.readLong();
        this.pitch = in.readLong();
        this.onGround = in.readBoolean();
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }
}
