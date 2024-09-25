package net.warcar.non_fruit_rework.abilities.raid_suit.sparking_red;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;

import java.util.function.Predicate;

public class SparkingFigureAbility extends PunchAbility2 {
    public static final AbilityCore<SparkingFigureAbility> INSTANCE;
    private final AltModeComponent<Mode> modeComponent;
    protected boolean doubleMode = false;

    public SparkingFigureAbility(AbilityCore<SparkingFigureAbility> core) {
        super(core);
        modeComponent = new AltModeComponent<>(this, Mode.class, Mode.SINGLE);
        this.addComponents(modeComponent);
    }

    public boolean isDouble() {
        return this.modeComponent.isMode(Mode.DOUBLE);
    }

    static {
        INSTANCE = new AbilityCore.Builder<>("Sparking Figure", AbilityCategory.EQUIPMENT, SparkingFigureAbility::new).addDescriptionLine("Powerful punch that creates explosion\n\n§2Shift+use§r: changes mode to Double").build();
    }

    public float getPunchCooldown() {
        if (this.doubleMode) {
            return 400;
        }
        return 200;
    }

    public void onHitEffect(LivingEntity entity, LivingEntity target, ModDamageSource modDamageSource) {
        modDamageSource.bypassArmor();
        int multiplier = 1;
        if (this.isDouble()) {
            multiplier = 3;
            entity.hurt(ModDamageSource.causeAbilityDamage(entity, INSTANCE), 10);
        }
        ExplosionAbility explosion = AbilityHelper.newExplosion(target, target.level, target.getX(), target.getY(), target.getZ(), 4.0f * multiplier);
        explosion.setDestroyBlocks(false);
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(5 * multiplier));
        explosion.setDamageEntities(false);
        explosion.doExplosion();
    }

    public Predicate<LivingEntity> canActivate() {
        return entity -> this.continuousComponent.isContinuous() && AbilityHelper.canUseBrawlerAbilities(entity);
    }

    public int getUseLimit() {
        return 1;
    }

    @Override
    public float getPunchDamage() {
        if (this.isDouble()) {
            return 180;
        }
        return 60;
    }

    public enum Mode {
        DOUBLE,
        SINGLE
    }
}
