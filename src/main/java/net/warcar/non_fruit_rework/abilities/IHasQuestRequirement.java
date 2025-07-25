package net.warcar.non_fruit_rework.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.warcar.non_fruit_rework.api.events.CanUseAbilityModeEvent;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public interface IHasQuestRequirement extends IHasRequirements {
    @Override
    default boolean canGet(LivingEntity player) {
        if (this.getQuest() == null) {
            return true;
        }
        return QuestHelper.hasFinishedQuest(player, this.getQuest());
    }

    QuestId<?> getQuest();

    static <E extends Enum<E>> E next(E value) {
        return (E) value.getClass().getEnumConstants()[(value.ordinal() + 1) % value.getClass().getEnumConstants().length];
    }

    default void throwUnfinishedQuest() {
        if (this.getQuest() != null) {
            throw new QuestHelper.UnfinishedQuestException(this.getQuest());
        }
    }

    static <E extends Enum<E> & IHasQuestRequirement> void addAltModeEvent(AltModeComponent<E> component) {
        component.addChangeModeEvent((livingEntity, iAbility, e) -> {
            if (!trueUnlock(livingEntity, e)) {
                component.setMode(livingEntity, IHasQuestRequirement.next(e));
                e.throwUnfinishedQuest();
            } else if (e instanceof IHasTexture && iAbility instanceof Ability) {
                ((Ability) iAbility).setDisplayIcon(((IHasTexture) e).getTexture());
            }
        });
    }

    static <E extends Enum<E> & IHasQuestRequirement> boolean trueUnlock(LivingEntity livingEntity, E e) {
        CanUseAbilityModeEvent event = new CanUseAbilityModeEvent(livingEntity, e);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == CanUseAbilityModeEvent.Result.ALLOW) {
            return true;
        } else if (event.getResult() == CanUseAbilityModeEvent.Result.DENY) {
            return false;
        }
        return e.canGet(livingEntity);
    }
}
