package net.feliscape.alchemy.networking.packets;

import net.feliscape.alchemy.sound.ModSounds;
import net.feliscape.alchemy.util.RandomUtil;
import net.feliscape.alchemy.util.VectorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlareNotificationS2CPacket {
    private final Vec3 position;

    public FlareNotificationS2CPacket(Vec3 position){
        this.position = position;
    }
    public FlareNotificationS2CPacket(FriendlyByteBuf buf){
        this.position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client Side!
            Minecraft client = Minecraft.getInstance();
            Level level = client.level;

            if (level == null){
                return;
            }
            for (int i = 0; i < 40; i++){
                if (i % 10 == 0){
                    level.addAlwaysVisibleParticle(ParticleTypes.FLASH, true,
                            position.x() + RandomUtil.particleOffset(level.getRandom(), 1.0D),
                            position.y() + RandomUtil.particleOffset(level.getRandom(), 1.0D),
                            position.z() + RandomUtil.particleOffset(level.getRandom(), 1.0D),
                            0.0D, 0.0D, 0.0D);
                }
                level.addAlwaysVisibleParticle(ParticleTypes.FIREWORK, true, position.x(), position.y(), position.z(),
                        RandomUtil.particleOffset(level.getRandom(), 0.5D),
                        RandomUtil.particleOffset(level.getRandom(), 0.5D),
                        RandomUtil.particleOffset(level.getRandom(), 0.5D));
            }
            if (client.player == null){
                return;
            }
            client.player.playNotifySound(ModSounds.FLARE_EXPLODED.get(), SoundSource.MASTER, 1f, 1f);
            client.player.displayClientMessage((Component.translatable("notification.alchemy.flare",
                    VectorUtil.Vec3ToStringXZ(position, true))), true);
        });
        return true;
    }
}
