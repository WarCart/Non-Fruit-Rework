package net.warcar.non_fruit_rework.screens.shop;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CSyncEntityStatsPacket;
import net.warcar.non_fruit_rework.screens.ScientistScreen;
import org.lwjgl.opengl.GL11;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShopScreenPanel extends ScrollPanel {
    private final ScientistScreen parent;
    private final IEntityStats props;
    private final List<Product> availableProducts = new ArrayList<>();
    private final FontRenderer font;

    public ShopScreenPanel(ScientistScreen parent, IEntityStats abilityProps, List<Product> products) {
        super(parent.getMinecraft(), 200, 180, parent.height / 2 - 100, parent.width / 2 - 190);
        this.parent = parent;
        this.props = abilityProps;
        Minecraft parentMinecraft = parent.getMinecraft();
        this.font = parentMinecraft.font;
        this.updateAvailableProducts(products);
        this.scrollDistance = -10.0F;
    }

    public void updateAvailableProducts(List<Product> products) {
        List<Product> prevQuests = new ArrayList<>(products);
        this.availableProducts.clear();

        this.availableProducts.addAll(prevQuests);

    }

    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
        return true;
    }

    protected int getContentHeight() {
        return (int)((double)this.availableProducts.size() * 55.0 - 2.0);
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
        for (Product product : this.availableProducts) {
            float y = (float)relativeY;
            float x = (float)(this.parent.width / 2 - 109 + 40);
            ITextComponent formattedQuestName = product.getName();
            String questColor = "#FFFFFF";

            if (this.parent.isAnimationComplete() && this.isMouseOverProduct(mouseX, mouseY, product)) {
                RenderSystem.color3f(0.8F, 0.8F, 0.8F);
            }

            matrixStack.pushPose();
            Minecraft.getInstance().getTextureManager().bind(ModResources.SCROLL);
            double scale = 0.5;
            matrixStack.translate(x - 180.0F, y - 196.0F, 0.0);
            matrixStack.translate(256.0, 256.0, 0.0);
            matrixStack.scale((float) (scale * 1.5), (float) (scale * 0.6), 0);
            matrixStack.translate(-256.0, -256.0, 0.0);
            this.blit(matrixStack, 0, 0, 0, 0, 256, 256);
            matrixStack.popPose();
            if (this.parent.isAnimationComplete()) {
                RenderSystem.color3f(1.0F, 1.0F, 1.0F);
            }

            if (this.font.width(formattedQuestName) <= 140) {
                WyHelper.drawStringWithBorder(this.font, matrixStack, formattedQuestName, (int)x - 80, (int)y + 16, WyHelper.hexToRGB(questColor).getRGB());
            } else {
                RenderSystem.pushMatrix();
                List<IReorderingProcessor> splittedText = this.font.split(formattedQuestName, 140);
                RenderSystem.translated(0.0, -((splittedText.size() - 1) * 5), 0.0);

                for(Iterator<IReorderingProcessor> var15 = splittedText.iterator(); var15.hasNext(); y += 10.0F) {
                    IReorderingProcessor string = var15.next();
                    WyHelper.drawStringWithBorder(this.font, matrixStack, string, (int)x - 80, (int)y + 8, WyHelper.hexToRGB(questColor).getRGB());
                }

                RenderSystem.popMatrix();
            }
            product.drawIcon(matrixStack, (int)x - 100, (int)y + 16);
            if (product.getPrice() > 0) {
                int color;
                if (this.props.getBelly() < product.getPrice()) {
                    color = 0xFF0000;
                } else {
                    color = 0xFFFFFF;
                }
                WyHelper.drawStringWithBorder(this.font, matrixStack, String.valueOf(product.getPrice()), (int) x - 80, (int) y + 26, color);
            }

            relativeY = (int)((double)relativeY + 55.0);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Product quest = this.findQuestEntry((int)mouseX, (int)mouseY);
        if (quest == null) {
            return false;
        } else {
            quest.buy(props);
            ModNetwork.sendToServer(new CSyncEntityStatsPacket(Minecraft.getInstance().player.getId(), props));
            this.updateAvailableProducts(this.availableProducts);
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public boolean isMouseOverProduct(double mouseX, double mouseY, Product over) {
        Product quest = this.findQuestEntry((int)mouseX, (int)mouseY);
        return quest != null && quest.equals(over) && super.isMouseOver(mouseX, mouseY);
    }

    @Nullable
    private Product findQuestEntry(int mouseX, int mouseY) {
        double offset = (float)(mouseY - this.top) + this.scrollDistance;
        boolean isHovered = mouseX >= this.left && mouseY >= this.top && mouseX < this.left + this.width - 5 && mouseY < this.top + this.height;
        if (!(offset <= 0.0) && isHovered) {
            int lineIdx = (int)(offset / 55.0);
            if (lineIdx >= this.availableProducts.size()) {
                return null;
            } else {
                return this.availableProducts.get(lineIdx);
            }
        } else {
            return null;
        }
    }
}