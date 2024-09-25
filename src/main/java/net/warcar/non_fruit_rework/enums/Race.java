package net.warcar.non_fruit_rework.enums;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IExtensibleEnum;
import net.warcar.non_fruit_rework.init.ModSelectionInfos;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.screens.extra.CharacterCreatorSelectionMap;

public enum Race implements ICharacterEnum, IExtensibleEnum {
    Human(ModSelectionInfos.HUMAN_INFO),
    Fishman(ModSelectionInfos.FISHMAN_INFO),
    Cyborg(ModSelectionInfos.CYBORG_INFO),
    Mink(new SubRace[]{new SubRace("mink_bunny", ModResources.MINK1), new SubRace("mink_dog", ModResources.MINK2), new SubRace("mink_lion", ModResources.MINK3)}, ModSelectionInfos.MINK_INFO),
    Modified_Human(ModSelectionInfos.MOD_HUMAN_INFO);

    public static String[] registryNames() {
        String[] out = new String[values().length + 1];
        out[0] = "random";
        for (int i = 0; i < values().length; i++) {
            out[i + 1] = values()[i].name().toLowerCase();
        }
        return out;
    }

    private CharacterCreatorSelectionMap.SelectionInfo info;

    private AbilityCore<?>[] cores;

    private final SubRace[] subRaces;

    public CharacterCreatorSelectionMap.SelectionInfo getInfo() {
        return this.info;
    }

    public String getName() {
        return this.name();
    }

    Race(SubRace[] subRaces, CharacterCreatorSelectionMap.SelectionInfo info) {
        this.info = info;
        this.subRaces = subRaces;
    }

    Race(CharacterCreatorSelectionMap.SelectionInfo info) {
        this(new SubRace[0], info);
    }

    public AbilityCore<?>[] getCores() {
        return this.cores;
    }

    public void setCores(AbilityCore<?>[] cores) {
        this.cores = cores;
    }

    public SubRace[] getSubRaces() {
        return subRaces;
    }

    public boolean hasSubRaces() {
        return this.getSubRaces().length != 0;
    }

    public static Race create(String name, SubRace[] subRaces, CharacterCreatorSelectionMap.SelectionInfo info) {
        throw new IllegalStateException("Enum not extended");
    }

    public static Race getFromName(String name) {
        for (Race race : Race.values()) {
            if (race.getName().equalsIgnoreCase(name))
                return race;
        }
        return null;
    }

    public static class SubRace {
        private final String name;
        private final ResourceLocation icon;

        public SubRace(String name, ResourceLocation icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return this.name;
        }

        public ResourceLocation getIcon() {
            return this.icon;
        }
    }
}
