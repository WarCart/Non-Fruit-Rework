package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

public class TimedAbilityUseObjective extends Objective implements ISurviveObjective {
    private final AbilityCore<?> core;
    private final Check check;

    public TimedAbilityUseObjective(String title, AbilityCore<?> core, int ticks) {
        this(title, core, ticks, (e, a) -> true);
    }

    public TimedAbilityUseObjective(String title, AbilityCore<?> core, int ticks, Check check) {
        super(title);
        this.core = core;
        this.check = check;
        this.setMaxProgress(ticks);
    }

    @Override
    public boolean checkTime(PlayerEntity playerEntity) {
        IAbility ability = AbilityDataCapability.get(playerEntity).getEquippedAbility(core);
        return ability != null && ability.getComponent(ModAbilityKeys.CONTINUOUS).map(ContinuousComponent::isContinuous).orElse(false) && check.checkAbility(playerEntity, ability);
    }

    @FunctionalInterface
    public interface Check {
        boolean checkAbility(PlayerEntity playerEntity, IAbility ability);
    }
}
