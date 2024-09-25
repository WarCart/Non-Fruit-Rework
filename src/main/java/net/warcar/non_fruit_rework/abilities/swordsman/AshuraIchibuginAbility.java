package net.warcar.non_fruit_rework.abilities.swordsman;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.warcar.non_fruit_rework.enums.Style;
import net.warcar.non_fruit_rework.init.ReworkedQuests;
import net.warcar.non_fruit_rework.quests.QuestProgression;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

public class AshuraIchibuginAbility extends Ability {
    public static final AbilityCore<AshuraIchibuginAbility> INSTANCE;
    private final HitTrackerComponent hitTrackerComponent;
    private final ChargeComponent chargeComponent;

    public AshuraIchibuginAbility(AbilityCore<AshuraIchibuginAbility> core) {
        super(core);
        this.isNew = true;
        hitTrackerComponent = new HitTrackerComponent(this);
        chargeComponent = new ChargeComponent(this);
        this.addComponents(hitTrackerComponent, chargeComponent);
        this.addCanUseCheck(AbilityHelper::canUseMomentumAbilities);
        this.addCanUseCheck(SwordsmanHelper::canUseSantoruAbilities);
        this.addUseEvent(this::onStartChargingEvent);
        this.chargeComponent.addEndEvent(this::onEndChargingEvent);
    }

    private void onStartChargingEvent(LivingEntity entity, IAbility ability) {
        if (HakiHelper.hasInfusionActive(entity)) {
            this.setDisplayName("Ashura Bakkei: Moja no Tawamure");
        } else {
            this.setDisplayName("Ashura: Ichibugin");
        }
        this.chargeComponent.startCharging(entity, 60);
    }

    private void onEndChargingEvent(LivingEntity player, IAbility ability) {
        for (AbilityProjectileEntity proj : WyHelper.getNearbyEntities(player.position(), player.level, 15, (entity -> !((AbilityProjectileEntity) entity).isPhysical() && !(((AbilityProjectileEntity) entity).getThrower() == player)), AbilityProjectileEntity.class)) {
            WyHelper.spawnParticleEffect(ModParticleEffects.KEMURI_BOSHI.get(), proj, proj.getX(), proj.getY(), proj.getZ());
            proj.kill();
        }
        BlockPos startPos = player.blockPosition();
        player.getMainHandItem().hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        BlockRayTraceResult mop = WyHelper.rayTraceBlocks(player, 10.0D);
        BlockPos blockpos;
        if (mop != null && mop.getType() != RayTraceResult.Type.MISS) {
            blockpos = WyHelper.getClearPositionForPlayer(player, mop.getBlockPos());
        } else {
            blockpos = WyHelper.rayTraceBlockSafe(player, 10.0F);
        }

        if (blockpos == null) {
            blockpos = WyHelper.rayTraceBlockSafe(player, 10.0F);
        }
        for (float f = 0.0F; f < 1.0F; f = (float) ((double) f + 0.13D)) {
            int x = (int) MathHelper.lerp(f, (float) startPos.getX(), (float) blockpos.getX());
            int y = (int) MathHelper.lerp(f, (float) startPos.getY(), (float) blockpos.getY());
            int z = (int) MathHelper.lerp(f, (float) startPos.getZ(), (float) blockpos.getZ());
            Vector3d pos = new Vector3d(x, y, z);
            List<LivingEntity> targets = WyHelper.getNearbyLiving(pos, player.level, 3.0D, ModEntityPredicates.getEnemyFactions(player));
            for (LivingEntity target : targets) {
                if (player.canSee(target) && this.hitTrackerComponent.canHit(target)) {
                    boolean flag = target.hurt(AbilityDamageSource.causeAbilityDamage(player, this, "player").setSlash(), HakiHelper.hasInfusionActive(player) ? 500 : 300);
                    if (flag) {
                        WyHelper.spawnParticles(ParticleTypes.SWEEP_ATTACK, (ServerWorld) player.level, target.getX(), target.getY() + (double) target.getEyeHeight(), target.getZ());
                    }
                }
            }
        }

        player.stopRiding();
        player.teleportToWithTicket(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        player.level.playSound(null, player.blockPosition(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundCategory.PLAYERS, 0.5F, 1.0F);
        this.cooldownComponent.startCooldown(player, 1800);
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Ashura: Ichibugin", AbilityCategory.STYLE, AshuraIchibuginAbility::new).addDescriptionLine("User uses immense power to split own body to 3 afterimages, then strikes opponent turning any projectiles on the way to mist")
                .setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setUnlockCheck(QuestProgression.hasFinishedQuest(ReworkedQuests.THREE_SWORD_STILE_TRIAL_01, Style.Swordsman)).build();
    }
}
