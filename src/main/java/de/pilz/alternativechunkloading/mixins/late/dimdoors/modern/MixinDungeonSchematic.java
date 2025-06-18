package de.pilz.alternativechunkloading.mixins.late.dimdoors.modern;

import net.minecraft.world.World;

import org.dimdev.dimdoors.Point3D;
import org.dimdev.dimdoors.core.DimLink;
import org.dimdev.dimdoors.core.NewDimData;
import org.dimdev.dimdoors.dungeon.DungeonSchematic;
import org.dimdev.dimdoors.schematic.IBlockSetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import de.pilz.alternativechunkloading.Utils;

@Mixin(DungeonSchematic.class)
public class MixinDungeonSchematic {

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lorg/dimdev/dimdoors/dungeon/DungeonSchematic;initDoorTileEntity(Lnet/minecraft/world/World;Lorg/dimdev/dimdoors/Point3D;)V"),
        method = "createEntranceReverseLink",
        remap = false)
    private static void createEntranceReverseLink$loadChunk(World world, NewDimData dimension, Point3D pocketCenter,
        DimLink entryLink, CallbackInfo callback) {
        Utils.ensureBlockExists(world, pocketCenter.getX(), pocketCenter.getY(), pocketCenter.getZ());
    }

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lorg/dimdev/dimdoors/schematic/BlockRotator;transformPoint(Lorg/dimdev/dimdoors/Point3D;Lorg/dimdev/dimdoors/Point3D;ILorg/dimdev/dimdoors/Point3D;)V",
            shift = Shift.AFTER),
        method = "createExitDoorLink",
        remap = false)
    private static void createExitDoorLink$loadChunk(World world, NewDimData dimension, Point3D point, Point3D entrance,
        int rotation, Point3D pocketCenter, IBlockSetter blockSetter, CallbackInfo callback,
        @Local(ordinal = 3) Point3D location) {
        Utils.ensureBlockExists(world, location.getX(), location.getY(), location.getZ());
    }

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lorg/dimdev/dimdoors/schematic/BlockRotator;transformPoint(Lorg/dimdev/dimdoors/Point3D;Lorg/dimdev/dimdoors/Point3D;ILorg/dimdev/dimdoors/Point3D;)V",
            shift = Shift.AFTER),
        method = "createDimensionalDoorLink",
        remap = false)
    private static void createDimensionalDoorLink$loadChunk(World world, NewDimData dimension, Point3D point,
        Point3D entrance, int rotation, Point3D pocketCenter, CallbackInfo callback,
        @Local(ordinal = 3) Point3D location) {
        Utils.ensureBlockExists(world, location.getX(), location.getY(), location.getZ());
    }

    @Inject(at = @At(value = "HEAD"), method = "initDoorTileEntity", remap = false)
    private static void initDoorTileEntity$loadChunk(World world, Point3D point, CallbackInfo callback) {
        Utils.ensureBlockExists(world, point.getX(), point.getY(), point.getZ());
    }

    @Inject(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"),
        method = "writeDepthSign",
        remap = false)
    private static void writeDepthSign$loadChunk(World world, Point3D pocketCenter, int depth, CallbackInfo callback,
        @Local(ordinal = 0) int x, @Local(ordinal = 1) int y, @Local(ordinal = 2) int z) {
        Utils.ensureBlockExists(world, x, y, z);
    }
}
