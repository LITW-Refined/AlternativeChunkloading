package de.pilz.alternativechunkloading.mixins.late;

import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import erebus.world.SpawnerErebus;

@Mixin(SpawnerErebus.class)
public class MixinErebus$SpawnerErebus {

    @Inject(
        method = "onServerTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/common/DimensionManager;getWorld(I)Lnet/minecraft/world/WorldServer;",
            shift = Shift.BY,
            by = 2),
        remap = false,
        cancellable = true)
    private void alternativeChunkloading$onServerTick$addNullCheck(CallbackInfo callback,
        @Local WorldServer erebusWorld) {
        if (erebusWorld == null) {
            callback.cancel();
        }
    }
}
