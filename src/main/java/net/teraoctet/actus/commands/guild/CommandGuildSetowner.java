package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.guildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.FACTION_CHEF_GRADE_GIVEN;
import static net.teraoctet.actus.utils.MessageManager.FACTION_NEW_CHEF;
import static net.teraoctet.actus.utils.MessageManager.FACTION_YOU_ARE_NEW_CHEF;
import static net.teraoctet.actus.utils.MessageManager.NOT_IN_SAME_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.FACTION_CHEF_GRADE_GIVEN;
import static net.teraoctet.actus.utils.MessageManager.FACTION_NEW_CHEF;
import static net.teraoctet.actus.utils.MessageManager.FACTION_YOU_ARE_NEW_CHEF;
import static net.teraoctet.actus.utils.MessageManager.NOT_IN_SAME_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuildSetowner implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.setowner")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(guildManager.hasAnyFaction(aplayer)) {
                if(guildManager.isOwner(aplayer)) {
                    Player targetPlayer = ctx.<Player> getOne("player").get();
                    APlayer target_aplayer = getAPlayer(targetPlayer.getIdentifier());
                    int target_id_guild = target_aplayer.getID_guild();
                    int player_id_guild = aplayer.getID_guild();
                    
                    if(player_id_guild == target_id_guild) {
                        String guildName = getGuild(player_id_guild).getName();
                        aplayer.setFactionRank(2);
                        target_aplayer.setFactionRank(1);
                        aplayer.update();
                        target_aplayer.update();
                        src.sendMessage(FACTION_CHEF_GRADE_GIVEN(targetPlayer.getName()));
                        targetPlayer.sendMessage(FACTION_YOU_ARE_NEW_CHEF());
                        getGame().getServer().getBroadcastChannel().send(FACTION_NEW_CHEF(targetPlayer.getName(), guildName));
                        return CommandResult.success();   
                    } else {
                        src.sendMessage(NOT_IN_SAME_FACTION(targetPlayer.getName()));
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
