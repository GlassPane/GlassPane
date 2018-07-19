package com.github.upcraftlp.glasspane.api.color;

public interface IDynamicColorPalette extends IColorPalette {

    void setBorderColor(int borderColor);

    void setFillColor(int fillColor);

    void setTextColor(int textColor);

    void setAccentColor(int accentColor);
}
