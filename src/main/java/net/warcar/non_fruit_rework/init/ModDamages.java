package net.warcar.non_fruit_rework.init;

import com.google.common.collect.Lists;
import net.minecraft.util.DamageSource;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

public class ModDamages {
    public static final DamageSource OVERDOSE = register(new ModDamageSource("old_age").setUnavoidable().setSourceTypes(Lists.newArrayList(SourceType.UNKNOWN)).setInternal().bypassInvul(), "%s died of old age");
    public static final DamageSource SAPPHIRE_SCALES = register(new ModDamageSource("sapphire_scales").setUnavoidable().setSourceTypes(Lists.newArrayList(SourceType.UNKNOWN)).setInternal().bypassInvul(), "%s succumbed to the sapphire scales decease");
    public static final DamageSource STONE = register(new ModDamageSource("stone_transformation").setUnavoidable().setSourceTypes(Lists.newArrayList(SourceType.UNKNOWN)).setInternal().bypassInvul(), "%s turned into stone");

    public static void init() {}

    private static <S extends DamageSource> S register(S source, String localisation) {
        LangHelper.registerLine("death.attack." + source.msgId, localisation);
        LangHelper.registerLine("death.attack." + source.msgId + ".player", localisation + " while fighting %s");
        return source;
    }
}
