package net.warcar.non_fruit_rework.abilities.fishman.advanced;

import net.minecraft.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanKarateHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.*;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

public class SosharkAbility extends Ability {
    public static final AbilityCore<SosharkAbility> INSTANCE = new AbilityCore.Builder<>("Soshark", AbilityCategory.RACIAL, SosharkAbility::new)
            .setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build();

    private static final float DAMAGE = 40;
    private static final float WATER_DAMAGE = 70;

    private final PoolComponent poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY);
    private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
    private final ChargeComponent chargeComponent = new ChargeComponent(this).addEndEvent(this::endCharging);
    private final GrabEntityComponent grabComponent = new GrabEntityComponent(this, true, true, true, 1);
    private final ContinuousComponent continuousComponent = new ContinuousComponent(this).addTickEvent(this::duringContinuous).addEndEvent(this::endContinuous);
    public SosharkAbility(AbilityCore<SosharkAbility> core) {
        super(core);
        this.isNew = true;
        this.addComponents(this.poolComponent, this.dealDamageComponent, this.chargeComponent, this.grabComponent, this.continuousComponent);
        this.addCanUseCheck(AbilityHelper::canUseBrawlerAbilities);
        this.addUseEvent(this::onUse);
    }

    private void onUse(LivingEntity entity, IAbility iAbility) {
        if (!this.chargeComponent.isCharging() && !this.isContinuous()) {
            if (grabComponent.grabNearest(entity)) {
                this.chargeComponent.startCharging(entity, 30);
            }
        } else if (this.isContinuous()) {
            this.continuousComponent.stopContinuity(entity);
        }
    }

    private void endCharging(LivingEntity entity, IAbility iAbility) {
        if (this.grabComponent.hasGrabbedEntity()) {
            LivingEntity grabbed = this.grabComponent.getGrabbedEntity();
            ModDamageSource source = ((ModDamageSource) this.dealDamageComponent.getDamageSource(entity)).setPiercing(0.25F);
            if (FishmanKarateHelper.isInWater(entity)) {
                this.dealDamageComponent.hurtTarget(entity, grabbed, WATER_DAMAGE, source);
            } else {
                this.dealDamageComponent.hurtTarget(entity, grabbed, DAMAGE, source);
            }
            this.continuousComponent.startContinuity(entity, 50);
        }
    }

    private void endContinuous(LivingEntity entity, IAbility iAbility) {
        this.cooldownComponent.startCooldown(entity, 550 + this.continuousComponent.getContinueTime());
        this.grabComponent.release(entity);
    }

    private void duringContinuous(LivingEntity entity, IAbility iAbility) {
        if (!this.grabComponent.hasGrabbedEntity() && !entity.level.isClientSide()) {
            this.continuousComponent.stopContinuity(entity);
        }
    }
}
