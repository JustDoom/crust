package packet.server.handshake;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestController extends Thread {

    public final Socket connection;
    private DataOutputStream out;

    public TestController(Socket connection) {
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

        StatusResponsePacketOut statusResponsePacketOut = new StatusResponsePacketOut("Custom 1.17.1", 756, 2210, 0);
        statusResponsePacketOut.send(out);
    }
}
