package com.imjustdoom.crust.packet.out;

import com.imjustdoom.crust.packet.PlayerPacket;
import com.imjustdoom.crust.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class LoginSuccessPacketOut extends PlayerPacket {

    private UUID uuid;
    private String username;

    public LoginSuccessPacketOut(UUID uuid, String username) {
        super("LoginSuccessPacketOut", 0x02);

        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
        DataUtil.writeString(buffer, username);

        return bufferArray.toByteArray();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
