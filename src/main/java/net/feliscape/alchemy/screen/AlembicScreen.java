package net.feliscape.alchemy.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.Element;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlembicScreen extends AbstractContainerScreen<AlembicMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemy.MOD_ID, "textures/gui/alembic.png");

    @Override
    protected void init() {
        super.init();
    }

    public AlembicScreen(AlembicMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.drawString(this.font, Integer.toString(this.menu.getAmount(Element.FIRE)), x + 143, y + 11, 16733525, false); // Fire
        guiGraphics.drawString(this.font, Integer.toString(this.menu.getAmount(Element.WATER)), x + 143, y + 23, 5592575, false); // Water
        guiGraphics.drawString(this.font, Integer.toString(this.menu.getAmount(Element.NATURE)), x + 143, y + 35, 5635925, false); // Nature
        guiGraphics.drawString(this.font, Integer.toString(this.menu.getAmount(Element.ENCHANTMENT)), x + 143, y + 47, 11141290, false); // Enchantment
        guiGraphics.drawString(this.font, Integer.toString(this.menu.getAmount(Element.SPIRIT)), x + 143, y + 59, 5636095, false); // Spirit
        if (this.menu.isLit()) {
            int litProgress = this.menu.getLitProgress();
            guiGraphics.blit(TEXTURE, x + 48, y + 36 + 12 - litProgress, 176, 12 - litProgress, 14, litProgress + 1);
        }

        int burnProgress = this.menu.getBurnProgress();
        guiGraphics.blit(TEXTURE, x + 71, y + 34, 176, 14, burnProgress + 1, 16);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
