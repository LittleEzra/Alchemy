package net.feliscape.alchemy.compat;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.Element;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.recipe.DistillationRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class DistillationCategory implements IRecipeCategory<DistillationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Alchemy.MOD_ID, "distillation");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Alchemy.MOD_ID, "textures/gui/alembic.png");

    public static final RecipeType<DistillationRecipe> DISTILLATION_TYPE =
            new RecipeType<>(UID, DistillationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;

    public DistillationCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 42, 7, 130, 63);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ALEMBIC.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(TEXTURE, 176, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
        staticFlame = helper.createDrawable(TEXTURE, 176, 0, 14, 14);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 200, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public RecipeType<DistillationRecipe> getRecipeType() {
        return DISTILLATION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.alchemy.alembic");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DistillationRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 6, 6).addIngredients(ingredients.get(0));
    }

    protected IDrawableAnimated getArrow(DistillationRecipe recipe) {
        return this.cachedArrows.getUnchecked(200);
    }

    @Override
    public void draw(DistillationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        animatedFlame.draw(guiGraphics, 6, 29);

        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(guiGraphics, 29, 27);

        guiGraphics.drawString(Minecraft.getInstance().font, Integer.toString(recipe.getAlchemicalCharge().get(Element.FIRE)), 101, 4, 16733525, false); // Fire
        guiGraphics.drawString(Minecraft.getInstance().font, Integer.toString(recipe.getAlchemicalCharge().get(Element.WATER)), 101, 16, 5592575, false); // Water
        guiGraphics.drawString(Minecraft.getInstance().font, Integer.toString(recipe.getAlchemicalCharge().get(Element.NATURE)), 101, 28, 5635925, false); // Nature
        guiGraphics.drawString(Minecraft.getInstance().font, Integer.toString(recipe.getAlchemicalCharge().get(Element.ENCHANTMENT)), 101, 40, 11141290, false); // Enchantment
        guiGraphics.drawString(Minecraft.getInstance().font, Integer.toString(recipe.getAlchemicalCharge().get(Element.SPIRIT)), 101, 52, 5636095, false); // Spirit
    }
}
