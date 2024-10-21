package com.kneelawk.kregistry.core.api;

import java.util.Map;
import java.util.function.Supplier;

import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Collects values to be registered in a cross-platform easy-to-use way.
 *
 * @param <T> the type this registrar holds.
 */
public class KRegistrar<T> {
    private final String modId;
    private final ResourceKey<? extends Registry<? extends T>> key;
    private final Map<ResourceLocation, KHolder<? extends T>> stuff = new Object2ReferenceLinkedOpenHashMap<>();

    KRegistrar(String modId, ResourceKey<? extends Registry<? extends T>> key) {
        this.modId = modId;
        this.key = key;
    }

    /**
     * Registers a lazily initializable value.
     *
     * @param path the path to register the value under.
     * @param ctor the supplier for the value.
     * @param <T2> the type being registered.
     * @return the custom holder for the value.
     */
    @SuppressWarnings("unchecked")
    public <T2 extends T> KHolder<T2> register(String path, Supplier<T2> ctor) {
        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(modId, path);
        if (stuff.containsKey(name)) throw new IllegalArgumentException("Tried to register " + name + " twice!");

        KHolder<T2> holder = new KHolder<>(ResourceKey.create((ResourceKey<? extends Registry<T2>>) key, name), ctor);
        stuff.put(name, holder);

        return holder;
    }

    /**
     * Registers all values that have been registered to this registrar to the given vanilla registry.
     *
     * @param registry the registry to register all this registrar's values to.
     */
    public void registerAll(Registry<? super T> registry) {
        if (key != registry.key()) return;

        for (var entry : stuff.entrySet()) {
            Registry.register(registry, entry.getKey(), entry.getValue().get());
        }
    }
}
