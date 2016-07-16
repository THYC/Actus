package net.teraoctet.actus.commands.portal;

import java.util.Optional;
import static net.teraoctet.actus.Actus.portalManager;
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

public class CommandPortalMsg implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.admin.portal")) { 
            Player player = (Player) src;
            
            // on vérifie que le joueur est bien renseigné la paramètre name
            if(ctx.getOne("name").isPresent()) { 
                String name = ctx.<String> getOne("name").get();
                
                // on vérifie que le portail existe
                if (portalManager.hasPortal(name) == true){
                    Portal portal = portalManager.getPortal(name).get();
                    Optional<String> arguments = ctx.<String> getOne("arguments");

                    // si le joueur n'a pas tapé d'arguments on affiche le message existant
                    if(!ctx.<String> getOne("arguments").isPresent()){
                        Text msg = MESSAGE(portal.getMessage());
                        player.sendMessage(msg); 
                        return CommandResult.success();

                    // sinon on remplace le message par les arguments
                    } else {
                        String[] args = arguments.get().split(" ");
                        String smsg = "";
                        for(int i = 1; i < args.length; i++){
                            smsg = smsg + args[i] + " ";
                        }
                        Text msg = MESSAGE(smsg);
                        portal.setMessage(smsg);
                        portal.update();
                        Data.commit();
                        player.sendMessage(MESSAGE("&cVotre nouveau message :"));
                        player.sendMessage(msg);
                        return CommandResult.success();
                    }  
                } else {
                    player.sendMessage(NOT_FOUND(name));
                    return CommandResult.empty();
                }
            } else {
                player.sendMessage(USAGE("/portal msg <name> : affiche le message d'accueil du portail"));
                player.sendMessage(USAGE("/portal msg <name> [message]: modifie le message d'accueil du portail"));
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