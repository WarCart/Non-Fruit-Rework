package net.warcar.non_fruit_rework.enums;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IExtensibleEnum;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.enums.ranks.AgentRank;
import net.warcar.non_fruit_rework.enums.ranks.IRank;
import net.warcar.non_fruit_rework.enums.ranks.MarineRank;
import net.warcar.non_fruit_rework.enums.ranks.RevolutionaryRank;
import net.warcar.non_fruit_rework.init.ModSelectionInfos;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.screens.extra.CharacterCreatorSelectionMap;

import javax.annotation.Nullable;
import java.util.function.Function;

public enum Faction implements ICharacterEnum, IExtensibleEnum {
    Pirate(ModSelectionInfos.PIRATE_INFO),
    Marine(MarineRank::getMarineRank, ModSelectionInfos.MARINE_INFO),
    Bounty_Hunter(ModSelectionInfos.BOUNTY_HUNTER_INFO),
    Revolutionary_Army(RevolutionaryRank::getRevolutionaryRank, ModSelectionInfos.REVOLUTIONARY_INFO),
    //Agent(AgentRank::getAgentRank, ModSelectionInfos.AGENT_INFO),
    ;

    private final CharacterCreatorSelectionMap.SelectionInfo info;

    private final Function<PlayerEntity, IRank> rank;

    @Nullable
    public Function<PlayerEntity, IRank> getRankFunction() {
        return this.rank;
    }

    @Nullable
    public IRank getRank(PlayerEntity loyalty) {
        if (this.rank == null) {
            return null;
        }
        return this.getRankFunction().apply(loyalty);
    }

    public CharacterCreatorSelectionMap.SelectionInfo getInfo() {
        return this.info;
    }

    public String getName() {
        return this.name();
    }

    public boolean isRanked() {
        return this.rank != null;
    }

    Faction(@Nullable Function<PlayerEntity, IRank> rank, CharacterCreatorSelectionMap.SelectionInfo info) {
        this.rank = rank;
        this.info = info;
    }

    Faction(CharacterCreatorSelectionMap.SelectionInfo info) {
        this(null, info);
    }

    public static Faction create(String name, @Nullable Function<Integer, IRank> rank, CharacterCreatorSelectionMap.SelectionInfo info) {
        throw new IllegalStateException("Enum not extended");
    }

    public static String[] registryNames() {
        String[] out = new String[values().length + 1];
        out[0] = "random";
        for (int i = 0; i < values().length; i++) {
            out[i + 1] = values()[i].name().toLowerCase();
        }
        return out;
    }

    public static Faction getFromName(String name) {
        for (Faction faction : Faction.values()) {
            if (faction.getName().equalsIgnoreCase(name))
                return faction;
        }
        return null;
    }
}
