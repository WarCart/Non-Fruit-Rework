package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.abilities.DummyAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.GustSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.WeatherEggAbility;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.CycloneTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.MirageTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderLanceTempo;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ThunderstormTempo;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.*;
import xyz.pixelatedw.mineminenomi.abilities.brawler.*;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.*;
import xyz.pixelatedw.mineminenomi.abilities.doctor.*;
import xyz.pixelatedw.mineminenomi.abilities.electro.*;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.*;
import xyz.pixelatedw.mineminenomi.abilities.marineloyalty.MusterAbility;
import xyz.pixelatedw.mineminenomi.abilities.marineloyalty.SmallMusterAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.*;
import xyz.pixelatedw.mineminenomi.abilities.sniper.*;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.*;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.screens.extra.CharacterCreatorSelectionMap;

public class ModSelectionInfos {
    public static final CharacterCreatorSelectionMap.SelectionInfo PIRATE_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.PIRATE_ICON, ModI18n.FACTION_PIRATE);
    public static final CharacterCreatorSelectionMap.SelectionInfo MARINE_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.MARINE_ICON, ModI18n.FACTION_MARINE);
    public static final CharacterCreatorSelectionMap.SelectionInfo BOUNTY_HUNTER_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.BOUNTY_HUNTER_ICON, ModI18n.FACTION_BOUNTY_HUNTER);
    public static final CharacterCreatorSelectionMap.SelectionInfo AGENT_INFO = new CharacterCreatorSelectionMap.SelectionInfo(new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/icons/agent.png"), new StringTextComponent("Agent"));
    public static final CharacterCreatorSelectionMap.SelectionInfo REVOLUTIONARY_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.REVOLUTIONARY_ARMY_ICON, ModI18n.FACTION_REVOLUTIONARY);

    public static final CharacterCreatorSelectionMap.SelectionInfo HUMAN_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.HUMAN, ModI18n.RACE_HUMAN);
    public static final CharacterCreatorSelectionMap.SelectionInfo FISHMAN_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.FISHMAN, ModI18n.RACE_FISHMAN);
    public static final CharacterCreatorSelectionMap.SelectionInfo CYBORG_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.CYBORG, ModI18n.RACE_CYBORG);
    public static final CharacterCreatorSelectionMap.SelectionInfo MINK_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.MINK1, ModI18n.RACE_MINK);
    public static final CharacterCreatorSelectionMap.SelectionInfo MOD_HUMAN_INFO = new CharacterCreatorSelectionMap.SelectionInfo(new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/icons/modified_human.png"), new StringTextComponent("Modified Human"));

    public static final CharacterCreatorSelectionMap.SelectionInfo SWORDSMAN_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.SWORDSMAN, ModI18n.STYLE_SWORDSMAN);
    public static final CharacterCreatorSelectionMap.SelectionInfo SNIPER_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.SNIPER, ModI18n.STYLE_SNIPER);
    public static final CharacterCreatorSelectionMap.SelectionInfo DOCTOR_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.DOCTOR, ModI18n.STYLE_DOCTOR);
    public static final CharacterCreatorSelectionMap.SelectionInfo ART_OF_WEATHER_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.ART_OF_WEATHER, ModI18n.STYLE_ART_OF_WEATHER);
    public static final CharacterCreatorSelectionMap.SelectionInfo BRAWLER_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.BRAWLER, ModI18n.STYLE_BRAWLER);
    public static final CharacterCreatorSelectionMap.SelectionInfo BLACK_LEG_INFO = new CharacterCreatorSelectionMap.SelectionInfo(ModResources.BLACK_LEG, ModI18n.STYLE_BLACK_LEG);

    static {
        AbilityCore<DummyAbility> cyborgArmorPerk = (new AbilityCore.Builder<>("Cyborg Armor", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "cyborg_armor")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_CYBORG_ARMOR_BONUS).build();
        AbilityCore<DummyAbility> modHumanMutationsPerk = (new AbilityCore.Builder<>("Genetic Modification", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "genetic_modification")).setIcon(new ResourceLocation(ModMain.PROJECT_ID, "textures/abilities/cape_guard.png"))
                .addDescriptionLine(new StringTextComponent("Germa genes, makes wearing a raid suit possible")).build();
        AbilityCore<DummyAbility> fishmanSwimSpeedPerk = (new AbilityCore.Builder<>("Fishman Swim Speed", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "fishman_swim_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_FISHMAN_SWIM_SPEED_BONUS).build();
        AbilityCore<DummyAbility> minkSpeedPerk = (new AbilityCore.Builder<>("Mink Speed", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "mink_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_MINK_SPEED_BONUS).build();
        AbilityCore<DummyAbility> minkJumpPerk = (new AbilityCore.Builder<>("Mink Jump", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "mink_jump")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_MINK_JUMP_BONUS).build();
        AbilityCore<DummyAbility> swordsmanDamagePerk = (new AbilityCore.Builder<>("Swordsman Damage", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "swordsman_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_SWORDSMAN_DAMAGE_BONUS).build();
        AbilityCore<DummyAbility> sniperAccuracyPerk = (new AbilityCore.Builder<>("Sniper Accuracy", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "sniper_accuracy")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_SNIPER_ACCURACY_BONUS).build();
        AbilityCore<DummyAbility> sniperGogglesPerk = (new AbilityCore.Builder<>("Sniper Goggles", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "sniper_goggles")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_SNIPER_GOGGLES_BONUS).build();
        AbilityCore<DummyAbility> blackLegDamagePerk = (new AbilityCore.Builder<>("Black Leg Damage", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "black_leg_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_BLACK_LEG_DAMAGE_BONUS).build();
        AbilityCore<DummyAbility> blackLegSpeedPerk = (new AbilityCore.Builder<>("Black Leg Attack Speed", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "black_leg_attack_speed")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_BLACK_LEG_ATTACK_SPEED_BONUS).build();
        AbilityCore<DummyAbility> brawlerDamagePerk = (new AbilityCore.Builder<>("Brawler Damage", AbilityCategory.STYLE, AbilityType.PASSIVE, DummyAbility::new)).setPhantomKey(new ResourceLocation("mineminenomi", "brawler_damage")).setIcon(ModResources.PERK_ICON).addDescriptionLine(ModI18n.PERK_BRAWLER_DAMAGE_BONUS).build();

        MARINE_INFO.addTopAbilities(SmallMusterAbility.INSTANCE, MusterAbility.INSTANCE, CommandAbility.INSTANCE);


        HUMAN_INFO.addTopAbilities(SoruAbility.INSTANCE, TekkaiAbility.INSTANCE, GeppoAbility.INSTANCE, KamieAbility.INSTANCE, RankyakuAbility.INSTANCE, RokuoganAbility.INSTANCE);

        MOD_HUMAN_INFO.addTopAbilities(SoruAbility.INSTANCE, TekkaiAbility.INSTANCE, GeppoAbility.INSTANCE, KamieAbility.INSTANCE, RankyakuAbility.INSTANCE, RokuoganAbility.INSTANCE);
        MOD_HUMAN_INFO.addBottomAbilities(modHumanMutationsPerk);

        FISHMAN_INFO.addTopAbilities(UchimizuAbility.INSTANCE, SamehadaShoteiAbility.INSTANCE, KachiageHaisokuAbility.INSTANCE, TwoFishEngineAbility.INSTANCE, YarinamiAbility.INSTANCE, KarakusagawaraSeikenAbility.INSTANCE);
        FISHMAN_INFO.addBottomAbilities(fishmanSwimSpeedPerk);

        CYBORG_INFO.addTopAbilities(StrongRightAbility.INSTANCE, RadicalBeamAbility.INSTANCE, CoupDeVentAbility.INSTANCE, FreshFireAbility.INSTANCE, CoupDeBooAbility.INSTANCE, ColaOverdriveAbility.INSTANCE);
        CYBORG_INFO.addBottomAbilities(cyborgArmorPerk);

        MINK_INFO.addTopAbilities(EleclawAbility.INSTANCE, ElectricalShowerAbility.INSTANCE, ElectricalLunaAbility.INSTANCE, ElectricalMissileAbility.INSTANCE, SulongAbility.INSTANCE);
        MINK_INFO.addBottomAbilities(minkSpeedPerk, minkJumpPerk);


        SWORDSMAN_INFO.addTopAbilities(ShiShishiSonsonAbility.INSTANCE, YakkodoriAbility.INSTANCE, SanbyakurokujuPoundHoAbility.INSTANCE, OTatsumakiAbility.INSTANCE, HiryuKaenAbility.INSTANCE);
        SWORDSMAN_INFO.addBottomAbilities(swordsmanDamagePerk);

        SNIPER_INFO.addTopAbilities(HiNoToriBoshiAbility.INSTANCE, KemuriBoshiAbility.INSTANCE, NemuriBoshiAbility.INSTANCE, TokuyoAburaBoshiAbility.INSTANCE, TetsuBoshiAbility.INSTANCE, SakuretsuSabotenBoshiAbility.INSTANCE);
        SNIPER_INFO.addBottomAbilities(sniperAccuracyPerk, sniperGogglesPerk);

        DOCTOR_INFO.addTopAbilities(FirstAidAbility.INSTANCE, FailedExperimentAbility.INSTANCE, DopingAbility.INSTANCE, VirusZoneAbility.INSTANCE, AntidoteShotAbility.INSTANCE, MedicBagExplosionAbility.INSTANCE);

        ART_OF_WEATHER_INFO.addTopAbilities(WeatherEggAbility.INSTANCE, ThunderstormTempo.INSTANCE, GustSwordAbility.INSTANCE, CycloneTempo.INSTANCE, MirageTempo.INSTANCE, ThunderLanceTempo.INSTANCE);

        BRAWLER_INFO.addTopAbilities(SuplexAbility.INSTANCE, GenkotsuMeteorAbility.INSTANCE, SpinningBrawlAbility.INSTANCE, HakaiHoAbility.INSTANCE, JishinHoAbility.INSTANCE, KingPunchAbility.INSTANCE);
        BRAWLER_INFO.addBottomAbilities(brawlerDamagePerk);

        BLACK_LEG_INFO.addTopAbilities(ConcasseAbility.INSTANCE, AntiMannerKickCourseAbility.INSTANCE, PartyTableKickCourseAbility.INSTANCE, SkywalkAbility.INSTANCE, DiableJambeAbility.INSTANCE, BienCuitGrillShotAbility.INSTANCE);
        BLACK_LEG_INFO.addBottomAbilities(blackLegDamagePerk, blackLegSpeedPerk);
    }
}
