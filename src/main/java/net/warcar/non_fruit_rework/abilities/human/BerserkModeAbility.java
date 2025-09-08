package net.warcar.non_fruit_rework.abilities.human;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.init.ModTexts;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.packets.server.SAddScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SRemoveScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.UUID;

public class BerserkModeAbility extends Ability {
    public static final AbilityCore<BerserkModeAbility> INSTANCE = new AbilityCore.Builder<>("Berserk Mode", AbilityCategory.RACIAL, BerserkModeAbility::new)
            .setUnlockCheck(RageMeterAbility::canUnlock).setIcon(new ResourceLocation(ModMain.PROJECT_ID, "textures/abilities/rokuogan.png")).build();

    private final ContinuousComponent continuousComponent = new ContinuousComponent(this, true)
            .addStartEvent(this::startContinuous).addEndEvent(this::endContinuous);
    private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("cae897a8-ca4b-4ab7-b25a-ea05af4be212"), "Berserk Strength", 2, AttributeModifier.Operation.MULTIPLY_TOTAL), entity -> continuousComponent.isContinuous());

    public BerserkModeAbility(AbilityCore<BerserkModeAbility> core) {
        super(core);
        this.isNew = true;
        this.addComponents(continuousComponent, statsComponent);
        this.addUseEvent(this::onUse);
        this.addCanUseCheck(this::canUse);
    }

    private AbilityUseResult canUse(LivingEntity entity, IAbility iAbility) {
        IAbilityData data = AbilityDataCapability.get(entity);
        RageMeterAbility ability = data.getPassiveAbility(RageMeterAbility.INSTANCE);
        if (ability != null && ability.getRage() == 100) {
            return AbilityUseResult.success();
        }
        return AbilityUseResult.fail(ModTexts.REQUIRES_RAGE);
    }

    private void onUse(LivingEntity entity, IAbility ability) {
        this.continuousComponent.triggerContinuity(entity);
    }

    private void startContinuous(LivingEntity livingEntity, IAbility iAbility) {
        if (livingEntity.level.isClientSide) {
            return;
        }
        IAbilityData abilityData = AbilityDataCapability.get(livingEntity);
        abilityData.getEquippedAbilities().forEach(ability -> {
            if (ability.getCore() != GeppoAbility.INSTANCE && ability.getCore() != SoruAbility.INSTANCE) {
                ability.getComponent(ModAbilityKeys.COOLDOWN).ifPresent(cooldownComponent -> {
                    cooldownComponent.stopCooldown(livingEntity);
                    cooldownComponent.getBonusManager().addBonus(UUID.fromString("c296909b-43f3-4043-8be2-783ff18d2b05"), "Berserk Cooldown bonus", BonusOperation.MUL, 0.1f);
                });
                ability.getComponent(ModAbilityKeys.CHARGE).ifPresent(cooldownComponent -> {
                    cooldownComponent.getMaxChargeBonusManager().addBonus(UUID.fromString("c296909b-43f3-4043-8be2-783ff18d2b05"), "Berserk Charge bonus", BonusOperation.MUL, 0.5f);
                });
            }
        });
        if (livingEntity instanceof PlayerEntity) {
            WyNetwork.sendTo(new SAddScreenShaderPacket(new ResourceLocation(NonFruitReworkMod.MOD_ID, "shaders/post/berserk.json")), (PlayerEntity) livingEntity);
        }
    }

    private void endContinuous(LivingEntity livingEntity, IAbility iAbility) {
        AbilityDataCapability.get(livingEntity).getEquippedAbilities().forEach(ability -> {
            ability.getComponent(ModAbilityKeys.COOLDOWN).ifPresent(cooldownComponent -> {
                cooldownComponent.getBonusManager().removeBonus(UUID.fromString("c296909b-43f3-4043-8be2-783ff18d2b05"));
            });
            ability.getComponent(ModAbilityKeys.CHARGE).ifPresent(cooldownComponent -> {
                cooldownComponent.getMaxChargeBonusManager().removeBonus(UUID.fromString("c296909b-43f3-4043-8be2-783ff18d2b05"));
            });
        });
        if (livingEntity instanceof ServerPlayerEntity) {
            WyNetwork.sendTo(new SRemoveScreenShaderPacket(new ResourceLocation(NonFruitReworkMod.MOD_ID, "shaders/post/berserk.json")), (PlayerEntity) livingEntity);
        }
    }
}
