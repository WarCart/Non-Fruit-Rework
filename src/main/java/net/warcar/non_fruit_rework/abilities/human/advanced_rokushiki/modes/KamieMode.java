package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasDescription;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.kamie.ZanshinQuest;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum KamieMode implements IHasQuestRequirement, IHasTexture, IHasDescription {
    SIMPLE(null, null),
    ZANSHIN(ZanshinQuest.INSTANCE, "kamie_zanshin", LangHelper.registerDescriptionText("kamie.zanshin", Pair.of("Fast moving dodge, that creates afterimage and moves you behind opponent on hit", null)));

    private final QuestId<?> questId;
    private final ResourceLocation texture;
    private final ITextComponent[] description;

    KamieMode(QuestId<?> questId, String texture, ITextComponent... description) {
        this.questId = questId;
        this.texture = IHasTexture.getAbilityTexture(texture);
        this.description = description;
    }

    @Override
    public QuestId<?> getQuest() {
        return this.questId;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }

    @Override
    public ITextComponent[] getDescription() {
        return description;
    }
}
