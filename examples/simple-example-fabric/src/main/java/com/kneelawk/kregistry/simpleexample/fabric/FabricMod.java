package com.kneelawk.kregistry.simpleexample.fabric;

import net.fabricmc.api.ModInitializer;

import com.kneelawk.kregistry.simpleexample.SimpleExampleMod;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        SimpleExampleMod.init();
    }
}
