package com.imjustdoom.crust.network.packet.out;

import com.imjustdoom.crust.network.packet.PacketOut;
import com.imjustdoom.crust.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class LoginSuccessPacketOut extends PacketOut {

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

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
}
