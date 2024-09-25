package net.warcar.non_fruit_rework.quests.rokushiki.advanced;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.quests.rokushiki.standart.RankyakuQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.UseAbilityObjective;

public class RankyakuRanQuest extends Quest {
    public static final QuestId<RankyakuRanQuest> INSTANCE = new QuestId.Builder<>("Trial: Rankyaku ran", RankyakuRanQuest::new).addRequirements(RankyakuQuest.INSTANCE).build();
    public RankyakuRanQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 2000);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies with slash attacks", 50, (playerEntity, livingEntity, damageSource) -> damageSource instanceof ModDamageSource && ((ModDamageSource) damageSource).isSlash()).addRequirement(objective));
        this.addObjective(new UseAbilityObjective("Use Geppo %s times", 15, GeppoAbility.INSTANCE).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(RankyakuAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
