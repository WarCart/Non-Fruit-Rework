package net.warcar.non_fruit_rework.quests.swordsman;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.abilities.swordsman.AshuraIchibuginAbility;
import net.warcar.non_fruit_rework.abilities.swordsman.SanzenSekaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial05Quest;

public class SanzenSekaiQuest extends Quest {
    public static final QuestId<SanzenSekaiQuest> INSTANCE = (new QuestId.Builder<>("Trial: Sanzen Sekai", SanzenSekaiQuest::new)).addRequirements(SwordsmanTrial05Quest.INSTANCE, NineSwordedStileQuest.INSTANCE).build();

    public SanzenSekaiQuest(QuestId core) {
        super(core);
        this.addObjective(new KillEntityObjective("Kill %s enemies using O Tatsumaki", 40, SharedKillChecks.checkAbilitySource(OTatsumakiAbility.INSTANCE)));
        this.addObjective(new KillEntityObjective("Kill %s enemies using Sanbyakurokuju Pound Ho", 50, SharedKillChecks.checkAbilitySource(SanbyakurokujuPoundHoAbility.INSTANCE)));
        this.addObjective(new KillEntityObjective("Kill %s enemies using Shi Shishi Sonson", 25, SharedKillChecks.checkAbilitySource(ShiShishiSonsonAbility.INSTANCE)));
        this.addObjective(new KillEntityObjective("Kill %s enemies using Hiryu: Kaen", 15, SharedKillChecks.checkAbilitySource(HiryuKaenAbility.INSTANCE)));
        this.addObjective(new KillEntityObjective("Kill %s enemies using Ashura: Ichibugin", 15, SharedKillChecks.checkAbilitySource(AshuraIchibuginAbility.INSTANCE)));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(SanzenSekaiAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
