package net.warcar.non_fruit_rework.entities.bosses;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.entities.goals.TransformationWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.EleclawWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.ElectricalLunaWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.ElectricalMissileWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.ElectricalTempestaWrapperGoal;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.challenges.OPBossEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiEmissionWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiImbuingWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.TekkaiWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.phases.SimplePhase;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public abstract class MinkDukeBoss<E extends MinkDukeBoss<E>> extends OPBossEntity<E> {
    protected final NPCPhase basicPhase = new SimplePhase<>("Basic phase", this);
    protected final NPCPhase sulongPhase = new SimplePhase<>("Sulong phase", this);
    protected final NPCPhase finalPhase = new SimplePhase<>("Final phase", this, MinkDukeBoss::startFinalPhase);

    public MinkDukeBoss(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public void initBoss() {
        super.initBoss();
        this.entityStats.setFaction(ModValues.PIRATE);
        this.entityStats.setFightingStyle(ModValues.SWORDSMAN);
        this.entityStats.setDoriki(5000);
        this.hakiCapability.setKenbunshokuHakiExp(35);
        this.hakiCapability.setBusoshokuHakiExp(60);
        //Generics
        EntityHelper.addDefaultBossGoals(this, this.getChallengeInfo());
        this.goalSelector.addGoal(3, new TekkaiWrapperGoal(this));
        //Haki
        this.basicPhase.addGoal(1, new BusoshokuHakiImbuingWrapperGoal(this));
        this.sulongPhase.addGoal(1, new BusoshokuHakiImbuingWrapperGoal(this));
        this.finalPhase.addGoal(1, new BusoshokuHakiEmissionWrapperGoal(this));
        //Electro
        this.goalSelector.addGoal(2, new EleclawWrapperGoal(this));
        this.goalSelector.addGoal(2, new ElectricalLunaWrapperGoal(this));
        this.goalSelector.addGoal(2, new ElectricalMissileWrapperGoal(this));
        this.goalSelector.addGoal(2, new ElectricalTempestaWrapperGoal(this));
        this.sulongPhase.addGoal(1, new TransformationWrapperGoal<>(this, SulongAbility.INSTANCE));
        this.finalPhase.addGoal(1, new TransformationWrapperGoal<>(this, SulongAbility.INSTANCE));
        this.getPhaseManager().setPhase(basicPhase);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MAX_HEALTH, 400.0).add(Attributes.ARMOR, 3.0).add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
    }

    public MinkDukeBoss(EntityType type, InProgressChallenge inProgressChallenge) {
        super(type, inProgressChallenge);
    }

    protected abstract void startFinalPhase();
}
