package net.feliscape.alchemy.particle;

import net.feliscape.alchemy.Alchemy;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Alchemy.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ECHO = PARTICLE_TYPES.register("echo",
            () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CINTER_SPARK = PARTICLE_TYPES.register("cinter_spark",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FLARE_SMOKE = PARTICLE_TYPES.register("flare_smoke",
            () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FLARE_SPARK = PARTICLE_TYPES.register("flare_spark",
            () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
