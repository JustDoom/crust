package net;

import packet.server.handshake.StatusResponseOutPacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PacketHandler extends Thread {

    public final Socket connection;
    private DataOutputStream out;

    public PacketHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handle() throws IOException, InterruptedException {
        out = new DataOutputStream(connection.getOutputStream());

        StatusResponseOutPacket statusResponsePacketOut = new StatusResponseOutPacket("Custom Software", 47, 2210, 1);
        statusResponsePacketOut.send(out);
    }
}
