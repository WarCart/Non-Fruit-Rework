package net.warcar.non_fruit_rework.abilities.raid_suit.poison_pink;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.function.Predicate;

public class DetoxicatingKissAbility extends PunchAbility2 {
    public static final AbilityCore<DetoxicatingKissAbility> INSTANCE;

    public DetoxicatingKissAbility(AbilityCore core) {
        super(core);
        this.hitTriggerComponent.addOnHitEvent(this::onHitEvent);
        this.setMaxCooldown(10);
    }

    private void onHitEvent(LivingEntity entity, LivingEntity target, ModDamageSource source, IAbility abl) {
        Effect[] effects = new Effect[]{Effects.POISON, Effects.WITHER, ModEffects.DRUNK.get()};
        for (Effect effect : effects) {
            if (target.hasEffect(effect)) {
                int amp = target.getEffect(effect).getAmplifier();
                if (entity instanceof PlayerEntity) ((PlayerEntity) entity).getFoodData().eat(amp, (float) amp / 10);
                target.removeEffect(effect);
            }
        }
        target.removeEffect(ModEffects.DOKU_POISON.get());
    }

    private static boolean canUnlock(LivingEntity entity) {
        /*for (EquipmentSlotType slotType : new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET}) {
            if (entity.getItemBySlot(slotType).getItem() == ((RaidSuitCapsule) ModRaidSuits.POISON_PINK_CAPSULE.get()).armor.get(slotType)) {
                return true;
            }
        }*/
        return false;
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Detoxicating Kiss", AbilityCategory.EQUIPMENT, DetoxicatingKissAbility::new).setUnlockCheck(DetoxicatingKissAbility::canUnlock)
                .addDescriptionLine("").build();
    }

    public float getPunchCooldown() {
        return 0;
    }

    public void onHitEffect(LivingEntity livingEntity, LivingEntity livingEntity1, ModDamageSource modDamageSource) {
    }

    public Predicate<LivingEntity> canActivate() {
        return entity -> this.continuousComponent.isContinuous();
    }

    public int getUseLimit() {
        return 1;
    }
}
