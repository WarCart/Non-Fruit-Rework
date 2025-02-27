package net.warcar.non_fruit_rework.data.entity.medical_data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MedicalDataCapability {
	@CapabilityInject(IMedicalData.class)
	public static final Capability<IMedicalData> INSTANCE = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IMedicalData.class, new Capability.IStorage<IMedicalData>() {
			@Override
			public INBT writeNBT(Capability<IMedicalData> capability, IMedicalData instance, Direction side) {
				CompoundNBT props = new CompoundNBT();

				return props;
			}

			@Override
			public void readNBT(Capability<IMedicalData> capability, IMedicalData instance, Direction side, INBT nbtData) {
				CompoundNBT props = (CompoundNBT) nbtData;
			}
		}, MedicalDataBase::new);
	}

	@Nullable
	public static IMedicalData get(@Nonnull final LivingEntity entity) {
		return getLazy(entity).orElse(new MedicalDataBase());
	}

	public static LazyOptional<IMedicalData> getLazy(@Nonnull final LivingEntity entity) {
		LazyOptional<IMedicalData> lazyGCD = entity.getCapability(INSTANCE, null);
		lazyGCD.ifPresent(data -> data.setDataOwner(entity));
		return lazyGCD;
	}
}
