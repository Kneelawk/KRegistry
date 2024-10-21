package com.kneelawk.kregistry.core.api;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import com.kneelawk.kregistry.core.api.lazy.Lazy;

public class KRegistry<T> {
    private final String modId;
    private final ResourceKey<T> key;
    private final Map<ResourceLocation, Lazy<? extends T>> stuff = new Object2ObjectLinkedOpenHashMap<>();

    KRegistry(String modId, ResourceKey<T> key) {
        this.modId = modId;
        this.key = key;
    }
}
