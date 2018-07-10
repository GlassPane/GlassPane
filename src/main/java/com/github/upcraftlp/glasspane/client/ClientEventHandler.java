package com.github.upcraftlp.glasspane.client;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.item.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Mod.EventBusSubscriber(modid = GlassPane.MODID, value = Side.CLIENT)
public class ClientEventHandler {

    //FIXME move to Colors class?
    private static boolean shouldRenderBackgroundColor = false;
    private static final int GRAY = 0xFF3F3F3F; //something gray-ish
    private static final int PURPLE_BLUE = 0xFF320E6A;
    private static final int YELLOW = 0xFFFAFA1E;
    private static final int BLACK = 0xFF000000;
    private static final int unused = new Color(0, 0, 0).getRGB(); //TODO this is for debug only, remove before release!

    @SubscribeEvent
    public static void preRenderTooltip(RenderTooltipEvent.Pre event) {
        Item item = event.getStack().getItem();
        if(item instanceof ItemBase && ((ItemBase) item).hasAdvancedTooltip(event.getStack()) && Lens.client.showAdvancedTooltipInfo && (Minecraft.getMinecraft().gameSettings.advancedItemTooltips || Keyboard.isKeyDown(Lens.client.keyAdvandedTooltip.getKeyCode()))) {
            shouldRenderBackgroundColor = true;
        }
    }

    @SubscribeEvent
    public static void renderTooltipBackground(RenderTooltipEvent.Color event) {
        if(shouldRenderBackgroundColor) {
            //TODO how to indicate debug tooltip?
            //event.setBackground(PURPLE_BLUE);
            //event.setBorderStart(YELLOW);
            //event.setBorderEnd(BLACK);
        }
    }

    @SubscribeEvent
    public static void postRenderTooltipBackground(RenderTooltipEvent.PostBackground event) {
        if(shouldRenderBackgroundColor) shouldRenderBackgroundColor = false;
    }

}
