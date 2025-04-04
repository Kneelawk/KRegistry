package com.kneelawk.kregistry.core.api;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

/**
 * Default {@link KHolder} implementation.
 *
 * @param <T> the type this holds.
 */
public class KHolderBase<T> implements KHolder<T> {
    private final ResourceKey<T> key;
    private final Supplier<T> supplier;
    private @Nullable T value = null;
    private @Nullable Holder.Reference<T> holder = null;

    /**
     * Creates a new lazily initialized holder.
     *
     * @param key      the key associated with the value.
     * @param supplier the value supplier.
     */
    public KHolderBase(ResourceKey<T> key, Supplier<T> supplier) {
        this.key = key;
        this.supplier = supplier;
    }

    @Override
    public T get() {
        T value = this.value;
        if (value == null) {
            this.value = value = supplier.get();
        }
        return value;
    }

    @Override
    public boolean isInitialized() {
        return value != null;
    }

    @Override
    public ResourceKey<T> getKey() {
        return key;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable Registry<T> getRegistry() {
        return (Registry<T>) BuiltInRegistries.REGISTRY.get(key.registry()).map(Holder.Reference::value).orElse(null);
    }

    @Override
    public @Nullable Holder.Reference<T> getHolder() {
        Holder.Reference<T> holder = this.holder;
        if (holder == null) {
            Registry<T> registry = getRegistry();
            if (registry == null) return null;

            this.holder = holder = registry.get(key).orElse(null);
        }
        return holder;
    }
}
