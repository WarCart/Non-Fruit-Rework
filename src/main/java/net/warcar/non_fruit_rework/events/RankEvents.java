package net.warcar.non_fruit_rework.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.world.marine.MarineInfo;
import net.warcar.non_fruit_rework.enums.ranks.MarineRank;
import net.warcar.non_fruit_rework.init.ModConfig;
import xyz.pixelatedw.mineminenomi.api.events.stats.LoyaltyEvent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiDataCapability;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class RankEvents {
    public static final Function<PlayerEntity, Double> haki = entity -> (double) HakiDataCapability.get(entity).getTotalHakiExp();

    public static final Function<PlayerEntity, Double> doriki = entity -> EntityStatsCapability.get(entity).getDoriki();

    public static final Function<PlayerEntity, Double> loyalty = entity -> (double) EntityStatsCapability.get(entity).getLoyalty();

    @SubscribeEvent
    public static void onHighMarineDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && ModConfig.INSTANCE.isMarineLimited()) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            MarineInfo info = MarineInfo.get(player.level);
            if (info.isFleetAdmiral(player)) {
                PlayerEntity strongest = getStrongest(player.level, (playerEntity) -> EntityStatsCapability.get(playerEntity).isMarine() && !info.isFleetAdmiral(playerEntity), new Function[]{haki, doriki, loyalty});
                if (strongest != null) {
                    info.replaceFleetAdmiral(player, strongest);
                }
            } else if (info.isAdmiral(player)) {
                PlayerEntity strongest = getStrongest(player.level, (playerEntity) -> EntityStatsCapability.get(playerEntity).isMarine() && !(info.isAdmiral(playerEntity) || info.isFleetAdmiral(playerEntity)), new Function[]{haki, doriki, loyalty});
                if (strongest != null) {
                    info.replaceAdmiral(player, strongest);
                }
            }
        }
    }

    @Nullable
    public static PlayerEntity getStrongest(World level, @Nullable Predicate<PlayerEntity> test, Function<PlayerEntity, Double>[] parametrs) {
        if (test == null) {
            test = playerEntity -> true;
        }
        PlayerEntity strongest = null;
        double[] vals = new double[parametrs.length];
        for (PlayerEntity playerEntity : level.players()) {
            if (test.test(playerEntity)) {
                for (int i = 0; i < vals.length; i++) {
                    if (parametrs[i].apply(playerEntity) > vals[i]) {
                        strongest = playerEntity;
                        for (int j = 0; j < vals.length; j++) {
                            vals[j] = parametrs[j].apply(strongest);
                        }
                        break;
                    } else if (parametrs[i].apply(playerEntity) < vals[i]) {
                        break;
                    }
                }
            }
        }
        return strongest;
    }

    @SubscribeEvent
    public static void onRankUp(LoyaltyEvent.Post event) {
        IEntityStats stats = EntityStatsCapability.get(event.getEntityLiving());
        MarineInfo info = MarineInfo.get(event.getEntityLiving().level);
        if (ModConfig.INSTANCE.isFleetAdmiralsLimited() && stats.getLoyalty() >= MarineRank.FLEET_ADMIRAL.getRequiredLoyalty() && stats.isMarine()) {
            info.addFleetAdmiral((PlayerEntity) event.getEntityLiving());
        } else if (ModConfig.INSTANCE.isAdmiralsLimited() && stats.getLoyalty() >= MarineRank.ADMIRAL.getRequiredLoyalty() && stats.isMarine()) {
            info.addAdmiral((PlayerEntity) event.getEntityLiving());
        }
        NonFruitReworkMod.LOGGER.info(ModConfig.INSTANCE.isAdmiralsLimited() && stats.getLoyalty() >= MarineRank.ADMIRAL.getRequiredLoyalty() && stats.isMarine());
    }
}
