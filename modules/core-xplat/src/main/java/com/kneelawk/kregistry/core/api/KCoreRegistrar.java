package com.kneelawk.kregistry.core.api;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Core {@link KRegistrar} implementation.
 * <p>
 * Other {@link KRegistrar} implementations will wrap this one.
 *
 * @param <T> the type this registrar holds.
 */
public final class KCoreRegistrar<T> implements KRegistrar<T> {
    private final String modId;
    private final ResourceKey<Registry<T>> key;
    private final Map<ResourceLocation, KHolder<? extends T>> stuff = new Object2ReferenceLinkedOpenHashMap<>();

    KCoreRegistrar(String modId, ResourceKey<Registry<T>> key) {
        this.modId = modId;
        this.key = key;
    }

    /**
     * {@return the mod id of the mod this registrar is associated with}
     */
    public String getModId() {
        return modId;
    }

    /**
     * {@return the key of the registry this registrar is associated with}
     */
    public ResourceKey<Registry<T>> getKey() {
        return key;
    }

    /**
     * Creates a key for the given element to be registered.
     *
     * @param path the path that the element will be registered at.
     * @param <T2> the type of the element.
     * @return the resource key for the given element.
     */
    @SuppressWarnings("unchecked")
    public <T2 extends T> ResourceKey<T2> getKeyFor(String path) {
        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(modId, path);
        return (ResourceKey<T2>) ResourceKey.create(key, name);
    }

    @Override
    public <T2 extends T> KHolder<T2> register(String path, Function<ResourceKey<T>, T2> ctor) {
        return register(path, ctor, (KHolderFactory<T2, KHolder<T2>>) KHolderBase::new);
    }

    /**
     * Registers a lazily initializable value.
     *
     * @param path          the path to register the value under.
     * @param ctor          the supplier for the value.
     * @param <T2>          the type being registered.
     * @param <H>           the type of holder.
     * @param holderFactory the constructor for the holder implementation.
     * @return the custom holder for the value.
     */
    public <T2 extends T, H extends KHolder<T2>> H register(String path, Supplier<T2> ctor,
                                                            KHolderFactory<T2, H> holderFactory) {
        return register(path, key -> ctor.get(), holderFactory);
    }

    /**
     * Registers a lazily initializable value with its resource key.
     *
     * @param path          the path to register the value under.
     * @param ctor          the creator for the value.
     * @param <T2>          the type being registered.
     * @param <H>           the type of holder.
     * @param holderFactory the constructor for the holder implementation.
     * @return the custom holder for the value.
     */
    @SuppressWarnings("unchecked")
    public <T2 extends T, H extends KHolder<T2>> H register(String path, Function<ResourceKey<T>, T2> ctor,
                                                            KHolderFactory<T2, H> holderFactory) {
        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(modId, path);
        if (stuff.containsKey(name)) throw new IllegalArgumentException("Tried to register " + name + " twice!");

        ResourceKey<T> resourceKey = ResourceKey.create(key, name);
        H holder = holderFactory.create((ResourceKey<T2>) resourceKey, () -> ctor.apply(resourceKey));
        stuff.put(name, holder);

        return holder;
    }

    /**
     * Registers a lazily initializable value with properties that need to have an id applied to them.
     *
     * @param path            the path to register the value under.
     * @param ctor            the final object's constructor.
     * @param idSetter        the function to set the id on the input properties.
     * @param inputProperties the properties passed to the final object's constructor.
     * @param <T2>            the type being registered.
     * @param <P>             the type of properties.
     * @param <H>             the type of holder.
     * @param holderFactory   the constructor for the holder implementation.
     * @return the custom holder for the value.
     */
    public <T2 extends T, P, H extends KHolder<T2>> H register(String path, Function<P, T2> ctor,
                                                               BiConsumer<P, ResourceKey<T>> idSetter,
                                                               P inputProperties,
                                                               KHolderFactory<T2, H> holderFactory) {
        return register(path, key -> {
            idSetter.accept(inputProperties, key);
            return ctor.apply(inputProperties);
        }, holderFactory);
    }

    /**
     * Applies all values that have been registered to this registrar to the given vanilla registry.
     *
     * @param registry the registry to register all this registrar's values to.
     */
    public void apply(Registry<? super T> registry) {
        if (key != registry.key()) return;

        for (var entry : stuff.entrySet()) {
            Registry.register(registry, entry.getKey(), entry.getValue().get());
        }
    }

    /**
     * Factory for specific {@link KHolder} implementations.
     *
     * @param <T> the type the holder will hold.
     * @param <H> the holder type itself.
     */
    public interface KHolderFactory<T, H extends KHolder<T>> {
        /**
         * Creates a holder implementation.
         *
         * @param key      the key of the holder.
         * @param supplier the object constructor for the holder.
         * @return the custom holder implementation.
         */
        H create(ResourceKey<T> key, Supplier<T> supplier);
    }
}
