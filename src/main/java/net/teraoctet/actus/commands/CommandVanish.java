package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class CommandVanish implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.vanish")) {
            Optional<Player> target = ctx.<Player> getOne("player");
            Player player = (Player) src;
            
            if(target.isPresent()){
                if(src.hasPermission("actus.vanish.others")){
                    player = target.get();
                }else{
                    src.sendMessage(NO_PERMISSIONS());
                }
            }

            if (player.get(Keys.VANISH).isPresent() && !player.get(Keys.VANISH).get()){
                player.offer(Keys.VANISH, true);
                player.sendMessage(MESSAGE("&eTu es maintenant invisible ..."));
            }else if(player.get(Keys.VANISH).isPresent() && player.get(Keys.VANISH).get()){
                player.offer(Keys.VANISH, false);
                player.sendMessage(MESSAGE("&eTu es de nouveau visible ..."));
            }
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
