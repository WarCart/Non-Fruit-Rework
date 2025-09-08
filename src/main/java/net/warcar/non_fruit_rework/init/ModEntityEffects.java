package net.warcar.non_fruit_rework.init;

import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.effects.SapphireScalesEffect;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.function.Supplier;

public class ModEntityEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, NonFruitReworkMod.MOD_ID);

    public static final RegistryObject<SapphireScalesEffect> SAPPHIRE_SCALES = registerEffect("Sapphire Scales", SapphireScalesEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

    public static <E extends Effect> RegistryObject<E> registerEffect(String name, Supplier<E> effect) {
        String id = WyHelper.getResourceName(name);
        return EFFECTS.register(id, effect);
    }
}
