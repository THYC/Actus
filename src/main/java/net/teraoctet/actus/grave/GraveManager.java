package net.teraoctet.actus.grave;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class GraveManager {
    
    public static final File FILE = new File("config/actus/grave.conf");
    public static final ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    public static ConfigurationNode grave = MANAGER.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void init() {
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();
            }
            grave = MANAGER.load();
        } catch (IOException e) {}	
    }
            
    /**
     * Sauvegarde une tombe
     * @param location coordonne de la tombe
     */
    public void saveGrave(String location){
        try {
            grave.getNode("grave",location);
            MANAGER.save(grave);
        } catch (IOException ex) {
            Logger.getLogger(GraveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                
    /**
     * Supprime une tombe
     * @param location coordonne de la tombe
     * @throws IOException Exception
     */
    public void delGrave(String location) throws IOException{
	grave.removeChild("grave." + location);
        MANAGER.save(grave);
        grave = MANAGER.load();
    }
        
    /**
     * Retourne une liste des tombes actives
     * @return
     */
    public List<String>getListGrave(){
        List<String> list = new ArrayList();
        grave.getChildrenMap().entrySet().stream().forEach((node) -> {
            list.add(node.getKey().toString());
        });
        return list;
    }
}
