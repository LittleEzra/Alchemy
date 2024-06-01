package net.feliscape.alchemy.block.entity;

import net.feliscape.alchemy.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EchoBlockEntity extends BlockEntity {
    public int tickCount;
    private int particleTimer;

    public EchoBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ECHO.get(), pPos, pBlockState);
    }

    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, EchoBlockEntity pBlockEntity) {
        ++pBlockEntity.tickCount;
        ++pBlockEntity.particleTimer;

        if (pBlockEntity.particleTimer >= 20){
            pBlockEntity.particleTimer = 0;
            Vec3 center = pPos.getCenter();
            pLevel.addParticle(ModParticles.ECHO.get(), center.x, center.y, center.z, 0.0D, 0.0D, 0.0D);
        }
    }
}
