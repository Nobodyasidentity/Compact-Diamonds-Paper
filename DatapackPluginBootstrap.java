package datapackplugin;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import java.io.IOException;
import java.net.URI;

public final class DatapackPluginBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(event -> {
                try {
                    URI jarRoot = DatapackPluginBootstrap.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI();
                    event.registrar().discoverPack(jarRoot, "provided");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
        );
    }
}