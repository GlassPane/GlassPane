package com.github.upcraftlp.glasspane.util.debug;

import com.github.upcraftlp.glasspane.GlassPane;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.*;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraftforge.common.util.Constants;

import java.io.*;
import java.util.IllegalFormatFlagsException;

@SuppressWarnings({"WeakerAccess", "deprecation", "unused"})
public class SchematicStructureConverter {

    //Schematic file format
    private static final String SCHEMATIC_WIDTH = "Width";
    private static final String SCHEMATIC_HEIGHT = "Height";
    private static final String SCHEMATIC_LENGTH = "Length";
    private static final String SCHEMATIC_BLOCKS = "Blocks";
    private static final String SCHEMATIC_DATA = "Data";
    private static final String SCHEMATIC_TILE_ENTITIES = "TileEntities";
    private static final String SCHEMATIC_ENTITIES = "Entities";
    private static final String SCHEMATIC_KEY_MATERIALS = "Materials";
    private static final String SCHEMATIC_ALPHA_FORMAT = "Alpha";

    //NBT file format
    private static final String KEY_POS_X = "x";
    private static final String KEY_POS_Y = "y";
    private static final String KEY_POS_Z = "z";
    private static final String KEY_POS = "pos";
    private static final String KEY_BLOCKSTATE = "state";
    private static final String KEY_TAG_COMPOUND = "nbt";
    private static final String KEY_PALETTE = "palette";
    private static final String KEY_BLOCKS = "blocks";
    private static final String KEY_ENTITIES = "entities";
    private static final String KEY_SIZE = "size";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DATA_VERSION = "DataVersion";
    private static final int DATA_VERSION = 1343; //change this when vanilla changes, see DataFixer#L27

    public static void main(String[] args) {
        for(String s : args) {
            File f = new File(s);
            if(f.exists() && !f.isDirectory()) convertFiletoVanillaFormat(f);
        }
        System.out.printf("converted %s files.", args.length);
        System.out.println();
    }

    /**
     * convert a schematic file to NBT format (output will be in a new file)
     *
     * @param schematicFile the file to convert
     */
    public static void convertFiletoVanillaFormat(File schematicFile) {
        String fileName = schematicFile.getName();
        File nbtFile = new File(schematicFile.getParentFile(), fileName.substring(0, fileName.lastIndexOf('.')) + ".nbt");
        try(InputStream is = new FileInputStream(schematicFile); OutputStream out = new BufferedOutputStream(new FileOutputStream(nbtFile))) {
            NBTTagCompound schematic = CompressedStreamTools.readCompressed(is);
            NBTTagCompound nbt = toVanillaFormat(schematic);
            CompressedStreamTools.writeCompressed(nbt, out);
            GlassPane.getDebugLogger().info("successfully converted {} to NBT", schematicFile.getName());
        }
        catch (IOException e) {
            GlassPane.getDebugLogger().error("unable to convert to NBT format", e);
        }
    }

    /**
     * convert an {@link NBTTagCompound} in schematic file format to vanilla's structureFormat
     *
     * @param schematic the structure data in schematic format
     * @return the structure data in vanilla's format
     */
    @SuppressWarnings("Duplicates")
    public static NBTTagCompound toVanillaFormat(NBTTagCompound schematic) {
        String format = schematic.getString(SCHEMATIC_KEY_MATERIALS);
        if(!format.equals(SCHEMATIC_ALPHA_FORMAT)) {
            throw new IllegalFormatFlagsException("schematic file is not in \"Alpha\" format!");
        }
        short width = schematic.getShort(SCHEMATIC_WIDTH);
        short height = schematic.getShort(SCHEMATIC_HEIGHT);
        short length = schematic.getShort(SCHEMATIC_LENGTH);
        byte[] blocks = schematic.getByteArray(SCHEMATIC_BLOCKS);
        byte[] data = schematic.getByteArray(SCHEMATIC_DATA);
        NBTTagList entities = schematic.getTagList(SCHEMATIC_ENTITIES, Constants.NBT.TAG_LIST);
        NBTTagList tileEntities = schematic.getTagList(SCHEMATIC_TILE_ENTITIES, Constants.NBT.TAG_LIST);

        //convert everything to a vanilla template
        NBTTagCompound templateCompound = new NBTTagCompound();
        NBTTagList blockTagList = new NBTTagList();
        BasicPalette basicPalette = new BasicPalette();

        NBTTagCompound[][][] tileEntityTagArray = new NBTTagCompound[height][length][width];
        for(int i = 0; i < tileEntities.tagCount(); i++) {
            NBTTagCompound te = tileEntities.getCompoundTagAt(i);
            tileEntityTagArray[te.getInteger(KEY_POS_Y)][te.getInteger(KEY_POS_Z)][te.getInteger(KEY_POS_X)] = te;
        }
        for(int y = 0; y < height; y++) {
            for(int z = 0; z < length; z++) {
                for(int x = 0; x < width; x++) {

                    NBTTagCompound blockNBT = new NBTTagCompound();
                    NBTTagList posList = new NBTTagList();
                    posList.appendTag(new NBTTagInt(x));
                    posList.appendTag(new NBTTagInt(y));
                    posList.appendTag(new NBTTagInt(z));
                    blockNBT.setTag(KEY_POS, posList);

                    int index = (y * length + z) * width + x;
                    Block block = Block.getBlockById(blocks[index]);

                    blockNBT.setInteger(KEY_BLOCKSTATE, basicPalette.idFor(block.getStateFromMeta(data[index])));
                    NBTTagCompound te = tileEntityTagArray[y][z][x];
                    if(te != null) blockNBT.setTag(KEY_TAG_COMPOUND, te);
                    blockTagList.appendTag(blockNBT);
                }
            }
        }
        NBTTagList paletteTag = new NBTTagList();
        for(IBlockState blockState : basicPalette.ids) {
            paletteTag.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), blockState));
        }
        NBTTagList sizeTag = new NBTTagList();
        sizeTag.appendTag(new NBTTagInt(width));
        sizeTag.appendTag(new NBTTagInt(height));
        sizeTag.appendTag(new NBTTagInt(length));

        //save everything to the tag
        templateCompound.setTag(KEY_PALETTE, paletteTag);
        templateCompound.setTag(KEY_BLOCKS, blockTagList);
        templateCompound.setTag(KEY_ENTITIES, entities);
        templateCompound.setTag(KEY_SIZE, sizeTag);
        templateCompound.setString(KEY_AUTHOR, "Structure Converter by UpcraftLP");
        templateCompound.setInteger(KEY_DATA_VERSION, DATA_VERSION);
        net.minecraftforge.fml.common.FMLCommonHandler.instance().getDataFixer().writeVersionData(templateCompound);

        return templateCompound;
    }

    /**
     * helper class, see net.minecraft.world.gen.structure.template.Template$BasicPalette
     */
    private static class BasicPalette {
        final ObjectIntIdentityMap<IBlockState> ids = new ObjectIntIdentityMap<>(16);
        private int lastId;

        int idFor(IBlockState state) {
            int i = this.ids.get(state);

            if(i == -1) {
                i = this.lastId++;
                this.ids.put(state, i);
            }

            return i;
        }
    }
}
