package net.teraoctet.actus.grave;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configGraveyard;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.type.TileEntityInventory;

public class GraveManager {
    
    private final TypeToken<Grave> TOKEN_CONFIG = TypeToken.of(Grave.class);
    private static final File FILE = new File("config/actus/grave.conf");
    private static final ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    private static ConfigurationNode graveNode = MANAGER.createEmptyNode(ConfigurationOptions.defaults());;
    
    /**
     * retourne la tombe du point idGrave
     * @param idGrave
     * @return 
     */
    public Optional<Grave> load(String idGrave){
        try {
            graveNode = MANAGER.load();
            Grave grave;
            if(!graveNode.getNode(idGrave).isVirtual()){
                grave = graveNode.getNode(idGrave).getValue(TOKEN_CONFIG);
                return Optional.of(grave);
            }
        } catch (ObjectMappingException | IOException ex) {
            Logger.getLogger(GraveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Sauvegarde une tombe
     * @param grave
     * @throws IOException
     * @throws ObjectMappingException 
     */
    public void save(Grave grave) throws IOException, ObjectMappingException{
        graveNode = MANAGER.load();
        graveNode.getNode(grave.idGrave).setValue(TOKEN_CONFIG, grave);
        MANAGER.save(graveNode);
    }
    
    /**
     * init du fichier tombe/grave.conf 
     */
    public static void init(){
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GraveManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
                                  
    /**
     * Supprime une tombe dans le fichier config et sur la map
     * @param location coordonne de la tombe
     * @throws IOException Exception
     */
    public void delGrave(String location) throws IOException{
        graveNode = MANAGER.load();
        Optional<Grave> grave = getGrave(location);
        if(grave.isPresent()){
            grave.get().getSignLocation().get().removeBlock();
            BlockState bs1 = grave.get().getBlock1();
            BlockState bs2 = grave.get().getBlock2();
            grave.get().getLocationBlock1().get().setBlock(bs1);
            grave.get().getLocationBlock2().get().setBlock(bs2);
            if(grave.get().getUniqueIdAS().isPresent()){
                Optional<Entity> ent = grave.get().getGraveLoc().getExtent().getEntity(UUID.fromString(grave.get().getUniqueIdAS().get()));
                if(ent.isPresent())ent.get().remove();
            }
            
        }
        graveNode.removeChild(location);
        MANAGER.save(graveNode);
        graveNode = MANAGER.load();
    }
        
    /**
     * Retourne une liste des tombes actives
     * @return
     */
    public List<String>getListGrave(){
        try {
            graveNode = MANAGER.load();
        } catch (IOException ex) {
            Logger.getLogger(GraveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> list = new ArrayList();
        graveNode.getChildrenMap().entrySet().stream().forEach((id) -> {
            list.add(id.getKey().toString());
        });
        return list;
    }
    
    /**
     * Teleporte sur deadLocation de la tombe / Grave
     * @param player joueur à téléporter
     * @param idGrave id de l'objet Grave
     * @return retourne True si idGrave est bien l'id d'une tombe
     */
    public boolean tpGrave(Player player, String idGrave){
        Optional<Grave> graveopt = getGrave(idGrave);
        if(graveopt.isPresent()){
            player.setLocation(graveopt.get().getDeadLocation().get());
            return true;
        }
        return false;
    }
        
    /**
     * Retourne l'objet Grave / tombe
     * @param idGrave String idGrave
     * @return objet Grave
     */
    public Optional<Grave> getGrave(String idGrave){
        try {
            graveNode = MANAGER.load();
            ConfigurationNode node = graveNode.getNode(idGrave);
            if(!node.isVirtual()){
                return load(idGrave);
            }
        } catch (IOException ex) {
            Logger.getLogger(GraveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Retourne True si l'emplacement est une tombe/graveNode
     * @param loc String location
     * @return Boolean
     */
    public boolean hasGrave(String loc){
        return !graveNode.getNode(loc).isVirtual();
    }
    
    /**
     * Retourne l'inventaire de la tombe
     * @param grave
     * @return 
     */
    public Inventory getGraveInventory(Grave grave){
        TileEntity chest = grave.getLocationBlock1().get().getTileEntity().get();
        final TileEntityInventory inventory = (TileEntityInventory) chest;
        return inventory;
    }
    
    /**
     * Ouvre l'inventaire de la tombe
     * @param player
     * @param grave 
     */
    public void openGrave(Player player, Grave grave){
        Inventory graveInv = getGraveInventory(grave);
        player.openInventory(graveInv);
    }
    
    /**
     * Déplace le contenu de la tombe dans un caveau
     * @param grave 
     */
    public void moveGrave(Grave grave){
        Optional<Graveyard> graveyard = configGraveyard.load(configGraveyard.getRandomID());
        if(graveyard.isPresent()){
            TileEntity cave = graveyard.get().locChest1.getTileEntity().get();
            final TileEntityInventory invCave = (TileEntityInventory) cave;
            
            Optional<TileEntity> chest = grave.getLocationBlock1().get().getTileEntity();
            if(chest.isPresent()){
                final TileEntityInventory invGrave = (TileEntityInventory) chest.get();
            
                invGrave.slots().forEach((Inventory slot) -> {
                    if (slot.peek().isPresent()) {                    
                        if (invCave.offer(slot.peek().get()).getType() != InventoryTransactionResult.Type.SUCCESS) invCave.offer(slot.peek().get());
                    }
                });
            
                invGrave.clear();
                
                Optional<TileEntity> signBlock = graveyard.get().signGrave.getTileEntity();
                if(signBlock.isPresent()){
                    TileEntity tileSign = signBlock.get();
                    Sign sign=(Sign)tileSign;
                    Optional<SignData> opSign = sign.getOrCreate(SignData.class);
                    SignData signData = opSign.get();
                    signData.setElement(1, MESSAGE("&4" + grave.getName()));
                    sign.offer(signData);
                }
            }
            try {
                delGrave(grave.getIDgrave());
            } catch (IOException ex) {
                Logger.getLogger(GraveManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }
    
    /**
     * Deplace le contenu des tombes ayant dépassé le délai au cimetière
     */
    public void moveGraveyard(){
        Grave grave;
        int nb = 0;
        for(String loc : getListGrave()){            
            if(sm.addDate(load(loc).get().getGraveTime()) < sm.dateToLong()){
                grave = load(loc).get();
                moveGrave(grave);
                plugin.getLogger().info("Grave : " + grave.getName());
                nb = nb+1;
            }  
        }
        plugin.getLogger().info("Grave : " + nb + " tombe(s) ont ete deplace dans des cryptes");
    }
}
