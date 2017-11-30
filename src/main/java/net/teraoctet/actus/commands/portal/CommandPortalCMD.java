package net.teraoctet.actus.commands.portal;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plm;
import net.teraoctet.actus.portal.Portal;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPortalCMD implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.admin.portal")) { 
            Player player = (Player) src;
            
            if(ctx.getOne("name").isPresent()) { 
                String name = ctx.<String> getOne("name").get();
                
                if (plm.hasPortal(name) == true){
                    Portal portal = plm.getPortal(name).get();
                    
                    Optional<String> cmd = ctx.<String> getOne("cmd");

                    if(!ctx.<String> getOne("cmd").isPresent()){
                        Text msg = MESSAGE(portal.getCMD());
                        player.sendMessage(msg); 
                        return CommandResult.success();

                    } else {
                        String[] args = cmd.get().split(" ");
                        String command = "";
                        for(int i = 0; i < args.length; i++){
                            command = command + args[i] + " ";
                        }
                        Text msg = MESSAGE(command);
                        portal.setCMD(command);
                        portal.update();
                        Data.commit();
                        player.sendMessage(MESSAGE("&cCmd enregistr\351 :"));
                        player.sendMessage(msg);
                        return CommandResult.success();
                    }  
                } else {
                    player.sendMessage(NOT_FOUND(name));
                    return CommandResult.empty();
                }
            } else {
                player.sendMessage(USAGE("/portal cmd <name> : affiche la commande du portail"));
                player.sendMessage(USAGE("/portal cmd <name> [command]: modifie la commande du portail"));
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