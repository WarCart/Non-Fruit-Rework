package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

//@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class ReworkedAttributes {
    public static final RegistryObject<Attribute> SIZE = WyRegistry.registerAttribute("Size", () -> new RangedAttribute("attribute.name.generic.mineminenomi.size", 1.0D, 0.0D, 32.0D).setSyncable(true));

    public static void init() {
    }

    @SubscribeEvent
    public static void resizeBB(EntityEvent.Size event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) event.getEntity();
            try {
                ModifiableAttributeInstance attributeInstance = living.getAttribute(ReworkedAttributes.SIZE.get());
                if (attributeInstance != null) {
                    event.setNewSize(event.getNewSize().scale((float) attributeInstance.getValue()), true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void resizeClient(RenderLivingEvent.Pre event) {
        LivingEntity living = event.getEntity();
        ModifiableAttributeInstance attributeInstance = living.getAttribute(ReworkedAttributes.SIZE.get());
        if (attributeInstance != null) {
            float size = (float) attributeInstance.getValue();
            event.getMatrixStack().scale(size, size, size);
        }
    }

    @Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Setup {

        @SubscribeEvent
        public static void onEntityConstruct(EntityAttributeModificationEvent event) {
            for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
                if (entityType != null) event.add(entityType, ReworkedAttributes.SIZE.get(), 1);
            }
        }
    }
}
