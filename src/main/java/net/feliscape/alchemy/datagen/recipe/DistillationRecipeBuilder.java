package net.feliscape.alchemy.datagen.recipe;

import com.google.gson.JsonObject;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.AlchemicalCharge;
import net.feliscape.alchemy.recipe.ModRecipes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DistillationRecipeBuilder {
    private final RecipeSerializer<?> type;
    private final Ingredient ingredient;
    private final AlchemicalCharge alchemicalCharge;

    public DistillationRecipeBuilder(RecipeSerializer<?> type,Ingredient pIngredient, AlchemicalCharge pAlchemicalCharge) {
        this.type = type;
        this.ingredient = pIngredient;
        this.alchemicalCharge = pAlchemicalCharge;
    }

    public static DistillationRecipeBuilder distillation(Ingredient pIngredient, AlchemicalCharge pAlchemicalCharge){
        return new DistillationRecipeBuilder(ModRecipes.DISTILLATION.get(), pIngredient, pAlchemicalCharge);
    }
    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ItemLike itemLike) {
        pRecipeConsumer.accept(new DistillationRecipeBuilder.Result(getRecipeId(itemLike), this.type, this.ingredient, this.alchemicalCharge));
    }

    private static ResourceLocation getRecipeId(ItemLike itemLike) {
        return Alchemy.asResource(ForgeRegistries.ITEMS.getKey(itemLike.asItem()).getPath() + "_distillation");
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        pRecipeConsumer.accept(new DistillationRecipeBuilder.Result(pLocation, this.type, this.ingredient, this.alchemicalCharge));
    }

    public static record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient ingredient, AlchemicalCharge alchemicalCharge) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", this.ingredient.toJson());
            pJson.add("alchemical_charge", this.alchemicalCharge.toJson());
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }
        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
