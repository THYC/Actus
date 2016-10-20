package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import java.util.Random;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.utils.Config.DIAMETER_MAX_TPR;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.AIR;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;

public class CommandTPR implements CommandExecutor {
    
    private final Random random = new Random();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.tpr")) {
            Player player = (Player) src;
            World world = player.getWorld();
            player.sendMessage(MESSAGE("&eRecherche d'un point de chute securis\351 ..."));
            
            Location loc = getRandomLocation(world).add(0, 2, 0);
            if(serverManager.teleport(player, world.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())){
                return CommandResult.success();
            }
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
    
    private Location getRandomLocation(World world){
        WorldBorder worldBorder = world.getWorldBorder();
        int d = (int)worldBorder.getDiameter();
        if(d > DIAMETER_MAX_TPR()) d = DIAMETER_MAX_TPR();

        plugin.getLogger().info(String.valueOf(d));
        Vector3d centre = worldBorder.getCenter();

        int x = random.nextInt(d) - d/2;
        plugin.getLogger().info(String.valueOf(x));
        int z = random.nextInt(d) - d/2;
        plugin.getLogger().info(String.valueOf(z));
        Location tmp = new Location<>(world, new Vector3d(x + centre.getX(), 250, z + centre.getZ()));
        tmp = getFinalLocation(tmp);
        
        if(isSafe(tmp)){
            return tmp;
        }else{
            tmp = getRandomLocation(world);
            return tmp;
        }        
    }
    
    private Boolean isSafe(Location location){
        BlockType bt = location.getBlockType();
        return !(bt.equals(BlockTypes.WATER) ||
            bt.equals(BlockTypes.LAVA) ||
            bt.equals(BlockTypes.FLOWING_WATER) ||
            bt.equals(BlockTypes.FLOWING_LAVA) || 
            plotManager.getPlot(location).isPresent());
    }
    
    private Location getFinalLocation(Location location){                
        while (location.getBlockType().equals(AIR)){
            location = location.add(0,-1,0);
        }
        return location;
    }
}