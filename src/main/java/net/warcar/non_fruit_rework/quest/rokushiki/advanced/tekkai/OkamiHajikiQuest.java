package net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.tekkai_kenpo.OkamiHajikiAbility;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class OkamiHajikiQuest extends Quest implements IHasRequirements {
    public static final QuestId<OkamiHajikiQuest> INSTANCE = new QuestId.Builder<>("Tekkai Kenpo Trial: Okami Haijiki", OkamiHajikiQuest::new)
            .addRequirements(TekkaiKenpoQuest.INSTANCE).build();

    public OkamiHajikiQuest(QuestId core) {
        super(core);
        this.addObjective(new KillEntityObjective("Kill %s enemies using your fists while using tekkai kenpo", 20, SharedKillChecks.HAS_EMPTY_HAND.and(OkamiHajikiQuest::test)));
        this.onCompleteEvent = this::check;
    }

    private static boolean test(PlayerEntity player, LivingEntity target, DamageSource source) {
        TekkaiAbility ability = AbilityDataCapability.get(player).getEquippedAbility(TekkaiAbility.INSTANCE);
        return ability != null && ability.isContinuous();
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }

    private boolean check(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(OkamiHajikiAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}
