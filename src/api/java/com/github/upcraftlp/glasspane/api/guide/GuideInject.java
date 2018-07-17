package com.github.upcraftlp.glasspane.api.guide;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * used to inject an instance of {@link IGuideBook} for a mod.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GuideInject {

    /**
     * the name of the guide
     */
    String value();

    /**
     * the mod id of the guide, used for naimg the guide and for reading it from the assets
     */
    String modid() default "minecraft";
}
