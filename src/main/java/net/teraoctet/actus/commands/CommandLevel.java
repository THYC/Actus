package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandLevel implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src.hasPermission("actus.admin.level")) { 
            if(!ctx.getOne("level").isPresent() || (src instanceof ConsoleSource && !ctx.getOne("player").isPresent())){
                if(src instanceof Player) {
                    src.sendMessage(USAGE("/level <level> [player]"));
                } else {
                    src.sendMessage(USAGE("/level <level> <player>"));
                }
                return CommandResult.empty();
            }

            Optional<Integer> level = ctx.<Integer> getOne("level");
            Player player = null;
            APlayer aplayer = null;
            
            if(ctx.getOne("player").isPresent()) {
                player = ctx.<Player> getOne("player").get();
                if(!player.isOnline()) {
                    src.sendMessage(NOT_FOUND(player.getName()));
                    return CommandResult.empty();
                }
            } else {
                player = (Player) src; 
            }

            aplayer = getAPlayer(player.getUniqueId().toString()); 
            aplayer.setLevel(level.get());
            src.sendMessage(MESSAGE("&6%name% est mont\351e au level %var1%",player,String.valueOf(level.get())));
            return CommandResult.success();
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
 
        return CommandResult.empty();
    }
}