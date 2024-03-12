package dupetrigger.init;

import dupetrigger.DupeTrigger;
import dupetrigger.network.DupeTriggerKeybindMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class DupeTriggerKeyMappings {
    public static final KeyMapping DUPE_TRIGGER_KEYBIND = new KeyMapping("Dupe Trigger", GLFW.GLFW_KEY_EQUAL,
            "key.categories.misc") {
        private boolean isDownOld = false;
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player != null && isDownOld != isDown && isDown) {
                DupeTrigger.PACKET_HANDLER.sendToServer(new DupeTriggerKeybindMessage(0, 0));
                DupeTriggerKeybindMessage.pressAction(Minecraft.getInstance().player, 0, 0);
            }
            isDownOld = isDown;
        }
    };
    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(DUPE_TRIGGER_KEYBIND);
    }
    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().screen == null) {
                DUPE_TRIGGER_KEYBIND.consumeClick();
            }
        }
    }
}
