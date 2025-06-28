package net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.tekkai_kenpo.OkamiHajikiAbility;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.tekkai_kenpo.RokaruAreaNetworkAbility;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedKillEntityObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class RokaruAreaNetworkQuest extends Quest implements IHasRequirements {
    public static final QuestId<RokaruAreaNetworkQuest> INSTANCE = new QuestId.Builder<>("Tekkai Kenpo Trial: Rokaru Area Network", RokaruAreaNetworkQuest::new)
            .addRequirements(TekkaiKenpoQuest.INSTANCE).build();

    public RokaruAreaNetworkQuest(QuestId core) {
        super(core);
        this.addObjective(new CustomUseAbilityObjective(35, SoruAbility.INSTANCE));
        this.addObjective(new CustomUseAbilityObjective(10, ShiganAbility.INSTANCE));
        this.addObjective(new CustomUseAbilityObjective(3, OkamiHajikiAbility.INSTANCE));
        this.addObjective(new KillEntityObjective("Kill %s enemies", 20));
        this.addObjective(new TimedKillEntityObjective("Kill %s enemies in less than %s seconds", 4, 10));
        this.onCompleteEvent = this::giveReward;
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(RokaruAreaNetworkAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
