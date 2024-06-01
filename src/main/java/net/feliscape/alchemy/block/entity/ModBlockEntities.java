package net.feliscape.alchemy.block.entity;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Alchemy.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlembicBlockEntity>> ALEMBIC_BE =
            BLOCK_ENTITIES.register("alembic_be", () -> BlockEntityType.Builder.of(AlembicBlockEntity::new,
                    ModBlocks.ALEMBIC.get()).build(null));
    public static final RegistryObject<BlockEntityType<EchoBlockEntity>> ECHO =
            BLOCK_ENTITIES.register("echo", () -> BlockEntityType.Builder.of(EchoBlockEntity::new,
                    ModBlocks.ECHO.get()).build(null));

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITIES.register("mod_sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new,
                    ModBlocks.ASPEN_SIGN.get(), ModBlocks.ASPEN_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN =
            BLOCK_ENTITIES.register("mod_hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                    ModBlocks.ASPEN_HANGING_SIGN.get(), ModBlocks.ASPEN_WALL_HANGING_SIGN.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
