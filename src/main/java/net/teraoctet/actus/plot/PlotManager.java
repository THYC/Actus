package net.teraoctet.actus.plot;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Data.jails;
import static net.teraoctet.actus.utils.Data.plots;
import static net.teraoctet.actus.utils.Data.setts;
import org.spongepowered.api.block.BlockType;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.STANDING_BANNER;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class PlotManager {   
    private Location<World> border1;
    private Location<World> border2;
    private Player player;
    
    public PlotManager() {}
    
    public PlotManager(Player player, Location border1, Location border2){
        this.player = player;
        this.border1 = border1;
        this.border2 = border2;
    }
    
    public static PlotManager getSett(Player player){
        if (!setts.containsKey(player.getName())){
            PlotManager sett = new PlotManager(player, null, null);
            setts.put(player.getName(), sett);
            return sett;
        }
        
        PlotManager sett = (PlotManager)setts.get(player.getName());
        sett.setPlayer(player);
        return sett;
    }

    public void setPlayer(Player player){this.player = player;}
  
    private Optional<Plot> plotContainsVector(Location loc, boolean flagJail){
        if (flagJail == true){
            for(Plot jail : jails){if(foundPlot(loc,jail)){return Optional.of(jail);}}
        }else{
            Optional<Plot> plotmaxlevel = Optional.empty();
            for (Plot plot : plots) {
                if(foundPlot(loc,plot)){
                    if(plotmaxlevel.isPresent()){
                        if(plot.getLevel() > plotmaxlevel.get().getLevel())plotmaxlevel=Optional.of(plot);
                    }else{
                        plotmaxlevel=Optional.of(plot);
                    }
                }
            }
            return plotmaxlevel;
        }
        return Optional.empty();
    }
    
    private Optional<Plot> plotContainsVector(String world, Vector3d vector, boolean flagJail){
        if (flagJail == true){
            for(Plot jail : jails){if(foundPlot(world, vector,jail)){return Optional.of(jail);}}
        }else{
            Optional<Plot> plotmaxlevel = Optional.empty();
            for (Plot plot : plots) {
                if(foundPlot(world, vector,plot)){
                    if(plotmaxlevel.isPresent()){
                        if(plot.getLevel() > plotmaxlevel.get().getLevel())plotmaxlevel=Optional.of(plot);
                    }else{
                        plotmaxlevel=Optional.of(plot);
                    }
                }
            }
            return plotmaxlevel;
        }
        return Optional.empty();
    }
    
    private boolean foundPlot(Location location, Plot plot){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (plot.getworldName().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}
        else if (((X < plot.getX1()) || (X > plot.getX2())) && ((X > plot.getX1()) || (X < plot.getX2()))){return false;}
        else if (((Z < plot.getZ1()) || (Z > plot.getZ2())) && ((Z > plot.getZ1()) || (Z < plot.getZ2()))){return false;}
        else if (((Y < plot.getY1()) || (Y > plot.getY2())) && ((Y > plot.getY1()) || (Y < plot.getY2()))){return false;}
        return true;
    }
    
    private boolean foundPlot(String world, Vector3d vector, Plot plot){
                
        int X = vector.getFloorX();
        int Y = vector.getFloorY();
        int Z = vector.getFloorZ();
        
        if (plot.getworldName().equalsIgnoreCase(world) == false){return false;}
        else if (((X < plot.getX1()) || (X > plot.getX2())) && ((X > plot.getX1()) || (X < plot.getX2()))){return false;}
        else if (((Z < plot.getZ1()) || (Z > plot.getZ2())) && ((Z > plot.getZ1()) || (Z < plot.getZ2()))){return false;}
        else if (((Y < plot.getY1()) || (Y > plot.getY2())) && ((Y > plot.getY1()) || (Y < plot.getY2()))){return false;}
        return true;
    }
    
    /**
     * Parcelle (Plot) enregistré au point de l'objet Location 
     * avec le Flag Jail = True si la paramètre flaJail = True
     * @param loc Objet Location
     * @param flagJail True pour les parcelles Jails
     * @return La parcelle ou Null si rien trouvé
     */
    public Optional<Plot> getPlot(Location loc, boolean flagJail){return plotContainsVector(loc, flagJail);}
    
    /**
     * Parcelle (Plot) enregistré au point de l'objet Location 
     * avec le Flag Jail = True si la paramètre flaJail = True
     * @param world nom du monde
     * @param vector vector3d
     * @param flagJail True pour les parcelles Jails
     * @return La parcelle ou Null si rien trouvé
     */
    public Optional<Plot> getPlot(String world, Vector3d vector, boolean flagJail){return plotContainsVector(world, vector, flagJail);}
    
    /**
     * Parcelle (Plot) enregistré au point de l'objet Location
     * @param loc Objet Location
     * @return La parcelle ou vide si rien trouvé
     */
    public Optional<Plot> getPlot(Location loc){return plotContainsVector(loc, false);}
    
    /**
     * Parcelle (Plot) enregistré au point de l'objet Location
     * @param world nom du monde
     * @param vector vector3d
     * @return La parcelle ou Null si rien trouvé
     */
    public Optional<Plot> getPlot(String world, Vector3d vector){return plotContainsVector(world, vector, false);}
    
    /**
     * Retourne True si le nom indiqué correspond bien a un nom de parcelle(Plot)
     * @param name Nom du Plot
     * @return Boolean
     */
    public Boolean hasPlot(String name){return plots.stream().anyMatch((plot) -> (plot.getName().contains(name)));}
    
    /**
     * Retourne True si le nom indiqué correspond a une parcelle enregistré en Jail(Prison)
     * @param name Nom de la parcelle (Plot)
     * @return Boolean
     */
    public Boolean hasJail(String name){return jails.stream().anyMatch((jail) -> (jail.getName().contains(name)));}
    
    /**
     * Retourne la valeur Plot de la parcelle nommée
     * @param plotName Nom de l'object Plot
     * @return Object Plot
     */
    public Optional<Plot> getPlot(String plotName){
        for (Plot plot : plots) {
            if(plot.getName().contains(plotName)){return Optional.of(plot);}
        }
        return Optional.empty();
    }
    
    /**
     * Retourne le propriétaire de la parcelle nommée
     * @param plotName Nom de l'object Plot
     * @return String
     */
    public String getPlotOwner(String plotName){
        for(Plot plot : plots){
            if(plot.getName().contains(plotName)){return plot.getUuidOwner();}
        }
        return null;
    }
                
    public void setBorder(int a, Location b){
        if (a == 1){
            this.border1 = b;
        } else if (a == 2) {
            this.border2 = b;
        }
    }
    
    public Optional<Location> getBorder1(){
        if (this.border1 != null){
            int x = this.border1.getBlockX();
            int y = this.border1.getBlockY();
            int z = this.border1.getBlockZ();

            Extent extent = this.border1.getExtent();
            Location loc = new Location(extent, x, y, z);
            return Optional.of(loc);
        }
        return Optional.empty();
    }
    
    public Optional<Location> getBorder2()
    {
        if (this.border2 != null)
        {
            int x = this.border2.getBlockX();
            int y = this.border2.getBlockY();
            int z = this.border2.getBlockZ();
            
            Extent extent = this.border2.getExtent();
            Location loc = new Location(extent,x, y, z);
            return Optional.of(loc);
        }
        return Optional.empty();
    }
    
    /**
     * Si TRUE retourne la list des jails enregistré
     * Si FALSE retourne la liste des parcelles enregistré
     * @param flagJail Boolean 
     * @return Text
     */        
    public Text getListPlot(boolean flagJail){
        String listplot = "&6";
        if (flagJail == true){
            for(Plot jail : jails){
                listplot = listplot + "\n" + jail.getName();
            }
        } else {
            for(Plot plot : plots){
                listplot = listplot + "\n" + plot.getName();
            }
        }
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(listplot)).toText();
        return text;
    }
    
    /**
     * Retourne la liste des parcelles appartenant au joueur nommé
     * @param playerUUID UUID du joueur 
     * @return  Text
     */
    public Text getListPlot(String playerUUID){
        String listplot = "&6Total : " + plots.size();
        for (Plot plot : plots) {
            if(plot.getUuidOwner().equalsIgnoreCase(playerUUID)){
                listplot = listplot + "\n" + plot.getName();
            }
        }
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(listplot)).toText();
        return text;
    }
    
    /**
     * Retourne un Array comprenant les parcelles du joueur
     * @param playerUUID UUID du joueur
     * @return  ArrayList
     */
    public static final ArrayList<Plot> playerPlots (String playerUUID)
    {
        ArrayList<Plot> playerPlots = new ArrayList<>();
        plots.stream().filter((plot) -> (plot.getUuidOwner().equalsIgnoreCase(playerUUID))).forEach((plot) -> {playerPlots.add(plot);});
        return playerPlots;        
    }
    
    /**
     * Retourne un Array comprenant les parcelles de la guild du joueur
     * @param player
     * @return  ArrayList
     */
    public static final ArrayList<Plot> guildPlots (Player player)
    {
        APlayer aplayer = getAPlayer(player.getIdentifier());
        ArrayList<Plot> guildPlots = new ArrayList<>();
        plots.stream().filter((plot) -> (plot.getIdGuild()==aplayer.getID_guild())).forEach((plot) -> {guildPlots.add(plot);});
        return guildPlots;        
    }
    
    /**
     * Retourne la validité des coodonnées pour la création d'une parcelle
     * retourne True si une parcelle est présente sur la zone séléctionné. 
     * @param l1 Location du point 1
     * @param l2 Location du point 2
     * @return Boolean
     */
    public boolean plotNotAllow(Location l1, Location l2){                
        Location <World> w = l1;
        World world = w.getExtent();
        Plot newPlot = new Plot(world.getName(),l1.getBlockX(),0,l1.getBlockZ(),l2.getBlockX(),500,l2.getBlockZ());
                
        for(Plot plot : plots){
            if(plot.getworldName().equalsIgnoreCase(newPlot.getworldName()))return true;
            if(foundPlot(plot.getLocX1Y1Z1(),newPlot) || foundPlot(plot.getLocX2Y2Z2(),newPlot) ||
                foundPlot(plot.getLocX1Y1Z2(),newPlot) || foundPlot(plot.getLocX2Y2Z1(),newPlot) ||
                foundPlot(plot.getLocX1Y2Z2(),newPlot) || foundPlot(plot.getLocX2Y1Z2(),newPlot) ||
                foundPlot(plot.getLocX2Y1Z1(),newPlot) || foundPlot(plot.getLocX1Y2Z1(),newPlot))return true;
        }
        return false;
    }
    
    /**
     * Retourne une liste des parcelles présentes sur la zone
     * @param l1 Location angle 1
     * @param l2 Location angle 2
     * @return 
     */
    public List<Plot> getListPlotParent(Location l1, Location l2){                
        Location <World> w = l1;
        List<Plot> plotList = new ArrayList();
        World world = w.getExtent();
        Plot newPlot = new Plot(world.getName(),l1.getBlockX(),0,l1.getBlockZ(),l2.getBlockX(),500,l2.getBlockZ());
                
        if(getPlot(newPlot.getLocX1Y1Z1()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX1Y1Z1()).get());
        }
        if(getPlot(newPlot.getLocX1Y1Z2()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX1Y1Z2()).get());
        }
        if(getPlot(newPlot.getLocX1Y2Z2()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX1Y2Z2()).get());
        }
        if(getPlot(newPlot.getLocX2Y2Z1()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX2Y2Z1()).get());
        }
        if(getPlot(newPlot.getLocX2Y2Z2()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX2Y2Z2()).get());
        }
        if(getPlot(newPlot.getLocX2Y1Z2()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX2Y1Z2()).get());
        }
        if(getPlot(newPlot.getLocX2Y1Z1()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX2Y1Z1()).get());
        }
        if(getPlot(newPlot.getLocX1Y2Z1()).isPresent()){
            plotList.add(getPlot(newPlot.getLocX1Y2Z1()).get());
        }
        return plotList;
    }
    
    /**
     * retourne True si toutes le player est Owner de toutes les parcelles parentes
     * @param player
     * @param l1
     * @param l2
     * @return 
     */
    public boolean hasOwnerPlotParent(Player player, Location l1, Location l2){
        if (!getListPlotParent(l1,l2).stream().noneMatch((plot) -> (!plot.getUuidOwner().equalsIgnoreCase(player.getIdentifier())))) {
            return false;
        }
        return true;
    }
    
    /**
     * Retourne le level le plus haut des parcelles parent + 1
     * @param player
     * @param l1
     * @param l2
     * @return 
     */
    public Integer  getMaxLevelPlotParent(Player player, Location l1, Location l2){
        int level = 0;
        for (Plot plot : getListPlotParent(l1,l2)) {
            if(plot.getLevel() >= level) level = plot.getLevel() + 1;
        }
        return level;
    }
    
    /**
     * Spawn un banner a l'angle transmis
     * @param loc 
     */
    public void spawnTag (Location loc){
        loc.setBlockType(STANDING_BANNER);
    }  
    
    public void remTag (Plot plot){
        World world = plot.getWorld().get();            
        cutTag(new Location(world,plot.getX1(),plot.getYSpawn(plot.getX1(), plot.getZ1())-1,plot.getZ1()));
        cutTag(new Location(world,plot.getX1(),plot.getYSpawn(plot.getX1(), plot.getZ2())-1,plot.getZ2()));
        cutTag(new Location(world,plot.getX2(),plot.getYSpawn(plot.getX2(), plot.getZ1())-1,plot.getZ1()));
        cutTag(new Location(world,plot.getX2(),plot.getYSpawn(plot.getX2(), plot.getZ2())-1,plot.getZ2()));
    }
    
    /**
     * Supprime le banner situé sur l'angle transmis
     * @param loc 
     */
    private void cutTag(Location loc){
        if(loc.getBlockType().equals(STANDING_BANNER)){
            loc.setBlockType(AIR); 
        }else if(loc.add(0, -1, 0).getBlockType().equals(STANDING_BANNER)){
            loc.setBlockType(AIR); 
        }else if(loc.add(0, -2, 0).getBlockType().equals(STANDING_BANNER)){
            loc.setBlockType(AIR); 
        }
    }
    
    public Boolean hasTag(Location loc, Plot plot){
        BlockType block = loc.getBlockType();
        if(loc.getBlockX() == plot.getX1() || loc.getBlockX() == plot.getX2()){
            if(loc.getBlockZ() == plot.getZ1() || loc.getBlockZ() == plot.getZ2()){
                if(block.equals(STANDING_BANNER)){
                    loc.setBlockType(AIR); 
                    return true;
                }
            }
        }
        return false;
    }
    
    public Boolean hasTag(Plot plot){
        Location loc = new Location(plot.getWorld().get(),plot.getX1(),plot.getYSpawn(plot.getX1(), plot.getZ1())-1,plot.getZ1());
        BlockType block = loc.getBlockType();
        if(loc.getBlockX() == plot.getX1() || loc.getBlockX() == plot.getX2()){
            if(loc.getBlockZ() == plot.getZ1() || loc.getBlockZ() == plot.getZ2()){
                if(loc.getBlockY() == plot.getYSpawn(loc.getBlockX(), loc.getBlockZ())-1){
                    if(block.equals(STANDING_BANNER))return true;     
                }
            }
        }
        
        loc = new Location(plot.getWorld().get(),plot.getX2(),plot.getYSpawn(plot.getX2(), plot.getZ2())-1,plot.getZ2());
        block = loc.getBlockType();
        if(loc.getBlockX() == plot.getX1() || loc.getBlockX() == plot.getX2()){
            if(loc.getBlockZ() == plot.getZ1() || loc.getBlockZ() == plot.getZ2()){
                if(loc.getBlockY() == plot.getYSpawn(loc.getBlockX(), loc.getBlockZ())-1){
                    if(block.equals(STANDING_BANNER))return true;     
                }
            }
        }
        
        loc = new Location(plot.getWorld().get(),plot.getX1(),plot.getYSpawn(plot.getX1(), plot.getZ2())-1,plot.getZ2());
        block = loc.getBlockType();
        if(loc.getBlockX() == plot.getX1() || loc.getBlockX() == plot.getX2()){
            if(loc.getBlockZ() == plot.getZ1() || loc.getBlockZ() == plot.getZ2()){
                if(loc.getBlockY() == plot.getYSpawn(loc.getBlockX(), loc.getBlockZ())-1){
                    if(block.equals(STANDING_BANNER))return true;     
                }
            }
        }
        
        loc = new Location(plot.getWorld().get(),plot.getX2(),plot.getYSpawn(plot.getX2(), plot.getZ1())-1,plot.getZ1());
        block = loc.getBlockType();
        if(loc.getBlockX() == plot.getX1() || loc.getBlockX() == plot.getX2()){
            if(loc.getBlockZ() == plot.getZ1() || loc.getBlockZ() == plot.getZ2()){
                if(loc.getBlockY() == plot.getYSpawn(loc.getBlockX(), loc.getBlockZ())-1){
                    if(block.equals(STANDING_BANNER))return true;     
                }
            }
        }
        return false;
    }
    
    /**
     * Retourne le nombre de parcelle/plot enregistré sur une guild
     * @param id_guild
     * @return int nombre de parcelle/plot
     */
    public int getCountPlotGuild(int id_guild){
        int nb = 0;
        nb = plots.stream().filter((plot) -> (plot.getIdGuild() == id_guild)).map((_item) -> 1).reduce(nb, Integer::sum);
        return nb;
    }
    
    /**
     * Retourne "OUI si value = 1 sinon retourne NON
     * @param id 
     * @return Valeur String OUI ou NON
     */
    public String ValueOf(int id) {
        if(id == 0) return "NON";
        return "OUI ";
    }
}

