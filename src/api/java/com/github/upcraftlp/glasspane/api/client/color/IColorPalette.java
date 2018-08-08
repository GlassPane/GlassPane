package com.github.upcraftlp.glasspane.api.color;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IColorPalette {
    int getBorderColor();

    int getFillColor();

    int getTextColor();

    int getAccentColor();
}
