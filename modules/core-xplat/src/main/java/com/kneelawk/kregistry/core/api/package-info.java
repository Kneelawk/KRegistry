/**
 * KRegistry core package.
 * <p>
 * Start by creating a {@link com.kneelawk.kregistry.core.api.KRegistrarSet} for your mod. Then use
 * {@link com.kneelawk.kregistry.core.api.KRegistrarSet#get(net.minecraft.resources.ResourceKey)} to get
 * {@link com.kneelawk.kregistry.core.api.KRegistrar}s for each registry. Next, register your objects using
 * {@link com.kneelawk.kregistry.core.api.KRegistrar#register(java.lang.String, java.util.function.Supplier)} to create
 * {@link com.kneelawk.kregistry.core.api.KHolder}s.
 * <p>
 * Finally, call {@link com.kneelawk.kregistry.core.api.KRegistrarSet#registerAll()} in your Fabric-side common
 * initializer and {@link com.kneelawk.kregistry.core.api.KRegistrarSet#register(net.minecraft.core.Registry)} in your
 * NeoForge-side {@code RegisterEvent}, or just use {@link com.kneelawk.kregistry.core.api.RegisterCallback} to avoid
 * platform-specific registration boilerplate.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
package com.kneelawk.kregistry.core.api;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
