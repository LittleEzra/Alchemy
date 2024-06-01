package net.feliscape.alchemy.particle.custom;

import net.feliscape.alchemy.particle.client.ModParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class EchoParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected EchoParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ);
        this.setParticleSpeed(pXSpeed * 0.2D, pYSpeed * 0.2D, pZSpeed * 0.2D);
        this.lifetime = 25;
        this.sprites = pSprites;
        this.scale(0.125f);
        this.setSpriteFromAge(pSprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && this.age > 0) {
            //this.setSpriteFromAge(this.sprites);
            this.scale(1f + 1f / this.age);
            if (age > 15){
                this.alpha -= 0.1f;
            }
        } else {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ModParticleRenderTypes.PARTICLE_SHEET_ALWAYS_VISIBLE;
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
            return new EchoParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
        }
    }
}
