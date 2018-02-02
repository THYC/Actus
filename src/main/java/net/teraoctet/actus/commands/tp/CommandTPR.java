package net.teraoctet.actus.commands.tp;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import java.util.Random;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.ptm;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.utils.Config.DIAMETER_MAX_TPR;
import static net.teraoctet.actus.utils.Config.DIAMETER_MIN_TPR;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.AIR;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;

public class CommandTPR implements CommandExecutor {
    
    private final Random random = new Random();
    
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.tpr")) {
            Player player = (Player) src;
            World world = player.getWorld();
            if(ctx.getOne("world").isPresent()){
                String worldname = ctx.<String> getOne("world").get();
                Optional<World> w = getGame().getServer().getWorld(worldname);
                if(w.isPresent())world = w.get();
            }
            player.sendMessage(MESSAGE("&eRecherche d'un point de chute securis\351 ..."));
            
            Location loc = getRandomLocation(world,false).add(0, 2, 0);
            
            Entity boat = player.getBaseVehicle();
            if(boat.getType().equals(EntityTypes.BOAT)){
                loc = getRandomLocation(world,true).add(0, 1, 0);
                sm.teleport(player, world.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), Optional.of(boat));
            }else if(boat != null){
                sm.teleport(player, world.getName(), loc.getBlockX(), loc.add(0, 1, 0).getBlockY(), loc.getBlockZ(), Optional.of(boat));   
            }else{     
                sm.teleport(player, world.getName(), loc.getBlockX(), loc.add(0, 1, 0).getBlockY(), loc.getBlockZ(), Optional.empty());   
            }
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
    
    private Location getRandomLocation(World world, boolean water){
        WorldBorder worldBorder = world.getWorldBorder();
        int D = (int)worldBorder.getDiameter();
        int d = DIAMETER_MIN_TPR();
        if(D > DIAMETER_MAX_TPR()) D = DIAMETER_MAX_TPR();

        Vector3d centre = worldBorder.getCenter();
        
        int x = d/2 + (int)(Math.random() * ((D/2 - d/2) + 1)); 
        //plugin.getLogger().info(String.valueOf(x));
        int z = d/2 + (int)(Math.random() * ((D/2 - d/2) + 1));
        //plugin.getLogger().info(String.valueOf(z));
        Location tmp = new Location<>(world, new Vector3d(x + centre.getX(), 250, z + centre.getZ()));
        tmp = getFinalLocation(tmp);
        
        if(water){
            if(isWater(tmp)){
                return tmp;
            }else{
                tmp = getRandomLocation(world,true);
                return tmp;
            }        
        }else{
            if(isSafe(tmp)){
                return tmp;
            }else{
                tmp = getRandomLocation(world,false);
                return tmp;
            }   
        }
    }
    
    private Boolean isSafe(Location location){
        BlockType bt = location.getBlockType();
        return !(bt.equals(BlockTypes.WATER) ||
            bt.equals(BlockTypes.LAVA) ||
            bt.equals(BlockTypes.FLOWING_WATER) ||
            bt.equals(BlockTypes.FLOWING_LAVA) || 
            ptm.getPlot(location).isPresent());
    }
    
    private Boolean isWater(Location location){
        BlockType bt = location.getBlockType();
        return (bt.equals(BlockTypes.WATER) &&
            !ptm.getPlot(location).isPresent());
    }
    
    private Location getFinalLocation(Location location){                
        while (location.getBlockType().equals(AIR)){
            location = location.add(0,-1,0);
        }
        return location;
    }
}
