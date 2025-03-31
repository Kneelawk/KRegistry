package com.kneelawk.kregistry.simpleexample.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import com.kneelawk.kregistry.core.api.KHolder;
import com.kneelawk.kregistry.core.api.KRegistrar;
import com.kneelawk.kregistry.simpleexample.SimpleExampleMod;

import static com.kneelawk.kregistry.simpleexample.SimpleExampleConstants.rl;

public class SEBlocks {
    private static final KRegistrar<Block> BLOCKS = SimpleExampleMod.SET.get(Registries.BLOCK);
    private static final KRegistrar<Item> ITEMS = SimpleExampleMod.SET.get(Registries.ITEM);

    public static final KHolder<Block> KATHOS_BRICKS = BLOCKS.register("kathos_bricks", () -> new Block(
        BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, rl("kathos_bricks")))
            .mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F)));
    public static final KHolder<Item> KATHOS_BRICKS_ITEM = ITEMS.register("kathos_bricks",
        () -> new BlockItem(KATHOS_BRICKS.get(),
            new Item.Properties().setId(ResourceKey.create(Registries.ITEM, rl("kathos_bricks")))));

    public static void register() {
    }
}
