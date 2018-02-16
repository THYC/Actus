package net.teraoctet.actus.utils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class Config {
    public static final File FILE = new File("config/actus/actus.conf");
    public static final ConfigurationLoader<CommentedConfigurationNode> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    public static CommentedConfigurationNode config = MANAGER.createEmptyNode();
    
    public static void setup() {
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();

                config.getNode("mysql").setComment("PARAMETRE DE CONNECTION MYSQL, SI USE=FALSE ALORS C'EST LA BASE H2 QUI EST UTILISE");
                config.getNode("mysql", "USE").setValue(false).setComment("UTILISATION DE MYSQL ? true/false");
                config.getNode("mysql", "HOST").setValue("localhost");
                config.getNode("mysql", "PORT").setValue(3306);
                config.getNode("mysql", "USERNAME").setValue("root");
                config.getNode("mysql", "PASSWORD").setValue("password");
                config.getNode("mysql", "DATABASE").setValue("minecraft");
                config.getNode("limits").getOptions().setHeader("limits");
                config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").setValue(3600);
                config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").setValue(600);
                config.getNode("limits", "DEFAULT_TEMP_JAIL_IN_SECONDS").setValue(3600);
                config.getNode("plot").setComment("PARAMETRAGE PLOT/PARCELLE");
                config.getNode("plot", "DEL_SIGN_AFTER_SALE").setValue(true).setComment("SUPPRIME LES PANNEAUX DE VENTE DE PARCELLE APRES VENTE");
                config.getNode("plot", "DISPLAY_PLOT_MSG_FOR_OWNER").setValue(true).setComment("AFFICHE LE MSG D'ACCUEIL POUR LE OWNER");
                config.getNode("guild").setComment("PARAMETRAGE GUILD");
                config.getNode("guild", "MAX_NUMBER_OF_MEMBER").setValue(20).setComment("NOMBRE MAXIMUM DE MEMBRE PAR GUILD");
                config.getNode("guild", "NAME_MAX_SIZE").setValue(25).setComment("NB MAXI DE CARACTERE POUR LE NOM DE GUILD");
                config.getNode("guild", "NAME_MIN_SIZE").setValue(5).setComment("NB MINI DE CARACTERE POUR LE NOM DE GUILD");
                config.getNode("server").setComment("PARAMETRAGE DU SERVEUR");
                config.getNode("server", "TITLE").setValue("Bienvenu sur CubiCraft").setComment("affichage msg Title a la connection");
                config.getNode("server", "SUBTITLE").setValue("Serveur [FR] CRAFT.TER@OCTET");
                config.getNode("server", "URLWEB").setValue("http://craft.teraoctet.net");
                config.getNode("server", "COOLDOWN_TO_TP").setValue(10);
                config.getNode("server", "UNIQUE_SPAWN_WORLD").setValue(false);
                config.getNode("server", "SPAWN_WORLD").setValue("World");
                config.getNode("server", "DIAMETER_MAX_TPR").setValue(5000);
                config.getNode("server", "DIAMETER_MIN_TPR").setValue(2000);
                config.getNode("server", "TNT_DISABLE").setValue(false);
                config.getNode("server", "CREEPER_DISABLE").setValue(false);
                config.getNode("server", "LEVEL_ADMIN").setValue(10);
                config.getNode("server", "LEVEL_DEFAULT").setValue(0);
                config.getNode("server", "AUTOFOREST").setValue(false);
                config.getNode("server", "ENABLE_TREEBREAK").setValue(false);
                config.getNode("server", "ENDERMAN_DESTRUCT").setValue(false);
                config.getNode("server", "AUTOLOCKCHEST").setValue(false);
                config.getNode("server", "ENABLE_LOCKCHEST").setValue(true);
                config.getNode("server", "ITEM_DURABILITY").setValue(10);
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
    public static int DIAMETER_MIN_TPR() { return config.getNode("server", "DIAMETER_MIN_TPR").getInt(); }
    public static String SPAWN_WORLD() { return config.getNode("server", "SPAWN_WORLD").getString(); }
    public static boolean TNT_DISABLE() { return config.getNode("server", "TNT_DISABLE").getBoolean(); }
    public static boolean CREEPER_DISABLE() { return config.getNode("server", "CREEPER_DISABLE").getBoolean(); }
    public static int LEVEL_ADMIN() { return config.getNode("server", "LEVEL_ADMIN").getInt(); }
    public static int LEVEL_DEFAULT() { return config.getNode("server", "LEVEL_DEFAULT").getInt(); }
    public static boolean AUTOFOREST() { return config.getNode("server", "AUTOFOREST").getBoolean(); }
    public static boolean ENABLE_TREEBREAK() { return config.getNode("server", "ENABLE_TREEBREAK").getBoolean(); }
    public static boolean ENDERMAN_DESTRUCT() { return config.getNode("server", "ENDERMAN_DESTRUCT").getBoolean(); }
    public static boolean AUTO_LOCKCHEST() { return config.getNode("server", "AUTO_LOCKCHEST").getBoolean(); }
    public static boolean ENABLE_LOCKCHEST() { return config.getNode("server", "ENABLE_LOCKCHEST").getBoolean(); }
    public static int DAYS_BEFORE_MOVE_GRAVE() { return config.getNode("grave", "DAYS_BEFORE_MOVE_GRAVE").getInt(3); }
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
    
    /**
     * Active/Desactive l'affichage de la transaction des Shops via un bookview
     * @param val 
     */
    public void setEnableShopBookview(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("shop","ENABLE_SHOP_BOOKVIEW").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Active/Desactive la replantation automatique des racines d'arbres
     * @param val 
     */
    public void setEnableAutoForest(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("server","AUTOFOREST").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Active/Desactive l'efondrement de l'arbre coupé avec la hache diamant
     * @param val 
     */
    public void setEnableTreeBreak(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("server","ENABLE_TREEBREAK").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Active/Desactive l'autoLock des coffres, si true, le coffre sera lock dès sa pose
     * @param val 
     */
    public void setEnableAutoLockChest(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("server","AUTO_LOCKCHEST").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Active/Desactive le module de LockChest (securite coffre)
     * @param val 
     */
    public void setEnableLockChest(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("server","ENABLE_LOCKCHEST").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Active/Desactive la casse des blocks lors de l'explosion des creepers
     * @param val 
     */
    public void setEnableCreeper(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("server","CREEPER_DISABLE").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Active/Desactive la casse des blocks lors de l'explosion de la TNT
     * @param val 
     */
    public void setEnableTNT(boolean val) {
        try {
            config = MANAGER.load();
            config.getNode("server","TNT_DISABLE").setValue(val);
            MANAGER.save(config);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}