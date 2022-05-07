package com.imjustdoom.crust.network.packet.out;

import com.imjustdoom.crust.Main;
import com.imjustdoom.crust.network.packet.PacketOut;
import com.imjustdoom.crust.util.DataUtil;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.LongArrayTag;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChunkDataPacketOut extends PacketOut {

    private int chunkX;
    private int chunkZ;
    private CompoundTag heightmaps;
    private byte[] chunkData;

    public ChunkDataPacketOut(int chunkX, int chunkZ, byte[] chunkData) {
        super("ChunkDataPacketOut", 0x22);

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkData = chunkData;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bufferArray = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bufferArray);

        buffer.writeInt(0);
        buffer.writeInt(0);
        buffer.writeBoolean(true);
        DataUtil.writeVarInt(buffer, 1);

        heightmaps = new CompoundTag();
        heightmaps.put("MOTION_BLOCKING", new LongArrayTag(new long[]{
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                537921540L}));

        heightmaps.put("WORLD_SURFACE", new LongArrayTag(new long[]{
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                72198606942111748L,
                537921540L}));

        DataUtil.writeCompoundTag(buffer, heightmaps);

        DataUtil.writeVarInt(buffer, 1024);
        for(int i = 0; i < 1024; i++) {
            DataUtil.writeVarInt(buffer, 127);
        }

        DataUtil.writeVarInt(buffer, 2056);

        buffer.writeShort(1);
        buffer.writeByte(4);
        DataUtil.writeVarInt(buffer, 2);
        DataUtil.writeVarInt(buffer, 1);
        DataUtil.writeVarInt(buffer, 9);
        DataUtil.writeVarInt(buffer, 256);

        for(int i = 0; i < 2048; i++) {
            buffer.writeByte(0);
        }

        DataUtil.writeVarInt(buffer, 0);

        return bufferArray.toByteArray();
    }
}
