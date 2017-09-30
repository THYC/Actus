package net.teraoctet.actus.commands;

import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandInvsee implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("actus.invsee")) {
            Player target = ctx.<Player> getOne("target").get();
            Player player = (Player) src; 
            player.openInventory(target.getInventory());
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