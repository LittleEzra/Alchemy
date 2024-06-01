package net.feliscape.alchemy.worldgen.tree;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.worldgen.tree.custom.AspenFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, Alchemy.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<AspenFoliagePlacer>> ASPEN_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("aspen_foliage_placer", () -> new FoliagePlacerType<>(AspenFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus){
        FOLIAGE_PLACERS.register(eventBus);
    }
}
