package net.warcar.non_fruit_rework.init;

import com.google.common.collect.Lists;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.quest.cyborg.*;
import net.warcar.non_fruit_rework.quest.fishman_karate.*;
import net.warcar.non_fruit_rework.quest.genetic_materials.*;
import net.warcar.non_fruit_rework.quest.rokushiki.*;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

public class ModQuests {
    public static final DeferredRegister<QuestId<?>> QUESTS = DeferredRegister.create(ModRegistries.QUESTS, NonFruitReworkMod.MOD_ID);

    public static final List<QuestId<?>> CYBORG_QUESTS = Lists.newArrayList();
    public static final List<QuestId<?>> GEN_MODIFICATION_QUESTS = Lists.newArrayList();
    public static final List<QuestId<?>> ROKUSHIKI_QUESTS = Lists.newArrayList();
    public static final List<QuestId<?>> FISHMAN_KARATE_QUESTS = Lists.newArrayList();

    public static void register(IEventBus bus) {
        QUESTS.register(bus);
        cyborgQuests();
        rokushikiQuests();
        fishmanQuests();
        geneticQuests();
    }

    private static void geneticQuests() {
        registerQuest(FishmanGenesQuest.INSTANCE, GEN_MODIFICATION_QUESTS);
        registerQuest(LunarianGenesQuest.INSTANCE, GEN_MODIFICATION_QUESTS);
    }

    private static void fishmanQuests() {
        registerQuest(KachiageHaisokuQuest.INSTANCE, FISHMAN_KARATE_QUESTS);
        registerQuest(KarakusagawaraSeikenQuest.INSTANCE, FISHMAN_KARATE_QUESTS);
        registerQuest(SamehadaShoteiQuest.INSTANCE, FISHMAN_KARATE_QUESTS);
        registerQuest(TwoFishEngineQuest.INSTANCE, FISHMAN_KARATE_QUESTS);
    }

    private static void rokushikiQuests() {
        registerQuest(GeppoQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(KamieQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(RankyakuQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(RokuoganQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(ShiganQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(SoruQuest.INSTANCE, ROKUSHIKI_QUESTS);
        registerQuest(TekkaiQuest.INSTANCE, ROKUSHIKI_QUESTS);
    }

    private static void cyborgQuests() {
        registerQuest(CyborgBodyQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(FreshFireQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(HeavyArmorQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(PressurizedTanksQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(RadicalBeamQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(StrongRightQuest.INSTANCE, CYBORG_QUESTS);
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
