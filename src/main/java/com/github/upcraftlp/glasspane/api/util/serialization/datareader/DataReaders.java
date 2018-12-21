package com.github.upcraftlp.glasspane.api.util.serialization.datareader;

import net.minecraft.nbt.*;
import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;

public class DataReaders {

    public static final DataReader<String> TEXT = new TextDataReader();
    public static final DataReader<String> STRING = in -> IOUtils.toString(in, StandardCharsets.UTF_8);
    public static final DataReader<NBTTagCompound> NBT_RAW = (in) -> CompressedStreamTools.read(new DataInputStream(in));
    public static final DataReader<NBTTagCompound> NBT_COMPRESSED = CompressedStreamTools::readCompressed;
}
