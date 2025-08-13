package net.warcar.non_fruit_rework.quest.cyborg;

import net.MrMagicalCart.cartaddon.abilities.cyborgextra.GeneralFrankyAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.ModList;
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
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class GeneralFrankyQuest extends Quest {
    public static final QuestId<GeneralFrankyQuest> INSTANCE = new QuestId.Builder<>("General Franky", GeneralFrankyQuest::new)
            .addRequirements(CyborgBodyQuest.INSTANCE).build();
    private final ObtainBellyObjective bellyObjective;
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public GeneralFrankyQuest(QuestId core) {
        super(core);
        if (ModList.get().isLoaded("cartaddon")) {

            this.bellyObjective = new ObtainBellyObjective("Obtain %s belly", 40000);
            this.addObjective(bellyObjective);
            this.collectObjectives = new CustomObtainItemObjective[] {
                    new CustomObtainItemObjective<>(64, Items.IRON_BLOCK),
                    new CustomObtainItemObjective<>(3, Items.NETHERITE_BLOCK),
                    new CustomObtainItemObjective<>(16, ModItems.ULTRA_COLA),
                    new CustomObtainItemObjective<>(32, Items.REDSTONE_BLOCK),
                    new CustomObtainItemObjective<>(8, Items.LAPIS_BLOCK),
                    new CustomObtainItemObjective<>(16, Items.GOLD_BLOCK),
                    new CustomObtainItemObjective<>(64, Items.GLASS)
            };
            for (CustomObtainItemObjective<Item> objective : collectObjectives) {
                objective.addRequirement(bellyObjective);
            }
            this.addObjectives(collectObjectives);
            this.onCompleteEvent = this::onFinished;
        } else {
            bellyObjective = null;
            collectObjectives = new CustomObtainItemObjective[0];
        }
    }

    private boolean onFinished(PlayerEntity player) {
        if (!ModList.get().isLoaded("cartaddon")) {
            return true;
        }
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
        props.addUnlockedAbility(GeneralFrankyAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
