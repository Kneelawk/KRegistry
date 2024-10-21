package com.kneelawk.kregistry.core.impl.lazy;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import org.jetbrains.annotations.UnknownNullability;

import com.kneelawk.kregistry.core.api.lazy.Lazy;

public class BlockingLazy<T> implements Lazy<T> {
    private volatile boolean initialized = false;
    private volatile @UnknownNullability T value = null;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Supplier<T> supplier;

    public BlockingLazy(Supplier<T> supplier) {this.supplier = supplier;}

    @Override
    public T get() {
        lock.readLock().lock();
        try {
            if (initialized) {
                return value;
            }
        } finally {
            lock.readLock().unlock();
        }

        lock.writeLock().lock();
        try {
            if (initialized) {
                return value;
            } else {
                final T value = this.value = supplier.get();
                initialized = true;
                return value;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean isInitialized() {
        lock.readLock().lock();
        try {
            return initialized;
        } finally {
            lock.readLock().unlock();
        }
    }
}
