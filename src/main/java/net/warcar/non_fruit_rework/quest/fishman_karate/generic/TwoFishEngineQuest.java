package net.warcar.non_fruit_rework.quest.fishman_karate.generic;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.quest.objectives.SwimObjective;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.TwoFishEngineAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class TwoFishEngineQuest extends Quest {
    public static final QuestId<TwoFishEngineQuest> INSTANCE = new QuestId.Builder<>("Trial: Two Fish Engine", TwoFishEngineQuest::new).build();

    public TwoFishEngineQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 550);
        this.addObjective(objective);
        this.addObjective(new SwimObjective(6000).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(TwoFishEngineAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
