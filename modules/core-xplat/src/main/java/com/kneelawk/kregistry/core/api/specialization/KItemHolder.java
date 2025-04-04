package com.kneelawk.kregistry.core.api.specialization;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import com.kneelawk.kregistry.core.api.KHolder;
import com.kneelawk.kregistry.core.api.KHolderBase;

/**
 * {@link KHolder} implementation specifically for {@link Item}s.
 *
 * @param <T> the type of item this holds.
 */
public class KItemHolder<T extends Item> extends KHolderBase<T> implements ItemLike {
    /**
     * Creates a new lazily initialized holder.
     *
     * @param key      the key associated with the value.
     * @param supplier the value supplier.
     */
    public KItemHolder(ResourceKey<T> key, Supplier<T> supplier) {
        super(key, supplier);
    }

    @Override
    public Item asItem() {
        return get().asItem();
    }

    /**
     * {@return an item stack containing this holder's item}
     */
    public ItemStack asStack() {
        return new ItemStack(this);
    }
}
