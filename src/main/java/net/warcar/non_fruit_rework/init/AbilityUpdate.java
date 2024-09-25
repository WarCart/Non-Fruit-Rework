package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.enums.Style;
import net.warcar.non_fruit_rework.mixins.ccr.AbilityCoreMixin;
import net.warcar.non_fruit_rework.quests.QuestProgression;
import net.warcar.non_fruit_rework.quests.rokushiki.standart.*;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.*;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.*;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.*;
import xyz.pixelatedw.mineminenomi.abilities.brawler.*;
import xyz.pixelatedw.mineminenomi.abilities.doctor.*;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.abilities.sniper.*;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.*;
import xyz.pixelatedw.mineminenomi.init.ModQuests;

public class AbilityUpdate {
    public static void init() {
        if (ModConfig.INSTANCE.getQuestRaceProgression()) {
            raceUpdate();
        }
        styleUpdate();
    }

    public static void raceUpdate() {
        ((AbilityCoreMixin) SoruAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(SoruQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(SoruQuest.DIFFICULT, null)));
        ((AbilityCoreMixin) TekkaiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(TekkaiQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(TekkaiQuest.DIFFICULT, null)));
        ((AbilityCoreMixin) ShiganAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ShiganQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(ShiganQuest.DIFFICULT, null)));
        ((AbilityCoreMixin) RankyakuAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(RankyakuQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(RankyakuQuest.DIFFICULT, null)));
        ((AbilityCoreMixin) KamieAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(KamieQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(KamieQuest.DIFFICULT, null)));
        ((AbilityCoreMixin) GeppoAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(GeppoQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(GeppoQuest.DIFFICULT, null)));
        ((AbilityCoreMixin) RokuoganAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(RokuoganQuest.INSTANCE, null).or(QuestProgression.hasFinishedQuest(RokuoganQuest.DIFFICULT, null)));
    }

    public static void styleUpdate() {
        ((AbilityCoreMixin) ShiShishiSonsonAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SWORDSMAN_TRIAL_01, Style.Swordsman));
        ((AbilityCoreMixin) YakkodoriAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SWORDSMAN_TRIAL_02, Style.Swordsman));
        ((AbilityCoreMixin) SanbyakurokujuPoundHoAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SWORDSMAN_TRIAL_03, Style.Swordsman));
        ((AbilityCoreMixin) OTatsumakiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SWORDSMAN_TRIAL_04, Style.Swordsman));
        ((AbilityCoreMixin) HiryuKaenAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SWORDSMAN_TRIAL_05, Style.Swordsman));

        ((AbilityCoreMixin) HiNoToriBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_01, Style.Sniper));
        ((AbilityCoreMixin) KemuriBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_02, Style.Sniper));
        ((AbilityCoreMixin) TokuyoAburaBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_03, Style.Sniper));
        ((AbilityCoreMixin) TetsuBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_03, Style.Sniper));
        ((AbilityCoreMixin) SakuretsuSabotenBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_04, Style.Sniper));
        ((AbilityCoreMixin) NemuriBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_05, Style.Sniper));
        ((AbilityCoreMixin) RenpatsuNamariBoshiAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.SNIPER_TRIAL_06, Style.Sniper));

        ((AbilityCoreMixin) FirstAidAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.DOCTOR_TRIAL_01, Style.Doctor));
        ((AbilityCoreMixin) FailedExperimentAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.DOCTOR_TRIAL_02, Style.Doctor));
        ((AbilityCoreMixin) MedicBagExplosionAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.DOCTOR_TRIAL_03, Style.Doctor));
        ((AbilityCoreMixin) DopingAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.DOCTOR_TRIAL_03, Style.Doctor));
        ((AbilityCoreMixin) AntidoteShotAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.DOCTOR_TRIAL_04, Style.Doctor));
        ((AbilityCoreMixin) VirusZoneAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.DOCTOR_TRIAL_04, Style.Doctor));

        ((AbilityCoreMixin) ConcasseAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_01, Style.Black_Leg));
        ((AbilityCoreMixin) ExtraHachisAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_02, Style.Black_Leg));
        ((AbilityCoreMixin) AntiMannerKickCourseAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_03, Style.Black_Leg));
        ((AbilityCoreMixin) PartyTableKickCourseAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_03, Style.Black_Leg));
        ((AbilityCoreMixin) SkywalkAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_04, Style.Black_Leg));
        ((AbilityCoreMixin) DiableJambeAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_05, Style.Black_Leg));
        ((AbilityCoreMixin) BienCuitGrillShotAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BLACK_LEG_TRIAL_05, Style.Black_Leg));

        ((AbilityCoreMixin) SuplexAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BRAWLER_TRIAL_01, Style.Brawler));
        ((AbilityCoreMixin) SpinningBrawlAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BRAWLER_TRIAL_02, Style.Brawler));
        ((AbilityCoreMixin) GenkotsuMeteorAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BRAWLER_TRIAL_03, Style.Brawler));
        ((AbilityCoreMixin) HakaiHoAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BRAWLER_TRIAL_04, Style.Brawler));
        ((AbilityCoreMixin) JishinHoAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BRAWLER_TRIAL_05, Style.Brawler));
        ((AbilityCoreMixin) KingPunchAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.BRAWLER_TRIAL_06, Style.Brawler));

        ((AbilityCoreMixin) HeatBallAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_01, Style.Art_Of_Weather));
        ((AbilityCoreMixin) CoolBallAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_01, Style.Art_Of_Weather));
        ((AbilityCoreMixin) WeatherCloudTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_01, Style.Art_Of_Weather));
        ((AbilityCoreMixin) ThunderBallAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_02, Style.Art_Of_Weather));
        ((AbilityCoreMixin) ThunderboltTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_02, Style.Art_Of_Weather));
        ((AbilityCoreMixin) RainTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_02, Style.Art_Of_Weather));
        ((AbilityCoreMixin) MirageTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_02, Style.Art_Of_Weather));
        ((AbilityCoreMixin) FogTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_02, Style.Art_Of_Weather));
        ((AbilityCoreMixin) ThunderstormTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_03, Style.Art_Of_Weather));
        ((AbilityCoreMixin) ThunderLanceTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_03, Style.Art_Of_Weather));
        ((AbilityCoreMixin) GustSwordAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_04, Style.Art_Of_Weather));
        ((AbilityCoreMixin) CycloneTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_04, Style.Art_Of_Weather));
        ((AbilityCoreMixin) HeatEggTempo.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_04, Style.Art_Of_Weather));
        ((AbilityCoreMixin) WeatherEggAbility.INSTANCE).setUnlockCheck(QuestProgression.hasFinishedQuest(ModQuests.ART_OF_WEATHER_TRIAL_04, Style.Art_Of_Weather));
    }
}
