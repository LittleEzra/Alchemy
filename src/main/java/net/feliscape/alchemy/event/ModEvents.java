package net.feliscape.alchemy.event;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Alchemy.MOD_ID)
    public static class ForgeEvents{
        public static void enderEntityTeleport(EntityTeleportEvent.EnderEntity event){
            Vec3 target = event.getTarget();
            BlockPos blockPos = BlockPos.containing(target);
            if (event.getEntity().level().getBlockState(blockPos).is(ModBlocks.SPIRIT_BLOCK.get())){
                event.setCanceled(true);
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Alchemy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents{
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event){

        }
    }
}
