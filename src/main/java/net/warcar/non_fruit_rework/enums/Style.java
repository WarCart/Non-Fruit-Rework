package net.warcar.non_fruit_rework.enums;

import net.minecraftforge.common.IExtensibleEnum;
import net.warcar.non_fruit_rework.init.ModSelectionInfos;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.screens.extra.CharacterCreatorSelectionMap;

public enum Style implements ICharacterEnum, IExtensibleEnum {
    Swordsman(ModSelectionInfos.SWORDSMAN_INFO, SourceType.SLASH),
    Sniper(ModSelectionInfos.SNIPER_INFO, SourceType.PROJECTILE),
    Doctor(ModSelectionInfos.DOCTOR_INFO),
    Art_Of_Weather(ModSelectionInfos.ART_OF_WEATHER_INFO),
    Brawler(ModSelectionInfos.BRAWLER_INFO, SourceType.FIST),
    Black_Leg(ModSelectionInfos.BLACK_LEG_INFO, SourceType.FIST);

    private final CharacterCreatorSelectionMap.SelectionInfo info;

    private final SourceType[] types;

    public CharacterCreatorSelectionMap.SelectionInfo getInfo() {
        return this.info;
    }

    public String getName() {
        return this.name();
    }

    public boolean isTrained(ModDamageSource source) {
        for (SourceType type : this.types) {
            if (source.hasType(type)) return true;
        }
        return false;
    }

    Style(CharacterCreatorSelectionMap.SelectionInfo info, SourceType... types) {
        this.info = info;
        this.types = types;
    }

    public static Style create(String name, CharacterCreatorSelectionMap.SelectionInfo info, SourceType... types) {
        throw new IllegalStateException("Enum not extended");
    }

    public static Style getFromName(String name) {
        for (Style style : Style.values()) {
            if (style.getName().equalsIgnoreCase(name))
                return style;
        }
        return null;
    }

    public static String[] registryNames() {
        String[] out = new String[values().length + 1];
        out[0] = "random";
        for (int i = 0; i < values().length; i++) {
            out[i + 1] = values()[i].name().toLowerCase();
        }
        return out;
    }
}
