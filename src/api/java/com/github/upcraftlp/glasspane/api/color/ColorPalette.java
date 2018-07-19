package com.github.upcraftlp.glasspane.api.color;

import javax.annotation.Nullable;

public class ColorPalette implements IColorPalette {

    private final int borderColor, fillColor, textColor, accentColor;

    public ColorPalette(@Nullable Integer borderColor, @Nullable Integer fillColor, @Nullable Integer textColor, @Nullable Integer accentColor) {
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
    public int getFillColor() {
        return fillColor;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }

    @Override
    public int getAccentColor() {
        return accentColor;
    }
}