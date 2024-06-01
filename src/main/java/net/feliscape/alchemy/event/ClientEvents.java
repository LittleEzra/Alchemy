package net.feliscape.alchemy.event;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.entity.ModBlockEntities;
import net.feliscape.alchemy.block.entity.renderer.EchoRenderer;
import net.feliscape.alchemy.entity.client.ModModelLayers;
import net.feliscape.alchemy.particle.ModParticles;
import net.feliscape.alchemy.particle.custom.CinterSparkParticle;
import net.feliscape.alchemy.particle.custom.EchoParticle;
import net.feliscape.alchemy.particle.custom.FlareSmokeParticle;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Alchemy.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

    }

    @Mod.EventBusSubscriber(modid = Alchemy.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ModModelLayers.ECHO, EchoRenderer::createEchoLayer);
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(ModParticles.ECHO.get(), EchoParticle.Provider::new);
            event.registerSpriteSet(ModParticles.CINTER_SPARK.get(), CinterSparkParticle.Provider::new);
            event.registerSpriteSet(ModParticles.FLARE_SMOKE.get(), FlareSmokeParticle.Provider::new);
            event.registerSpriteSet(ModParticles.FLARE_SPARK.get(), CinterSparkParticle.PersistentProvider::new);
        }
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.ECHO.get(), EchoRenderer::new);
        }
    }
}
