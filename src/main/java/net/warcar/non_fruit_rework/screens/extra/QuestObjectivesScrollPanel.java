package net.warcar.non_fruit_rework.screens.extra;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

public class QuestObjectivesScrollPanel extends ScrollPanel {
    private final Quest quest;
    private final IQuestData data;
    private int height;

    public QuestObjectivesScrollPanel(Minecraft client, int top, int left, Quest quest, IQuestData data) {
        super(client, 280, 118, top, left);
        this.quest = quest;
        this.data = data;
    }

    @Override
    protected int getContentHeight() {
        if (height == 0) {
            height = 28 * quest.getObjectives().size();
        }
        return height;
    }

    @Override
    protected void drawGradientRect(MatrixStack mStack, int left, int top, int right, int bottom, int color1, int color2) {
    }

    @Override
    protected void drawPanel(MatrixStack matrixStack, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
        int yOffset = -20;
        FontRenderer font = Minecraft.getInstance().font;
        for(Objective obj : quest.getObjectives()) {
            String objectiveName = obj.getLocalizedTitle();
            double objectiveProgress = obj.getProgress() / obj.getMaxProgress() * (double)100.0F;
            String progress = "";
            yOffset += 20;
            String textColor = "#FFFFFF";
            if (obj.isComplete()) {
                textColor = "#00FF00";
            }

            if (obj.isLocked()) {
                textColor = "#505050";
            } else {
                progress = "- " + String.format("%.1f", objectiveProgress) + "%";
            }

            if (obj.isHidden()) {
                WyHelper.drawStringWithBorder(font, matrixStack, "• ", this.left, this.top + yOffset, WyHelper.hexToRGB(textColor).getRGB());
            } else {
                String optional = obj.isOptional() ? "(Optional) " : "";
                objectiveName = "• " + optional + objectiveName + " " + progress;
                List<IReorderingProcessor> splitText = font.split(new StringTextComponent(objectiveName), 270);

                for(int j = 0; j < splitText.size(); ++j) {
                    WyHelper.drawStringWithBorder(font, matrixStack, splitText.get(j), this.left + 1, relativeY + yOffset + j * 12, WyHelper.hexToRGB(textColor).getRGB());
                }

                yOffset += splitText.size() * 8;
            }
        }
        this.height = yOffset + 2;
    }
}
