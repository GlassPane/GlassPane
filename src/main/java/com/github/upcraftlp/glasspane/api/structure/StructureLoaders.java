package com.github.upcraftlp.glasspane.api.structure;

import com.github.upcraftlp.glasspane.util.debug.SchematicStructureConverter;

/**
 * load structures into the world
 *
 * <b>Schematic files not supported!</b>
 * @see SchematicStructureConverter
 */
public class StructureLoaders {

    public static final StructureLoader VANILLA_NBT = new StructureLoaderNBT();
}
