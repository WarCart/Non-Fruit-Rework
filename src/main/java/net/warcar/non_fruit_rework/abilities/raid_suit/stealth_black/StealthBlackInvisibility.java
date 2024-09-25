package net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.warcar.non_fruit_rework.init.ModRaidSuits;
import net.warcar.non_fruit_rework.items.RaidSuitCapsule;
import xyz.pixelatedw.mineminenomi.abilities.suke.SkattingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class StealthBlackInvisibility extends Ability {
    public static final AbilityCore<StealthBlackInvisibility> INSTANCE;
    private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);

    public StealthBlackInvisibility(AbilityCore<StealthBlackInvisibility> core) {
        super(core);
        this.isNew = true;
        this.addComponents(this.continuousComponent);
        this.addUseEvent(this::useEvent);
    }

    private void useEvent(LivingEntity entity, IAbility ability) {
        this.continuousComponent.triggerContinuity(entity);
    }

    private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
        if (!entity.hasEffect(ModEffects.SUKE_INVISIBILITY.get())) {
            entity.addEffect(new EffectInstance(ModEffects.SUKE_INVISIBILITY.get(), 2147483647, 0, false, false, true));
        }

    }

    private void endContinuityEvent(LivingEntity entity, IAbility ability) {
        entity.removeEffect(ModEffects.SUKE_INVISIBILITY.get());
    }

    private static boolean canUnlock(LivingEntity entity) {
        for (EquipmentSlotType slotType : new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET}) {
            if (entity.getItemBySlot(slotType).getItem() == ((RaidSuitCapsule) ModRaidSuits.STEALTH_BLACK_CAPSULE.get()).armor.get(slotType)) {
                return true;
            }
        }
        return false;
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Stealth Black Invisibility", AbilityCategory.EQUIPMENT, StealthBlackInvisibility::new)
                .setUnlockCheck(StealthBlackInvisibility::canUnlock).addDescriptionLine("Turns the user's entire body invisible").build();
    }

    public void onDamageTaken(LivingEntity livingEntity, double amount) {
        if (this.isContinuous()) {
            this.continuousComponent.stopContinuity(livingEntity);
            this.cooldownComponent.startCooldown(livingEntity, (float) Math.sqrt(amount) * 20);
        }
    }
}
