package net.warcar.non_fruit_rework.abilities.raid_suit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.warcar.non_fruit_rework.items.RaidSuitItem;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class CapeGuard extends Ability {
    public static final AbilityCore<CapeGuard> INSTANCE;
    private final ContinuousComponent continuousComponent;
    private final PoolComponent poolComponent;


    public CapeGuard(AbilityCore<CapeGuard> core) {
        super(core);
        continuousComponent = new ContinuousComponent(this);
        poolComponent = new PoolComponent(this, ModAbilityPools.TEKKAI_LIKE);
        this.addComponents(continuousComponent, poolComponent);
        this.isNew = true;
        this.addUseEvent((entity, ability) -> this.continuousComponent.triggerContinuity(entity, 300));
        this.continuousComponent.addTickEvent(this::duringContinuity);
        this.continuousComponent.addEndEvent((livingEntity, iAbility) -> this.cooldownComponent.startCooldown(livingEntity, 100));

    }

    private static boolean onStartContinuityEvent(LivingEntity player) {
        return player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof RaidSuitItem;
    }

    private void duringContinuity(LivingEntity player, IAbility ability) {
        player.addEffect(new EffectInstance(ModEffects.GUARDING.get(), 2, 0, false, false));
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Cape Guard", AbilityCategory.EQUIPMENT, CapeGuard::new).setUnlockCheck(CapeGuard::onStartContinuityEvent).addDescriptionLine("User covers with his cape to protect himself from damage").build();
    }
}
