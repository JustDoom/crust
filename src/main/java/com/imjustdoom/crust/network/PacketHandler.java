package com.imjustdoom.crust.network;

import com.imjustdoom.crust.Main;
import com.imjustdoom.crust.network.packet.in.*;
import com.imjustdoom.crust.network.packet.out.*;
import com.imjustdoom.crust.util.DataUtil;
import com.imjustdoom.crust.world.Chunk;
import lombok.Getter;

import javax.swing.text.Position;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

@Getter
public class PacketHandler extends Thread {

    private String username;
    private UUID uuid;
    private HandshakePacketIn handshakeUsed;

    private ConnectionStatus status = ConnectionStatus.HANDSHAKING;

    private final Socket connection;
    private DataInputStream in;
    private DataOutputStream out;

    public PacketHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        Main.getServer().getConnections().add(this);

        try {
            handlePackets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        status = ConnectionStatus.CLOSED;

        Main.getServer().getConnections().remove(this);
    }

    public void handlePackets() throws IOException, InterruptedException {
        in = new DataInputStream(connection.getInputStream());
        out = new DataOutputStream(connection.getOutputStream());

        while (true) {
            int packetSize = DataUtil.readVarInt(in);
            int packetId = DataUtil.readVarInt(in);

            if(packetId == 0x00) {
                switch (status) {
                    case HANDSHAKING -> {
                        handshakeUsed = new HandshakePacketIn(in);
                        status = handshakeUsed.getNextStatus();
                    }
                    case STATUS -> {
                        StatusResponseOutPacket packet = new StatusResponseOutPacket(Main.getServer().getVersion(),
                                Main.getServer().getProtocolVersion(),
                                Main.getServer().getConnections().size(),
                                Main.getServer().getMaxPlayers(),
                                Main.getServer().getDescription());
                        packet.send(out);

                        PongOutPacket pong = new PongOutPacket(System.currentTimeMillis());
                        pong.send(out);

                        connection.close();
                        return;
                    }
                    case LOGIN -> {
                        System.out.println("Login packet received");
                        LoginStartPacketIn loginStartPacketIn = new LoginStartPacketIn(in);
                        username = loginStartPacketIn.getUsername();
                        uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes());

                        System.out.println("[INFO] " + username + " has connected to the server.");

                        LoginSuccessPacketOut loginSuccessPacketOut = new LoginSuccessPacketOut(uuid, username);
                        loginSuccessPacketOut.send(out);

                        JoinGamePacketOut joinGamePacketOut = new JoinGamePacketOut(1, false, (byte) 0, (byte) 0, 0L, 1, 8, false, false, false, true);
                        joinGamePacketOut.send(out);

                        ByteArrayOutputStream brandBufferArray = new ByteArrayOutputStream();
                        DataOutputStream brandBuffer = new DataOutputStream(brandBufferArray);
                        DataUtil.writeString(brandBuffer, "Crust");
                        PluginMessagePacketOut brandPacket = new PluginMessagePacketOut("minecraft:brand", brandBufferArray.toByteArray());
                        brandPacket.send(out);

                        SpawnPositionPacketOut spawnPositionPacketOut = new SpawnPositionPacketOut((byte) 0, (byte) 10, (byte) 0, 90f);
                        spawnPositionPacketOut.send(out);

                        PlayerAbilitiesPacketOut playerAbilitiesPacketOut = new PlayerAbilitiesPacketOut((byte) 0x07, 0f, 0.1f);
                        playerAbilitiesPacketOut.send(out);

                        PlayerPositionAndLookPacketOut playerPositionAndLookPacketOut = new PlayerPositionAndLookPacketOut(0d, 0d, 0d, 90f, 0f, (byte) 0x00, 1, false);
                        playerPositionAndLookPacketOut.send(out);

                        status = ConnectionStatus.PLAYING;
                        System.out.println("[INFO] " + username + " has joined the game.");

                        ChunkDataPacketOut chunkDataPacketOut = new ChunkDataPacketOut(0, 0, new byte[0]);
                        chunkDataPacketOut.send(out);


                    }
                }
            } else if (packetId == 0x01) {
                PingInPacket pingPacketIn = new PingInPacket(in);

                PongOutPacket pongPacketOut = new PongOutPacket(pingPacketIn.getPayload());
                pongPacketOut.send(out);
            } else if (packetId == 0x0F) {
                new KeepAlivePacketIn(in);
            } else if (packetId == 0x11) {
                new PlayerPositionPacketIn(in);
            } else if (packetId == 0x13) {
                new PlayerRotationPacketIn(in);
            } else {
                System.out.println("[WARN] Unknown packet: " + packetId);
                in.skipBytes(packetSize - DataUtil.getVarIntLength(packetId));
            }
        }
    }
}
