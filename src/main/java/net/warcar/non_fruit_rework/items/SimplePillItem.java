package net.warcar.non_fruit_rework.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.warcar.non_fruit_rework.data.entity.medical_data.IMedicalData;
import net.warcar.non_fruit_rework.data.entity.medical_data.MedicalDataCapability;
import xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;

public class SimplePillItem extends AbstractPillItem {
    private final PillEffect effect;

    public SimplePillItem(Properties properties, PillEffect effect) {
        super(properties);
        this.effect = effect;
    }

    @Override
    protected boolean pillEffect(LivingEntity entity, ItemStack stack) {
        IMedicalData medicalData = MedicalDataCapability.get(entity);
        switch (this.effect) {
            case ENERGY_STEROID:
                medicalData.popEnergySteroids();
                return true;
            case SULONG_BALL:
                medicalData.popSulongBall();
                SulongAbility ability = AbilityDataCapability.get(entity).getEquippedAbility(SulongAbility.INSTANCE);
                if (ability != null && !ability.isContinuous()) {
                    ability.use(entity);
                }
                return true;
            case RUMBLE_BALL:
                return medicalData.popRumbleBall();
        }
        return false;
    }

    public enum PillEffect {
        ENERGY_STEROID,
        SULONG_BALL,
        RUMBLE_BALL
    }
}
