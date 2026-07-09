package datapackplugin;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class DatapackPluginBootstrap implements PluginBootstrap{
    @Override
    public void bootstrap(BootstrapContext context){
        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(event->{
                try{
                    Path datapackRoot=resolveDatapackRoot();
                    event.registrar().discoverPack(datapackRoot,"provided");
                }catch(URISyntaxException|IOException e){
                    throw new RuntimeException(e);
                }
            })
        );
    }
    private Path resolveDatapackRoot() throws IOException,URISyntaxException{
        URL codeSource=getClass().getProtectionDomain().getCodeSource().getLocation();
        Path location=Path.of(codeSource.toURI()).normalize();
        if(Files.isDirectory(location)){
            Path packMetadata=location.resolve("pack.mcmeta");
            if (Files.exists(packMetadata)){
                return location;
            }
        }
        if (Files.isRegularFile(location)){
            Path tempDir=Files.createTempDirectory("compact-diamonds-datapack");
            try(JarFile jarFile=new JarFile(location.toFile())){
                Enumeration<JarEntry> entries=jarFile.entries();
                boolean copiedPack=false;
                while(entries.hasMoreElements()){
                    JarEntry entry=entries.nextElement();
                    String name=entry.getName();
                    if(!name.equals("pack.mcmeta")&&!name.startsWith("data/")&&!name.equals("pack.png")){continue;}
                    if(entry.isDirectory()){
                        Files.createDirectories(tempDir.resolve(name));
                        continue;
                    }
                    Path target=tempDir.resolve(name).normalize();
                    if(!target.startsWith(tempDir)){
                        throw new IOException("Invalid datapack entry path: "+name);
                    }
                    Files.createDirectories(target.getParent());
                    try (InputStream input=jarFile.getInputStream(entry);
                         OutputStream output=Files.newOutputStream(target)){
                        input.transferTo(output);
                    }
                    copiedPack=true;
                }
                if (!copiedPack){
                    throw new IOException("No datapack files were found in the plugin jar.");
                }
            }
            return tempDir;
        }
        throw new IOException("Unable to locate an unpacked datapack root from the plugin jar.");
    }
}