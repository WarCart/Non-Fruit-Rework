package net.warcar.non_fruit_rework.entities.mobs.trainers;

import com.google.common.collect.Lists;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.entities.goals.StandardWrapperGoal;
import net.warcar.non_fruit_rework.init.ReworkedQuests;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.api.entities.TrainerEntity;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.entities.mobs.quest.givers.IHakiTrainer;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;
import java.util.function.Supplier;

public class CP9Trainer extends TrainerEntity implements IHakiTrainer {
    public CP9Trainer(EntityType type, World world) {
        super(type, world, new ResourceLocation[]{MobsHelper.entityTexture("black_leg_trainer2")});
        if (!world.isClientSide) {
            this.getEntityStats().setFaction("marine");
            this.getEntityStats().setRace("human");
            this.setDoriki(2000.0D + WyHelper.randomWithRange(0, 1000));
            this.setBelly(20.0D + WyHelper.randomWithRange(0, 20));
            this.goalSelector.addGoal(2, new SoruWrapperGoal(this));
            this.goalSelector.addGoal(2, new GeppoWrapperGoal(this));
            this.goalSelector.addGoal(1, new SwimGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
            this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true, true));
            this.goalSelector.addGoal(0, new SprintTowardsTargetGoal(this));
            this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.2D, true));
            WeightedList<Supplier<Goal>> goals = new WeightedList<>();
            goals.addEntry(() -> new TekkaiWrapperGoal(this), 15.0F);
            goals.addEntry(() -> new KamieWrapperGoal(this), 15.0F);
            goals.addEntry(() -> new RankyakuWrapperGoal(this), 10.0F);
            goals.addEntry(() -> new ShiganWrapperGoal(this), 10.0F);
            goals.addEntry(() -> new StandardWrapperGoal<>(this, RokuoganAbility.INSTANCE, 5), 1.0F);
            MobsHelper.getRandomizedGoals(this, 3, goals).forEach((goal) -> this.goalSelector.addGoal(2, goal));
        }
    }

    public List<QuestId> getAvailableQuests(PlayerEntity playerEntity) {
        List<QuestId> questIds;
        if (EntityStatsCapability.get(playerEntity).isHuman()) {
            questIds = ReworkedQuests.ROKUSHIKI_QUESTS;
        } else {
            questIds = ReworkedQuests.DIFFICULT_ROKUSHIKI_QUESTS;
        }
        QuestId quest = null;
        IQuestData data = QuestDataCapability.get(playerEntity);
        for (QuestId questId : questIds) {
            if (data.hasInProgressQuest(questId)) {
                quest = questId;
                break;
            }
        }
        if (quest != null) {
            return Lists.newArrayList(quest);
        }
        return questIds;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.ARMOR, 15.0D);
    }

    public HakiType getTrainingHaki() {
        return HakiType.BUSOSHOKU;
    }
}
