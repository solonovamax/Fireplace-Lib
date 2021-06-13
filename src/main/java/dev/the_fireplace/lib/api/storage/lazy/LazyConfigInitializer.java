package dev.the_fireplace.lib.api.storage.lazy;

import dev.the_fireplace.annotateddi.AnnotatedDI;
import dev.the_fireplace.lib.api.storage.utility.ReloadableManager;

public final class LazyConfigInitializer {
    private static final ReloadableManager RELOADABLE_MANAGER = AnnotatedDI.getInjector().getInstance(ReloadableManager.class);
    public static <T extends LazyConfig> T lazyInitialize(T config) {
        config.load();
        config.save();
        RELOADABLE_MANAGER.register(config);

        return config;
    }
}
