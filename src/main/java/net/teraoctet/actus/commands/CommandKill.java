package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.KILLED_BY;
import static net.teraoctet.actus.utils.MessageManager.SUICIDE;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class CommandKill implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        Optional<Player> tplayer = ctx.<Player> getOne("player");
        
        if (tplayer.isPresent() && src.hasPermission("actus.modo.kills.others")) {
            tplayer.get().offer(Keys.HEALTH, 0d);
            getGame().getServer().getBroadcastChannel().send(KILLED_BY(tplayer.get().getName(), src.getName())); 
            return CommandResult.success();
        } 
        
        else if (src.hasPermission("actus.player.kills")){
            if(src instanceof Player) {
                Player player = (Player) src;
                player.offer(Keys.HEALTH, 0d);
                getGame().getServer().getBroadcastChannel().send(SUICIDE(src.getName())); 
                return CommandResult.success();
            } else {
                src.sendMessage(USAGE("/kill <player>"));
            }
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();	 
    }
}
