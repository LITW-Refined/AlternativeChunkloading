package de.pilz.alternativechunkloading.mixins.late.dimdoors.legacy;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import org.dimdev.dimdoors.schematic.WorldBlockSetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.pilz.alternativechunkloading.Utils;

@Mixin(WorldBlockSetter.class)
public class MixinWorldBlockSettings {

    @Inject(at = @At(value = "HEAD"), method = "setBlock", remap = false)
    private void setBlock$loadChunk(World world, int x, int y, int z, Block block, int metadata,
        CallbackInfo callback) {
        Utils.ensureBlockExists(world, x, y, z);
    }
}
