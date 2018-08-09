package com.github.upcraftlp.glasspane.api.skin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IHasSkin<T> {

    void setSkin(T t, int skin);

    default void setSkin(T t, boolean hasSkin) {
        setSkin(t, hasSkin ? 1 : 0);
    }

    int getSkin(T t, @Nullable World world, @Nullable Entity entity);

    String getSkinID();
}
