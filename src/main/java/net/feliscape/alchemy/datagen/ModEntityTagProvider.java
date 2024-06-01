package net.feliscape.alchemy.datagen;

import net.feliscape.alchemy.Alchemy;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends EntityTypeTagsProvider {
    public ModEntityTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, Alchemy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}
