package net.warcar.non_fruit_rework.renderers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.warcar.non_fruit_rework.entities.AfterimageEntity;
import net.warcar.non_fruit_rework.mixin.ILivingRendererMixin;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;

import java.util.List;
import java.util.Map;

public class AfterimageRenderer extends EntityRenderer<AfterimageEntity> {
    protected static final Map<AfterimageEntity, PlayerEntity> playerEntityMap = Maps.newHashMap();
    protected AfterimageRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(AfterimageEntity entity) {
        PlayerEntity player = entity.getPlayer();
        if (player instanceof AbstractClientPlayerEntity) {
            return ((AbstractClientPlayerEntity) player).getSkinTextureLocation();
        }
        return new ResourceLocation("null");
    }

    @Override
    public void render(AfterimageEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        Minecraft mc = Minecraft.getInstance();
        KenbunshokuHakiAuraAbility ability = AbilityDataCapability.get(mc.player).getEquippedAbility(KenbunshokuHakiAuraAbility.INSTANCE);
        if (ability != null && ability.isContinuous()) {
            return;
        }
        matrixStack.pushPose();
        PlayerEntity player = playerEntityMap.get(entity);
        if (player == null) {
            ClientPlayerEntity pl = (ClientPlayerEntity) entity.getPlayer();
            player = new ClientPlayerEntity(mc, mc.level, pl.connection, pl.getStats(), pl.getRecipeBook(), false, false);
            player.load(pl.saveWithoutId(new CompoundNBT()));
            player.yBodyRotO = pl.yBodyRot;
            player.yRotO = pl.yRot;
            player.xRotO = pl.xRot;
            player.yBodyRot = pl.yBodyRot;
            player.yRot = pl.yRot;
            player.xRot = pl.xRot;
            player.yHeadRot = pl.yHeadRot;
            player.yHeadRotO = pl.yHeadRot;
            player.getActiveEffectsMap().clear();
            playerEntityMap.put(entity, player);
        }
        EntityRenderer<? super PlayerEntity> renderer;
        if (MorphHelper.getZoanInfo(entity.getPlayer()) != null) {
            MorphInfo info = MorphHelper.getZoanInfo(entity.getPlayer());
            renderer = info.getRendererFactory(entity.getPlayer()).createRenderFor(entityRenderDispatcher);
            if (renderer instanceof ILivingRendererMixin) {
                ((ILivingRendererMixin) renderer).setModel(info.getModel());
            }
        } else {
            renderer = entityRenderDispatcher.getRenderer(player);
        }
        renderer.render(player, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        matrixStack.popPose();
        checkMap();
    }

    private static void checkMap() {
        List<AfterimageEntity> toRemove = Lists.newArrayList();
        playerEntityMap.forEach((entity1, player1) -> {
            if (!entity1.isAlive()) {
                toRemove.add(entity1);
            }
        });
        toRemove.forEach(playerEntityMap::remove);
    }

    public static class Factory implements IRenderFactory<AfterimageEntity> {
        @Override
        public EntityRenderer<? super AfterimageEntity> createRenderFor(EntityRendererManager manager) {
            return new AfterimageRenderer(manager);
        }
    }
}
