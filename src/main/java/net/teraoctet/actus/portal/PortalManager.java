package net.teraoctet.actus.portal;

import static net.teraoctet.actus.utils.Data.portals;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PortalManager {          
        
    public PortalManager(){}
    
    private Portal portalContainsVector(Location loc){
        for (Portal portal : portals) {
            if(foundPortal(loc,portal)){return portal;}
        }
        return null;
    }
    
    public Boolean hasPortal(String name){
        return portals.stream().anyMatch((portal) -> (portal.getName().contains(name)));
    }
            
    private boolean foundPortal(Location location, Portal portal){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (portal.getworld().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}
        if ((X < portal.getX1()) || (X > portal.getX2())){return false;}
        if ((Z < portal.getZ1()) || (Z > portal.getZ2())){return false;}
        if ((Y < portal.getY1()) || (Y > portal.getY2())){return false;}
        return true;
    }
  
    public Portal getPortal(Location loc){return portalContainsVector(loc);}
        
    public Portal getPortal(String portalName){
        for(Portal portal : portals){
            if(portal.getName().contains(portalName)){return portal;}
        }
        return null;
    }
                    
    public Text listPortal(){
        String listportal = "";
        for(Portal portal : portals){
            listportal = listportal + System.getProperty("line.separator") + portal.getName();
        }
        
        Text text = Text.builder(listportal).color(TextColors.GOLD).build();
        return text;
    }
}

