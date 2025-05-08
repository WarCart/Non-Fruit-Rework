package net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import net.warcar.non_fruit_rework.quest.objectives.TimedAbilityUseObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.TekkaiQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class TekkaiKenpoQuest extends Quest implements IHasRequirements {
    public static final QuestId<TekkaiKenpoQuest> INSTANCE = new QuestId.Builder<>("Trial: Tekkai Kenpo", TekkaiKenpoQuest::new)
            .addRequirements(TekkaiQuest.INSTANCE).build();

    public TekkaiKenpoQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 1300);
        this.addObjective(objective);
        this.addObjective(new TakeDamageObjective("Take %s Damage while using tekkai", 50, this::checkDamage).addRequirement(objective));
        this.addObjective(new TimedAbilityUseObjective("Use tekkai for %s seconds", TekkaiAbility.INSTANCE, 1000).addRequirement(objective));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.canUseAdvancedRokushiki(player);
    }

    private boolean checkDamage(PlayerEntity player, float amount, DamageSource source) {
        TekkaiAbility ability = AbilityDataCapability.get(player).getEquippedAbility(TekkaiAbility.INSTANCE);
        return ability != null && ability.isContinuous();
    }
}
