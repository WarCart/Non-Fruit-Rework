package net.warcar.non_fruit_rework.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.warcar.non_fruit_rework.screens.extra.QuestObjectivesScrollPanel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.screens.QuestsTrackerScreen;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

@Mixin(QuestsTrackerScreen.class)
public class QuestTrackerScreenMixin extends Screen {
    @Shadow private Quest currentQuest;

    @Shadow private int questIndex;

    @Shadow private List<Quest> availableQuests;

    @Shadow private IQuestData qprops;
    private QuestObjectivesScrollPanel panel;

    private QuestTrackerScreenMixin(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        int posX = width / 2;
        int posY = height / 2;
        panel = new QuestObjectivesScrollPanel(this.minecraft, posY - 55, posX - 130, this.currentQuest, this.qprops);
        this.addWidget(panel);
        this.setFocused(panel);
    }

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private void reRender(MatrixStack matrixStack, int x, int y, float f, CallbackInfo ci) { /// IM DONE
        ci.cancel();
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int posX = this.width / 2;
        int posY = this.height / 2;
        Minecraft.getInstance().getTextureManager().bind(ModResources.BLANK);
        RenderSystem.pushMatrix();
        double scale = 1.1;
        RenderSystem.translated(posX - 35, posY + 10, 0.0F);
        RenderSystem.translated(256.0F, 256.0F, 0.0F);
        RenderSystem.scaled(scale * (double)1.5F, scale * 1.4, 0.0F);
        RenderSystem.translated(-256.0F, -256.0F, 0.0F);
        GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, 1.0F);
        RenderSystem.translated(-30.0F, 50.0F, 0.0F);
        RenderSystem.translated(256.0F, 256.0F, 0.0F);
        RenderSystem.scaled(scale * 0.7, scale * 0.9, 0.0F);
        RenderSystem.translated(-256.0F, -256.0F, 0.0F);
        RenderSystem.popMatrix();
        String currentQuestName = this.currentQuest != null ? this.currentQuest.getCore().getLocalizedTitle() : "None";
        double currentQuestProgress = this.currentQuest != null ? this.currentQuest.getProgress() * (double)100.0F : (double)-1.0F;
        if (this.currentQuest != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translated(posX + 150, posY - 110, 0.0F);
            String pageNumber = this.questIndex + 1 + "/" + this.availableQuests.size();
            WyHelper.drawStringWithBorder(this.font, matrixStack, pageNumber, 0, 0, WyHelper.hexToRGB("#FFFFFF").getRGB());
            RenderSystem.popMatrix();
            RenderSystem.pushMatrix();
            double scale1 = 1.4;
            RenderSystem.translated(posX + 100, posY + 10, 0.0F);
            RenderSystem.translated(256.0F, 256.0F, 0.0F);
            RenderSystem.scaled(scale1, scale1, 0.0F);
            RenderSystem.translated(-256.0F, -256.0F, 0.0F);
            WyHelper.drawStringWithBorder(this.font, matrixStack, currentQuestName, -this.font.width(currentQuestName) / 2, 0, WyHelper.hexToRGB("#FFFFFF").getRGB());
            RenderSystem.popMatrix();
            if (currentQuestProgress != (double)-1.0F) {
                String textColor = "#FFFFFF";
                if (this.currentQuest.isComplete()) {
                    textColor = "#00FF55";
                }

                String progress = TextFormatting.BOLD + (new TranslationTextComponent(ModI18n.GUI_QUEST_PROGRESS)).getString() + " : " + String.format("%.1f", currentQuestProgress) + "%";
                WyHelper.drawStringWithBorder(this.font, matrixStack, progress, posX - 120, posY - 65, WyHelper.hexToRGB(textColor).getRGB());
            }

            RenderSystem.pushMatrix();
            this.panel.render(matrixStack, x, y, f);
            RenderSystem.popMatrix();
        }

        super.render(matrixStack, x, y, f);
    }
}
