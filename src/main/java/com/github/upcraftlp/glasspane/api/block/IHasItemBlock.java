package com.github.upcraftlp.glasspane.api.block;

public interface IHasItemBlock {

    default boolean createItemBlock() {
        return true;
    }
}
