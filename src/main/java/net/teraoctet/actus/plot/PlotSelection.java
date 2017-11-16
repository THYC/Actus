package net.teraoctet.actus.plot;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.world.World;

public class PlotSelection {
    Vector3i minPos;
    Vector3i maxPos;
    World world;
    
    public PlotSelection(){}
    
    public PlotSelection(Vector3i minPos, Vector3i maxPos, World world){
        this.minPos = minPos;
        this.maxPos = maxPos;
        this.world = world;
    }
    
    public void setMinPos(Vector3i minPos){this.minPos = minPos;}
    public void setMaxPos(Vector3i maxPos){this.maxPos = maxPos;}
    public void setWorld(World world){this.world = world;}
    
    public Vector3i getMinPos(){return minPos;}
    public Vector3i getMaxPos(){return maxPos;}
    public World getWorld(){return world;}
    
}
