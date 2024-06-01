package net.feliscape.alchemy.block;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.block.custom.*;
import net.feliscape.alchemy.item.ModItems;
import net.feliscape.alchemy.util.ModWoodTypes;
import net.feliscape.alchemy.worldgen.tree.AspenTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Alchemy.MOD_ID);

    public static final RegistryObject<Block> ALEMBIC = registerBlock("alembic",
            () -> new AlembicBlock(BlockBehaviour.Properties.copy(Blocks.FURNACE).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> ECHO = registerBlock("echo",
            () -> new EchoBlock(BlockBehaviour.Properties.of().noCollission().noOcclusion().noParticlesOnBreak().instabreak().sound(SoundType.GLASS)));
    public static final RegistryObject<Block> FLARE = registerBlock("flare",
            () -> new FlareBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().strength(0.5F).sound(SoundType.GRASS).mapColor(MapColor.COLOR_YELLOW)));

    public static final RegistryObject<Block> CINTER_BLOCK = registerBlock("cinter_block",
            () -> new CinterBlock(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1.0F, 12.0F)));
    public static final RegistryObject<Block> FRENZIED_CINTER_BLOCK = registerBlock("frenzied_cinter_block",
            () -> new FrenziedCinterBlock(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1.0F, 3600.0F)));

    public static final RegistryObject<Block> SPIRIT_BLOCK = registerBlock("spirit_block",
            () -> new SpiritBlock(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1.0F, 0.0F).isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never)
                    .isViewBlocking(ModBlocks::never).noOcclusion()));

    //region Aspen Wood

    public static final RegistryObject<Block> ASPEN_LEAVES = registerBlock("aspen_leaves",
            () -> leaves(SoundType.GRASS));

    public static final RegistryObject<Block> ASPEN_LOG = registerBlock("aspen_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(4f)));
    public static final RegistryObject<Block> ASPEN_WOOD = registerBlock("aspen_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(4f)));
    public static final RegistryObject<Block> STRIPPED_ASPEN_LOG = registerBlock("stripped_aspen_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(4f)));
    public static final RegistryObject<Block> STRIPPED_ASPEN_WOOD = registerBlock("stripped_aspen_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f)));

    public static final RegistryObject<Block> ASPEN_PLANKS = registerBlock("aspen_planks",
            () -> simpleFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA), 20, 5));

    public static final RegistryObject<Block> ASPEN_SIGN = BLOCKS.register("aspen_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(ASPEN_PLANKS.get().defaultMapColor()), ModWoodTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_WALL_SIGN = BLOCKS.register("aspen_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).mapColor(ASPEN_PLANKS.get().defaultMapColor()), ModWoodTypes.ASPEN));

    public static final RegistryObject<Block> ASPEN_HANGING_SIGN = BLOCKS.register("aspen_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).mapColor(ASPEN_PLANKS.get().defaultMapColor()), ModWoodTypes.ASPEN));
    public static final RegistryObject<Block> ASPEN_WALL_HANGING_SIGN = BLOCKS.register("aspen_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(ASPEN_PLANKS.get().defaultMapColor()), ModWoodTypes.ASPEN));

    public static final RegistryObject<Block> ASPEN_STAIRS = registerBlock("aspen_stairs",
            () -> flammableStairBlock(ASPEN_PLANKS.get(), BlockBehaviour.Properties.copy(ASPEN_PLANKS.get()), 5, 20));
    public static final RegistryObject<Block> ASPEN_SLAB = registerBlock("aspen_slab",
            () -> flammableSlabBlock(BlockBehaviour.Properties.of().mapColor(ASPEN_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava(), 5, 20));

    public static final RegistryObject<Block> ASPEN_BUTTON = registerBlock("aspen_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY),
                    BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> ASPEN_PRESSURE_PLATE = registerBlock("aspen_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.of().mapColor(ASPEN_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission()
                            .strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY) , BlockSetType.OAK));

    public static final RegistryObject<Block> ASPEN_FENCE = registerBlock("aspen_fence",
            () -> flammableFenceBlock(BlockBehaviour.Properties.of().mapColor(ASPEN_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD), 5, 20));
    public static final RegistryObject<Block> ASPEN_FENCE_GATE = registerBlock("aspen_fence_gate",
            () -> flammableFenceGateBlock(BlockBehaviour.Properties.of().mapColor(ASPEN_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).ignitedByLava(), 5, 20));

    public static final RegistryObject<Block> ASPEN_DOOR = registerBlock("aspen_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(ASPEN_PLANKS.get()).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ASPEN_TRAPDOOR = registerBlock("aspen_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(ASPEN_PLANKS.get()).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> ASPEN_SAPLING = registerBlock("aspen_sapling",
            () -> new SaplingBlock(new AspenTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    //endregion

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    //region HELPER FUNCTIONS
    private static LeavesBlock leaves(SoundType p_152615_) {
        return new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(p_152615_).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never));
    }

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }
    private static Boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return (boolean)true;
    }
    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return (boolean)(p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT);
    }

    private static Block simpleFlammableBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new Block(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static SlabBlock flammableSlabBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new SlabBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static StairBlock flammableStairBlock(Block base, BlockBehaviour.Properties properties, int flammability, int firespread){
        return new StairBlock(base::defaultBlockState, properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceBlock flammableFenceBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceGateBlock flammableFenceGateBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceGateBlock(properties, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceGateBlock flammableFenceGateBlock(BlockBehaviour.Properties properties, WoodType woodType, int flammability, int firespread){
        return new FenceGateBlock(properties, woodType){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    //endregion

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }
}
