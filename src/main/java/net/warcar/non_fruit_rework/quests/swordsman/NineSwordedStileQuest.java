package net.warcar.non_fruit_rework.quests.swordsman;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial04Quest;

public class NineSwordedStileQuest extends Quest {
    public static final QuestId<NineSwordedStileQuest> INSTANCE = (new QuestId.Builder<>("Trial: Nine-Sword Style", NineSwordedStileQuest::new)).addRequirements(SwordsmanTrial04Quest.INSTANCE).build();

    public NineSwordedStileQuest(QuestId core) {
        super(core);
        this.addObjective(new KillEntityObjective("Kill %s enemies with critical attacks using sword", 5, SharedKillChecks.HAS_SWORD.and(SharedKillChecks.CRITICAL_KILL_CHECK)));
        this.addObjective(new KillEntityObjective("Kill %s enemies using O Tatsumaki", 20, SharedKillChecks.checkAbilitySource(OTatsumakiAbility.INSTANCE)));
        this.addObjective(new KillEntityObjective("Kill %s enemies using Sanbyakurokuju Pound Ho", 25, SharedKillChecks.checkAbilitySource(SanbyakurokujuPoundHoAbility.INSTANCE)));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(RokuoganAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
