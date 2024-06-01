package net.feliscape.alchemy.datagen.loot;

import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    //protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));

    @Override
    protected void generate() {
        // If you want a block to drop nothing ever, call .noLootTable() on the block. That will make it exempt from this.

        /* Special Loot Tables:
        this.add(ModBlocks.BLOCK.get(),
                block -> BUILDERFUNCTION);*/

        this.dropSelf(ModBlocks.ALEMBIC.get());
        this.dropSelf(ModBlocks.ECHO.get());

        this.dropSelf(ModBlocks.CINTER_BLOCK.get());
        this.dropSelf(ModBlocks.FRENZIED_CINTER_BLOCK.get());
        this.dropSelf(ModBlocks.FLARE.get());
        this.dropSelf(ModBlocks.SPIRIT_BLOCK.get());

        this.dropSelf(ModBlocks.ASPEN_LOG.get());
        this.dropSelf(ModBlocks.ASPEN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ASPEN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ASPEN_WOOD.get());
        this.dropSelf(ModBlocks.ASPEN_PLANKS.get());

        this.add(ModBlocks.ASPEN_SIGN.get(), block ->
                createSingleItemTable(ModItems.ASPEN_SIGN.get()));
        this.add(ModBlocks.ASPEN_WALL_SIGN.get(), block ->
                createSingleItemTable(ModItems.ASPEN_SIGN.get()));
        this.add(ModBlocks.ASPEN_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.ASPEN_HANGING_SIGN.get()));
        this.add(ModBlocks.ASPEN_WALL_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.ASPEN_HANGING_SIGN.get()));

        dropSelf(ModBlocks.ASPEN_STAIRS.get());
        dropSelf(ModBlocks.ASPEN_BUTTON.get());
        dropSelf(ModBlocks.ASPEN_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ASPEN_TRAPDOOR.get());
        dropSelf(ModBlocks.ASPEN_FENCE.get());
        dropSelf(ModBlocks.ASPEN_FENCE_GATE.get());
        dropSelf(ModBlocks.ASPEN_SAPLING.get());

        this.add(ModBlocks.ASPEN_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ASPEN_SLAB.get()));
        this.add(ModBlocks.ASPEN_DOOR.get(),
                block -> createDoorTable(ModBlocks.ASPEN_DOOR.get()));

        this.add(ModBlocks.ASPEN_LEAVES.get(),
                block -> createLeavesDrops(ModBlocks.ASPEN_LEAVES.get(), ModBlocks.ASPEN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
