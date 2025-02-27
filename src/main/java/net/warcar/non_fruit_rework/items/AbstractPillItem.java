package net.warcar.non_fruit_rework.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class AbstractPillItem extends Item {
    public AbstractPillItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand).copy();
        if (this.pillEffect(player, item)) {
            player.getItemInHand(hand).shrink(1);
            player.getCooldowns().addCooldown(item.getItem(), 20);
            return ActionResult.consume(player.getItemInHand(hand));
        } else {
            return ActionResult.fail(item);
        }
    }

    protected abstract boolean pillEffect(LivingEntity entity, ItemStack stack);
}
