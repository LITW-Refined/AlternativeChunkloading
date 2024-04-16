package de.pilz.alternativechunkloading;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.pilz.alternativechunkloading.configuration.ConfigManager;

@Mod(
    modid = AlternativeChunkloading.MODID,
    version = Tags.VERSION,
    name = "Alternative Chunkloading",
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*")
public class AlternativeChunkloading {

    public static final String MODID = "alternativechunkloading";
    public static final Logger LOG = LogManager.getLogger(MODID);

    EventHandler eventHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigManager.registerConfigs();
        AlternativeChunkloading.LOG.info("I am the mod 'Alternative Chunkloading' at version " + Tags.VERSION);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        FMLCommonHandler.instance()
            .bus()
            .register(eventHandler);
    }
}
