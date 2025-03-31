package com.kneelawk.kregistry.simpleexample.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import com.kneelawk.kregistry.simpleexample.SimpleExampleConstants;
import com.kneelawk.kregistry.simpleexample.SimpleExampleMod;

@Mod(SimpleExampleConstants.MOD_ID)
public class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        SimpleExampleMod.init();
    }
}
