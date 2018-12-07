package com.github.upcraftlp.glasspane.api.util;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {

    private static final Random RANDOM = new Random();

    /**
     * get a random element from any collection
     *
     * @param collection the collection
     * @return an object of type {@code T}, or {@code null} if the collection has no elements matching the predicate (or is empty)
     */
    @Nullable
    public static <T> T getRandomElement(Collection<T> collection) {
        return getRandomElement(collection, t -> true);
    }

    /**
     * get a random element from any collection
     *
     * @param collection the collection
     * @param predicate  an optional filter to be applied to the collection before getting the random element
     * @return an object of type {@code T}, or {@code null} if the collection has no elements matching the predicate (or is empty)
     */
    @Nullable
    public static <T> T getRandomElement(Collection<T> collection, Predicate<T> predicate) {
        return getRandomElement(collection, RANDOM, predicate);
    }

    /**
     * get a random element from any collection
     *
     * @param collection the collection
     * @param random     the {@link Random} instance to use
     * @param predicate  an optional filter to be applied to the collection before getting the random element
     * @return an object of type {@code T}, or {@code null} if the collection has no elements matching the predicate (or is empty)
     */
    @Nullable
    public static <T> T getRandomElement(Collection<T> collection, Random random, Predicate<T> predicate) {
        if(!collection.isEmpty()) {
            List<T> filtered = collection.stream().filter(predicate).collect(Collectors.toList());
            if(!filtered.isEmpty()) return filtered.get(random.nextInt(filtered.size()));
        }
        return null;
    }
}
