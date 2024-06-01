package net.feliscape.alchemy.networking;

import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.networking.packets.FlareNotificationS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register(){

        /* Registering a Packet:
        net.messageBuilder(PACKET.class, id(), NetworkDirection.)
                .decoder(PACKET::new)
                .encoder(PACKET::toBytes)
                .consumerMainThread(PACKET::handle)
                .add();*/

        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Alchemy.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        net.messageBuilder(FlareNotificationS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FlareNotificationS2CPacket::new)
                .encoder(FlareNotificationS2CPacket::toBytes)
                .consumerMainThread(FlareNotificationS2CPacket::handle)
                .add();

        INSTANCE = net;
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToAllClients(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
