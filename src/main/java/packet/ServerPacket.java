package packet;

import io.netty.buffer.ByteBuf;

public interface ServerPacket {
    void write(ByteBuf buffer);

    int getId();
}
