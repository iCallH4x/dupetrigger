package dupetrigger.network;

import dupetrigger.DupeTrigger;
import dupetrigger.procedures.DupeTriggerMain;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DupeTriggerKeybindMessage {
    int type, pressedms;
    public DupeTriggerKeybindMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }
    public DupeTriggerKeybindMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }
    public static void buffer(DupeTriggerKeybindMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }
    public static void handler(DupeTriggerKeybindMessage message, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            if (context.getSender() != null) {
                pressAction(context.getSender(), message.type, message.pressedms);
            }
        });
        context.setPacketHandled(true);
    }
    public static void pressAction(Player entity, int type, int pressedms) {
        Level world = entity.level();
        BlockPos pos = entity.blockPosition();
        int x = pos.getX();
        int z = pos.getZ();

// Check if the player's current block position has a chunk associated with it
        if (world.hasChunk(x >> 4, z >> 4)) {
            if (type == 0) {
                DupeTriggerMain.execute(entity);
            }
        }

    }
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        DupeTrigger.addNetworkMessage(DupeTriggerKeybindMessage.class, DupeTriggerKeybindMessage::buffer, DupeTriggerKeybindMessage::new,
                DupeTriggerKeybindMessage::handler);
    }
}

