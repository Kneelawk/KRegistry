package com.kneelawk.kregistry.simpleexample.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import com.kneelawk.kregistry.core.api.specialization.KBlockHolder;
import com.kneelawk.kregistry.core.api.specialization.KBlockRegistrar;
import com.kneelawk.kregistry.simpleexample.SimpleExampleMod;

public class SEBlocks {
    private static final KBlockRegistrar BLOCKS = SimpleExampleMod.SET.getBlock();

    public static final KBlockHolder<Block> KATHOS_BRICKS = BLOCKS.registerSimpleWithItem("kathos_bricks",
        BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops().strength(1.5F, 6.0F));

    public static void register() {
    }
}
