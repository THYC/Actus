package net.teraoctet.actus.commands.portal;

import static net.teraoctet.actus.Actus.plm;
import net.teraoctet.actus.portal.Portal;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPortalRemove implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.admin.portal")) { 
            Player player = (Player) src;
        
            if(!ctx.getOne("name").isPresent()) { 
               player.sendMessage(USAGE("/portal remove <name> : supprime un portail"));
               return CommandResult.empty();
            }

            String name = ctx.<String> getOne("name").get();
            if (plm.hasPortal(name) == true){
                Portal portal = plm.getPortal(name).get();
                portal.delete();
                Data.commit();
                Data.removePortal(portal);
                player.sendMessage(MESSAGE("&e" + portal.getName() + " &aa \351t\351 supprim\351"));
            } else {
                player.sendMessage(NOT_FOUND(name));
                return CommandResult.empty();
            }
        } else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE()); 
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
        return CommandResult.empty();
    }
}