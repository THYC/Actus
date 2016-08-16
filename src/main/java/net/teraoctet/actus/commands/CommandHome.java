package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.utils.Home;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.Config;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.HOME_TP_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.HOME_TP_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.HOME_TP_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.HOME_TP_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;

public class CommandHome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {       
                   
        if(src instanceof Player && src.hasPermission("actus.home")) { 
            Player player = (Player) src;           
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            String name = "default"; 
            Optional<String> homeName = ctx.<String> getOne("home");

            if(homeName.isPresent()) { 
                name = homeName.get().toLowerCase();
            }
            
            if(aplayer.getHome(name).isPresent()){
                Home home = aplayer.getHome(name).get();
                Text msg = HOME_TP_SUCCESS(player,"");
                if (!name.equalsIgnoreCase("default")){
                    msg = HOME_TP_SUCCESS(player,name);
                }
                serverManager.teleport(player, home.getWorld(), home.getX(), home.getY(), home.getZ(),msg);
                src.sendMessage(MESSAGE("&eVous serez TP dans environ " + Config.COOLDOWN_TO_TP()));

                return CommandResult.success();
            }else{
                src.sendMessage(HOME_NOT_FOUND()); 
                return CommandResult.empty();
            }
        } 
        
        else if (src instanceof ConsoleSource){
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage (NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
