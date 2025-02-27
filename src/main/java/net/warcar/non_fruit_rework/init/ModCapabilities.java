package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.medical_data.MedicalDataCapability;
import net.warcar.non_fruit_rework.data.entity.medical_data.MedicalDataProvider;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class ModCapabilities {
    public static void init() {
        MedicalDataCapability.register();
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(new ResourceLocation(NonFruitReworkMod.MOD_ID, "medical_data"), new MedicalDataProvider());
        }
    }
}
