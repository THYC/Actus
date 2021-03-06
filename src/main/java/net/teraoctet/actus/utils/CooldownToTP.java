package net.teraoctet.actus.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static net.teraoctet.actus.Actus.mapCountDown;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import static net.teraoctet.actus.utils.MessageManager.ERROR;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.World;

public class CooldownToTP {
    
    private Task taskCountdown = null;
    private final Player player;
    private final String world;
    private final int X;
    private final int Y;
    private final int Z;
    private Optional<Entity> entity = Optional.empty();
    private Optional<Text> msg = Optional.empty();
    private boolean result;
    
    public CooldownToTP(Player player, String world, int X, int Y, int Z){
        this.result = false;
        this.player = player;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    
    public CooldownToTP(Player player, Optional<Entity> entity, String world, int X, int Y, int Z){
        this.result = false;
        this.player = player;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.entity = entity;
    }
    
    public CooldownToTP(Player player, String world, int X, int Y, int Z, Optional<Text> msg){
        this.result = false;
        this.player = player;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.msg = msg;
    }
    
    @SuppressWarnings("empty-statement")
    public void run(){
        taskCountdown = Sponge.getScheduler().createTaskBuilder().execute(() -> {
            try{
                Location lastLocation = player.getLocation();
                Optional<Location<World>> newloc = Sponge.getGame().getTeleportHelper().getSafeLocation(new Location(getGame().getServer().getWorld(world).get(), new Vector3d(X, Y, Z)));
                if(newloc.isPresent()){
                    player.setLocation(newloc.get());
                    if(entity.isPresent())entity.get().setLocation(newloc.get());
                }else{
                    player.sendMessage(MESSAGE("&4Erreur Teleportation"));
                }
                mapCountDown.remove(player.getIdentifier());
                APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                aplayer.setLastposition(DeSerialize.location(lastLocation));
                aplayer.update();
                this.result = true;
                if(msg.isPresent())player.sendMessage(msg.get());
            }catch(Exception ex){
                player.sendMessage(ERROR());
                this.result = false;
            }                          
        }).delay(Config.COOLDOWN_TO_TP(), TimeUnit.SECONDS).submit(plugin);
    }
    
    public boolean getResult(){
        return this.result;
    }

    public void stopTP(){
        taskCountdown.cancel();
    }
}
