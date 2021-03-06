package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.gdm;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Config.GUILD_NAME_MAX_SIZE;
import static net.teraoctet.actus.utils.Config.GUILD_NAME_MIN_SIZE;
import net.teraoctet.actus.utils.Data;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_GUILD_MEMBER;
import static net.teraoctet.actus.utils.MessageManager.GUILD_CREATED_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_CHARACTERS_NUMBER;
import org.spongepowered.api.service.permission.SubjectData;

public class CommandGuildCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.guild.create")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getIdentifier());
            
            if(GuildManager.hasAnyGuild(aplayer)) {
                src.sendMessage(ALREADY_GUILD_MEMBER());
            } else {
                String guildName = ctx.<String> getOne("name").get();
                if(guildName.length() >= GUILD_NAME_MIN_SIZE() && guildName.length() <= GUILD_NAME_MAX_SIZE()) {
                    int key = gdm.newKey();
                    Guild gguild = new Guild(key, guildName,"N",0,0,0,0,0,0,0);
                    aplayer.setFactionRank(1);
                    aplayer.setID_guild(key);
                    aplayer.update();
                    gguild.insert();
                    Data.commit();
                    Data.addGuild(key, gguild);
                    player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT,"prefix","[" + guildName + "]");
                    src.sendMessage(GUILD_CREATED_SUCCESS(guildName));
                    return CommandResult.success();
                } else {
                    src.sendMessage(WRONG_CHARACTERS_NUMBER(Integer.toString(GUILD_NAME_MIN_SIZE()), Integer.toString(GUILD_NAME_MAX_SIZE())));
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
