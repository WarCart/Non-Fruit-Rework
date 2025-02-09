package net.warcar.non_fruit_rework.quest.cyborg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaFuelAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CyborgPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class CyborgBodyQuest extends Quest {
    public static final QuestId<CyborgBodyQuest> INSTANCE = new QuestId.Builder<>("Cyborg Body", CyborgBodyQuest::new).build();
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public CyborgBodyQuest(QuestId core) {
        super(core);
        this.collectObjectives = new CustomObtainItemObjective[]{
                new CustomObtainItemObjective<>(1, () -> Items.IRON_CHESTPLATE),
                new CustomObtainItemObjective<>(1, () -> Items.IRON_LEGGINGS),
                new CustomObtainItemObjective<>(3, ModItems.ULTRA_COLA),
                new CustomObtainItemObjective<>(32, () -> Items.REDSTONE),
                new CustomObtainItemObjective<>(16, () -> Items.IRON_INGOT)};
        this.addObjectives(collectObjectives);
        this.onCompleteEvent = this::onFinished;
    }

    private boolean onFinished(PlayerEntity player) {
        for (CustomObtainItemObjective<Item> objective : collectObjectives) {
            if (!this.removeQuestItem(player, objective.getItemTarget().get(), objective.getItemsNeeded())) {
                return false;
            }
        }
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(CyborgPassiveBonusesAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        props.addUnlockedAbility(ColaFuelAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);

        return true;
    }
}
