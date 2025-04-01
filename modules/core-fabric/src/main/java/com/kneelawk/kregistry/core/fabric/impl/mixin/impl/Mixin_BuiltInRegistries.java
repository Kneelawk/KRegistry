package com.kneelawk.kregistry.core.fabric.impl.mixin.impl;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import com.kneelawk.kregistry.core.fabric.impl.FabricMod;
import com.kneelawk.kregistry.core.fabric.impl.mixin.api.Duck_MappedRegistry;
import com.kneelawk.kregistry.core.impl.KRLog;

@Mixin(BuiltInRegistries.class)
public class Mixin_BuiltInRegistries {
    @Shadow
    @Final
    public static Registry<? extends Registry<?>> REGISTRY;

    @Inject(method = "freeze", at = @At("HEAD"))
    private static void onFreeze(CallbackInfo ci) {
        if ((REGISTRY instanceof Duck_MappedRegistry duck) && duck.kregistry_core_isFrozen()) {
            KRLog.LOG.warn("BuiltInRegistries.freeze() called after registries were already frozen",
                new RuntimeException("Stack Trace"));
            return;
        }

        FabricMod.init();
    }
}
