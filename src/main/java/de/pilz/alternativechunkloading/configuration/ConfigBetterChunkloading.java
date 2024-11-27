package de.pilz.alternativechunkloading.configuration;

import com.gtnewhorizon.gtnhlib.config.Config;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Config(modid = AlternativeChunkloading.MODID, category = "alternative_chunkloading")
public class ConfigBetterChunkloading {

    @Config.Comment("Disables the option \"loadChunkOnProvideRequest\" on server to not load chunks whenever they are maybe requested.")
    @Config.DefaultBoolean(true)
    public static boolean disableChunkLoadingOnRequest;

    @Config.Comment("When \"disableChunkLoadingOnRequest\" is true then this option helps that chunk load tickets continue to load the target chunk automatically. Otherwise such tickets are useless and chunkloaders too. You may want to disable this if your Chunkloader mod can that too (probably not).")
    @Config.DefaultBoolean(true)
    public static boolean autoLoadChunksOnTicketCreation;

    @Config.Comment("Dimension that should be ignored.")
    @Config.DefaultIntList({})
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
