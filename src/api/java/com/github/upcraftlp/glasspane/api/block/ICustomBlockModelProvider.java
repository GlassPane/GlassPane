package com.github.upcraftlp.glasspane.api.block;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public interface ICustomBlockModelProvider {

    /**
     * register a model for the block, also register {@link TileEntitySpecialRenderer} here
     */
    void initBlockModel();
}
