package net.warcar.non_fruit_rework.init;

import com.google.common.collect.Lists;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.quest.cyborg.*;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

public class ModQuests {
    public static final DeferredRegister<QuestId<?>> QUESTS = DeferredRegister.create(ModRegistries.QUESTS, NonFruitReworkMod.MOD_ID);

    public static final List<QuestId> CYBORG_QUESTS = Lists.newArrayList();

    public static void register(IEventBus bus) {
        QUESTS.register(bus);
        LangHelper.registerLine("quest.objective.non_fruit_rework.collect_items", "Collect %s %s");
        registerQuest(CyborgBodyQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(FreshFireQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(HeavyArmorQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(PressurizedTanksQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(RadicalBeamQuest.INSTANCE, CYBORG_QUESTS);
        registerQuest(StrongRightQuest.INSTANCE, CYBORG_QUESTS);
    }

    private static <Q extends Quest> void registerQuest(QuestId<Q> instance, List<QuestId> group) {
        String resourceName = WyHelper.getResourceName(instance.getName());
        QUESTS.register(resourceName, () -> instance);
        LangHelper.registerLine(String.format("quest.%s.%s", ModMain.PROJECT_ID, resourceName), instance.getName());
        group.add(instance);
    }
}
