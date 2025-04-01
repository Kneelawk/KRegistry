package com.kneelawk.kregistry.core.fabric.impl.mixin.impl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.MappedRegistry;

import com.kneelawk.kregistry.core.fabric.impl.mixin.api.Duck_MappedRegistry;

@Mixin(MappedRegistry.class)
public class Mixin_MappedRegistry implements Duck_MappedRegistry {
    @Shadow
    private boolean frozen;

    @Override
    public boolean kregistry_core_isFrozen() {
        return frozen;
    }
}
