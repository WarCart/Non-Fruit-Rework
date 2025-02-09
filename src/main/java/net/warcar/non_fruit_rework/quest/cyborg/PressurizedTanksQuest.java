package net.warcar.non_fruit_rework.quest.cyborg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.ColaFuelAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeBooAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CoupDeVentAbility;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CyborgPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class PressurizedTanksQuest extends Quest {
    public static final QuestId<PressurizedTanksQuest> INSTANCE = new QuestId.Builder<>("Pressurized Tanks", PressurizedTanksQuest::new)
            .addRequirements(CyborgBodyQuest.INSTANCE).build();
    private final CustomObtainItemObjective<Item>[] collectObjectives;

    public PressurizedTanksQuest(QuestId core) {
        super(core);
        this.collectObjectives = new CustomObtainItemObjective[] {
                new CustomObtainItemObjective<>(5, ModItems.EMPTY_ULTRA_COLA),
                new CustomObtainItemObjective<>(16, () -> Items.REDSTONE),
                new CustomObtainItemObjective<>(8, () -> Items.IRON_INGOT)};
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

        props.addUnlockedAbility(CoupDeBooAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        props.addUnlockedAbility(CoupDeVentAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);

        return true;
    }
}
