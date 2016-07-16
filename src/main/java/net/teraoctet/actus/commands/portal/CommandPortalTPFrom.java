package net.teraoctet.actus.commands.portal;

import static net.teraoctet.actus.Actus.portalManager;
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

public class CommandPortalTPFrom implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.admin.portal")) { 
            Player player = (Player) src;
        
            if(!ctx.getOne("name").isPresent()) { 
               player.sendMessage(USAGE("/portal tpfrom <name> : enregistre le point d'apparition du portail"));
               return CommandResult.empty();
            }

            String name = ctx.<String> getOne("name").get();  
            if (portalManager.hasPortal(name) == true){
                Portal portal = portalManager.getPortal(name).get();
                portal.settoworld(player.getWorld().getName());
                portal.settoX(player.getLocation().getBlockX());
                portal.settoY(player.getLocation().getBlockY());
                portal.settoZ(player.getLocation().getBlockZ());
                portal.update();
                Data.commit();
                player.sendMessage(MESSAGE("&e" + portal.getName() + ": &a point d'arriv\351 enregistr\351"));
            } else {
                player.sendMessage(NOT_FOUND(name));
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