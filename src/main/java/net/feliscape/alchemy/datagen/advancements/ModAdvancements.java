package net.feliscape.alchemy.datagen.advancements;

import net.feliscape.alchemy.advancements.ModAdvancement;
import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ModAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    public static final List<ModAdvancement> ENTRIES = new ArrayList<>();

    public static final ModAdvancement START = null,

        ROOT = create("root", b -> b.icon(ModItems.BLANK_TABLET.get())
                .title("Alchemy")
                .description("Reviving an ancient art")
                .whenGetIcon()
                .special(ModAdvancement.TaskType.SILENT)),

        ALEMBIC = create("alembic", b -> b.icon(ModBlocks.ALEMBIC.get())
                .title("Distillery")
                .description("Craft an Alembic to distill alchemical charges")
                .whenGetIcon()
                .after(ROOT)),

        CLOUD_FLUFF = create("cloud_fluff", b -> b.icon(ModItems.CLOUD_FLUFF.get())
                .title("Net in the Clouds")
                .description("Collect Cloud Fluff by using a Net high enough in the sky")
                .whenGetIcon()
                .after(ROOT)),
        CINTER = create("cinter", b -> b.icon(ModItems.CINTER.get())
                .title("Immaculate Flame")
                .description("Discover Cinter, a crystal of pure fire")
                .whenGetIcon()
                .after(ROOT)),
        FLARE = create("flare", b -> b.icon(ModBlocks.FLARE.get())
                .title("SOS")
                .description("Light a Flare to notify others about your position")
                .when(ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location()
                        .setBlock(blockPredicate(ModBlocks.FLARE.get())), anyItemPredicateBuilder()))
                .after(ROOT)),

        ECHO = create("echo", b -> b.icon(ModBlocks.ECHO.get())
                .title("From Times Long Gone")
                .description("Find a Golden Echo in the Ancient Forest")
                .whenGetIcon()
                .after(ROOT)),

            END = null;

    private static BlockPredicate blockPredicate(Block block){
        return BlockPredicate.Builder.block().of(ModBlocks.FLARE.get()).build();
    }
    private static ItemPredicate.Builder anyItemPredicateBuilder(){
        return ItemPredicate.Builder.item().of();
    }

    private static ModAdvancement create(String id, UnaryOperator<ModAdvancement.Builder> b) {
        return new ModAdvancement(id, b);
    }
    private static ModAdvancement create(String subfolder, String id, UnaryOperator<ModAdvancement.Builder> b) {
        return new ModAdvancement(subfolder, id, b);
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> pWriter, ExistingFileHelper existingFileHelper) {
        for (ModAdvancement advancement : ENTRIES)
            advancement.save(pWriter);
    }
}
