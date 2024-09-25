package net.warcar.non_fruit_rework.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.enums.Race;
import xyz.pixelatedw.mineminenomi.api.events.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;

import java.util.Random;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class RaceEvents {
    @SubscribeEvent
    public static void onCreating(SetPlayerDetailsEvent event) {
        String subRace;
        IEntityStats entityProps = EntityStatsCapability.get(event.getPlayer());
        String race = entityProps.getRace();
        if (Race.getFromName(race).hasSubRaces()) {
            if (CommonConfig.INSTANCE.getAllowMinkRaceSelect() && !race.equalsIgnoreCase(Race.Mink.getName())) {
                subRace = Race.getFromName(race).getSubRaces()[0].getName();
            } else {
                subRace = Race.getFromName(race).getSubRaces()[new Random().nextInt(Race.getFromName(race).getSubRaces().length)].getName();
            }
            entityProps.setSubRace(subRace);
        }
    }
}
