package com.kneelawk.kregistry.core.impl.lazy;

import java.util.function.Supplier;

import org.jetbrains.annotations.UnknownNullability;

import com.kneelawk.kregistry.core.api.lazy.Lazy;

public class SimpleLazy<T> implements Lazy<T> {
    private boolean initialized = false;
    private @UnknownNullability T value = null;
    private final Supplier<T> supplier;

    public SimpleLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        T value = this.value;
        if (!initialized) {
            this.value = value = supplier.get();
            initialized = true;
        }
        return value;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
