package net.warcar.non_fruit_rework.mixins.swordsman;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.data.entity.mastery.IMasteryData;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.swordsman.YakkodoriProjectile;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;

@Mixin(YakkodoriAbility.class)
public class YakkodoriMixin {
    @Inject(method = "onUseEvent", at = @At("TAIL"), remap = false)
    private void range(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        IMasteryData mastery = MasteryDataCapability.get(entity);
        for (int i = (int) (-mastery.getMastery() * 0.3f); i < (int) (mastery.getMastery() * 0.3f); i++) {
            if (i != 0) {
                YakkodoriProjectile proj = new YakkodoriProjectile(entity.level, entity);
                entity.level.addFreshEntity(proj);
                proj.shootFromRotation(entity, entity.xRot + i, entity.yRot, 0.0F, 2, 0);
                proj.onBlockImpactEvent = pos -> {
                    ExplosionAbility explosion = AbilityHelper.newExplosion(entity, entity.level, pos.getX(), pos.getY(), pos.getZ(), mastery.getMastery() * 0.015f);
                    explosion.setStaticDamage(10.0F);
                    explosion.setExplosionSound(true);
                    explosion.setDamageOwner(false);
                    explosion.setDestroyBlocks(true);
                    explosion.setFireAfterExplosion(false);
                    explosion.setSmokeParticles(new CommonExplosionParticleEffect(2));
                    explosion.setDamageEntities(true);
                    explosion.doExplosion();
                };
            }
        }
    }
}
