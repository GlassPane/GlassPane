package com.github.upcraftlp.glasspane.api.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.*;
import net.minecraft.util.datafix.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

public interface StructureLoader {

    DataFixer DATA_FIXER = DataFixesManager.createFixer();

    /**
     * @param structureIdentifier an identifier for the structure loader to find the structure
     * @param world the world to load the structure in
     * @param pos the position to load the structure at
     * @param settings the placement settings
     * @param centered if true, the {@param pos} is considered the center position, rather than a corner. <b>(X/Z) ONLY</b>
     * @param flags flags to pass to {@link World#setBlockState(BlockPos, IBlockState, int)}
     */
    void placeInWorld(ResourceLocation structureIdentifier, World world, BlockPos pos, PlacementSettings settings, boolean centered, int flags);

    /**
     * @param structureIdentifier an identifier for the structure loader to find the structure
     * @param world the world to load the structure in
     * @param pos the position to load the structure at
     * @param settings the placement settings
     * @param centered if true, the {@param pos} is considered the center position, rather than a corner. <b>(X/Z) ONLY</b>
     */
    default void placeInWorld(ResourceLocation structureIdentifier, World world, BlockPos pos, PlacementSettings settings, boolean centered) {
        placeInWorld(structureIdentifier, world, pos, settings, centered, 2);
    }

}
