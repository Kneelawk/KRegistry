package com.kneelawk.kregistry.core.api.lazy;

import java.util.function.Supplier;

import com.kneelawk.kregistry.core.impl.lazy.AtomicLazy;
import com.kneelawk.kregistry.core.impl.lazy.BlockingLazy;
import com.kneelawk.kregistry.core.impl.lazy.SimpleLazy;

/**
 * Lazily initialized supplier.
 *
 * @param <T> the type this lazy holds.
 */
public interface Lazy<T> extends Supplier<T> {
    /**
     * {@return whether this lazy has been initialized}
     */
    boolean isInitialized();

    /**
     * Creates a new simple lazy that does not have any thread safety.
     *
     * @param supplier the supplier for the lazy data.
     * @param <T>      the type of data held in the lazy.
     * @return the new lazy.
     */
    static <T> Lazy<T> simple(Supplier<T> supplier) {
        return new SimpleLazy<>(supplier);
    }

    /**
     * Creates a new lazy that handles thread safety by an atomic get-and-set operation to initialize its state.
     * <p>
     * Note: This can result in the supplier being invoked multiple times on different threads, but only one of the
     * threads will have its value stored in the lazy, meaning that the result of {@link #get()} will be consistent
     * across all threads.
     *
     * @param supplier the supplier for the lazy data.
     * @param <T>      the type of data held in the lazy.
     * @return the new lazy.
     */
    static <T> Lazy<T> atomic(Supplier<T> supplier) {
        return new AtomicLazy<>(supplier);
    }

    /**
     * Creates a new lazy that handles thread safety using a read-write lock to gate initialization.
     * <p>
     * Note: This lazy may block when {@link #get()} is called if it has not been initialized yet and another thread is
     * trying to initialize it. However, this means that the supplier is only ever called once.
     *
     * @param supplier the supplier for the lazy data.
     * @param <T>      the tye of data held in the lazy.
     * @return the new lazy.
     */
    static <T> Lazy<T> blocking(Supplier<T> supplier) {
        return new BlockingLazy<>(supplier);
    }
}
