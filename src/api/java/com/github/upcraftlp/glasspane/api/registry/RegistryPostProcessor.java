package com.github.upcraftlp.glasspane.api.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public abstract class RegistryPostProcessor<T extends IForgeRegistryEntry<T>> {

    private static final List<RegistryPostProcessor> REGISTERED_PARSERS = new ArrayList<>();

    /**
     * @return whether or not to process entries of the specified type
     */
    public <V extends IForgeRegistryEntry<V>> boolean shouldProcess(Class<V> type) {
        return type == this.getType();
    }

    /**
     * @return the type of this post processor, to match the registry supertype
     */
    public abstract Class<T> getType();

    public static void registerPostProcessor(RegistryPostProcessor postProcessor) {
        REGISTERED_PARSERS.add(postProcessor);
    }

    public static <T extends IForgeRegistryEntry<T>> List<RegistryPostProcessor> getPostProcessors(Class<T> type) {
        return REGISTERED_PARSERS.stream().filter(entry -> entry.shouldProcess(type)).collect(Collectors.toList());
    }

    /**
     * @param t the entry to be processed
     * @param side the *physical* side we're loading on
     */
    public abstract void process(T t, IForgeRegistry<T> registry, Side side);
}
