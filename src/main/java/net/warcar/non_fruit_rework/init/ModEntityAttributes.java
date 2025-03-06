package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

public class ModEntityAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, NonFruitReworkMod.MOD_ID);

    public static final Attribute SIZE = registerAttribute(new RangedAttribute("Size", 1, -2048, 2048).setSyncable(true));

    private static <A extends Attribute> A registerAttribute(A attribute) {
        String id = "attribute.generic." + NonFruitReworkMod.MOD_ID + "." + WyHelper.getResourceName(attribute.getDescriptionId());
        ATTRIBUTES.register(id, () -> attribute);
        LangHelper.registerLine(id, attribute.getDescriptionId());
        return attribute;
    }

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }
    @Mod.EventBusSubscriber(
            modid = NonFruitReworkMod.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static class Setup {
        public Setup() {
        }

        @SubscribeEvent
        public static void onEntityConstruct(EntityAttributeModificationEvent event) {
            for (EntityType<? extends LivingEntity> type : event.getTypes()) {
                event.add(type, SIZE);
            }
        }
    }
}
