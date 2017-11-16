package net.teraoctet.actus;

import com.flowpowered.math.vector.Vector3i;
import net.teraoctet.actus.plot.PlotListener;
import net.teraoctet.actus.plot.PlotManager;
import net.teraoctet.actus.portal.PortalListener;
import net.teraoctet.actus.portal.PortalManager;
import net.teraoctet.actus.utils.Config;
import net.teraoctet.actus.utils.Data;
import net.teraoctet.actus.utils.MessageManager;
import net.teraoctet.actus.world.WorldListener;
import net.teraoctet.actus.world.WorldManager;
import net.teraoctet.actus.commands.CommandManager;
import net.teraoctet.actus.shop.ItemShopManager;
import net.teraoctet.actus.shop.ShopListener;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.utils.CooldownToTP;
import net.teraoctet.actus.utils.ServerManager;
import net.teraoctet.actus.utils.TPAH;

import com.google.inject.Inject;
import com.sk89q.worldedit.IncompleteRegionException;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.teraoctet.actus.bookmessage.CallBackBook;
import net.teraoctet.actus.bookmessage.ConfigBook;
import net.teraoctet.actus.commands.grave.CallBackGrave;
import net.teraoctet.actus.commands.plot.CallBackPlot;
import net.teraoctet.actus.grave.ConfigGrave;
import net.teraoctet.actus.grave.ConfigGraveyard;
import net.teraoctet.actus.inventory.ConfigInventory;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.grave.GraveListener;
import net.teraoctet.actus.player.PlayerListener;
import net.teraoctet.actus.player.PlayerManager;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.trace.TraceListener;
import net.teraoctet.actus.trace.TraceManager;
import net.teraoctet.actus.troc.TrocListener;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import net.teraoctet.actus.warp.WarpManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.channel.MessageChannel;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.session.SessionManager;
import net.teraoctet.actus.troc.ItemTransact;
import net.teraoctet.actus.troc.TrocManager;
import org.spongepowered.api.world.World;

@Plugin(
    id = "actus", 
    name = "Actus", 
    version = "0.1.0",
    url = "http://actus.teraoctet.net",
    authors = {
        "thyc82","Votop"
    },
    description = "Server management plugin",
    dependencies=@Dependency(id = "worldedit", optional = true)
)

public class Actus {
     
    @Inject private Logger logger;
    
    public static ServerManager serverManager = new ServerManager();
    public static PlotManager plotManager = new PlotManager();
    public static PortalManager portalManager = new PortalManager();
    public static GuildManager guildManager = new GuildManager();
    public static ItemShopManager itemShopManager = new ItemShopManager();
    public static PlayerManager playerManager = new PlayerManager();
    public static WarpManager warpManager = new WarpManager();
    public static TraceManager traceManager = new TraceManager();
    public static WorldManager worldManager = new WorldManager();
    public static TrocManager trocManager = new TrocManager(); 
    public Logger getLogger(){return logger;}  
    public static Game game;
    public static PluginContainer plugin;
    public static Map<Player, CooldownToTP> mapCountDown = new HashMap<>();
    public static Map<Player,String>inputShop = new HashMap<>();
    public static Map<Player,Double>inputDouble = new HashMap<>();
    public static Map<Player,String>action = new HashMap<>();
    public static Map<Player, Map<Integer, ItemTransact>> TROC = new HashMap<>();
    public static final ArrayList<TPAH> ATPA = new ArrayList<>();
    public static Config config = new Config();
    public static ConfigBook configBook = new ConfigBook();
    public static ConfigInventory configInv = new ConfigInventory();
    public static ConfigGrave configGrave = new ConfigGrave();
    public static ConfigGraveyard configGraveyard = new ConfigGraveyard();
    public static final CallBackBook CB_BOOK = new CallBackBook();
    public static final CallBackPlot CB_PLOT = new CallBackPlot();
    public static final CallBackGrave CB_GRAVE = new CallBackGrave();
    public static EconomyService economyService;
    private static WorldEdit WEdit;
    
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;
    
    @Listener
    public void onServerInit(GameInitializationEvent event) throws ObjectMappingException {

        MessageChannel.TO_CONSOLE.send(MESSAGE("&b[ACTUS] &edevelopped by THYC and Votop ... Init..."));
        plugin = Sponge.getPluginManager().getPlugin("actus").get();  
        
        //Sponge.getRegistry().register(WorldGeneratorModifier.class, new OceanWorldGeneratorModifier());
                
        if(!init())plugin.getLogger().error("Erreur init");
                 
        getGame().getEventManager().registerListeners(this, new PlotListener());
        getGame().getEventManager().registerListeners(this, new PortalListener());
        getGame().getEventManager().registerListeners(this, new PlayerListener());
        getGame().getEventManager().registerListeners(this, new WorldListener());
        getGame().getEventManager().registerListeners(this, new ShopListener());
        getGame().getEventManager().registerListeners(this, new TraceListener());
        getGame().getEventManager().registerListeners(this, new GraveListener());
        getGame().getEventManager().registerListeners(this, new TrocListener());
        
        getGame().getCommandManager().register(this, new CommandManager().CommandActus, "actus");
	getGame().getCommandManager().register(this, new CommandManager().CommandKill, "kill", "tue");
	getGame().getCommandManager().register(this, new CommandManager().CommandSun, "sun", "soleil");
	getGame().getCommandManager().register(this, new CommandManager().CommandRain, "rain", "pluie");
	getGame().getCommandManager().register(this, new CommandManager().CommandStorm, "storm", "orage");
	getGame().getCommandManager().register(this, new CommandManager().CommandDay, "day", "timeday", "jour");
	getGame().getCommandManager().register(this, new CommandManager().CommandNight, "night", "timenight", "nuit");
        getGame().getCommandManager().register(this, new CommandManager().CommandPlot, "plot", "parcel", "parcelle", "p", "protege");
	getGame().getCommandManager().register(this, new CommandManager().CommandFly, "fly", "vole");
	getGame().getCommandManager().register(this, new CommandManager().CommandSetHome, "sethome", "homeset");
	getGame().getCommandManager().register(this, new CommandManager().CommandHome, "home", "maison");
        getGame().getCommandManager().register(this, new CommandManager().CommandDelhome, "delhome", "removehome");
	getGame().getCommandManager().register(this, new CommandManager().CommandBack, "back", "gsback", "reviens", "retour");
	getGame().getCommandManager().register(this, new CommandManager().CommandLevel, "level", "lev");
        getGame().getCommandManager().register(this, new CommandManager().CommandWorldCreate, "worldcreate", "createworld", "newworld");
	getGame().getCommandManager().register(this, new CommandManager().CommandWorldTP, "worldtp", "tpworld");
        getGame().getCommandManager().register(this, new CommandManager().CommandWorldLoad, "worldload");
        getGame().getCommandManager().register(this, new CommandManager().CommandClearinventory, "clearinventory", "ci", "clear");
        getGame().getCommandManager().register(this, new CommandManager().CommandInvsee, "invsee", "is");
        getGame().getCommandManager().register(this, new CommandManager().CommandPlayerinfo, "playerinfo", "pi", "info");
        getGame().getCommandManager().register(this, new CommandManager().CommandBroadcast, "broadcastmessage", "broadcast", "bm", "b");
        getGame().getCommandManager().register(this, new CommandManager().CommandFaction, "guild", "f", "guilde", "g", "horde");
	getGame().getCommandManager().register(this, new CommandManager().CommandTest, "test");
        getGame().getCommandManager().register(this, new CommandManager().CommandRocket, "rocket");
        getGame().getCommandManager().register(this, new CommandManager().CommandPortal, "portal", "portail", "pl", "po");
        getGame().getCommandManager().register(this, new CommandManager().CommandSkull, "skull", "head", "tete");
        getGame().getCommandManager().register(this, new CommandManager().CommandMagicCompass, "mc", "magic", "compass", "boussole");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignWrite, "write", "ecrire", "signwrite", "sw", "print");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignHelp, "signhelp", "sh", "shelp");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignPost, "signpost", "post", "poste", "spost");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignBank, "signbank", "sb", "sbank");
        getGame().getCommandManager().register(this, new CommandManager().CommandSetName, "setname", "sn", "dn");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopCreate, "shopcreate", "shopc");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopPurchase, "shoppurchase", "shopp");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopSell, "shopsell", "shops");
        getGame().getCommandManager().register(this, new CommandManager().CommandVanish, "vanish", "vh");
        getGame().getCommandManager().register(this, new CommandManager().CommandInvisible, "invisible", "invi", "ghost");
        getGame().getCommandManager().register(this, new CommandManager().CommandEnchant, "enchant");
        getGame().getCommandManager().register(this, new CommandManager().CommandRule, "rules", "rule", "rul");
        getGame().getCommandManager().register(this, new CommandManager().CommandBank, "bank", "banque", "bk");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopList, "shoplist", "shopl");
        getGame().getCommandManager().register(this, new CommandManager().CommandChest, "chest", "coffre", "lwc");
        getGame().getCommandManager().register(this, new CommandManager().CommandNPC, "npc");
        getGame().getCommandManager().register(this, new CommandManager().CommandSpawn, "spawn");
        getGame().getCommandManager().register(this, new CommandManager().CommandSetspawn, "setspawn", "spawnset");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignCmd, "signcmd", "cmd", "scmd");
        getGame().getCommandManager().register(this, new CommandManager().CommandButcher, "butcher", "massacre");
        getGame().getCommandManager().register(this, new CommandManager().CommandTPR, "tpr", "rtp");
        getGame().getCommandManager().register(this, new CommandManager().CommandTPThru, "tpthru", "tpt", "thru");
        getGame().getCommandManager().register(this, new CommandManager().CommandData, "data");
        getGame().getCommandManager().register(this, new CommandManager().CommandAS, "as", "armorstand");
        getGame().getCommandManager().register(this, new CommandManager().CommandMailBox, "mailbox", "mb", "bal", "mail");
        getGame().getCommandManager().register(this, new CommandManager().CommandPlotClaim, "claim");
        getGame().getCommandManager().register(this, new CommandManager().CommandWorld, "world", "aworld", "monde");
        getGame().getCommandManager().register(this, new CommandManager().CommandGrave, "grave", "tombe");
        getGame().getCommandManager().register(this, new CommandManager().CommandGraveyard, "graveyard", "crypte", "caveau", "cav", "cim");
        getGame().getCommandManager().register(this, new CommandManager().CommandTroc, "troc");
    }
        
    @Listener
    public void onDisable(GameStoppingServerEvent event) {
        game.getServer().getOnlinePlayers().stream().map((player) -> {
            APlayer aplayer = getAPlayer(player.getIdentifier());
            long timeConnect = serverManager.dateToLong()- PlayerManager.getFirstTime(player.getIdentifier());
            long onlineTime = (long)aplayer.getOnlinetime() + timeConnect;
            PlayerManager.removeFirstTime(player.getIdentifier());
            aplayer.setLastonline(serverManager.dateToLong());
            aplayer.setOnlinetime(onlineTime);
            return aplayer;
        }).forEach((aplayer) -> {
            aplayer.update();
        });
    	Data.commit();
    }

    @Listener
    public void onServerLoadComplete(GameLoadCompleteEvent event){
        MessageChannel.TO_CONSOLE.send(MESSAGE("&b[ACTUS] &echarging is complete"));
        
    }  
    
    @Listener
    public void onServerStarted(GameStartedServerEvent event){
        game = Sponge.getGame();    	
        WorldManager.init();
        configGrave.moveGraveyard();
    } 
    
    @Listener
    public void onGamePostInit(GamePostInitializationEvent event){
	Optional<EconomyService> econService = Sponge.getServiceManager().provide(EconomyService.class);
	if (econService.isPresent()){
            economyService = econService.get();
	}else{
            getLogger().error("aucun plugin economy detecte !");
	}
    }
    
    @Listener
    public void reload(GameReloadEvent event) {
        init();
    }
    
    private boolean init() {
        File folder = new File("config/actus/book");
        if(!folder.exists()) folder.mkdir();
        folder = new File("config/actus/inventory");
        if(!folder.exists()) folder.mkdir();
        Config.setup();
        Data.setup();
        Data.load();
        MessageManager.init();
        ItemShopManager.init();
        ConfigGrave.init();
        ConfigGraveyard.init();
        TrocManager.init();
        return true;
    }
    
    /**
     * retourne True si WorldEdit est actif
     * @return Boolean
     */
    public boolean WEisActive() {
	return Sponge.getPluginManager().getPlugin("worldedit").isPresent();
    }
    
    /**
     * Retourne la region selectionné par le joueur sur WorldEdit
     * @param player
     * @return PlotSelection
     */
    public static Optional<PlotSelection> getWESelection(Player player)
    {
        PlotSelection plotSelect;
        LocalSession localSession = getLocalSession(player);
        com.sk89q.worldedit.world.World w = localSession.getSelectionWorld();
        if (w == null) return Optional.empty();
        RegionSelector regionSelector = localSession.getRegionSelector(w);
        Region sel;
        if (!regionSelector.isDefined()) return Optional.empty();
        try {
            sel = regionSelector.getRegion();
            com.sk89q.worldedit.world.World weWorld = sel.getWorld();
            if (weWorld==null) return Optional.empty();
            Optional<World> world = Sponge.getGame().getServer().getWorld(weWorld.getName());
            if(!world.isPresent())return Optional.empty();
            plotSelect = new PlotSelection(VectorWEToSponge(sel.getMinimumPoint()),VectorWEToSponge(sel.getMaximumPoint()),world.get());
        } catch (IncompleteRegionException e) {
            return Optional.empty();
        }
        return Optional.of(plotSelect);
    }
    
    private static LocalSession getLocalSession(Player player){
        if (WEdit == null) WEdit = WorldEdit.getInstance();
        SessionManager sessionManager = WEdit.getSessionManager();
        LocalSession localSession = sessionManager.findByName(player.getName());
        return localSession;
    }
    
    private static Vector3i VectorWEToSponge(Vector v){
        return new Vector3i(v.getX(), v.getY(), v.getZ());
    }
}
