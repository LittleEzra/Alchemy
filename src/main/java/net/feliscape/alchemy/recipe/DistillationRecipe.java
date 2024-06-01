package net.feliscape.alchemy.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.AlchemicalCharge;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DistillationRecipe implements Recipe<SimpleContainer> {
    private final Ingredient ingredient;
    private final ResourceLocation id;
    private final AlchemicalCharge alchemicalCharge;

    public DistillationRecipe(Ingredient pIngredient, AlchemicalCharge pAlchemicalCharge, ResourceLocation pId) {
        this.ingredient = pIngredient;
        this.id = pId;
        this.alchemicalCharge = pAlchemicalCharge;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return ingredient.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, ingredient);
    }

    public AlchemicalCharge getAlchemicalCharge() {
        return alchemicalCharge;
    }


    public static class Type implements RecipeType<DistillationRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "distillation";
    }

    public static class Serializer implements RecipeSerializer<DistillationRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Alchemy.MOD_ID, "distillation");

        @Override
        public DistillationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(pSerializedRecipe, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredient"), false);
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"), false);
            }
            AlchemicalCharge alchemicalCharge = AlchemicalCharge.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "alchemical_charge"));
            return new DistillationRecipe(ingredient, alchemicalCharge, pRecipeId);
        }

        @Override
        public @Nullable DistillationRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new DistillationRecipe(Ingredient.fromNetwork(pBuffer), AlchemicalCharge.fromNetwork(pBuffer), pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, DistillationRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pRecipe.alchemicalCharge.toNetwork(pBuffer);
        }
    }
}
