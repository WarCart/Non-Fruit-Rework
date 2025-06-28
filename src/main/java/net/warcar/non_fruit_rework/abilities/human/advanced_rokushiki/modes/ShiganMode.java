package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasTexture;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.JushiganQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.ShiganOrenQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.TobuShiganQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum ShiganMode implements IHasQuestRequirement, IHasTexture {
    SIMPLE(null, null),
    TOBU(TobuShiganQuest.INSTANCE, null),
    OREN(ShiganOrenQuest.INSTANCE, "shigan_oren"),
    JUSHIGAN(JushiganQuest.INSTANCE, null),
    ;

    private final QuestId<?> questId;
    private final ResourceLocation texture;

    ShiganMode(QuestId<?> questId, String texture) {
        this.questId = questId;
        this.texture = IHasTexture.getAbilityTexture(texture);
    }

    @Override
    public QuestId<?> getQuest() {
        return questId;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }
}
