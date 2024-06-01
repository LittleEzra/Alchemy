package net.feliscape.alchemy.util;

import net.feliscape.alchemy.Alchemy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> ASPEN_LOGS = create("aspen_logs");

        private static TagKey<Block> create(String path){
            return BlockTags.create(new ResourceLocation(Alchemy.MOD_ID, path));
        }
    }

    public static class Items{
        public static final TagKey<Item> ASPEN_LOGS = create("aspen_logs");

        private static TagKey<Item> create(String path){
            return ItemTags.create(new ResourceLocation(Alchemy.MOD_ID, path));
        }
    }
}
