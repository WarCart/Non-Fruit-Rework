package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rokuogan.SaiDaiRinRokuoganQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum RokuoganMode implements IHasQuestRequirement, IHasTexture {
    SIMPLE(null, null),
    SAI_DAI_RIN(SaiDaiRinRokuoganQuest.INSTANCE, "sai_dai_rin_rokuogan"),
    ;

    private final QuestId<?> quest;
    private final ResourceLocation texture;

    RokuoganMode(QuestId<?> questId, String texture) {
        this.quest = questId;
        this.texture = IHasTexture.getAbilityTexture(texture);
    }

    @Override
    public QuestId<?> getQuest() {
        return quest;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }
}
