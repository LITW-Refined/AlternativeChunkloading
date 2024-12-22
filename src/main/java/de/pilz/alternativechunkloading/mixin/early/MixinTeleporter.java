package de.pilz.alternativechunkloading.mixin.early;

import net.minecraft.block.Block;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.pilz.alternativechunkloading.Utils;

@Mixin(Teleporter.class)
public class MixinTeleporter {

    @Redirect(
        method = "placeInExistingPortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block pilz$placeInExistingPortal$getBlock(WorldServer world, int x, int y, int z) {
        Utils.ensureChunkLoaded(world, x, y, z);
        return world.getBlock(x, y, z);
    }

    @Redirect(
        method = "placeInPortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;setBlock(IIILnet/minecraft/block/Block;)Z"))
    private boolean pilz$placeInPortal$setBlock(WorldServer world, int x, int y, int z, Block block) {
        Utils.ensureChunkLoaded(world, x, y, z);
        return world.setBlock(x, y, z, block);
    }

    @Redirect(
        method = "makePortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block pilz$makePortal$getBlock(WorldServer world, int x, int y, int z) {
        Utils.ensureChunkLoaded(world, x, y, z);
        return world.getBlock(x, y, z);
    }

    @Redirect(
        method = "makePortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;setBlock(IIILnet/minecraft/block/Block;)Z"))
    private boolean pilz$makePortal$setBlock(WorldServer world, int x, int y, int z, Block block) {
        Utils.ensureChunkLoaded(world, x, y, z);
        return world.setBlock(x, y, z, block);
    }

    @Redirect(
        method = "makePortal",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;isAirBlock(III)Z"))
    private boolean pilz$makePortal$isAirBlock(WorldServer world, int x, int y, int z) {
        Utils.ensureChunkLoaded(world, x, y, z);
        return world.isAirBlock(x, y, z);
    }
}
