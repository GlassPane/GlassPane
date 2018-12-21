package com.github.upcraftlp.glasspane.api.structure;

import com.github.upcraftlp.glasspane.api.event.StructurePlaceEvent;
import com.github.upcraftlp.glasspane.api.util.ForgeUtils;
import com.github.upcraftlp.glasspane.api.util.serialization.datareader.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.*;
import net.minecraftforge.common.MinecraftForge;

public class StructureLoaderNBT implements StructureLoader {

    @Override
    public void placeInWorld(ResourceLocation structureIdentifier, World world, BlockPos pos, PlacementSettings settings, boolean centered, int flags) {
        settings.setSeed(world.getSeed());
        settings.setIntegrity(MathHelper.clamp(settings.getIntegrity(), 0.0F, 1.0F));
        Template template = new Template();
        NBTTagCompound nbt = ForgeUtils.readAssetData(structureIdentifier, DataReaders.NBT_COMPRESSED, DataReader.AssetType.DATA);
        template.read(DATA_FIXER.process(FixTypes.STRUCTURE, nbt));
        if(centered) {
            pos = pos.subtract(Template.transformedBlockPos(settings, new BlockPos(template.transformedSize(settings.getRotation()).getX() / 2, template.transformedSize(settings.getRotation()).getY(), template.transformedSize(settings.getRotation()).getZ() / 2)));
        }
        StructurePlaceEvent event = new StructurePlaceEvent(world, pos, centered, structureIdentifier, flags, settings, template);
        if(!MinecraftForge.EVENT_BUS.post(event)) event.getStructureTemplate().addBlocksToWorld(event.getWorld(), event.getPosition(), event.getPlacementSettings(), event.getBlockPlacementFlags());
    }
}
