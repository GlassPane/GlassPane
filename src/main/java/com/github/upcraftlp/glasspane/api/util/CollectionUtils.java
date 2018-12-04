package com.github.upcraftlp.glasspane.api.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {

    private static final Random RANDOM = new Random();

    /**
     * get a random element from any collection
     * @param collection the collection
     * @param random the {@link Random} instance to use
     * @return an object of type {@link T}, or {@code null} if hte collection has no elements matching the predicate (or is empty)
     */
    public static <T> T getRandomElement(Collection<T> collection, Random random, Predicate<T> predicate) {
        if(!collection.isEmpty()) {
            List<T> filtered = collection.stream().filter(predicate).collect(Collectors.toList());
            if(!filtered.isEmpty()) return filtered.get(random.nextInt(filtered.size()));
        }
        return null;
    }

    /**
     * get a random element from any collection
     * @param collection the collection
     * @return an object of type {@link T}, or {@code null} if hte collection has no elements matching the predicate (or is empty)
     */
    public static <T> T getRandomElement(Collection<T> collection) {
        return getRandomElement(collection, RANDOM, t -> true);
    }
}
