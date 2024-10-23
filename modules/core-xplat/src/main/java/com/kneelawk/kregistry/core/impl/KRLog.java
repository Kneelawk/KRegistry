package com.kneelawk.kregistry.core.impl;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KRLog {
    public static final Logger LOG = LoggerFactory.getLogger(KRConstants.MOD_ID);

    public static Consumer<Exception> error(String message) {
        return e -> LOG.error("[KRegistry] {}", message, e);
    }
}
