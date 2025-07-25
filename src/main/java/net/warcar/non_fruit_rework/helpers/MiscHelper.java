package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.abilities.human.BerserkModeAbility;
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.entities.AfterimageEntity;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;

public final class MiscHelper {
    private MiscHelper() {} //Don't initialize

    public static boolean isBerserk(LivingEntity entity) {
        BerserkModeAbility ability = AbilityDataCapability.get(entity).getEquippedAbility(BerserkModeAbility.INSTANCE);
        if (ability == null) {
            return false;
        }
        return ability.isContinuous();
    }

    public static AfterimageEntity spawnAfterimage(LivingEntity entity) {
        if (entity instanceof PlayerEntity && CommonConfig.INSTANCE.spawnAfterimages()) {
            AfterimageEntity afterimage = new AfterimageEntity(entity.level, 10);
            afterimage.setPlayer((PlayerEntity) entity);
            afterimage.setPos(entity.getX(), entity.getY(), entity.getZ());
            entity.level.addFreshEntity(afterimage);
            return afterimage;
        }
        return null;
    }
}
