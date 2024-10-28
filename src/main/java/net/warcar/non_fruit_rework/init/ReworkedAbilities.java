package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.abilities.black_leg.MoutonShotAbility;
import net.warcar.non_fruit_rework.abilities.raid_suit.CapeGuard;
import net.warcar.non_fruit_rework.abilities.raid_suit.poison_pink.DetoxicatingKissAbility;
import net.warcar.non_fruit_rework.abilities.raid_suit.poison_pink.PoisonHitAbility;
import net.warcar.non_fruit_rework.abilities.raid_suit.poison_pink.PoisonPinkInvulnerability;
import net.warcar.non_fruit_rework.abilities.raid_suit.sparking_red.SparkingFigureAbility;
import net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black.RyoseiOsobaKick;
import net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black.StealthBlackInvisibility;
import net.warcar.non_fruit_rework.abilities.rokushiki.RankyakuRanAbility;
import net.warcar.non_fruit_rework.abilities.swordsman.AshuraIchibuginAbility;
import net.warcar.non_fruit_rework.abilities.swordsman.SanzenSekaiAbility;
import net.warcar.non_fruit_rework.abilities.test.IkokuAbility;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class ReworkedAbilities {
    public static void init(IEventBus bus) {
        registerAbility(StealthBlackInvisibility.INSTANCE);
        registerAbility(CapeGuard.INSTANCE);
        registerAbility(RyoseiOsobaKick.INSTANCE);
        registerAbility(SparkingFigureAbility.INSTANCE);
        registerAbility(PoisonPinkInvulnerability.INSTANCE);
        registerAbility(PoisonHitAbility.INSTANCE);
        registerAbility(DetoxicatingKissAbility.INSTANCE);
        registerAbility(RankyakuRanAbility.INSTANCE);
        registerAbility(IkokuAbility.INSTANCE);
    }

    public static <T extends IAbility> AbilityCore<T> registerAbility(AbilityCore<T> core) {
        String resourceName = WyHelper.getResourceName(core.getId());
        ResourceLocation key = new ResourceLocation(NonFruitReworkMod.MOD_ID, resourceName);
        WyRegistry.getLangMap().put("ability." + NonFruitReworkMod.MOD_ID + "." + resourceName, core.getUnlocalizedName());
        RegistryObject<AbilityCore<?>> ret = RegistryObject.of(key, ModRegistries.ABILITIES);
        if (!WyRegistry.ABILITIES.getEntries().contains(ret)) {
            WyRegistry.ABILITIES.register(resourceName, () -> core);
            if (core.getIcon() == null) {
                core.setIcon(new ResourceLocation(key.getNamespace(), "textures/abilities/" + key.getPath() + ".png"));
            }
        }

        return core;
    }
}
