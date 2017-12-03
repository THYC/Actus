package net.teraoctet.actus.commands.shop;

import java.util.Optional;
import static net.teraoctet.actus.Actus.configBook;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandBankVerse implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.admin.bank.verse")) {
            if(!ctx.getOne("coin").isPresent() || (src instanceof ConsoleSource && !ctx.getOne("player").isPresent())){
                if(src instanceof Player) {
                    src.sendMessage(USAGE("/verse <coin> [player]"));
                } else {
                    src.sendMessage(USAGE("/level <coin> <player>"));
                }
                return CommandResult.empty();
            }

            Optional<Integer> coin = ctx.<Integer> getOne("coin");
            Player player;
            APlayer aplayer;
            
            if(ctx.getOne("player").isPresent()) {
                player = ctx.<Player> getOne("player").get();              
            }else{
                player = (Player)src;            
            }
            
            aplayer = getAPlayer(player.getIdentifier());
            aplayer.setMoney(aplayer.getMoney() + coin.get());
            aplayer.update();
            commit();
            
            if(player.isOnline()){
                player.sendMessage(MESSAGE("&6Tu disposes de " + String.valueOf(aplayer.getMoney()) + " Emeraude(s)"));
            }else{
                configBook.SendBookMessage(player, MESSAGE("Tu as recu du serveur la somme de " + coin.get() + "\nTu disposes maintenant de " + String.valueOf(aplayer.getMoney()) + " Emeraude(s)"));
            }
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
