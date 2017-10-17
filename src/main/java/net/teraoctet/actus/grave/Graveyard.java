package net.teraoctet.actus.grave;

import static net.teraoctet.actus.Actus.configGraveyard;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

@ConfigSerializable
public class Graveyard {
    
    String idGraveyard;
    @Setting Location<World> locChest1;
    @Setting Location<World> locChest2;
    @Setting Location<World> signGrave;
    
    public Graveyard(){}
    
    public Graveyard(String idGraveyard, Location<World> locChest1, Location<World> locChest2, Location<World> signGrave){
        this.idGraveyard = idGraveyard;
        this.locChest1 = locChest1;
        this.locChest2 = locChest2;
        this.signGrave = signGrave;
    }
    
    public void setLocChest1(Location<World> locChest1){ this.locChest1 = locChest1;}
    public void setLocChest2(Location<World> locChest2){ this.locChest2 = locChest2;}
    public void setSignGrave(Location<World> signGrave){ this.signGrave = signGrave;}
    
    
}
