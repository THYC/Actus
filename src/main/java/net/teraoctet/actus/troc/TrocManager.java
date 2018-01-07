package net.teraoctet.actus.troc;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.CB_TROC;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.troc.EnumTransactType.BUY;
import static net.teraoctet.actus.troc.EnumTransactType.SALE;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.Data.getGuild;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import static org.spongepowered.api.block.BlockTypes.WALL_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.BARRIER;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackComparators;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TrocManager {
    
    private final TypeToken<Troc> TOKEN_CONFIG = TypeToken.of(Troc.class);
    private static final File FILE = new File("config/actus/troc.conf");
    private static final ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    private static ConfigurationNode trocNode = MANAGER.createEmptyNode(ConfigurationOptions.defaults());;
    
    /**
     * retourne l'item Troc spécifié à la position slot du Troc
     * @param loc String location du coffre Troc
     * @param slot position dans le coffre Troc
     * @return Troc
     */
    public Optional<Troc> load(String loc, int slot){
        try {
            trocNode = MANAGER.load();
            Troc troc;
            if(!trocNode.getNode(loc,String.valueOf(slot)).isVirtual()){
                troc = trocNode.getNode(loc,String.valueOf(slot)).getValue(TOKEN_CONFIG);
                return Optional.of(troc);
            }
        } catch (ObjectMappingException | IOException ex) {
            Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Sauvegarde le troc transmis
     * @param troc objet Troc 
     */
    public void save(Troc troc){
        try {
            trocNode = MANAGER.load();
            trocNode.getNode(troc.getLoc(), String.valueOf(troc.getSlot())).setValue(TOKEN_CONFIG, troc);
            MANAGER.save(trocNode);
        } catch (IOException | ObjectMappingException ex) {
            Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Enregistre le owner/guild du chestTroc
     * @param trocLoc position au format String location "world:x:y:z"
     * @param player owner/proprietaire du Troc
     * @param idGuild id de la Guild proprietaire du Troc
     * @throws IOException
     * @throws ObjectMappingException 
     */
    public void initOwnerTroc(String trocLoc, Player player, int idGuild) throws IOException, ObjectMappingException{
        trocNode = MANAGER.load();
        if(idGuild == 0){
            trocNode.getNode(trocLoc,"owner").setValue(player.getName());
            trocNode.getNode(trocLoc,"guild").setValue(idGuild);
        }else{
            trocNode.getNode(trocLoc,"owner").setValue("_");
            trocNode.getNode(trocLoc,"guild").setValue(idGuild);
        }
        MANAGER.save(trocNode);
    }
    
    /**
     * Retourne le proprietaire du chestTroc si aucune Guild declaré
     * @param trocLoc position au format String location "world:x:y:z"
     * @return String Owner
     */
    public String getOwnerTroc(String trocLoc){
        try {
            trocNode = MANAGER.load();
            String owner = trocNode.getNode(trocLoc,"owner").getString("owner");
            return owner;
        } catch (IOException ex) {
            Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "LIBRE";
    }
    
    /**
     * Retourne l'ID de la Guild proprietaire du chestTroc
     * @param trocLoc position au format String location "world:x:y:z"
     * @return l'id guild format Integer
     */
    public Integer getIdGuildTroc(String trocLoc){
        try {
            trocNode = MANAGER.load();
            int idGuild = trocNode.getNode(trocLoc,"guild").getInt(0);
            return idGuild;
        } catch (IOException ex) {
            Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * init du fichier troc.conf 
     */
    public static void init(){
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
                                  
    /**
     * Supprime un troc dans le fichier config
     * @param loc String location du coffre Troc
     * @param slot position du slot dans le coffre Troc
     * @return true si supprimé avec succès
     * @throws IOException Exception
     */
    public boolean delTroc(String loc, int slot) throws IOException{
        trocNode = MANAGER.load();
        ConfigurationNode node = trocNode.getNode(loc);
        if(!node.isVirtual()){
            node.removeChild(String.valueOf(slot));
            MANAGER.save(trocNode);
            trocNode = MANAGER.load();
            return true;
        }
        return false;
    }
    
    public Optional<ItemStackSnapshot> getItemStackSnapshot(String loc, int slot){
        Optional<Troc> troc = getTroc(loc,slot);
        if(troc.isPresent()){
            return Optional.of(troc.get().getItem());
        }
        return Optional.empty();
    }
    
    /**
     * Retourne l'objet Troc
     * @param loc String location du coffre Troc
     * @param slot position slot dans le coffre Troc
     * @return objet Troc
     */
    public Optional<Troc> getTroc(String loc, int slot){
        try {
            trocNode = MANAGER.load();
            ConfigurationNode node = trocNode.getNode(loc, String.valueOf(slot));
            if(!node.isVirtual()){
                return load(loc, slot);
            }
        } catch (IOException ex) {
            Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }
    
    /**
     * Définit la quantité maximum d'achat demandé, change le Lore de l'item
     * @param chestTroc
     * @param loc String location du coffre Troc
     * @param slot position dans le coffre
     * @param qteMax quantité maximum
     * @return 
     */
    public boolean setBuyQteMax(Inventory chestTroc, String loc, int slot,int qteMax){
        Optional<Troc> troc = getTroc(loc,slot);
        if(troc.isPresent()){
            troc.get().setQteMax(qteMax);
            save(troc.get());
            
            String[] parts = chestTroc.getName().get().split(" ");
            String locTroc = parts[1];

            int index = 0;
            
            for (Inventory inv : chestTroc.slots()) {
                if (slot == index){
                    Optional<ItemStack> is = setItemTroc(troc.get(), BUY, qteMax, troc.get().getPrice(), locTroc);
                    inv.set(is.get());
                }
                index = index + 1;
                if(index == 9)return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Affiche sur le contenu du troc dans le scoreboard
     * @param player
     * @param loc position string location du chest troc au format world:x:y:z
     */
    public void displayScoreboard(Player player, String loc){    
        Scoreboard scoreboard = Scoreboard.builder().build();
	Objective obj = Objective.builder().name("TransactionTroc").criterion(Criteria.DUMMY).displayName(MESSAGE("&9---- TROC ----")).build();

	Score c;
        
        if(!getTroc(loc,0).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,0).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,0).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,0).get().getPrice() + "  -1"));
            c.setScore(1);
        }

        if(!getTroc(loc,1).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,1).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,1).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,1).get().getPrice() + "  -2"));
            c.setScore(2);
        }

        if(!getTroc(loc,2).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,2).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,2).get().getItem().getType().getTranslation() 
                    + " - " + getTroc(loc,2).get().getPrice() + "  -3"));
            c.setScore(3);
        }

        if(!getTroc(loc,3).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,3).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,3).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,3).get().getPrice() + "  -4"));
            c.setScore(4);
        }

        if(!getTroc(loc,4).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,4).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,4).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,4).get().getPrice() + "  -5"));
            c.setScore(5);
        }

        if(!getTroc(loc,5).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,5).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,5).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,5).get().getPrice() + "  -6"));
            c.setScore(6);
        }

        if(!getTroc(loc,6).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,6).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,6).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,6).get().getPrice() + "  -7"));
            c.setScore(7);
        }

        if(!getTroc(loc,7).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,7).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,7).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,7).get().getPrice() + "  -8"));
            c.setScore(8);
        }

        if(!getTroc(loc,8).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,8).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,8).get().getItem().getType().getTranslation()
                    + " - " + getTroc(loc,8).get().getPrice() + "  -9"));
            c.setScore(9);
        }
        
        scoreboard.addObjective(obj);
        scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);

        player.setScoreboard(scoreboard);

    }
    
    /**
     * 
     * @param transact
     * @param qte
     * @param price 
     * @param loc 
     * @param item 
     * @return  
     */
    public Optional<ItemStack> setItemTroc(EnumTransactType transact, int qte, double price, String loc, ItemStackSnapshot item){       
        ItemStack itemTroc = item.createStack();
        itemTroc.setQuantity(1);
        LoreData itemData = itemTroc.getOrCreate(LoreData.class).get();
        String displayName = "&2" + transact.toString() + " &0:&e" + price;
        
        itemTroc.offer(Keys.DISPLAY_NAME, MESSAGE(displayName));     
        List<Text> itemLore = itemData.lore().get();
        itemLore.add(MESSAGE("&b" + item.getTranslation().get()));
        if(transact.equals(EnumTransactType.BUY)){
        itemLore.add(MESSAGE("&bBesoin Qte : &9" + qte));}
        DataTransactionResult dataTransactionResult = itemTroc.offer(Keys.ITEM_LORE, itemLore);

        if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(itemTroc); 
        } else {
            return Optional.empty();
        }
    }
    
    /**
     * Change l'info Qte dans le lore de l'itemTroc
     * @param troc
     * @param transact
     * @param qte
     * @param price 
     * @param loc 
     * @return  
     */
    public Optional<ItemStack> setItemTroc(Troc troc, EnumTransactType transact, int qte, double price, String loc){       
        
        ItemStack itemTroc = troc.getItem().createStack();
        LoreData itemData = itemTroc.getOrCreate(LoreData.class).get();
        String displayName = "&2" + transact.toString() + " &0:&e" + price;
        
        itemTroc.offer(Keys.DISPLAY_NAME, MESSAGE(displayName));     
        List<Text> itemLore = itemData.lore().get();
        itemLore.add(MESSAGE("&b" + itemTroc.getTranslation().get()));
        if(transact.equals(EnumTransactType.BUY)){
            itemLore.add(MESSAGE("&bBesoin Qte : &9" + qte));
        }
        DataTransactionResult dataTransactionResult = itemTroc.offer(Keys.ITEM_LORE, itemLore);

        if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(itemTroc); 
        } else {
            return Optional.empty();
        }
    }
    
    /**
     * Retourne True si la position est un coffre Troc
     * @param location
     * @return 
     */
    public boolean hasTroc(Location<World> location){
        try {
            String loc = DeSerialize.location(location);
            trocNode = MANAGER.load();
            ConfigurationNode node = trocNode.getNode(loc);
            return !node.isVirtual();
        } catch (IOException ex) {
            Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * retourne l'emplacement du coffre Troc au format String : world:x:y:z
     * @param is ItemStack
     * @return String Location
     */
    public Optional<String> getStringLoc(ItemStackSnapshot is){
        plugin.getLogger().info(" - " + is.get(Keys.ITEM_LORE).isPresent());
        if(is.get(Keys.ITEM_LORE).isPresent()){
            Optional<List<Text>> ist = is.get(Keys.ITEM_LORE);
                if(ist.get().get(2).toPlain().contains("Qte")){
                    return Optional.of(ist.get().get(3).toText().toPlain());
                }else{
                    return Optional.of(ist.get().get(2).toText().toPlain());
                }
        }
        return Optional.empty();
    }
    
    /**
     * Retourne le slot de l'item en transaction
     * @param loc Emplacement du chest Troc au format string Location world:x:y:z
     * @param item ItemStackSnapshot a tester
     * @return retourne la position Slot du chest de 0 a 9, retourne -1 si aucune valeur
     */
    public int getSlot(String loc, ItemStackSnapshot item){      
        if(getTroc(loc,0).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,0).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,0).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,0).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,0).get().getItem().createStack(), item.createStack()) == 0)return 0;
        }
        if(getTroc(loc,1).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,1).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,1).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,1).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,1).get().getItem().createStack(), item.createStack()) == 0)return 1;
        }
        if(getTroc(loc,2).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,2).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,2).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,2).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,2).get().getItem().createStack(), item.createStack()) == 0)return 2;
        }
        if(getTroc(loc,3).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,3).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,3).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,3).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,3).get().getItem().createStack(), item.createStack()) == 0)return 3;
        }
        if(getTroc(loc,4).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,4).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,4).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,4).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,4).get().getItem().createStack(), item.createStack()) == 0)return 4;
        }
        if(getTroc(loc,5).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,5).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,5).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,5).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,5).get().getItem().createStack(), item.createStack()) == 0)return 5;
        }
        if(getTroc(loc,6).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,6).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,6).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,6).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,6).get().getItem().createStack(), item.createStack()) == 0)return 6;
        }
        if(getTroc(loc,7).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,7).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,7).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,7).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,7).get().getItem().createStack(), item.createStack()) == 0)return 7;
        }
        if(getTroc(loc,8).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,8).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,8).get().getItem().createStack(), item.createStack()) == 0
            && sm.getItemID(getTroc(loc,8).get().getItem()).equals(sm.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,8).get().getItem().createStack(), item.createStack()) == 0)return 8;
        }
        return -1;
    }
    
    /**
     * Retourne l'inventaire du chest Troc
     * @param troc
     * @return 
     */
    public Optional<Inventory> getInventory(Troc troc){
        Optional<Location<World>> locChest = DeSerialize.getLocation(troc.getLoc());
        if(locChest.isPresent()){
            Optional<TileEntity> chestBlock = locChest.get().getTileEntity();
            Optional<Chest> chest = Optional.of((Chest)chestBlock.get());
            Optional<Inventory> chestTroc;
                
            if(chest.isPresent()){
                if(chest.get().getDoubleChestInventory().isPresent()){
                    chestTroc = chest.get().getDoubleChestInventory();
                }else{
                    chestTroc = Optional.of(chest.get().getInventory());
                }
            }else{
                return Optional.empty();
            }
            return chestTroc;
        }
        return Optional.empty();
    }
    
    /**
     * Ajoute un itemTroc dans le chestTroc
     * @param player joueur createur de l'itemTroc
     * @param chestTroc Inventory chestTroc
     * @param slot position Slot
     * @param type transaction achat/BUY ou vente/SALE
     * @param price prix de l'item
     * @param qte quantite maxi si achat/BUY
     * @param item item a ajouter
     * @return boolean
     */
    public boolean AddTroc(Player player, Inventory chestTroc, int slot, EnumTransactType type, double price, int qte, ItemStackSnapshot item) {
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());      
        String[] parts = chestTroc.getName().get().split(" ");
        String locTroc = parts[1];
        String ownerTroc = parts[2];
        int idGuild = Integer.valueOf(parts[3]);

        if(!ownerTroc.equalsIgnoreCase(player.getName()) && !ownerTroc.equalsIgnoreCase("LIBRE") && idGuild == 0){
            player.sendMessage(MESSAGE("&eOperation impossible, ce troc appartient a : " + getAPlayer(ownerTroc)));
            return false;
        }
        if(idGuild != 0 && aplayer.getID_guild() != idGuild){
            player.sendMessage(MESSAGE("&eOperation impossible, ce troc appartient a la guild : " + Data.getGuild(idGuild)));
            return false;
        }
 
        Troc troc;
        int index = 0;         
        for (Inventory inv : chestTroc.slots()) {
            if (slot == index){
                Optional<ItemStack> is = setItemTroc(type, qte, price, locTroc, item);
                if(!is.isPresent()){
                    player.sendMessage(MESSAGE("&4Creation de l'itemTroc impossible !"));
                    return false;
                }
                inv.set(is.get());
                troc = new Troc(locTroc, index, type, qte, price, item,
                player.getName(),player.getIdentifier(),aplayer.getID_guild());
                save(troc);
                player.sendMessage(MESSAGE("&eItem troc cr\351\351 avec succes !"));
                return true;    
            }
            index = index + 1;
            if(index == 9){
                player.sendMessage(MESSAGE("&eTroc complet "));
            } 
        }
        return true; 
    }
    
    /**
     * Definit un chestTroc
     * @param locTroc position au format Location String "World:x;y;z"
     * @param owner Nom du proprietaire du coffre
     * @param idGuild ID de la guild
     * @return boolean
     */
    public boolean setChestTroc(String locTroc, String owner, int idGuild){                  
        Optional<Location<World>> locChest1 = DeSerialize.getLocation(locTroc);
        if (locChest1.isPresent()){
            Location<World> locChest2;
            Optional<TileEntity> chestBlock = locChest1.get().getTileEntity();
            TileEntity tileChest = chestBlock.get();
            String chestName = "&l&3TROC&r " + locTroc + " " + owner + " " + idGuild;
            if(owner.equalsIgnoreCase("LIBRE"))chestName = "&l&aTROC&r " + locTroc + " " + owner + " " + idGuild;
            tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                
            if(sm.locDblChest(locChest1.get()).isPresent()){
                locChest2 = sm.locDblChest(locChest1.get()).get();
                Optional<TileEntity> dblchestBlock = locChest2.getTileEntity();
                TileEntity tiledblChest = dblchestBlock.get();
                tiledblChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
            }
            //String ownerSign = owner;
            //if(idGuild != 0)ownerSign = getGuild(idGuild).getName();
            writeToChestSign(locTroc,owner,Optional.empty());
            return true;
        }
        return false;
    }
    
    public ItemStack getIS(){
        ItemStack notroc = ItemStack.builder().itemType(ItemTypes.BARRIER).build();
        LoreData itemData = notroc.getOrCreate(LoreData.class).get();            
        List<Text> itemLore = itemData.lore().get();
        itemLore.add(MESSAGE("&eEmplacement libre"));
        itemLore.add(MESSAGE("&bclique ici avec ton item pour l'ajouter"));
        notroc.offer(Keys.ITEM_LORE, itemLore);
        return notroc;            
    }
    
    /**
     * Ouvre un livre pour l'acheminement de la creation d'un itemTroc
     * @param inv le chestTroc  
     * @param slot emplacement Slot
     * @param player joueur
     * @param item ItemStackSnapshot
     */
    public void sendBookSelectTransactType(Inventory inv, int slot, Player player, ItemStackSnapshot item){
        BookView.Builder bv = BookView.builder()
            .author(MESSAGE("&bADMIN"))
            .title(MESSAGE("&bTROC"));
                
        Text text = 
                Text.builder()
                .append(MESSAGE("&l&4Clique sur le type de troc souhait\351\n\n"))
                .append(MESSAGE("&8Item : &1" + item.getType().getTranslation().get() + "\n\n"))
                .append(MESSAGE("&l&4type de transaction ? :" + "\n"))
                .append(Text.builder()
                .append(MESSAGE("&l&1-  " + EnumTransactType.SALE + "\n"))
                .onClick(TextActions.executeCallback(CB_TROC.callSelectPrix(inv,slot,SALE,0, item)))
                .onHover(TextActions.showText(MESSAGE("&eClique ici pour un item a la vente"))).build())
                .append(Text.builder()        
                .append(MESSAGE("&l&1-  " + EnumTransactType.BUY + "\n"))
                .onClick(TextActions.executeCallback(CB_TROC.callSelectPrix(inv,slot,BUY,0, item)))
                .onHover(TextActions.showText(MESSAGE("&eClique ici pour un item a l'achat"))).build())
     
                .build();
        bv.addPage(text);
        player.sendBookView(bv.build());   
    }
    
    /**
     * retourne True si le chestTroc est vide
     * @param loc position au format Location String "World:x;y;z"
     * @return 
     */
    public boolean chestTrocHasEmpty(String loc){
        
        return (getTroc(loc,0).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,1).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,2).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,3).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,4).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,5).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,6).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,7).get().getPlayerName().equals("LIBRE") &&
                getTroc(loc,8).get().getPlayerName().equals("LIBRE"));
    }
    
    public void writeToChestSign(String locTroc, String owner, Optional<String> guild){
        Optional<Location<World>> locChest1 = DeSerialize.getLocation(locTroc);
        if (locChest1.isPresent()){
            Optional<Location<World>> locChest2 = Optional.empty();
            Optional<Location<World>> locSign;
                            
            if(sm.locDblChest(locChest1.get()).isPresent())locChest2 = Optional.of(sm.locDblChest(locChest1.get()).get());                
            
            locSign = getLocationSignTroc(locChest1.get());
            if(!locSign.isPresent())locSign = getLocationSignTroc(locChest2.get());
            
            if(locSign.isPresent()){
                Optional<TileEntity> signBlock = locSign.get().getTileEntity();
                TileEntity tileSign = signBlock.get();
                Sign sign=(Sign)tileSign;
                Optional<SignData> opSign = sign.getOrCreate(SignData.class);
                String ownerTroc = "&l&3" + owner;
                //if(guild.isPresent())ownerTroc = "&l&9" + guild.get();
                if(owner.equalsIgnoreCase("LIBRE"))ownerTroc = "&l&4" + owner;
                SignData signData = opSign.get();
                List<Text> signTroc = new ArrayList<>();
                signTroc.add(MESSAGE("&l&8[TROC]"));
                signTroc.add(MESSAGE("&3" + ownerTroc));
                signTroc.add(MESSAGE("&8Pour des infos :"));
                signTroc.add(MESSAGE("&8Clique droit ici"));
                signData.set(Keys.SIGN_LINES,signTroc );
                sign.offer(signData);   
            }
        }
        
    }
    
    private Optional<Location<World>> getLocationSignTroc(Location<World> locChest){
        Optional<Location<World>> locSign = Optional.empty();       
            
        if(locChest.getBlockRelative(Direction.NORTH).getBlockType().equals(WALL_SIGN))locSign = Optional.of(locChest.getBlockRelative(Direction.NORTH));
        if(locChest.getBlockRelative(Direction.EAST).getBlockType().equals(WALL_SIGN))locSign = Optional.of(locChest.getBlockRelative(Direction.EAST));
        if(locChest.getBlockRelative(Direction.SOUTH).getBlockType().equals(WALL_SIGN))locSign = Optional.of(locChest.getBlockRelative(Direction.SOUTH));
        if(locChest.getBlockRelative(Direction.WEST).getBlockType().equals(WALL_SIGN))locSign = Optional.of(locChest.getBlockRelative(Direction.WEST));
        
        return locSign;
    }
}
