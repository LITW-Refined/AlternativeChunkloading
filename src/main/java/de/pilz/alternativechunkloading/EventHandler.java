package de.pilz.alternativechunkloading;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager.ForceChunkEvent;
import net.minecraftforge.common.ForgeChunkManager.UnforceChunkEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import de.pilz.alternativechunkloading.configuration.ConfigAutoUnloadDimensions;
import de.pilz.alternativechunkloading.configuration.ConfigBetterChunkloading;

public class EventHandler {

    private HashMap<WorldServer, HashMap<ChunkCoordIntPair, Integer>> pendingForcedChunks = new HashMap<>();
    private HashMap<WorldServer, Integer> pendingUnloadDimensions = new HashMap<>();

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (!event.world.isRemote && event.world instanceof WorldServer) {
            WorldServer world = (WorldServer) event.world;

            if (ConfigBetterChunkloading.disableChunkLoadingOnRequest
                && !ConfigBetterChunkloading.isDimensionBlacklisted(world.provider.dimensionId)
                && !ConfigBetterChunkloading.isProviderBlacklisted(world.provider)) {
                IChunkProvider chunkProvider = world.getChunkProvider();

                if (chunkProvider instanceof ChunkProviderServer) {
                    ((ChunkProviderServer) chunkProvider).loadChunkOnProvideRequest = false;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (!event.world.isRemote) {
            pendingForcedChunks.remove(event.world);
            pendingUnloadDimensions.remove(event.world);
        }
    }

    @SubscribeEvent
    public void onChunkForce(ForceChunkEvent event) {
        if (ConfigBetterChunkloading.disableChunkLoadingOnRequest
            && ConfigBetterChunkloading.autoLoadChunksOnTicketCreation
            && !event.ticket.world.isRemote
            && !ConfigBetterChunkloading.isDimensionBlacklisted(event.ticket.world.provider.dimensionId)
            && !ConfigBetterChunkloading.isProviderBlacklisted(event.ticket.world.provider)) {
            if (event.ticket.world instanceof WorldServer) {
                // Do not load the chunk instantly to prevent colidation with sync chunk loading and chunkloaders that
                // creates a ticket while loading the chunk.
                var coordMap = getPendingForcedChunksForWorld((WorldServer) event.ticket.world);
                if (!coordMap.containsKey(event.location)) {
                    coordMap.put(event.location, 0);
                }

                // Don't auto unload dimension - we have forced chunks!
                pendingUnloadDimensions.remove(event.ticket.world);
            }
        }
    }

    @SubscribeEvent
    public void onChunkUnforce(UnforceChunkEvent event) {
        if (!event.ticket.world.isRemote
            && !ConfigBetterChunkloading.isDimensionBlacklisted(event.ticket.world.provider.dimensionId)
            && !ConfigBetterChunkloading.isProviderBlacklisted(event.ticket.world.provider)
            && event.ticket.world instanceof WorldServer) {
            WorldServer world = (WorldServer) event.ticket.world;

            // Prevent chunks to be auto loaded
            var coordMap = getPendingForcedChunksForWorld(world);
            if (coordMap.containsKey(event.location)) {
                coordMap.remove(event.location);
            }
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        pendingUnloadDimensions.remove(event.world);
    }

    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event) {
        if (event.side == Side.SERVER && event.world instanceof WorldServer) {
            WorldServer world = (WorldServer) event.world;

            // Load forced chunks
            if (!ConfigBetterChunkloading.isDimensionBlacklisted(event.world.provider.dimensionId)
                && !ConfigBetterChunkloading.isProviderBlacklisted(event.world.provider)) {
                loadChunks(world);
            }

            // Unload dimension
            if (!ConfigAutoUnloadDimensions.isDimensionBlacklisted(event.world.provider.dimensionId)
                && !ConfigAutoUnloadDimensions.isProviderBlacklisted(event.world.provider)) {
                unloadDimension(world);
            }
        }
    }

    private void checkDimensionToUnload(WorldServer world) {
        // Check blacklist
        if (!ConfigAutoUnloadDimensions.enabled
            || ConfigAutoUnloadDimensions.isDimensionBlacklisted(world.provider.dimensionId)
                && !ConfigAutoUnloadDimensions.isProviderBlacklisted(world.provider)) {
            return;
        }

        // Check loaded chunks
        if (world.theChunkProviderServer.getLoadedChunkCount() != 0) {
            return;
        }

        // Check forced chunks
        if (world.getPersistentChunks()
            .size() != 0) {
            return;
        }

        // Put on list or reset timer
        pendingUnloadDimensions.put(world, 0);
    }

    private HashMap<ChunkCoordIntPair, Integer> getPendingForcedChunksForWorld(WorldServer world) {
        if (!pendingForcedChunks.containsKey(world)) {
            pendingForcedChunks.put(world, new HashMap<>());
        }
        return pendingForcedChunks.get(world);
    }

    private void loadChunks(WorldServer world) {
        var coordMap = getPendingForcedChunksForWorld(world);
        var toRemove = new HashSet<ChunkCoordIntPair>();

        for (ChunkCoordIntPair coords : coordMap.keySet()) {
            Integer ticksWaited = coordMap.get(coords);

            // Wait at least one second (= 20 ticks) before loading forced chunks.
            if (ticksWaited >= ConfigBetterChunkloading.ticksBeforeLoadChunk) {
                IChunkProvider provider = world.getChunkProvider();
                if (!provider.chunkExists(coords.chunkXPos, coords.chunkZPos)) {
                    provider.loadChunk(coords.chunkXPos, coords.chunkZPos);
                }
                toRemove.add(coords);
            } else {
                // Tick
                coordMap.put(coords, ticksWaited + 1);
            }
        }

        for (ChunkCoordIntPair coords : toRemove) {
            coordMap.remove(coords);
        }
    }

    private void unloadDimension(WorldServer world) {
        Integer ticksWaited = pendingUnloadDimensions.getOrDefault(world, -1);

        if (ticksWaited == -1) {
            checkDimensionToUnload(world);
        } else if (ticksWaited >= ConfigAutoUnloadDimensions.ticksBeforeUnloadDimension) {
            // Unload dimension
            pendingUnloadDimensions.remove(world);
            DimensionManager.unloadWorld(world.provider.dimensionId);
        } else {
            // Tick
            pendingUnloadDimensions.put(world, ticksWaited + 1);
        }
    }
}
