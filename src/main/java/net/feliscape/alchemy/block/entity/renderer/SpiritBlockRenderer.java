package net.feliscape.alchemy.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.block.entity.SpiritBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.client.model.data.ModelData;

public class SpiritBlockRenderer implements BlockEntityRenderer<SpiritBlockEntity> {
    private BlockRenderDispatcher blockRenderer;

    public SpiritBlockRenderer(BlockEntityRendererProvider.Context pContext) {
        blockRenderer = pContext.getBlockRenderDispatcher();
    }

    @Override
    public void render(SpiritBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

    }
}
