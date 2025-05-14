package de.pilz.alternativechunkloading.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import de.pilz.alternativechunkloading.Utils;

@Mixin(Teleporter.class)
public class MixinTeleporter {

    @WrapOperation(
        method = "placeInExistingPortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block placeInExistingPortal$getBlock(WorldServer world, int x, int y, int z, Operation<Block> original) {
        Utils.ensureBlockExists(world, x, y, z);
        return original.call(world, x, y, z);
    }

    @WrapOperation(
        method = "placeInExistingPortal",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;isAirBlock(III)Z"))
    private boolean placeInExistingPortal$isAirBlock(WorldServer world, int x, int y, int z,
        Operation<Boolean> original) {
        Utils.ensureBlockExists(world, x, y, z);
        return original.call(world, x, y, z);
    }

    @WrapOperation(
        method = "placeInPortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;setBlock(IIILnet/minecraft/block/Block;)Z"))
    private boolean placeInPortal$setBlock(WorldServer world, int x, int y, int z, Block block,
        Operation<Boolean> original) {
        Utils.ensureBlockExists(world, x, y, z);
        return original.call(world, x, y, z, block);
    }

    @WrapOperation(
        method = "makePortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;getBlock(III)Lnet/minecraft/block/Block;"))
    private Block makePortal$getBlock(WorldServer world, int x, int y, int z, Operation<Block> original) {
        Utils.ensureBlockExists(world, x, y, z);
        return original.call(world, x, y, z);
    }

    @WrapOperation(
        method = "makePortal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;setBlock(IIILnet/minecraft/block/Block;)Z"))
    private boolean makePortal$setBlock(WorldServer world, int x, int y, int z, Block block,
        Operation<Boolean> original) {
        Utils.ensureBlockExists(world, x, y, z);
        return original.call(world, x, y, z, block);
    }

    @WrapOperation(
        method = "makePortal",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;isAirBlock(III)Z"))
    private boolean makePortal$isAirBlock(WorldServer world, int x, int y, int z, Operation<Boolean> original) {
        Utils.ensureBlockExists(world, x, y, z);
        return original.call(world, x, y, z);
    }
}
