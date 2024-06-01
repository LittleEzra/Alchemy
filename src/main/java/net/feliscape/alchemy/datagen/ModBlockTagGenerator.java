package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Alchemy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //this.tag(ModTags.Blocks.NAME)
        //        .add(Tags.Blocks.STONE);

        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ASPEN_FENCE_GATE.get());


        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ASPEN_PLANKS.get());
        this.tag(ModTags.Blocks.ASPEN_LOGS)
                .add(ModBlocks.ASPEN_LOG.get())
                .add(ModBlocks.ASPEN_WOOD.get())
                .add(ModBlocks.STRIPPED_ASPEN_LOG.get())
                .add(ModBlocks.STRIPPED_ASPEN_WOOD.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.ASPEN_LOGS);

        this.tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.ASPEN_STAIRS.get());
        this.tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.ASPEN_SLAB.get());
        this.tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.ASPEN_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ASPEN_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.ASPEN_FENCE.get());
        this.tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.ASPEN_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ASPEN_TRAPDOOR.get());
        this.tag(BlockTags.SAPLINGS)
                .add(ModBlocks.ASPEN_SAPLING.get());

        this.tag(BlockTags.LEAVES)
                .add(ModBlocks.ASPEN_LEAVES.get()
                );
    }
}
