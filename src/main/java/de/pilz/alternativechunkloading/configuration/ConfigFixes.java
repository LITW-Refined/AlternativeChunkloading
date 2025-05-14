package de.pilz.alternativechunkloading.configuration;

import com.gtnewhorizon.gtnhlib.config.Config;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Config(modid = AlternativeChunkloading.MODID, category = "fixes")
public class ConfigFixes {

    @Config.Comment("Load chunk before generating nether portal blocks.")
    @Config.DefaultBoolean(true)
    public static boolean fixVanillaNetherPortalGeneration;

    @Config.Comment("Adds a missing null check to Erebus.")
    @Config.DefaultBoolean(true)
    public static boolean fixErebusNullChecks;

    @Config.Comment("Adds compatibility with Dimensional Doors.")
    @Config.DefaultBoolean(true)
    public static boolean fixDimDoorsCompatibility;
}
