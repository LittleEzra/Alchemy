package net.feliscape.alchemy.util;


import net.feliscape.alchemy.Alchemy;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType ASPEN = WoodType.register(new WoodType(Alchemy.MOD_ID + ":aspen", BlockSetType.OAK));
}
