package com.github.upcraftlp.glasspane.client.render.layer;

import com.github.upcraftlp.glasspane.GlassPane;
import com.github.upcraftlp.glasspane.api.skin.IHasSkin;
import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LayerCapeCustom extends LayerCape implements IHasSkin<EntityPlayer> {

    public static final ResourceLocation GLASSPANE_CAPE_0 = new ResourceLocation("glasspane_cape:cape_0");
    public static final ResourceLocation GLASSPANE_CAPE_1 = new ResourceLocation("glasspane_cape:cape_1");

    protected RenderPlayer playerRenderer;

    public LayerCapeCustom(RenderPlayer playerRendererIn) {
        super(playerRendererIn);
        this.playerRenderer = playerRendererIn;
    }

    protected boolean shouldRender(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        return !player.isInvisible() && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA && player.isWearing(EnumPlayerModelParts.CAPE) && (player.hasPlayerInfo() || CrystalBall.canUseFeature(player.getUniqueID(), this.getSkinID())) && this.getCapeTexture(player) != null;
    }

    protected ResourceLocation getCapeTexture(AbstractClientPlayer player) {
        int skin = this.getSkin(player, player.world, player);
        if(skin > 0) return new ResourceLocation(GlassPane.MODID, "textures/capes/cape_" + (skin - 1) + ".png");
        return player.getLocationCape();
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (this.shouldRender(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale))
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.playerRenderer.bindTexture(this.getCapeTexture(player));
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double)partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks);
            double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double)partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks);
            double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double)partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks);
            float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
            double d3 = (double) MathHelper.sin(f * 0.017453292F);
            double d4 = (double)(-MathHelper.cos(f * 0.017453292F));
            float f1 = (float)d1 * 10.0F;
            f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;

            if (f2 < 0.0F)
            {
                f2 = 0.0F;
            }

            float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
            f1 = f1 + MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

            if (player.isSneaking())
            {
                f1 += 25.0F;
            }

            GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerRenderer.getMainModel().renderCape(0.0625F);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void setSkin(EntityPlayer player, int skin) {
        //NO-OP
    }

    @Override
    public int getSkin(EntityPlayer player, @Nullable World world, @Nullable Entity entity) {
        return GlassPane.proxy.getSelectedSkin(this.getSkinID(), player);
    }

    @Override
    public String getSkinID() {
        return "glasspane_cape";
    }
}
