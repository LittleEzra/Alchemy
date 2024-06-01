package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.damagesource.ModDamageTypes;
import net.feliscape.alchemy.datagen.language.ModLanguageProvider;
import net.feliscape.alchemy.worldgen.ModConfiguredFeatures;
import net.feliscape.alchemy.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Alchemy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            //.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            //.add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            //.add(Registries.BIOME, ModBiomes::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
            //.add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)
            ;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModLanguageProvider(packOutput, "en_us"));

        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTagProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModBiomeTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, BUILDER, Set.of(Alchemy.MOD_ID)));
    }
}
