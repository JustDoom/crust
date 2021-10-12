package packet.server.handshake;

import io.netty.channel.unix.Buffer;
import packet.Packet;

import java.io.UnsupportedEncodingException;

public class ResponsePacket implements Packet {

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
    public void write(Buffer buffer) {
        System.out.println("ee");
        writeString(buffer, JSON_EXAMPLE);
    }

    @Override
    public int getId() {
        return 0x00;
    }

    public void test() {
        System.out.println("grefd");
    }

    public static void writeString(Byte buffer, String value) {
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
            buffer.putBytes(bytes);
        }
    }

    public static void writeVarInt(Buffer buffer, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            //buffer.putByte(temp);
        } while (value != 0);
    }
}
