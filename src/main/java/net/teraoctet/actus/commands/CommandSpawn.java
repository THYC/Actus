package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.game;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.utils.Config;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class CommandSpawn implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.spawn")) {
            Player player = (Player) src;
            if(Config.UNIQUE_SPAWN_WORLD() == 0){
                sm.teleport(player,player.getWorld().getName(),
                        player.getWorld().getSpawnLocation().getBlockX(),
                        player.getWorld().getSpawnLocation().getBlockY(),
                        player.getWorld().getSpawnLocation().getBlockZ());
                player.sendMessage(MESSAGE("command.world.spawn.success"));
            }else{
                String worldName = Config.SPAWN_WORLD();
                Optional<World> world = game.getServer().getWorld(worldName);
                if(world.isPresent()){
                    sm.teleport(player,world.get().getName(),
                        world.get().getSpawnLocation().getBlockX(),
                        world.get().getSpawnLocation().getBlockY(),
                        world.get().getSpawnLocation().getBlockZ());
                    player.sendMessage(MESSAGE("command.world.spawn.success"));
                }else{
                    plugin.getLogger().error("la valeur 'SPAWN_WORLD' dans le fichier Config est incorrect, la map '" + worldName + "' n'est pas present sur ce serveur ");
                }
                
            }
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
