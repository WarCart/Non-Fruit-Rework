package net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.AmaneDachiAbility;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.RankyakuMode;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
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
import xyz.pixelatedw.mineminenomi.quests.objectives.UseAbilityObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class AmaneDachiQuest extends Quest implements IHasRequirements {
    public static final QuestId<AmaneDachiQuest> INSTANCE = new QuestId.Builder<>("Trial: Amane Dachi", AmaneDachiQuest::new)
            .addRequirements(RankyakuHakuraiQuest.INSTANCE).build();

    public AmaneDachiQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 5000);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Rankyaku", 75, SharedKillChecks.checkAbilitySource(RankyakuAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective1);
        Objective objective2 = new UseAbilityObjective("Use Geppo %s times", 100, GeppoAbility.INSTANCE).addRequirement(objective);
        this.addObjective(objective2);
        Objective objective3 = new UseAbilityObjective("Use Rankyaku Hakurai %s times", 35, AmaneDachiQuest::chechAbility).addRequirement(objective);
        this.addObjective(objective3);
        this.addObjective(new KillEntityObjective("Kill %s enemies", 200, (playerEntity, livingEntity, damageSource) -> true).addRequirements(objective, objective1, objective2, objective3));
    }

    private static boolean chechAbility(PlayerEntity playerEntity, IAbility iAbility) {
        if (iAbility instanceof RankyakuAbility) {
            return iAbility.getComponent(ModAbilityKeys.ALT_MODE).map(c -> c.getCurrentMode().equals(RankyakuMode.HAKURAI)).orElse(false);
        }
        return false;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(AmaneDachiAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }
}