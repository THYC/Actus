package net.teraoctet.actus.grave;

import java.util.Optional;
import net.teraoctet.actus.utils.DeSerialize;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

@ConfigSerializable
public class Grave {
    
    String idGrave;
    @Setting private String uuid;
    @Setting private String name;
    @Setting private Location<World> deadLoc;
    @Setting private Location<World> graveLoc;
    @Setting private Location<World> signLoc;
    @Setting private long gravetime;
    @Setting private BlockState block1;
    @Setting private BlockState block2;
    @Setting private Text deadMessage;
    @Setting private String idAS;
    
    public Grave() {}
    
    public Grave(String uuid, String name, Location<World> deadLoc, Location<World> graveLoc, Location<World> signLoc,
            long gravetime, BlockState block1, BlockState block2, Text deadMessage) {
        this.idGrave = DeSerialize.location(graveLoc);
        this.uuid = uuid;
        this.name = name;
        this.deadLoc = deadLoc;
        this.graveLoc = graveLoc;
        this.signLoc = signLoc;
        this.gravetime = gravetime;
        this.block1 = block1;
        this.block2 = block2;
        this.deadMessage = deadMessage;
        this.idAS = "" ;   
    }
    
    public Grave(String uuid, String name, Location<World> deadLoc, Location<World> graveLoc, Location<World> signLoc,
            long gravetime, BlockState block1, BlockState block2, Text deadMessage,String idAS) {
        this.idGrave = DeSerialize.location(graveLoc);
        this.uuid = uuid;
        this.name = name;
        this.deadLoc = deadLoc;
        this.graveLoc = graveLoc;
        this.signLoc = signLoc;
        this.gravetime = gravetime;
        this.block1 = block1;
        this.block2 = block2;
        this.deadMessage = deadMessage;
        this.idAS = idAS;
    }
    
    public void setUUID(String uuid) { this.uuid = uuid; }
    public void setName(String name) { this.name = name; }
    public void setDeadLoc(Location<World> deadLoc) { this.deadLoc = deadLoc; }
    public void setGraveLoc(Location<World> graveLoc) { this.graveLoc = graveLoc; }
    public void setSignLoc(Location<World> signLoc) { this.signLoc = signLoc; }
    public void setGraveTime(long gravetime){ this.gravetime = gravetime; }
    public void setBlock1(BlockState block1){ this.block1 = block1; }
    public void setBlock2(BlockState block2){ this.block2 = block2; }
    public void setDeadMessage(Text deadMessage){ this.deadMessage = deadMessage; }
    public void setIdAS(String idAS){this.idAS = idAS; }
	
    public String getUUID() { return uuid; }
    public String getName() { return name; }
    public Location<World> getDeadLoc() { return deadLoc; }
    public Location<World> getGraveLoc() { return graveLoc; }
    public Location<World> getSignLoc() { return signLoc; }
    public long getGraveTime() { return gravetime; }
    public BlockState getBlock1() { return block1; }
    public BlockState getBlock2() { return block2; }
    public Text getDeadMessage() { return deadMessage; }
    public String getIDgrave(){ return DeSerialize.location(graveLoc);}
    
    /**
     * Retourne l'UUID format string de l'armorStand
     * @return 
     */
    public Optional<String> getUniqueIdAS(){
        if(this.idAS == null)return Optional.empty();
        if(this.idAS.equalsIgnoreCase(""))return Optional.empty();
        return Optional.of(idAS);
    }
    
    /**
     * Retourne l'emplacement Location ou le joueur est mort
     * @return 
     */
    public Optional<Location<World>> getDeadLocation() {
        return Optional.of(getDeadLoc());
    } 
    
    /**
     * Retourne l'emplacement Location du panneau/Sign
     * @return 
     */
    public Optional<Location<World>> getSignLocation() {
        return Optional.of(getSignLoc());
    } 
    
    /**
     * Retourne l'emplacement Location du Bloc Chest1
     * @return 
     */
    public Optional<Location<World>> getLocationBlock1() {
        return Optional.of(getGraveLoc());
    }  
    
    /**
     * Retourne l'emplacement Location du Bloc Chest2
     * @return 
     */
    public Optional<Location<World>> getLocationBlock2() {
        if(getLocationBlock1().isPresent()){
            return Optional.of(getLocationBlock1().get().add(0, 0, 1));
        }else{
            return Optional.empty();
        }
    }  
}
