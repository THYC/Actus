package net.teraoctet.actus.commands.guild;

import java.util.List;
import static net.teraoctet.actus.Actus.gdm;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;

public class CommandGuildMemberslist implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.guild.memberslist")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            //si le joueur est membre d'une guild
            if(GuildManager.hasAnyGuild(aplayer)) {
                Guild gguild = getGuild(aplayer.getID_guild());
                List list = gdm.getGuildPlayers(gguild.getID());
                
                src.sendMessage(MESSAGE("&2Listes des membres de " + gguild.getName() + " : &a" + list));
                
                
                //src.sendMessage(MESSAGE("&2Listes des membres de " + gguild.getName() + " : &a" + String.valueOf(gdm.getPlayers(gguild.getID()).size())));
                return CommandResult.success();
            }
            
            //si le joueur n'est dans aucune guild
            else {
                src.sendMessage(NO_GUILD());
            }
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
