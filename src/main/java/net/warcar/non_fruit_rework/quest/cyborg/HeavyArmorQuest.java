package net.warcar.non_fruit_rework.quest.cyborg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.abilities.cyborg.CyborgHeavyPlatingAbility;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaFuelAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaOverdriveAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CyborgPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.SouthlandSuplexAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class HeavyArmorQuest extends Quest {
    public static final QuestId<HeavyArmorQuest> INSTANCE = new QuestId.Builder<>("Heavy Armor", HeavyArmorQuest::new)
            .addRequirements(CyborgBodyQuest.INSTANCE).build();
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public HeavyArmorQuest(QuestId core) {
        super(core);
        this.collectObjectives = new CustomObtainItemObjective[] {
                new CustomObtainItemObjective<>(1, () -> Items.NETHERITE_CHESTPLATE),
                new CustomObtainItemObjective<>(1, () -> Items.NETHERITE_LEGGINGS),
                new CustomObtainItemObjective<>(128, () -> Items.REDSTONE),
                new CustomObtainItemObjective<>(9, () -> Items.DIAMOND),
                new CustomObtainItemObjective<>(2, () -> Items.NETHERITE_INGOT)};
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

        props.addUnlockedAbility(CyborgHeavyPlatingAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        props.addUnlockedAbility(SouthlandSuplexAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        props.addUnlockedAbility(ColaOverdriveAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);

        return true;
    }
}
