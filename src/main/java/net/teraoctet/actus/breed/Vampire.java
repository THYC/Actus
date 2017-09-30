package net.teraoctet.actus.breed;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 *
 * @author thyc
 */
public class Vampire {
    
    public void spawnBat(Location<World> location, Player player, EntityType type, int amount){
        for (int i = 1; i <= amount; i++){
            Entity entity = location.getExtent().createEntity(type, location.getPosition());
            location.getExtent().spawnEntity(entity);
        }
    }
}
