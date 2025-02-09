package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.mixin.IAbilityCoreMixin;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.cyborg.*;
import net.warcar.non_fruit_rework.quest.rokushiki.*;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.*;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;

public class ReworkedUnlockRequirements {
    public static void init() {
        cyborgAbilities();
        rokushikiAbilities();
    }

    private static void rokushikiAbilities() {
        setReqs(GeppoAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(GeppoQuest.INSTANCE)));
        setReqs(KamieAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(KamieQuest.INSTANCE)));
        setReqs(RankyakuAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(RankyakuQuest.INSTANCE)));
        setReqs(RokuoganAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(RokuoganQuest.INSTANCE)));
        setReqs(ShiganAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(ShiganQuest.INSTANCE)));
        setReqs(SoruAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(SoruQuest.INSTANCE)));
        setReqs(TekkaiAbility.INSTANCE, isTrueRace("human").or(QuestHelper.questFinnished(TekkaiQuest.INSTANCE)));
    }

    private static void cyborgAbilities() {
        setReqs(StrongRightAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(StrongRightQuest.INSTANCE)));
        setReqs(FreshFireAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(FreshFireQuest.INSTANCE)));
        setReqs(CoupDeBooAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(PressurizedTanksQuest.INSTANCE)));
        setReqs(CoupDeVentAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(PressurizedTanksQuest.INSTANCE)));
        setReqs(RadicalBeamAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(RadicalBeamQuest.INSTANCE)));
        setReqs(ColaOverdriveAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(HeavyArmorQuest.INSTANCE)));
        setReqs(SouthlandSuplexAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinnished(HeavyArmorQuest.INSTANCE)));
    }

    private static void setReqs(AbilityCore<?> core, AbilityCore.ICanUnlock check) {
        ((IAbilityCoreMixin) core).setUnlockCheck(check);
    }

    private static AbilityCore.ICanUnlock isTrueRace(String race) {
        return entity -> EntityStatsCapability.get(entity).getRace().equalsIgnoreCase(race);
    }
}
