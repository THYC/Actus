package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.guildManager;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.FACTION_NAME_MAX_SIZE;
import static net.teraoctet.actus.utils.Config.FACTION_NAME_MIN_SIZE;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.FACTION_RENAMED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_CHARACTERS_NUMBER;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.FACTION_RENAMED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_FACTION;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_CHARACTERS_NUMBER;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuildRename implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.rename")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(guildManager.hasAnyFaction(aplayer)) {
                if(aplayer.getFactionRank() <= 2){
                    String newName = ctx.<String> getOne("name").get();
                    if(newName.length() >= FACTION_NAME_MIN_SIZE() && newName.length() <= FACTION_NAME_MAX_SIZE()) {
                        Guild gguild = getGuild(aplayer.getID_guild());
                        String oldName = gguild.getName();
                        gguild.setName(newName);
                        gguild.update();
                        src.sendMessage(FACTION_RENAMED_SUCCESS(oldName, newName));
                        return CommandResult.success(); 
                    } else {
                        src.sendMessage(WRONG_CHARACTERS_NUMBER(Integer.toString(FACTION_NAME_MIN_SIZE()), Integer.toString(FACTION_NAME_MAX_SIZE())));
                    }
                } else {
                    src.sendMessage(WRONG_RANK());
                }
            } else {
                src.sendMessage(NO_FACTION());
            }
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
