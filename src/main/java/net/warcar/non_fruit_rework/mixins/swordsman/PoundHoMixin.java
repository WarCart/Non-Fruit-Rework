package net.warcar.non_fruit_rework.mixins.swordsman;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.warcar.non_fruit_rework.abilities.swordsman.SwordsmanHelper;
import net.warcar.non_fruit_rework.data.entity.mastery.IMasteryData;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.swordsman.SanbyakurokujuPoundHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;

@Mixin(SanbyakurokujuPoundHoAbility.class)
public class PoundHoMixin {
    @Shadow @Final private ProjectileComponent projectileComponent;

    @Inject(method = "createProjectile", at = @At("TAIL"), remap = false)
    private void reuse(LivingEntity entity, CallbackInfoReturnable<SanbyakurokujuPoundHoProjectile> cir) {
        if (SwordsmanHelper.getSwords(entity) == 3) {
            cir.getReturnValue().setDamage(100);
        } else if (SwordsmanHelper.getSwords(entity) == 2) {
            cir.getReturnValue().setDamage(60);
        }
    }

    @Inject(method = "onUseEvent", at = @At("TAIL"), remap = false)
    private void range(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        this.projectileComponent.getShotProjectile().shootFromRotation(entity, entity.xRot, entity.yRot, 0, 2, 0);
        IMasteryData mastery = MasteryDataCapability.get(entity);
        this.projectileComponent.getShotProjectile().onBlockImpactEvent = hit -> this.explodeWithSize(hit, entity, mastery.getMastery() * 0.03f);
        for (int i = (int) (-mastery.getMastery() * 0.3f); i < (int) (mastery.getMastery() * 0.3f); i++) {
            if (i != 0) {
                this.projectileComponent.shoot(entity);
                this.projectileComponent.getShotProjectile().shootFromRotation(entity, entity.xRot, entity.yRot + i, 0, 2, 0);
                this.projectileComponent.getShotProjectile().onBlockImpactEvent = hit -> this.explodeWithSize(hit, entity, mastery.getMastery() * 0.03f);
            }
        }
    }

    private void explodeWithSize(BlockPos hit, Entity entity, float size) {
        ExplosionAbility explosion = AbilityHelper.newExplosion(entity, entity.level, hit.getX(), hit.getY(), hit.getZ(), size);
        explosion.setStaticDamage(10.0F);
        explosion.setExplosionSound(true);
        explosion.setDamageOwner(false);
        explosion.setDestroyBlocks(true);
        explosion.setFireAfterExplosion(false);
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(2));
        explosion.setDamageEntities(true);
        explosion.doExplosion();
    }
}
