package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.entities.projectiles.rankyaku.AmaneDachiProjectile;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.AmaneDachiQuest;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;

public class AmaneDachiAbility extends Ability {
    public static final AbilityCore<AmaneDachiAbility> INSTANCE = new AbilityCore.Builder<>("Rankyaku: Amane Dachi", AbilityCategory.RACIAL, AmaneDachiAbility::new)
            .setUnlockCheck(QuestHelper.questFinished(AmaneDachiQuest.INSTANCE)).build();

    private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
    private final AnimationComponent animationComponent = new AnimationComponent(this);
    private final ChargeComponent chargeComponent = new ChargeComponent(this).addStartEvent(this::startCharging).addEndEvent(this::onCharged);

    public AmaneDachiAbility(AbilityCore<AmaneDachiAbility> core) {
        super(core);
        this.isNew = true;
        this.addUseEvent(this::onUseEvent);
        this.addComponents(projectileComponent, animationComponent, chargeComponent);

    }

    private void onUseEvent(LivingEntity entity, IAbility iAbility) {
        this.chargeComponent.startCharging(entity, 120);
    }

    private void onCharged(LivingEntity entity, IAbility ability) {
        this.projectileComponent.shoot(entity, 4.3f, 1.0F);
        this.cooldownComponent.startCooldown(entity, 700);
        this.animationComponent.stop(entity);
    }

    private AmaneDachiProjectile createProjectile(LivingEntity entity) {
        return new AmaneDachiProjectile(entity.level, entity);
    }

    private void startCharging(LivingEntity livingEntity, IAbility iAbility) {
        this.animationComponent.start(livingEntity, ModAnimations.HAND_STAND_SPIN);
    }
}
