package net.teraoctet.actus.commands;

import java.util.Optional;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.DeSerialize.getLocation;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.TP_BACK;

public class CommandBack implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
                
        if (src instanceof Player && src.hasPermission("actus.modo.back")){           
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional<Location<World>> location = getLocation(aplayer.getLastposition());
            if(!location.isPresent()){
                player.sendMessage(MESSAGE("&eAucun point d'enregistr\351 en derni\350re position"));
                return CommandResult.empty();
            }
            aplayer.setLastposition(DeSerialize.location(player.getLocation()));
            aplayer.update();
                
            if (player.getLocation().getExtent().getUniqueId().equals(location.get().getExtent().getUniqueId())) {
                player.setLocation(location.get());
            } else {
                player.transferToWorld(location.get().getExtent(), location.get().getPosition());
            }		
            src.sendMessage(TP_BACK(player));
            return CommandResult.success();
	} 
       
        else if(src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
	}
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
	return CommandResult.empty();
    }
}
