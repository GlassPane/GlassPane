package com.github.upcraftlp.glasspane.api.capability;


import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

/**
 * A simple implementation of {@link ICapabilityProvider} and {@link INBTSerializable} that supports a single {@link Capability} handler instance.
 * <p>
 * Uses the {@link Capability}'s {@link IStorage} to serialise/deserialise NBT.
 *
 * @author Choonster
 * @see "https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/e24805590998b80c65d24f7d134472790d92a81c/src/main/java/choonster/testmod3/capability/CapabilityProviderSerializable.java"
 */
public class CapabilityProviderSerializable<HANDLER> extends CapabilityProviderSimple<HANDLER> implements INBTSerializable<NBTBase> {

    /**
     * Create a provider for the default handler instance.
     *
     * @param capability The Capability instance to provide the handler for
     * @param facing     The EnumFacing to provide the handler for
     */
    public CapabilityProviderSerializable(final Capability<HANDLER> capability, @Nullable final EnumFacing facing) {
        this(capability, facing, capability.getDefaultInstance());
    }

    /**
     * Create a provider for the specified handler instance.
     *
     * @param capability The Capability instance to provide the handler for
     * @param facing     The EnumFacing to provide the handler for
     * @param instance   The handler instance to provide
     */
    public CapabilityProviderSerializable(final Capability<HANDLER> capability, @Nullable final EnumFacing facing, @Nullable final HANDLER instance) {
        super(capability, facing, instance);
    }

    @Nullable
    @Override
    public NBTBase serializeNBT() {
        return getCapability().writeNBT(getInstance(), getFacing());
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        getCapability().readNBT(getInstance(), getFacing(), nbt);
    }

}
