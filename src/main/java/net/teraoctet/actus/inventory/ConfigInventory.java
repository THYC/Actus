package net.teraoctet.actus.inventory;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;

public class ConfigInventory {
    
    private final TypeToken<AInventory> TOKEN_CONFIG = TypeToken.of(AInventory.class);
    
    public Optional<AInventory> load(Player player, String id_inv){
        try {
            File file = new File("config/actus/inventory/" + player.getIdentifier() + ".conf");
            if(!file.exists())return Optional.empty();
            ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
            ConfigurationNode node = manager.createEmptyNode(ConfigurationOptions.defaults());
            node = manager.load();
            AInventory inv;
            if(!node.getNode("inv",id_inv).isVirtual()){
                inv =  node.getNode("inv",id_inv).getValue(TOKEN_CONFIG);
                inv.setPlayer(player); 
            }else{
                inv = new AInventory(player,id_inv);
                this.save(inv);
            }
            return Optional.of(inv);
        } catch (ObjectMappingException | IOException ex) {Logger.getLogger(ConfigInventory.class.getName()).log(Level.SEVERE, null, ex);}
        return Optional.empty();
    }
    
    public void save(AInventory inv){
        try {
            File file = new File("config/actus/inventory/" + inv.uuid + ".conf");
            ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
            ConfigurationNode node = manager.createEmptyNode(ConfigurationOptions.defaults());
            node = manager.load();
            node.getNode("inv",inv.id_inv).setValue(TOKEN_CONFIG, inv);
            try {
                manager.save(node);
            } catch (IOException ex) { Logger.getLogger(ConfigInventory.class.getName()).log(Level.SEVERE, null, ex);}
        } catch (ObjectMappingException | IOException ex) { Logger.getLogger(ConfigInventory.class.getName()).log(Level.SEVERE, null, ex);}
    }
}
