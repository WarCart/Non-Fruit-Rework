package net.warcar.non_fruit_rework.abilities.test;

import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.StringTextComponent;
import net.warcar.non_fruit_rework.abilities.components.ComboAbilityComponent;
import net.warcar.non_fruit_rework.entities.projectiles.IkokuProjectile;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.brawler.KingPunchProjectile;

import java.util.List;

public class IkokuAbility extends Ability {
    public static final AbilityCore<IkokuAbility> INSTANCE = new AbilityCore.Builder<>("Ikoku", AbilityCategory.RACIAL, IkokuAbility::new)
            .setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.SHOCKWAVE).build();

    private final ComboAbilityComponent comboComponent = new ComboAbilityComponent(this, new ComboAbilityComponent.ComboCore(false, new AbilityCore[]{INSTANCE}));
    private final ProjectileComponent projectileComponent = new ProjectileComponent(this, IkokuProjectile::new);

    public IkokuAbility(AbilityCore<IkokuAbility> core) {
        super(core);
        this.isNew = true;
        this.addCanUseCheck(AbilityHelper::canUseWeaponAbilities);
        comboComponent.addUseEvent(this::useCombo);
        this.addComponents(comboComponent, projectileComponent);
        this.addUseEvent((entity, ability) -> comboComponent.use(entity));
    }

    private void useCombo(LivingEntity user, LivingEntity target, boolean main, List<Pair<Ability, LivingEntity>> calledAbilities) {
        if (target != null)
            user.lookAt(EntityAnchorArgument.Type.EYES, target.position().add(0.0, target.getEyeHeight(), 0.0));
        if (main) {
            this.projectileComponent.shoot(user);
            this.projectileComponent.getShotProjectile().setDamage(15 * (calledAbilities.size() + 1));
            this.projectileComponent.getShotProjectile().onBlockImpactEvent = blockPos -> {
                ExplosionAbility ability = AbilityHelper.newExplosion(user, user.level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), (calledAbilities.size() + 1) * 4);
                ability.doExplosion();
            };
        }
        if (calledAbilities.isEmpty()) {
            this.setDisplayName(new StringTextComponent("Ikoku"));
        } else if (HakiHelper.hasInfusionActive(user)) {
            boolean allHasHaki = true;
            for (Pair<Ability, LivingEntity> pair : calledAbilities) {
                if (!HakiHelper.hasInfusionActive(pair.getRight())) {
                    allHasHaki = false;
                    break;
                }
            }
            if (allHasHaki) {
                this.setDisplayName(new StringTextComponent("Hakai"));
            } else {
                this.setDisplayName(new StringTextComponent("Hakoku"));
            }
        } else {
            this.setDisplayName(new StringTextComponent("Hakoku"));
        }
        user.getMainHandItem().hurtAndBreak(100, user, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        this.cooldownComponent.startCooldown(user, 1000);
    }
}
