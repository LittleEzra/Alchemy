package net.feliscape.alchemy.entity;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.entity.custom.PrimedFlare;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Alchemy.MOD_ID);

    /* Registering an Entity:
    public static final RegistryObject<EntityType<ENTITY>> TWILIGHT_JELLYFISH = ENTITY_TYPES.register("ENTITY",
            () -> EntityType.Builder.of(ENTITY::new, MobCategory.AMBIENT).sized(1.0f, 1.0f)
                    .build(new ResourceLocation(Template.MOD_ID, "ENTITY").toString()));
                    */

    public static final RegistryObject<EntityType<PrimedFlare>> FLARE = ENTITY_TYPES.register("flare",
            () -> EntityType.Builder.<PrimedFlare>of(PrimedFlare::new, MobCategory.MISC).fireImmune().sized(1f, 0.625f)
                    .clientTrackingRange(10).updateInterval(10).build(new ResourceLocation(Alchemy.MOD_ID, "flare").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
