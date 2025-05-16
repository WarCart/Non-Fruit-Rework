package net.warcar.non_fruit_rework.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    private final ForgeConfigSpec.BooleanValue fullQuest;
    private final ForgeConfigSpec.BooleanValue keepZoan;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Racial");
        this.fullQuest = builder.comment("Completely removes ability to gain racial abilities without quests").define("Disable Doriki Progresstion", false);
        this.keepZoan = builder.comment("Allows to keep zoan type devil fruits on cloning").define("Keep Zoan", true);
    }

    public boolean isFullQuest() {
        return fullQuest.get();
    }

    public boolean isKeepZoan() {
        return keepZoan.get();
    }

    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }
}
