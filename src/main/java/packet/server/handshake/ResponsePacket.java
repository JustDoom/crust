package packet.server.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import packet.ServerPacket;

import java.io.UnsupportedEncodingException;

public class ResponsePacket implements ServerPacket {

    private static final String JSON_EXAMPLE = "{\n" +
            "    \"version\": {\n" +
            "        \"name\": \"1.14.4\",\n" +
            "        \"protocol\": 498\n" +
            "    },\n" +
            "    \"players\": {\n" +
            "        \"max\": 100,\n" +
            "        \"online\": 1,\n" +
            "        \"sample\": [\n" +
            "            {\n" +
            "                \"name\": \"TheMode\",\n" +
            "                \"id\": \"4566e69f-c907-48ee-8d71-d7ba5aa00d20\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\t\n" +
            "    \"description\": {\n" +
            "        \"text\": \"Wallah les cubes\"\n" +
            "    },\n" +
            "    \"favicon\": \"data:image/png;base64,<data>\"\n" +
            "}";

    @Override
    public void write(ByteBuf buffer) {
        System.out.println("ee");
        writeString(buffer, JSON_EXAMPLE);
    }

    @Override
    public int getId() {
        return 0x00;
    }

    public static void writeString(ByteBuf buffer, String value) {
        byte[] bytes = new byte[0];
        try {
            bytes = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (bytes.length > 32767) {
            System.out.println("String too big (was " + value.length() + " bytes encoded, max " + 32767 + ")");
        } else {
            writeVarInt(buffer, bytes.length);
            buffer.writeBytes(bytes);
        }
    }

    public static void writeVarInt(ByteBuf buffer, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buffer.writeByte(temp);
        } while (value != 0);
    }
}
