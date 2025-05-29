package net.warcar.non_fruit_rework.entities.seraphim;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.entities.IThreatLevel;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.GapCloserGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.cyborg.RadicalBeamWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.GeppoWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.SoruWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

import javax.annotation.Nullable;

public class SeraphimEntity extends OPEntity implements ICommandReceiver, IThreatLevel {
    private long lastCommandTime;
    private LivingEntity lastCommandSender;
    private NPCCommand currentCommand;

    public SeraphimEntity(EntityType<? extends SeraphimEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean canReceiveCommandFrom(LivingEntity livingEntity) {
        IEntityStats props = EntityStatsCapability.get(livingEntity);
        boolean frendly = this.getEntityStats().getFaction().equals(props.getFaction());
        if (!frendly) {
            return false;
        }
        if (this.getCurrentCommand() != NPCCommand.IDLE && this.getLastCommandSender() != null) {
            //TODO: Sophisticated hierarchy chips system
        }
        return true;
    }

    public void setCurrentCommand(@Nullable LivingEntity commandSender, NPCCommand command) {
        this.lastCommandTime = this.level.getGameTime();
        this.lastCommandSender = commandSender;
        this.currentCommand = command;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 90.0).add(Attributes.ATTACK_DAMAGE, 10).add(Attributes.MAX_HEALTH, 250).add(ModAttributes.TOUGHNESS.get());
    }

    public boolean canMaintainCommand() {
        return this.lastCommandSender == null || !this.lastCommandSender.isAlive() || !EntityStatsCapability.get(this.lastCommandSender).isRogue();
    }

    public NPCCommand getCurrentCommand() {
        return this.currentCommand == null ? NPCCommand.IDLE : this.currentCommand;
    }

    @Nullable
    public LivingEntity getLastCommandSender() {
        return this.lastCommandSender;
    }

    public long getLastCommandTime() {
        return this.lastCommandTime;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //Generic
        MobsHelper.addBasicNPCGoals(this);
        CommandAbility.addCommandGoals(this);
        this.goalSelector.addGoal(0, new JumpOutOfHoleGoal(this));
        this.goalSelector.addGoal(0, new GapCloserGoal(this));
        this.goalSelector.addGoal(0, new DashDodgeProjectilesGoal(this, 200.0F, 2.5F));
        this.goalSelector.addGoal(0, new DashDodgeTargetGoal(this, 250.0F, 2.5F));
        this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.addGoal(3, new SoruWrapperGoal(this));
        this.goalSelector.addGoal(3, new GeppoWrapperGoal(this));
        this.goalSelector.addGoal(2, new RadicalBeamWrapperGoal(this));
    }

    protected IDevilFruit getDevilFruit() {
        return DevilFruitCapability.get(this);
    }

    protected IHakiData getHakiData() {
        return HakiDataCapability.get(this);
    }

    @Override
    public float getThreatLevel() {
        return 1;
    }
}
