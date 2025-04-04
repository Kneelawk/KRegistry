package com.kneelawk.kregistry.core.api.specialization;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.kneelawk.kregistry.core.api.KCoreRegistrar;
import com.kneelawk.kregistry.core.api.KRegistrar;

/**
 * Specialized {@link KRegistrar} for {@link Item}s.
 */
public class KItemRegistrar implements KRegistrar<Item> {
    private final KCoreRegistrar<Item> core;

    /**
     * Creates a new {@link KItemRegistrar}.
     *
     * @param core the core registrar this wraps.
     */
    public KItemRegistrar(KCoreRegistrar<Item> core) {this.core = core;}

    @Override
    public <T2 extends Item> KItemHolder<T2> register(String path, Supplier<T2> ctor) {
        return core.register(path, ctor, (KCoreRegistrar.KHolderFactory<T2, KItemHolder<T2>>) KItemHolder::new);
    }

    @Override
    public <T2 extends Item> KItemHolder<T2> register(String path, Function<ResourceKey<Item>, T2> ctor) {
        return core.register(path, ctor, (KCoreRegistrar.KHolderFactory<T2, KItemHolder<T2>>) KItemHolder::new);
    }

    @Override
    public <T2 extends Item, P> KItemHolder<T2> register(String path, Function<P, T2> ctor,
                                                         BiConsumer<P, ResourceKey<Item>> idSetter, P inputProperties) {
        return core.register(path, ctor, idSetter, inputProperties,
            (KCoreRegistrar.KHolderFactory<T2, KItemHolder<T2>>) KItemHolder::new);
    }

    /**
     * Registers a lazily initializable item with the given properties.
     *
     * @param path            the path to register the item to.
     * @param ctor            the item's constructor.
     * @param inputProperties the properties passed to the item.
     * @param <T>             the type of item being registered.
     * @return the item holder.
     */
    public <T extends Item> KItemHolder<T> register(String path, Function<Item.Properties, T> ctor,
                                                    Item.Properties inputProperties) {
        return register(path, ctor, Item.Properties::setId, inputProperties);
    }

    /**
     * Registers an item with the given properties.
     *
     * @param path            the path to register the item to.
     * @param inputProperties the properties passed to the item.
     * @return the item holder.
     */
    public KItemHolder<Item> registerSimple(String path, Item.Properties inputProperties) {
        return register(path, Item::new, inputProperties);
    }

    /**
     * Registers an item with the default item properties.
     *
     * @param path the path to register the item to.
     * @return the item holder.
     */
    public KItemHolder<Item> registerSimple(String path) {
        return registerSimple(path, new Item.Properties());
    }

    /**
     * Registers a block item with the given block and properties.
     *
     * @param path           the path to register the block item to.
     * @param block          the block for the block item.
     * @param itemCtor       the block item constructor.
     * @param itemProperties the properties passed to the block item.
     * @param <T>            the type of block item.
     * @return the block item holder.
     */
    public <T extends BlockItem> KItemHolder<T> registerBlock(String path, Supplier<? extends Block> block,
                                                              BiFunction<Block, Item.Properties, T> itemCtor,
                                                              Item.Properties itemProperties) {
        return register(path, props -> itemCtor.apply(block.get(), props), itemProperties.useBlockDescriptionPrefix());
    }

    /**
     * Registers a block item with the given block.
     *
     * @param path     the path to register the block item to.
     * @param block    the block for the block item.
     * @param itemCtor the block item constructor.
     * @param <T>      the type of block item.
     * @return the block item holder.
     */
    public <T extends BlockItem> KItemHolder<T> registerBlock(String path, Supplier<? extends Block> block,
                                                              BiFunction<Block, Item.Properties, T> itemCtor) {
        return registerBlock(path, block, itemCtor, new Item.Properties());
    }

    /**
     * Registers a block item with the given block and properties.
     *
     * @param path           the path to register the block item to.
     * @param block          the block for the block item.
     * @param itemProperties the properties passed to the block item.
     * @return the block item holder.
     */
    public KItemHolder<BlockItem> registerBlock(String path, Supplier<? extends Block> block,
                                                Item.Properties itemProperties) {
        return registerBlock(path, block, BlockItem::new, itemProperties);
    }

    /**
     * Registers a block item with the given block.
     *
     * @param path  the path to register the block item to.
     * @param block the block for the block item.
     * @return the block item holder.
     */
    public KItemHolder<BlockItem> registerBlock(String path, Supplier<? extends Block> block) {
        return registerBlock(path, block, new Item.Properties());
    }
}
