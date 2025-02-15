package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class TakeDamageObjective extends Objective {
    private final IDamageCheck check;

    public TakeDamageObjective(String title, float damage) {
        this(title, damage, (player, amount, source) -> true);
    }

    public TakeDamageObjective(String title, float damage, IDamageCheck check) {
        super(title);
        this.check = check;
        this.setMaxProgress(damage);
    }

    public IDamageCheck getCheck() {
        return check;
    }

    @FunctionalInterface
    public interface IDamageCheck {
        boolean valid(PlayerEntity player, float amount, DamageSource source);
    }
}
