package packet;

import io.netty.channel.unix.Buffer;

public interface Packet {
    void write(Buffer buffer);

    int getId();
}
