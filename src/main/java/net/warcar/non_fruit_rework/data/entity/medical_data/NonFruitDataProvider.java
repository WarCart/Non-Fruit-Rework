package net.warcar.non_fruit_rework.data.entity.medical_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class NonFruitDataProvider implements ICapabilitySerializable<CompoundNBT>
{
	private INonFruitData instance = NonFruitDataCapability.INSTANCE.getDefaultInstance();

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return NonFruitDataCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> instance));
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		return (CompoundNBT) NonFruitDataCapability.INSTANCE.getStorage().writeNBT(NonFruitDataCapability.INSTANCE, instance, null);
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		NonFruitDataCapability.INSTANCE.getStorage().readNBT(NonFruitDataCapability.INSTANCE, instance, null, nbt);
	}
}
