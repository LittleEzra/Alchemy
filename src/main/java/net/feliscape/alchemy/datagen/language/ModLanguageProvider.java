package net.feliscape.alchemy.datagen.language;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.advancements.ModAdvancement;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.datagen.advancements.ModAdvancements;
import net.feliscape.alchemy.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

/*
  "item.mod_id.item_id": "Item",
  "block.mod_id.block_id": "Block",
  "creativetab.mod_id.tab_id": "Creative Tab",

  "advancements.mod_id.advancement.title": "Advancement Title",
  "advancements.mod_id.advancement.description": "Advancement Description",

  "death.attack.damage_source": "%1$s died from x",
  "death.attack.damage_source.player": "%1$s died from x whilst fighting %2$s"
 */

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Alchemy.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        ModAdvancements.ENTRIES.forEach(this::addAdvancement);

        this.addItem(ModItems.CLOUD_FLUFF, "Cloud Fluff");
        this.addItem(ModItems.CINTER, "Cinter");
        this.addItem(ModItems.FRENZIED_CINTER, "Frenzied Cinter");
        this.addItem(ModItems.FULMINATING_CINTER, "Fulminating Cinter");

        this.addItem(ModItems.BLANK_TABLET, "Blank Tablet");

        this.addItem(ModItems.FIRE_TABLET, "Fire Tablet");
        this.addItem(ModItems.WATER_TABLET, "Water Tablet");
        this.addItem(ModItems.NATURE_TABLET, "Nature Tablet");
        this.addItem(ModItems.ENCHANTMENT_TABLET, "Enchantment Tablet");
        this.addItem(ModItems.SPIRIT_TABLET, "Spirit Tablet");

        this.addItem(ModItems.NET, "Net");

        this.add(ModItems.ASPEN_SIGN.get(), "Aspen Sign");
        this.add(ModItems.ASPEN_HANGING_SIGN.get(), "Aspen Hanging Sign");
        //this.add(ModItems.ASPEN_BOAT.get(), "Aspen Boat");
        //this.add(ModItems.ASPEN_CHEST_BOAT.get(), "Aspen Chest Boat");

        this.add(ModBlocks.ASPEN_SAPLING.get(), "Aspen Sapling");

        this.addBlock(ModBlocks.ALEMBIC, "Alembic");
        this.addBlock(ModBlocks.ECHO, "Echo");
        this.addBlock(ModBlocks.FLARE, "Flare");

        this.addBlock(ModBlocks.CINTER_BLOCK, "Block of Cinter");
        this.addBlock(ModBlocks.FRENZIED_CINTER_BLOCK, "Block of Frenzied Cinter");
        this.addBlock(ModBlocks.SPIRIT_BLOCK, "Spirit Block");

        this.addBlock(ModBlocks.ASPEN_LEAVES, "Aspen Leaves");
        this.addBlock(ModBlocks.ASPEN_LOG, "Aspen Log");
        this.addBlock(ModBlocks.ASPEN_WOOD, "Aspen Wood");
        this.addBlock(ModBlocks.STRIPPED_ASPEN_LOG, "Stripped Aspen Log");
        this.addBlock(ModBlocks.STRIPPED_ASPEN_WOOD, "Stripped Aspen Wood");
        this.addBlock(ModBlocks.ASPEN_PLANKS, "Aspen Planks");
        this.addBlock(ModBlocks.ASPEN_STAIRS, "Aspen Stairs");
        this.addBlock(ModBlocks.ASPEN_SLAB, "Aspen Slab");
        this.addBlock(ModBlocks.ASPEN_BUTTON, "Aspen Button");
        this.addBlock(ModBlocks.ASPEN_PRESSURE_PLATE, "Aspen Pressure Plate");
        this.addBlock(ModBlocks.ASPEN_FENCE, "Aspen Fence");
        this.addBlock(ModBlocks.ASPEN_FENCE_GATE, "Aspen Fence Gate");
        this.addBlock(ModBlocks.ASPEN_DOOR, "Aspen Door");
        this.addBlock(ModBlocks.ASPEN_TRAPDOOR, "Aspen Trapdoor");


        this.add("creativetab.alchemy.alchemy_tab", "Alchemy");
        this.add("container.alchemy.alembic", "Alembic");
        this.add("item.alchemy.tablet_tooltip.fire", "%1$s Fire");
        this.add("item.alchemy.tablet_tooltip.water", "%1$s Water");
        this.add("item.alchemy.tablet_tooltip.nature", "%1$s Nature");
        this.add("item.alchemy.tablet_tooltip.enchantment", "%1$s Enchantment");
        this.add("item.alchemy.tablet_tooltip.spirit", "%1$s Spirit");

        this.add("notification.alchemy.flare", "A flare has been fired from %1$s!");

        this.add("subtitles.entity.flare.explode", "Flare Exploded");
    }

    private void addAdvancement(ModAdvancement modAdvancement) {
        this.add(modAdvancement.titleKey(), modAdvancement.getTitle());
        this.add(modAdvancement.descriptionKey(), modAdvancement.getDescription());
    }


}
