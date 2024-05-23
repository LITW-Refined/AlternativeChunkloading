package de.pilz.alternativechunkloading.configuration;

import com.gtnewhorizon.gtnhlib.config.Config;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Config(modid = AlternativeChunkloading.MODID)
public class ConfigGeneral {

    @Config.Comment("Disables the option \"loadChunkOnProvideRequest\" on server to not load chunks whenever they are maybe requested.")
    @Config.DefaultBoolean(true)
    public static boolean disableChunkLoadingOnRequest;

    @Config.Comment("When \"disableChunkLoadingOnRequest\" is true then this option helps that chunk load tickets continue to load the target chunk automatically. Otherwise such tickets are useless and chunkloaders too. You may want to disable this if your Chunkloader mod can that too (probably not).")
    @Config.DefaultBoolean(true)
    public static boolean autoLoadChunksOnTicketCreation;

    @Config.Comment("Ticks before a chunk should be unloaded after a chunkload ticket as been removed. Only has affect if \"autoLoadChunksOnTicketCreation\" is active.")
    @Config.DefaultInt(20)
    public static int ticksBeforeLoadChunk;

    @Config.Comment("Automatically unload dimensions if there is no chunk loaded.")
    @Config.DefaultBoolean(true)
    public static boolean autoUnloadDimensions;

    @Config.Comment("Ticks before a dimension should be unloaded when it hasn't any chunk loaded. Only has affect if \"autoLoadChunksOnTicketCreation\" is active.")
    @Config.DefaultInt(300)
    public static int ticksBeforeUnloadDimension;

    @Config.Comment("Ticks before a dimension should be unloaded when it hasn't any chunk loaded. Only has affect if \"autoLoadChunksOnTicketCreation\" is active.")
    @Config.DefaultStringList({ "1", "-1" })
    public static String[] autoUnloadDimensionsBlacklist;

    @Config.Comment("Tread \"autoUnloadDimensionsBlacklist\" as whitelist.")
    @Config.DefaultBoolean(false)
    public static boolean autoUnloadDimensionsBlacklistAsWhitelist;

    public static boolean isOnAutoUnloadDimensionBlacklist(int dim) {
        String dimStr = "" + dim;

        for (String test : autoUnloadDimensionsBlacklist) {
            if (test.equalsIgnoreCase(dimStr)) {
                return !autoUnloadDimensionsBlacklistAsWhitelist;
            }
        }

        return autoUnloadDimensionsBlacklistAsWhitelist;
    }
}
