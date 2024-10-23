package com.kneelawk.kregistry.core.api;

import com.kneelawk.commonevents.api.Event;
import com.kneelawk.kregistry.core.impl.KRLog;

/**
 * Optional callback called right before registrar sets are queried and their contents registered.
 * <p>
 * This acts both as a way to provide {@link KRegistrarSet}s to a central platform-specific registration mechanism,
 * and as a great place to initialize the classes that actually hold {@link KHolder}s of the objects to be registered.
 * <p>
 * Note: On some platforms, this may be invoked shortly before some mods common initializers and shortly after others.
 */
@FunctionalInterface
public interface RegisterCallback {
    /**
     * Event fired right before registrar sets are queried and their contents registered.
     */
    Event<RegisterCallback> EVENT = Event.createSimple(RegisterCallback.class, KRLog.error("Error while registering"));

    /**
     * Called right before registrar sets are queried and their contents registered.
     *
     * @param ctx the interface allowing {@link KRegistrarSet}s to be collected and registered by platform-specific code.
     */
    void register(Context ctx);

    /**
     * Context for a register callback.
     */
    interface Context {
        /**
         * Adds the given registrar set to the ones handled by platform-specific code.
         *
         * @param set the registrar set to add.
         */
        void register(KRegistrarSet set);
    }
}
