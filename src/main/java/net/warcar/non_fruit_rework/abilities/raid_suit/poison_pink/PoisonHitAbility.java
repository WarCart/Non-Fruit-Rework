package net.warcar.non_fruit_rework.abilities.raid_suit.poison_pink;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.function.Predicate;

public class PoisonHitAbility extends PunchAbility2 {
    public static final AbilityCore<PoisonHitAbility> INSTANCE;

    public PoisonHitAbility(AbilityCore<PoisonHitAbility> core) {
        super(core);
    }

    public void onHitEffect(LivingEntity entity, LivingEntity target, ModDamageSource source) {
        target.addEffect(new EffectInstance(ModEffects.DOKU_POISON.get(), 400, 0));
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Poison Hit", AbilityCategory.EQUIPMENT, PoisonHitAbility::new)
                .addDescriptionLine("Hit infused with strong poison").build();
    }

    public float getPunchCooldown() {
        return 40;
    }

    public Predicate<LivingEntity> canActivate() {
        return entity -> this.continuousComponent.isContinuous();
    }

    public int getUseLimit() {
        return -1;
    }
}
