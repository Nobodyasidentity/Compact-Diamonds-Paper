package datapackplugin;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import java.io.IOException;
import java.net.URISyntaxException;

public final class DatapackPluginBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(event -> {
                try {
                    event.registrar().discoverPack(
                        getClass().getResource("/datapackplugin").toURI(),
                        "provided"
                    );
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            })
        );
    }
}