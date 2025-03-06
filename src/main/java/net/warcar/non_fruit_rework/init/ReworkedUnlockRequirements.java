package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.mixin.IAbilityCoreMixin;
import net.warcar.non_fruit_rework.quest.cyborg.*;
import net.warcar.non_fruit_rework.quest.electro.*;
import net.warcar.non_fruit_rework.quest.fishman_karate.generic.*;
import net.warcar.non_fruit_rework.quest.fishman_karate.racial.*;
import net.warcar.non_fruit_rework.quest.rokushiki.*;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.*;
import xyz.pixelatedw.mineminenomi.abilities.electro.*;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.*;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class ReworkedUnlockRequirements {
    public static void init() {
        cyborgAbilities();
        rokushikiAbilities();
        fishmanAbilities();
        minkAbilities();
    }

    private static void minkAbilities() {
        addReqs(EleclawAbility.INSTANCE, entity -> QuestHelper.isHybridRace(entity, "mink"));
        addReqs(ElectricalLunaAbility.INSTANCE, QuestHelper.questFinished(ElectricalLunaQuest.INSTANCE));
        addReqs(ElectricalMissileAbility.INSTANCE, QuestHelper.questFinished(ElectricalMissileQuest.INSTANCE));
        addReqs(ElectricalShowerAbility.INSTANCE, QuestHelper.questFinished(ElectricalShowerQuest.INSTANCE));
        addReqs(ElectricalTempestaAbility.INSTANCE, QuestHelper.questFinished(ElectricalTempestaQuest.INSTANCE));
    }

    private static void fishmanAbilities() {
        addReqs(KachiageHaisokuAbility.INSTANCE, QuestHelper.questFinished(KachiageHaisokuQuest.INSTANCE));
        addReqs(KarakusagawaraSeikenAbility.INSTANCE, QuestHelper.questFinished(KarakusagawaraSeikenQuest.INSTANCE));
        addReqs(SamehadaShoteiAbility.INSTANCE, QuestHelper.questFinished(SamehadaShoteiQuest.INSTANCE));
        addReqs(TwoFishEngineAbility.INSTANCE, QuestHelper.questFinished(TwoFishEngineQuest.INSTANCE));

        addReqs(MurasameAbility.INSTANCE, QuestHelper.questFinished(MurasameQuest.INSTANCE));
        addReqs(UchimizuAbility.INSTANCE, QuestHelper.questFinished(UchimizuQuest.INSTANCE));
        addReqs(YarinamiAbility.INSTANCE, QuestHelper.questFinished(YarinamiQuest.INSTANCE));
    }

    private static void rokushikiAbilities() {
        addReqs(GeppoAbility.INSTANCE, QuestHelper.questFinished(GeppoQuest.INSTANCE));
        addReqs(KamieAbility.INSTANCE, QuestHelper.questFinished(KamieQuest.INSTANCE));
        addReqs(RankyakuAbility.INSTANCE, QuestHelper.questFinished(RankyakuQuest.INSTANCE));
        addReqs(RokuoganAbility.INSTANCE, QuestHelper.questFinished(RokuoganQuest.INSTANCE));
        addReqs(ShiganAbility.INSTANCE, QuestHelper.questFinished(ShiganQuest.INSTANCE));
        addReqs(SoruAbility.INSTANCE, QuestHelper.questFinished(SoruQuest.INSTANCE));
        addReqs(TekkaiAbility.INSTANCE, QuestHelper.questFinished(TekkaiQuest.INSTANCE));
    }

    private static void cyborgAbilities() {
        setReqs(StrongRightAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(StrongRightQuest.INSTANCE)));
        setReqs(FreshFireAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(FreshFireQuest.INSTANCE)));
        setReqs(CoupDeBooAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(PressurizedTanksQuest.INSTANCE)));
        setReqs(CoupDeVentAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(PressurizedTanksQuest.INSTANCE)));
        setReqs(RadicalBeamAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(RadicalBeamQuest.INSTANCE)));
        setReqs(ColaOverdriveAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
        setReqs(SouthlandSuplexAbility.INSTANCE, isTrueRace("cyborg").or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
    }

    private static void setReqs(AbilityCore<?> core, AbilityCore.ICanUnlock check) {
        ((IAbilityCoreMixin) core).setUnlockCheck(check);
    }

    private static void addReqs(AbilityCore<?> core, AbilityCore.ICanUnlock check) {
        ((IAbilityCoreMixin) core).setUnlockCheck(check.or(((IAbilityCoreMixin) core).getUnlockCheck()));
    }

    private static AbilityCore.ICanUnlock isTrueRace(String race) {
        return entity -> QuestHelper.isTrueRace(entity, race);
    }
}
