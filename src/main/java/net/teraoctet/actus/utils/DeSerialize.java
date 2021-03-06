package net.teraoctet.actus.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class DeSerialize {
    
    /**
     * Retourne la location au format String worldName:x:y:z
     * @param world
     * @param x
     * @param y
     * @param z
     * @return 
     */
    public static String location(String world, double x, double y, double z) {
        return world + ":" + x + ":" + y + ":" + z;
    }
    
    /**
     * Retourne la location au format String worldName:x:y:z
     * @param location
     * @return 
     */
    public static String location(Location<World> location) {
        return location.getExtent().getName() + ":" + String.valueOf(location.getBlockX()) + ":" + String.valueOf(location.getBlockY()) + ":" + String.valueOf(location.getBlockZ());
    }
    
    /**
     * Deserialyze une chaine type String representant un objet Location
     * @param stringLocation Chaine String sous la forme "world:x:y:z"
     * @return retourne un objet Location
     */
    public static Optional<Location<World>> getLocation(String stringLocation) {
        String loc[] = stringLocation.split(":");
        if(loc.length < 4) return Optional.empty();
        Optional<World> world = getGame().getServer().getWorld(loc[0]);
        Location<World> location = new Location<>(world.get(), Double.valueOf(loc[1]), Double.valueOf(loc[2]), Double.valueOf(loc[3]));
        return Optional.of(location);
    }
    
    /**
     * Deserialyze une chaine type String representant un objet Location
     * @param stringLocation Chaine String sous la forme "world:x:y:z"
     * @return retourne un objet world
     */
    public static Optional<World> getWorld(String stringLocation) {
        String loc[] = stringLocation.split(":");
        Optional<World> world = getGame().getServer().getWorld(loc[0]);
        return world;
    }
    
    /**
     * Deserialyze une chaine type String representant un objet Location
     * @param stringLocation Chaine String sous la forme "world:x:y:z"
     * @return retourne un objet Vector3d
     */
    public static Vector3d getVector3d(String stringLocation) {
        String loc[] = stringLocation.split(":");
        if(loc.length == 4){
            Vector3d v3d = new Vector3d(Double.valueOf(loc[1]), Double.valueOf(loc[2]), Double.valueOf(loc[3]));
            return v3d;
        }else{
            Vector3d v3d = new Vector3d(Double.valueOf(loc[0]), Double.valueOf(loc[1]), Double.valueOf(loc[2]));
            return v3d;
        }
    }
}
