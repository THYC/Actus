package net.teraoctet.actus.trace;

import java.util.Optional;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.utils.Data;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Trace {
    
    private double date;
    private String world;
    private double x;
    private double y;
    private double z;
    private String uuid;
    private String type;
    private String block;
                
    public Trace(String world, double x, double y, double z, String uuid, String type, String block){
        this.date = Double.valueOf(serverManager.dateToLong());
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.uuid = uuid;
        this.type = type;
        this.block = block;
    }
    
    public Trace(Player player, Location<World> loc, String type, String block) {
        this.date = Double.valueOf(serverManager.dateToLong());
        this.world = loc.getExtent().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.uuid = player.getIdentifier();
        this.type = type;
        this.block = block;
    }

    public void insert() {
        Data.queue("INSERT INTO trace VALUES (" + date + ", '" + world + "', " + x + ", " + y + ", " + z + ", '" + uuid + "', '" + type + "', '" + block + "')");
    }

    public void delete() {
        Data.queue("DELETE FROM trace WHERE date = " + date + " AND uuid = '" + uuid + "'");
    }
        
    public void setWorld(String world){ this.world = world;}
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}
    public void setZ(double z){this.z = z;}
    public void setUUID(String uuid){ this.uuid = uuid;}
    public void setType(String type){ this.type = type;}
    public void setBlock(String block){ this.block = block;}
    public String getWorldName(){ return this.world;}
    public Optional<World> getWorld(){ 
        Optional<World> w = getGame().getServer().getWorld(world);
        if(w.isPresent())return w;
        return Optional.empty();
    }
    public String getDate(){ return serverManager.longToDateString(this.date);}
    public String getUUID(){ return this.uuid;}
    public Double getX(){return this.x;}
    public Double getY(){return this.y;}
    public Double getZ(){return this.z;}

 
    
}
