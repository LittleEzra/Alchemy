package net.feliscape.alchemy.worldgen;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.worldgen.tree.custom.AspenFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_TREE_KEY = registerKey("elderberry_tree");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        register(context, ASPEN_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ASPEN_LOG.get()),
                new StraightTrunkPlacer(4, 1, 3),

                BlockStateProvider.simple(ModBlocks.ASPEN_LEAVES.get()),
                new AspenFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3, UniformInt.of(9, 11)),

                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Alchemy.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
