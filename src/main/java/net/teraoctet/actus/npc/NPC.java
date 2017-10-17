package net.teraoctet.actus.npc;

import com.flowpowered.math.vector.Vector3d;
import net.teraoctet.actus.skin.SkinUUID;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class NPC {
    
    private SkinUUID skin;
    private Text name;
    private Location<World> spawn;
    private Vector3d position;
    private Text message;
        
    public NPC(SkinUUID skin, Text name, Location<World> spawn, Vector3d position, Text message){
        this.skin = skin;
        this.name = name;
        this.spawn = spawn;
        this.position = position;
    }
    
    public void setSkinUUID(SkinUUID skin){this.skin = skin;}
    public void setName(Text name){this.name = name;}
    public void setSpawn(Location spawn){this.spawn = spawn;}
    
    
}
