package net.warcar.non_fruit_rework.data.entity.mastery;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;

public class MasteryDataCapability {
    @CapabilityInject(IMasteryData.class)
    public static final Capability<IMasteryData> INSTANCE = null;

    public static IMasteryData get(LivingEntity entity) {
        return entity.getCapability(INSTANCE, null).orElse(new MasteryDataBase());
    }

    public static LazyOptional<IMasteryData> getLazy(LivingEntity entity) {
        return entity.getCapability(INSTANCE, null);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IMasteryData.class, new Capability.IStorage<IMasteryData>() {
            public INBT writeNBT(Capability<IMasteryData> capability, IMasteryData instance, Direction side) {
                CompoundNBT props = new CompoundNBT();
                props.putFloat("mastery", instance.getMastery());
                return props;
            }
            public void readNBT(Capability<IMasteryData> capability, IMasteryData instance, Direction side, INBT nbt) {
                instance.setMastery(((CompoundNBT) nbt).getFloat("mastery"));
            }
        }, MasteryDataBase::new);
    }
}
