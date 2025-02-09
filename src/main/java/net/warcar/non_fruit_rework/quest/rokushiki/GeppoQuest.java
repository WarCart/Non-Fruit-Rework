package net.warcar.non_fruit_rework.quest.rokushiki;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedSurvivalObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class GeppoQuest extends Quest {
    public static final QuestId<GeppoQuest> INSTANCE = new QuestId.Builder<>("Trial: Geppo", GeppoQuest::new).build();
    private final CustomObtainItemObjective<Item> collectObjective;

    public GeppoQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 510);
        this.addObjective(objective);
        collectObjective = new CustomObtainItemObjective<>(2, () -> Items.RABBIT_FOOT);
        this.addObjective(collectObjective.addRequirement(objective));
        this.addObjective(new TimedSurvivalObjective("Survive for %s seconds without getting hit", 300).addRequirement(objective));
        this.addObjective(new CustomUseAbilityObjective(10, SoruAbility.INSTANCE).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        if (!this.removeQuestItem(player, collectObjective.getItemTarget().get(), collectObjective.getItemsNeeded())) {
            return false;
        }
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(GeppoAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
