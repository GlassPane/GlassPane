package com.github.upcraftlp.glasspane.api.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class StructurePlaceEvent extends Event {

    private final World world;
    private BlockPos pos;
    private boolean centered;
    private ResourceLocation structure;
    private int blockFlags;
    private PlacementSettings settings;
    private Template structureTemplate;

    public StructurePlaceEvent(World world, BlockPos pos, boolean centered, ResourceLocation structure, int flags, PlacementSettings settings, Template template) {
        this.world = world;
        this.pos = pos;
        this.centered = centered;
        this.structure = structure;
        blockFlags = flags;
        this.settings = settings;
        structureTemplate = template;
    }

    public BlockPos getPosition() {
        return pos;
    }

    public void setPosition(BlockPos pos) {
        this.pos = pos;
    }

    public boolean isCenteredPosition() {
        return centered;
    }

    public void setCenteredPosition(boolean centered) {
        this.centered = centered;
    }

    public ResourceLocation getStructureID() {
        return structure;
    }

    public void setStructureID(ResourceLocation structure) {
        this.structure = structure;
    }

    public int getBlockPlacementFlags() {
        return blockFlags;
    }

    public void setBlockPlacementFlags(int blockFlags) {
        this.blockFlags = blockFlags;
    }

    public PlacementSettings getPlacementSettings() {
        return settings;
    }

    public void setPlacementSettings(PlacementSettings settings) {
        this.settings = settings;
    }

    public Template getStructureTemplate() {
        return structureTemplate;
    }

    public void setStructureTemplate(Template structureTemplate) {
        this.structureTemplate = structureTemplate;
    }

    public World getWorld() {
        return world;
    }
}
