package com.imjustdoom.crust.packet.out;

import com.imjustdoom.crust.packet.PlayerPacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerAbilitiesPacketOut extends PlayerPacket {

    private byte flags;
    private float flyingSpeed;
    private float fovMultiplier;

    public PlayerAbilitiesPacketOut(byte flags, float flyingSpeed, float fovMultiplier) {
        super("PlayerAbilitiesPacketOut", 0x32);

        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.fovMultiplier = fovMultiplier;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        buffer.writeByte(flags);
        buffer.writeFloat(flyingSpeed);
        buffer.writeFloat(fovMultiplier);

        return bufferArray.toByteArray();
    }

    public byte getFlags() {
        return this.flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public float getFlyingSpeed() {
        return this.flyingSpeed;
    }

    public void setFlyingSpeed(float flyingSpeed) {
        this.flyingSpeed = flyingSpeed;
    }

    public float getFovMultiplier() {
        return this.fovMultiplier;
    }

    public void setFovMultiplier(float fovMultiplier) {
        this.fovMultiplier = fovMultiplier;
    }
}
