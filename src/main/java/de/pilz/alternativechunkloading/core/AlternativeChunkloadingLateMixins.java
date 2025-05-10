package de.pilz.alternativechunkloading.core;

import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhlib.mixin.IMixins;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import de.pilz.alternativechunkloading.mixins.Mixins;

@LateMixin
public class AlternativeChunkloadingLateMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.alternativechunkloading.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return IMixins.getLateMixins(Mixins.class, loadedMods);
    }
}
