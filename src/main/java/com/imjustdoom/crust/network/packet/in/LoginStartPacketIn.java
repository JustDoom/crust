package com.imjustdoom.crust.network.packet.in;

import com.imjustdoom.crust.network.packet.PacketIn;
import com.imjustdoom.crust.util.DataUtil;

import java.io.DataInputStream;
import java.io.IOException;

public class LoginStartPacketIn extends PacketIn {

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
        return username;
    }
}
