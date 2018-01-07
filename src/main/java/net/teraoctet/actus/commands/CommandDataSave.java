package net.teraoctet.actus.commands;

import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;

public class CommandDataSave implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.admin.data")) {
            Data.commit();
            src.sendMessage(MESSAGE("&7Data sauvegard\351"));
            return CommandResult.success();
        } 
                
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
