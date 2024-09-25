package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.*;

public class GeppoQuest extends Quest {
    public static final QuestId<GeppoQuest> INSTANCE = new QuestId.Builder<>("Trial: Geppo", GeppoQuest::new).build();
    public static final QuestId<GeppoQuest> DIFFICULT = new QuestId.Builder<>("Trial: Geppo difficult", GeppoQuest::difficult).build();
    
    public GeppoQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 510);
        this.addObjective(objective);
        this.addObjective(new ObtainItemObjective<>("Collect %s rabbit feet", 1, () -> Items.RABBIT_FOOT).addRequirement(objective));
        this.addObjective(new TimedSurvivalObjective("Survive for %s seconds without getting hit", 300).addRequirement(objective));
        this.addObjective(new UseAbilityObjective("Use Soru %s times", 5, SoruAbility.INSTANCE).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public static GeppoQuest difficult(QuestId core) {
        GeppoQuest quest = new GeppoQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 610);
        quest.addObjective(objective);
        quest.addObjective(new ObtainItemObjective<>("Collect %s rabbit feet", 2, () -> Items.RABBIT_FOOT).addRequirement(objective));
        quest.addObjective(new TimedSurvivalObjective("Survive for %s seconds without getting hit", 360).addRequirement(objective));
        quest.addObjective(new UseAbilityObjective("Use Soru %s times", 6, SoruAbility.INSTANCE).addRequirement(objective));
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(GeppoAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
