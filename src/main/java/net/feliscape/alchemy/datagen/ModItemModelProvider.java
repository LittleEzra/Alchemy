package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Alchemy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.CLOUD_FLUFF);
        simpleItem(ModItems.CINTER);
        simpleItem(ModItems.FRENZIED_CINTER);
        simpleItem(ModItems.FULMINATING_CINTER);
        blockItemSprite(ModBlocks.FLARE);

        simpleItem(ModItems.BLANK_TABLET);

        simpleItem(ModItems.FIRE_TABLET);
        simpleItem(ModItems.WATER_TABLET);
        simpleItem(ModItems.NATURE_TABLET);
        simpleItem(ModItems.ENCHANTMENT_TABLET);
        simpleItem(ModItems.SPIRIT_TABLET);

        handheldItem(ModItems.NET);

        manualBlockItem(ModBlocks.ALEMBIC);
        blockItemSprite(ModBlocks.ECHO);

        //region Aspen Wood
        simpleItem(ModItems.ASPEN_SIGN);
        simpleItem(ModItems.ASPEN_HANGING_SIGN);

        //simpleItem(ModItems.ASPEN_BOAT);
        //simpleItem(ModItems.ASPEN_CHEST_BOAT);

        blockItemSprite(ModBlocks.ASPEN_DOOR);

        manualBlockItem(ModBlocks.ASPEN_STAIRS);
        manualBlockItem(ModBlocks.ASPEN_SLAB);
        manualBlockItem(ModBlocks.ASPEN_PRESSURE_PLATE);
        manualBlockItem(ModBlocks.ASPEN_FENCE_GATE);
        buttonItem(ModBlocks.ASPEN_BUTTON, ModBlocks.ASPEN_PLANKS);
        fenceItem(ModBlocks.ASPEN_FENCE, ModBlocks.ASPEN_PLANKS);
        trapdoorItem(ModBlocks.ASPEN_TRAPDOOR);

        generatedBlockItem(ModBlocks.ASPEN_SAPLING);
        //endregion
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = Alchemy.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }
    private void trimmedLeatherArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = Alchemy.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation armorItemOverlayResLoc = new ResourceLocation(MOD_ID, armorItemPath + "_overlay");
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", armorItemOverlayResLoc)
                        .texture("layer2", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()))
                        .texture("layer1",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath() + "_overlay"));
            });
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder handheldItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder rotatedHandheldItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(Alchemy.MOD_ID, "item/rotated_handheld")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void manualBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(Alchemy.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(Alchemy.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(Alchemy.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(Alchemy.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder blockItemSprite(RegistryObject<Block> item) { // Uses a block instead of item
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID,"item/" + item.getId().getPath()));
    }
    private ItemModelBuilder generatedBlockItem(RegistryObject<Block> item) { // Uses the texture from textures/block
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID,"block/" + item.getId().getPath()));
    }
    private ItemModelBuilder torchItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID,"block/" + item.getId().getPath()));
    }
    private ItemModelBuilder mugItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(Alchemy.MOD_ID, "item/mug_item")).texture("layer0",
                new ResourceLocation(Alchemy.MOD_ID, "item/" + item.getId().getPath()));
    }
}
