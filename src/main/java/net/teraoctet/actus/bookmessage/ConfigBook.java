package net.teraoctet.actus.bookmessage;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.createFile;
import static java.nio.file.Files.notExists;

import com.google.common.base.Objects;
import com.google.common.reflect.TypeToken;
import java.io.File;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.text.BookView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static net.teraoctet.actus.Actus.plugin;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@ConfigSerializable
public class ConfigBook {

    private final TypeToken<ConfigBook> TOKEN_CONFIG = TypeToken.of(ConfigBook.class);

    @Setting public BookView book;
    private static final Path path = Paths.get("config/actus/book.conf");
    
    //public static File file = new File("config/actus/actus.conf");
    //public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    //public static ConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    private final String nodeBook = "newbie";

    //public ConfigBook(String nodeBook) {
        //this.nodeBook = nodeBook;
    //}

    public ConfigBook load() throws IOException, ObjectMappingException{
        //if (notExists(path)) {
           //createDirectories(path.getParent());
           //defaultConfig.copyToFile(path);
        //}
        BookView.builder().build();
        return createLoader().load().getNode(nodeBook).getValue(TOKEN_CONFIG);
    }

    public ConfigBook save(String book) throws IOException, ObjectMappingException {
        if (notExists(path)) {
            createDirectories(path.getParent());
            createFile(path);
        }
        HoconConfigurationLoader loader = createLoader();
        ConfigurationNode root = loader.createEmptyNode().getNode(book).setValue(TOKEN_CONFIG, this);
        loader.save(root);
        return this;
    }

    private static HoconConfigurationLoader createLoader() {
        return HoconConfigurationLoader.builder().setPath(path).build();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("book", this.book).toString();
    }

}
