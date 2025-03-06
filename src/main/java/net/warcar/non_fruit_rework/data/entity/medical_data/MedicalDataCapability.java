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
import java.util.HashMap;
import java.util.Map;

public class MedicalDataCapability {
	@CapabilityInject(IMedicalData.class)
	public static final Capability<IMedicalData> INSTANCE = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IMedicalData.class, new Capability.IStorage<IMedicalData>() {
			@Override
			public INBT writeNBT(Capability<IMedicalData> capability, IMedicalData instance, Direction side) {
				CompoundNBT props = new CompoundNBT();
				props.putInt("energySteroidTicks", instance.getEnergySteroidTicks());
				props.putInt("energySteroidLevel", instance.getEnergySteroidLevel());

				props.putInt("rumbleBallTicks", instance.getRumbleBallTicks());
				props.putInt("rumbleBallLevel", instance.getRumbleBallLevel());

				props.putInt("sulongBallTicks", instance.getSulongBallTicks());

				CompoundNBT genome = new CompoundNBT();
				for (String name : instance.getGenome().keySet()) {
					genome.putFloat(name, instance.getGenome().get(name));
				}
				props.put("genome", genome);

				return props;
			}

			@Override
			public void readNBT(Capability<IMedicalData> capability, IMedicalData instance, Direction side, INBT nbtData) {
				CompoundNBT props = (CompoundNBT) nbtData;
				instance.setEnergySteroidTicks(props.getInt("energySteroidTicks"));
				instance.setEnergySteroidLevel(props.getInt("energySteroidLevel"));

				instance.setRumbleBallTicks(props.getInt("rumbleBallTicks"));
				instance.setRumbleBallLevel(props.getInt("rumbleBallLevel"));

				instance.setSulongBallTicks(props.getInt("sulongBallTicks"));

				CompoundNBT genome = props.getCompound("genome");
				Map<String, Float> genomeMap = new HashMap<>();
				for (String name : genome.getAllKeys()) {
					genomeMap.put(name, genome.getFloat(name));
				}
				instance.setGenome(genomeMap);
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
