package net.warcar.non_fruit_rework.quest.cyborg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.abilities.cyborg.WeaponsLeftAbility;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import net.warcar.non_fruit_rework.quest.objectives.ObtainBellyObjective;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class WeaponsLeftQuest extends Quest {
    public static final QuestId<WeaponsLeftQuest> INSTANCE = new QuestId.Builder<>("Weapons left", WeaponsLeftQuest::new)
            .addRequirements(CyborgBodyQuest.INSTANCE).build();
    private final ObtainBellyObjective bellyObjective;
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public WeaponsLeftQuest(QuestId core) {
        super(core);
        this.bellyObjective = new ObtainBellyObjective("Obtain %s belly", 10000);
        this.addObjective(bellyObjective);
        this.collectObjectives = new CustomObtainItemObjective[] {
                new CustomObtainItemObjective<>(64, Items.GUNPOWDER),
                new CustomObtainItemObjective<>(18, Items.IRON_INGOT),
                new CustomObtainItemObjective<>(1, ModWeapons.BAZOOKA),
                new CustomObtainItemObjective<>(64, ModItems.CANNON_BALL),
                new CustomObtainItemObjective<>(64, ModItems.BULLET),
                new CustomObtainItemObjective<>(5, ModWeapons.SENRIKU),
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
            }
        }
        IEntityStats stats = EntityStatsCapability.get(player);
        stats.alterBelly(-bellyObjective.getCost(), StatChangeSource.QUEST);
        WyNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player.getId(), stats), player);

        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(WeaponsLeftAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);

        return true;
    }
}
