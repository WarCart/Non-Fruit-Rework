package net.warcar.non_fruit_rework.quest.fishman_karate.generic;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanKarateHelper;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class KarakusagawaraSeikenQuest extends Quest {
    public static final QuestId<KarakusagawaraSeikenQuest> INSTANCE = new QuestId.Builder<>("Trial: Karakusagawara Seiken", KarakusagawaraSeikenQuest::new)
            .addRequirements(KachiageHaisokuQuest.INSTANCE, SamehadaShoteiQuest.INSTANCE, TwoFishEngineQuest.INSTANCE).build();

    public KarakusagawaraSeikenQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 4000);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Kachiage Haisoku", 50, SharedKillChecks.checkAbilitySource(KachiageHaisokuAbility.INSTANCE))
                .addRequirement(objective);
        this.addObjective(objective1);
        this.addObjective(new KillEntityObjective("Kill %s enemies while in water", 25, (playerEntity, livingEntity, damageSource) -> FishmanKarateHelper.isInWater(playerEntity))
                .addRequirements(objective1, objective));
        this.addObjective(new KillEntityObjective("Kill %s enemies in water", 35, (playerEntity, livingEntity, damageSource) -> FishmanKarateHelper.isInWater(livingEntity))
                .addRequirements(objective1, objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(KarakusagawaraSeikenAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}