package de.pilz.alternativechunkloading.configuration;

import net.minecraft.world.WorldProvider;

import com.gtnewhorizon.gtnhlib.config.Config;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Config(modid = AlternativeChunkloading.MODID, category = "auto_unload_dims")
public class ConfigAutoUnloadDimensions {

    @Config.Comment("Automatically unload dimensions if there is no chunk loaded.")
    @Config.DefaultBoolean(true)
    public static boolean enabled;

    @Config.Comment("Ticks before a chunk should be loaded or unloaded after a chunkload ticket as been created or removed. Only has affect if \"enabled\" is active.")
    @Config.DefaultInt(1200)
    public static int ticksBeforeUnloadDimension;

    @Config.Comment("Dimensions that should be ignored.")
    @Config.DefaultIntList({ 1, -1 })
    public static int[] dimensionBlacklist;

    @Config.Comment("Tread \"dimensionBlacklist\" as whitelist.")
    @Config.DefaultBoolean(false)
    public static boolean dimensionBlacklistAsWhitelist;

    @Config.Comment("Provider that should be ignored. Write down the full class name (includes the package name).")
    @Config.DefaultIntList({})
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
