package net.warcar.non_fruit_rework.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.abilities.swordsman.AshuraIchibuginAbility;
import net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black.StealthBlackInvisibility;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import net.warcar.non_fruit_rework.enums.Style;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.damagesource.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.api.events.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.api.events.WyLivingHurtEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.mixins.client.ILivingRendererMixin;
import xyz.pixelatedw.mineminenomi.packets.client.CFinishCCPacket;

import java.awt.*;
import java.util.Collection;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class AbilityEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void onHit(LivingAttackEvent event) {
        StealthBlackInvisibility invisibility = AbilityDataCapability.get(event.getEntityLiving()).getEquippedAbility(StealthBlackInvisibility.INSTANCE);
        if (!(invisibility == null || event.isCanceled())) {
            invisibility.onDamageTaken(event.getEntityLiving(), event.getAmount());
        }
    }

    @SubscribeEvent
    public static void damageRecalculation(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getEntity();
            {
                Collection<AttributeModifier> collection = player.getItemBySlot(EquipmentSlotType.OFFHAND).getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE);
                for (AttributeModifier modifier : collection) {
                    if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
                        event.setAmount((float) modifier.getAmount() + event.getAmount());
                }
                if (!collection.isEmpty()) {
                    player.getItemBySlot(EquipmentSlotType.OFFHAND).hurtEnemy(event.getEntityLiving(), player);
                }
            }
            {
                Collection<AttributeModifier> collection = player.getItemBySlot(EquipmentSlotType.HEAD).getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE);
                for (AttributeModifier modifier : collection) {
                    if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
                        event.setAmount((float) modifier.getAmount() + event.getAmount());
                }
                if (!collection.isEmpty()) {
                    player.getItemBySlot(EquipmentSlotType.HEAD).hurtEnemy(event.getEntityLiving(), player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void mountSword(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack hand = player.getMainHandItem();
        if (player.isSteppingCarefully() && ItemsHelper.isSword(hand)) {
            ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD).copy();
            player.setItemSlot(EquipmentSlotType.HEAD, hand.copy());
            hand.shrink(1);
            player.addItem(head);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void ashuraRender(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
        AshuraIchibuginAbility ability = AbilityDataCapability.get(event.getEntity()).getEquippedAbility(AshuraIchibuginAbility.INSTANCE);
        LivingEntity entity = event.getEntity();
        if (ability != null && ability.isCharging()) {
            MatrixStack stack = event.getMatrixStack();
            {
                stack.pushPose();
                stack.mulPose(Vector3f.YN.rotationDegrees(entity.yRot));
                stack.translate(0.35, 0, -0.15);
                stack.mulPose(Vector3f.YN.rotationDegrees(150));
                stack.scale(-1, 1, 1);
                render(event, entity, stack);
                stack.popPose();
            }
            {
                stack.pushPose();
                stack.mulPose(Vector3f.YN.rotationDegrees(entity.yRot));
                stack.translate(-0.35, 0, -0.15);
                stack.mulPose(Vector3f.YP.rotationDegrees(150));
                render(event, entity, stack);
                stack.popPose();
            }
        }
    }

    private static void render(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event, LivingEntity entity, MatrixStack stack) {
        LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer = event.getRenderer();
        stack.scale(-1, -1, 1);
        stack.translate(0, -1.5, 0);
        renderer.getModel().renderToBuffer(stack, event.getBuffers().getBuffer(renderer.getModel().renderType(renderer.getTextureLocation(entity))), event.getLight(), OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.75f);
        for (LayerRenderer<LivingEntity, EntityModel<LivingEntity>> layerRenderer : ((ILivingRendererMixin<LivingEntity, EntityModel<LivingEntity>>) renderer).getLayers()) {
            float f5 = entity.animationPosition - entity.animationSpeed * (1.0F - event.getPartialRenderTick());
            float f8 = MathHelper.lerp(event.getPartialRenderTick(), entity.animationSpeedOld, entity.animationSpeed);
            float f = MathHelper.rotLerp(event.getPartialRenderTick(), entity.yBodyRotO, entity.yBodyRot);
            float f1 = MathHelper.rotLerp(event.getPartialRenderTick(), entity.yHeadRotO, entity.yHeadRot);
            float f2 = f1 - f;
            float f6 = MathHelper.lerp(event.getPartialRenderTick(), entity.xRotO, entity.xRot);
            layerRenderer.render(stack, event.getBuffers(), event.getLight(), entity, f5, f8, event.getPartialRenderTick(), entity.tickCount + event.getPartialRenderTick(), f2, f6);
        }
        HaoshokuHakiInfusionAbility hakiInfusion = AbilityDataCapability.get(entity).getEquippedAbility(HaoshokuHakiInfusionAbility.INSTANCE);
        if (hakiInfusion != null && hakiInfusion.isContinuous()) {
            stack.pushPose();
            stack.scale(1.01f, 1.01f, 1.01f);
            Color color = new Color(HakiHelper.getHaoshokuColour(entity));
            renderer.getModel().renderToBuffer(stack, event.getBuffers().getBuffer(ModRenderTypes.ENERGY), event.getLight(), OverlayTexture.NO_OVERLAY, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 0.25f);
            stack.popPose();
        }
    }
}
