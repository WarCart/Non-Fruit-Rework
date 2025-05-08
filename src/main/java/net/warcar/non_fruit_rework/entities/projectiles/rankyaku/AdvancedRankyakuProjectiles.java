package net.warcar.non_fruit_rework.entities.projectiles.rankyaku;

import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.init.ModEntityTypes;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.CrescentModel;
import xyz.pixelatedw.mineminenomi.renderers.abilities.AbilityProjectileRenderer;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdvancedRankyakuProjectiles {
    public static final RegistryObject<EntityType<RankyakuRanProjectile>> RANKYAKU_RAN = ModEntityTypes.registerEntity("Rankyaku: Ran", WyRegistry.createEntityType(RankyakuRanProjectile::new).sized(2.25F, 0.25F).build("meh"));
    public static final RegistryObject<EntityType<HakuraiProjectile>> HAKURAI = ModEntityTypes.registerEntity("Rankyaku: Hakurai", WyRegistry.createEntityType(HakuraiProjectile::new).sized(11F, 1F).build("meh"));
    public static final RegistryObject<EntityType<AmaneDachiProjectile>> AMANE_DACHI = ModEntityTypes.registerEntity("Rankyaku: Amane Dachi", WyRegistry.createEntityType(AmaneDachiProjectile::new).sized(17F, 1F).build("meh"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(RANKYAKU_RAN.get(), new AbilityProjectileRenderer.Factory(new CrescentModel()).setColor("#B1B1E1").setScale(0.33F, 0.67F, 0.33F).setRotation(0.0F, 0.0F, 90.0F));
        RenderingRegistry.registerEntityRenderingHandler(HAKURAI.get(), new AbilityProjectileRenderer.Factory(new CrescentModel()).setColor("#B1B1E1").setScale(2, 3, 2).setRotation(0.0F, 0.0F, 90.0F));
        RenderingRegistry.registerEntityRenderingHandler(AMANE_DACHI.get(), new AbilityProjectileRenderer.Factory(new CrescentModel()).setColor("#B1B1E1").setScale(3, 3, 3).setRotation(0.0F, 0.0F, 90.0F));
    }
}
