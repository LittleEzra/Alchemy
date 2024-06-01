package net.feliscape.alchemy.item;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.Element;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.item.custom.NetItem;
import net.feliscape.alchemy.item.custom.TabletItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Alchemy.MOD_ID);

    public static final RegistryObject<Item> NET = ITEMS.register("net",
            () -> new NetItem(new Item.Properties().durability(32)));
    public static final RegistryObject<Item> CLOUD_FLUFF = ITEMS.register("cloud_fluff",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CINTER = ITEMS.register("cinter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FRENZIED_CINTER = ITEMS.register("frenzied_cinter",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FULMINATING_CINTER = ITEMS.register("fulminating_cinter",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLANK_TABLET = ITEMS.register("blank_tablet",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FIRE_TABLET = ITEMS.register("fire_tablet",
            () -> new TabletItem(new Item.Properties(), Element.FIRE));
    public static final RegistryObject<Item> WATER_TABLET = ITEMS.register("water_tablet",
            () -> new TabletItem(new Item.Properties(), Element.WATER));
    public static final RegistryObject<Item> NATURE_TABLET = ITEMS.register("nature_tablet",
            () -> new TabletItem(new Item.Properties(), Element.NATURE));
    public static final RegistryObject<Item> ENCHANTMENT_TABLET = ITEMS.register("enchantment_tablet",
            () -> new TabletItem(new Item.Properties(), Element.ENCHANTMENT));
    public static final RegistryObject<Item> SPIRIT_TABLET = ITEMS.register("spirit_tablet",
            () -> new TabletItem(new Item.Properties(), Element.SPIRIT));

    public static final RegistryObject<Item> ASPEN_SIGN = ITEMS.register("aspen_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.ASPEN_SIGN.get(), ModBlocks.ASPEN_WALL_SIGN.get()));
    public static final RegistryObject<Item> ASPEN_HANGING_SIGN = ITEMS.register("aspen_hanging_sign",
            () -> new HangingSignItem(ModBlocks.ASPEN_HANGING_SIGN.get(), ModBlocks.ASPEN_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
