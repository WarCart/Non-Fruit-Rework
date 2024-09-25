package net.warcar.non_fruit_rework.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.germa_genes.GeneDataCapability;
import net.warcar.non_fruit_rework.data.entity.germa_genes.IGeneData;
import net.warcar.non_fruit_rework.items.RaidSuitCapsule;
import net.warcar.non_fruit_rework.items.RaidSuitItem;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class GenesAwakeningEffects {
    @SubscribeEvent
    public static void tickEffect(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IGeneData genes = GeneDataCapability.get(entity);
        if (genes.isAwakenGenes()) {
            AttributeModifier toughnessModifier = new AttributeModifier(UUID.fromString("2be941e8-6ee1-4959-8f55-c6459daff147"), "Genes Toughness Bonus", 4, AttributeModifier.Operation.MULTIPLY_TOTAL);
            ModifiableAttributeInstance toughness = entity.getAttribute(ModAttributes.TOUGHNESS.get());
            if (!toughness.hasModifier(toughnessModifier))
                toughness.addPermanentModifier(toughnessModifier);

            AttributeModifier regenModifier = new AttributeModifier(UUID.fromString("f159174c-3c94-46dc-8f5c-45f4010dbe6c"), "Genes Regen Rate Bonus", 4, AttributeModifier.Operation.MULTIPLY_TOTAL);
            ModifiableAttributeInstance regen = entity.getAttribute(ModAttributes.REGEN_RATE.get());
            if (!regen.hasModifier(regenModifier))
                regen.addPermanentModifier(regenModifier);

            AttributeModifier speedModifier = new AttributeModifier(UUID.fromString("99466c31-c9e9-4f9e-a395-17ffb21e11d6"), "Genes Speed Bonus", 1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            ModifiableAttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (!speed.hasModifier(speedModifier))
                speed.addPermanentModifier(speedModifier);

            AttributeModifier jumpModifier = new AttributeModifier(UUID.fromString("4ddab506-df10-4c6c-96b4-ab8ed907318a"), "Genes Jump Height Bonus", 1, AttributeModifier.Operation.MULTIPLY_TOTAL);
            ModifiableAttributeInstance jump = entity.getAttribute(ModAttributes.JUMP_HEIGHT.get());
            if (!jump.hasModifier(jumpModifier))
                jump.addPermanentModifier(jumpModifier);

            AttributeModifier healthModifier = new AttributeModifier(UUID.fromString("754bf152-99bc-40f0-b51e-ab564bdacbda"), "Genes Health Bonus", .2, AttributeModifier.Operation.MULTIPLY_TOTAL);
            ModifiableAttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
            if (!health.hasModifier(healthModifier))
                health.addPermanentModifier(healthModifier);
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        boolean gave = false;
        for (ItemStack stack : entity.getAllSlots()) {
            if (stack.getItem() instanceof RaidSuitItem) {
                if (entity instanceof PlayerEntity && !gave) {
                    ItemStack capsule = ((RaidSuitItem) stack.getItem()).getCapsuleStack(stack);
                    ((PlayerEntity) entity).addItem(capsule);
                    gave = true;
                }
                stack.setCount(0);
            }
        }
    }

    @SubscribeEvent
    public static void setAttributes(EntityJoinWorldEvent event) {
        RaidSuitCapsule.ATTRIBUTES.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("930379a8-81f7-43d6-92ff-71ae3e66551e"), "Raid Suit Attack Boost", 3, AttributeModifier.Operation.MULTIPLY_TOTAL));
        RaidSuitCapsule.ATTRIBUTES.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("b703d1f2-28a3-11ef-982c-325096b39f47"), "Raid Suit Speed Boost", 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        RaidSuitCapsule.ATTRIBUTES.put(ModAttributes.JUMP_HEIGHT.get(), new AttributeModifier(UUID.fromString("b703d4fe-28a3-11ef-90c6-325096b39f47"), "Raid Suit Jump Boost", 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        RaidSuitCapsule.ATTRIBUTES.put(ModAttributes.TOUGHNESS.get(), new AttributeModifier(UUID.fromString("b703d5a8-28a3-11ef-bd4b-325096b39f47"), "Raid Suit Toughness Boost", 2, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
