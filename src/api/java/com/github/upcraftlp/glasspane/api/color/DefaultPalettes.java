package com.github.upcraftlp.glasspane.api.color;

/**
 * shared default color palettes that look nice and smooth
 */
public class DefaultPalettes {

    //TODO brown-ish and green colors from vanilla mc
    public static final IColorPalette VANILLA = new ColorPalette(DefaultColors.BACKGROUND.WHITE, DefaultColors.BACKGROUND.BLACK, DefaultColors.FOREGROUND.WHITE, DefaultColors.FOREGROUND.WHITE);

    /**
     * see "http://www.color-hex.com/color-palette/25362"
     */
    public static final IColorPalette DISCORD = new ColorPalette(0xFF23272A, DefaultColors.DARK_GRAY_1, 0xFF99AAB5, DefaultColors.DISCORD_BLUE);
}
