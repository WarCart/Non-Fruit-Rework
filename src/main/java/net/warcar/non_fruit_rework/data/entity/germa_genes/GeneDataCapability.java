package net.warcar.non_fruit_rework.data.entity.germa_genes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;

public class GeneDataCapability {
    @CapabilityInject(IGeneData.class)
    public static final Capability<IGeneData> INSTANCE = null;

    public static IGeneData get(LivingEntity entity) {
        return entity.getCapability(INSTANCE, null).orElse(new GeneDataBase());
    }

    public static LazyOptional<IGeneData> getLazy(LivingEntity entity) {
        return entity.getCapability(INSTANCE, null);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IGeneData.class, new Capability.IStorage<IGeneData>() {
            public INBT writeNBT(Capability<IGeneData> capability, IGeneData instance, Direction side) {
                CompoundNBT props = new CompoundNBT();
                props.putBoolean("awakenGenes", instance.isAwakenGenes());
                props.putInt("usedSuit", instance.usedSuit());
                return props;
            }
            public void readNBT(Capability<IGeneData> capability, IGeneData instance, Direction side, INBT nbt) {
                instance.setAwakenGenes(((CompoundNBT) nbt).getBoolean("awakenGenes"));
                instance.setUsedSuit(((CompoundNBT) nbt).getInt("usedSuit"));
            }
        }, GeneDataBase::new);
    }
}
