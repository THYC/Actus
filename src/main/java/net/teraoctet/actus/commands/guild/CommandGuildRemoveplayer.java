package net.teraoctet.actus.commands.guild;

import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getUUID;
import static net.teraoctet.actus.utils.ServerManager.isOnline;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.CANNOT_EJECT_OWNER;
import static net.teraoctet.actus.utils.MessageManager.DATA_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.GUILD_MEMBER_REMOVED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.GUILD_RETURNED_BY;
import static net.teraoctet.actus.utils.MessageManager.NOT_IN_SAME_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuildRemoveplayer implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.removeplayer")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(GuildManager.hasAnyGuild(aplayer)) {
                int playerRank = aplayer.getGuildRank();
                if(playerRank <= 2) {
                    String targetName = ctx.<String> getOne("name").get();
                    String targetUUID = getUUID(targetName);
                    
                    if(targetUUID == null){
                        src.sendMessage(DATA_NOT_FOUND(targetName));
                    } else {
                        APlayer target_aplayer = getAPlayer(targetUUID);
                        int target_id_guild = target_aplayer.getID_guild();
                        int player_id_guild = aplayer.getID_guild();

                        if(player_id_guild == target_id_guild) {
                            if(playerRank == 2 && target_aplayer.getGuildRank() <= 2) {
                                src.sendMessage(CANNOT_EJECT_OWNER());
                            } else {
                                target_aplayer.setID_guild(0);
                                target_aplayer.setFactionRank(0);
                                target_aplayer.update();

                                if(isOnline(targetName)) { 
                                    Player targetPlayer = getGame().getServer().getPlayer(targetName).get();
                                    targetPlayer.sendMessage(GUILD_RETURNED_BY(src.getName()));
                                } else {  
                                    //ENVOYER UN MAIL AU JOUEUR QUI A ETE RENVOYE
                                }

                                //ENVOYER UNE NOTIFICATION DANS LE CANAL DE GUILD
                                src.sendMessage(GUILD_MEMBER_REMOVED_SUCCESS(targetName));
                                return CommandResult.success();
                            }
                        } else {
                            src.sendMessage(NOT_IN_SAME_GUILD(targetName));
                        } 
                    }
                } else {
                    src.sendMessage(WRONG_RANK());
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
