package net.feliscape.alchemy.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.entity.custom.PrimedFlare;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.data.ModelData;

public class PrimedFlareRenderer extends EntityRenderer<PrimedFlare> {
    private final BlockRenderDispatcher blockRenderer;

    public PrimedFlareRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.5F;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
    }

    public void render(PrimedFlare pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0F, 0.5F, 0.0F);

        pMatrixStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        pMatrixStack.translate(-0.5F, -0.5F, 0.5F);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        this.blockRenderer.renderSingleBlock(ModBlocks.FLARE.get().defaultBlockState(), pMatrixStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(PrimedFlare pEntity) {
        return new ResourceLocation(Alchemy.MOD_ID, "textures/block/flare");
    }
}
