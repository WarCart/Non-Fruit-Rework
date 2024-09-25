package net.warcar.non_fruit_rework.data.entity.germa_genes;

public class GeneDataBase implements IGeneData {
    private boolean awakenGenes;
    private int usedSuitCounter;

    public boolean isAwakenGenes() {
        return this.awakenGenes;
    }

    public void setAwakenGenes(boolean awakenGenes) {
        this.awakenGenes = awakenGenes;
    }

    public int usedSuit() {
        return this.usedSuitCounter;
    }

    public void setUsedSuit(int usedSuit) {
        this.usedSuitCounter = usedSuit;
    }
}
