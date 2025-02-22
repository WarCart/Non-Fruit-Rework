package net.warcar.non_fruit_rework.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.warcar.non_fruit_rework.entities.seraphim.SeraphimEntity;
import net.warcar.non_fruit_rework.enums.PacifistaModel;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.ModEntityTypes;
import net.warcar.non_fruit_rework.init.ModQuests;
import net.warcar.non_fruit_rework.init.ModTexts;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CSpawnPacifistaModelPacket;
import net.warcar.non_fruit_rework.network.packets.client.CSpawnSeraphimModelPacket;
import net.warcar.non_fruit_rework.quest.genetic_materials.LunarianGenesQuest;
import net.warcar.non_fruit_rework.screens.extra.AvailableQuestsListScreenPanel;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.screens.extra.SequencedString;
import xyz.pixelatedw.mineminenomi.screens.extra.buttons.FactionButton;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VegapunkScreen extends Screen {
    private final PlayerEntity player;
    private final IQuestData questData;
    private final IEntityStats entityStats;
    private final LivingEntity trainer;
    private float animationTime = 0.0F;
    private float animationTranslation = 100.0F;
    private State guiState = State.INTRO;
    private SequencedString startMessage = new SequencedString("", 0, 0);
    private AvailableQuestsListScreenPanel availableQuestsPanel;

    public VegapunkScreen(PlayerEntity player, LivingEntity trainer) {
        super(new StringTextComponent(""));
        this.player = player;
        this.questData = QuestDataCapability.get(player);
        this.entityStats = EntityStatsCapability.get(player);
        this.trainer = trainer;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        if (this.animationTime < 10.0F) {
            this.animationTime = (float) ((double) this.animationTime + 0.2);
        }

        if (this.animationTranslation > 0.0F) {
            this.animationTranslation = 100.0F - this.animationTime * 40.0F;
        }

        int posX = this.width / 2;
        int posY = this.height / 2;
        switch (this.guiState) {
            case INTRO:
                this.renderMenu(matrixStack, mouseX, mouseY, partialTicks);
                break;
            case GENETIC_QUESTS:
            case CYBORG_QUESTS:
                this.renderQuestList(matrixStack, mouseX, mouseY, partialTicks);
                break;
            case BUY_PACIFISTA:
            case BUY_SERAPHIM:
                break;
            case CUSTOM_SERAPHIM:
        }

        matrixStack.pushPose();
        matrixStack.translate(this.animationTranslation, 0.0, 0.0);
        RenderSystem.enableBlend();
        InventoryScreen.renderEntityInInventory(posX + 150, posY + 150, 100, 40.0F, 5.0F, this.trainer);
        matrixStack.popPose();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void renderMenu(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int posX = this.width / 2;
        int posY = this.height / 2;
        this.startMessage.render(matrixStack, posX - 150, posY - 105, partialTicks);
    }

    public void renderQuestList(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        matrixStack.pushPose();
        matrixStack.translate(-this.animationTranslation, 0.0, 0.0);
        RenderSystem.enableBlend();
        this.availableQuestsPanel.render(matrixStack, mouseX, mouseY, partialTicks);
        this.availableQuestsPanel.isMouseOver(mouseX, mouseY);
        matrixStack.popPose();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void init(Minecraft mc, int width, int height) {
        super.init(mc, width, height);
        int posX = this.width / 2;
        int posY = this.height / 2;
        switch (this.guiState) {
            case CYBORG_QUESTS:
                registerQuestState(ModQuests.CYBORG_QUESTS, posX, posY);
                break;
            case GENETIC_QUESTS:
                registerQuestState(ModQuests.GEN_MODIFICATION_QUESTS, posX, posY);
                break;
            case BUY_PACIFISTA:
                registerBuyPacifistaState(mc, posX, posY);
                break;
            case BUY_SERAPHIM:
                registerBuySeraphim(mc, posX, posY);
                break;
            case CUSTOM_SERAPHIM:
                registerCustomSeraphim(mc, posX, posY);
                break;
            case GENETIC_MODIFICATIONS:
                break;
            case INTRO:
            default:
                registerIntroState(posX, posY);
        }
    }

    private void registerCustomSeraphim(Minecraft mc, int posX, int posY) {
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), (btn) -> {
            this.guiState = State.BUY_SERAPHIM;
            this.init(this.getMinecraft(), this.width, this.height);
        });
        this.addButton(backButton);
    }

    private void registerBuySeraphim(Minecraft mc, int posX, int posY) {
        for (int i = 0; i < ModEntityTypes.SERAPHIMS.size(); i++) {
            EntityType<SeraphimEntity> model = (EntityType<SeraphimEntity>) ModEntityTypes.SERAPHIMS.get(i);
            int modelId = i;
            Button.ITooltip tooltip = Button.NO_TOOLTIP;
            if (this.entityStats.getBelly() < 1000000) {
                tooltip = (btn, matrix, mouseX, mouseY) -> this.renderTooltip(matrix, this.minecraft.font.split(ModTexts.BROKE, Math.max(this.width / 2 - 43, 170)), mouseX, mouseY);
            }
            FactionButton modelButton = new FactionButton(posX - 180, posY + 15 * i - 50, 100, 10, new TranslationTextComponent(ModTexts.BUY_SERAPHIM_LVL.getKey(), model.getDescription()), btn -> {
                if (this.entityStats.getBelly() >= 1000000) {
                    ModNetwork.sendToServer(new CSpawnSeraphimModelPacket(modelId, 1000000));
                    mc.setScreen(null);
                }
            }, tooltip);
            modelButton.active = this.entityStats.getBelly() >= 1000000;
            this.addButton(modelButton);
        }
        FactionButton customSeraphim = new FactionButton(posX - 180, posY + 15 * ModEntityTypes.SERAPHIMS.size() - 35, 200, 20, ModTexts.CUSTOM_SERAPHIM, (btn) -> {
            this.guiState = State.CUSTOM_SERAPHIM;
            this.init(this.getMinecraft(), this.width, this.height);
        });
        this.addButton(customSeraphim);
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), (btn) -> {
            this.guiState = State.BUY_PACIFISTA;
            this.init(this.getMinecraft(), this.width, this.height);
        });
        this.addButton(backButton);
    }

    private void registerBuyPacifistaState(Minecraft mc, int posX, int posY) {
        for (int i = 0; i < PacifistaModel.values().length; i++) {
            PacifistaModel model = PacifistaModel.values()[i];
            Button.ITooltip tooltip = Button.NO_TOOLTIP;
            if (this.entityStats.getBelly() < model.getPrice()) {
                tooltip = (btn, matrix, mouseX, mouseY) -> this.renderTooltip(matrix, this.minecraft.font.split(ModTexts.BROKE, Math.max(this.width / 2 - 43, 170)), mouseX, mouseY);
            }
            FactionButton modelButton = new FactionButton(posX - 180, posY + 15 * i - 50, 100, 10, new TranslationTextComponent(ModTexts.BUY_PACIFISTA_LVL.getKey(), model.getLocalizedName(), model.getPrice()), btn -> {
                if (this.entityStats.getBelly() >= model.getPrice()) {
                    ModNetwork.sendToServer(new CSpawnPacifistaModelPacket(model));
                    mc.setScreen(null);
                }
            }, tooltip);
            modelButton.active = this.entityStats.getBelly() >= model.getPrice();
            this.addButton(modelButton);
        }
        Button.ITooltip tooltip = Button.NO_TOOLTIP;
        boolean genome = QuestHelper.hasFinishedQuest(this.player, LunarianGenesQuest.INSTANCE);
        if (!genome) {
            tooltip = (btn, matrix, mouseX, mouseY) -> this.renderTooltip(matrix, this.minecraft.font.split(new TranslationTextComponent(ModTexts.GENOME_NOT_INCLUDED.getKey(), "Lunarian", LunarianGenesQuest.INSTANCE.getLocalizedTitle()), Math.max(this.width / 2 - 43, 170)), mouseX, mouseY);
        }
        FactionButton seraphimButton = new FactionButton(posX - 180, posY + 15 * PacifistaModel.values().length - 35, 100, 10, ModTexts.BUY_SERAPHIM, btn -> {
            this.guiState = State.BUY_SERAPHIM;
            this.init(this.getMinecraft(), this.width, this.height);
        }, tooltip);
        seraphimButton.active = genome;
        //this.addButton(seraphimButton);
        /// Uncomment when seraphims are balanced
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), (btn) -> {
            this.guiState = State.INTRO;
            this.init(this.getMinecraft(), this.width, this.height);
        });
        this.addButton(backButton);
    }

    private void registerQuestState(List<QuestId<?>> cyborgQuests, int posX, int posY) {
        this.availableQuestsPanel = new AvailableQuestsListScreenPanel(this, this.questData, cyborgQuests);
        this.children.add(this.availableQuestsPanel);
        this.setFocused(this.availableQuestsPanel);
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), (btn) -> {
            this.guiState = State.INTRO;
            this.init(this.getMinecraft(), this.width, this.height);
        });
        this.addButton(backButton);
    }

    private void registerIntroState(int posX, int posY) {
        FactionButton cyborgButton = new FactionButton(posX - 180, posY - 50, 100, 20, ModTexts.CYBORG_UPGRADES, (btn) -> {
            boolean hasQuests = false;

            for (int i = 0; i <= ModQuests.CYBORG_QUESTS.size() - 1; ++i) {
                QuestId<?> quest = ModQuests.CYBORG_QUESTS.get(i);
                if (!this.questData.hasFinishedQuest(quest)) {
                    hasQuests = true;
                    break;
                }
            }

            if (hasQuests) {
                this.guiState = State.CYBORG_QUESTS;
                this.init(this.getMinecraft(), this.width, this.height);
            } else {
                String message = (new TranslationTextComponent(ModI18n.TRAINER_NO_TRIALS_AVAILABLE)).getString();
                this.startMessage = new SequencedString(message, 250, this.font.width(message) / 2);
            }

        });
        this.addButton(cyborgButton);
        FactionButton geneticsButton = new FactionButton(posX - 180, posY - 20, 100, 20, ModTexts.GENETIC_COLLECTION, (btn) -> {
            boolean hasQuests = false;

            for (int i = 0; i <= ModQuests.GEN_MODIFICATION_QUESTS.size() - 1; ++i) {
                QuestId<?> quest = ModQuests.GEN_MODIFICATION_QUESTS.get(i);
                if (!this.questData.hasFinishedQuest(quest)) {
                    hasQuests = true;
                    break;
                }
            }

            if (hasQuests) {
                this.guiState = State.GENETIC_QUESTS;
                this.init(this.getMinecraft(), this.width, this.height);
            } else {
                String message = (new TranslationTextComponent(ModI18n.TRAINER_NO_TRIALS_AVAILABLE)).getString();
                this.startMessage = new SequencedString(message, 250, this.font.width(message) / 2);
            }

        });
        this.addButton(geneticsButton);
        FactionButton pacifistaButton = new FactionButton(posX - 180, posY + 10, 100, 20, ModTexts.BUY_PACIFISTA, btn->{
            this.guiState = State.BUY_PACIFISTA;
            this.init(this.getMinecraft(), this.width, this.height);
        });
        this.addButton(pacifistaButton);
    }

    public boolean isAnimationComplete() {
        return this.animationTime >= 5.0F;
    }

    private enum State {
        INTRO,
        CYBORG_QUESTS,
        GENETIC_QUESTS,
        GENETIC_MODIFICATIONS,
        BUY_PACIFISTA,
        BUY_SERAPHIM,
        CUSTOM_SERAPHIM
    }
}