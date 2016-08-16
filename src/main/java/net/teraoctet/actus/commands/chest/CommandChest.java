package net.teraoctet.actus.commands.chest;

import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;

public class CommandChest implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.chest")) {
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder();  

            builder.title(MESSAGE("&6Chest"))
                .contents(
                        MESSAGE("&e/chest add &b<joueur> : &7Ajoute les droits d'utilisation a un joueur"),
                        MESSAGE("&e/chest remove &b<joueur> : &7Retire les droits d'un joueur"),
                        MESSAGE("&e/chest remove : &7Rend le coffre public"),
                        MESSAGE("&e/chest info : &7Liste les utilisateurs du coffre"))
                .header(MESSAGE("&eUsage:"))
                .padding(Text.of("-"))
                .sendTo(src); 
            
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
