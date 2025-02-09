package net.warcar.non_fruit_rework.quest.rokushiki;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedKillEntityObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class SoruQuest extends Quest {
    public static final QuestId<SoruQuest> INSTANCE = new QuestId.Builder<>("Trial: Soru", SoruQuest::new).build();

    public SoruQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 500);
        this.addObjective(objective1);
        this.addObjective(new TimedKillEntityObjective("Kill %s enemies in %s seconds", 3, 5).addRequirement(objective1));
        this.onStartEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(SoruAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
