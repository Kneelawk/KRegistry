package com.kneelawk.kregistry.simpleexample.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import com.kneelawk.kregistry.core.api.KHolder;
import com.kneelawk.kregistry.core.api.KRegistrar;
import com.kneelawk.kregistry.simpleexample.SimpleExampleConstants;
import com.kneelawk.kregistry.simpleexample.SimpleExampleMod;
import com.kneelawk.kregistry.simpleexample.block.SEBlocks;

public class SECreativeTabs {
    private static final KRegistrar<CreativeModeTab> TABS = SimpleExampleMod.SET.get(Registries.CREATIVE_MODE_TAB);

    public static final KHolder<CreativeModeTab> TAB = TABS.register("main", () -> CreativeModeTab.builder(null, -1)
        .title(Component.translatable("itemGroup." + SimpleExampleConstants.MOD_ID + ".main"))
        .icon(SEBlocks.KATHOS_BRICKS::asStack)
        .displayItems((itemDisplayParameters, output) -> {
            output.accept(SEBlocks.KATHOS_BRICKS);
        }).build());

    public static void register() {
    }
}
