package net.warcar.non_fruit_rework.enums.ranks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModI18n;

public enum RevolutionaryRank implements IRank {
    MEMBER(ModI18n.REVOLUTIONARY_TITLE_MEMBER, 0),
    OFFICER(ModI18n.REVOLUTIONARY_TITLE_OFFICER, 30),
    COMMANDER(ModI18n.REVOLUTIONARY_TITLE_COMMANDER, 50),
    CHIEF_OF_STAFF(ModI18n.REVOLUTIONARY_TITLE_CHIEF_OF_STAFF, 80),
    SUPREME_COMMANDER(ModI18n.REVOLUTIONARY_TITLE_SUPREME_COMMANDER, 100);

    private final String unlocalizedName;
    private final double loyaltyRequired;

    RevolutionaryRank(String unlocalizedName, double loyaltyRequired) {
        this.unlocalizedName = unlocalizedName;
        this.loyaltyRequired = loyaltyRequired;
    }

    public String getLocalizedName() {
        return (new TranslationTextComponent(this.unlocalizedName)).getString();
    }

    public double getRequiredLoyalty() {
        return this.loyaltyRequired;
    }

    public static IRank getRevolutionaryRank(PlayerEntity entity) {
        double loyalty = EntityStatsCapability.get(entity).getLoyalty();
        IRank out = RevolutionaryRank.MEMBER;
        for (RevolutionaryRank rank : RevolutionaryRank.values()) {
            if (rank.getRequiredLoyalty() <= loyalty && rank.getRequiredLoyalty() > out.getRequiredLoyalty()) {
                out = rank;
            }
        }
        return out;
    }
}
