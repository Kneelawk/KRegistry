package com.kneelawk.kregistry.simpleexample;

import com.kneelawk.commonevents.api.Listen;
import com.kneelawk.commonevents.api.Scan;
import com.kneelawk.kregistry.core.api.KRegistrarSet;
import com.kneelawk.kregistry.core.api.RegisterCallback;
import com.kneelawk.kregistry.simpleexample.block.SEBlocks;

@Scan
public class SimpleExampleMod {
    public static final KRegistrarSet SET = new KRegistrarSet(SimpleExampleConstants.MOD_ID);

    public static void init() {
    }

    @Listen(RegisterCallback.class)
    public static void register(RegisterCallback.Context ctx) {
        SEBlocks.register();
        ctx.register(SET);
    }
}
