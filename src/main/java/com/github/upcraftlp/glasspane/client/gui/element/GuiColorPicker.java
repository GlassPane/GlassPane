package com.github.upcraftlp.glasspane.client.gui.element;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.gui.GuiConstants;
import com.github.upcraftlp.glasspane.api.gui.IGuiElement;
import com.github.upcraftlp.glasspane.api.client.color.DefaultColors;
import com.github.upcraftlp.glasspane.api.client.color.IColorPalette;
import com.github.upcraftlp.glasspane.api.util.MathUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Locale;
import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class GuiColorPicker extends Gui implements IGuiElement, GuiPageButtonList.GuiResponder {

    //TODO get rid of recursive update color calls!
    private static final int BORDER_SIZE = 1;
    private static final int COLOR_PICKER_ALPHA = 255;

    /**
     * may only be an even divisor of 360 or the circle will look like a rainbow pac-man
     * more than 120 really doesn't make sense at all and just wastes CPU and GPU power.
     */
    private static final int CIRCLE_TRIANGLE_EDGES = 120; //TODO config setting?

    private final  Consumer<Color> callback;
    private final int x, y, width, height;
    public final int componentId;
    private final IColorPalette colors;

    private float hue = 0.0F;
    private float saturation = 0.0F;

    private double centerX, centerY, radius;
    private GuiTextField textField;
    private GuiSlider sliderBrightness;

    public GuiColorPicker(int componentId, FontRenderer fontRenderer, Consumer<Color> callback, int x, int y, int width, int height, IColorPalette colorPalette) {
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
        Rectangle circle = this.getCircleSize();
        this.textField = new GuiTextField(0, fontRenderer, circle.x + 1, circle.y + circle.height + 4, circle.width - 2, 20);
        this.textField.setGuiResponder(this);
        this.textField.setValidator(this::apply);
        this.textField.setMaxStringLength(7);
        this.sliderBrightness = new GuiSlider(1, this::handleSliderInput, circle.x + 1, this.textField.y + this.textField.height + 4, circle.width - 2, 10, colorPalette);
        this.sliderBrightness.setSliderColorStart(DefaultColors.FOREGROUND.WHITE);
        this.sliderBrightness.setSliderColorStart(DefaultColors.FOREGROUND.BLACK);
    }

    @Nullable
    @Override
    public Object getChildComponentByID(int id) {
        if(id == this.textField.getId()) return this.textField;
        if(id == this.sliderBrightness.componentId) return this.sliderBrightness;
        return null;
    }

    private boolean apply(String s) {
        return s.matches("^[0-9a-fA-F]{0,6}?$");
    }

    private void setSelectedColor(double hue, double saturation, double brightness, boolean updateSlider) {
        this.hue = (float) hue;
        this.saturation = (float) saturation;
        this.sliderBrightness.setValue((float) brightness, updateSlider);
        Color color = Color.getHSBColor(this.hue, this.saturation, (float) brightness);
        if(GlassPane.isDebugMode()) GlassPane.getDebugLogger().info("Color-Wheel: H: {}, S: {}, B: {}, RGB: #{}", this.hue, this.saturation, brightness, Integer.toHexString(color.getRGB()).substring(2).toUpperCase(Locale.ROOT));
        this.callback.accept(color);
        this.textField.setText(Integer.toHexString(color.getRGB()).substring(2).toUpperCase(Locale.ROOT));
    }

    public void setSelectedColor(double hue, double saturation, double brightness) {
        this.setSelectedColor(hue, saturation, brightness, true);
    }

    public void setSelectedColor(Color color) {
        float[] values = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
        this.setSelectedColor(values[0], values[1], values[2]);
    }

    public void handleSliderInput(float value) {
        this.setSelectedColor(this.hue, this.saturation, value, false);
    }

    @Override
    public void updateScreen() {
        this.textField.updateCursorCounter();
        this.sliderBrightness.updateScreen();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        this.sliderBrightness.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseButton == GuiConstants.LEFT_MOUSE_BUTTON) this.trackMouseColor(mouseX, mouseY);
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.sliderBrightness.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if(clickedMouseButton == GuiConstants.LEFT_MOUSE_BUTTON) this.trackMouseColor(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.sliderBrightness.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() {
        this.sliderBrightness.handleMouseInput();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(this.textField.textboxKeyTyped(typedChar, keyCode)) this.updateColorFromTextBox(this.textField.getText());
        else {
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) this.sliderBrightness.keyTyped(typedChar, keyCode);
            else switch(keyCode) {
                case Keyboard.KEY_UP:
                case Keyboard.KEY_W:
                    this.setSelectedColor(this.hue, MathHelper.clamp(this.saturation + 0.05F, 0.0F, 1.0F), this.sliderBrightness.getValue());
                    break;
                case Keyboard.KEY_DOWN:
                case Keyboard.KEY_S:
                    this.setSelectedColor(this.hue, MathHelper.clamp(this.saturation - 0.05F, 0.0F, 1.0F), this.sliderBrightness.getValue());
                    break;
                case Keyboard.KEY_RIGHT:
                case Keyboard.KEY_D:
                    this.setSelectedColor((this.hue - 0.05F + 1.0F) % 1.0F, this.saturation, this.sliderBrightness.getValue());
                    break;
                case Keyboard.KEY_LEFT:
                case Keyboard.KEY_A:
                    this.setSelectedColor((this.hue + 0.05F) % 1.0F, this.saturation, this.sliderBrightness.getValue());
                    break;
            }
        }
    }

    private void trackMouseColor(int mouseX, int mouseY) {
        double relX = mouseX - this.centerX - this.x;
        double relY = this.centerY - mouseY + this.y;
        double distance = Math.hypot(relX, relY);
        if(distance <= this.radius) {
            double angleRad = (MathUtils.TAU + Math.atan2(relY, relX)) % MathUtils.TAU;
            this.setSelectedColor(angleRad / MathUtils.TAU, distance / radius, this.sliderBrightness.getValue(), false);
        }
    }

    @Override
    public void drawElement() {
        Rectangle dimension = this.getSize();
        drawRect((int) dimension.getMinX(), (int) dimension.getMinY(), (int) dimension.getMaxX(), (int) dimension.getMaxY(), this.colors.getBorderColor());
        drawRect((int) dimension.getMinX() + BORDER_SIZE, (int) dimension.getMinY() + BORDER_SIZE, (int) dimension.getMaxX() - BORDER_SIZE * 2, (int) dimension.getMaxY() - BORDER_SIZE * 2, this.colors.getFillColor());

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexBuffer = tessellator.getBuffer();

            vertexBuffer.setTranslation(this.x, this.y, 0);
            vertexBuffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            {
                Color centerColorRGB = new Color(Color.HSBtoRGB(0.0F, 0.0F, this.sliderBrightness.getValue())); //cannot just use white here because brightness might be different -> gray / black
                vertexBuffer.pos(this.centerX, this.centerY, 0).color(centerColorRGB.getRed(), centerColorRGB.getGreen(), centerColorRGB.getBlue(), COLOR_PICKER_ALPHA).endVertex();
                double triangleSize = MathUtils.TAU / CIRCLE_TRIANGLE_EDGES;
                for(int i = 0; i < CIRCLE_TRIANGLE_EDGES + 1; i++) {
                    double angleRad = i * triangleSize;
                    Color pixelColorRGB = new Color(Color.HSBtoRGB((float) (angleRad / MathUtils.TAU), 1.0F, this.sliderBrightness.getValue()));
                    vertexBuffer.pos(this.centerX + Math.cos(angleRad) * radius, this.centerY - Math.sin(angleRad) * radius, 0).color(pixelColorRGB.getRed(), pixelColorRGB.getGreen(), pixelColorRGB.getBlue(), COLOR_PICKER_ALPHA).endVertex();
                }
            }
            vertexBuffer.setTranslation(0, 0, 0);
            tessellator.draw();
        }
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        double angle = MathUtils.TAU - this.hue * MathUtils.TAU;

        int x = (int) (this.centerX + this.x + Math.cos(angle) * this.saturation * this.radius);
        int y = (int) (this.centerY + this.y + Math.sin(angle) * this.saturation * this.radius);

        drawRect(x - 1, y, x + 2, y + 1, this.colors.getAccentColor());
        drawRect(x, y - 1, x + 1, y + 2, this.colors.getAccentColor());

        this.textField.drawTextBox();
        this.sliderBrightness.drawElement();
    }

    @Override
    public Rectangle getSize() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle getCircleSize() {
        int ceilRadius = (int) Math.ceil(this.radius * 2.0D);
        return new Rectangle(this.x + (int) (this.centerX - this.radius), this.y + (int) (this.centerY - this.radius), ceilRadius, ceilRadius);
    }

    private void updateColorFromTextBox(String text) {
        if(text.matches("^[0-9a-fA-F]{6}$")) {
            Color result = null;
            try {
                result = Color.decode("#" + text.toUpperCase(Locale.ROOT));
            }
            catch(Exception e) {
                //should never happen
                e.printStackTrace();
            }
            if(result != null) this.setSelectedColor(result);
        }
    }

    @Override
    public void setEntryValue(int id, boolean value) {
        //NO-OP
    }

    @Override
    public void setEntryValue(int id, float value) {
        //NO-OP
    }

    @Override
    public void setEntryValue(int id, String value) {
        this.updateColorFromTextBox(value);
    }
}
