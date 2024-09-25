package net.warcar.non_fruit_rework.abilities.rokushiki;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.quests.QuestProgression;
import net.warcar.non_fruit_rework.quests.rokushiki.advanced.RankyakuRanQuest;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility2;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.rokushiki.RankyakuProjectile;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;

public class RankyakuRanAbility extends RepeaterAbility2 {
    public static final AbilityCore<RankyakuRanAbility> INSTANCE = new AbilityCore.Builder<>("Rankyaku Ran", AbilityCategory.RACIAL, RankyakuRanAbility::new)
            .setSourceType(SourceType.SLASH, SourceType.PROJECTILE).setSourceHakiNature(SourceHakiNature.IMBUING)
            .setUnlockCheck(QuestProgression.hasFinishedQuest(RankyakuRanQuest.INSTANCE)).build();

    public RankyakuRanAbility(AbilityCore<RankyakuRanAbility> core) {
        super(core);
        this.setCustomShootLogic(entity -> {
            for (int i = 0; i < 5; i++) {
                this.projectileComponent.shootWithSpread(entity, 2, 3.0F, 1);
            }
        });
    }

    public int getMaxTriggers() {
        return 4;
    }

    public int getTriggerInterval() {
        return 3;
    }

    public float getRepeaterCooldown() {
        return 500;
    }

    public RankyakuProjectile getProjectileFactory(LivingEntity livingEntity) {
        RankyakuProjectile projectile = new RankyakuProjectile(livingEntity.level, livingEntity);
        projectile.onBlockImpactEvent = blockPos -> {
            ExplosionAbility explosion = AbilityHelper.newExplosion(livingEntity, livingEntity.level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.5F);
            explosion.setStaticDamage(5.0F);
            explosion.setSmokeParticles(new CommonExplosionParticleEffect(3));
            explosion.doExplosion();
        };
        projectile.setDamage(20);
        return projectile;
    }
}
