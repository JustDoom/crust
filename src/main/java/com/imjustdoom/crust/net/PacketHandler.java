package com.imjustdoom.crust.net;

import com.imjustdoom.crust.Main;
import com.imjustdoom.crust.packet.in.*;
import com.imjustdoom.crust.packet.out.*;
import com.imjustdoom.crust.packet.server.handshake.StatusResponseOutPacket;
import com.imjustdoom.crust.util.DataUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class PacketHandler extends Thread {

    public ConnectionStatus status = ConnectionStatus.HANDSHAKING;
    public final Socket connection;
    private DataOutputStream out;
    private DataInputStream in;

    private String username;
    private UUID uuid;
    private HandshakePacketIn handshakeUsed;

    public PacketHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        Main.connections.add(this);

        try {
            handle();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        status = ConnectionStatus.CLOSED;

        Main.connections.remove(this);
    }

    private void handle() throws IOException, InterruptedException {
        in = new DataInputStream(connection.getInputStream());
        out = new DataOutputStream(connection.getOutputStream());

        while(true) {
            int packetSize = DataUtil.readVarInt(in);
            int packetID = DataUtil.readVarInt(in);

            System.out.println("Starting handling of 0x" + String.format("%02X", packetID) + " with size " + packetSize + ".");

            if (packetID == 0x00) {
                switch (status) {
                    case HANDSHAKING -> {
                        handshakeUsed = new HandshakePacketIn(in);
                        status = handshakeUsed.getNextStatus();
                    }
                    case STATUS -> {
                        StatusResponseOutPacket statusResponsePacketOut = new StatusResponseOutPacket("Custom Software", 758, 2210, 14362);
                        statusResponsePacketOut.send(out);

                        // Instantly follow up with pong packet.
                        PongPacketOut pongPacketOut = new PongPacketOut(System.currentTimeMillis());
                        pongPacketOut.send(out);

                        // Close the connection afterwards.
                        connection.close();
                        return;
                    }
                    case LOGIN -> {
                        LoginStartPacketIn loginStartPacketIn = new LoginStartPacketIn(in);
                        username = loginStartPacketIn.getUsername();
                        uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes());
                        //setName(threadId + "-" + username);
                        System.out.println("Player is " + username + " (" + uuid + ")!");

                        LoginSuccessPacketOut loginSuccessPacketOut = new LoginSuccessPacketOut(uuid, username);
                        loginSuccessPacketOut.send(out);

                        // Sleep for a bit.
                        Thread.sleep(500);
                        JoinGamePacketOut joinGamePacketOut = new JoinGamePacketOut(1, false, (byte) 0, (byte) 0, 0L, 1, 8, false, false, false, true);
                        joinGamePacketOut.send(out);

                        // Send server brand packet.
                        ByteArrayOutputStream brandBufferArray = new ByteArrayOutputStream();
                        DataOutputStream brandBuffer = new DataOutputStream(brandBufferArray);
                        DataUtil.writeString(brandBuffer, "Custom server");
                        PluginMessagePacketOut brandPluginMessagePacketOut = new PluginMessagePacketOut("minecraft:brand", brandBufferArray.toByteArray());
                        brandPluginMessagePacketOut.send(out);

                        // Send spawn position packet. (Not location)
                        SpawnPositionPacketOut spawnPositionPacketOut = new SpawnPositionPacketOut((byte) 0, (byte) 0, (byte) 0, 90f);
                        spawnPositionPacketOut.send(out);

                        // Send abilities.
                        PlayerAbilitiesPacketOut playerAbilitiesPacketOut = new PlayerAbilitiesPacketOut((byte) 0x07, 0f, 0.1f);
                        playerAbilitiesPacketOut.send(out);

                        // Finally, set location.
                        PlayerPositionAndLookPacketOut playerPositionAndLookPacketOut = new PlayerPositionAndLookPacketOut(0d, 0d, 0d, 90f, 0f, (byte) 0x00, 1, false);
                        playerPositionAndLookPacketOut.send(out);

                        // Set correct status.
                        status = ConnectionStatus.PLAYING;
                    }
                    default -> {
                    }
                }
            } else if (packetID == 0x01) {
                PingPacketIn pingPacketIn = new PingPacketIn(in);

                // Instantly follow up with pong packet.
                PongPacketOut pongPacketOut = new PongPacketOut(pingPacketIn.getPayload());
                pongPacketOut.send(out);
            } else if (packetID == 0x0F) {
                // We don't need to respond with anything.
                new KeepAlivePacketIn(in);
            } else if(packetID == 0x11) {
                // We don't need to respond with anything.
                new PlayerPositionPacketIn(in);
            } else if(packetID == 0x13) {
                // We don't need to respond with anything.
                new PlayerRotationPacketIn(in);
            } else {
                System.out.println("Packet 0x" + String.format("%02X", packetID) + " is unimplemented, ignoring it.");
                // Skip packet data.
                in.skipBytes(packetSize - DataUtil.getVarIntLength(packetID));
            }
        }
    }

    public DataOutputStream getOut() {
        return out;
    }

    public DataInputStream getIn() {
        return in;
    }
}
