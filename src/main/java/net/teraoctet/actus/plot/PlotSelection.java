package net.teraoctet.actus.plot;

import com.flowpowered.math.vector.Vector3i;
import java.util.Optional;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PlotSelection {
    Optional<Vector3i> minPos = Optional.empty();
    Optional<Vector3i> maxPos = Optional.empty();
    Optional<World> world = Optional.empty();
    
    public PlotSelection(){}
    
    public PlotSelection(Vector3i minPos, Vector3i maxPos, World world){
        this.minPos = Optional.of(minPos);
        this.maxPos = Optional.of(maxPos);
        this.world = Optional.of(world);
    }
    
    public void setMinPos(Vector3i minPos){this.minPos = Optional.of(minPos);}
    public void setMaxPos(Vector3i maxPos){this.maxPos = Optional.of(maxPos);}
    public void setWorld(World world){this.world = Optional.of(world);}
    
    public Optional<Vector3i> getMinPos(){return minPos;}
    public Optional<Vector3i> getMaxPos(){return minPos;}  
    public Optional<World> getWorld(){return world;}
    
    public Optional<Location<World>> getMinPosLoc(){
        if(minPos.isPresent()){
            Location<World> location = new Location(world.get(),minPos.get());
            return Optional.of(location);
        }else{
            return Optional.empty();
        }
    }
    
    public Optional<Location<World>> getMaxPosLoc(){
        if(maxPos.isPresent()){
            Location<World> location = new Location(world.get(),maxPos.get());
            return Optional.of(location);
        }else{
            return Optional.empty();
        }
    }
    
}
