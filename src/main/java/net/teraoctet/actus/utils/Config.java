package net.teraoctet.actus.utils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {
    public static final File FILE = new File("config/actus/actus.conf");
    public static final ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    public static ConfigurationNode config = MANAGER.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void setup() {
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();
                config.getNode("mysql").getOptions().setHeader("PARAMETRE DE CONNECTION MYSQL, SI USE=FALSE ALORS C'EST LA BASE H2 QUI EST UTILISE");
                config.getNode("mysql", "USE").setValue(false);
                config.getNode("mysql", "HOST").setValue("localhost");
                config.getNode("mysql", "PORT").setValue(3306);
                config.getNode("mysql", "USERNAME").setValue("root");
                config.getNode("mysql", "PASSWORD").setValue("password");
                config.getNode("mysql", "DATABASE").setValue("minecraft");
                config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").setValue(3600);
                config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").setValue(600);
                config.getNode("limits", "DEFAULT_TEMP_JAIL_IN_SECONDS").setValue(3600);
                config.getNode("plot", "DEL_SIGN_AFTER_SALE").setValue(true);
                config.getNode("plot", "DISPLAY_PLOT_MSG_FOR_OWNER").setValue(true);
                config.getNode("guild", "MAX_NUMBER_OF_MEMBER").setValue(20);
                config.getNode("guild", "NAME_MAX_SIZE").setValue(25);
                config.getNode("guild", "NAME_MIN_SIZE").setValue(5);
                config.getNode("server", "TITLE").setValue("Bienvenu sur CubiCraft");
                config.getNode("server", "SUBTITLE").setValue("Serveur [FR] CRAFT.TER@OCTET");
                config.getNode("server", "URLWEB").setValue("http://craft.teraoctet.net");
                config.getNode("server", "COOLDOWN_TO_TP").setValue(10);
                config.getNode("server", "UNIQUE_SPAWN_WORLD").setValue(false);
                config.getNode("server", "SPAWN_WORLD").setValue("World");
                config.getNode("server", "DIAMETER_MAX_TPR").setValue(2000);
                config.getNode("server", "TNT_DISABLE").setValue(false);
                config.getNode("server", "CREEPER_DISABLE").setValue(false);
                config.getNode("server", "LEVEL_ADMIN").setValue(10);
                config.getNode("server", "LEVEL_DEFAULT").setValue(0);
                config.getNode("grave", "DAYS_BEFORE_MOVE_GRAVE").setValue(10);
                config.getNode("grave", "ENABLE_SKULL_GRAVE").setValue(true);
                config.getNode("grave", "ENABLE_SIGN_GRAVE").setValue(false);
                config.getNode("troc", "ENABLE_TROC_SCOREBOARD").setValue(false);
                config.getNode("shop", "ENABLE_SHOP_BOOKVIEW").setValue(false);
                config.getNode("version").setValue(2);
                MANAGER.save(config);
            }
            config = MANAGER.load();
        } catch (IOException e) {}
    }
    
    public static String SERVER_TITLE() { return config.getNode("server", "TITLE").getString(); }
    public static String SERVER_SUBTITLE() { return config.getNode("server", "SUBTITLE").getString(); }
    public static String SERVER_URLWEB() { return config.getNode("server", "URLWEB").getString(); }
    public static boolean MYSQL_USE() { return config.getNode("mysql", "USE").getBoolean(); }
    public static String MYSQL_HOST() { return config.getNode("mysql", "HOST").getString(); }
    public static int MYSQL_PORT() { return config.getNode("mysql", "PORT").getInt(); }
    public static String MYSQL_USERNAME() { return config.getNode("mysql", "USERNAME").getString(); }
    public static String MYSQL_PASSWORD() { return config.getNode("mysql", "PASSWORD").getString(); }
    public static String MYSQL_DATABASE() { return config.getNode("mysql", "DATABASE").getString(); }
    public static int LIMITS_MAX_TEMPBAN_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").getInt(); }
    public static int LIMITS_MAX_MUTE_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").getInt(); }
    public static int DEFAULT_TEMP_JAIL_IN_SECONDS() { return config.getNode("limits", "DEFAULT_TEMP_JAIL_IN_SECONDS").getInt(); }
    public static boolean DEL_SIGN_AFTER_SALE() { return config.getNode("plot", "DEL_SIGN_AFTER_SALE").getBoolean(); }
    public static boolean DISPLAY_PLOT_MSG_FOR_OWNER() { return config.getNode("plot", "DISPLAY_PLOT_MSG_FOR_OWNER").getBoolean(); }
    public static int GUILD_MAX_NUMBER_OF_MEMBER() { return config.getNode("guild", "MAX_NUMBER_OF_MEMBER").getInt(); }
    public static int GUILD_NAME_MAX_SIZE() { return config.getNode("guild", "NAME_MAX_SIZE").getInt(); }
    public static int GUILD_NAME_MIN_SIZE() { return config.getNode("guild", "NAME_MIN_SIZE").getInt(); }
    public static int COOLDOWN_TO_TP() { return config.getNode("server", "COOLDOWN_TO_TP").getInt(); }
    public static boolean UNIQUE_SPAWN_WORLD() { return config.getNode("server", "UNIQUE_SPAWN_WORLD").getBoolean(); }
    public static int DIAMETER_MAX_TPR() { return config.getNode("server", "DIAMETER_MAX_TPR").getInt(); }
    public static String SPAWN_WORLD() { return config.getNode("server", "SPAWN_WORLD").getString(); }
    public static boolean TNT_DISABLE() { return config.getNode("server", "TNT_DISABLE").getBoolean(); }
    public static boolean CREEPER_DISABLE() { return config.getNode("server", "CREEPER_DISABLE").getBoolean(); }
    public static int LEVEL_ADMIN() { return config.getNode("server", "LEVEL_ADMIN").getInt(); }
    public static int LEVEL_DEFAULT() { return config.getNode("server", "LEVEL_DEFAULT").getInt(); }
    public static int DAYS_BEFORE_MOVE_GRAVE() { return config.getNode("grave", "DAYS_BEFORE_MOVE_GRAVE").getInt(); }
    public static boolean ENABLE_SKULL_GRAVE() { return config.getNode("grave", "ENABLE_SKULL_GRAVE").getBoolean(); }
    public static boolean ENABLE_SIGN_GRAVE() { return config.getNode("grave", "ENABLE_SIGN_GRAVE").getBoolean(); }
    public static boolean ENABLE_TROC_SCOREBOARD() { return config.getNode("troc", "ENABLE_TROC_SCOREBOARD").getBoolean(); }
    public static boolean ENABLE_SHOP_BOOKVIEW() { return config.getNode("shop", "ENABLE_SHOP_BOOKVIEW").getBoolean(); }
    
    public void setEnableSkullGrave(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("grave","ENABLE_SKULL_GRAVE").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setEnableSignGrave(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("grave","ENABLE_SIGN_GRAVE").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setEnableShopBookview(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("shop","ENABLE_SHOP_BOOKVIEW").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}