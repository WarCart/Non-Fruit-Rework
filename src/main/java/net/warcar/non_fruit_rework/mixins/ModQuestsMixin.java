package net.warcar.non_fruit_rework.mixins;

import net.warcar.non_fruit_rework.quests.swordsman.NineSwordedStileQuest;
import net.warcar.non_fruit_rework.quests.swordsman.SanzenSekaiQuest;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.init.ModQuests;

import java.util.Arrays;
import java.util.List;

@Mixin(ModQuests.class)
public class ModQuestsMixin {
    @Shadow
    @Final
    public static final List<QuestId> SWORDSMAN_TRIALS = Arrays.asList(ModQuests.SWORDSMAN_TRIAL_01, ModQuests.SWORDSMAN_TRIAL_02,
            ModQuests.SWORDSMAN_TRIAL_03, ModQuests.SWORDSMAN_TRIAL_04, NineSwordedStileQuest.INSTANCE, ModQuests.SWORDSMAN_TRIAL_05,
            SanzenSekaiQuest.INSTANCE);
}
