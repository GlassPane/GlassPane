package com.github.upcraftlp.glasspane.api.color;

/**
 * some default colors, always in the format {@code #AARRGGBB}
 */
@SuppressWarnings("WeakerAccess")
public class DefaultColors {

    public static final Foreground FOREGROUND = new Foreground();
    public static final Background BACKGROUND = new Background();

    /**
     * Foreground Colors
     */
    public static class Foreground {

        public final int BLACK           = 0xFF000000;
        public final int DARK_BLUE       = 0xFF0000AA;
        public final int DARK_GREEN      = 0xFF00AA00;
        public final int DARK_AQUA       = 0xFF00AAAA;
        public final int DARK_RED        = 0xFFAA0000;
        public final int DARK_PURPLE     = 0xFFAA00AA;
        public final int GOLD            = 0xFFFFAA00;
        public final int GRAY            = 0xFFAAAAAA;
        public final int DARK_GRAY       = 0xFF555555;
        public final int BLUE            = 0xFF5555FF;
        public final int GREEN           = 0xFF55FF55;
        public final int AQUA            = 0xFF55FFFF;
        public final int RED             = 0xFFFF5555;
        public final int LIGHT_PURPLE    = 0xFFFF55FF;
        public final int YELLOW          = 0xFFFFFF55;
        public final int WHITE           = 0xFFFFFFFF;
    }

    /**
     * Background Colors
     */
    public static class Background {

        public final int BLACK           = 0xFF000000;
        public final int DARK_BLUE       = 0xFF00002A;
        public final int DARK_GREEN      = 0xFF002A00;
        public final int DARK_AQUA       = 0xFF002A2A;
        public final int DARK_RED        = 0xFF2A0000;
        public final int DARK_PURPLE     = 0xFF2A002A;
        public final int GOLD            = 0xFF2A2A00;
        public final int GRAY            = 0xFF2A2A2A;
        public final int DARK_GRAY       = 0xFF151515;
        public final int BLUE            = 0xFF15153F;
        public final int GREEN           = 0xFF153F15;
        public final int AQUA            = 0xFF153F3F;
        public final int RED             = 0xFF3F1515;
        public final int LIGHT_PURPLE    = 0xFF3F153F;
        public final int YELLOW          = 0xFF3F3F15;
        public final int WHITE           = 0xFF3F3F3F;
    }

    public static final int PURPLE_BLUE     = 0xFF320E6A;
    public static final int YELLOW          = 0xFFFAFA1E;

    //Discord color palette (see )
    public static final int DISCORD_BLUE    = 0xFF7289DA;

    //GUI color palette (see http://www.color-hex.com/color-palette/15737)
    public static final int LIGHT_GRAY_1    = 0xFFC6C6C6;
    public static final int LIGHT_GRAY      = 0xFF8B8B8B;
    public static final int GRAY            = 0xFF373737;
    public static final int DARK_GRAY       = 0xFF373737;
    public static final int DARK_GRAY_1     = 0xFF212121;

    //portal color palette (see http://www.color-hex.com/color-palette/7641)
    public static final int PORTAL_BLUE_1   = 0xFF0065FF;
    public static final int PORTAL_BLUE_2   = 0xFF00A2FF;
    public static final int PORTAL_ORANGE_1 = 0xFFFF5D00;
    public static final int PORTAL_ORANGE_2 = 0xFFFF9A00;
}
