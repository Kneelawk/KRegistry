package com.kneelawk.kregistry.lite.impl;

import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectLinkedOpenHashMap;

import net.minecraft.resources.ResourceLocation;

import com.kneelawk.kregistry.lite.api.LiteRegistry;

public class SimpleLiteRegistry<T> implements LiteRegistry<T> {
    private final ResourceLocation name;
    private final Map<ResourceLocation, T> forward = new Object2ReferenceLinkedOpenHashMap<>();
    private final Map<T, ResourceLocation> reverse = new Reference2ObjectLinkedOpenHashMap<>();

    public SimpleLiteRegistry(ResourceLocation name) {this.name = name;}

    @Override
    public ResourceLocation getName() {
        return name;
    }

    @Override
    public synchronized <T2 extends T> T2 register(ResourceLocation key, T2 value) {
        if (forward.containsKey(key))
            throw new IllegalArgumentException("Value already registered with key '" + key + "'");
        if (reverse.containsKey(value))
            throw new IllegalArgumentException("Value already registered '" + value + "'");

        forward.put(key, value);
        reverse.put(value, key);
        return value;
    }

    @Override
    public @Nullable T get(ResourceLocation key) {
        return forward.get(key);
    }

    @Override
    public @Nullable ResourceLocation getKey(T value) {
        return reverse.get(value);
    }

    @Override
    public Set<ResourceLocation> keySet() {
        return forward.keySet();
    }

    @Override
    public Set<Map.Entry<ResourceLocation, T>> entrySet() {
        return forward.entrySet();
    }
}
