package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Alchemy.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CINTER_BLOCK);
        blockWithItem(ModBlocks.FRENZIED_CINTER_BLOCK);
        blockWithItemAndRenderType(ModBlocks.SPIRIT_BLOCK, "translucent");

        leavesBlock(ModBlocks.ASPEN_LEAVES, "cutout_mipped");

        logBlock((RotatedPillarBlock) ModBlocks.ASPEN_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.ASPEN_WOOD.get(), blockTexture(ModBlocks.ASPEN_LOG.get()), blockTexture(ModBlocks.ASPEN_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ASPEN_LOG.get(), blockTexture(ModBlocks.STRIPPED_ASPEN_LOG.get()),
                new ResourceLocation(Alchemy.MOD_ID, "block/stripped_aspen_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ASPEN_WOOD.get(), blockTexture(ModBlocks.STRIPPED_ASPEN_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_ASPEN_LOG.get()));

        blockItem(ModBlocks.ASPEN_LOG);
        blockItem(ModBlocks.ASPEN_WOOD);
        blockItem(ModBlocks.STRIPPED_ASPEN_LOG);
        blockItem(ModBlocks.STRIPPED_ASPEN_WOOD);

        blockWithItem(ModBlocks.ASPEN_PLANKS);

        signBlock(((StandingSignBlock) ModBlocks.ASPEN_SIGN.get()), ((WallSignBlock) ModBlocks.ASPEN_WALL_SIGN.get()),
                blockTexture(ModBlocks.ASPEN_PLANKS.get()));

        hangingSignBlock(ModBlocks.ASPEN_HANGING_SIGN.get(), ModBlocks.ASPEN_WALL_HANGING_SIGN.get(),
                blockTexture(ModBlocks.ASPEN_PLANKS.get()));

        stairsBlock(((StairBlock) ModBlocks.ASPEN_STAIRS.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.ASPEN_SLAB.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()));

        buttonBlock(((ButtonBlock) ModBlocks.ASPEN_BUTTON.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.ASPEN_PRESSURE_PLATE.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()));

        fenceBlock(((FenceBlock) ModBlocks.ASPEN_FENCE.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.ASPEN_FENCE_GATE.get()), blockTexture(ModBlocks.ASPEN_PLANKS.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.ASPEN_DOOR.get()), modLoc("block/aspen_door_bottom"), modLoc("block/aspen_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.ASPEN_TRAPDOOR.get()), modLoc("block/aspen_trapdoor"), true, "cutout");

        crossBlockWithRenderType(ModBlocks.ASPEN_SAPLING.get(), "cutout");
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Alchemy.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void blockWithItemAndRenderType(RegistryObject<Block> blockRegistryObject, String renderType){
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(name(blockRegistryObject.get()), blockTexture(blockRegistryObject.get())).renderType(renderType));
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject, String renderType){
        ModelFile model = models().withExistingParent(blockRegistryObject.getId().getPath(), "minecraft:block/leaves")
                .texture("all", blockTexture(blockRegistryObject.get())).renderType(renderType);
        getVariantBuilder(blockRegistryObject.get())
                .partialState().setModels( new ConfiguredModel(model));
        simpleBlockItem(blockRegistryObject.get(), model);
    }

    public void logBlockWithItem(RotatedPillarBlock block) {
        axisBlockWithItem(block, blockTexture(block), extend(blockTexture(block), "_top"));
    }

    public void axisBlockWithItem(RotatedPillarBlock block) {
        axisBlockWithItem(block, extend(blockTexture(block), "_side"),
                extend(blockTexture(block), "_end"));
    }

    public void axisBlockWithItem(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) {
        axisBlockWithItem(block,
                models().cubeColumn(name(block), side, end),
                models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
    }

    public void axisBlockWithItem(RotatedPillarBlock block, ModelFile vertical, ModelFile horizontal) {
        getVariantBuilder(block)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
        simpleBlockItem(block, vertical);
    }

    public void crossBlockWithRenderType(Block block, String renderType) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().cross(name(block), blockTexture(block)).renderType(renderType)));
    }

    public void horizontalBlockWithItem(RegistryObject<Block> block, ModelFile model){
        horizontalBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }
    public void cubeBottomTop(Block block){
        simpleBlockWithItem(block, models().cubeBottomTop(name(block),
                extend(blockTexture(block), "_side"),
                extend(blockTexture(block), "_bottom"),
                extend(blockTexture(block), "_top")));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
    private String name(Block block) {
        return key(block).getPath();
    }
    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

}
