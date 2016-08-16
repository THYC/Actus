package net.teraoctet.actus.plot;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.utils.Data.jails;
import static net.teraoctet.actus.utils.Data.plots;
import static net.teraoctet.actus.utils.Data.setts;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.GLOWSTONE;
import static org.spongepowered.api.block.BlockTypes.OBSIDIAN;
import static org.spongepowered.api.block.BlockTypes.TORCH;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class PlotManager {   
    private Location border1;
    private Location border2;
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
            for(Plot plot : plots){if(foundPlot(loc,plot)){return Optional.of(plot);}}
        }
        return Optional.empty();
    }
    
    private Optional<Plot> plotContainsVector(String world, Vector3d vector, boolean flagJail){
        if (flagJail == true){
            for(Plot jail : jails){if(foundPlot(world, vector,jail)){return Optional.of(jail);}}
        }else{
            for(Plot plot : plots){if(foundPlot(world, vector,plot)){return Optional.of(plot);}}
        }
        return Optional.empty();
    }
    
    private boolean foundPlot(Location location, Plot plot){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (plot.getworldName().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}
        else if ((X < plot.getX1()) || (X > plot.getX2())){return false;}
        else if ((Z < plot.getZ1()) || (Z > plot.getZ2())){return false;}
        else if ((Y < plot.getY1()) || (Y > plot.getY2())){return false;}
        return true;
    }
    
    private boolean foundPlot(String world, Vector3d vector, Plot plot){
                
        int X = vector.getFloorX();
        int Y = vector.getFloorY();
        int Z = vector.getFloorZ();
        
        if (plot.getworldName().equalsIgnoreCase(world) == false){return false;}
        else if ((X < plot.getX1()) || (X > plot.getX2())){return false;}
        else if ((Z < plot.getZ1()) || (Z > plot.getZ2())){return false;}
        else if ((Y < plot.getY1()) || (Y > plot.getY2())){return false;}
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
     * @return La parcelle ou Null si rien trouvé
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
    
    public Location getBorder1(){
        if ((this.border1 != null) && (this.border2 != null)){
            int x0 = this.border1.getBlockX();
            int y0 = this.border1.getBlockY();
            int z0 = this.border1.getBlockZ();
            int x1 = this.border2.getBlockX();
            int y1 = this.border2.getBlockY();
            int z1 = this.border2.getBlockZ();
            
            Extent extent = this.border1.getExtent();
            
            return new Location(extent, Math.min(x0, x1), Math.min(y0, y1), Math.min(z0, z1));
        }
        return this.border1;
    }
    
    public Location getBorder2()
    {
        if ((this.border1 != null) && (this.border2 != null))
        {
            int x0 = this.border1.getBlockX();
            int y0 = this.border1.getBlockY();
            int z0 = this.border1.getBlockZ();
            int x1 = this.border2.getBlockX();
            int y1 = this.border2.getBlockY();
            int z1 = this.border2.getBlockZ();
            
            Extent extent = this.border1.getExtent();
            
          return new Location(extent, Math.max(x0, x1), Math.max(y0, y1), Math.max(z0, z1));
        }
        return this.border2;
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
        for(Plot plot : plots){
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
     * Retourne la validité des coodonnées pour la création d'une parcelle
     * @param l1 Location du point 1
     * @param l2 Location du point 2
     * @return Boolean
     */
    public boolean plotAllow(Location l1, Location l2){                
        Location <World> w = l1;
        World world = w.getExtent();
        Plot newPlot = new Plot(world.getName(),this.border1.getBlockX(),0,l1.getBlockZ(),l2.getBlockX(),500,l2.getBlockZ());
                
        return plots.stream().filter((plot) -> (plot.getworldName().equalsIgnoreCase(newPlot.getworldName()))).anyMatch((plot) -> ( 
                foundPlot(plot.getLocX1Y1Z1(),newPlot) || foundPlot(plot.getLocX2Y2Z2(),newPlot) ||
                foundPlot(plot.getLocX1Y1Z2(),newPlot) || foundPlot(plot.getLocX2Y2Z1(),newPlot) ||
                foundPlot(plot.getLocX1Y2Z2(),newPlot) || foundPlot(plot.getLocX2Y1Z2(),newPlot) ||
                foundPlot(plot.getLocX2Y1Z1(),newPlot) || foundPlot(plot.getLocX1Y2Z1(),newPlot)));
    }
    
    public void spawnTag (Location loc){
        loc.setBlockType(GLOWSTONE, Cause.of(NamedCause.source(plugin)));
    }  
    
    public void remTag (Plot plot){
        World world = plot.getWorld().get();            
        cutTag(new Location(world,plot.getX1(),plot.getYSpawn(plot.getX1(), plot.getZ1())-1,plot.getZ1()));
        cutTag(new Location(world,plot.getX1(),plot.getYSpawn(plot.getX1(), plot.getZ2())-1,plot.getZ2()));
        cutTag(new Location(world,plot.getX2(),plot.getYSpawn(plot.getX2(), plot.getZ1())-1,plot.getZ1()));
        cutTag(new Location(world,plot.getX2(),plot.getYSpawn(plot.getX2(), plot.getZ2())-1,plot.getZ2()));
    }
    
    private void cutTag(Location loc){
        loc.setBlockType(AIR, Cause.of(NamedCause.source(plugin)));  
    }
    
    public Boolean hasTag(Location loc, Plot plot){
        BlockType block = loc.getBlockType();
        if(loc.getBlockX() == plot.getX1() || loc.getBlockX() == plot.getX2()){
            if(loc.getBlockZ() == plot.getZ1() || loc.getBlockZ() == plot.getZ2()){
                if(loc.getBlockY() == plot.getYSpawn(loc.getBlockX(), loc.getBlockZ())-1){
                    if(block.equals(GLOWSTONE))return true;     
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
                    if(block.equals(GLOWSTONE))return true;     
                }
            }
        }
        return false;
    }
}

