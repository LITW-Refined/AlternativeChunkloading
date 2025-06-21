package de.pilz.alternativechunkloading.mixins.early.minecraft;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderGenerate;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(ChunkProviderGenerate.class)
public abstract class MixinChunkProviderGenerate {

    @WrapMethod(method = "provideChunk")
    private Chunk alternativechunkloading$provideChunk$addremovethread(int x, int y, Operation<Chunk> original) {
        // long thread = Thread.currentThread().getId();
        // AlternativeChunkloading.ignoreThreads.add(thread);
        Chunk chunk = original.call(x, y);
        // AlternativeChunkloading.ignoreThreads.remove(thread);
        return chunk;
    }
}
