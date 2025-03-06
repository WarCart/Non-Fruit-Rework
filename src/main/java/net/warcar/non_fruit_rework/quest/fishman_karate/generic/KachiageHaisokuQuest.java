package net.warcar.non_fruit_rework.quest.fishman_karate.generic;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.quest.objectives.SwimObjective;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanKarateHelper;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class KachiageHaisokuQuest extends Quest {
    public static final QuestId<KachiageHaisokuQuest> INSTANCE = new QuestId.Builder<>("Trial: Kachiage Haisoku", KachiageHaisokuQuest::new).build();

    public KachiageHaisokuQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 600);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies in water using your fists", 70, SharedKillChecks.HAS_EMPTY_HAND.and((playerEntity, livingEntity, damageSource) -> FishmanKarateHelper.isInWater(livingEntity))).addRequirement(objective));
        this.addObjective(new SwimObjective(3600).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(KachiageHaisokuAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}