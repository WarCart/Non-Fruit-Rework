package net.warcar.non_fruit_rework.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;

import javax.annotation.Nullable;
import java.util.List;

public class EnergySteroidBatchItem extends AbstractPillItem {
    public EnergySteroidBatchItem() {
        super(new Properties().tab(ItemGroup.TAB_BREWING));
    }

    @Override
    protected boolean pillEffect(LivingEntity entity, ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        int pills;
        if (tag == null) {
            pills = 0;
        } else {
            pills = tag.getInt("pills");
        }
        NonFruitDataCapability.get(entity).popEnergySteroids(pills);
        return true;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {
            ItemStack stack5 = new ItemStack(this);
            stack5.setTag(getCapNBT(5));
            stacks.add(stack5);
            ItemStack stack10 = new ItemStack(this);
            stack10.setTag(getCapNBT(10));
            stacks.add(stack10);
            ItemStack stack50 = new ItemStack(this);
            stack50.setTag(getCapNBT(50));
            stacks.add(stack50);
        }
    }

    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        CompoundNBT tag = stack.getTag();
        int pills;
        if (tag == null) {
            pills = 0;
        } else {
            pills = tag.getInt("pills");
        }
        list.add(new StringTextComponent("" + pills));
    }

    private static CompoundNBT getCapNBT(int pills) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("pills", pills);
        return nbt;
    }
}
