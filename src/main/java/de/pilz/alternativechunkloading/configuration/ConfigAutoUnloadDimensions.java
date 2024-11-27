package de.pilz.alternativechunkloading.configuration;

import com.gtnewhorizon.gtnhlib.config.Config;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Config(modid = AlternativeChunkloading.MODID, category = "auto_unload_dims")
public class ConfigAutoUnloadDimensions {

    @Config.Comment("Automatically unload dimensions if there is no chunk loaded.")
    @Config.DefaultBoolean(true)
    public static boolean enabled;

    @Config.Comment("Ticks before a chunk should be loaded or unloaded after a chunkload ticket as been created or removed. Only has affect if \"enabled\" is active.")
    @Config.DefaultInt(20)
    public static int ticksBeforeLoadChunk;

    @Config.Comment("Dimensions that should be ignored.")
    @Config.DefaultIntList({ 1, -1 })
    public static int[] blacklist;

    @Config.Comment("Tread \"blacklist\" as whitelist.")
    @Config.DefaultBoolean(false)
    public static boolean blacklistAsWhitelist;

    public static boolean isDimensionBlacklisted(int dim) {
        for (int test : blacklist) {
            if (test == dim) {
                return !blacklistAsWhitelist;
            }
        }
        return blacklistAsWhitelist;
    }
}
