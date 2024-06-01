package net.feliscape.alchemy.util;

import net.minecraft.util.RandomSource;

public class RandomUtil {
    public static double particleOffset(RandomSource random, double spread){
        return random.nextDouble() * spread * (random.nextBoolean() ? 1 : -1);
    }
}
