package net.warcar.non_fruit_rework.enums.ranks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.world.marine.MarineInfo;
import net.warcar.non_fruit_rework.init.ModConfig;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModI18n;

public enum MarineRank implements IRank {
    CHORE_BOY(ModI18n.MARINE_TITLE_CHORE_BOY, 0),
    SEAMAN(ModI18n.MARINE_TITLE_SEAMAN, 5),
    PETTY_OFFICER(ModI18n.MARINE_TITLE_PETTY_OFFICER, 10),
    LIEUTENANT(ModI18n.MARINE_TITLE_LIEUTENANT, 15),
    COMMANDER(ModI18n.MARINE_TITLE_COMMANDER, 20),
    CAPTAIN(ModI18n.MARINE_TITLE_CAPTAIN, 25),
    COMMODORE(ModI18n.MARINE_TITLE_COMMODORE, 40),
    VICE_ADMIRAL(ModI18n.MARINE_TITLE_VICE_ADMIRAL, 50),
    ADMIRAL(ModI18n.MARINE_TITLE_ADMIRAL, 70),
    FLEET_ADMIRAL(ModI18n.MARINE_TITLE_FLEET_ADMIRAL, 100);

    private final String unlocalizedName;
    private final double loyaltyRequired;

    MarineRank(String unlocalizedName, double loyaltyRequired) {
        this.unlocalizedName = unlocalizedName;
        this.loyaltyRequired = loyaltyRequired;
    }

    public String getLocalizedName() {
        return (new TranslationTextComponent(this.unlocalizedName)).getString();
    }

    public double getRequiredLoyalty() {
        return this.loyaltyRequired;
    }

    public static IRank getMarineRank(PlayerEntity entity) {
        double loyalty = EntityStatsCapability.get(entity).getLoyalty();
        /*if (ModConfig.INSTANCE.isFleetAdmiralsLimited() && MarineInfo.get(entity.level).isFleetAdmiral(entity)) {
            return FLEET_ADMIRAL;
        } else if (ModConfig.INSTANCE.isAdmiralsLimited() && MarineInfo.get(entity.level).isAdmiral(entity)) {
            return ADMIRAL;
        }*/
        IRank out = CHORE_BOY;
        for (MarineRank marineRank : MarineRank.values()) {
            if (marineRank.getRequiredLoyalty() <= loyalty && marineRank.getRequiredLoyalty() > out.getRequiredLoyalty()) {
                out = marineRank;
            }
        }
        if (out == FLEET_ADMIRAL && ModConfig.INSTANCE.isFleetAdmiralsLimited()) {
            out = ADMIRAL;
        }
        if (out == ADMIRAL && ModConfig.INSTANCE.isAdmiralsLimited()) {
            out = VICE_ADMIRAL;
        }
        return out;
    }
}
