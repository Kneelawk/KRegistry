package com.kneelawk.kregistry.core.api.specialization;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.kneelawk.kregistry.core.api.KCoreRegistrar;
import com.kneelawk.kregistry.core.api.KRegistrar;

/**
 * Specialized {@link KRegistrar} for {@link Block}s.
 */
public class KBlockRegistrar implements KRegistrar<Block> {
    private final KCoreRegistrar<Block> core;
    private final KItemRegistrar blockItems;

    /**
     * Creates a new {@link KBlockRegistrar}.
     *
     * @param core       the core registrar this wraps.
     * @param blockItems the item registrar for block items.
     */
    public KBlockRegistrar(KCoreRegistrar<Block> core, KItemRegistrar blockItems) {
        this.core = core;
        this.blockItems = blockItems;
    }

    @Override
    public <T2 extends Block> KBlockHolder<T2> register(String path, Supplier<T2> ctor) {
        return core.register(path, ctor, (KCoreRegistrar.KHolderFactory<T2, KBlockHolder<T2>>) KBlockHolder::new);
    }

    @Override
    public <T2 extends Block> KBlockHolder<T2> register(String path, Function<ResourceKey<Block>, T2> ctor) {
        return core.register(path, ctor, (KCoreRegistrar.KHolderFactory<T2, KBlockHolder<T2>>) KBlockHolder::new);
    }

    @Override
    public <T2 extends Block, P> KBlockHolder<T2> register(String path, Function<P, T2> ctor,
                                                           BiConsumer<P, ResourceKey<Block>> idSetter,
                                                           P inputProperties) {
        return core.register(path, ctor, idSetter, inputProperties,
            (KCoreRegistrar.KHolderFactory<T2, KBlockHolder<T2>>) KBlockHolder::new);
    }

    /**
     * Registers a lazily initializable block with the given properties.
     *
     * @param path            the path to register the block to.
     * @param ctor            the block's constructor.
     * @param inputProperties the properties passed to the block.
     * @param <T>             the type of block.
     * @return the block holder.
     */
    public <T extends Block> KBlockHolder<T> register(String path, Function<BlockBehaviour.Properties, T> ctor,
                                                      BlockBehaviour.Properties inputProperties) {
        return register(path, ctor, BlockBehaviour.Properties::setId, inputProperties);
    }

    /**
     * Registers a block with the given properties.
     *
     * @param path            the path to register the block to.
     * @param inputProperties the properties passed to the block.
     * @return the block holder.
     */
    public KBlockHolder<Block> registerSimple(String path, BlockBehaviour.Properties inputProperties) {
        return register(path, Block::new, BlockBehaviour.Properties::setId, inputProperties);
    }

    /**
     * Registers a lazily initializable block and its block item.
     *
     * @param path the path to register the block and item to.
     * @param ctor the block's constructor.
     * @param <T2> the type of block.
     * @return the block holder.
     */
    public <T2 extends Block> KBlockHolder<T2> registerWithItem(String path, Function<ResourceKey<Block>, T2> ctor) {
        return core.register(path, ctor,
            (KCoreRegistrar.KHolderFactory<T2, KBlockHolder<T2>>) (key, supplier) -> new KBlockHolder<>(key, supplier,
                memoized -> blockItems.registerBlock(path, memoized)));
    }

    /**
     * Registers a lazily initializable block with the given properties and its block item.
     *
     * @param path            the path to register the block and item to.
     * @param ctor            the block's constructor.
     * @param inputProperties the properties passed to the block.
     * @param <T>             the type of block.
     * @return the block holder.
     */
    public <T extends Block> KBlockHolder<T> registerWithItem(String path, Function<BlockBehaviour.Properties, T> ctor,
                                                              BlockBehaviour.Properties inputProperties) {
        return core.register(path, ctor, BlockBehaviour.Properties::setId, inputProperties,
            (KCoreRegistrar.KHolderFactory<T, KBlockHolder<T>>) (key, supplier) -> new KBlockHolder<>(key, supplier,
                memoized -> blockItems.registerBlock(path, memoized)));
    }

    /**
     * Registers a block with the given properties and its item.
     *
     * @param path            the path to register the block and item to.
     * @param inputProperties the properties passed to the block.
     * @return the block holder.
     */
    public KBlockHolder<Block> registerSimpleWithItem(String path, BlockBehaviour.Properties inputProperties) {
        return core.register(path, Block::new, BlockBehaviour.Properties::setId, inputProperties,
            (KCoreRegistrar.KHolderFactory<Block, KBlockHolder<Block>>) (key, supplier) -> new KBlockHolder<>(key,
                supplier,
                memoized -> blockItems.registerBlock(path, memoized)));
    }
}
