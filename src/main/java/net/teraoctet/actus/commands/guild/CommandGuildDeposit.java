package net.teraoctet.actus.commands.guild;

import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Data.getGuild;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.DEPOSIT_SUCCESS;

public class CommandGuildDeposit implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.guild.deposit")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            if(GuildManager.hasAnyGuild(aplayer)) {
                double amount = ctx.<Double> getOne("amount").get();
                double playerMoney = aplayer.getMoney();
                
                if(playerMoney >= amount){
                    Guild guild = getGuild(aplayer.getID_guild());
                    
                    playerMoney = playerMoney - amount;
                    aplayer.setMoney(playerMoney);
                    guild.setMoney(guild.getMoney() + amount);
                    aplayer.update();
                    guild.update();
                    
                    src.sendMessage(DEPOSIT_SUCCESS(Double.toString(amount)));
                    return CommandResult.success();
                } else {
                    src.sendMessage(MISSING_BALANCE());
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
