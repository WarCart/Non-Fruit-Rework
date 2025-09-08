package net.warcar.non_fruit_rework.screens;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.fml.RegistryObject;
import net.warcar.non_fruit_rework.abilities.GenesAbility;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.entities.quests.mads.CaesarEntity;
import net.warcar.non_fruit_rework.entities.quests.mads.JudgeEntity;
import net.warcar.non_fruit_rework.entities.quests.mads.QueenEntity;
import net.warcar.non_fruit_rework.entities.quests.mads.VegapunkEntity;
import net.warcar.non_fruit_rework.entities.seraphim.SeraphimEntity;
import net.warcar.non_fruit_rework.enums.ModifiableAttributes;
import net.warcar.non_fruit_rework.enums.PacifistaModel;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.*;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CRestartPlayerPacket;
import net.warcar.non_fruit_rework.network.packets.client.CSyncEntityStatsPacket;
import net.warcar.non_fruit_rework.network.packets.client.CSyncNonFruitDataPacket;
import net.warcar.non_fruit_rework.network.packets.client.CUpdatePassiveAbilityDataPacket;
import net.warcar.non_fruit_rework.quest.genetic_materials.FishmanGenesQuest;
import net.warcar.non_fruit_rework.quest.genetic_materials.MinkGenesQuest;
import net.warcar.non_fruit_rework.screens.extra.AvailableQuestsListScreenPanel;
import net.warcar.non_fruit_rework.screens.extra.OptionSlider;
import net.warcar.non_fruit_rework.screens.extra.PlankToggle;
import net.warcar.non_fruit_rework.screens.shop.*;
import xyz.pixelatedw.mineminenomi.api.charactercreator.RaceId;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.screens.extra.SequencedString;
import xyz.pixelatedw.mineminenomi.screens.extra.buttons.FactionButton;
import xyz.pixelatedw.mineminenomi.screens.extra.buttons.PlankButton;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ScientistScreen extends Screen {
    public final Button.ITooltip WIP = (btn, matrix, mouseX, mouseY) -> this.renderTooltip(matrix, ModTexts.WIP, mouseX, mouseY);
    private final PlayerEntity player;
    private final IQuestData questData;
    private final IEntityStats entityStats;
    private final INonFruitData medicalData;
    private final IAbilityData abilityData;
    private final LivingEntity trainer;
    private final Type type;
    private float animationTime = 0.0F;
    private float animationTranslation = 100.0F;
    private State guiState = State.INTRO;
    private GeneticModsState geneticState = GeneticModsState.HYBRID_RACES;
    private SequencedString startMessage = new SequencedString("", 0, 0);
    private AvailableQuestsListScreenPanel availableQuestsPanel;
    private ShopScreenPanel shopScreenPanel;
    private OptionSlider[] hybridGenesSliders = {};
    private OptionSlider[] otherGenesSliders = {};
    private PlankButton[] pristineRaceButtons = {};
    private PlankToggle germaGenes;
    private FactionButton finishButton;

    public ScientistScreen(PlayerEntity player, LivingEntity trainer) {
        super(new StringTextComponent(""));
        this.player = player;
        this.questData = QuestDataCapability.get(player);
        this.entityStats = EntityStatsCapability.get(player);
        this.medicalData = NonFruitDataCapability.get(player);
        this.abilityData = AbilityDataCapability.get(player);
        this.trainer = trainer;
        if (trainer instanceof VegapunkEntity) {
            this.type = Type.VEGAPUNK;
        } else if (trainer instanceof JudgeEntity) {
            this.type = Type.JUDGE;
        } else if (trainer instanceof CaesarEntity) {
            this.type = Type.CAESAR;
        } else if (trainer instanceof QueenEntity) {
            this.type = Type.QUEEN;
        } else {
            this.type = null;
        }
    }

    @Override
    public void tick() {
        if (this.finishButton != null) {
            long finishPrice = this.getFinishPrice();
            this.finishButton.active = !this.isGenomeDamaged() && this.entityStats.getBelly() > finishPrice;
            this.finishButton.setMessage(new TranslationTextComponent(ModTexts.FINISH.getKey(), finishPrice));
        }
    }

    private long getFinishPrice() {
        return getRacialPrice() + getAdditionalGenesPrice();
    }

    private long getAdditionalGenesPrice() {
        long price = 0;
        GenesAbility ability = abilityData.getPassiveAbility(GenesAbility.INSTANCE);
        if (ability != null && this.otherGenesSliders.length > 0) {
            for (ModifiableAttributes attribute : ModifiableAttributes.values()) {
                OptionSlider slider = this.otherGenesSliders[attribute.ordinal()];
                double delta = slider.getValueStrict() - slider.unapplyValue(ability.getGenes().getOrDefault(attribute, 0d));
                long powed = (long) Math.pow(1.7, 15 * Math.abs(delta + 1)) + 10000;
                if (delta != 0) {
                    price += powed;
                }
            }
        }
        return price;
    }

    private long getRacialPrice() {
        try {
            long racialPrice = 0;
            if (this.chosenPristineRace() != 0) {
                racialPrice = 1000000;
            } else {
                long defaultPrice = 5000;
                for (int i = 0; i < HybridRaces.values().length; i++) {
                    HybridRaces race = HybridRaces.values()[i];
                    if (this.hybridGenesSliders[i].getValueStrict() == 0) {
                        continue;
                    }
                    if (this.entityStats.getRace().equals(ModRaces.HYBRID.getId())) {
                        if (medicalData.getGenome().containsKey(race.getId())) {
                            racialPrice += 1000;
                        } else {
                            racialPrice += 10000;
                        }
                    } else if (!entityStats.getRace().equals(race.getId())) {
                        racialPrice += 15000;
                    }
                }
                racialPrice += defaultPrice;
            }
            return racialPrice;
        } catch (Exception e) {
            return 0;
        }
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
            case GENETIC_MODIFICATIONS:
                this.renderMessage(matrixStack, posX, posY);
                break;
            case INTRO:
                this.renderMenu(matrixStack, mouseX, mouseY, partialTicks);
                break;
            case GENETIC_QUESTS:
            case CYBORG_QUESTS:
                this.renderQuestList(matrixStack, mouseX, mouseY, partialTicks);
                break;
            case SHOP:
                this.renderShop(matrixStack, mouseX, mouseY , partialTicks);
                break;
            case CUSTOM_SERAPHIM:
        }

        matrixStack.pushPose();
        matrixStack.translate(this.animationTranslation, 0.0, 0.0);
        RenderSystem.enableBlend();
        if (this.guiState != State.GENETIC_MODIFICATIONS) {
            InventoryScreen.renderEntityInInventory(posX + 150, posY + 150, 100, 40.0F, 5.0F, this.trainer);
        }
        matrixStack.popPose();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void renderShop(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        matrixStack.pushPose();
        matrixStack.translate(-this.animationTranslation, 0.0, 0.0);
        RenderSystem.enableBlend();
        this.shopScreenPanel.render(matrixStack, mouseX, mouseY, partialTicks);
        this.shopScreenPanel.isMouseOver(mouseX, mouseY);
        matrixStack.popPose();
    }

    public void renderMessage(MatrixStack matrixStack, int posX, int posY) {
        if (isGenomeDamaged()) {
            WyHelper.drawStringWithBorder(this.minecraft.font, matrixStack, ModTexts.GENOME_DAMAGED, posX - 150, posY - 75, Color.RED.getRGB());
        }
    }

    private boolean isGenomeDamaged() {
        return getTotalGenes() != 1 && this.chosenPristineRace() == 0;
    }

    private double getTotalGenes() {
        double totalGenes = 0;
        for (OptionSlider slider : this.hybridGenesSliders) {
            totalGenes += slider.getValueStrict();
        }
        return totalGenes;
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
            case EXPERIMENT:
                registerExperimentState(posX, posY);
                break;
            case SHOP:
                registerBuyPacifistaState(posX, posY);
                break;
            case CUSTOM_SERAPHIM:
                registerCustomSeraphim(posX, posY);
                break;
            case GENETIC_MODIFICATIONS:
                registerGeneticModifications(posX, posY);
                break;
            case INTRO:
            default:
                registerIntroState(posX, posY);
        }
    }

    private void registerExperimentState(int posX, int posY) {
    }

    private void registerGeneticModifications(int posX, int posY) {
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), changeState(State.INTRO));
        this.addButton(backButton);
        for (int i = 0; i < GeneticModsState.values().length; i++) {
            GeneticModsState state = GeneticModsState.values()[i];
            PlankButton button = new PlankButton(40 + i * 140, 20, 120, 30, state.component, btn -> {
                this.geneticState = state;
                this.init(this.minecraft, this.width, this.height);
            });
            if (this.geneticState == state) {
                button.active = false;
            }
            this.addButton(button);
        }
        this.finishButton = this.addButton(new FactionButton(posX + 40, posY + 80, 200, 20, new TranslationTextComponent(ModTexts.FINISH.getKey(), getFinishPrice()), btn -> finish()));
        switch (this.geneticState) {
            case HYBRID_RACES:
                this.initHybridableRaces(posX, posY);
                break;
            case PRISTINE_RACES:
                this.initPristineRaces(posX, posY);
                break;
            case OTHER_GENES:
                this.initOtherGenes(posX, posY);
                break;
        }
    }

    private void finish() {
        if (this.chosenPristineRace() == 0) {
            boolean isHybrid = false;
            ResourceLocation race = xyz.pixelatedw.mineminenomi.init.ModRaces.EMPTY.getId();
            Map<ResourceLocation, Float> genomeMap = new HashMap<>();

            for (int i = 0; i < hybridGenesSliders.length; i++) {
                OptionSlider slider = hybridGenesSliders[i];
                race = HybridRaces.values()[i].getId();
                genomeMap.put(race, (float) slider.getValueStrict());
                if (slider.getValueStrict() != 1 && slider.getValueStrict() != 0) {
                    isHybrid = true;
                } else if (slider.getValueStrict() == 1) {
                    break;
                }
            }
            if (isHybrid) {
                this.entityStats.setRace(ModRaces.HYBRID.getId());
                this.medicalData.setGenome(genomeMap);
            } else {
                this.entityStats.setRace(race);
            }
            ModNetwork.sendToServer(new CSyncEntityStatsPacket(this.player.getId(), this.entityStats));
            ModNetwork.sendToServer(new CSyncNonFruitDataPacket(this.player.getId(), this.medicalData));
        } else {
            this.entityStats.setRace(PristineRaces.values()[chosenPristineRace() - 1].getId());
            ModNetwork.sendToServer(new CSyncEntityStatsPacket(this.player.getId(), this.entityStats));
        }
        IAbilityData abilityData = AbilityDataCapability.get(player);
        GenesAbility ability = abilityData.getPassiveAbility(GenesAbility.INSTANCE);
        if (ability != null) {
            for (int i = 0; i < this.otherGenesSliders.length; i++) {
                ability.getGenes().put(ModifiableAttributes.values()[i], this.otherGenesSliders[i].getValue());
            }
            ModNetwork.sendToServer(new CUpdatePassiveAbilityDataPacket(this.player, ability));
        }
        ModNetwork.sendToServer(new CRestartPlayerPacket());
        player.refreshDimensions();
        this.minecraft.setScreen(null);
    }

    private int chosenPristineRace() {
        for (int i = 0; i < pristineRaceButtons.length; i++) {
            if (!pristineRaceButtons[i].active) {
                return i;
            }
        }
        return 0;
    }

    private void initOtherGenes(int posX, int posY) {
        if (this.otherGenesSliders.length == 0) {
            this.otherGenesSliders = new OptionSlider[ModifiableAttributes.values().length];
            for (int i = 0; i < otherGenesSliders.length; i++) {
                int yPos = i % 4;
                int xPos = i / 4;
                ModifiableAttributes attribute = ModifiableAttributes.values()[i];
                OptionSlider slider = new OptionSlider(posX - 220 + 110 * xPos, posY + 30 * yPos - 50, 100, 20, new TranslationTextComponent("gui.gene." + WyHelper.getResourceName(attribute.name())), 0) {
                    @Override
                    public void updateMessage() {
                        this.setMessage(new StringTextComponent(this.message.getString() + ": " + this.getValue()));
                    }
                };
                slider.setMaxValue(attribute.getMax());
                slider.setMinValue(attribute.getMin());
                slider.setSteps(attribute.getSteps());
                GenesAbility ability = abilityData.getPassiveAbility(GenesAbility.INSTANCE);
                if (ability != null) {
                    slider.setValue(ability.getGenes().getOrDefault(attribute, 0d));
                } else {
                    slider.setValue(0);
                }
                slider.updateMessage();
                otherGenesSliders[i] = this.addButton(slider);
            }
            int yPos = otherGenesSliders.length % 4;
            int xPos = otherGenesSliders.length / 4;
            this.germaGenes = new PlankToggle(posX - 220 + 110 * xPos, posY + 30 * yPos - 50, 100, 20, ModTexts.WIP, btn -> {
            });//TODO: Well, germa genes
        } else {
            for (OptionSlider slider : this.otherGenesSliders) {
                this.addButton(slider);
            }
        }
    }

    private void initPristineRaces(int posX, int posY) {
        if (this.pristineRaceButtons.length == 0) {
            this.pristineRaceButtons = new PlankButton[PristineRaces.values().length + 1];
            boolean hasOtherRace = false;
            for (int i = 1; i < pristineRaceButtons.length; i++) {
                int buttonId = i;
                PristineRaces race = PristineRaces.values()[i - 1];
                this.pristineRaceButtons[i] = this.addButton(new PlankButton(posX - 180, posY + 30 * i - 50, 120, 20, new TranslationTextComponent("race." + getRaceName(race.race)), btn -> {
                    choosePristineRace(buttonId);
                }, WIP));
                this.pristineRaceButtons[i].active = PristineRaces.values()[i - 1].canHave(this.player);
                if (entityStats.getRace().equals(race.race)) {
                    hasOtherRace = true;
                    this.pristineRaceButtons[i].active = false;
                }
            }
            this.pristineRaceButtons[0] = this.addButton(new PlankButton(posX - 180, posY - 50, 120, 20, new TranslationTextComponent("race.empty"), btn -> {
                choosePristineRace(0);
            }));
            this.pristineRaceButtons[0].active = hasOtherRace;
        } else {
            for (PlankButton button : this.pristineRaceButtons) {
                this.addButton(button);
            }
        }
    }

    private void choosePristineRace(int choice) {
        for (int i = 0; i < pristineRaceButtons.length; i++) {
            if (i == choice) {
                pristineRaceButtons[i].active = false;
            } else if (i != 0) {
                pristineRaceButtons[i].active = PristineRaces.values()[i - 1].canHave(this.player);
            } else {
                pristineRaceButtons[i].active = true;
            }
        }
    }

    private void initHybridableRaces(int posX, int posY) {
        if (this.hybridGenesSliders.length == 0) {
            this.hybridGenesSliders = new OptionSlider[HybridRaces.values().length];
            ResourceLocation race = EntityStatsCapability.get(this.player).getRace();
            for (int i = 0; i < HybridRaces.values().length; i++) {
                HybridRaces hybridRace = HybridRaces.values()[i];
                double val;
                if (race.equals(ModRaces.HYBRID.getId())) {
                    val = this.medicalData.getGenome().computeIfAbsent(hybridRace.getId(), s -> 0f);
                } else if (race.equals(hybridRace.getId())) {
                    val = 1;
                } else {
                    val = 0;
                }
                TranslationTextComponent raceName = new TranslationTextComponent("race." + getRaceName(hybridRace.race));
                Button.ITooltip tooltip;
                if (!hybridRace.canModify(this.player)) {
                    tooltip = (btn, matrix, mouseX, mouseY) -> this.renderTooltip(matrix, new TranslationTextComponent(ModTexts.GENOME_NOT_INCLUDED.getKey(), raceName.getString(), hybridRace.requirement == null ? null : hybridRace.requirement.getLocalizedTitle()), mouseX, mouseY);
                } else {
                    tooltip = Button.NO_TOOLTIP;
                }
                hybridGenesSliders[i] = this.addButton(new OptionSlider(posX - 180, posY + 30 * i - 50, 200, 20, raceName, val) {
                    @Override
                    public void updateMessage() {
                        this.setMessage(new StringTextComponent(this.message.getString() + ": " + (int) (this.value * 100) + "%"));
                    }

                    @Override
                    public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
                        tooltip.onTooltip(null, matrixStack, mouseX, mouseY);
                    }
                });
                hybridGenesSliders[i].updateMessage();
                hybridGenesSliders[i].active = hybridRace.canModify(this.player);
            }
        } else {
            for (OptionSlider slider : this.hybridGenesSliders) {
                this.addButton(slider);
            }
        }
    }

    private static String getRaceName(RegistryObject<RaceId> race) {
        return race.getId().toString().replace(':', '.');
    }

    private void registerCustomSeraphim(int posX, int posY) {
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), changeState(State.SHOP));
        this.addButton(backButton);
    }

    private void registerBuyPacifistaState(int posX, int posY) {
        ArrayList<Product> products = new ArrayList<>();
        if (this.type == Type.VEGAPUNK) {
            for (PacifistaModel pacifistaModel : PacifistaModel.values()) {
                products.add(new PacifistaProduct(pacifistaModel));
            }
            for (EntityType<? extends SeraphimEntity> seraphim : ModEntityTypes.SERAPHIMS) {
                products.add(new EntityProduct(1000000, seraphim));
            }
        } else if (this.type == Type.CAESAR) {
            products.add(new BasicItemProduct(15000, ModItems.ENERGY_STEROID));
            products.add(new BasicItemProduct(20000, ModItems.SULONG_BALL));
        }
        this.shopScreenPanel = new ShopScreenPanel(this, this.entityStats, products);
        this.children.add(shopScreenPanel);
        this.setFocused(shopScreenPanel);
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), changeState(State.INTRO));
        this.addButton(backButton);
    }

    private void registerQuestState(List<QuestId<?>> cyborgQuests, int posX, int posY) {
        this.availableQuestsPanel = new AvailableQuestsListScreenPanel(this, this.questData, cyborgQuests);
        this.children.add(this.availableQuestsPanel);
        this.setFocused(this.availableQuestsPanel);
        FactionButton backButton = new FactionButton(posX - 180, posY + 80, 200, 20, new TranslationTextComponent("gui.cancel"), changeState(State.INTRO));
        this.addButton(backButton);
    }

    private void registerIntroState(int posX, int posY) {
        if (this.type == Type.JUDGE) {
            createButton(posX, posY, ModTexts.GENETIC_COLLECTION, onQuestPress(ModQuests.GEN_MODIFICATION_QUESTS, changeState(State.GENETIC_QUESTS)));
            createButton(posX, posY, ModTexts.MODIFY_ME, changeState(State.GENETIC_MODIFICATIONS));
        } else if (this.type == Type.QUEEN) {
            createButton(posX, posY, ModTexts.CYBORG_UPGRADES, onQuestPress(ModQuests.CYBORG_QUESTS, changeState(State.CYBORG_QUESTS)));
        } else if (this.type == Type.CAESAR) {
            createButton(posX, posY, new StringTextComponent("Do an experiment on me"), (btn) -> {
                applyRandomResult();
            });
        }
        createButton(posX, posY, ModTexts.SHOP, changeState(State.SHOP));
    }

    private void applyRandomResult() {
        this.entityStats.alterBelly(12500, StatChangeSource.STORE);
        ModNetwork.sendToServer(new CSyncEntityStatsPacket(player.getId(), entityStats));
        INonFruitData data = NonFruitDataCapability.get(player);
        Stream<ExperimentResult> experiments = ModRegistries.EXPERIMENT_RESULTS.getEntries().stream().map(Map.Entry::getValue);
        float rand = player.getRandom().nextFloat();
        ExperimentResult.Type type;
        if (rand < 0.05f) {
            type = ExperimentResult.Type.FATAL;
        } else if (rand > 0.9998f) {
            type = ExperimentResult.Type.SUCCESSFUL;
        } else if (rand < 0.75f) {
            type = ExperimentResult.Type.NEGATIVE;
        } else if (rand > 0.9f) {
            type = ExperimentResult.Type.POSITIVE;
        } else {
            type = ExperimentResult.Type.NEUTRAL;
        }
        List<ExperimentResult> collect = experiments.filter(exp -> exp.getType() == type)
                .filter((e) -> !data.getExperiments().contains(e)).collect(Collectors.toList());
        //data.addExperiment(collect.get(player.getRandom().nextInt(collect.size())));
        data.addExperiment(ModExperimentResults.GENETIC_DRIFT.get());
        ModNetwork.sendToServer(new CSyncNonFruitDataPacket(player.getId(), data));
    }

    private void createButton(int posX, int posY, ITextComponent text, Button.IPressable onPress) {
        FactionButton cyborgButton = new FactionButton(posX - 180, posY - 50 + this.buttons.size() * 20, 100, 20, text, onPress);
        this.addButton(cyborgButton);
    }

    public boolean isAnimationComplete() {
        return this.animationTime >= 5.0F;
    }

    private Button.IPressable onQuestPress(List<QuestId<?>> quests, Button.IPressable onHasQuests) {
        return (btn) -> {
            boolean hasQuests = false;

            for (int i = 0; i <= quests.size() - 1; ++i) {
                QuestId<?> quest = quests.get(i);
                if (!this.questData.hasFinishedQuest(quest)) {
                    hasQuests = true;
                    break;
                }
            }

            if (hasQuests) {
                onHasQuests.onPress(btn);
            } else {
                String message = (new TranslationTextComponent(ModI18n.TRAINER_NO_TRIALS_AVAILABLE)).getString();
                this.startMessage = new SequencedString(message, 250, this.font.width(message) / 2);
            }
        };
    }

    private Button.IPressable changeState(State state) {
        return (btn) -> {
            this.guiState = state;
            this.init(this.getMinecraft(), this.width, this.height);
        };
    }

    public enum Type {
        VEGAPUNK,
        CAESAR,
        JUDGE,
        QUEEN
    }

    private enum State {
        INTRO,
        CYBORG_QUESTS,
        GENETIC_QUESTS,
        GENETIC_MODIFICATIONS,
        SHOP,
        CUSTOM_SERAPHIM,
        EXPERIMENT,
    }

    private enum GeneticModsState {
        HYBRID_RACES(ModTexts.HYBRID_RACES),
        PRISTINE_RACES(ModTexts.PRISTINE_RACES),
        OTHER_GENES(ModTexts.OTHER_GENES);

        private final ITextComponent component;

        GeneticModsState(ITextComponent component) {
            this.component = component;
        }
    }

    public enum HybridRaces implements IExtensibleEnum, IHasQuestRequirement {
        HUMAN(xyz.pixelatedw.mineminenomi.init.ModRaces.HUMAN, null),
        FISHMAN(xyz.pixelatedw.mineminenomi.init.ModRaces.FISHMAN, FishmanGenesQuest.INSTANCE),
        MINK(xyz.pixelatedw.mineminenomi.init.ModRaces.MINK, MinkGenesQuest.INSTANCE),
        GIANT(ModRaces.GIANT, null),
        ;

        private final RegistryObject<RaceId> race;
        @Nullable
        private final QuestId<?> requirement;

        HybridRaces(@Nonnull RegistryObject<RaceId> race, @Nullable QuestId<?> requirement) {
            this.race = race;
            this.requirement = requirement;
        }

        public boolean canModify(LivingEntity entity) {
            if (this.requirement == null || QuestHelper.hasFinishedQuest(entity, this.requirement)) {
                return true;
            } else {
                return EntityStatsCapability.get(entity).getRace().equals(this.race.getId());
            }
        }

        public ResourceLocation getId() {
            return this.race.getId();
        }

        public static HybridRaces create(String name, RegistryObject<RaceId> race, QuestId<?> requirement) {
            throw new IllegalStateException(name + "not created");
        }

        @Override
        public QuestId<?> getQuest() {
            return requirement;
        }
    }

    public enum PristineRaces implements IExtensibleEnum {
        ANCIENT_GIANT(ModRaces.ANCIENT_GIANT, entity -> false),
        ;

        private final Predicate<LivingEntity> requirement;
        private final RegistryObject<RaceId> race;

        PristineRaces(RegistryObject<RaceId> race, @Nullable QuestId<?> requirement) {
            this(race, requirement == null ? Predicates.alwaysTrue() : QuestHelper.questFinished(requirement)::canUnlock);
        }

        PristineRaces(RegistryObject<RaceId> race, Predicate<LivingEntity> requirement) {
            this.race = race;
            if (requirement == null) {
                this.requirement = Predicates.alwaysFalse();
            } else {
                this.requirement = requirement;
            }
        }

        public ResourceLocation getId() {
            return this.race.getId();
        }

        public boolean canHave(LivingEntity entity) {
            return this.requirement.test(entity);
        }

        public static PristineRaces create(String name, RegistryObject<RaceId> race, Predicate<LivingEntity> requirement) {
            throw new IllegalStateException(name + "not created");
        }
    }
}