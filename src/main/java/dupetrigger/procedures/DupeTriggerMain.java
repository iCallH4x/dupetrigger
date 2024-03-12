package dupetrigger.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DupeTriggerMain {
    public static void execute(Entity entity) {
        if (entity == null) {
            return;
        }
        ItemStack mainHandItem = entity instanceof LivingEntity ? ((LivingEntity) entity).getMainHandItem() : ItemStack.EMPTY;
        if (!mainHandItem.isEmpty()) {
            mainHandItem.setCount(mainHandItem.getCount() * 2);
        }
    }
}