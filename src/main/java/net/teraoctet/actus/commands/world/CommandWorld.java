package net.teraoctet.actus.commands.world;

import static net.teraoctet.actus.Actus.CB_BOOK;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandWorld implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if (src instanceof Player && src.hasPermission("actus.admin.world.worldcreate")){
            
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder();

            builder.title(Text.builder().append(MESSAGE("&eWORLD")).toText())
            .contents(
                    Text.builder().append(MESSAGE("&9/world create <name> : &7cr\351ation d'un monde"))
                        .onClick(TextActions.suggestCommand("/world create <WorldNameHere> -d minecraft:overworld -g default -e survival -y minecraft:normal"))    
                        .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                    Text.builder().append(MESSAGE("&9/world tp <name> : &7TP sur le spawn du monde"))
                        .onClick(TextActions.suggestCommand("/portal tp "))    
                        .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText(),
                    Text.builder().append(MESSAGE("&9/world load <name> : &7Charge le monde"))
                        .onClick(TextActions.suggestCommand("/world load "))    
                        .onHover(TextActions.showText(MESSAGE("Click lancer la commande"))).toText())
            .header(Text.builder().append(MESSAGE("&o&eUsage:")).toText())
            .footer(Text.builder().append(MESSAGE("&o&9 Besoin d'aide ?"))
                            .onClick(TextActions.executeCallback(CB_BOOK.callHelpBook("help_world")))    
                            .onHover(TextActions.showText(MESSAGE("Click pour afficher l'aide"))).toText())
            .padding(MESSAGE("&9-"))
            .sendTo(src);

            return CommandResult.success();
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
