package net.warcar.non_fruit_rework.quest.cyborg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import net.warcar.non_fruit_rework.quest.objectives.ObtainBellyObjective;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.StrongRightAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class StrongRightQuest extends Quest {
    public static final QuestId<StrongRightQuest> INSTANCE = new QuestId.Builder<>("Strong right", StrongRightQuest::new)
            .addRequirements(CyborgBodyQuest.INSTANCE).build();
    private final ObtainBellyObjective bellyObjective;
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public StrongRightQuest(QuestId core) {
        super(core);
        this.bellyObjective = new ObtainBellyObjective("Obtain %s belly", 10000);
        this.addObjective(bellyObjective);
        this.collectObjectives = new CustomObtainItemObjective[] {
                new CustomObtainItemObjective<>(8, Items.REDSTONE),
                new CustomObtainItemObjective<>(4, Items.IRON_INGOT)};
        for (CustomObtainItemObjective<Item> objective : collectObjectives) {
            objective.addRequirement(bellyObjective);
        }
        this.addObjectives(collectObjectives);
        this.onCompleteEvent = this::onFinished;
    }

    private boolean onFinished(PlayerEntity player) {
        if (!bellyObjective.checkBelly(player)) {
            return false;
        }
        for (CustomObtainItemObjective<Item> objective : collectObjectives) {
            if (!this.removeQuestItem(player, objective.getItemTarget().get(), objective.getItemsNeeded())) {
                return false;
            }
        }
        IEntityStats stats = EntityStatsCapability.get(player);
        stats.alterBelly(-bellyObjective.getCost(), StatChangeSource.QUEST);
        WyNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player.getId(), stats), player);

        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(StrongRightAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);

        return true;
    }
}
