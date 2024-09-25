package net.warcar.non_fruit_rework.mixins.swordsman;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.warcar.non_fruit_rework.abilities.swordsman.SwordsmanHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.*;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@Mixin(ShiShishiSonsonAbility.class)
public abstract class ShishisonsonMixin extends Ability {
    @Shadow @Final private HitTrackerComponent hitTrackerComponent;

    @Shadow @Final private AnimationComponent animationComponent;

    @Shadow @Final private DealDamageComponent dealDamageComponent;

    @Shadow @Final private RangeComponent rangeComponent;

    public ShishisonsonMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }

    @Inject(method = "startChargeEvent", at = @At("RETURN"), remap = false)
    private void rename(LivingEntity entity, IAbility ability, CallbackInfo info) {
        ((ShiShishiSonsonAbility) (Object) this).setDisplayName(new String[]{"Shi Shishi Sonson", "Nitoryu Iai: Rashomon", "Oni Giri"}[SwordsmanHelper.getSwords(entity) - 1]);
    }


    @Inject(method = "endChargeEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void reDamage(LivingEntity entity, IAbility abl, CallbackInfo ci) {
        ShiShishiSonsonAbility ability = (ShiShishiSonsonAbility) (Object) this;
        ItemStack stack = entity.getMainHandItem();
        stack.hurtAndBreak(1, entity, (user) -> user.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        BlockPos blockpos = WyHelper.rayTraceBlockSafe(entity, 30.0F);
        AbilityDamageSource source = ((ModDamageSource)this.dealDamageComponent.getDamageSource(entity)).setSlash();
        Vector3d startPos = entity.position();
        float actualTeleportDistance = 30.0F;

        for(double f = 0.0D; f < 1.0D; f += 0.13D) {
            double x = MathHelper.lerp(f, startPos.x(), blockpos.getX());
            double y = MathHelper.lerp(f, startPos.y(), blockpos.getY());
            double z = MathHelper.lerp(f, startPos.z(), blockpos.getZ());
            Vector3d pos = new Vector3d(x, y, z);
            List<ProjectileEntity> projectiles = WyHelper.getNearbyEntities(pos, entity.level, entity.getBbWidth(), entity.getBbHeight(), entity.getBbWidth(), null, ProjectileEntity.class);
            if (!projectiles.isEmpty()) {
                projectiles.sort(TargetHelper.closestComparator(startPos));
                actualTeleportDistance = MathHelper.sqrt(projectiles.get(0).distanceToSqr(startPos));
                break;
            }
        }

        blockpos = WyHelper.rayTraceBlockSafe(entity, actualTeleportDistance);
        List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, actualTeleportDistance, 2.5F);
        for (LivingEntity target : targets) {
            if (this.hitTrackerComponent.canHit(target)) {
                boolean flag = this.dealDamageComponent.hurtTarget(entity, target, 20.0F + SwordsmanHelper.getSwords(entity) * 10, source);
                if (flag && !entity.level.isClientSide) {
                    WyHelper.spawnParticles(ParticleTypes.SWEEP_ATTACK, (ServerWorld) entity.level, target.getX(), target.getY() + (double) target.getEyeHeight(), target.getZ());
                }
            }
        }
        entity.stopRiding();
        entity.teleportToWithTicket(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        if (!entity.level.isClientSide) {
            ((ServerWorld)entity.level).getChunkSource().broadcastAndSend(entity, new SAnimateHandPacket(entity, 0));
        }

        entity.level.playSound(null, entity.blockPosition(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundCategory.PLAYERS, 2.0F, 1.0F);
        this.animationComponent.stop(entity);
        this.cooldownComponent.startCooldown(entity, 180.0F);
    }
}
