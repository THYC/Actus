package net.teraoctet.actus.commands.grave;

import static net.teraoctet.actus.Actus.CB_GRAVE;
import static net.teraoctet.actus.Actus.configGrave;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandGraveList implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("actus.admin.grave")) {
            Player player = (Player)src;
            if(!configGrave.getListGrave().isEmpty()){
                src.sendMessage(MESSAGE("&e------------------------------"));
                src.sendMessage(MESSAGE("&e     Liste des tombes"));
                src.sendMessage(MESSAGE("&e------------------------------"));
                configGrave.getListGrave().stream().forEach((grave) -> {
                    src.sendMessages(Text.builder()
                            .append(MESSAGE("&d[i] "))
                            .onClick(TextActions.executeCallback(CB_GRAVE.callInfoGrave(grave)))
                            .onHover(TextActions.showText(MESSAGE("&eInfo"))).build()
                            .concat(Text.builder()
                            .append(MESSAGE("&a - " + grave))
                            .onClick(TextActions.executeCallback(CB_GRAVE.callTPGrave(grave)))
                            .onHover(TextActions.showText(MESSAGE("&eClick ici pour te TP sur la tombe"))).build())
                            .concat(Text.builder()
                            .append(MESSAGE(" &e[Oo] "))
                            .onClick(TextActions.executeCallback(CB_GRAVE.callOpenGrave(grave)))
                            .onHover(TextActions.showText(MESSAGE("&eRegarde le contenu de la tombe"))).build())
                            .concat(Text.builder()
                            .append(MESSAGE(" &b[Crypte] "))
                            .onClick(TextActions.executeCallback(CB_GRAVE.callMoveGrave(grave)))
                            .onHover(TextActions.showText(MESSAGE("&eDeplace la tombe dans la crypte"))).build())
                            .concat(Text.builder()
                            .append(MESSAGE("&4[Del]"))
                            .onClick(TextActions.executeCallback(CB_GRAVE.callDelGrave(grave)))
                            .onHover(TextActions.showText(MESSAGE("&4Click ici pour supprimer la tombe"))).build()));
                });
                return CommandResult.success();
                
            }else{
                src.sendMessage(MESSAGE("&6Aucune tombe active"));
                return CommandResult.success();
            }
        }else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
       
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
