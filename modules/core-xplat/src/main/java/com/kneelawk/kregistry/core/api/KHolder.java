package com.kneelawk.kregistry.core.api;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

/**
 * Holds a lazily-initialized value and its associated resource key.
 *
 * @param <T> the type this holder holds.
 */
public class KHolder<T> implements Supplier<T> {
    private final ResourceKey<T> key;
    private final Supplier<T> supplier;
    private @Nullable T value = null;
    private @Nullable Holder<T> holder = null;

    /**
     * Creates a new lazily initialized holder.
     *
     * @param key      the key associated with the value.
     * @param supplier the value supplier.
     */
    KHolder(ResourceKey<T> key, Supplier<T> supplier) {
        this.key = key;
        this.supplier = supplier;
    }

    /**
     * Gets and initializes the value in this holder.
     *
     * @return the value in this holder.
     */
    @Override
    public T get() {
        T value = this.value;
        if (value == null) {
            this.value = value = supplier.get();
        }
        return value;
    }

    /**
     * {@return whether the value in this holder has been lazily initialized yet}
     */
    public boolean isInitialized() {
        return value != null;
    }

    /**
     * {@return the key associated with the value in this holder}
     */
    public ResourceKey<T> getKey() {
        return key;
    }

    /**
     * {@return the registry for the registry key associated with the value in this holder, if the registry key references a valid key}
     */
    @SuppressWarnings("unchecked")
    public @Nullable Registry<T> getRegistry() {
        return (Registry<T>) BuiltInRegistries.REGISTRY.get(key.registry());
    }

    /**
     * {@return the vanilla holder for the value in this holder, if it has been registered}
     */
    public @Nullable Holder<T> getHolder() {
        Holder<T> holder = this.holder;
        if (holder == null) {
            Registry<T> registry = getRegistry();
            if (registry == null) return null;

            this.holder = holder = registry.getHolder(key).orElse(null);
        }
        return holder;
    }
}
