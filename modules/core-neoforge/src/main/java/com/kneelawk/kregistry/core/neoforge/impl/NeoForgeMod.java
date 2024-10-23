package com.kneelawk.kregistry.core.neoforge.impl;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import com.kneelawk.kregistry.core.api.KRegistrarSet;
import com.kneelawk.kregistry.core.api.RegisterCallback;
import com.kneelawk.kregistry.core.impl.KRConstants;

@Mod(KRConstants.MOD_ID)
public class NeoForgeMod {
    private boolean initialized = false;
    private final ObjectArrayList<KRegistrarSet> sets = new ObjectArrayList<>();

    public NeoForgeMod(IEventBus modBus) {
        modBus.addListener(this::onRegister);
    }

    private void ensureInitialized() {
        if (!initialized) {
            initialized = true;

            RegisterCallback.EVENT.invoker().register(sets::add);
        }
    }

    private void onRegister(RegisterEvent event) {
        ensureInitialized();

        for (KRegistrarSet set : sets) {
            set.register(event.getRegistry());
        }
    }
}
