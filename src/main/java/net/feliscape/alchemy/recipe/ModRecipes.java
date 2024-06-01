package net.feliscape.alchemy.recipe;

import net.feliscape.alchemy.Alchemy;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Alchemy.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DistillationRecipe>> DISTILLATION =
            SERIALIZERS.register("distillation", () -> DistillationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
