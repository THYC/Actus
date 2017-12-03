package net.teraoctet.actus.portal;

import java.util.Optional;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.utils.Data;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Portal {

    //private final Location border1 = null;
    //private final Location border2 = null;
    private String portalname;
    private int level;
    private String world;
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;
    private String toworld;
    private int tox;
    private int toy;
    private int toz;
    private String message;
    private String cmd;
    
    public Portal(String portalname, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, 
    String toworld, int tox, int toy, int toz, String message){
        
        this.portalname = portalname;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.toworld = toworld;
        this.tox = tox;
        this.toy = toy;
        this.toz = toz;
        this.message = message;
        this.cmd = "DISABLED";
    }
    
    public Portal(String portalname, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, 
    String toworld, int tox, int toy, int toz, String message, String cmd){
        
        this.portalname = portalname;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.toworld = toworld;
        this.tox = tox;
        this.toy = toy;
        this.toz = toz;
        this.message = message;
        this.cmd = cmd;
    }
    
    public Portal(String portalname, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, String message){
        
        this.portalname = portalname;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.toworld = "DISABLED";
        this.tox = 0;
        this.toy = 0;
        this.toz = 0;
        this.message = message;
        this.cmd = "DISABLED";
    }
        
    public void insert() {
	Data.queue("INSERT INTO portal VALUES ('" + portalname + "', " + level + ", '" + world + "', " + x1 + ", " + y1 + ", " + z1
        + ", " + x2 + ", " + y2 + ", " + z2 + ", '" + toworld + "', " + tox + ", " + toy + ", " + toz + ", '" + message + "', '" + cmd + "')");
    }
	
    public void update() {
	Data.queue("UPDATE portal SET portalname = '" + portalname + "', level = " + level + ", world = '" + world 
        + "', x1 = " + x1 + ", y1 = " + y1 + ", z1 = " + z1 + ", x2 = " + x2 + ", y2 = " + y2 + ", z2 = " + z2 
        + ", toworld = '" + toworld + "', tox = " + tox + ", toy = " + toy + ", toz = " + toz + ", message = '" + sm.quoteToSQL(message) 
        + "', cmd = '" + cmd + "' WHERE portalname = '" + portalname + "'");
    }
	
    public void delete() {
	Data.queue("DELETE FROM portal WHERE portalname = '" + portalname + "'");
    }
       
    public void setName(String portalname){this.portalname = portalname;}
    public void setLevel(int level){this.level = level;}
    public void setworld(String world){this.world = world;}
    public void setX1(int x1){this.x1 = x1;}
    public void setX2(int x2){this.x2 = x2;}
    public void setY1(int y1){this.y1 = y1;}
    public void setY2(int y2){this.y2 = y2;}
    public void setZ1(int z1){this.z1 = z1;}
    public void setZ2(int z2){this.z2 = z2;}
    public void settoworld(String toworld){this.toworld = toworld;}
    public void settoX(int tox){this.tox = tox;}
    public void settoY(int toy){this.toy = toy;}
    public void settoZ(int toz){this.toz = toz;}
    public void setMessage(String message){this.message = message;}
    public void setCMD(String cmd){this.cmd = cmd;}
        
    public Optional<Location<World>> getBorder1(){
        Optional<World> w = Sponge.getServer().getWorld(world);
        if(w.isPresent()){
            Location<World> location = new Location<>(w.get(), x1, y1, z1);
            return Optional.of(location);
        }
        return Optional.empty();
    }
    
    public Optional<Location<World>> getBorder2(){
        Optional<World> w = Sponge.getServer().getWorld(world);
        if(w.isPresent()){
            Location<World> location = new Location<>(w.get(), x2, y2, z2);
            return Optional.of(location);
        }
        return Optional.empty();
    }
    
    public String getName(){return this.portalname;}
    public int getLevel(){return this.level;}
    public String getworld(){return this.world;}
    public int getX1(){return this.x1;}
    public int getX2(){return this.x2;}
    public int getY1(){return this.y1;}
    public int getY2(){return this.y2;}
    public int getZ1(){return this.z1;}
    public int getZ2(){return this.z2;}
    public String gettoworld(){return this.toworld;}
    public int gettoX(){return this.tox;}
    public int gettoY(){return this.toy;}
    public int gettoZ(){return this.toz;}
    public String getMessage(){return this.message;} 
    public String getCMD(){return this.cmd;} 
    
    public Optional<World> getObjWorld(){
        return Sponge.getGame().getServer().getWorld(this.world);
    }
}
