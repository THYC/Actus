package net.teraoctet.actus.warp;

import java.util.Optional;
import net.teraoctet.actus.utils.Data;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class Warp {
    
    private final String name;
    private String world;
    private double x;
    private double y;
    private double z;
    private String message;
    
    public Warp(String name, String world, double x, double y, double z, String message) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.message = message;      
    }
    
    public Warp(String name, String world, double x, double y, double z) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.message = "";      
    }
    
    public Warp(String name, Player player) {
        this.name = name;
        this.world = player.getWorld().getName();
        this.x = player.getLocation().getX();
        this.y = player.getLocation().getY();
        this.z = player.getLocation().getZ();
        this.message = "";      
    }
    
    public void insert() {
        Data.queue("INSERT INTO warps VALUES ('" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + message + ")");
    }

    public void update() {
            Data.queue("UPDATE warps SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", message = '" + message + "' WHERE name = '" + name + "'");
    }

    public void delete() {
            Data.queue("DELETE FROM warps WHERE name = '" + name + "'");
    }
    
    public void setWorld(String world){ this.world = world;}
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}
    public void setZ(double z){this.z = z;}
    public void setMessage(String message){ this.message = message;}
    public String getWorldName(){ return this.world;}
    public Optional<World> getWorld(){ 
        Optional<World> w = getGame().getServer().getWorld(world);
        if(w.isPresent())return w;
        return Optional.empty();
    }
    public String getName(){ return this.name;}
    public Double getX(){return this.x;}
    public Double getY(){return this.y;}
    public Double getZ(){return this.z;}
    public String getMessage(){ return this.message;}
}
