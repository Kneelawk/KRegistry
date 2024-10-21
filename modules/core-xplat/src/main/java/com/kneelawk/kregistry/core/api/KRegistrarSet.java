package com.kneelawk.kregistry.core.api;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceLinkedOpenHashMap;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

import com.kneelawk.kregistry.core.impl.KRLog;

/**
 * Collects registrars that then collect values to register.
 */
public class KRegistrarSet {
    private final Map<ResourceKey<? extends Registry<?>>, KRegistrar<?>> registrars =
        new Reference2ReferenceLinkedOpenHashMap<>();
    private final String modId;

    /**
     * Creates a new registrar set for the given mod id.
     *
     * @param modId the mod id to register values for.
     */
    public KRegistrarSet(String modId) {this.modId = modId;}

    /**
     * Gets or creates the registrar for the given registry.
     *
     * @param key the key of the registry to get the registrar for.
     * @param <T> the type the registrar holds.
     * @return the registrar for the given registry.
     */
    @SuppressWarnings("unchecked")
    public <T> KRegistrar<T> get(ResourceKey<? extends Registry<? super T>> key) {
        return (KRegistrar<T>) registrars.computeIfAbsent(key, k -> new KRegistrar<>(modId, k));
    }

    /**
     * Registers all collected values for the given registry.
     * <p>
     * This should generally be called on a NeoForge backend.
     *
     * @param registry the registry to register values to.
     */
    @SuppressWarnings("unchecked")
    public void register(Registry<?> registry) {
        KRegistrar<?> registrar = registrars.get(registry.key());
        if (registrar == null) return;

        registrar.registerAll((Registry<? super Object>) registry);
    }

    /**
     * Registers all collected values to their associated registries.
     * <p>
     * This should generally be called on a fabric backend.
     */
    @SuppressWarnings("unchecked")
    public void registerAll() {
        for (var entry : registrars.entrySet()) {
            Registry<Object> registry = ((Registry<Registry<Object>>) BuiltInRegistries.REGISTRY).get(
                (ResourceKey<Registry<Object>>) entry.getKey());
            if (registry == null) {
                KRLog.LOG.error("Attempted to register items for {} but there is no registry with that key",
                    entry.getKey());
                continue;
            }
            entry.getValue().registerAll(registry);
        }
    }
}
