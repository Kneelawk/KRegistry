package com.kneelawk.kregistry.lite.api;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;

import net.minecraft.resources.ResourceLocation;

import com.kneelawk.kregistry.lite.impl.SimpleLiteRegistry;

/**
 * Like a {@link net.minecraft.core.Registry} but does not deal with freezing, tags, or integer ids, and does not need
 * to be registered with another registry.
 * <p>
 * Note: values are checked via reference equality.
 *
 * @param <T> the type this lite registry holds.
 */
public interface LiteRegistry<T> extends Keyable {
    /**
     * {@return this registry's name, much like a vanilla registry's registry key}
     */
    ResourceLocation getName();

    /**
     * Registers a new key and value pair with the registry.
     * <p>
     * Both the key and value must be unique for the registry to function properly.
     *
     * @param key   the key being registered.
     * @param value the value being registered.
     * @param <T2>  th type of value being registered.
     * @return the value being registered.
     */
    <T2 extends T> T2 register(ResourceLocation key, T2 value);

    /**
     * Gets a registered value by its key.
     *
     * @param key the key the value was registered under.
     * @return the value for the given key, or {@code null} if the value was not registered.
     */
    @Nullable T get(ResourceLocation key);

    /**
     * Gets the key for a given value, if that value has been registered.
     *
     * @param value the value to get the key of.
     * @return the key for the given value, or {@code null} if the value was not registered.
     */
    @Nullable ResourceLocation getKey(T value);

    /**
     * {@return a set of all keys in this registry}
     */
    Set<ResourceLocation> keySet();

    /**
     * {@return a set of all entries in this registry}
     */
    Set<Map.Entry<ResourceLocation, T>> entrySet();

    @Override
    default <U> Stream<U> keys(DynamicOps<U> ops) {
        return keySet().stream().map(key -> ops.createString(key.toString()));
    }

    /**
     * {@return a codec for looking up registry values by their keys}
     */
    default Codec<T> byNameCodec() {
        return ResourceLocation.CODEC.flatXmap(name -> {
            T value = get(name);
            if (value == null)
                return DataResult.error(() -> "Registry '" + getName() + "' has no value with name '" + name + "'");
            return DataResult.success(value);
        }, value -> {
            ResourceLocation rl = getKey(value);
            if (rl == null) return DataResult.error(() -> "Registry '" + getName() + "' has no value '" + value + "'");
            return DataResult.success(rl);
        });
    }

    /**
     * Creates a new simple registry.
     * <p>
     * Simple registries have synchronized registration via {@link #register(ResourceLocation, Object)}, meaning
     * that they are thread-safe as long as none of the read methods are called while registration is occurring.
     *
     * @param name the name of the new registry.
     * @param <T>  the type of value the new registry holds.
     * @return the new registry.
     */
    static <T> LiteRegistry<T> simple(ResourceLocation name) {
        return new SimpleLiteRegistry<>(name);
    }
}
