package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.gdm;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.LEAVING_GUILD_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.OWNER_CANNOT_LEAVE;

public class CommandGuildLeave implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.guild.setowner")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            int id_guild = aplayer.getID_guild();
            Guild gguild = getGuild(id_guild);
            
            if(GuildManager.hasAnyGuild(aplayer)) {
                if(GuildManager.isOwner(aplayer)) {
                    if(gdm.getGuildPlayers(id_guild).size() > 1) {
                        src.sendMessage(OWNER_CANNOT_LEAVE());
                    } else {
                        getGame().getCommandManager().process(src, "guild delete " + gguild.getName());
                    }
                } else {
                    String guildName = gguild.getName();
                    aplayer.setID_guild(0);
                    aplayer.setFactionRank(0);
                    aplayer.update();
                    src.sendMessage(LEAVING_GUILD_SUCCESS(guildName));
                    //ENVOYER UNE NOTIFICATION DANS LE CANAL DE GUILDE
                    return CommandResult.success();
                }
            } else {
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
