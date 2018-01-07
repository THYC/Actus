package net.teraoctet.actus.commands;

import java.util.HashMap;
import java.util.Optional;
import static net.teraoctet.actus.utils.Data.commit;
import net.teraoctet.actus.utils.Home;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.NB_ALLOWED_HOME;
import static net.teraoctet.actus.utils.MessageManager.HOME_SET_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.HOME_ALREADY_EXIST;
import static net.teraoctet.actus.utils.MessageManager.NB_HOME;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;


public class CommandSetHome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.sethome")) { 
            Player player = (Player) src;
            Optional<String> homeName = ctx.<String> getOne("home");
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            String name = "default"; 
            
            if(homeName.isPresent()) name = homeName.get().toLowerCase();

            HashMap<String, Home> homes = aplayer.getHomes();
            if(homes.containsKey(name)) {
                src.sendMessage(HOME_ALREADY_EXIST());
                return CommandResult.empty(); 
            }

            int nbHomes = 0;
            for(int i = 1; i <= 100; i++) {if(player.hasPermission("actus.sethome." + i)) nbHomes = i;}

            if(!player.hasPermission("actus.home.unlimited") && nbHomes <= homes.size()) {
                src.sendMessage(NB_ALLOWED_HOME(player,String.valueOf(nbHomes)));
                return CommandResult.empty(); 
            }

            String world = player.getWorld().getName();
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();

            Home home = new Home(aplayer.getUUID(), name, world, x, y, z);
            home.insert();
            commit();
            aplayer.setHome(name, home);

            if(name.equalsIgnoreCase("default")){
                src.sendMessage(HOME_SET_SUCCESS(player,""));
            } else {
                src.sendMessage(HOME_SET_SUCCESS(player,name));
            }

            if(player.hasPermission("actus.home.unlimited")) src.sendMessage(NB_HOME(player,String.valueOf(homes.size()),"illimité"));
            else src.sendMessage(NB_HOME(player,String.valueOf(homes.size()),String.valueOf(nbHomes)));  
            
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
