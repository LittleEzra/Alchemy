package net.feliscape.alchemy.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.recipe.DistillationRecipe;
import net.feliscape.alchemy.screen.AlembicScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIAlchemyPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Alchemy.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new DistillationCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<DistillationRecipe> juicingRecipes = recipeManager.getAllRecipesFor(DistillationRecipe.Type.INSTANCE);
        registration.addRecipes(DistillationCategory.DISTILLATION_TYPE, juicingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlembicScreen.class, 72, 35, 22, 15,
                DistillationCategory.DISTILLATION_TYPE);
    }
}
