package net.warcar.non_fruit_rework.abilities.lunarian;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.math.vector.Vector3d;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.UUID;
import java.util.function.Predicate;

public class DisasterFlamesAbility extends Ability {
    public static final AbilityCore<DisasterFlamesAbility> INSTANCE = new AbilityCore.Builder<>("Disaster Flames", AbilityCategory.RACIAL, DisasterFlamesAbility::new).build();
    private static final AbilityAttributeModifier TOUGHNESS_MODIFIER = new AbilityAttributeModifier(UUID.fromString("d7da40d5-a651-48ee-83bd-9a8b0c73150f"), INSTANCE, "Lunarian Flames defence", 10, AttributeModifier.Operation.ADDITION);
    private static final AbilityAttributeModifier ARMOR_MODIFIER = new AbilityAttributeModifier(UUID.fromString("d7da40d5-a651-48ee-83ba-9a8b0c73150f"), INSTANCE, "Lunarian Flames defence", 70, AttributeModifier.Operation.ADDITION);
    private static final AbilityAttributeModifier SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("d5da40d5-a651-481e-83ba-9a8b0f73150f"), INSTANCE, "Lunarian Flames Speed", 4, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AbilityAttributeModifier DAMAGE_MODIFIER = new AbilityAttributeModifier(UUID.fromString("d5da40d5-f451-481e-83ba-9a8b0f43150f"), INSTANCE, "Lunarian Flames Damage", 4, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private final Predicate<LivingEntity> isActive = entity -> this.isContinuous();
    private final Predicate<LivingEntity> isInactive = isActive.negate();

    private final ContinuousComponent continuousComponent = new ContinuousComponent(this);
    private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this).addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isInactive).addAttributeModifier(Attributes.ARMOR, ARMOR_MODIFIER, isInactive).addAttributeModifier(Attributes.MOVEMENT_SPEED, SPEED_MODIFIER, isActive).addAttributeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER);

    public DisasterFlamesAbility(AbilityCore<DisasterFlamesAbility> core) {
        super(core);
        this.isNew = true;
        this.addComponents(continuousComponent, statsComponent);
        this.addTickEvent(this::tick);
        this.addUseEvent(this::onUse);
    }

    private void tick(LivingEntity entity, Ability ability) {
        if (!this.isContinuous()) {
            Vector3d pos = entity.position().subtract(entity.getLookAngle());
            WyHelper.spawnParticleEffect(ModParticleEffects.MERA_LOGIA.get(), entity, pos.x(), pos.y(), pos.z());
            WyHelper.spawnParticleEffect(ModParticleEffects.MERA_LOGIA.get(), entity, pos.x(), pos.y() + 0.5, pos.z());
        }
    }

    private void onUse(LivingEntity entity, IAbility ability) {
        this.continuousComponent.triggerContinuity(entity);
    }
}
