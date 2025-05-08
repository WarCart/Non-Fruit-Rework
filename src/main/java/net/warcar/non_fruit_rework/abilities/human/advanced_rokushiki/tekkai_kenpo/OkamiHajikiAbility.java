package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.tekkai_kenpo;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.TekkaiMode;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.OkamiHajikiQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility2;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RequireAbilityComponent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

import java.util.function.Predicate;

public class OkamiHajikiAbility extends PunchAbility2 {
    public static final AbilityCore<OkamiHajikiAbility> INSTANCE = new AbilityCore.Builder<>("Tekkai Kenpo: Okami Hajiki", AbilityCategory.RACIAL, OkamiHajikiAbility::new)
            .setUnlockCheck(QuestHelper.questFinished(OkamiHajikiQuest.INSTANCE)).build();
    public static final RequireAbilityComponent.CheckData<TekkaiAbility> CHECK = new RequireAbilityComponent.CheckData<>(TekkaiAbility.INSTANCE, (livingEntity, abilityCore) -> {
        TekkaiAbility ability = AbilityDataCapability.get(livingEntity).getEquippedAbility(abilityCore);
        if (ability == null) {
            return false;
        }
        boolean isKenpo = ability.getComponent(ModAbilityKeys.ALT_MODE).map(comp -> comp.getCurrentMode() == TekkaiMode.TEKKAI_KENPO).orElse(false);
        return ability.isContinuous() && isKenpo;
    });

    private final RequireAbilityComponent requireAbilityComponent = new RequireAbilityComponent(this, CHECK);

    public OkamiHajikiAbility(AbilityCore<OkamiHajikiAbility> core) {
        super(core);
        this.addComponents(requireAbilityComponent);
    }

    @Override
    public float getPunchCooldown() {
        return 200;
    }

    @Override
    public boolean onHitEffect(LivingEntity livingEntity, LivingEntity livingEntity1, ModDamageSource modDamageSource) {
        return true;
    }

    @Override
    public Predicate<LivingEntity> canActivate() {
        return e -> this.isContinuous() && e.getMainHandItem().isEmpty();
    }

    @Override
    public int getUseLimit() {
        return 1;
    }

    @Override
    public float getPunchDamage() {
        return 25;
    }

    @Override
    public boolean isParallel() {
        return true;
    }
}
