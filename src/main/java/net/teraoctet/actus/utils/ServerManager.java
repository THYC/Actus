package net.teraoctet.actus.utils;

import com.flowpowered.math.vector.Vector3d;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Consumer;
import static net.teraoctet.actus.Actus.mapCountDown;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Config.DAYS_BEFORE_MOVE_GRAVE;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ServerManager {
    
    public ServerManager(){}
    
    /**
     * retourne l'objet Player
     * @param player nom du joueur à retourner
     * @return Player
     */
    public Optional<Player> getPlayer(String player) {
        Optional<Player> onlinePlayer = Sponge.getServer().getPlayer(player);
        if (onlinePlayer.isPresent()) {
            return onlinePlayer;
        }
        Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
        return userStorage.get().get(player).get().getPlayer();
    }
        
    /**
     * retourne l'idetifier UUID du joueur
     * @param player nom du joueur à retourner
     * @return String
     */
    public Optional<String> getPlayerUUID(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getIdentifier());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    /**
     * retourne le GameProfile du joueur
     * @param player nom du joueur à retourner
     * @return GameProfile
     */
    public Optional<GameProfile> getPlayerProfile(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getProfile());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    /**
     * retourne 'inventaire du joueur à partir du GameProfile
     * @param player nom du joueur à retourner
     * @return Inventory
     */
    public Optional<Inventory> getPlayerInventory(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getInventory());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    /**
     * retourne True si le joueur est en ligne
     * @param playerName nom du joueur à controler
     * @return Boolean
     */
    public static boolean isOnline(String playerName){
        
        boolean online = false;
        for (Player player : Sponge.getServer().getOnlinePlayers()) {
            if(player.getName().toLowerCase().equals(playerName.toLowerCase())){ online = true;}
        }
        return online;
    }

    public static void broadcast(Text text) {
        getGame().getServer().getBroadcastChannel().send(text);
    }
              
    /**
     * double les quotes contenu dans le message
     * @param message chaine du message à modifier
     * @return String
     */
    public String quoteToSQL(String message){
        if(message.contains("'")){
            message = message.replace("'", "''");
        }
        return message;
    }
    
    /**
     * retourne la date au format dd MMM yyyy HH:mm:ss
     * @return String
     */
    public String dateToString(){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(now);
        cal.add(Calendar.YEAR, 1900);
        
	SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+02"));
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * retourne la date au format dd-MM-yy_HH-mm
     * @return String
     */
    public String dateShortToString(){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(now);
        cal.add(Calendar.YEAR, 1900);
        
	SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yy_HH-mm");
	simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+02"));
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * retourne la date au format ss
     * @return Long
     */
    public Long dateToLong(){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        //cal.add(Calendar.YEAR, 1900);
        
	return cal.getTimeInMillis();
    }
    
    /**
     * retourne la date incrementé de DAYS_BEFORE_MOVE_GRAVE
     * @param datelong
     * @return 
     */
    public long addDate(Long datelong){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datelong);
        cal.add(Calendar.DATE, DAYS_BEFORE_MOVE_GRAVE());
        return cal.getTime().getTime();
    }
    
    /**
     * Retourne TimeInMillis au format "dd MMM yyyy HH:mm:ss"
     * @param date de type Long en Milliseconde
     * @return String
     */
    public String longToDateString(long date){
        Calendar cal = Calendar.getInstance();        
	cal.setTimeInMillis(date);
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+02"));
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * Retourne TimeInMillis au format "dd MMM yyyy HH:mm:ss"
     * @param date de type Double en Milliseconde
     * @return String
     */
    public String longToDateString(double date){
        Calendar cal = Calendar.getInstance(); 
        Long dateL = (long)date;
	cal.setTimeInMillis(dateL);
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+02"));
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * Retourne TimeInMillis au format "dd-MM-yy-HH-mm"
     * @param date de type Double en Milliseconde
     * @return String
     */
    public String longToDateString2(double date){
        Calendar cal = Calendar.getInstance(); 
        Long dateL = (long)date;
	cal.setTimeInMillis(dateL);
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
	simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+02"));
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * Retourne TimeInMillis au format "HH:mm:ss"
     * @param millis
     * @return 
     */
    public String longToTime(Long millis){
        Calendar cal = Calendar.getInstance(); 
	cal.setTimeInMillis(millis);
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm:ss");
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * Téléporte un joueur et enregistre ces prédentes coordonnées dans le param LastLocation 
     * @param player nom du joeur a téléporter
     * @param worldS nom du monde d'arrivée
     * @param X coordonnée X
     * @param Y coordonnée Y
     * @param Z coordonnée Z
     * @param msg message affiché en retour
     * @return Boolean TRUE si le joueur a bien été téléporté
     */
    public boolean teleport(Player player, String worldS, int X, int Y, int Z, Text msg){
                 
        if(Config.COOLDOWN_TO_TP()>0){
            CooldownToTP tp = new CooldownToTP(player,worldS, X, Y, Z, Optional.ofNullable(msg));
            tp.run();
            mapCountDown.put(player, tp);
            return tp.getResult();
        }else{
            Location lastLocation = player.getLocation();
            Optional<World> w = getGame().getServer().getWorld(worldS);
            if(w.isPresent()){
                player.transferToWorld(w.get(), new Vector3d(X, Y, Z));
                APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                aplayer.setLastposition(DeSerialize.location(lastLocation));
                aplayer.update();
                return true;
            }else{
                return false;
            }
  
        }
    }
    
    /**
     * Téléporte un joueur et enregistre ces prédentes coordonnées dans le param LastLocation 
     * @param player nom du joeur a téléporter
     * @param worldS nom du monde d'arrivée
     * @param X coordonnée X
     * @param Y coordonnée Y
     * @param Z coordonnée Z
     * @param entity
     * @return Boolean TRUE si le joueur a bien été téléporté
     */
    public boolean teleport(Player player, String worldS, int X, int Y, int Z, Optional<Entity> entity){
                 
        if(Config.COOLDOWN_TO_TP()>0){
            CooldownToTP tp = new CooldownToTP(player,entity,worldS, X, Y, Z);
            tp.run();
            mapCountDown.put(player, tp);
            return tp.getResult();
        }else{
            Location lastLocation = player.getLocation();
            Optional<World> w = getGame().getServer().getWorld(worldS);
            if(w.isPresent()){
                player.transferToWorld(w.get(), new Vector3d(X, Y, Z));
                APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                aplayer.setLastposition(DeSerialize.location(lastLocation));
                aplayer.update();
                return true;
            }else{
                return false;
            }
  
        }
    }
        
    /**
     * Téléporte un joueur sur un autre joueur et enregistre ces prédentes coordonnées dans le param LastLocation 
     * @param player nom du joueur a téléporter
     * @param target nom du joueur de destination
     * @return Boolean TRUE si le joueur a bien été téléporté
     */
    public boolean teleport(Player player,Player target){
                 
        if(Config.COOLDOWN_TO_TP()>0){
            CooldownToTP tp = new CooldownToTP(player,target.getWorld().getName(), 
                    target.getLocation().getBlockX(), target.getLocation().getBlockY(),target.getLocation().getBlockZ());
            tp.run();
            mapCountDown.put(player, tp);
            return tp.getResult();
        }else{
            Location lastLocation = player.getLocation();
            Optional<World> w = Optional.of(target.getWorld());
            if(w.isPresent()){
                player.transferToWorld(w.get(), new Vector3d(target.getLocation().getBlockX(), 
                target.getLocation().getBlockY(),target.getLocation().getBlockZ()));
                APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                aplayer.setLastposition(DeSerialize.location(lastLocation));
                aplayer.update();
                return true;
            }else{
                return false;
            }
        }
    }
        
    public void sendCommand(Player player, String cmd){
        getGame().getCommandManager().get(cmd, player);
    }
    
    /**
     * Retourne les coordonnées d'un possible coffre attenant 
     * @param location Location du coffre a ajouter
     * @return loction du coffre existant
     */
    public Optional<Location<World>> locDblChest(Location<World> location) {
        TileEntity chest = location.getTileEntity().get();
        final Set<Direction> directions = chest.get(Keys.CONNECTED_DIRECTIONS).orElse(new HashSet<>());
            
        for (Direction direction : directions) {
            if (chest.getLocation().getBlockRelative(direction).getBlock().getType().equals(chest.getBlock().getType())) {
                return Optional.of(chest.getLocation().getBlockRelative(direction));
            }
        }
        return Optional.empty();
    }
    
    /**
     * Retourne le sous type de l'item
     * @param is ItemStack
     * @return 
     */
    public Optional<Integer> getItemID(ItemStack is){
        DataContainer container = is.toContainer();
	DataQuery query = DataQuery.of('/', "UnsafeDamage");
        if(container.get(query).isPresent()){
            return Optional.of(Integer.valueOf(container.get(query).get().toString()));
        }
        return Optional.empty();
    }
    
    /**
     * Retourne le sous type de l'item
     * @param is ItemStackSnapshot
     * @return 
     */
    public Optional<Integer> getItemID(ItemStackSnapshot is){
        DataContainer container = is.toContainer();
	DataQuery query = DataQuery.of('/', "UnsafeDamage");
        if(container.get(query).isPresent()){
            return Optional.of(Integer.valueOf(container.get(query).get().toString()));
        }
        return Optional.empty();
    }
    
    /**
     * teleporte un joueur au coordonnée String location
     * @param target joueur a téléporter
     * @param location String location au format world:x:y:z
     * @return 
     */
    public Consumer<CommandSource> callTP(Optional<Player> target, String location) {
	return (CommandSource src) -> {
            Optional<Location<World>> loc = DeSerialize.getLocation(location);
            if(loc.isPresent()){
                Player p = (Player)src;
                if(target.isPresent())p = target.get();
                Location lastLocation = p.getLocation();
                APlayer aplayer = getAPlayer(p.getUniqueId().toString());
                aplayer.setLastposition(DeSerialize.location(lastLocation));
                aplayer.update();
                p.transferToWorld(loc.get().getExtent(), new Vector3d(loc.get().getBlockX(), 
                loc.get().getBlockY(),loc.get().getBlockZ()));           
            }         
        };
    }
}
