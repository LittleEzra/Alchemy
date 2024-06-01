package net.feliscape.alchemy.item;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Alchemy.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ALCHEMY = CREATIVE_MODE_TABS.register("alchemy",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CLOUD_FLUFF.get()))
                    .title(Component.translatable("creativetab.alchemy.alchemy_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.NET.get());

                        pOutput.accept(ModItems.CLOUD_FLUFF.get());
                        pOutput.accept(ModBlocks.CINTER_BLOCK.get());
                        pOutput.accept(ModItems.CINTER.get());
                        pOutput.accept(ModBlocks.FRENZIED_CINTER_BLOCK.get());
                        pOutput.accept(ModItems.FRENZIED_CINTER.get());
                        pOutput.accept(ModItems.FULMINATING_CINTER.get());
                        pOutput.accept(ModBlocks.SPIRIT_BLOCK.get());

                        pOutput.accept(ModItems.BLANK_TABLET.get());
                        pOutput.accept(ModItems.FIRE_TABLET.get());
                        pOutput.accept(ModItems.WATER_TABLET.get());
                        pOutput.accept(ModItems.NATURE_TABLET.get());
                        pOutput.accept(ModItems.ENCHANTMENT_TABLET.get());
                        pOutput.accept(ModItems.SPIRIT_TABLET.get());

                        pOutput.accept(ModBlocks.ALEMBIC.get());

                        pOutput.accept(ModBlocks.ECHO.get());

                        pOutput.accept(ModBlocks.ASPEN_LEAVES.get());
                        pOutput.accept(ModBlocks.ASPEN_LOG.get());
                        pOutput.accept(ModBlocks.ASPEN_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_ASPEN_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_ASPEN_WOOD.get());
                        pOutput.accept(ModBlocks.ASPEN_PLANKS.get());

                        pOutput.accept(ModItems.ASPEN_SIGN.get());
                        pOutput.accept(ModItems.ASPEN_HANGING_SIGN.get());
                        //pOutput.accept(ModItems.ASPEN_BOAT.get());
                        //pOutput.accept(ModItems.ASPEN_CHEST_BOAT.get());

                        pOutput.accept(ModBlocks.ASPEN_STAIRS.get());
                        pOutput.accept(ModBlocks.ASPEN_SLAB.get());
                        pOutput.accept(ModBlocks.ASPEN_FENCE.get());
                        pOutput.accept(ModBlocks.ASPEN_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.ASPEN_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.ASPEN_BUTTON.get());
                        pOutput.accept(ModBlocks.ASPEN_DOOR.get());
                        pOutput.accept(ModBlocks.ASPEN_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.ASPEN_SAPLING.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
