package net.feliscape.alchemy.particle.custom;

import net.feliscape.alchemy.particle.client.ModParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class CinterSparkParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private float rotSpeed;

    protected CinterSparkParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites, int baseLifetime) {
        super(pLevel, pX, pY, pZ);
        this.setParticleSpeed(pXSpeed * 0.2D, pYSpeed * 0.2D, pZSpeed * 0.2D);
        this.lifetime = baseLifetime + this.random.nextInt(5);
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -120.0D : 120.0D);
        this.sprites = pSprites;
        this.gravity = 2E-2F;
        this.setSpriteFromAge(pSprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime) {
            this.setSpriteFromAge(sprites);
            this.yd -= (double)this.gravity;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
        } else {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CinterSparkParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites, 10);
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static class PersistentProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public PersistentProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CinterSparkParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites, 30);
        }
    }
}
