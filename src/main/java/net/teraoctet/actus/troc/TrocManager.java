package net.teraoctet.actus.troc;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import static org.spongepowered.api.item.ItemTypes.BARRIER;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackComparators;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
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
     * @throws IOException
     * @throws ObjectMappingException 
     */
    public void save(Troc troc) throws IOException, ObjectMappingException{
        trocNode = MANAGER.load();
        trocNode.getNode(troc.getLoc(), String.valueOf(troc.getSlot())).setValue(TOKEN_CONFIG, troc);
        MANAGER.save(trocNode);
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
     * 
     * @param loc
     * @param slot
     * @param qteMax
     * @return 
     */
    public boolean setBuyQteMax(String loc, int slot,int qteMax){
        Optional<Troc> troc = getTroc(loc,slot);
        if(troc.isPresent()){
            troc.get().setQteMax(qteMax);
            try {
                save(troc.get());
            } catch (IOException | ObjectMappingException ex) {
                Logger.getLogger(TrocManager.class.getName()).log(Level.SEVERE, null, ex);
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
                    + ": &e" + getTroc(loc,0).get().getItem().getType().getTranslation().get(Locale.FRENCH)
                    + " - " + getTroc(loc,0).get().getPrice() + "  -1"));
            c.setScore(1);
        }

        if(!getTroc(loc,1).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,1).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,1).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,1).get().getPrice() + "  -2"));
            c.setScore(2);
        }

        if(!getTroc(loc,2).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,2).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,2).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,2).get().getPrice() + "  -3"));
            c.setScore(3);
        }

        if(!getTroc(loc,3).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,3).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,3).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,3).get().getPrice() + "  -4"));
            c.setScore(4);
        }

        if(!getTroc(loc,4).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,4).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,4).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,4).get().getPrice() + "  -5"));
            c.setScore(5);
        }

        if(!getTroc(loc,5).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,5).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,5).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,5).get().getPrice() + "  -6"));
            c.setScore(6);
        }

        if(!getTroc(loc,6).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,6).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,6).get().getItem().getType().getTranslation().get(Locale.FRENCH)  
                    + " - " + getTroc(loc,6).get().getPrice() + "  -7"));
            c.setScore(7);
        }

        if(!getTroc(loc,7).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,7).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,7).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,7).get().getPrice() + "  -8"));
            c.setScore(8);
        }

        if(!getTroc(loc,8).get().getItem().getType().equals(BARRIER)){
            c = obj.getOrCreateScore(MESSAGE("&9" + getTroc(loc,8).get().getTransactType().toString()
                    + ": &e" + getTroc(loc,8).get().getItem().getType().getTranslation().get(Locale.FRENCH) 
                    + " - " + getTroc(loc,8).get().getPrice() + "  -9"));
            c.setScore(9);
        }
        
        scoreboard.addObjective(obj);
        scoreboard.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);

        player.setScoreboard(scoreboard);

    }
    
    /**
     * 
     * @param player
     * @param name
     * @param transact
     * @param qte
     * @param price 
     * @return  
     */
    public Optional<ItemStack> setItemTroc(Player player, String name, EnumTransactType transact, int qte, double price, String loc){       
        Optional<ItemStack> itemTroc = player.getItemInHand(HandTypes.MAIN_HAND);
        if(!itemTroc.isPresent()){
            player.sendMessage(MESSAGE("&bAucun item dans la main !?"));
            return Optional.empty();
        }
        LoreData itemData = itemTroc.get().getOrCreate(LoreData.class).get();
        String displayName = "&9" + transact.toString() + " :&e" + price;
        
        itemTroc.get().offer(Keys.DISPLAY_NAME, MESSAGE(displayName));     
        List<Text> itemLore = itemData.lore().get();
        itemLore.add(MESSAGE("&e" + name));
        itemLore.add(MESSAGE("&b" + itemTroc.get().getType().getName()));
        if(transact.equals(EnumTransactType.BUY)){
        itemLore.add(MESSAGE("&bQte : &9" + qte));}
        itemLore.add(MESSAGE("&7" + loc));
        DataTransactionResult dataTransactionResult = itemTroc.get().offer(Keys.ITEM_LORE, itemLore);

        if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(itemTroc.get()); 
        } else {
            player.sendMessage(MESSAGE("&bErreur de creation du lore"));
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
     * Retourne True si l'item en transaction est bien en offre de transaction TROC
     * @param loc Emplacement du chest Troc au format string Location world:x:y:z
     * @param item ItemStackSnapshot a tester
     * @return retourne la position Slot du chest de 0 a 9, retourne -1 si aucune valeur
     */
    public int getSlot(String loc, ItemStackSnapshot item){      
        if(getTroc(loc,0).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,0).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,0).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,0).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,0).get().getItem().createStack(), item.createStack()) == 0)return 0;
        }
        if(getTroc(loc,1).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,1).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,1).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,1).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,1).get().getItem().createStack(), item.createStack()) == 0)return 1;
        }
        if(getTroc(loc,2).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,2).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,2).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,2).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,2).get().getItem().createStack(), item.createStack()) == 0)return 2;
        }
        if(getTroc(loc,3).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,3).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,3).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,3).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,3).get().getItem().createStack(), item.createStack()) == 0)return 3;
        }
        if(getTroc(loc,4).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,4).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,4).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,4).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,4).get().getItem().createStack(), item.createStack()) == 0)return 4;
        }
        if(getTroc(loc,5).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,5).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,5).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,5).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,5).get().getItem().createStack(), item.createStack()) == 0)return 5;
        }
        if(getTroc(loc,6).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,6).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,6).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,6).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,6).get().getItem().createStack(), item.createStack()) == 0)return 6;
        }
        if(getTroc(loc,7).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,7).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,7).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,7).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,7).get().getItem().createStack(), item.createStack()) == 0)return 7;
        }
        if(getTroc(loc,8).isPresent()){
            if(ItemStackComparators.TYPE.compare(getTroc(loc,8).get().getItem().createStack(), item.createStack()) == 0 
            && ItemStackComparators.PROPERTIES.compare(getTroc(loc,8).get().getItem().createStack(), item.createStack()) == 0
            && serverManager.getItemID(getTroc(loc,8).get().getItem()).equals(serverManager.getItemID(item))
            && ItemStackComparators.ITEM_DATA.compare(getTroc(loc,8).get().getItem().createStack(), item.createStack()) == 0)return 8;
        }
        return -1;
    }
}
