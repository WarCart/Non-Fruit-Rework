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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.warcar.non_fruit_rework.animations.SanzenSekaiAnimation;
import net.warcar.non_fruit_rework.enums.Style;
import net.warcar.non_fruit_rework.init.ReworkedQuests;
import net.warcar.non_fruit_rework.init.ReworkedAnimations;
import net.warcar.non_fruit_rework.quests.QuestProgression;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.swordsman.SanbyakurokujuPoundHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

public class SanzenSekaiAbility extends Ability {
    public static final AbilityCore<SanzenSekaiAbility> INSTANCE;
    private final AltModeComponent<Mode> modeComponent;
    private final HitTrackerComponent hitTrackerComponent;
    private final ChargeComponent chargeComponent;
    private final AnimationComponent animationComponent;

    public SanzenSekaiAbility(AbilityCore<SanzenSekaiAbility> core) {
        super(core);
        this.isNew = true;
        modeComponent = new AltModeComponent<>(this, Mode.class, Mode.SANZEN_SEKAI);
        hitTrackerComponent = new HitTrackerComponent(this);
        chargeComponent = new ChargeComponent(this);
        animationComponent = new AnimationComponent(this);
        this.addComponents(modeComponent, hitTrackerComponent, chargeComponent, animationComponent);
        this.addCanUseCheck(AbilityHelper::canUseMomentumAbilities);
        this.addCanUseCheck(SwordsmanHelper::canUseSantoruAbilities);
        this.addCanUseCheck(this::isReady);
        this.addUseEvent(this::onStartChargingEvent);
        this.chargeComponent.addEndEvent(this::onEndChargingEvent);
    }

    private AbilityUseResult isReady(LivingEntity entity, IAbility ability) {
        if (this.modeComponent.isMode(Mode.ICHIDAI_SANZEN_DAISEN_SEKAI) && !HakiHelper.hasImbuingActive(entity)) {
            return AbilityUseResult.fail(new TranslationTextComponent(ModI18n.ABILITY_MESSAGE_MISSING_DEPENDENCY_SINGLE, INSTANCE.getLocalizedName(), BusoshokuHakiImbuingAbility.INSTANCE.getLocalizedName()));
        }
        return AbilityUseResult.success();
    }

    private void onStartChargingEvent(LivingEntity player, IAbility ability) {
        this.hitTrackerComponent.clearHits();
        this.animationComponent.start(player, SanzenSekaiAnimation.INSTANCE);
        this.chargeComponent.startCharging(player, this.modeComponent.isMode(Mode.ICHIDAI_SANZEN_DAISEN_SEKAI) ? 240 : 160);
    }

    private void onEndChargingEvent(LivingEntity player, IAbility ability) {
        BlockPos startPos = player.blockPosition();
        player.getMainHandItem().hurtAndBreak(7, player, (user) -> user.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        BlockRayTraceResult mop = WyHelper.rayTraceBlocks(player, 15.0D);
        BlockPos blockpos;
        if (mop != null && mop.getType() != RayTraceResult.Type.MISS) {
            blockpos = WyHelper.getClearPositionForPlayer(player, mop.getBlockPos());
        } else {
            blockpos = WyHelper.rayTraceBlockSafe(player, 15.0F);
        }

        if (blockpos == null) {
            blockpos = WyHelper.rayTraceBlockSafe(player, 15.0F);
        }

        for(float f = 0.0F; f < 1.0F; f = (float)((double)f + 0.13D)) {
            int x = (int) MathHelper.lerp(f, (float)startPos.getX(), (float)blockpos.getX());
            int y = (int)MathHelper.lerp(f, (float)startPos.getY(), (float)blockpos.getY());
            int z = (int)MathHelper.lerp(f, (float)startPos.getZ(), (float)blockpos.getZ());
            Vector3d pos = new Vector3d(x, y, z);
            List<LivingEntity> targets = WyHelper.getNearbyLiving(pos, player.level, 3.0D, ModEntityPredicates.getEnemyFactions(player));
            for (LivingEntity target : targets) {
                if (player.canSee(target) && this.hitTrackerComponent.canHit(target)) {
                    boolean flag = target.hurt(AbilityDamageSource.causeAbilityDamage(player, this, "player").setSlash(), this.modeComponent.isMode(Mode.ICHIDAI_SANZEN_DAISEN_SEKAI) ? 200 : 300);
                    if (flag) {
                        WyHelper.spawnParticles(ParticleTypes.SWEEP_ATTACK, (ServerWorld) player.level, target.getX(), target.getY() + (double) target.getEyeHeight(), target.getZ());
                    }
                }
            }
        }
        player.stopRiding();
        player.teleportToWithTicket(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        player.level.playSound(null, player.blockPosition(), ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundCategory.PLAYERS, 0.5F, 1.0F);
        if (this.modeComponent.isMode(Mode.ICHIDAI_SANZEN_DAISEN_SEKAI)) {
            for (int i = -90; i < 91; i+=2) {
                SanbyakurokujuPoundHoProjectile projectile = new SanbyakurokujuPoundHoProjectile(player.level, player, this);
                player.level.addFreshEntity(projectile);
                projectile.setCanGetStuckInGround();
                float sx = player.xRot + i * (float) Math.sin(Math.toRadians(player.xRot));
                float sy = player.yRot + i * (float) Math.cos(Math.toRadians(player.xRot));
                projectile.shootFromRotation(player, sx, sy, 0.0F, 2.0F, 0.0F);
            }
        }
        this.cooldownComponent.startCooldown(player, this.modeComponent.isMode(Mode.ICHIDAI_SANZEN_DAISEN_SEKAI) ? 1200 : 800);
        this.animationComponent.stop(player);
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Sanzen Sekai", AbilityCategory.STYLE, SanzenSekaiAbility::new).addDescriptionLine("The user dashes forward rapidly spinning swords in hands to gain more momentum and slash opponent as strong as possible\n\n§2SHIFT-USE while haki imbuing is active§r: Sends Extreme powerful shockwave strong enough to cut giant mountains")
                .setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).setUnlockCheck(QuestProgression.hasFinishedQuest(ReworkedQuests.THREE_SWORD_STILE_TRIAL_02, Style.Swordsman)).build();
    }

    public enum Mode {
        SANZEN_SEKAI,
        ICHIDAI_SANZEN_DAISEN_SEKAI
    }
}
