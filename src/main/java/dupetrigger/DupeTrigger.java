package dupetrigger;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Mod("dupetrigger")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DupeTrigger {
    public static final String MODID = "dupetrigger";
    private static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel PACKET_HANDLER = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(MODID, MODID))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .simpleChannel();

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder,
                                             Function<FriendlyByteBuf, T> decoder,
                                             BiConsumer<T, CustomPayloadEvent.Context> messageConsumer) {
        PACKET_HANDLER.messageBuilder(messageType)
                .encoder(encoder)
                .decoder(decoder)
                .consumerNetworkThread(messageConsumer)
                .add();
    }

    @SubscribeEvent
    public static void onModConstruction(final FMLCommonSetupEvent event) {
        System.out.println("Dupe Trigger is ready.");
    }
}
