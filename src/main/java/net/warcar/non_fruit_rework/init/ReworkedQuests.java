package net.warcar.non_fruit_rework.init;

import com.google.common.collect.Lists;
import net.warcar.non_fruit_rework.quests.rokushiki.advanced.ShiganOrenQuest;
import net.warcar.non_fruit_rework.quests.rokushiki.standart.*;
import net.warcar.non_fruit_rework.quests.swordsman.NineSwordedStileQuest;
import net.warcar.non_fruit_rework.quests.swordsman.SanzenSekaiQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.List;

public class ReworkedQuests {
    public static final QuestId THREE_SWORD_STILE_TRIAL_01 = WyRegistry.registerQuest(NineSwordedStileQuest.INSTANCE);
    public static final QuestId THREE_SWORD_STILE_TRIAL_02 = WyRegistry.registerQuest(SanzenSekaiQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_01 = WyRegistry.registerQuest(SoruQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_02 = WyRegistry.registerQuest(TekkaiQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_03 = WyRegistry.registerQuest(GeppoQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_04 = WyRegistry.registerQuest(KamieQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_05 = WyRegistry.registerQuest(ShiganQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_06 = WyRegistry.registerQuest(RankyakuQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_07 = WyRegistry.registerQuest(RokuoganQuest.INSTANCE);
    public static final QuestId ROKUSHIKI_TRIAL_51 = WyRegistry.registerQuest(ShiganOrenQuest.INSTANCE);
    public static final List<QuestId> ROKUSHIKI_QUESTS = Lists.newArrayList(ROKUSHIKI_TRIAL_01, ROKUSHIKI_TRIAL_02, ROKUSHIKI_TRIAL_03, ROKUSHIKI_TRIAL_04, ROKUSHIKI_TRIAL_05, ROKUSHIKI_TRIAL_06, ROKUSHIKI_TRIAL_07);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_01 = WyRegistry.registerQuest(SoruQuest.DIFFICULT);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_02 = WyRegistry.registerQuest(TekkaiQuest.DIFFICULT);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_03 = WyRegistry.registerQuest(GeppoQuest.DIFFICULT);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_04 = WyRegistry.registerQuest(KamieQuest.DIFFICULT);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_05 = WyRegistry.registerQuest(ShiganQuest.DIFFICULT);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_06 = WyRegistry.registerQuest(RankyakuQuest.DIFFICULT);
    public static final QuestId DIFFICULT_ROKUSHIKI_TRIAL_07 = WyRegistry.registerQuest(RokuoganQuest.DIFFICULT);
    public static final List<QuestId> DIFFICULT_ROKUSHIKI_QUESTS = Lists.newArrayList(DIFFICULT_ROKUSHIKI_TRIAL_01, DIFFICULT_ROKUSHIKI_TRIAL_02,
            DIFFICULT_ROKUSHIKI_TRIAL_03, DIFFICULT_ROKUSHIKI_TRIAL_04, DIFFICULT_ROKUSHIKI_TRIAL_05, DIFFICULT_ROKUSHIKI_TRIAL_06, DIFFICULT_ROKUSHIKI_TRIAL_07);

    public static void init() {
    }
}
