package net.feliscape.alchemy.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.entity.EchoBlockEntity;
import net.feliscape.alchemy.entity.client.ModModelLayers;
import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.joml.Quaternionf;

public class EchoRenderer implements BlockEntityRenderer<EchoBlockEntity> {
    public static final Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(Alchemy.MOD_ID, "entity/echo"));
    private final BlockEntityRenderDispatcher renderer;
    private final ModelPart echo;

    public EchoRenderer(BlockEntityRendererProvider.Context pContext) {
        this.renderer = pContext.getBlockEntityRenderDispatcher();
        this.echo = pContext.bakeLayer(ModModelLayers.ECHO);
    }

    public static LayerDefinition createEchoLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("echo", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void render(EchoBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        float f = (float)pBlockEntity.tickCount + pPartialTick;
        float f2 = Mth.sin(f * 0.1F) / 2.0F + 0.5F;
        Camera camera = this.renderer.camera;
        pPoseStack.pushPose();
        //pPoseStack.translate(0.5F, 0.3F + f2 * 0.2F, 0.5F); // with undulation
        pPoseStack.translate(0.5F, 0.5F, 0.5F);
        pPoseStack.scale(0.5F, 0.5F, 0.5F);
        float f3 = -camera.getYRot();
        pPoseStack.mulPose((new Quaternionf()).rotationYXZ(f3 * ((float)Math.PI / 180F), camera.getXRot() * ((float)Math.PI / 180F), (float)Math.PI));
        pPoseStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
        this.echo.render(pPoseStack, TEXTURE.buffer(pBuffer, RenderType::entityCutoutNoCull), pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }
}
