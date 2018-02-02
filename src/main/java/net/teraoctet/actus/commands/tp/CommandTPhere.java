package net.teraoctet.actus.commands.tp;

import static net.teraoctet.actus.Actus.ATPA;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.utils.TPAH;
import net.teraoctet.actus.utils.TaskTP;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandTPhere implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.tpa")) {
            Player player = (Player) src; 
            if(ctx.getOne("player").isPresent()) {
                Player target = ctx.<Player> getOne("player").get();
                if(!target.isOnline()) {
                    src.sendMessage(NOT_FOUND(target.getName()));
                    return CommandResult.empty();
                }
            
                TPAH tpa = new TPAH(target, player,"tphere");
                ATPA.add(tpa);
                final int index = ATPA.indexOf(tpa);

                player.sendMessage(MESSAGE("&eVeuillez patienter, demande envoy\351e ..."));
                target.sendMessage(MESSAGE("&eAcceptez vous que %name% vous t\351l\351porte sur lui ?", player));
                target.sendMessage(MESSAGE("&eVous avez 30s pour accepter cette demande en tapant : /tpaccept"));
                
                TaskTP tp = new TaskTP(player,index);
                tp.run();
                                
                return CommandResult.success();
                
            }else{
                player.sendMessage(USAGE("/tphere <player>"));
                return CommandResult.empty();
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

