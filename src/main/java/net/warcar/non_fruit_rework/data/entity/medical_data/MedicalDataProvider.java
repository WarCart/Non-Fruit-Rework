package net.warcar.non_fruit_rework.data.entity.medical_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MedicalDataProvider implements ICapabilitySerializable<CompoundNBT>
{
	private IMedicalData instance = MedicalDataCapability.INSTANCE.getDefaultInstance();

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return MedicalDataCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> instance));
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		return (CompoundNBT) MedicalDataCapability.INSTANCE.getStorage().writeNBT(MedicalDataCapability.INSTANCE, instance, null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		MedicalDataCapability.INSTANCE.getStorage().readNBT(MedicalDataCapability.INSTANCE, instance, null, nbt);
	}
}
