package com.github.upcraftlp.glasspane.api.client.color;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IDynamicColorPalette extends IColorPalette {

    void setBorderColor(int borderColor);

    void setFillColor(int fillColor);

    void setTextColor(int textColor);

    void setAccentColor(int accentColor);
}
