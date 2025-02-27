package net.warcar.non_fruit_rework.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.abilities.cyborg.CyborgHeavyPlatingAbility;
import net.warcar.non_fruit_rework.abilities.fishman.FishmanPowerAbility;
import net.warcar.non_fruit_rework.abilities.human.BerserkModeAbility;
import net.warcar.non_fruit_rework.abilities.human.RageMeterAbility;
import net.warcar.non_fruit_rework.abilities.lunarian.DisasterFlamesAbility;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

public class ModAbilities {
    public static final DeferredRegister<AbilityCore<?>> ABILITIES = DeferredRegister.create(ModRegistries.ABILITIES, NonFruitReworkMod.MOD_ID);

    public static void register(IEventBus bus) {
        ABILITIES.register(bus);
        registerAbility(CyborgHeavyPlatingAbility.INSTANCE);
        registerAbility(DisasterFlamesAbility.INSTANCE);
        registerAbility(RageMeterAbility.INSTANCE);
        registerAbility(BerserkModeAbility.INSTANCE);
        registerAbility(FishmanPowerAbility.INSTANCE);
    }

    private static <A extends IAbility> void registerAbility(AbilityCore<A> instance) {
        String resourceName = WyHelper.getResourceName(instance.getUnlocalizedName());
        ABILITIES.register(resourceName, () -> instance);
        LangHelper.registerLine(String.format("ability.%s.%s", NonFruitReworkMod.MOD_ID, resourceName), instance.getUnlocalizedName());
    }
}
