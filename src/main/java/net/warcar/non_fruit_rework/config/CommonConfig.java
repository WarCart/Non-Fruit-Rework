package net.warcar.non_fruit_rework.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    private final ForgeConfigSpec.BooleanValue fullQuest;
    private final ForgeConfigSpec.BooleanValue keepZoan;
    private final ForgeConfigSpec.BooleanValue customRaces;
    private final ForgeConfigSpec.EnumValue<AdvancedRokushikiUnlockType> advancedRokushikiUnlock;
    private final ForgeConfigSpec.BooleanValue spawnAfterimages;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Racial");
        this.fullQuest = builder.comment("Completely removes ability to gain racial abilities without quests").define("Disable Doriki Progresstion", false);
        this.customRaces = builder.comment("Replaces Cyborg with giant in character creator book").define("Custom Races", true);
        this.advancedRokushikiUnlock = builder.comment(
                "Chooses what players can unlock advanced rokushiki",
                "Modes: ",
                "\tTRUE_HUMAN: only non hybrid humans can use it",
                "\tHUMAN: hybrid that include more than 10% of human genes or true humans",
                "\tANY: anyone can use advanced rokushiki"
        ).defineEnum("Advanced rokushiki unlock", AdvancedRokushikiUnlockType.TRUE_HUMAN);
        builder.pop();
        builder.push("Misc");
        this.keepZoan = builder.comment("Allows to keep zoan type devil fruits on cloning").define("Keep Zoan", true);
        this.spawnAfterimages = builder.comment("Defines if afterimages should spawn on high speed moves").define("Spawn afterimages", true);
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

    public AdvancedRokushikiUnlockType getAdvancedRokushikiUnlock() {
        return advancedRokushikiUnlock.get();
    }

    public boolean spawnAfterimages() {
        return spawnAfterimages.get();
    }

    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public enum AdvancedRokushikiUnlockType {
        TRUE_HUMAN,
        HUMAN,
        ANY;
    }
}
