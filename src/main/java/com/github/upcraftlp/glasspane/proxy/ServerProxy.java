package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.vanity.CrystalBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.SERVER)
public class ServerProxy implements IProxy {

    @Override
    public ResourceLocation getSelectedSkin(String skinID, EntityPlayer player) {
        return CrystalBall.getSelectedFeature(skinID, player);
    }
}
