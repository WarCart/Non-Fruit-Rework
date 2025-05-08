package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.tekkai_kenpo;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RequireAbilityComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.EnumSet;
import java.util.Set;

public class RokaruAreaNetworkAbility extends Ability {
    public static final AbilityCore<RokaruAreaNetworkAbility> INSTANCE = new AbilityCore.Builder<>("Tekkai Kenpo: Rokaru Area Network", AbilityCategory.RACIAL, RokaruAreaNetworkAbility::new)
            .setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build();

    private final ContinuousComponent continuousComponent = new ContinuousComponent(this).addTickEvent(this::onTick).addEndEvent(this::onStop);
    private final DealDamageComponent damageComponent = new DealDamageComponent(this);
    private final RequireAbilityComponent requireAbilityComponent = new RequireAbilityComponent(this, OkamiHajikiAbility.CHECK);

    private LivingEntity target;
    public RokaruAreaNetworkAbility(AbilityCore<RokaruAreaNetworkAbility> core) {
        super(core);
        this.isNew = true;
        this.addComponents(continuousComponent, damageComponent, requireAbilityComponent);
        this.addCanUseCheck(this::canUse);
        this.addUseEvent(AbilityHelper::canUseBrawlerAbilities);
        this.addUseEvent(this::onUse);
    }

    private void onUse(LivingEntity entity, IAbility iAbility) {
        this.target = (LivingEntity) WyHelper.rayTraceEntities(entity, 20).getEntity();
        this.continuousComponent.startContinuity(entity, 100);
    }

    private void onTick(LivingEntity entity, IAbility iAbility) {
        if (this.target != null && this.target.isAlive()) {
            MiscHelper.spawnAfterimage(entity);
            double rot = Math.PI * 2 / 5 * (this.continuousComponent.getContinueTime() * 2 % 5);
            entity.teleportToWithTicket(target.getX() + Math.sin(rot), target.getY(), target.getZ() + Math.cos(rot));
            entity.yRot = 180 - (float) Math.toDegrees(rot);
            if (entity instanceof PlayerEntity) {
                Set<SPlayerPositionLookPacket.Flags> flags = EnumSet.of(SPlayerPositionLookPacket.Flags.X, SPlayerPositionLookPacket.Flags.Y, SPlayerPositionLookPacket.Flags.Z);
                ((ServerPlayerEntity)entity).connection.teleport(entity.getX(), entity.getY(), entity.getZ(), entity.yRot, entity.xRot, flags);
            }
            this.damageComponent.hurtTarget(entity, target, 10);
        } else if (this.target != null) {
            this.continuousComponent.stopContinuity(entity);
        }
    }

    private AbilityUseResult canUse(LivingEntity entity, IAbility iAbility) {
        EntityRayTraceResult result = WyHelper.rayTraceEntities(entity, 20);
        if (!(result.getEntity() instanceof LivingEntity)) {
            return AbilityUseResult.fail(new TranslationTextComponent(ModI18n.ABILITY_MESSAGE_NO_TARGET));
        }
        return AbilityUseResult.success();
    }

    private void onStop(LivingEntity entity, IAbility iAbility) {
        this.cooldownComponent.startCooldown(entity, 400);
    }
}
