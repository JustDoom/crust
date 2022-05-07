package com.imjustdoom.crust.world;

import com.imjustdoom.crust.network.packet.out.ChunkDataPacketOut;
import net.querz.mca.Section;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntArrayTag;
import net.querz.nbt.tag.LongArrayTag;

import java.io.BufferedWriter;
import java.util.Map;

public class Chunk {

//    public static ChunkDataPacketOut createChunkPacket() {
//        final CompoundTag heightmapsNBT;
//        // TODO: don't hardcode heightmaps
//        // Heightmap
//
//        int dimensionHeight = 256;
//        int[] motionBlocking = new int[16 * 16];
//        int[] worldSurface = new int[16 * 16];
//        for (int x = 0; x < 16; x++) {
//            for (int z = 0; z < 16; z++) {
//                motionBlocking[x + z * 16] = 0;
//                worldSurface[x + z * 16] = dimensionHeight - 1;
//            }
//        }
//        //final int bitsForHeight = MathUtils.bitsToRepresent(dimensionHeight);
//        heightmapsNBT = new CompoundTag();
//        heightmapsNBT.put("MOTION_BLOCKING", new IntArrayTag(motionBlocking));
//        heightmapsNBT.put("WORLD_SURFACE", new IntArrayTag(worldSurface));
//
//        // Data
//        byte[] data = new byte[16 * 16 * 256];
//
////        for (int y = 0; y < 256; y++) {
////            for (int z = 0; z < 16; z++) {
////                for (int x = 0; x < 16; x++) {
////                    int blockNumber = (((y * 256) + z) * 16) + x;
////                    int startLong = (blockNumber * 16) / 64;
////                    int startOffset = (blockNumber * 16) % 64;
////                    int endLong = ((blockNumber + 1) * 16 - 1) / 64;
////
////                   // BlockState state = section.GetState(x, y, z);
////
////                    byte value = 1;
////                    //value &= individualValueMask;
////
////                    data[startLong] |= (value << startOffset);
////
////                    if (startLong != endLong) {
////                        data[endLong] = (byte) (value >> (64 - startOffset));
////                    }
////                }
////            }
////        }
//
//        return new ChunkDataPacketOut(0, 0,
//                heightmapsNBT,
//                data);
//    }
}
