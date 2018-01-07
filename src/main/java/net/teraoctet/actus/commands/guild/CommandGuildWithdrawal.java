package net.teraoctet.actus.commands.guild;

import static net.teraoctet.actus.Actus.gdm;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.GUILD_MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WITHDRAW_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuildWithdrawal implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.withdrawal")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(GuildManager.hasAnyGuild(aplayer)) {
                if(aplayer.getGuildRank() <= 2){
                    double amount = ctx.<Double> getOne("amount").get();
                    Guild gguild = getGuild(aplayer.getID_guild());
                    double guildMoney = gguild.getMoney();
                    
                    if(guildMoney >= amount) {
                        double playerMoney = aplayer.getMoney();

                        gguild.setMoney(guildMoney - amount);
                        playerMoney = playerMoney + amount;
                        aplayer.setMoney(playerMoney);
                        aplayer.update();
                        gguild.update();

                        src.sendMessage(WITHDRAW_SUCCESS(Double.toString(amount)));
                        //AJOUTER NOTIFICATION DE LE CANAL GUILD
                        return CommandResult.success();
                    } else {
                        src.sendMessage(GUILD_MISSING_BALANCE());
                    }
                } else {
                    src.sendMessage(WRONG_RANK());
                }
            } else {
                src.sendMessage(NO_GUILD());
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
