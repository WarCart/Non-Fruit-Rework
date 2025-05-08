package net.warcar.non_fruit_rework.quest.rokushiki.advanced.rokuogan;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.AmaneDachiAbility;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.ShiganMode;
import net.warcar.non_fruit_rework.quest.objectives.TimedAbilityUseObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.RokuoganQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.geppo.KamisoriQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.kamie.ZanshinQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.AmaneDachiQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.ShiganOrenQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.soru.TekkaiDamaQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiGoQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class SaiDaiRinRokuoganQuest extends Quest {
    public static final QuestId<SaiDaiRinRokuoganQuest> INSTANCE = new QuestId.Builder<>("Trial: Sai Dai Rin Rokuogan", SaiDaiRinRokuoganQuest::new)
            .addRequirements(TekkaiDamaQuest.INSTANCE, TekkaiGoQuest.INSTANCE, KamisoriQuest.INSTANCE, ZanshinQuest.INSTANCE, ShiganOrenQuest.INSTANCE,
                    AmaneDachiQuest.INSTANCE, RokuoganQuest.INSTANCE).build();

    public SaiDaiRinRokuoganQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 9000);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Rokuogan", 150, SharedKillChecks.checkAbilitySource(RokuoganAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective1);
        Objective objective2 = new KillEntityObjective("Kill %s enemies using Rankyaku: Amane Dachi", 450, SharedKillChecks.checkAbilitySource(AmaneDachiAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective2);
        Objective objective3 = new TimedAbilityUseObjective("Use Shigan: Oren for %s seconds total", ShiganAbility.INSTANCE, 2400, SaiDaiRinRokuoganQuest::checkOren).addRequirement(objective);
        this.addObjective(objective3);
        this.addObjective(new KillEntityObjective("Kill %s enemies", 2000, (p, e, s) -> true).addRequirements(objective1, objective2, objective3, objective));
        this.onCompleteEvent = this::giveReward;
    }

    private static boolean checkOren(PlayerEntity player, IAbility ability) {
        return ability.getComponent(ModAbilityKeys.ALT_MODE).map(c -> c.getCurrentMode().equals(ShiganMode.OREN)).orElse(false);
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(RokuoganAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}