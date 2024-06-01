package net.feliscape.alchemy.damagesource;

import net.feliscape.alchemy.Alchemy;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> FRENZIED_CINTER = register("frenzied_cinter");

    public static void bootstrap(BootstapContext<DamageType> context){
        context.register(ModDamageTypes.FRENZIED_CINTER, new DamageType("frenzied_cinter", 0.1F));
    }

    private static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Alchemy.MOD_ID, name));
    }
}
