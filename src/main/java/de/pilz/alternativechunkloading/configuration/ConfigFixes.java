package de.pilz.alternativechunkloading.configuration;

import com.gtnewhorizon.gtnhlib.config.Config;

public class ConfigFixes {

    @Config.Comment("Load chunk before generating nether portal blocks.")
    @Config.DefaultBoolean(true)
    public static boolean fixVanillaNetherPortalGeneration;
}
