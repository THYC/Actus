package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static net.teraoctet.actus.utils.MessageManager.INVENTORY_CLEARED;
import static net.teraoctet.actus.utils.MessageManager.CLEARINVENTORY_SUCCESS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
    
public class CommandClearinventory implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        Optional<Player> player = ctx.<Player> getOne("player");
        
        if (player.isPresent() && src.hasPermission("actus.admin.clearinventory.others")) { 
            player.get().getInventory().clear(); 
            player.get().sendMessage(INVENTORY_CLEARED());
            src.sendMessage(CLEARINVENTORY_SUCCESS(player.get().getName()));
            return CommandResult.success();
        } 
        
        else if (src.hasPermission("actus.player.clearinventory")){
            if(src instanceof Player){
                Player senderPlayer = (Player)src;
                senderPlayer.getInventory().clear();
                senderPlayer.sendMessage(INVENTORY_CLEARED());
                return CommandResult.success();
            } else {
                src.sendMessage(USAGE("/clearinventory <player>"));
            }
        } 
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
