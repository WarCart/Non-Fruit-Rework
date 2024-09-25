package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.quests.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedSurvivalObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.UseAbilityObjective;

import java.util.ArrayList;
import java.util.List;

public class KamieQuest extends Quest {
    private final List<AbilityProjectileEntity> targets = new ArrayList<>();
    public static final QuestId<KamieQuest> INSTANCE = new QuestId.Builder<>("Trial: Kamie", KamieQuest::new).build();
    public static final QuestId<KamieQuest> DIFFICULT = new QuestId.Builder<>("Trial: Kamie difficult", KamieQuest::difficult).build();

    public KamieQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 535);
        this.addObjective(objective1);
        this.addObjective(new UseAbilityObjective("Use Soru %s times", 9, SoruAbility.INSTANCE).addRequirement(objective1));
        this.addObjective(new TakeDamageObjective("Take %s Damage", 60).addRequirement(objective1));
        Objective objective2 = new TimedSurvivalObjective("Survive event without getting hit", 100)/* {
            @Override
            public boolean checkTime(PlayerEntity player) {
                return false;
            }
        }*/.addRequirement(objective1)/*;
        objective2.setHasEvent(true);
        objective2.onStartEvent = player -> {
            for(int i = 0; i < 20; ++i) {
                AbilityProjectileEntity target = new CannonBallProjectile(player.level, new CP9Trainer(ReworkedEntities.ROKUSHIKI_TRAINER.get(), player.level));
                target.setDamageSource(new ModDamageSource("quest").setProjectile().setUnavoidable());
                target.onBlockImpactEvent = pos -> {
                    ExplosionAbility explosion = AbilityHelper.newExplosion(target.getThrower(), player.level, pos.getX(), pos.getY(), pos.getZ(), 2.0F);
                    explosion.setStaticDamage(8.0F);
                    explosion.setDestroyBlocks(false);
                    explosion.setSmokeParticles(new CommonExplosionParticleEffect(2));
                    explosion.doExplosion();
                    targets.remove(target);
                    if (targets.size() == 0) {
                        objective2.setProgress(objective2.getMaxProgress());
                    }
                };
                target.onEntityImpactEvent = livingEntity -> target.onBlockImpactEvent.onImpact(livingEntity.blockPosition());
                target.setDamage(3);
                target.setMaxLife(400);
                target.setDeltaMovement(0, -(double) WyHelper.randomWithRange(400, 1000) / 400, 0);
                double posX = player.getX() + WyHelper.randomWithRange(-5, 5);
                double posY = player.getY() + 30.0D;
                double posZ = player.getZ() + WyHelper.randomWithRange(-5, 5);
                target.moveTo(posX, posY, posZ, 0.0F, 0.0F);
                player.level.addFreshEntity(target);
                this.targets.add(target);
                if (target.isAlive()) {
                    WyNetwork.sendToAll(new SDespawnQuestObjectivePacket(player.getUUID(), target.getId()));
                }
            }
        };
        objective2.onRestartEvent = playerEntity -> {
            if (this.targets.size() > 0) {
                for (AbilityProjectileEntity entity : this.targets) {
                    entity.remove();
                }
                this.targets.clear();
                objective2.setProgress(0);
            }
            return true;
        }*/;
        this.addObjective(objective2);
        this.onStartEvent = this::giveReward;
    }

    public static KamieQuest difficult(QuestId core) {
        KamieQuest quest = new KamieQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 645);
        quest.addObjective(objective1);
        quest.addObjective(new UseAbilityObjective("Use Soru %s times", 11, SoruAbility.INSTANCE).addRequirement(objective1));
        quest.addObjective(new TakeDamageObjective("Take %s Damage", 72).addRequirement(objective1));
        Objective objective2 = new TimedSurvivalObjective("Survive event without getting hit", 120).addRequirement(objective1);
        quest.addObjective(objective2);
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(KamieAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
