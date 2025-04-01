package com.kneelawk.kregistry.simpleexample.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import com.kneelawk.kregistry.core.api.KHolder;
import com.kneelawk.kregistry.core.api.KRegistrar;
import com.kneelawk.kregistry.simpleexample.SimpleExampleMod;

public class SEBlocks {
    private static final KRegistrar<Block> BLOCKS = SimpleExampleMod.SET.get(Registries.BLOCK);
    private static final KRegistrar<Item> ITEMS = SimpleExampleMod.SET.get(Registries.ITEM);

    public static final KHolder<Block> KATHOS_BRICKS =
        BLOCKS.register("kathos_bricks", Block::new, BlockBehaviour.Properties::setId,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops().strength(1.5F, 6.0F));
    public static final KHolder<Item> KATHOS_BRICKS_ITEM =
        ITEMS.register("kathos_bricks", properties -> new BlockItem(KATHOS_BRICKS.get(), properties),
            Item.Properties::setId, new Item.Properties());

    public static void register() {
    }
}
