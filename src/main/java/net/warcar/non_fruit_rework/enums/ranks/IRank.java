package net.warcar.non_fruit_rework.enums.ranks;

public interface IRank {
    double getRequiredLoyalty();

    String getLocalizedName();

    default boolean weakerThen(IRank rank) {
        return this.getRequiredLoyalty() < rank.getRequiredLoyalty();
    }
}
