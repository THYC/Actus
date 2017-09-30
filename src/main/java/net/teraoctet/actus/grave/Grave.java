package net.teraoctet.actus.grave;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

@ConfigSerializable
public class Grave {

    @Setting private String uuid;
    @Setting private String name;
    @Setting private String world;
    @Setting private int x;
    @Setting private int y;
    @Setting private int z;
    @Setting private int gx;
    @Setting private int gy;
    @Setting private int gz;
    @Setting private double gravetime;
    @Setting private BlockState block1;
    @Setting private BlockState block2;
	
    public Grave(String uuid, String name, String world, int x, int y, int z, int gx, int gy, int gz, 
            double gravetime, BlockState block1, BlockState block2) {
        
        this.uuid = uuid;
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
        this.gravetime = gravetime;
        this.block1 = block1;
        this.block2 = block2;
    }
    
    public void setUUID(String uuid) { this.uuid = uuid; }
    public void setName(String name) { this.name = name; }
    public void setWorld(String world) { this.world = world; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setZ(int z) { this.z = z; }
    public void setGX(int gx) { this.gx = gx; }
    public void setGY(int gy) { this.gy = gy; }
    public void setGZ(int gz) { this.gz = gz; }
    public void setGraveTime(double gravetime){ this.gravetime = gravetime; }
    public void setBlock1(BlockState block1){ this.block1 = block1; }
    public void setBlock2(BlockState block2){ this.block2 = block2; }
	
    public String getUUID() { return uuid; }
    public String getName() { return name; }
    public String getWorld() { return world; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    public int getGX() { return gx; }
    public int getGY() { return gy; }
    public int getGZ() { return gz; }
    public double getGraveTime() { return gravetime; }
    public BlockState getBlock1() { return block1; }
    public BlockState getBlock2() { return block2; }
    
    public Location<World> getLocation() {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x, this.y, this.z);
        return location;
    }  
}
