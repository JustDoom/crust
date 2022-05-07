package com.imjustdoom.crust.network.packet.out;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.imjustdoom.crust.network.packet.PacketOut;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StatusResponseOutPacket extends PacketOut {

    private String versionName;
    private int protocol;
    private int onlinePlayers;
    private int maxPlayers;
    private String description;

    public StatusResponseOutPacket(String versionName, int protocol, int players, int maxPlayers, String description) {
        super("StatusResponseOutPacket", 0x00);

        this.versionName = versionName;
        this.protocol = protocol;
        this.onlinePlayers = players;
        this.maxPlayers = maxPlayers;
        this.description = description;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        JsonObject motd = new JsonObject();

        JsonObject version = new JsonObject();
        version.addProperty("name", this.versionName);
        version.addProperty("protocol", this.protocol);

        JsonObject players = new JsonObject();
        players.addProperty("max", this.maxPlayers);
        players.addProperty("online", this.onlinePlayers);
        players.add("sample", new JsonArray());

        JsonObject description = new JsonObject();
        description.addProperty("text", this.description);

        motd.add("version", version);
        motd.add("players", players);
        motd.add("description", description);

        String motdString = motd.toString();

        writeString(buffer, motdString);

        return bufferArray.toByteArray();
    }

    public static void writeString(DataOutputStream out, String stringValue) throws IOException {
        writeVarInt(out, stringValue.length());
        out.write(stringValue.getBytes());
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
