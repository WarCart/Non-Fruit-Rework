package net.warcar.non_fruit_rework.mixins;

import net.warcar.non_fruit_rework.abilities.black_leg.MoutonShotAbility;
import net.warcar.non_fruit_rework.abilities.swordsman.AshuraIchibuginAbility;
import net.warcar.non_fruit_rework.abilities.swordsman.SanzenSekaiAbility;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.*;
import xyz.pixelatedw.mineminenomi.abilities.swordsman.*;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

@Mixin(ModAbilities.class)
public class ModAbilitiesMixin {
    @Shadow
    @Final
    public static final AbilityCore[] BLACK_LEG_ABILITIES = new AbilityCore[]{DiableJambeAbility.INSTANCE, SkywalkAbility.INSTANCE, AntiMannerKickCourseAbility.INSTANCE, ExtraHachisAbility.INSTANCE, PartyTableKickCourseAbility.INSTANCE, ConcasseAbility.INSTANCE, BienCuitGrillShotAbility.INSTANCE, MoutonShotAbility.INSTANCE, BlackLegPassiveBonusesAbility.INSTANCE};

    @Shadow
    @Final
    public static final AbilityCore[] SWORDSMAN_ABILITIES = new AbilityCore[]{SanzenSekaiAbility.INSTANCE, ShiShishiSonsonAbility.INSTANCE, YakkodoriAbility.INSTANCE, SanbyakurokujuPoundHoAbility.INSTANCE, OTatsumakiAbility.INSTANCE, AshuraIchibuginAbility.INSTANCE, HiryuKaenAbility.INSTANCE};
}
