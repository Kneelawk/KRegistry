package com.kneelawk.kregistry.core.api;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceKey;

/**
 * Collects values to be registered in a cross-platform easy-to-use way.
 *
 * @param <T> the type this registrar holds.
 */
public interface KRegistrar<T> {
    /**
     * Registers a lazily initializable value.
     *
     * @param path the path to register the value under.
     * @param ctor the supplier for the value.
     * @param <T2> the type being registered.
     * @return the custom holder for the value.
     */
    default <T2 extends T> KHolder<T2> register(String path, Supplier<T2> ctor) {
        return register(path, key -> ctor.get());
    }

    /**
     * Registers a lazily initializable value with its resource key.
     *
     * @param path the path to register the value under.
     * @param ctor the creator for the value.
     * @param <T2> the type being registered.
     * @return the custom holder for the value.
     */
    @SuppressWarnings("unchecked")
    <T2 extends T> KHolder<T2> register(String path, Function<ResourceKey<T>, T2> ctor);

    /**
     * Registers a lazily initializable value with properties that need to have an id applied to them.
     *
     * @param path            the path to register the value under.
     * @param ctor            the final object's constructor.
     * @param idSetter        the function to set the id on the input properties.
     * @param inputProperties the properties passed to the final object's constructor.
     * @param <T2>            the type being registered.
     * @param <P>             the type of properties.
     * @return the custom holder for the value.
     */
    default <T2 extends T, P> KHolder<T2> register(String path, Function<P, T2> ctor,
                                                   BiConsumer<P, ResourceKey<T>> idSetter, P inputProperties) {
        return register(path, key -> {
            idSetter.accept(inputProperties, key);
            return ctor.apply(inputProperties);
        });
    }
}
