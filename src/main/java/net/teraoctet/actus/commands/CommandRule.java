package net.teraoctet.actus.commands;

import static net.teraoctet.actus.utils.MessageManager.RULES;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class CommandRule implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        src.sendMessage(RULES());
        return CommandResult.empty();
    }
}
