package com.kneelawk.kregistry.simpleexample;

import net.minecraft.resources.ResourceLocation;

public class SimpleExampleConstants {
    public static final String MOD_ID = "kregistry_simple_example";
    
    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
