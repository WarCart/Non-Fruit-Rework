package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiGoQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiKenpoQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiUtsugiQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

import javax.annotation.Nullable;

public enum TekkaiMode implements IHasQuestRequirement, IHasTexture {
    SIMPLE(null, null),
    TEKKAI_KENPO(TekkaiKenpoQuest.INSTANCE, "tekkai_kenpo"),
    TEKKAI_GO(TekkaiGoQuest.INSTANCE, "tekkai_go"),
    UTSUGI(TekkaiUtsugiQuest.INSTANCE, "tekkai_utsugi"),
    ;

    private final QuestId<?> questId;
    private final ResourceLocation texture;

    TekkaiMode(@Nullable QuestId<?> questId, String texture) {
        this.questId = questId;
        this.texture = IHasTexture.getAbilityTexture(texture);
    }


    @Override
    public QuestId<?> getQuest() {
        return questId;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }
}
