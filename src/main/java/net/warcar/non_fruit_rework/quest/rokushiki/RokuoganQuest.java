package net.warcar.non_fruit_rework.quest.rokushiki;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
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

public class RokuoganQuest extends Quest {
    public static final QuestId<RokuoganQuest> INSTANCE = new QuestId.Builder<>("Trial: Rokuogan", RokuoganQuest::new)
            .addRequirements(SoruQuest.INSTANCE, TekkaiQuest.INSTANCE, GeppoQuest.INSTANCE, KamieQuest.INSTANCE, ShiganQuest.INSTANCE, RankyakuQuest.INSTANCE).build();

    public RokuoganQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 3500);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Shigan", 50, SharedKillChecks.checkAbilitySource(ShiganAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective1);
        Objective objective2 = new KillEntityObjective("Kill %s enemies using Rankyaku", 35, SharedKillChecks.checkAbilitySource(RankyakuAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective2);
        this.addObjective(new KillEntityObjective("Kill %s enemies", 350, (p, e, s) -> true).addRequirements(objective1, objective2, objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(RokuoganAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}