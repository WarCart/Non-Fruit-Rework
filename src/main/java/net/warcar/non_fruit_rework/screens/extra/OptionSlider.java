package net.warcar.non_fruit_rework.screens.extra;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class OptionSlider extends AbstractSlider {
    protected final ITextComponent message;
    protected double maxValue = 1;
    protected double minValue = 0;
    protected double steps = 100;

    public OptionSlider(int x, int y, int width, int height, ITextComponent message, double value) {
        super(x, y, width, height, message, value);
        this.message = message;
    }

    @Override
    public void updateMessage() {
    }

    @Override
    protected void applyValue() {
        this.value = (double) Math.round(this.value * steps) / steps;
    }

    public double getValue() {
        double v = this.value;
        return applyValue(v);
    }

    public double applyValue(double v) {
        return (double) Math.round((v * (maxValue - minValue) + minValue) * 100) / 100;
    }

    public double getValueStrict() {
        return this.value;
    }

    public void setValue(double value) {
        this.setValueStrict(unapplyValue(value));
    }

    public double unapplyValue(double value) {
        return (value - minValue) / (maxValue - minValue);
    }

    public void setValueStrict(double value) {
        double oldValue = this.value;
        this.value = MathHelper.clamp(value, 0.0D, 1.0D);
        if (oldValue != this.value) {
            this.applyValue();
        }

        this.updateMessage();
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getSteps() {
        return steps;
    }

    public void setSteps(double step) {
        this.steps = step;
    }
}
