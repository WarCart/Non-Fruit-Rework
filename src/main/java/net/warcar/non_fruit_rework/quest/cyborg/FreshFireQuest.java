package net.warcar.non_fruit_rework.quest.cyborg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import net.warcar.non_fruit_rework.quest.objectives.ObtainBellyObjective;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.FreshFireAbility;
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

public class FreshFireQuest extends Quest {
    public static final QuestId<FreshFireQuest> INSTANCE = new QuestId.Builder<>("Fresh Fire", FreshFireQuest::new)
            .addRequirements(CyborgBodyQuest.INSTANCE).build();
    private final ObtainBellyObjective bellyObjective;
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public FreshFireQuest(QuestId core) {
        super(core);
        this.bellyObjective = new ObtainBellyObjective("Obtain %s belly", 10000);
        this.addObjective(bellyObjective);
        this.collectObjectives = new CustomObtainItemObjective[] {
                new CustomObtainItemObjective<>(1, Items.LAVA_BUCKET),
                new CustomObtainItemObjective<>(4, Items.BLAZE_ROD),
                new CustomObtainItemObjective<>(1, Items.FIRE_CHARGE),
                new CustomObtainItemObjective<>(8, Items.GOLD_INGOT)
        };
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
            } else if (objective.getItemTarget().get() == Items.LAVA_BUCKET) {
                player.addItem(new ItemStack(Items.BUCKET));
            }
        }

        IEntityStats stats = EntityStatsCapability.get(player);
        stats.alterBelly(-bellyObjective.getCost(), StatChangeSource.QUEST);
        WyNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player.getId(), stats), player);

        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(FreshFireAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);

        return true;
    }
}
