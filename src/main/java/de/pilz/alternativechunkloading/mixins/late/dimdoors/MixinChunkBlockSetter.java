package de.pilz.alternativechunkloading.mixins.late.dimdoors;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import org.dimdev.dimdoors.schematic.ChunkBlockSetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.pilz.alternativechunkloading.Utils;

@Mixin(ChunkBlockSetter.class)
public class MixinChunkBlockSetter {

    @Inject(at = @At(value = "HEAD"), method = "setBlock", remap = false)
    private static void setBlock$loadChunk(World world, int x, int y, int z, Block block, int metadata,
        CallbackInfo callback) {
        Utils.ensureBlockExists(world, x, y, z);
    }
}
