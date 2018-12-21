package com.github.upcraftlp.glasspane.api.util.serialization.datareader;

import net.minecraft.nbt.*;

import java.io.*;

public class NBTDataReader implements DataReader<NBTTagCompound> {

    @Override
    public NBTTagCompound readData(InputStream in) throws IOException {
        return CompressedStreamTools.readCompressed(in);
    }
}
