package com.github.upcraftlp.glasspane.api.gui;

import net.minecraft.util.ResourceLocation;

/**
 * what background style a GUI should use
 */
public enum EnumGuiBackgroundType {

    VANILLA(0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF, -1072689136, -804253680, "glasspane:textures/gui/skins/background_vanilla.png"),
    FUTURISTIC(0, 0, 0, 0, 0, 0, new ResourceLocation("glasspane:textures/gui/skins/background_futuristic.png")),
    THERMAL(0, 0, 0, 0, 0, 0, new ResourceLocation("glasspane:textures/gui/skins/background_thermal.png"));

    public final int borderColor;
    public final int fillColor;
    public final int accentColor;
    public final int textColor;
    public final int worldBackgroundColorStart;
    public final int worldBackgroundColorEnd;
    public final ResourceLocation backgroundTexture;

    EnumGuiBackgroundType(int borderColor, int fillColor, int accentColor, int textColor, int worldBackgroundColorStart, int worldBackgroundColorEnd, ResourceLocation backgroundTexture) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.accentColor = accentColor;
        this.textColor = textColor;
        this.worldBackgroundColorStart = worldBackgroundColorStart;
        this.worldBackgroundColorEnd = worldBackgroundColorEnd;
        this.backgroundTexture = backgroundTexture;
    }

    EnumGuiBackgroundType(int borderColor, int fillColor, int accentColor, int textColor, int worldBackgroundColorStart, int worldBackgroundColorEnd, String backgroundTexture) {
        this(borderColor, fillColor, accentColor, textColor, worldBackgroundColorStart, worldBackgroundColorEnd, new ResourceLocation(backgroundTexture));
    }
}
