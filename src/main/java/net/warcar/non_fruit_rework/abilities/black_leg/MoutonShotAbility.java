package net.warcar.non_fruit_rework.abilities.black_leg;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.warcar.non_fruit_rework.enums.Style;
import net.warcar.non_fruit_rework.quests.QuestProgression;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class MoutonShotAbility extends Ability {
    public static final AbilityCore<MoutonShotAbility> INSTANCE = new AbilityCore.Builder<>("Mouton Shot", AbilityCategory.STYLE, MoutonShotAbility::new)
            .addDescriptionLine("High speed forward dash, followed by rapid kick").setSourceType(SourceType.FIST)
            .setSourceHakiNature(SourceHakiNature.HARDENING)
            .setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_01, Style.Black_Leg)).build();
    private final HitTrackerComponent hitTrackerComponent;

    public MoutonShotAbility(AbilityCore<MoutonShotAbility> core) {
        super(core);
        this.isNew = true;
        hitTrackerComponent = new HitTrackerComponent(this);
        this.addCanUseCheck(AbilityHelper::canUseMomentumAbilities);
        this.addComponents(hitTrackerComponent);
        this.addUseEvent(this::onUseEvent);
        this.cooldownComponent.addTickEvent(this::duringCooldown);
        this.addTickEvent(this::onTick);
    }

    private void onTick(LivingEntity entity, IAbility abl) {
        DiableJambeAbility ability = AbilityDataCapability.get(entity).getEquippedAbility(DiableJambeAbility.INSTANCE);
        if (ability != null && ability.isContinuous() && ((IDiableJambeMixin) ability).isIfrit()) {
            this.setDisplayName(new StringTextComponent("Boeuf Burst"));
            this.setCustomIcon("Beef Blast");
        } else if (ability != null && ability.isContinuous()) {
            this.setDisplayName(new StringTextComponent("Diable Mouton Shot"));
            this.setCustomIcon("Diable Mouton Shot");
        } else {
            this.setDisplayName((ITextComponent) null);
            this.setDisplayIcon((ResourceLocation) null);
        }
    }

    private void onUseEvent(LivingEntity player, IAbility ability) {
        this.hitTrackerComponent.clearHits();
        Vector3d speed = player.getLookAngle().multiply(3, 3, 3);
        player.setDeltaMovement(speed.x, speed.y, speed.z);
        player.hurtMarked = true;
        this.cooldownComponent.startCooldown(player, 200);
        ((ServerWorld)player.level).getChunkSource().broadcastAndSend(player, new SAnimateHandPacket(player, 0));
    }

    private void duringCooldown(LivingEntity player, IAbility ability) {
        if (this.cooldownComponent.getStartCooldown() - this.cooldownComponent.getCooldown() < 20) {
            DiableJambeAbility diableJambeAbility = AbilityDataCapability.get(player).getEquippedAbility(DiableJambeAbility.INSTANCE);
            List<LivingEntity> targets = WyHelper.getNearbyLiving(player.position(), player.level, 1.8D, ModEntityPredicates.getEnemyFactions(player));
            targets.remove(player);
            for (LivingEntity entity : targets) {
                DamageSource source = ModDamageSource.causeAbilityDamage(player, this, "player");
                if (diableJambeAbility != null && diableJambeAbility.isContinuous() && ((IDiableJambeMixin) ability).isIfrit()) {
                    source.bypassArmor();
                }
                if (this.hitTrackerComponent.canHit(entity) && entity.hurt(source, 10)) {
                    Vector3d speed = player.position().vectorTo(entity.position()).normalize().multiply(2, 2, 2);
                    entity.setDeltaMovement(speed.x, 0.2D, speed.z);
                    entity.hurtMarked = true;
                    if (diableJambeAbility != null && ((IDiableJambeMixin) ability).isIfrit() && this.isContinuous()) {
                        entity.setSecondsOnFire(5);
                    } else if (diableJambeAbility != null && diableJambeAbility.isContinuous()) {
                        entity.setSecondsOnFire(2);
                    }
                }
            }
        }

    }
}
