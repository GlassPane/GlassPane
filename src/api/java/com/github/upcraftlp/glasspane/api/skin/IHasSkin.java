package com.github.upcraftlp.glasspane.api.skin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IHasSkin<T> {

    void setSkin(T t, int skin);

    int getSkin(T t, @Nullable World world, @Nullable Entity entity);
}
