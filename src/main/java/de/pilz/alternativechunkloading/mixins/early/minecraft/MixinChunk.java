package de.pilz.alternativechunkloading.mixins.early.minecraft;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import de.pilz.alternativechunkloading.AlternativeChunkloading;

@Mixin(Chunk.class)
public class MixinChunk {

    @WrapMethod(method = "populateChunk")
    private void populateChunk(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int p_76624_3_, int p_76624_4_,
        Operation<Void> original) {
        long thread = Thread.currentThread()
            .getId();
        AlternativeChunkloading.ignoreThreads.add(thread);
        original.call(p_76624_1_, p_76624_2_, p_76624_3_, p_76624_4_);
        AlternativeChunkloading.ignoreThreads.remove(thread);
    }
}
