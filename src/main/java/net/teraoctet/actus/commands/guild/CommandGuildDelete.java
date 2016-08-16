package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.guildManager;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.GUILD_DELETED_NOTIFICATION;
import static net.teraoctet.actus.utils.MessageManager.GUILD_DELETED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuildDelete implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.delete")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            if(aplayer.getID_guild() != 0){
                if(aplayer.getGuildRank() == 1){
                    Guild guild = getGuild(aplayer.getID_guild());
                    guildManager.delGuild(aplayer.getID_guild());
                    src.sendMessage(GUILD_DELETED_SUCCESS(guild.getName()));
                    getGame().getServer().getBroadcastChannel().send(GUILD_DELETED_NOTIFICATION(guild.getName()));
                    return CommandResult.success();
                }else{
                    src.sendMessage(WRONG_RANK());
                }
            }else{
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
