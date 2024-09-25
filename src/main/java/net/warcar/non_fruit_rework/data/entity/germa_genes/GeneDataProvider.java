package net.warcar.non_fruit_rework.data.entity.germa_genes;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class GeneDataProvider implements ICapabilitySerializable<CompoundNBT> {
    private IGeneData instance;

    public GeneDataProvider() {
        this.instance = GeneDataCapability.INSTANCE.getDefaultInstance();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return GeneDataCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> this.instance));
    }

    public CompoundNBT serializeNBT() {
        return (CompoundNBT)GeneDataCapability.INSTANCE.getStorage().writeNBT(GeneDataCapability.INSTANCE, this.instance, null);
    }

    public void deserializeNBT(CompoundNBT nbt) {
        GeneDataCapability.INSTANCE.getStorage().readNBT(GeneDataCapability.INSTANCE, this.instance, null, nbt);
    }
}
