package com.fwloopins.nullmovement;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullMovement implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NullMovement");

    @Override
    public void onInitialize() {
        logInfo("NullMovement initialised");
    }

    public static void logInfo(String msg) {
        LOGGER.info("[NullMovement] " + msg);
    }
}
