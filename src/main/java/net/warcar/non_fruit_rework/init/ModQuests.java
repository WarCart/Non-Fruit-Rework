package net.warcar.non_fruit_rework.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
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
import net.warcar.non_fruit_rework.quest.genetic_materials.FishmanGenesQuest;
import net.warcar.non_fruit_rework.quest.genetic_materials.MinkGenesQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.*;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.geppo.KamisoriQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.kamie.ZanshinQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.AmaneDachiQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.RankyakuHakuraiQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.RankyakuRanQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rokuogan.SaiDaiRinRokuoganQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.JushiganQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.ShiganOrenQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.TobuShiganQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.soru.TekkaiDamaQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.*;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.ArrayList;
import java.util.List;

public class ModQuests {
    public static final DeferredRegister<QuestId<?>> QUESTS = DeferredRegister.create(ModRegistries.QUESTS, NonFruitReworkMod.MOD_ID);

    public static final List<QuestId<?>> CYBORG_QUESTS = new ArrayList<>();
    public static final List<QuestId<?>> GEN_MODIFICATION_QUESTS = new ArrayList<>();
    public static final List<QuestId<?>> ROKUSHIKI_QUESTS = new ArrayList<>();
    public static final List<QuestId<?>> TEKKAI_KENPO_QUESTS = new ArrayList<>();
    public static final List<QuestId<?>> FISHMAN_KARATE_GENERIC_QUESTS = new ArrayList<>();
    public static final List<QuestId<?>> FISHMAN_KARATE_RACIAL_QUESTS = new ArrayList<>();
    public static final List<QuestId<?>> ELECTRO_QUESTS = new ArrayList<>();

    public static void register(IEventBus bus) {
        QUESTS.register(bus);
        cyborgQuests();
        rokushikiQuests();
        fishmanQuests();
        geneticQuests();
        electroQuests();
    }

    private static void electroQuests() {
        registerQuest(ElectricalLunaQuest.INSTANCE, ELECTRO_QUESTS);
        registerQuest(ElectricalMissileQuest.INSTANCE, ELECTRO_QUESTS);
        registerQuest(ElectricalShowerQuest.INSTANCE, ELECTRO_QUESTS);
        registerQuest(ElectricalTempestaQuest.INSTANCE, ELECTRO_QUESTS);
    }

    private static void geneticQuests() {
        registerQuest(FishmanGenesQuest.INSTANCE, GEN_MODIFICATION_QUESTS);
        registerQuest(MinkGenesQuest.INSTANCE, GEN_MODIFICATION_QUESTS);
        //registerQuest(LunarianGenesQuest.INSTANCE, GEN_MODIFICATION_QUESTS);
    }

    private static void fishmanQuests() {
        //Generics
        registerQuest(KachiageHaisokuQuest.INSTANCE, FISHMAN_KARATE_GENERIC_QUESTS);
        registerQuest(KarakusagawaraSeikenQuest.INSTANCE, FISHMAN_KARATE_GENERIC_QUESTS);
        registerQuest(SamehadaShoteiQuest.INSTANCE, FISHMAN_KARATE_GENERIC_QUESTS);
        registerQuest(TwoFishEngineQuest.INSTANCE, FISHMAN_KARATE_GENERIC_QUESTS);

        //Racial
        registerQuest(MurasameQuest.INSTANCE, FISHMAN_KARATE_RACIAL_QUESTS);
        registerQuest(UchimizuQuest.INSTANCE, FISHMAN_KARATE_RACIAL_QUESTS);
        registerQuest(YarinamiQuest.INSTANCE, FISHMAN_KARATE_RACIAL_QUESTS);
    }

    private static void rokushikiQuests() {
        //Basic 7 techniques
        registerQuest(GeppoQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(KamieQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(RankyakuQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(RokuoganQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(ShiganQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(SoruQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(TekkaiQuest.INSTANCE, ROKUSHIKI_QUESTS);

        //Advanced
        registerQuest(KamisoriQuest.INSTANCE, null);
        registerQuest(TekkaiDamaQuest.INSTANCE, null);
        registerQuest(ZanshinQuest.INSTANCE, null);
        registerQuest(SaiDaiRinRokuoganQuest.INSTANCE, null);
        rankyakuPart();
        shiganPart();
        tekkaiPart();
    }

    private static void rankyakuPart() {
        registerQuest(RankyakuRanQuest.INSTANCE, null);
        registerQuest(RankyakuHakuraiQuest.INSTANCE, null);
        registerQuest(AmaneDachiQuest.INSTANCE, null);
    }

    private static void shiganPart() {
        registerQuest(TobuShiganQuest.INSTANCE, null);
        registerQuest(ShiganOrenQuest.INSTANCE, null);
        registerQuest(JushiganQuest.INSTANCE, null);
    }

    private static void tekkaiPart() {
        registerQuest(TekkaiGoQuest.INSTANCE, null);
        registerQuest(TekkaiUtsugiQuest.INSTANCE, null);
        //Kenpo
        registerQuest(TekkaiKenpoQuest.INSTANCE, TEKKAI_KENPO_QUESTS);
        registerQuest(OkamiHajikiQuest.INSTANCE, TEKKAI_KENPO_QUESTS);
        registerQuest(RokaruAreaNetworkQuest.INSTANCE, TEKKAI_KENPO_QUESTS);
    }

    private static void cyborgQuests() {
        registerQuest(CyborgBodyQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(FreshFireQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(HeavyArmorQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(PressurizedTanksQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(RadicalBeamQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(StrongRightQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(WeaponsLeftQuest.INSTANCE, CYBORG_QUESTS);
    }

    private static <Q extends Quest> void registerQuest(QuestId<Q> instance, List<QuestId<?>> group) {
        String resourceName = WyHelper.getResourceName(instance.getName());
        QUESTS.register(resourceName, () -> instance);
        LangHelper.registerLine(String.format("quest.%s.%s", ModMain.PROJECT_ID, resourceName), instance.getName());
        for (Objective objective : instance.createQuest().getObjectives()) {
            LangHelper.registerLine("quest.objective.mineminenomi." + objective.getId(), objective.getTitle());
        }
        if (group != null) {
            group.add(instance);
        }
    }
}
