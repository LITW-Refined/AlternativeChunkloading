package de.pilz.alternativechunkloading.mixins.early.minecraft;

import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Mixin(ChunkProviderServer.class)
public abstract class MixinChunkProviderServer {

    @ModifyExpressionValue(
        method = "provideChunk",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/gen/ChunkProviderServer;loadChunkOnProvideRequest:Z",
            opcode = Opcodes.GETFIELD))
    private boolean alternativechunkloading$provideChunk$allowChunkload(boolean original) {
        return original || AlternativeChunkloading.ignoreThreads.contains(
            Thread.currentThread()
                .getId());
    }
}
