package net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.warcar.non_fruit_rework.init.ModRaidSuits;
import net.warcar.non_fruit_rework.items.RaidSuitItem;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class RyoseiOsobaKick extends DropHitAbility {
    public static final AbilityCore<RyoseiOsobaKick> INSTANCE;
    private boolean acceding;

    public RyoseiOsobaKick(AbilityCore core) {
        super(core);
        this.continuousComponent.addTickEvent(this::duringContinuityEvent);
        this.continuousComponent.addStartEvent(this::onStartContinuityEvent);
        this.addCanUseCheck(AbilityHelper::canUseMomentumAbilities);
    }

    public void onLanding(LivingEntity player) {
        if (!this.acceding) {
            List<LivingEntity> targets = WyHelper.getNearbyLiving(player.position(), player.level, 1.5D, ModEntityPredicates.getEnemyFactions(player));
            targets.remove(player);

            for (LivingEntity entity : targets) {
                DamageSource source = ModDamageSource.causeAbilityDamage(player, this, "player");
                if (this.hitTrackerComponent.canHit(entity) && entity.hurt(source, 30)) {
                    Vector3d speed = player.position().vectorTo(entity.position()).normalize().scale(2);
                    entity.setDeltaMovement(speed.x, speed.y, speed.z);
                    entity.hurtMarked = true;
                }
            }
        }
        this.cooldownComponent.startCooldown(player, 360);
    }

    private void duringContinuityEvent(LivingEntity player, IAbility ability) {
        if (player.getDeltaMovement().y <= 0.1 && this.acceding) {
            this.acceding = false;
            Vector3d speed = WyHelper.propulsion(player, 6, 6, 6);
            player.setDeltaMovement(speed.x, speed.y, speed.z);
            player.hurtMarked = true;
        }
    }

    private void onStartContinuityEvent(LivingEntity player, IAbility ability) {
        this.hitTrackerComponent.clearHits();
        this.acceding = true;
        Vector3d speed = WyHelper.propulsion(player, 1.0D, 1.0D);
        player.setDeltaMovement(speed.x, 2D, speed.z);
        player.hurtMarked = true;
    }

    private static boolean canUnlock(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlotType.FEET).getItem() == ModRaidSuits.STEALTH_BLACK_BOOTS.get();
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Ryosei Osoba Kick", AbilityCategory.EQUIPMENT, RyoseiOsobaKick::new)
                .addDescriptionLine("User uses jet boots to fly up, then propels himself forward towards enemy").setUnlockCheck(RyoseiOsobaKick::canUnlock)
                .setSourceType(SourceType.FIST).setSourceHakiNature(SourceHakiNature.HARDENING).build();
    }
}
