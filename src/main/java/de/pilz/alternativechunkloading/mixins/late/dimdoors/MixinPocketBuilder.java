package de.pilz.alternativechunkloading.mixins.late.dimdoors;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import org.dimdev.dimdoors.config.DDProperties;
import org.dimdev.dimdoors.util.Point4D;
import org.dimdev.dimdoors.world.PocketBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import de.pilz.alternativechunkloading.Utils;

@Mixin(PocketBuilder.class)
public class MixinPocketBuilder {

    @Inject(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"),
        method = "getDoorOrientation",
        remap = false)
    private static void getDoorOrientation$loadChunk(Point4D source, DDProperties properties,
        CallbackInfoReturnable<Integer> callback, @Local World world) {
        Utils.ensureBlockExists(world, source.getX(), source.getY(), source.getZ());
    }

    @Inject(at = @At(value = "HEAD"), method = "setBlockDirectly", remap = false)
    private static void setBlockDirectly$loadChunk(World world, int x, int y, int z, Block block, int metadata,
        CallbackInfo callback) {
        Utils.ensureBlockExists(world, x, y, z);
    }
}
