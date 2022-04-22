package com.imjustdoom.crust.packet.in;

import com.imjustdoom.crust.packet.ServerPacket;
import com.imjustdoom.crust.util.DataUtil;

import java.io.DataInputStream;
import java.io.IOException;

public class LoginStartPacketIn extends ServerPacket {

    private final String username;

    public LoginStartPacketIn(String username) {
        super("LoginStartPacketIn", 0x00);

        this.username = username;
    }

    public LoginStartPacketIn(DataInputStream in) throws IOException {
        super("LoginStartPacketIn", 0x00);

        this.username = DataUtil.readString(in);
    }

    public String getUsername() {
        return this.username;
    }
}
