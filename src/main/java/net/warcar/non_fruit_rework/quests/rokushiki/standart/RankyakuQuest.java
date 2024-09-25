package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.*;

public class RankyakuQuest extends Quest {
    public static final QuestId<RankyakuQuest> INSTANCE = new QuestId.Builder<>("Trial: Rankyaku", RankyakuQuest::new).build();
    public static final QuestId<RankyakuQuest> DIFFICULT = new QuestId.Builder<>("Trial: Rankyaku difficult", RankyakuQuest::difficult).build();

    public RankyakuQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 575);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies", 50).addRequirement(objective));
        this.addObjective(new UseAbilityObjective("Use Geppo %s times", 15, GeppoAbility.INSTANCE).addRequirement(objective));
        this.onStartEvent = this::giveReward;
    }

    public static RankyakuQuest difficult(QuestId core) {
        RankyakuQuest quest = new RankyakuQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 700);
        quest.addObjective(objective);
        quest.addObjective(new KillEntityObjective("Kill %s enemies", 60).addRequirement(objective));
        quest.addObjective(new UseAbilityObjective("Use Geppo %s times", 20, GeppoAbility.INSTANCE).addRequirement(objective));
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(RankyakuAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
