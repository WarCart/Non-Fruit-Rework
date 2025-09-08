package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.abilities.GenesAbility;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.enums.ModifiableAttributes;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CUpdatePassiveAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdatePassiveAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class GeneticDriftResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
        IAbilityData abilityData = AbilityDataCapability.get(entity);
        GenesAbility ability = abilityData.getPassiveAbility(GenesAbility.INSTANCE);
        if (ability != null) {
            ModifiableAttributes value = ModifiableAttributes.values()[entity.getRandom().nextInt(ModifiableAttributes.values().length)];
            double delta = value.getMax() - value.getMin();
            int steps = entity.getRandom().nextInt(value.getSteps() * 2) - value.getSteps();
            steps /= 5;
            double oldVal = ability.getGenes().get(value);
            double newVal = delta / value.getSteps() * steps + oldVal;
            ability.getGenes().put(value, newVal);
            NonFruitReworkMod.LOGGER.info("{}: {} ({})", value, newVal, oldVal);
            if (entity.level.isClientSide()) {
                ModNetwork.sendToServer(new CUpdatePassiveAbilityDataPacket(entity, ability));
            } else {
                WyNetwork.sendToAllTracking(new SUpdatePassiveAbilityDataPacket(entity, ability), entity);
            }
        }
    }

    @Override
    public void tick(LivingEntity entity) {
        INonFruitData data = NonFruitDataCapability.get(entity);
        data.removeExperiment(this);
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.NEUTRAL;
    }
}
