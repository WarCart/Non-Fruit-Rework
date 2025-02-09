package net.warcar.non_fruit_rework.quest.rokushiki;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
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

public class ShiganQuest extends Quest {
    public static final QuestId<ShiganQuest> INSTANCE = new QuestId.Builder<>("Trial: Shigan", ShiganQuest::new).build();

    public ShiganQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 550);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies using your fists", 120, SharedKillChecks.HAS_EMPTY_HAND).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(ShiganAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}