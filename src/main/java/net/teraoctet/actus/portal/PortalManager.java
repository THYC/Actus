package net.teraoctet.actus.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.plot.Wedit;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.Data.PORTALS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PortalManager {          
        
    public PortalManager(){}
    
    private Optional<Portal> portalContainsVector(Location loc){
        for (Portal portal : PORTALS) {
            if(foundPortal(loc,portal)){return Optional.of(portal);}
        }
        return Optional.empty();
    }
    
    public Boolean hasPortal(String name){
        return PORTALS.stream().anyMatch((portal) -> (portal.getName().contains(name)));
    }
            
    private boolean foundPortal(Location location, Portal portal){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (portal.getworld().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}        
        else if (((X < portal.getX1()) || (X > portal.getX2())) && ((X > portal.getX1()) || (X < portal.getX2()))){return false;}
        else if (((Z < portal.getZ1()) || (Z > portal.getZ2())) && ((Z > portal.getZ1()) || (Z < portal.getZ2()))){return false;}
        else if (((Y < portal.getY1()) || (Y > portal.getY2())) && ((Y > portal.getY1()) || (Y < portal.getY2()))){return false;}
        return true;
    }
  
    public Optional<Portal> getPortal(Location loc){return portalContainsVector(loc);}
        
    public Optional<Portal> getPortal(String portalName){
        for(Portal portal : PORTALS){
            if(portal.getName().contains(portalName)){return Optional.of(portal);}
        }
        return Optional.empty();
    }
                    
    public List<Text> listPortal(){
        List<Text> text = new ArrayList();
        text.add(MESSAGE("&3LISTE DES PORTAILS"));
        PORTALS.forEach((portal) -> {                   
            text.add(Text.builder().append(MESSAGE("&e" + portal.getName() + " &bworld : " + portal.getworld() + " &acmd : " + portal.getCMD())).build()
                .concat(Text.builder().append(MESSAGE(" &e[ affiche ] ")).onClick(TextActions.executeCallback(callDisplayPortal(portal.getName()))).build()
                .concat(Text.builder().append(MESSAGE(" &4[ X ] ")).onClick(TextActions.executeCallback(callDelPortal(portal.getName()))).build()).toText()));
        });
        return text;
    }
    
    public Consumer<CommandSource> callDisplayPortal(String portalName) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            Optional<Portal> portal = getPortal(portalName);
            if(portal.isPresent()){
                if(Wedit.WEisActive() && portal.get().getObjWorld().isPresent()){
                    PlotSelection plotSelect = new PlotSelection(portal.get().getBorder1().get().getBlockPosition(),portal.get().getBorder2().get().getBlockPosition(),portal.get().getObjWorld().get());
                    Wedit.setSelection(player, plotSelect);
                }
            }
        };
    }
    
    public Consumer<CommandSource> callDelPortal(String portalName) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            Optional<Portal> portal = getPortal(portalName);
            if(portal.isPresent()){
                portal.get().delete();
                Data.commit();
                player.sendMessage(MESSAGE("&6Portail supprimé !"));
            }
        };
    }
}

