package de.pilz.alternativechunkloading.mixins;

import java.util.List;
import java.util.function.Supplier;

import com.gtnewhorizon.gtnhlib.mixin.IMixins;
import com.gtnewhorizon.gtnhlib.mixin.ITargetedMod;
import com.gtnewhorizon.gtnhlib.mixin.MixinBuilder;
import com.gtnewhorizon.gtnhlib.mixin.Phase;
import com.gtnewhorizon.gtnhlib.mixin.Side;
import com.gtnewhorizon.gtnhlib.mixin.TargetedMod;

import de.pilz.alternativechunkloading.configuration.ConfigBetterChunkloading;
import de.pilz.alternativechunkloading.configuration.ConfigFixes;

public enum Mixins implements IMixins {

    VANILLA_MODIFY_FIX_STRUCTURE_GENERATION(
        new MixinBuilder("Modifies provideChunk to block unallowed requests.").addTargetedMod(TargetedMod.VANILLA)
            .setSide(Side.BOTH)
            .setPhase(Phase.EARLY)
            .setApplyIf(() -> ConfigBetterChunkloading.enable && ConfigBetterChunkloading.disableForPopulation)
            .addMixinClasses("minecraft.MixinChunkProviderServer", "minecraft.MixinChunk")),
    VANILLA_FIX_NETHER_PORTAL_GENERATION(
        new MixinBuilder("Fix nether portal generation").addTargetedMod(TargetedMod.VANILLA)
            .setSide(Side.BOTH)
            .setPhase(Phase.EARLY)
            .setApplyIf(() -> ConfigFixes.fixVanillaNetherPortalGeneration)
            .addMixinClasses("minecraft.MixinTeleporter")),
    EREBUS_FIX_EREBUS_NULL_CHECKS(new MixinBuilder("Add missing null checks").addTargetedMod(TargetedModEx.THEEREBUS)
        .setSide(Side.BOTH)
        .setPhase(Phase.LATE)
        .setApplyIf(() -> ConfigFixes.fixErebusNullChecks)
        .addMixinClasses("erebus.MixinSpawnerErebus")),
    EREBUS_FIX_DIMDOORS_COMPAT(
        new MixinBuilder("Add compatibility with Dimensional Doors").addTargetedMod(TargetedModEx.DIMDOORS)
            .setSide(Side.BOTH)
            .setPhase(Phase.LATE)
            .setApplyIf(() -> ConfigFixes.fixDimDoorsCompatibility)
            .addMixinClasses(
                "dimdoors.modern.MixinChunkBlockSetter",
                "dimdoors.modern.MixinDungeonSchematic",
                "dimdoors.modern.MixinWorldBlockSettings",
                "dimdoors.modern.MixinPocketBuilder")),
    EREBUS_FIX_DIMDOORS_LEGACY_COMPAT(
        new MixinBuilder("Add compatibility with Dimensional Doors").addTargetedMod(TargetedModEx.DIMDOORS)
            .setSide(Side.BOTH)
            .setPhase(Phase.LATE)
            .setApplyIf(() -> ConfigFixes.fixDimDoorsCompatibility)
            .addMixinClasses(
                "dimdoors.legacy.MixinChunkBlockSetter",
                "dimdoors.legacy.MixinDungeonSchematic",
                "dimdoors.legacy.MixinWorldBlockSettings",
                "dimdoors.legacy.MixinPocketBuilder"));

    private final List<String> mixinClasses;
    private final Supplier<Boolean> applyIf;
    private final Phase phase;
    private final Side side;
    private final List<ITargetedMod> targetedMods;
    private final List<ITargetedMod> excludedMods;

    Mixins(MixinBuilder builder) {
        this.mixinClasses = builder.mixinClasses;
        this.applyIf = builder.applyIf;
        this.side = builder.side;
        this.targetedMods = builder.targetedMods;
        this.excludedMods = builder.excludedMods;
        this.phase = builder.phase;
        if (this.targetedMods.isEmpty()) {
            throw new RuntimeException("No targeted mods specified for " + this.name());
        }
        if (this.applyIf == null) {
            throw new RuntimeException("No ApplyIf function specified for " + this.name());
        }
    }

    public List<String> getMixinClasses() {
        return mixinClasses;
    }

    public Supplier<Boolean> getApplyIf() {
        return applyIf;
    }

    public Phase getPhase() {
        return phase;
    }

    public Side getSide() {
        return side;
    }

    public List<ITargetedMod> getTargetedMods() {
        return targetedMods;
    }

    public List<ITargetedMod> getExcludedMods() {
        return excludedMods;
    }

}
