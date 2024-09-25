package net.warcar.non_fruit_rework.data.entity.germa_genes;

public interface IGeneData {
    boolean isAwakenGenes();

    void setAwakenGenes(boolean awakenGenes);

    int usedSuit();

    void setUsedSuit(int usedSuit);

    default void addSuitUse(int addition) {
        this.setUsedSuit(this.usedSuit() + addition);
        if (this.usedSuit() >= 3 && !this.isAwakenGenes()) {
            this.setAwakenGenes(true);
        }
    }
}
