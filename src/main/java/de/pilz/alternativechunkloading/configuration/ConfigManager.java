package de.pilz.alternativechunkloading.configuration;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

public final class ConfigManager {

    public static void registerConfigs() {
        try {
            ConfigurationManager.registerConfig(ConfigGeneral.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }
}
