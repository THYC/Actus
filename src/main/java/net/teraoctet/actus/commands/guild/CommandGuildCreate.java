package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.guildManager;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.FACTION_NAME_MAX_SIZE;
import static net.teraoctet.actus.utils.Config.FACTION_NAME_MIN_SIZE;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_FACTION_MEMBER;
import static net.teraoctet.actus.utils.MessageManager.FACTION_CREATED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_CHARACTERS_NUMBER;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_FACTION_MEMBER;
import static net.teraoctet.actus.utils.MessageManager.FACTION_CREATED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_CHARACTERS_NUMBER;

public class CommandGuildCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.create")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getIdentifier());
            
            if(guildManager.hasAnyFaction(aplayer)) {
                src.sendMessage(ALREADY_FACTION_MEMBER());
            } else {
                String guildName = ctx.<String> getOne("name").get();
                if(guildName.length() >= FACTION_NAME_MIN_SIZE() && guildName.length() <= FACTION_NAME_MAX_SIZE()) {
                    int key = guildManager.newKey();
                    Guild gguild = new Guild(key, guildName,"N",0,0,0,0,0,0,0);
                    aplayer.setFactionRank(1);
                    aplayer.setID_guild(key);
                    aplayer.update();
                    gguild.insert();
                    Data.commit();
                    Data.addGuild(key, gguild); 
                    src.sendMessage(FACTION_CREATED_SUCCESS(guildName));
                    return CommandResult.success();
                } else {
                    src.sendMessage(WRONG_CHARACTERS_NUMBER(Integer.toString(FACTION_NAME_MIN_SIZE()), Integer.toString(FACTION_NAME_MAX_SIZE())));
                }
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
