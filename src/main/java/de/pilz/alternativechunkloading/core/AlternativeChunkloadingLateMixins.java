package de.pilz.alternativechunkloading.core;

import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;

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
