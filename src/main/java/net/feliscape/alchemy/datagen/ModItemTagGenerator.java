package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Alchemy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.LEAVES)
                .add(ModBlocks.ASPEN_LEAVES.get().asItem());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ASPEN_LOG.get().asItem());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ASPEN_PLANKS.get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS)
                .add(ModBlocks.ASPEN_STAIRS.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.ASPEN_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS)
                .add(ModBlocks.ASPEN_BUTTON.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ASPEN_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.ASPEN_FENCE.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.ASPEN_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ASPEN_TRAPDOOR.get().asItem());
        this.tag(ItemTags.SAPLINGS)
                .add(ModBlocks.ASPEN_SAPLING.get().asItem());

        this.copy(ModTags.Blocks.ASPEN_LOGS, ModTags.Items.ASPEN_LOGS);
    }
}
