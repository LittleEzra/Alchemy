package net.feliscape.alchemy.block.custom;

import net.feliscape.alchemy.block.ModBlocks;
import net.feliscape.alchemy.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CinterBlock extends Block {
    public CinterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(2) == 0){
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

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        pEntity.hurt(pLevel.damageSources().hotFloor(), 2f);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        level.setBlock(pos, ModBlocks.FRENZIED_CINTER_BLOCK.get().defaultBlockState(), 11);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return false;
    }
}
