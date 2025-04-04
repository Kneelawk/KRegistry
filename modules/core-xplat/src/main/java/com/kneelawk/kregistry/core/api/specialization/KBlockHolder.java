package com.kneelawk.kregistry.core.api.specialization;

import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import com.kneelawk.kregistry.core.api.KHolder;
import com.kneelawk.kregistry.core.api.KHolderBase;

/**
 * {@link KHolder} implementation specifically for {@link Block}s.
 *
 * @param <T> the type of block this holds.
 */
public class KBlockHolder<T extends Block> extends KHolderBase<T> implements ItemLike {
    private final @Nullable KItemHolder<BlockItem> item;

    /**
     * Creates a new lazily initialized holder.
     *
     * @param key      the key associated with the value.
     * @param supplier the value supplier.
     */
    public KBlockHolder(ResourceKey<T> key, Supplier<T> supplier) {
        this(key, supplier, null);
    }

    /**
     * Creates a new lazily initialized holder.
     *
     * @param key      the key associated with the value.
     * @param supplier the value supplier.
     * @param itemCtor the constructor of the item holder for the block item associated with this holder's block.
     */
    public KBlockHolder(ResourceKey<T> key, Supplier<T> supplier,
                        @Nullable Function<Supplier<T>, KItemHolder<BlockItem>> itemCtor) {
        super(key, supplier);
        if (itemCtor == null) {
            item = null;
        } else {
            item = itemCtor.apply(this);
        }
    }

    @Override
    public Item asItem() {
        if (item != null) {
            return item.asItem();
        }
        return get().asItem();
    }

    /**
     * {@return the item holder for the block item associated with this block, or null if this was not created with one}
     */
    public @Nullable KItemHolder<BlockItem> getItemHolder() {
        return item;
    }

    /**
     * {@return the item holder for the block item associated with this block, or throws if this was not created with one}
     */
    public KItemHolder<BlockItem> getItemHolderOrThrow() {
        if (item == null) {
            throw new IllegalStateException("Block " + getKey() + " has no associated item");
        }
        return item;
    }

    /**
     * {@return an item stack holding the item associated with this block}
     */
    public ItemStack asStack() {
        return new ItemStack(this);
    }
}
