package de.pilz.alternativechunkloading.configuration;

import net.minecraft.world.WorldProvider;

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

    @Config.Comment("Ticks before a chunk should be loaded or unloaded after a chunkload ticket as been created or removed. Only has affect if \"autoLoadChunksOnTicketCreation\" is active.")
    @Config.DefaultInt(20)
    public static int ticksBeforeLoadChunk;

    @Config.Comment("Dimension that should be ignored. Write down the id of the dimension.")
    @Config.DefaultIntList({})
    public static int[] dimensionBlacklist;

    @Config.Comment("Tread \"dimensionBlacklist\" as whitelist.")
    @Config.DefaultBoolean(false)
    public static boolean dimensionBlacklistAsWhitelist;

    @Config.Comment("Provider that should be ignored. Write down the full class name (includes the package name).")
    @Config.DefaultStringList({})
    public static String[] providerBlacklist;

    @Config.Comment("Tread \"providerBlacklist\" as whitelist.")
    @Config.DefaultBoolean(false)
    public static boolean providerBlacklistAsWhitelist;

    public static boolean isDimensionBlacklisted(int dim) {
        for (int test : dimensionBlacklist) {
            if (test == dim) {
                return !dimensionBlacklistAsWhitelist;
            }
        }
        return dimensionBlacklistAsWhitelist;
    }

    public static boolean isProviderBlacklisted(WorldProvider provider) {
        String name = provider.getClass()
            .getName();
        for (String test : providerBlacklist) {
            if (test.equals(name)) {
                return !providerBlacklistAsWhitelist;
            }
        }
        return providerBlacklistAsWhitelist;
    }
}
