package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import net.teraoctet.actus.utils.Home;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.utils.MessageManager.HOME_DEL_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.HOME_NOT_FOUND;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandDelhome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.delhome")) {
            Player player = (Player) src;  
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            String name = "default"; 
            Optional<String> homeName = ctx.<String> getOne("home");

            if(homeName.isPresent()) { 
                name = homeName.get().toLowerCase();
            }
            if(aplayer.getHome(name).isPresent()){
                Home home = aplayer.getHome(name).get();
                home.delete();
                aplayer.removeHome(name);
                commit();
                src.sendMessage(HOME_DEL_SUCCESS(player,""));
                return CommandResult.success();
            } else {
                src.sendMessage(HOME_NOT_FOUND()); 
            }
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
