package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasDescription;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.RankyakuHakuraiQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.RankyakuRanQuest;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum RankyakuMode implements IHasQuestRequirement, IHasTexture, IHasDescription {
    SIMPLE(null, null),
    HAKURAI(RankyakuHakuraiQuest.INSTANCE, "rankyaku_hakurai", LangHelper.registerDescriptionText("rankyaku.hakurai", Pair.of("Stronger slash, more destructive than basic", null))),
    RAN(RankyakuRanQuest.INSTANCE, "rankyaku_ran", LangHelper.registerDescriptionText("rankyaku.ran", Pair.of("Rapid fire version that creates barrage of slashes", null))),
    ;

    private final QuestId<?> questId;
    private final ResourceLocation texture;
    private final ITextComponent[] description;

    RankyakuMode(QuestId<?> questId, String texture, ITextComponent... description) {
        this.questId = questId;
        this.texture = IHasTexture.getAbilityTexture(texture);
        this.description = description;
    }

    @Override
    public QuestId<?> getQuest() {
        return questId;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public ITextComponent[] getDescription() {
        return description;
    }
}
