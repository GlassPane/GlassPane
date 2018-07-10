package com.github.upcraftlp.glasspane.api.gui;

import net.minecraft.util.ResourceLocation;

/**
 * what background style a GUI should use
 */
public enum EnumGuiBackgroundType {

    VANILLA(new ResourceLocation("glasspane:textures/gui/skins/background_vanilla.png")),
    FUTURISTIC(new ResourceLocation("glasspane:textures/gui/skins/background_futuristic.png")),
    THERMAL(new ResourceLocation("glasspane:textures/gui/skins/background_thermal.png"));

    private final ResourceLocation textureLocation;

    EnumGuiBackgroundType(String texture) {
        this(new ResourceLocation(texture));
    }

    EnumGuiBackgroundType(ResourceLocation texture) {
        this.textureLocation = texture;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
