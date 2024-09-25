package net.warcar.non_fruit_rework.enums.ranks;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;

public enum AgentRank implements IRank {
    CP1(0),
    CP2(5),
    CP3(10),
    CP4(15),
    CP5(20),
    CP6(30),
    CP7(40),
    CP8(50),
    CP9(70),
    CP0(100);

    AgentRank(double loyaltyNeeded) {
        this.loyaltyNeeded = loyaltyNeeded;
    }

    private final double loyaltyNeeded;

    public double getRequiredLoyalty() {
        return loyaltyNeeded;
    }

    public String getLocalizedName() {
        return this.name();
    }

    public static IRank getAgentRank(PlayerEntity entity) {
        double loyalty = EntityStatsCapability.get(entity).getLoyalty();
        IRank out = AgentRank.CP1;
        for (AgentRank rank : AgentRank.values()) {
            if (rank.getRequiredLoyalty() <= loyalty && rank.getRequiredLoyalty() > out.getRequiredLoyalty()) {
                out = rank;
            }
        }
        return out;
    }
}
