package net.warcar.non_fruit_rework.quest.rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.extra.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.quest.SDespawnQuestObjectivePacket;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedSurvivalObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.ArrayList;
import java.util.List;

public class KamieQuest extends Quest {
    private final List<AbilityProjectileEntity> targets = new ArrayList<>();
    public static final QuestId<KamieQuest> INSTANCE = new QuestId.Builder<>("Trial: Kamie", KamieQuest::new).build();

    public KamieQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 535);
        this.addObjective(objective1);
        this.addObjective(new CustomUseAbilityObjective(10, SoruAbility.INSTANCE).addRequirement(objective1));
        this.addObjective(new TakeDamageObjective("Take %s Damage", 72).addRequirement(objective1));
        Objective objective2 = new TimedSurvivalObjective("Survive event without getting hit", 100) {
            @Override
            public boolean checkTime(PlayerEntity player) {
                if (this.isComplete()) {
                    return true;
                }
                if (!this.hasStartedEvent()) {
                    this.setProgress(player, 0.0, true);
                    return false;
                }
                if (!super.checkTime(player)) {
                    this.triggerRestartEvent(player);
                    return false;
                }
                return true;
            }
        }.addRequirement(objective1);
        objective2.setHasEvent(true);
        objective2.onStartEvent = player -> {
            LivingEntity e = WyHelper.getNearbyLiving(player.position(), player.level, 100, ModEntityPredicates.getEnemyFactions(player)).stream().findFirst().orElse(null);
            for(int i = 0; i < 20; ++i) {
                AbilityProjectileEntity target = new CannonBallProjectile(player.level, e);
                target.setDamageSource(new ModDamageSource("quest").setProjectile().setUnavoidable().setBypassFriendlyDamage());
                target.onBlockImpactEvent = pos -> {
                    ExplosionAbility explosion = AbilityHelper.newExplosion(target, player.level, pos.getX(), pos.getY(), pos.getZ(), 2.0F);
                    explosion.setStaticDamage(18.0F);
                    explosion.setDestroyBlocks(false);
                    explosion.setDamageOwner(true);
                    explosion.setSmokeParticles(new CommonExplosionParticleEffect(2));
                    explosion.doExplosion();
                    targets.remove(target);
                    if (targets.isEmpty()) {
                        objective2.setProgress(player, objective2.getMaxProgress(), false);
                    }
                    if (target.isAlive()) {
                        WyNetwork.sendToAll(new SDespawnQuestObjectivePacket(player.getUUID(), target.getId()));
                    }
                };
                target.onEntityImpactEvent = livingEntity -> target.onBlockImpactEvent.onImpact(livingEntity.blockPosition());
                target.setDamage(3);
                target.setMaxLife(400);
                target.setDeltaMovement(0, -(double) WyHelper.randomWithRange(400, 1000) / 600, 0);
                double posX = player.getX() + WyHelper.randomWithRange(-5, 5);
                double posY = player.getY() + 30.0D;
                double posZ = player.getZ() + WyHelper.randomWithRange(-5, 5);
                target.moveTo(posX, posY, posZ, 0.0F, 0.0F);
                player.level.addFreshEntity(target);
                this.targets.add(target);
            }
        };
        objective2.onRestartEvent = playerEntity -> {
            if (!this.targets.isEmpty()) {
                for (AbilityProjectileEntity entity : this.targets) {
                    entity.remove();
                }
                this.targets.clear();
                objective2.setProgress(playerEntity, 0, true);
            }
            return true;
        };
        this.addObjective(objective2);
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(KamieAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
