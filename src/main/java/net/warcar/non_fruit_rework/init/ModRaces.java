package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.charactercreator.CharacterCreatorSelectionMap;
import xyz.pixelatedw.mineminenomi.api.charactercreator.RaceId;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.function.Supplier;

public class ModRaces {
    public static final ResourceLocation GIANT_ICON = new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/character_creator/giant.png");
    public static final DeferredRegister<RaceId> RACES = DeferredRegister.create(RaceId.class, NonFruitReworkMod.MOD_ID);

    public static final RegistryObject<RaceId> HYBRID = registerRace("Hybrid", () ->
            new RaceId(new CharacterCreatorSelectionMap.SelectionInfo(ModResources.RANDOM), false));

    public static final RegistryObject<RaceId> GIANT = registerRace("Giant", () -> {
        CharacterCreatorSelectionMap.SelectionInfo info = new CharacterCreatorSelectionMap.SelectionInfo(GIANT_ICON);
        return new RaceId(info, CommonConfig.INSTANCE.isCustomRaces(), 3);
    });


    public static final RegistryObject<RaceId> LUNARIAN = registerRace("Lunarian", () ->
            new RaceId(new CharacterCreatorSelectionMap.SelectionInfo(ModResources.RANDOM), false));

    public static final RegistryObject<RaceId> ONI = registerRace("Oni", () ->
            new RaceId(new CharacterCreatorSelectionMap.SelectionInfo(ModResources.RANDOM), false));

    public static final RegistryObject<RaceId> ANCIENT_GIANT = registerRace("Ancient Giant", () ->
            new RaceId(new CharacterCreatorSelectionMap.SelectionInfo(ModResources.RANDOM), false));

    private static RegistryObject<RaceId> registerRace(String name, Supplier<RaceId> supplier) {
        String resourceName = WyHelper.getResourceName(name);
        LangHelper.registerLine("race." + NonFruitReworkMod.MOD_ID + "." + resourceName, name);
        return RACES.register(resourceName, supplier);
    }

    public static void register(IEventBus eventBus) {
        RACES.register(eventBus);
    }
}
