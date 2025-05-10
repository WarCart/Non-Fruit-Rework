package net.warcar.non_fruit_rework.events;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.world.spawners.RaceTrainerSpawner;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class WorldEvents {
    public static final RaceTrainerSpawner TRAINER_SPAWNER = new RaceTrainerSpawner();

    @SubscribeEvent
    public static void onServerTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.world.dimension() == World.OVERWORLD) {
            if (event.world.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                event.world.getProfiler().push("worldSpawners");
                if (CommonConfig.INSTANCE.canSpawnTrainers()) {
                    TRAINER_SPAWNER.tick((ServerWorld) event.world);
                }
                event.world.getProfiler().pop();
            }
        }
    }
}
