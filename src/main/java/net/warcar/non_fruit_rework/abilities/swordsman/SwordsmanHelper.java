package net.warcar.non_fruit_rework.abilities.swordsman;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUseResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModI18n;

public class SwordsmanHelper {
    public static int getSwords(LivingEntity player) {
        int swords = 0;
        for (EquipmentSlotType type : new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND, EquipmentSlotType.HEAD}) {
            ItemStack stack = player.getItemBySlot(type);
            if (ItemsHelper.isSword(stack)) {
                stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(type));
                swords++;
            }
        }
        return swords;
    }

    public static boolean canUseNitoruAbilities(LivingEntity player) {
        Ability sparClawAbility = AbilityDataCapability.get(player).getEquippedAbility(SparClawAbility.INSTANCE);
        return getSwords(player) >= 2 || (sparClawAbility != null && sparClawAbility.isContinuous());
    }

    public static boolean canUseNitoruAbilities(LivingEntity player, IAbility ability) {
        return canUseNitoruAbilities(player);
    }

    public static boolean canUseSantoruAbilities(LivingEntity player) {
        Ability sparClawAbility = AbilityDataCapability.get(player).getEquippedAbility(SparClawAbility.INSTANCE);
        return getSwords(player) >= 3 || (sparClawAbility != null && sparClawAbility.isContinuous());
    }

    public static AbilityUseResult canUseSantoruAbilities(LivingEntity player, IAbility ability) {
        if (canUseSantoruAbilities(player)) {
            return AbilityUseResult.success();
        } else {
            return AbilityUseResult.fail(new TranslationTextComponent("ability.item.message.need_three_sword"));
        }
    }
}
