package net.feliscape.alchemy.block.custom;

import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.damagesource.ModDamageSources;
import net.feliscape.alchemy.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FrenziedCinterBlock extends Block {
    public FrenziedCinterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        int count = pRandom.nextIntBetweenInclusive(2, 3);
        for (int i = 0; i < count; i++){
            Direction direction = Direction.getRandom(pRandom);
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (!pState.canOcclude() || !blockstate.isFaceSturdy(pLevel, blockpos, direction.getOpposite())) {
                double xOffset = direction.getStepX() == 0 ? pRandom.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
                double yOffset = direction.getStepY() == 0 ? pRandom.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
                double zOffset = direction.getStepZ() == 0 ? pRandom.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
                double xSpeed = direction.getStepX() == 0 ? (pRandom.nextDouble() - 0.5D) * 0.1D : pRandom.nextDouble() * 0.1D * direction.getStepX();
                double ySpeed = direction.getStepY() == 0 ? pRandom.nextDouble() * 0.05D : pRandom.nextDouble() * 0.1D * direction.getStepY();
                double zSpeed = direction.getStepZ() == 0 ? (pRandom.nextDouble() - 0.5D) * 0.1D : pRandom.nextDouble() * 0.1D * direction.getStepZ();
                pLevel.addParticle(ModParticles.CINTER_SPARK.get(),
                        (double)pPos.getX() + xOffset, (double)pPos.getY() + yOffset, (double)pPos.getZ() + zOffset,
                        xSpeed, ySpeed, zSpeed);
            }
        }
    }

    private static boolean touchesLiquid(BlockGetter pLevel, BlockPos pPos, BlockState state) {
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for(Direction direction : Direction.values()) {
            BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
            if (direction != Direction.DOWN || state.canBeHydrated(pLevel, pPos, blockstate.getFluidState(), blockpos$mutableblockpos)) {
                blockpos$mutableblockpos.setWithOffset(pPos, direction);
                blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
                if (state.canBeHydrated(pLevel, pPos, blockstate.getFluidState(), blockpos$mutableblockpos) && !blockstate.isFaceSturdy(pLevel, pPos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return touchesLiquid(pLevel, pPos, pState) ? ModBlocks.CINTER_BLOCK.get().defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity.tickCount % 3 == 0){
            pEntity.hurt(ModDamageSources.frenziedCinter(pLevel.registryAccess()), 1f);
        }
    }
}
