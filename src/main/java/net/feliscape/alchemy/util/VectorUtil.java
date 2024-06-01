package net.feliscape.alchemy.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class VectorUtil {
    public static String Vec3ToString(Vec3 vec3, boolean floor){
        return "x: " + (floor ? Math.floor(vec3.x) : vec3.x) +
                ", y: " + (floor ? Math.floor(vec3.y) : vec3.y) +
                ", z: " + (floor ? Math.floor(vec3.z) : vec3.z);
    }
    public static String Vec3ToStringXZ(Vec3 vec3, boolean floor){
        return "x: " + (floor ? Math.floor(vec3.x) : vec3.x) +
                ", z: " + (floor ? Math.floor(vec3.z) : vec3.z);
    }
    public static String BlockPosToString(BlockPos pos){
        return "x: " + pos.getX() +
                ", y: " + pos.getX() +
                ", z: " + pos.getZ();
    }
    public static String BlockPosToStringXZ(BlockPos pos){
        return "x: " + pos.getX() +
                ", z: " + pos.getZ();
    }
}
