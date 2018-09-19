package com.github.upcraftlp.glasspane.api.skin;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IHasSkin<T> {

    void setSkin(T t, @Nullable ResourceLocation skin);

    @Nullable
    ResourceLocation getSkin(T t, @Nullable World world, @Nullable Entity entity);

    String getSkinID();
}
