package net.warcar.non_fruit_rework.data.entity.mastery;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MasteryDataProvider implements ICapabilitySerializable<CompoundNBT> {
    private IMasteryData instance;

    public MasteryDataProvider() {
        this.instance = MasteryDataCapability.INSTANCE.getDefaultInstance();
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return MasteryDataCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> this.instance));
    }

    public CompoundNBT serializeNBT() {
        return (CompoundNBT)MasteryDataCapability.INSTANCE.getStorage().writeNBT(MasteryDataCapability.INSTANCE, this.instance, null);
    }

    public void deserializeNBT(CompoundNBT nbt) {
        MasteryDataCapability.INSTANCE.getStorage().readNBT(MasteryDataCapability.INSTANCE, this.instance, null, nbt);
    }
}
