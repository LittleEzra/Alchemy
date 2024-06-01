package net.feliscape.alchemy.entity.custom;

import net.feliscape.alchemy.entity.ModEntityTypes;
import net.feliscape.alchemy.networking.ModMessages;
import net.feliscape.alchemy.networking.packets.FlareNotificationS2CPacket;
import net.feliscape.alchemy.particle.ModParticles;
import net.feliscape.alchemy.util.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PrimedFlare extends Entity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedFlare.class, EntityDataSerializers.INT);
    private static final int DEFAULT_FUSE_TIME = 60;
    @Nullable
    private LivingEntity owner;
    private Vec3 spread = Vec3.ZERO;

    public PrimedFlare(EntityType<? extends PrimedFlare> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public PrimedFlare(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(ModEntityTypes.FLARE.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(DEFAULT_FUSE_TIME);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
        this.spread = new Vec3(
                random.nextDouble() * 0.04D * (random.nextBoolean() ? 1 : -1),
                0D,
                random.nextDouble() * 0.04D * (random.nextBoolean() ? 1 : -1));
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return !this.isRemoved();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        this.setDeltaMovement(spread.add(0.0D, 1.5D, 0.0D));

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        /*if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }*/

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            if (!this.level().isClientSide){
                explode(true);
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                makeParticles();
            }
        }

    }
    public void makeParticles() {
        this.level().addAlwaysVisibleParticle(ModParticles.FLARE_SMOKE.get(), true,
                position().x() + this.random.nextDouble() / 3.0D * (double)(this.random.nextBoolean() ? 1 : -1),
                position().y() + this.random.nextDouble() + this.random.nextDouble(),
                position().z() + this.random.nextDouble() / 3.0D * (double)(this.random.nextBoolean() ? 1 : -1),
                0.0D, 0.0D, 0.0D);
        this.level().addAlwaysVisibleParticle(ModParticles.FLARE_SPARK.get(), true,
                position().x(), position().y(), position().z(),
                RandomUtil.particleOffset(this.random, 1.0D),
                this.random.nextDouble() * 2.0D,
                RandomUtil.particleOffset(this.random, 1.0D));
    }

    protected void explode(boolean sendMessage) {
        this.discard();

        ModMessages.sendToAllClients(new FlareNotificationS2CPacket(this.position()));
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short)this.getFuse());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.15F;
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }
}
