package de.pilz.alternativechunkloading.mixins;

import com.gtnewhorizon.gtnhlib.mixin.ITargetedMod;

public enum TargetedModEx implements ITargetedMod {

    THEEREBUS("Erebus", null, "erebus");

    /** The "name" in the @Mod annotation */
    private final String modName;
    /** Class that implements the IFMLLoadingPlugin interface */
    private final String coreModClass;
    /** The "modid" in the @Mod annotation */
    private final String modId;

    TargetedModEx(String modName, String coreModClass) {
        this(modName, coreModClass, null);
    }

    TargetedModEx(String modName, String coreModClass, String modId) {
        this.modName = modName;
        this.coreModClass = coreModClass;
        this.modId = modId;
    }

    @Override
    public String toString() {
        return "TargetedMod{modName='" + modName + "', coreModClass='" + coreModClass + "', modId='" + modId + "'}";
    }

    @Override
    public String getCoreModClass() {
        return coreModClass;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public String getModName() {
        return modName;
    }
}
