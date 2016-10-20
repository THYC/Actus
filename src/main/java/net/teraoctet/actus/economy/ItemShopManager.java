package net.teraoctet.actus.economy;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class ItemShopManager {
    
    public static File file = new File("config/actus/itemShop.conf");
    public static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode shop = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void init() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            shop = manager.load();
        } catch (IOException e) {}	
    }
    
    public static Object serializeItemStack(ItemStack itemStack){
        ConfigurationNode node = DataTranslators.CONFIGURATION_NODE.translate(itemStack.toContainer());
        return node.getValue();
    }

    public static Optional<ItemStack> getItemStack(ConfigurationNode node){
        DataView view = DataTranslators.CONFIGURATION_NODE.translate(node);
        view = (DataView) view.get(DataQuery.of(String.valueOf("item"))).get();
        return Sponge.getDataManager().deserialize(ItemStack.class, view);
    }
        
    /**
     * Sauvegarde un ItemShop
     * @param uuid UUID de l'ItemFrame/ArmorStand
     * @param itemShop object ItemShop à sauvegarder
     * @return Boolean 
     * @throws IOException Exception
     */
    public boolean saveShop(UUID uuid, ItemShop itemShop) throws IOException{
        shop.getNode(uuid.toString(),"item").setValue(serializeItemStack(itemShop.getItemStack()));
        shop.getNode(uuid.toString(),"transacttype").setValue(itemShop.getTransactType());
        shop.getNode(uuid.toString(),"price").setValue(itemShop.getPrice());
        shop.getNode(uuid.toString(),"qte").setValue(itemShop.getQte());
        manager.save(shop);
        return true;
    }
        
    /**
     * Retourne un ItemShop 
     * @param uuid UUID de l'ItemFrame/ArmorStand
     * @return ItemShop
     */
    public Optional<ItemShop> getItemShop(UUID uuid){
	ConfigurationNode node = shop.getNode(uuid.toString());
	Optional<ItemStack> optItemStack = getItemStack(node);
        if(optItemStack.isPresent()){
            String transactType = node.getNode("transacttype").getString();
            Double price = node.getNode("price").getDouble();
            int qte = node.getNode("qte").getInt();
            ItemShop is = new ItemShop(optItemStack.get(),transactType,price,qte);
            return Optional.of(is);
        }
        return Optional.empty(); 
    }
        
    /**
     * Supprime un object ItemShop
     * @param uuid UUID de l'ItemFrame/ArmorStand
     * @throws IOException Exception
     */
    public void delItemShop(UUID uuid) throws IOException{
	shop.removeChild(uuid.toString());
        manager.save(shop);
        shop = manager.load();
    }
    
    /**
     * Retourne TRUE si Entity contient un ItemShop
     * @param uuid UUID de l'ItemFrame/ArmorStand
     * @return Boolean
     */
    public boolean hasShop(UUID uuid){
        return !shop.getNode(uuid.toString()).isVirtual();
    }
    
    /**
     * Retourne TRUE si l'Objet ItemStack est un CoinPurses
     * @param coinPurses Objet ItemStack
     * @return Boolean
     */
    public boolean hasCoinPurses(ItemStack coinPurses){
        if(coinPurses.get(Keys.DISPLAY_NAME).isPresent()){
            return coinPurses.get(Keys.DISPLAY_NAME).get().toPlain().equalsIgnoreCase("CoinPurses");	
        }
        return false;
    }
    
    /**
     * Retourne un object ItemStack CoinPurses
     * @param player player recevant l'object ItemStack CoinPurses
     * @param credit Somme a crediter
     * @return un object ItemStack CoinPurses
     */
    public Optional<ItemStack> CoinPurses(Player player, double credit){
        ItemStack coinPurses = ItemStack.builder().itemType(ItemTypes.PAPER).build();
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();
        coinPurses.offer(Keys.DISPLAY_NAME, MESSAGE("&eCoinPurses"));
                 
	List<Text> newLore = loreD.lore().get();
        newLore.add(MESSAGE("&2Owner: &e" + player.getName()));
        newLore.add(MESSAGE("&2Credit: &b" + credit));
	DataTransactionResult dataTransactionResult = coinPurses.offer(Keys.ITEM_LORE, newLore);

	if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(coinPurses);
        } else {
            return Optional.empty();
        }				
    }
    
    /**
     * Retourne l'objet CoinPurses crédité de la somme
     * @param credit Somme type Double a crédité sur l'objet CoinPurses
     * @param coinPurses Object ItemStack CoinPurses a crediter
     * @return ItemStack CoinPurses
     */
    public Optional<ItemStack> addCoin(double credit, ItemStack coinPurses){
        //APlayer aplayer = getAPlayer(player.getIdentifier());
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();                
	List<Text> lore = loreD.lore().get();
        String[] arg = lore.get(1).toPlain().split(":");
        Double coin = Double.valueOf(arg[1]);
        coin = coin + credit;
        lore.set(1, MESSAGE("&2Credit: &b" + coin));
        
	DataTransactionResult dataTransactionResult = coinPurses.offer(Keys.ITEM_LORE, lore);
        if(dataTransactionResult.isSuccessful()){
            return Optional.of(coinPurses);
        }
        return Optional.empty();				
    }
    
    /**
     * Retourne l'objet CoinPurses débité de la somme
     * @param debit Somme type Double a debité de l'objet CoinPurses
     * @param coinPurses Object ItemStack CoinPurses a debiter
     * @return ItemStack CoinPurses
     */
    public Optional<ItemStack> removeCoin(double debit, ItemStack coinPurses){
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();                
	List<Text> lore = loreD.lore().get();
        String[] arg = lore.get(1).toPlain().split(":");
        Double coin = Double.valueOf(arg[1]);
        if(debit <= coin){
            coin = (round((coin - debit)*100.0))/100.0;
            lore.set(1, MESSAGE("&2Credit: &b" + coin));
            DataTransactionResult dataTransactionResult = coinPurses.offer(Keys.ITEM_LORE, lore);
            if(dataTransactionResult.isSuccessful()){
                return Optional.of(coinPurses);
            }
        }
        return Optional.empty();	
    }
    
    /**
     * Retourne la somme totale du CoinPurses
     * @param coinPurses Object ItemStack CoinPurses a debiter
     * @return Double
     */
    public Double getQteCoin(ItemStack coinPurses){
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();                
	List<Text> lore = loreD.lore().get();
        String[] arg = lore.get(1).toPlain().split(":");
        Double coin = Double.valueOf(arg[1]);
        return coin;	
    }
    
    /**
     * Retourne une liste des ItemShop actif
     * @return Liste ItemShop
     */
    public List<String>getListItemShop(){
        List<String> list = new ArrayList();
        shop.getChildrenMap().entrySet().stream().forEach((node) -> {
            list.add(node.getKey().toString());
        });
        return list;
    }
}
