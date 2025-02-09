package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import xyz.pixelatedw.mineminenomi.quests.objectives.ObtainItemObjective;

import java.util.function.Supplier;

public class CustomObtainItemObjective<I extends Item> extends ObtainItemObjective<I> {
    private final int count;
    private final Supplier<I> itemTarget;

    public CustomObtainItemObjective(int count, Supplier<I> itemTarget) {
        super("Collect %s %s", count, itemTarget);
        this.count = count;
        this.itemTarget = itemTarget;
    }

    @Override
    public String getLocalizedTitle() {
        return new TranslationTextComponent("quest.objective.non_fruit_rework.collect_items", this.getItemsNeeded(), new ItemStack(this.itemTarget.get()).getHoverName().getString()).getString();
    }

    public Supplier<I> getItemTarget() {
        return itemTarget;
    }

    public int getItemsNeeded() {
        return count;
    }
}
