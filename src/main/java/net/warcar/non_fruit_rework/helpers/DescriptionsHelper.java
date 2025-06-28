package net.warcar.non_fruit_rework.helpers;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModI18n;

import java.util.Map;
import java.util.Set;

public final class DescriptionsHelper {
    private static final StringTextComponent PERCENTAGE_SIGN = new StringTextComponent("%");
    private DescriptionsHelper() {} //Do not initialize

    public static void addDescription(Set<AbilityDescriptionLine> set, boolean isAdvanced, AbilityDescriptionLine.IDescriptionLine... description) {
        for (AbilityDescriptionLine.IDescriptionLine line : description) {
            set.add(AbilityDescriptionLine.of(line, isAdvanced));
        }
    }

    public static void addDescription(Set<AbilityDescriptionLine> set, ITextComponent... description) {
        for (ITextComponent line : description) {
            set.add(AbilityDescriptionLine.of(line));
        }
    }

    public static <M extends Enum<M>> AbilityDescriptionLine.IDescriptionLine getCDTooltip(Map<M, Pair<Float, Float>> cd) {
        return (e, a) -> {
            Pair<Float, Float> cooldown = a.getComponent(ModAbilityKeys.ALT_MODE).map(AltModeComponent::getCurrentMode).map(cd::get).orElse(Pair.of(0.0F, 0.0F));
            float minSeconds = (float)Math.round(cooldown.getFirst() / 20.0F);
            float maxSeconds = (float)Math.round(cooldown.getSecond() / 20.0F);
            AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18n.ABILITY_DESCRIPTION_STAT_NAME_COOLDOWN, minSeconds, maxSeconds)).withUnit(ModI18n.ABILITY_DESCRIPTION_STAT_UNIT_SECONDS);
            a.getComponent(ModAbilityKeys.COOLDOWN).ifPresent((comp) -> {
                float minBonus = (float)Math.round((comp.getBonusManager().applyBonus(minSeconds) - minSeconds) / 20.0F);
                float maxBonus = (float)Math.round((comp.getBonusManager().applyBonus(maxSeconds) - maxSeconds) / 20.0F);
                float diffBonus = minBonus + maxBonus;
                AbilityStat.AbilityStatType bonusType = diffBonus < 0.0F ? AbilityStat.AbilityStatType.BUFF : (diffBonus > 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
                statBuilder.withBonus(minBonus, maxBonus, bonusType);
            });
            return statBuilder.build().getStatDescription();
        };
    }

    public static AbilityDescriptionLine.IDescriptionLine[] getProjectileTooltips() {
        AbilityDescriptionLine.IDescriptionLine[] list = new AbilityDescriptionLine.IDescriptionLine[3];
        list[0] = (entity, ability) -> new StringTextComponent("§a" + ModI18n.ABILITY_DESCRIPTION_STAT_NAME_PROJECTILE.getString() + "§r");
        list[1] = getDamageFromProjectileTooltip();
        list[2] = getPiercingFromProjectileTooltip();
        return list;
    }

    private static AbilityDescriptionLine.IDescriptionLine getDamageFromProjectileTooltip() {
        return (entity, ability) -> {
            AbilityProjectileEntity proj = ability.getComponent(ModAbilityKeys.PROJECTILE).map((comp) -> (AbilityProjectileEntity) comp.getNewProjectile(entity)).orElse(null);
            if (proj != null && proj.getDamage() > 0.0F) {
                float bonus = ability.getComponent(ModAbilityKeys.PROJECTILE).map(ProjectileComponent::getDamageBonusManager).map((manager) -> manager.applyBonus(proj.getDamage()) - proj.getDamage()).orElse(0.0F);
                AbilityStat.AbilityStatType bonusType = bonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (bonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
                AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18n.ABILITY_DESCRIPTION_STAT_NAME_DAMAGE, proj.getDamage())).withBonus(bonus, bonusType);
                return statBuilder.build().getStatDescription(2);
            } else {
                return null;
            }
        };
    }

    private static AbilityDescriptionLine.IDescriptionLine getPiercingFromProjectileTooltip() {
        return (e, a) -> {
            AbilityProjectileEntity proj = a.getComponent(ModAbilityKeys.PROJECTILE).map((comp) -> (AbilityProjectileEntity) comp.getNewProjectile(e)).orElse(null);
            if (proj != null && proj.getArmorPiercing() > 0.0F) {
                AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18n.ABILITY_DESCRIPTION_STAT_NAME_PIERCING, proj.getArmorPiercing() * 100.0F)).withUnit(PERCENTAGE_SIGN);
                return statBuilder.build().getStatDescription(2);
            } else {
                return null;
            }
        };
    }

    public static <M extends Enum<M>> void addDescriptionByModes(Set<AbilityDescriptionLine> set, Map<M, ITextComponent[]> descMap, boolean isAdvanced) {
        AbilityDescriptionLine.IDescriptionLine description = (entity, ability) -> {
            ITextComponent[] desc = ability.getComponent(ModAbilityKeys.ALT_MODE).map(AltModeComponent::getCurrentMode).map(descMap::get).orElse(new ITextComponent[0]);
            StringBuilder descBuilder = new StringBuilder();
            for (ITextComponent comp : desc) {
                descBuilder.append(comp.getString());
                descBuilder.append("\n");
            }
            return new StringTextComponent(descBuilder.toString());
        };
        set.add(AbilityDescriptionLine.of(description, isAdvanced));
    }

    @SafeVarargs
    public static <A extends IAbility, M extends Enum<M>> AbilityDescriptionLine.IDescriptionLine<A> modeWrapper(M mode, AbilityDescriptionLine.IDescriptionLine<A>... desc) {
        return (entity, ability) -> {
            if (ability.getComponent(ModAbilityKeys.ALT_MODE).map(AltModeComponent::getCurrentMode).map(mode::equals).orElse(false)) {
                StringBuilder descBuilder = new StringBuilder();
                for (AbilityDescriptionLine.IDescriptionLine descriptionLine : desc) {
                    descBuilder.append(descriptionLine.expand(entity, ability).getString());
                    descBuilder.append('\n');
                }
                if (descBuilder.length() > 0) {
                    descBuilder.deleteCharAt(descBuilder.length() - 1);
                }
                return new StringTextComponent(descBuilder.toString());
            }
            return new StringTextComponent("");
        };
    }
}
