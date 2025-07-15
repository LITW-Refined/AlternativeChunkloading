package de.pilz.alternativechunkloading.mixins;

import java.util.function.Supplier;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import de.pilz.alternativechunkloading.configuration.ConfigBetterChunkloading;
import de.pilz.alternativechunkloading.configuration.ConfigFixes;

public enum Mixins implements IMixins {

    VANILLA_MODIFY_FIX_STRUCTURE_GENERATION(Side.COMMON, Phase.EARLY,
        () -> ConfigBetterChunkloading.enable && ConfigBetterChunkloading.disableForPopulation,
        "minecraft.MixinChunkProviderServer", "minecraft.MixinChunk"),
    VANILLA_FIX_NETHER_PORTAL_GENERATION(Side.COMMON, Phase.EARLY, () -> ConfigFixes.fixVanillaNetherPortalGeneration,
        "minecraft.MixinTeleporter"),
    EREBUS_FIX_EREBUS_NULL_CHECKS(Side.COMMON, Phase.LATE, () -> ConfigFixes.fixErebusNullChecks,
        "erebus.MixinSpawnerErebus"),
    EREBUS_FIX_DIMDOORS_COMPAT(Side.COMMON, Phase.LATE, () -> ConfigFixes.fixDimDoorsCompatibility,
        "dimdoors.modern.MixinChunkBlockSetter", "dimdoors.modern.MixinDungeonSchematic",
        "dimdoors.modern.MixinWorldBlockSettings", "dimdoors.modern.MixinPocketBuilder"),
    EREBUS_FIX_DIMDOORS_LEGACY_COMPAT(Side.COMMON, Phase.LATE, () -> ConfigFixes.fixDimDoorsCompatibility,
        "dimdoors.legacy.MixinChunkBlockSetter", "dimdoors.legacy.MixinDungeonSchematic",
        "dimdoors.legacy.MixinWorldBlockSettings", "dimdoors.legacy.MixinPocketBuilder");

    private final MixinBuilder builder;

    Mixins(Side side, Phase phase, String... mixins) {
        builder = new MixinBuilder().addSidedMixins(side, mixins)
            .setPhase(phase);
    }

    Mixins(Side side, Phase phase, Supplier<Boolean> applyIf, String... mixins) {
        builder = new MixinBuilder().addSidedMixins(side, mixins)
            .setPhase(phase)
            .setApplyIf(applyIf);
    }

    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }
}
