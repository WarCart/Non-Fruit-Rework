package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.germa_genes.GeneDataCapability;
import net.warcar.non_fruit_rework.data.entity.germa_genes.GeneDataProvider;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataProvider;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class ModCapabilities {
    public static void init() {
        GeneDataCapability.register();
        MasteryDataCapability.register();
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(new ResourceLocation(NonFruitReworkMod.MOD_ID, "genes_data"), new GeneDataProvider());
            event.addCapability(new ResourceLocation(NonFruitReworkMod.MOD_ID, "mastery_data"), new MasteryDataProvider());
        }
    }
}
