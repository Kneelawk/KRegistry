package com.kneelawk.kregistry.core.api;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Holds a lazily-initialized value and its associated resource key.
 * <p>
 * Unlike {@code DeferredHolder}s, these <em>will</em> initialize early if {@link #get()} is called early.
 * <p>
 * Note: {@link #get()} is called by {@link KCoreRegistrar}s when registering their contents, so there is no need to
 * manually make sure each holder has been lazily initialized.
 *
 * @param <T> the type this holder holds.
 */
public interface KHolder<T> extends Supplier<T> {
    /**
     * Gets and initializes the value in this holder.
     *
     * @return the value in this holder.
     */
    @Override
    T get();

    /**
     * {@return whether the value in this holder has been lazily initialized yet}
     */
    boolean isInitialized();

    /**
     * {@return the key associated with the value in this holder}
     */
    ResourceKey<T> getKey();

    /**
     * {@return the registry for the registry key associated with the value in this holder, if the resource key references a valid registry}
     */
    @SuppressWarnings("unchecked")
    @Nullable Registry<T> getRegistry();

    /**
     * {@return the vanilla holder for the value in this holder, if it has been registered, or null if it has not}
     */
    @Nullable Holder.Reference<T> getHolder();

    /**
     * {@return the vanilla holder for the value in this holder, if it has been registered, or throws if it has not}
     */
    default @NotNull Holder.Reference<T> getHolderOrThrow() {
        Holder.Reference<T> ref = getHolder();
        if (ref == null) {
            throw new IllegalStateException("Holder " + getKey() + " has not been registered!");
        }
        return ref;
    }
}
