package net.warcar.non_fruit_rework.abilities.human;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdatePassiveAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class RageMeterAbility extends PassiveAbility2 {
    public static final AbilityCore<RageMeterAbility> INSTANCE = new AbilityCore.Builder<>("Rage Meter", AbilityCategory.RACIAL, AbilityType.PASSIVE, RageMeterAbility::new)
            .setUnlockCheck(RageMeterAbility::canUnlock).build();

    private float rage = 0;
    private int ragedTicks = 0;
    private final DamageTakenComponent damageTakenComponent = new DamageTakenComponent(this, this::damageCheck, DamageTakenComponent.DamageState.DAMAGE);

    public RageMeterAbility(AbilityCore<RageMeterAbility> core) {
        super(core);
        if (this.isClientSide()) {
            GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
            this.addComponents(gaugeComponent);
        }
        this.addComponents(damageTakenComponent);
        this.addDuringPassiveEvent(this::duringPassive);
    }

    public static boolean canUnlock(LivingEntity livingEntity) {
        IEntityStats stats = EntityStatsCapability.get(livingEntity);
        return EntityHelper.isAnyRace(livingEntity, ModValues.HUMAN) && stats.getDoriki() > 4500;
    }

    @OnlyIn(Dist.CLIENT)
    private void renderGauge(PlayerEntity playerEntity, MatrixStack matrixStack, int x, int y, RageMeterAbility ability) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bind(ModResources.WIDGETS);
        float brightness = this.rage == 100 ? 1 : 0.5f;
        RendererHelper.drawAbilityIcon(BerserkModeAbility.INSTANCE, matrixStack, (float)x, (float)(y - 38), 0, 32, 32, brightness, brightness, brightness);
        if (rage != 100) {
            String text = Integer.toString((int) ability.rage);
            WyHelper.drawStringWithBorder(mc.font, matrixStack, text, x + 15 - mc.font.width(text) / 2, y - 25, -1);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putFloat("rage", rage);
        return super.save(nbt);
    }

    @Override
    public void load(CompoundNBT nbt) {
        super.load(nbt);
        this.rage = nbt.getFloat("rage");
    }

    public float getRage() {
        return rage;
    }

    private float damageCheck(LivingEntity livingEntity, IAbility ign, DamageSource damageSource, float amount) {
        this.rage += amount / 2f;
        rage = MathHelper.clamp(rage, 0, 100);
        this.ragedTicks = 40;
        if (livingEntity instanceof PlayerEntity && !livingEntity.level.isClientSide) {
            WyNetwork.sendTo(new SUpdatePassiveAbilityDataPacket(livingEntity, this), (PlayerEntity) livingEntity);
        }
        return amount;
    }

    private void duringPassive(LivingEntity livingEntity) {
        if (livingEntity.level.isClientSide) {
            return;
        }
        if (ragedTicks != 0) {
            ragedTicks--;
            return;
        }
        if (rage > 80) {
            rage -= 0.075f;
        } else if (rage > 50) {
            rage -= 0.05f;
        } else {
            rage -= 0.025f;
        }
        rage = MathHelper.clamp(rage, 0, 100);
        if (livingEntity instanceof PlayerEntity) {
            WyNetwork.sendTo(new SUpdatePassiveAbilityDataPacket(livingEntity, this), (PlayerEntity) livingEntity);
        }
    }
}
