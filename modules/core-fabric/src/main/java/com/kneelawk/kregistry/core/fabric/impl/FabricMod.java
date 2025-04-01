package com.kneelawk.kregistry.core.fabric.impl;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import com.kneelawk.kregistry.core.api.KRegistrarSet;
import com.kneelawk.kregistry.core.api.RegisterCallback;

public class FabricMod {
    public static void init() {
        ObjectArrayList<KRegistrarSet> sets = new ObjectArrayList<>();
        RegisterCallback.EVENT.invoker().register(sets::add);

        for (KRegistrarSet set : sets) {
            set.apply();
        }
    }
}
