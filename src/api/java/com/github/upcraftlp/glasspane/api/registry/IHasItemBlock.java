package com.github.upcraftlp.glasspane.api.registry;

public interface IHasItemBlock {

    default boolean createItemBlock() {
        return true;
    }
}
