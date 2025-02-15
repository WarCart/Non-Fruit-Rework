package net.warcar.non_fruit_rework.screens.extra;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import net.warcar.non_fruit_rework.screens.VegapunkScreen;
import org.lwjgl.opengl.GL11;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CUpdateQuestStatePacket;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AvailableQuestsListScreenPanel extends ScrollPanel {
    private final VegapunkScreen parent;
    private final IQuestData props;
    private final List<QuestId<?>> availableQuests = new ArrayList<>();
    private final FontRenderer font;

    public AvailableQuestsListScreenPanel(VegapunkScreen parent, IQuestData abilityProps, List<QuestId<?>> quests) {
        super(parent.getMinecraft(), 200, 180, parent.height / 2 - 110, parent.width / 2 - 190);
        this.parent = parent;
        this.props = abilityProps;
        Minecraft parentMinecraft = parent.getMinecraft();
        this.font = parentMinecraft.font;
        this.updateAvailableQuests(quests);
        this.scrollDistance = -10.0F;
    }

    public void updateAvailableQuests(List<QuestId<?>> quests) {
        List<QuestId<?>> prevQuests = new ArrayList<>(quests);
        this.availableQuests.clear();

        for(int i = 0; i <= prevQuests.size() - 1; ++i) {
            QuestId<?> quest = prevQuests.get(i);
            boolean exists = quest != null;
            boolean isNotFinished = exists && !this.props.hasFinishedQuest(quest);
            boolean isNotInProgress = exists && (this.props.getInProgressQuest(quest) == null || this.props.getInProgressQuest(quest) != null && this.props.getInProgressQuest(quest).isComplete());
            if (isNotFinished && isNotInProgress) {
                this.availableQuests.add(quest);
            }
        }

    }

    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        return true;
    }

    protected int getContentHeight() {
        return (int)((double)this.availableQuests.size() * 55.0 - 2.0);
    }

    protected int getScrollAmount() {
        return 12;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Tessellator tess = Tessellator.getInstance();
        double scale = this.parent.getMinecraft().getWindow().getGuiScale();
        GL11.glEnable(3089);
        GL11.glScissor((int)((double)this.left * scale), (int)((double)this.parent.getMinecraft().getWindow().getHeight() - (double)this.bottom * scale), (int)((double)this.width * scale), (int)((double)this.height * scale));
        int baseY = this.top + 4 - (int)this.scrollDistance;
        this.drawPanel(matrixStack, this.right, baseY, tess, mouseX, mouseY);
        GL11.glDisable(3089);
    }

    protected void drawPanel(MatrixStack matrixStack, int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {
        Iterator<QuestId<?>> var7 = this.availableQuests.iterator();

        while(true) {
            QuestId<?> quest;
            do {
                if (!var7.hasNext()) {
                    return;
                }

                quest = var7.next();
            } while(quest == null);

            float y = (float)relativeY;
            float x = (float)(this.parent.width / 2 - 109 + 40);
            String formattedQuestName = quest.getLocalizedTitle();
            String questColor = "#FFFFFF";
            Quest inProgressQuest = this.props.getInProgressQuest(quest);
            if (inProgressQuest != null) {
                if (this.isMouseOverQuest(mouseX, mouseY, quest) && !inProgressQuest.isComplete()) {
                    formattedQuestName = (new TranslationTextComponent(ModI18n.TRAINER_NO_QUESTS_AVAILABLE)).getString();
                }

                if (inProgressQuest.isComplete()) {
                    questColor = "#00FF55";
                }
            }

            if (quest.isLocked(this.props)) {
                questColor = "#505050";
            }

            if (this.parent.isAnimationComplete() && this.isMouseOverQuest(mouseX, mouseY, quest)) {
                RenderSystem.color3f(0.8F, 0.8F, 0.8F);
            }

            RenderSystem.pushMatrix();
            Minecraft.getInstance().getTextureManager().bind(ModResources.SCROLL);
            double scale = 0.5;
            RenderSystem.translated(x - 180.0F, y - 196.0F, 0.0);
            RenderSystem.translated(256.0, 256.0, 0.0);
            RenderSystem.scaled(scale * 1.5, scale * 0.6, 0.0);
            RenderSystem.translated(-256.0, -256.0, 0.0);
            this.blit(matrixStack, 0, 0, 0, 0, 256, 256);
            RenderSystem.popMatrix();
            if (this.parent.isAnimationComplete()) {
                RenderSystem.color3f(1.0F, 1.0F, 1.0F);
            }

            if (this.font.width(formattedQuestName) <= 140) {
                WyHelper.drawStringWithBorder(this.font, matrixStack, formattedQuestName, (int)x - 80, (int)y + 16, WyHelper.hexToRGB(questColor).getRGB());
            } else {
                RenderSystem.pushMatrix();
                List<IReorderingProcessor> splittedText = this.font.split(new StringTextComponent(formattedQuestName), 140);
                RenderSystem.translated(0.0, -((splittedText.size() - 1) * 5), 0.0);

                for(Iterator<IReorderingProcessor> var15 = splittedText.iterator(); var15.hasNext(); y += 10.0F) {
                    IReorderingProcessor string = var15.next();
                    WyHelper.drawStringWithBorder(this.font, matrixStack, string, (int)x - 80, (int)y + 16, WyHelper.hexToRGB(questColor).getRGB());
                }

                RenderSystem.popMatrix();
            }

            relativeY = (int)((double)relativeY + 55.0);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        QuestId<?> quest = this.findQuestEntry((int)mouseX, (int)mouseY);
        if (button != 0) {
            return false;
        } else {
            Quest inprogressQuest = this.props.getInProgressQuest(quest);
            if (quest != null && inprogressQuest != null && inprogressQuest.isComplete()) {
                WyNetwork.sendToServer(new CUpdateQuestStatePacket(quest));
                this.availableQuests.remove(quest);
            } else if (quest != null && !quest.isLocked(this.props)) {
                WyNetwork.sendToServer(new CUpdateQuestStatePacket(quest));
                this.availableQuests.remove(quest);
            }

            this.updateAvailableQuests(this.availableQuests);
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public boolean isMouseOverQuest(double mouseX, double mouseY, QuestId<?> overQuest) {
        QuestId<?> quest = this.findQuestEntry((int)mouseX, (int)mouseY);
        return quest != null && quest.equals(overQuest) && super.isMouseOver(mouseX, mouseY);
    }

    @Nullable
    private QuestId<?> findQuestEntry(int mouseX, int mouseY) {
        double offset = (float)(mouseY - this.top) + this.scrollDistance;
        boolean isHovered = mouseX >= this.left && mouseY >= this.top && mouseX < this.left + this.width - 5 && mouseY < this.top + this.height;
        if (!(offset <= 0.0) && isHovered) {
            int lineIdx = (int)(offset / 55.0);
            if (lineIdx >= this.availableQuests.size()) {
                return null;
            } else {
                return this.availableQuests.get(lineIdx);
            }
        } else {
            return null;
        }
    }
}