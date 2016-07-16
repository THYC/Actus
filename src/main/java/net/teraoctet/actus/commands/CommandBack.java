package net.teraoctet.actus.commands;

import net.teraoctet.actus.utils.DeSerialize;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.DeSerialize.getLocation;
import net.teraoctet.actus.player.APlayer;
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
                
        if (src instanceof Player && src.hasPermission("actus.back")){           
            Player player = (Player) src;
            APlayer aplayer = Data.getAPlayer(player.getUniqueId().toString());
            Location<World> location = getLocation(aplayer.getLastposition());
            aplayer.setLastposition(DeSerialize.location(player.getLocation()));
            aplayer.update();
                
            if (player.getLocation().getExtent().getUniqueId().equals(location.getExtent().getUniqueId())) {
                player.setLocation(location);
            } else {
                player.transferToWorld(location.getExtent(), location.getPosition());
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
