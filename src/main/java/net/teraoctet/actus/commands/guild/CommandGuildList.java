package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.guildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.FACTION_MAX_NUMBER_OF_MEMBER;
import static net.teraoctet.actus.utils.Data.getFactions;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.ONHOVER_FACTION_LIST_LVL10;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.ONHOVER_FACTION_LIST_LVL10;

public class CommandGuildList implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.list")) {
            src.sendMessage(MESSAGE("&6&lListe des guilds :"));
            
            for (Integer id_guild : getFactions().keySet()) {
                String guildName = getFactions().get(id_guild).getName();
                String ownerName = guildManager.getOwner(id_guild).getName();
                int guildSize = guildManager.getFactionPlayers(id_guild).size();
                APlayer aplayer = getAPlayer(src.getIdentifier());
                int level = aplayer.getLevel();
                
                if(level == 10) {
                    src.sendMessage(Text.builder().append(MESSAGE("&e- ID: " + id_guild + " &r" + guildName + "&r&e (" + guildSize + "/" + FACTION_MAX_NUMBER_OF_MEMBER() + ")"))
                            .onShiftClick(TextActions.insertText("/guild delete " + guildName))
                            .onHover(TextActions.showText(ONHOVER_FACTION_LIST_LVL10(guildName, ownerName))).toText());
                } else {
                    src.sendMessage(Text.builder().append(MESSAGE("&e- &r" + guildName + "&r&e (" + guildSize + "/" + FACTION_MAX_NUMBER_OF_MEMBER() + ")"))
                            .onHover(TextActions.showText(MESSAGE("Chef : " + ownerName))).toText());
                }
            }
            
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
