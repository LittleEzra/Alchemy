package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.AlchemicalCharge;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.datagen.recipe.DistillationRecipeBuilder;
import net.feliscape.alchemy.item.ModItems;
import net.feliscape.alchemy.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        planksFromLog(pWriter, ModBlocks.ASPEN_PLANKS.get(), ModTags.Items.ASPEN_LOGS, 4);
        woodFromLogs(pWriter, ModBlocks.ASPEN_WOOD.get(), ModBlocks.ASPEN_LOG.get());
        woodFromLogs(pWriter, ModBlocks.STRIPPED_ASPEN_WOOD.get(), ModBlocks.STRIPPED_ASPEN_LOG.get());

        Ingredient aspenPlankIngredient = Ingredient.of(ModBlocks.ASPEN_PLANKS.get());

        stairBuilder(ModBlocks.ASPEN_STAIRS.get(), aspenPlankIngredient);
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ASPEN_SLAB.get(), ModBlocks.ASPEN_PLANKS.get());
        doorBuilder(ModBlocks.ASPEN_DOOR.get(), aspenPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ASPEN_PLANKS.get()), has(ModBlocks.ASPEN_PLANKS.get()))
                .save(pWriter);
        trapdoorBuilder(ModBlocks.ASPEN_TRAPDOOR.get(), aspenPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ASPEN_PLANKS.get()), has(ModBlocks.ASPEN_PLANKS.get()))
                .save(pWriter);
        signBuilder(ModItems.ASPEN_SIGN.get(), aspenPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ASPEN_PLANKS.get()), has(ModBlocks.ASPEN_PLANKS.get()))
                .save(pWriter);
        hangingSign(pWriter, ModItems.ASPEN_HANGING_SIGN.get(), ModBlocks.ASPEN_PLANKS.get());

        fenceBuilder(ModBlocks.ASPEN_FENCE.get(), aspenPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ASPEN_PLANKS.get()), has(ModBlocks.ASPEN_PLANKS.get()))
                .save(pWriter);
        fenceGateBuilder(ModBlocks.ASPEN_FENCE_GATE.get(), aspenPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ASPEN_PLANKS.get()), has(ModBlocks.ASPEN_PLANKS.get()))
                .save(pWriter);;
        buttonBuilder(ModBlocks.ASPEN_BUTTON.get(), aspenPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ASPEN_PLANKS.get()), has(ModBlocks.ASPEN_PLANKS.get()))
                .save(pWriter);;
        pressurePlate(pWriter, ModBlocks.ASPEN_PRESSURE_PLATE.get(), ModBlocks.ASPEN_PLANKS.get());

        //region Distillations
        distillation(pWriter, ModItems.CLOUD_FLUFF.get(), new AlchemicalCharge(0, 2, 1, 0, 0));
        distillation(pWriter, Items.SCULK_SHRIEKER, new AlchemicalCharge(0, 0, 0, 0, 3));
        distillation(pWriter, Items.SCULK_CATALYST, new AlchemicalCharge(0, 0, 0, 0, 1));
        distillation(pWriter, Items.ECHO_SHARD, new AlchemicalCharge(0, 0, 0, 0, 8));
        distillation(pWriter, Items.BLAZE_POWDER, new AlchemicalCharge(2, 0, 0, 0, 0));
        distillation(pWriter, Items.BLAZE_ROD, new AlchemicalCharge(4, 0, 0, 0, 0));
        distillation(pWriter, Items.MAGMA_CREAM, new AlchemicalCharge(1, 0, 0, 0, 0));
        distillation(pWriter, ItemTags.LEAVES, new AlchemicalCharge(0, 0, 1, 0, 0), "leaves_distillation");
        //endregion
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Alchemy.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void distillation(Consumer<FinishedRecipe> pWriter, ItemLike pItem, AlchemicalCharge pCharge){
        DistillationRecipeBuilder.distillation(Ingredient.of(pItem), pCharge).save(pWriter, pItem);
    }
    protected static void distillation(Consumer<FinishedRecipe> pWriter, TagKey<Item> pTag, AlchemicalCharge pCharge, String pLocation){
        DistillationRecipeBuilder.distillation(Ingredient.of(pTag), pCharge).save(pWriter, Alchemy.asResource(pLocation));
    }
    protected static void distillation(Consumer<FinishedRecipe> pWriter, ItemLike pItem, AlchemicalCharge pCharge, String pLocation){
        DistillationRecipeBuilder.distillation(Ingredient.of(pItem), pCharge).save(pWriter, Alchemy.asResource(pLocation));
    }
}
