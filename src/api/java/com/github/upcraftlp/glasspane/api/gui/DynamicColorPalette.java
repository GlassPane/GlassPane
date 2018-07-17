package com.github.upcraftlp.glasspane.api.gui;

import javax.annotation.Nullable;

public class DynamicColorPalette implements IDynamicColorPalette {

    private int borderColor, fillColor, textColor, accentColor;

    public DynamicColorPalette(@Nullable Integer borderColor, @Nullable Integer fillColor, @Nullable Integer textColor, @Nullable Integer accentColor) {
        this.borderColor = borderColor != null ? borderColor : 0;
        this.fillColor = fillColor != null ? fillColor : 0;
        this.textColor = textColor != null ? textColor : 0;
        this.accentColor = accentColor != null ? accentColor : 0;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public int getFillColor() {
        return fillColor;
    }

    @Override
    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public int getAccentColor() {
        return accentColor;
    }

    @Override
    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
    }
}
