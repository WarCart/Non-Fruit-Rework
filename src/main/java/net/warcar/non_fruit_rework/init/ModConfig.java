package net.warcar.non_fruit_rework.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {
    public static final ModConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;
    //private final ForgeConfigSpec.IntValue maxAdmirals;
    //private final ForgeConfigSpec.IntValue maxFleetAdmirals;
    private final ForgeConfigSpec.IntValue maxMastery;
    private final ForgeConfigSpec.BooleanValue playerChallenges;
    private final ForgeConfigSpec.BooleanValue questRaceProgression;

    public ModConfig(ForgeConfigSpec.Builder builder) {
        //this.maxAdmirals = builder.comment("Defines how many Admirals can coexist\nSet to 0 to be infinite \nDefault: 0").defineInRange("Max Admirals", 0, 0, Integer.MAX_VALUE);
        //this.maxFleetAdmirals = builder.comment("Defines how many Fleet Admirals can coexist\nSet to 0 to be infinite \nDefault: 0").defineInRange("Max Fleet Admirals", 0, 0, Integer.MAX_VALUE);
        this.maxMastery = builder.comment("Sets a new limit for maximum mastery of fighting style a player may obtain \nDefault: 100").defineInRange("Max Mastery", 100, 1, 300);
        this.playerChallenges = builder.comment("Gives Player ability to start challenge against other player \nRequires restart \nDefault: true").define("Player only challenges", true);
        this.questRaceProgression = builder.comment("New race system that requires player to learn racial skills by quests \nRequires restart \nDefault: true").define("Quest Race Progression", true);
    }

    public int getMaxAdmirals() {
        return //this.maxAdmirals.get();
        0;
    }

    public boolean isAdmiralsLimited() {
        return this.getMaxAdmirals() != 0;
    }

    public int getMaxFleetAdmirals() {
        return //this.maxFleetAdmirals.get();
        0;
    }

    public boolean isFleetAdmiralsLimited() {
        return this.getMaxFleetAdmirals() != 0;
    }

    public boolean isMarineLimited() {
        return this.isAdmiralsLimited() || this.isFleetAdmiralsLimited();
    }

    public int getMaxMastery() {
        return this.maxMastery.get();
    }

    public boolean isPlayerChallenges() {
        return this.playerChallenges.get();
    }

    static {
        Pair<ModConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(ModConfig::new);
        SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public boolean getQuestRaceProgression() {
        return this.questRaceProgression.get();
    }
}
