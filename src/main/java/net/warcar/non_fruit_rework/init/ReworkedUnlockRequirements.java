package net.warcar.non_fruit_rework.init;

import net.MrMagicalCart.cartaddon.abilities.cyborgextra.*;
import net.MrMagicalCart.cartaddon.abilities.electroextra.*;
import net.MrMagicalCart.cartaddon.abilities.fishmankarateextra.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.mixin.IAbilityCoreMixin;
import net.warcar.non_fruit_rework.quest.cyborg.*;
import net.warcar.non_fruit_rework.quest.electro.ElectricalLunaQuest;
import net.warcar.non_fruit_rework.quest.electro.ElectricalMissileQuest;
import net.warcar.non_fruit_rework.quest.electro.ElectricalShowerQuest;
import net.warcar.non_fruit_rework.quest.electro.ElectricalTempestaQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.generic.KachiageHaisokuQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.generic.KarakusagawaraSeikenQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.generic.SamehadaShoteiQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.generic.TwoFishEngineQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.racial.MurasameQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.racial.UchimizuQuest;
import net.warcar.non_fruit_rework.quest.fishman_karate.racial.YarinamiQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.*;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.*;
import xyz.pixelatedw.mineminenomi.abilities.electro.*;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.*;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public class ReworkedUnlockRequirements {
    public static void init() {
        cyborgAbilities();
        humanAbilities();
        fishmanAbilities();
        minkAbilities();
        if (ModList.get().isLoaded("cartaddon")) {
            cartAbilities();
        }
    }

    private static void cartAbilities() {
        cartCyborg();
        cartMink();
        cartFishman();
    }

    private static void cartCyborg() {
        setReqs(CartStrongRightAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(StrongRightQuest.INSTANCE)));
        setReqs(ReworkedFreshFireAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(FreshFireQuest.INSTANCE)));
        setReqs(CartCoupDeBooAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(PressurizedTanksQuest.INSTANCE)));
        setReqs(ReworkedCoupDeVentAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(PressurizedTanksQuest.INSTANCE)));
        setReqs(CartRadicalBeamAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(RadicalBeamQuest.INSTANCE)));
        setReqs(ReworkedColaOverdriveAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
        setReqs(CartSouthlandSuplexAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
        setReqs(IronBoxingAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
        setReqs(WeaponsLeftAbility.INSTANCE, alwaysFalse());
        setReqs(GeneralFrankyAbility.INSTANCE, isTrueRace(ModValues.CYBORG).and(doriki(3000))
                .or(QuestHelper.questFinished(GeneralFrankyQuest.INSTANCE)));
    }

    private static void cartMink() {
        addReqs(CartEleclawAbility.INSTANCE, entity -> EntityHelper.isHybridRace(entity, ModValues.MINK));
        addReqs(CartElectricalLunaAbility.INSTANCE, QuestHelper.questFinished(ElectricalLunaQuest.INSTANCE));
        addReqs(CartElectricalMissileAbility.INSTANCE, QuestHelper.questFinished(ElectricalMissileQuest.INSTANCE));
        addReqs(CartElectricalShowerAbility.INSTANCE, QuestHelper.questFinished(ElectricalShowerQuest.INSTANCE));
        addReqs(CartElectricalTempestaAbility.INSTANCE, QuestHelper.questFinished(ElectricalTempestaQuest.INSTANCE));
    }

    private static void cartFishman() {
        addReqs(ReworkedKachiageHaisokuAbility.INSTANCE, QuestHelper.questFinished(KachiageHaisokuQuest.INSTANCE));
        addReqs(ReworkedKarakusagawaraSeikenAbility.INSTANCE, QuestHelper.questFinished(KarakusagawaraSeikenQuest.INSTANCE));
        addReqs(ReworkedSamehadaShoteiAbility.INSTANCE, QuestHelper.questFinished(SamehadaShoteiQuest.INSTANCE));
        addReqs(ReworkedTwoFishEngineAbility.INSTANCE, QuestHelper.questFinished(TwoFishEngineQuest.INSTANCE));

        addReqs(ReworkedMurasameAbility.INSTANCE, QuestHelper.questFinished(MurasameQuest.INSTANCE));
        addReqs(ReworkedUchimizuAbility.INSTANCE, QuestHelper.questFinished(UchimizuQuest.INSTANCE));
        addReqs(ReworkedYarinamiAbility.INSTANCE, QuestHelper.questFinished(YarinamiQuest.INSTANCE));
        //TODO: still some moves left to cover
    }

    private static void minkAbilities() {
        addReqs(EleclawAbility.INSTANCE, entity -> EntityHelper.isHybridRace(entity, ModValues.MINK));
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

    private static void humanAbilities() {
        addReqs(GeppoAbility.INSTANCE, QuestHelper.questFinished(GeppoQuest.INSTANCE));
        addReqs(KamieAbility.INSTANCE, QuestHelper.questFinished(KamieQuest.INSTANCE));
        addReqs(RankyakuAbility.INSTANCE, QuestHelper.questFinished(RankyakuQuest.INSTANCE));
        addReqs(RokuoganAbility.INSTANCE, QuestHelper.questFinished(RokuoganQuest.INSTANCE));
        addReqs(ShiganAbility.INSTANCE, QuestHelper.questFinished(ShiganQuest.INSTANCE));
        addReqs(SoruAbility.INSTANCE, QuestHelper.questFinished(SoruQuest.INSTANCE));
        addReqs(TekkaiAbility.INSTANCE, QuestHelper.questFinished(TekkaiQuest.INSTANCE));
    }

    private static void cyborgAbilities() {
        setReqs(StrongRightAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(StrongRightQuest.INSTANCE)));
        setReqs(FreshFireAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(FreshFireQuest.INSTANCE)));
        setReqs(CoupDeBooAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(PressurizedTanksQuest.INSTANCE)));
        setReqs(CoupDeVentAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(PressurizedTanksQuest.INSTANCE)));
        setReqs(RadicalBeamAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(RadicalBeamQuest.INSTANCE)));
        setReqs(ColaOverdriveAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
        setReqs(SouthlandSuplexAbility.INSTANCE, isTrueRace(ModValues.CYBORG).or(QuestHelper.questFinished(HeavyArmorQuest.INSTANCE)));
    }

    private static void setReqs(AbilityCore<?> core, AbilityCore.ICanUnlock check) {
        ((IAbilityCoreMixin) core).setUnlockCheck(check);
    }

    private static void addReqs(AbilityCore<?> core, AbilityCore.ICanUnlock check) {
        if (CommonConfig.INSTANCE.isFullQuest()) {
            setReqs(core, check);
        } else {
            ((IAbilityCoreMixin) core).setUnlockCheck(check.or(((IAbilityCoreMixin) core).getUnlockCheck()));
        }
    }

    private static AbilityCore.ICanUnlock isTrueRace(ResourceLocation race) {
        return entity -> EntityHelper.isTrueRace(entity, race);
    }

    private static AbilityCore.ICanUnlock doriki(int doriki) {
        return e -> {
            IEntityStats stats = EntityStatsCapability.get(e);
            return stats.getDoriki() >= doriki;
        };
    }

    private static AbilityCore.ICanUnlock alwaysFalse() {
        return e -> false;
    }
}
