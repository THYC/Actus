package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.guildManager;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.FACTION_DELETED_NOTIFICATION;
import static net.teraoctet.actus.utils.MessageManager.FACTION_DELETED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_NAME;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.FACTION_DELETED_NOTIFICATION;
import static net.teraoctet.actus.utils.MessageManager.FACTION_DELETED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_NAME;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuildDelete implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.delete")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(guildManager.hasAnyFaction(aplayer)) {
                if(guildManager.isOwner(aplayer)){
                    String name = ctx.<String> getOne("name").get();
                    Guild gguild = getGuild(aplayer.getID_guild());
                    String guildName = gguild.getName();
                    
                    if(guildName.toLowerCase().contains(name.toLowerCase())) {
                        int id_guild = aplayer.getID_guild();
                        guildManager.removeFaction(id_guild);
                        src.sendMessage(FACTION_DELETED_SUCCESS(guildName));
                        getGame().getServer().getBroadcastChannel().send(FACTION_DELETED_NOTIFICATION(guildName));
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WRONG_NAME());
                    }
                } else {
                    src.sendMessage(WRONG_RANK());
                }
            } else {
                src.sendMessage(NO_FACTION());
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
