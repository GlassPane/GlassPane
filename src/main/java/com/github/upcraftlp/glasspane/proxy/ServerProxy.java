package com.github.upcraftlp.glasspane.proxy;

import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.SERVER)
public class ServerProxy implements IProxy {

    @Override
    public int getSelectedSkin(String skinID, EntityPlayer player) {
        //TODO implement skin getter serverside!
        return 0;
    }
}
