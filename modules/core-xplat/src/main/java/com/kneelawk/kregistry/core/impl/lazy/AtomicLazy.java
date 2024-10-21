package com.kneelawk.kregistry.core.impl.lazy;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import com.kneelawk.kregistry.core.api.lazy.Lazy;

public class AtomicLazy<T> implements Lazy<T> {
    private static final Object NOT_INITIALIZED = new Object();

    private final AtomicReference<Object> holder = new AtomicReference<>(NOT_INITIALIZED);
    private final Supplier<T> supplier;

    public AtomicLazy(Supplier<T> supplier) {this.supplier = supplier;}

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
        final Object value = holder.get();
        if (value == NOT_INITIALIZED) {
            // this section may happen multiple times on different threads
            final T newValue = supplier.get();
            holder.compareAndSet(NOT_INITIALIZED, newValue);
            return (T) holder.get();
        } else {
            return (T) value;
        }
    }

    @Override
    public boolean isInitialized() {
        return holder.get() != NOT_INITIALIZED;
    }
}
