package de.pilz.alternativechunkloading;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Utils {

    public static void ensureBlockExists(World world, int x, int y, int z) {
        if (world instanceof WorldServer worldServer) {
            ensureBlockExists(worldServer, x, y, z);
        }
    }

    public static void ensureBlockExists(WorldServer world, int x, int y, int z) {
        if (world != null && !world.blockExists(x, y, z)) {
            world.getChunkProvider()
                .loadChunk(x >> 4, z >> 4);
        }
    }
}
