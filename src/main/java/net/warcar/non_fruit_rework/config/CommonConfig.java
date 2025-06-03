package net.warcar.non_fruit_rework.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    private final ForgeConfigSpec.BooleanValue fullQuest;
    private final ForgeConfigSpec.BooleanValue keepZoan;
    private final ForgeConfigSpec.BooleanValue customRaces;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Racial");
        this.fullQuest = builder.comment("Completely removes ability to gain racial abilities without quests").define("Disable Doriki Progresstion", false);
        this.keepZoan = builder.comment("Allows to keep zoan type devil fruits on cloning").define("Keep Zoan", true);
        this.customRaces = builder.comment("Replaces Cyborg with giant in character creator book").define("Custom Races", true);
    }

    public boolean isFullQuest() {
        return fullQuest.get();
    }

    public boolean isKeepZoan() {
        return keepZoan.get();
    }

    public boolean isCustomRaces() {
        return customRaces.get();
    }

    public boolean seraphims() {
        return true; //TODO: Turn off to official release
    }

    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }
}
