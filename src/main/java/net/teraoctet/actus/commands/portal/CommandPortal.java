package net.teraoctet.actus.commands.portal;

import static net.teraoctet.actus.Actus.CB_BOOK;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.action.TextActions;

public class CommandPortal implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.portal")) { 
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();
            
            builder.title(Text.builder().append(MESSAGE("&ePortal")).toText())
            .contents(Text.builder().append(MESSAGE("&9/portal create <name> : &7cr\351ation d'un portail au point d\351clar\351"))
                    .onClick(TextActions.suggestCommand("/portal create "))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                Text.builder().append(MESSAGE("&9/portal remove <name> : &7supprime le portail"))
                    .onClick(TextActions.suggestCommand("/portal remove "))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                Text.builder().append(MESSAGE("&9/portal tpfrom  <name> : &7enregistre le point d'arriv\351 du portail"))
                    .onClick(TextActions.suggestCommand("/portal tpfrom "))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                Text.builder().append(MESSAGE("&9/portal list : &7liste les portails"))
                    .onClick(TextActions.runCommand("/portal list"))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                Text.builder().append(MESSAGE("&9/portal message <name> : &7affiche le message d'arriv\351e du portail"))
                    .onClick(TextActions.suggestCommand("/portal message"))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                Text.builder().append(MESSAGE("&9/portal message <name> <message> : &7modifie le message d'arriv\351e du portail"))
                    .onClick(TextActions.suggestCommand("/portal nom message "))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                Text.builder().append(MESSAGE("&9/portal cmd <command> : &7Execute la commande au passage du portail"))
                    .onClick(TextActions.suggestCommand("/portal cmd nom command"))    
                    .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText())
            .footer(Text.builder().append(MESSAGE("&o&9 Besoin d'aide ?"))
                            .onClick(TextActions.executeCallback(CB_BOOK.callHelpBook("help_portal")))    
                            .onHover(TextActions.showText(MESSAGE("Click pour afficher l'aide"))).toText())
            .padding(MESSAGE("&9-"))
            .sendTo(src);

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