package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasDescription;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.soru.TekkaiDamaQuest;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum SoruMode implements IHasQuestRequirement, IHasTexture, IHasDescription {
    SIMPLE(null),
    TEKKAI_DAMA(TekkaiDamaQuest.INSTANCE, new ResourceLocation(ModMain.PROJECT_ID, "textures/abilities/tekkai_walk.png"),
            LangHelper.registerDescriptionText("soru.tekkai_dama", Pair.of("After building up sufficient speed user tenses their body using tekkai to deal crushing damage on hit", null)));

    private final QuestId<?> questId;
    private final ResourceLocation texture;
    private final ITextComponent[] description;

    SoruMode(QuestId<?> questId) {
        this(questId, null);
    }

    SoruMode(QuestId<?> questId, ResourceLocation texture, ITextComponent... description) {
        this.questId = questId;
        this.texture = texture;
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
