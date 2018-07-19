package com.github.upcraftlp.glasspane.client.gui;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.client.gui.IGuiElement;
import com.github.upcraftlp.glasspane.api.color.IColorPalette;
import com.github.upcraftlp.glasspane.api.util.MathUtils;
import com.github.upcraftlp.glasspane.config.Lens;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Locale;
import java.util.function.Consumer;

public class GuiColorPicker extends Gui implements IGuiElement {

    private static final int BORDER_SIZE = 1;
    private static final int COLOR_PICKER_ALPHA = 255;
    private static final int RENDER_Z_LEVEL = 100;

    private final  Consumer<Color> callback;
    private final int x, y, width, height;
    public final int componentId;
    private final IColorPalette colors;

    private float hue = 0.0F;
    private float saturation = 0.0F;
    private float brightness = 1.0F; //TODO slider

    private double centerX, centerY, radius;
    private Color color;

    public GuiColorPicker(int componentId, Consumer<Color> callback, int x, int y, int width, int height, IColorPalette colorPalette) {
        this.componentId = componentId;
        this.callback = callback;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        colors = colorPalette;
        this.centerX = this.width / 2.0D;  // 1/2
        this.centerY = this.height * 0.4D; // 2/5
        this.radius = Math.min((this.width) / 2.0D, (this.height) / 3.0D) * 0.8D;
    }

    public void setSelectedColor(Color color) {
        float[] values = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.hue = values[0];
        this.saturation = values[1];
        this.brightness = values[2];
        this.color = color;
        this.callback.accept(color);
    }

    public void setSelectedColor(double hue, double saturation, double brightness) {
        this.hue = (float) hue;
        this.saturation = (float) saturation;
        this.brightness = (float) brightness;
        this.color = Color.getHSBColor(this.hue, this.saturation, this.brightness);
        if(Lens.debugMode) GlassPane.getDebugLogger().info("Color-Wheel: H: {}, S: {}, B: {}, RGB: #{}", this.hue, this.saturation, this.brightness, Integer.toHexString(this.color.getRGB()).substring(2).toUpperCase(Locale.ROOT));
        this.callback.accept(this.color);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.trackMouseColor(mouseX, mouseY);
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.trackMouseColor(mouseX, mouseY);
    }

    private void trackMouseColor(int mouseX, int mouseY) {
        double relX = this.centerX - mouseX + this.x;
        double relY = this.centerY - mouseY + this.y;
        double distance = Math.hypot(relX, relY);
        if(distance <= this.radius) {
            double angleRad = (Math.atan2(relX, relY) + Math.PI - Math.PI / 3) % MathUtils.TAU;
            this.setSelectedColor(1.0D - angleRad / MathUtils.TAU, distance / radius, this.brightness);
        }
    }

    @Override
    public void drawElement() {
        Rectangle dimension = this.getSize();
        drawRect((int) dimension.getMinX(), (int) dimension.getMinY(), (int) dimension.getMaxX(), (int) dimension.getMaxY(), this.colors.getBorderColor());
        drawRect((int) dimension.getMinX() + BORDER_SIZE, (int) dimension.getMinY() + BORDER_SIZE, (int) dimension.getMaxX() - BORDER_SIZE * 2, (int) dimension.getMaxY() - BORDER_SIZE * 2, this.colors.getFillColor());

        //drawRect(this.buttonX, this.buttonY, this.buttonX + this.buttonWidth, this.buttonY + this.buttonHeight, this.rgbColor);

        //TODO config setting?
        //may only be an even divisor of 360 or the circle will look like a rainbow pac-man
        //more than 120 really doesn't make sense at all and just wastes CPU and GPU power.
        int EDGES = 120;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexBuffer = tessellator.getBuffer();

            vertexBuffer.setTranslation(this.x, this.y, RENDER_Z_LEVEL);
            vertexBuffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            {
                Color centerColorRGB = new Color(Color.HSBtoRGB(0.0F, 0.0F, this.brightness)); //cannot just use white here because brightness might be different -> gray / black
                vertexBuffer.pos(this.centerX, this.centerY, 0).color(centerColorRGB.getRed(), centerColorRGB.getGreen(), centerColorRGB.getBlue(), COLOR_PICKER_ALPHA).endVertex();
                double triangleSize = MathUtils.TAU / EDGES;
                for(int i = 0; i < EDGES + 1; i++) {
                    double angleRad = MathUtils.TAU - i * triangleSize;
                    Color pixelColorRGB = new Color(Color.HSBtoRGB((float) (angleRad / MathUtils.TAU), 1.0F, this.brightness));
                    vertexBuffer.pos(this.centerX + Math.cos(angleRad) * radius, this.centerY + Math.sin(angleRad) * radius, 0).color(pixelColorRGB.getRed(), pixelColorRGB.getGreen(), pixelColorRGB.getBlue(), COLOR_PICKER_ALPHA).endVertex();
                }
            }
            tessellator.draw();
        }
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    @Override
    public Rectangle getSize() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle getCircleSize() {
        int ceilRadius = (int) Math.ceil(this.radius * 2.0D);
        return new Rectangle((int) (this.centerX - this.radius), (int) (this.centerY - this.radius), ceilRadius, ceilRadius);
    }

}
