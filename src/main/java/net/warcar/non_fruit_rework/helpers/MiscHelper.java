package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.abilities.human.BerserkModeAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;

public final class MiscHelper {
    private MiscHelper() {}

    public static boolean isBerserk(LivingEntity entity) {
        BerserkModeAbility ability = AbilityDataCapability.get(entity).getEquippedAbility(BerserkModeAbility.INSTANCE);
        if (ability == null) {
            return false;
        }
        return ability.isContinuous();
    }
}
