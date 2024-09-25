package net.warcar.non_fruit_rework.events;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.mastery.IMasteryData;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import net.warcar.non_fruit_rework.enums.Style;
import net.warcar.non_fruit_rework.events.api.QuestFinishedEvent;
import net.warcar.non_fruit_rework.init.ModConfig;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.damagesource.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.events.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.api.events.WyLivingHurtEvent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.events.QuestEvents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class MasteryGainEvents {
    @SubscribeEvent
    public static void onDamage(WyLivingHurtEvent event) {
    if (event.getSource() instanceof ModDamageSource && event.getSource().getEntity() instanceof LivingEntity) {
        ModDamageSource source = (ModDamageSource) event.getSource();
        LivingEntity entity = (LivingEntity) source.getEntity();
        Style style = Style.getFromName(EntityStatsCapability.get(entity).getFightingStyle());
        if ((source instanceof AbilityDamageSource && ((AbilityDamageSource) source).getAbilitySource().getCategory() == AbilityCategory.STYLE) || (style != null && style.isTrained(source))) {
            IMasteryData data = MasteryDataCapability.get(entity);
            int multiplier;
            if (HakiDataCapability.get(event.getEntityLiving()).getTotalHakiExp() > HakiDataCapability.get(entity).getTotalHakiExp()) {
                multiplier = 1;
            } else {
                multiplier = 10;
            }
            data.addMastery(getFinalHakiExp(event.getAmount() / 100, multiplier, data));
        }
    }
}

    @SubscribeEvent
    public static void resetMastery(SetPlayerDetailsEvent event) {
        MasteryDataCapability.get(event.getEntityLiving()).setMastery(0);
        QuestDataCapability.get(event.getPlayer()).clearFinishedQuests();
    }

    @SubscribeEvent
    public static void onEndedQuest(QuestFinishedEvent event) {
        MasteryDataCapability.get(event.getPlayer()).addMastery(10);
    }

    private static float getFinalHakiExp(float val, int multiplier, IMasteryData data) {
        float currentHaki = data.getMastery();
        float finalExp = val * ((float) ModConfig.INSTANCE.getMaxMastery() - currentHaki) / (float)multiplier / 10000.0F;
        return Math.max(0.001F, finalExp);
    }
}
