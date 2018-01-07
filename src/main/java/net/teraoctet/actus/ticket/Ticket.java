package net.teraoctet.actus.ticket;

import java.util.Optional;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.utils.Data;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class Ticket {
    
    private final double date;
    private final String uuid;
    private String message;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private final String assigned;
    private final Priority priority;
    private final Status status;

    public Ticket(double date, String uuid, String message, String world, double x, double y, double z, String assigned, String priority, String status) {
        this.date = date;
        this.uuid = uuid;
        this.message = message;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.assigned = assigned;
        this.priority = Priority.valueOf(priority);
        this.status = Status.valueOf(status);
    }
    
    public Ticket(double date, String uuid, String message, String world, double x, double y, double z) {
        this.date = date;
        this.uuid = uuid;
        this.message = message;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.assigned = "ND";
        this.priority = Priority.NORMAL;
        this.status = Status.PENDING;
    }
    
    public Ticket(Player player, String message) {
        this.date = Double.valueOf(sm.dateToLong());
        this.uuid = player.getIdentifier();
        this.message = message;
        this.world = player.getWorld().getName();
        this.x = player.getLocation().getX();
        this.y = player.getLocation().getY();
        this.z = player.getLocation().getZ();
        this.assigned = "ND";
        this.priority = Priority.NORMAL;
        this.status = Status.PENDING;
    }
    
    public Ticket(Player player, String message, String priority) {
        this.date = Double.valueOf(sm.dateToLong());
        this.uuid = player.getIdentifier();
        this.message = message;
        this.world = player.getWorld().getName();
        this.x = player.getLocation().getX();
        this.y = player.getLocation().getY();
        this.z = player.getLocation().getZ();
        this.assigned = "ND";
        this.priority = Priority.valueOf(priority);
        this.status = Status.PENDING;
    }

    public void insert() {
        Data.queue("INSERT INTO TICKET VALUES (" + date + ", '" + uuid + "', " + ", '" + message + "', " + ", '" + world + "', " + x + ", " + y + ", " + z + ", '" + ", '" + assigned + "', " + ", '" + priority + "', " + status + ")");
    }

    public void update() {
        Data.queue("UPDATE TICKET SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", message = '" + message + "' WHERE date = '" + date + "' AND uuid = '" + uuid + "'");
    }

    public void delete() {
        Data.queue("DELETE FROM TICKET WHERE date = " + date + " AND uuid = '" + uuid + "'");
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
    public String getUUID(){ return this.uuid;}
    public Double getX(){return this.x;}
    public Double getY(){return this.y;}
    public Double getZ(){return this.z;}
    public String getMessage(){ return this.message;}
    
     
    
}
