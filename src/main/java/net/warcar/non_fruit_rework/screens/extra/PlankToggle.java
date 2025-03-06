package net.warcar.non_fruit_rework.screens.extra;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import xyz.pixelatedw.mineminenomi.screens.extra.buttons.PlankButton;

public class PlankToggle extends PlankButton {
    boolean value = false;

    public PlankToggle(int posX, int posY, int width, int height, ITextComponent string, IPressable onPress, ITooltip onTooltip) {
        super(posX, posY, width, height, string, onPress, onTooltip);
    }

    public PlankToggle(int posX, int posY, int width, int height, ITextComponent string, IPressable onPress) {
        this(posX, posY, width, height, string, onPress, Button.NO_TOOLTIP);
    }
}
