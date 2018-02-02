package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.ATPA;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import net.teraoctet.actus.utils.TPAH;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandGuildAccept implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.guild.accept")) {
            Player player = (Player) src; 
            Player toPlayer = null;
            Player fromPlayer = null;
            int i = 0;
            int index = 0;                  
                    
            for(TPAH guild:ATPA){
                if(guild.getToPlayer() == player && "guild".equals(guild.getType())){
                    fromPlayer = guild.getFromPlayer();
                    toPlayer = player;
                    index = i;
                }
                i++;
            }
            
            if (fromPlayer == null && toPlayer == null){
                player.sendMessage(MESSAGE("&eAucune invitation en cours !"));
                return CommandResult.success();
            }
            
            APlayer aplayer = getAPlayer(player.getIdentifier());
            APlayer faplayer = getAPlayer(fromPlayer.getIdentifier());
            
            aplayer.setID_guild(faplayer.getID_guild());
            src.sendMessage(MESSAGE("&eVous etes maintenant un membre de la guild " + Data.getGuild(faplayer.getID_guild()).getName()));
            fromPlayer.sendMessage(MESSAGE("&e" + player.getName() + " a accept\351 l'invitation !"));
            ATPA.remove(index);
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
