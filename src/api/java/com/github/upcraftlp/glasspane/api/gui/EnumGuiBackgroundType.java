package com.github.upcraftlp.glasspane.api.gui;

import com.github.upcraftlp.glasspane.api.client.color.ColorPalette;
import com.github.upcraftlp.glasspane.api.client.color.IColorPalette;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * what background style a GUI should use
 */
public enum EnumGuiBackgroundType {

    VANILLA     (0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF, -1072689136,    -804253680, "glasspane:textures/gui/skins/background_vanilla.png"      ),
    FUTURISTIC  (null,       null,       null,       null,       null,           null,       "glasspane:textures/gui/skins/background_futuristic.png"   ),
    THERMAL     (null,       null,       null,       null,       null,           null,       "glasspane:textures/gui/skins/background_thermal.png"      );

    public final int worldBackgroundColorStart;
    public final int worldBackgroundColorEnd;
    public final ResourceLocation backgroundTexture;
    public final IColorPalette colors;

    EnumGuiBackgroundType(@Nullable Integer borderColor, @Nullable Integer fillColor, @Nullable Integer textColor, @Nullable Integer accentColor, @Nullable Integer worldBackgroundColorStart, @Nullable Integer worldBackgroundColorEnd, @Nullable ResourceLocation backgroundTexture) {
        this.colors = new ColorPalette(borderColor, fillColor, textColor, accentColor);
        this.worldBackgroundColorStart = worldBackgroundColorStart != null ? worldBackgroundColorStart : 0;
        this.worldBackgroundColorEnd = worldBackgroundColorEnd != null ? worldBackgroundColorEnd : 0;
        this.backgroundTexture = backgroundTexture != null ? backgroundTexture : ForgeUtils.MISSING;
    }

    EnumGuiBackgroundType(@Nullable Integer borderColor, @Nullable Integer fillColor, @Nullable Integer textColor, @Nullable Integer accentColor, @Nullable Integer worldBackgroundColorStart, @Nullable Integer worldBackgroundColorEnd, @Nullable String backgroundTexture) {
        this(borderColor, fillColor, accentColor, textColor, worldBackgroundColorStart, worldBackgroundColorEnd, backgroundTexture != null ? new ResourceLocation(backgroundTexture) : null);
    }
}
