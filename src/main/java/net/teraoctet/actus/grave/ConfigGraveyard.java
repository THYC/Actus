package net.teraoctet.actus.grave;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.type.TileEntityInventory;

public class ConfigGraveyard {
    
    private final TypeToken<Graveyard> TOKEN_CONFIG = TypeToken.of(Graveyard.class);
    private static final File FILE = new File("config/actus/graveyard.conf");
    private static final ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    private static ConfigurationNode graveyardNode = MANAGER.createEmptyNode(ConfigurationOptions.defaults());;
    

    //public static ConfigurationNode graveNode = MANAGER.createEmptyNode(ConfigurationOptions.defaults());
    
    /**
     * retourne le caveau du point idGraveyard
     * @param idGraveyard
     * @return 
     */
    public Optional<Graveyard> load(String idGraveyard){
        try {
            graveyardNode = MANAGER.load();
            Graveyard graveyard;
            if(!graveyardNode.getNode(idGraveyard).isVirtual()){
                graveyard = graveyardNode.getNode(idGraveyard).getValue(TOKEN_CONFIG);
                return Optional.of(graveyard);
            }
        } catch (ObjectMappingException | IOException ex) {
            Logger.getLogger(ConfigGraveyard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Sauvegarde un caveau
     * @param graveyard
     * @throws IOException
     * @throws ObjectMappingException 
     */
    public void save(Graveyard graveyard) throws IOException, ObjectMappingException{
        graveyardNode = MANAGER.load();
        graveyardNode.getNode(graveyard.idGraveyard).setValue(TOKEN_CONFIG, graveyard);
        MANAGER.save(graveyardNode);
    }
    
    /**
     * init du fichier graveyard.conf 
     */
    public static void init(){
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ConfigGraveyard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
                                  
    /**
     * Supprime un caveau dans le fichier config
     * @param idGraveyard
     * @throws IOException Exception
     */
    public void delGrave(String idGraveyard) throws IOException{
        graveyardNode = MANAGER.load();
        Optional<Graveyard> graveyard = load(idGraveyard);
        if(graveyard.isPresent()){
            graveyardNode.removeChild(idGraveyard);
        MANAGER.save(graveyardNode);
        graveyardNode = MANAGER.load();
        }
    }
        
    /**
     * Retourne une liste des caveaux actifs
     * @return List
     */
    public List<String>getListGraveyard(){
        try {
            graveyardNode = MANAGER.load();
        } catch (IOException ex) {
            Logger.getLogger(ConfigGraveyard.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> list = new ArrayList();
        graveyardNode.getChildrenMap().entrySet().stream().forEach((id) -> {
            list.add(id.getKey().toString());
        });
        return list;
    }
    
    /**
     * retourne un ID pour le nouveau caveau
     * @return int
     */
    public int getmaxID(){
        int x;      
        x = getListGraveyard().size();
        return x + 1;
    }
    
    /**
     * retourne un ID caveau/graveyard aleatoire
     * @return 
     */
    public String getRandomID(){
        
        int max = getListGraveyard().size();
        int min = 1;
        
        Random rand = new Random(); 
        int id = rand.nextInt(max - min + 1) + min;       
        
        return String.valueOf(id);
    }
    
    /**
     * 
     * @param graveyard
     * @return 
     */
    public Inventory getGraveyardInventory(Graveyard graveyard){
        TileEntity chest = graveyard.locChest1.getTileEntity().get();
        final TileEntityInventory inventory = (TileEntityInventory) chest;
        return inventory;
    }
    
    /**
     * Ouvre l'inventaire du caveau 
     * @param player
     * @param graveyard 
     */
    public void openGrave(Player player, Graveyard graveyard){
        Inventory inv = getGraveyardInventory(graveyard);
        player.openInventory(inv);
    }
    
}
