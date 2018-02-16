package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.ATPA;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NOT_CONNECTED;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.utils.TPAH;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandGuildInvit implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.guild.invit")) {
            Player player = (Player)src;
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(GuildManager.hasAnyGuild(aplayer)) {
                
                if(ctx.getOne("player").isPresent()) {
                    User user = ctx.<User> getOne("player").get();
                    
                    if(!user.isOnline()) {
                        src.sendMessage(NOT_CONNECTED(user.getName()));
                        return CommandResult.empty();
                    }
               
                    TPAH invit = new TPAH(player, user.getPlayer().get(),"guild");
                    ATPA.add(invit);

                    player.sendMessage(MESSAGE("&edemande envoy\351e ..."));
                    user.getPlayer().get().sendMessage(MESSAGE("&eTu es invit\351 a rejoindre la guild " + Data.getGuild(aplayer.getID_guild()).getName())); 
                    user.getPlayer().get().sendMessage(Text.builder().append(MESSAGE("&bclick ici pour accepter cette demande")).onClick(TextActions.runCommand("/guild accept")).build().toText());

                    return CommandResult.success();
                }else{
                    src.sendMessage(USAGE("/guild invit <player>"));
                }    
            } else {
                src.sendMessage(NO_GUILD());
            }
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }else {
            src.sendMessage(NO_PERMISSIONS());
        }               
        return CommandResult.empty();
    }
}
