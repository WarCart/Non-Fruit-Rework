package net.warcar.non_fruit_rework.entities.quests;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.TekkaiMode;
import net.warcar.non_fruit_rework.entities.goals.rokushiki.RokuoganWrapperGoal;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.ModQuests;
import xyz.pixelatedw.mineminenomi.api.entities.TrainerEntity;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiEmissionWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiFullbodyHardeningWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiHardeningWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiImbuingWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.entities.mobs.quest.givers.IHakiTrainer;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.List;

public class CP9Trainer extends TrainerEntity implements IHakiTrainer {
    public static final EntityType<CP9Trainer> INSTANCE = WyRegistry.createEntityType(CP9Trainer::new).build("");

    private static final DataParameter<Boolean> HAS_ADV_GEPPO = EntityDataManager.defineId(CP9Trainer.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> TEKKAI_MODE = EntityDataManager.defineId(CP9Trainer.class, DataSerializers.INT);

    public CP9Trainer(EntityType type, World world) {
        super(type, world, EntityHelper.getTexture("lucci"));
        if (!world.isClientSide) {
            this.getEntityStats().setFaction(ModValues.WORLD_GOVT);
            this.getEntityStats().setRace(ModValues.HUMAN);
            this.setDoriki(2000.0D + WyHelper.randomWithRange(0, 1000));
            this.setBelly(20.0D + WyHelper.randomWithRange(0, 20));
            //Rokushiki
            this.goalSelector.addGoal(2, new SoruWrapperGoal(this));
            this.goalSelector.addGoal(2, new GeppoWrapperGoal(this));
            this.goalSelector.addGoal(2, new TekkaiWrapperGoal(this));
            this.goalSelector.addGoal(2, new KamieWrapperGoal(this));
            this.goalSelector.addGoal(2, new RankyakuWrapperGoal(this));
            if (this.getRandom().nextFloat() < 0.7) {
                this.goalSelector.addGoal(2, new ShiganWrapperGoal(this));
            }
            if (this.getRandom().nextFloat() < 0.1) {
                this.goalSelector.addGoal(2, new RokuoganWrapperGoal(this));
            }
            float hakiLevel = this.getRandom().nextFloat();
            if (hakiLevel < 0.05) {
                this.goalSelector.addGoal(2, new BusoshokuHakiEmissionWrapperGoal(this));
            } else if (hakiLevel < 0.5) {
                this.goalSelector.addGoal(2, new BusoshokuHakiFullbodyHardeningWrapperGoal(this));
            } else {
                this.goalSelector.addGoal(2, new BusoshokuHakiHardeningWrapperGoal(this));
                this.goalSelector.addGoal(2, new BusoshokuHakiImbuingWrapperGoal(this));
            }
            //Other goals
            this.goalSelector.addGoal(1, new SwimGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
            this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
            this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true, true));
            this.goalSelector.addGoal(0, new SprintTowardsTargetGoal(this));
            this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.2D, true));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_ADV_GEPPO, random.nextBoolean());
        this.entityData.define(TEKKAI_MODE, random.nextInt(TekkaiMode.values().length));
    }

    public List<QuestId> getAvailableQuests(PlayerEntity playerEntity) {
        return QuestHelper.getQuestsSorted(playerEntity, ModQuests.ROKUSHIKI_QUESTS, ModQuests.ADV_ROKUSHIKI_QUESTS);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.MAX_HEALTH, 200.0D).add(Attributes.ARMOR, 15.0D);
    }

    public HakiType getTrainingHaki() {
        return HakiType.BUSOSHOKU;
    }
}