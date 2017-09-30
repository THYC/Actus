package net.teraoctet.actus.utils;
import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {
    public static File file = new File("config/actus/actus.conf");
    public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void setup() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                config.getNode("mysql", "use").setValue(false);
                config.getNode("mysql", "host").setValue("localhost");
                config.getNode("mysql", "port").setValue(3306);
                config.getNode("mysql", "username").setValue("root");
                config.getNode("mysql", "password").setValue("password");
                config.getNode("mysql", "database").setValue("minecraft");
                config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").setValue(3600);
                config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").setValue(600);
                config.getNode("limits", "DEFAULT_TEMP_JAIL_IN_SECONDS").setValue(3600);
                config.getNode("plot", "DEL_SIGN_AFTER_SALE").setValue(true);
                config.getNode("plot", "DISPLAY_PLOT_MSG_FOR_OWNER").setValue(true);
                config.getNode("guild", "MAX_NUMBER_OF_MEMBER").setValue(20);
                config.getNode("guild", "NAME_MAX_SIZE").setValue(25);
                config.getNode("guild", "NAME_MIN_SIZE").setValue(5);
                config.getNode("server", "COOLDOWN_TO_TP").setValue(10);
                config.getNode("server", "UNIQUE_SPAWN_WORLD").setValue(0);
                config.getNode("server", "SPAWN_WORLD").setValue("World");
                config.getNode("server", "DIAMETER_MAX_TPR").setValue(2000);
                config.getNode("server", "TNT_DISABLE").setValue(false);
                config.getNode("server", "CREEPER_DISABLE").setValue(false);
                config.getNode("version").setValue(1);
                manager.save(config);
            }
            config = manager.load();
        } catch (IOException e) {}
    }
    
    public static String SERVER_TITLE() { return config.getNode("server", "title").getString(); }
    public static String SERVER_SUBTITLE() { return config.getNode("server", "subtitle").getString(); }
    public static String SERVER_URLWEB() { return config.getNode("server", "urlweb").getString(); }
    public static boolean MYSQL_USE() { return config.getNode("mysql", "use").getBoolean(); }
    public static String MYSQL_HOST() { return config.getNode("mysql", "host").getString(); }
    public static int MYSQL_PORT() { return config.getNode("mysql", "port").getInt(); }
    public static String MYSQL_USERNAME() { return config.getNode("mysql", "username").getString(); }
    public static String MYSQL_PASSWORD() { return config.getNode("mysql", "password").getString(); }
    public static String MYSQL_DATABASE() { return config.getNode("mysql", "database").getString(); }
    public static int LIMITS_MAX_TEMPBAN_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").getInt(); }
    public static int LIMITS_MAX_MUTE_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").getInt(); }
    public static int DEFAULT_TEMP_JAIL_IN_SECONDS() { return config.getNode("limits", "DEFAULT_TEMP_JAIL_IN_SECONDS").getInt(); }
    public static boolean DEL_SIGN_AFTER_SALE() { return config.getNode("plot", "DEL_SIGN_AFTER_SALE").getBoolean(); }
    public static boolean DISPLAY_PLOT_MSG_FOR_OWNER() { return config.getNode("plot", "DISPLAY_PLOT_MSG_FOR_OWNER").getBoolean(); }
    public static int GUILD_MAX_NUMBER_OF_MEMBER() { return config.getNode("guild", "MAX_NUMBER_OF_MEMBER").getInt(); }
    public static int GUILD_NAME_MAX_SIZE() { return config.getNode("guild", "NAME_MAX_SIZE").getInt(); }
    public static int GUILD_NAME_MIN_SIZE() { return config.getNode("guild", "NAME_MIN_SIZE").getInt(); }
    public static int COOLDOWN_TO_TP() { return config.getNode("server", "COOLDOWN_TO_TP").getInt(); }
    public static int UNIQUE_SPAWN_WORLD() { return config.getNode("server", "UNIQUE_SPAWN_WORLD").getInt(); }
    public static int DIAMETER_MAX_TPR() { return config.getNode("server", "DIAMETER_MAX_TPR").getInt(); }
    public static String SPAWN_WORLD() { return config.getNode("server", "SPAWN_WORLD").getString(); }
    public static boolean TNT_DISABLE() { return config.getNode("server", "TNT_DISABLE").getBoolean(); }
    public static boolean CREEPER_DISABLE() { return config.getNode("server", "CREEPER_DISABLE").getBoolean(); }
}