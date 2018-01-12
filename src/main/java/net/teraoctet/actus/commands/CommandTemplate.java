package net.teraoctet.actus.commands;

import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandTemplate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.")) {
            //remplir "src.hasPermission("actus.")" avec le nom de la permission de la commande
            //      -------------------------------------------
            //si la console peut utiliser la commande :
            //  - supprimer "src instanceof Player"
            //  - supprimer "else if (src instanceof ConsoleSource)"            
            
            //CODER LA COMMANDE ICI
            
            return CommandResult.success();
        } else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
