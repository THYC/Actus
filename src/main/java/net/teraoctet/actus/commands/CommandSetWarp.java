package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.utils.Data.WARPS;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.WARP_ALREADY_EXIST;
import static net.teraoctet.actus.utils.MessageManager.WARP_SET_SUCCESS;
import net.teraoctet.actus.warp.Warp;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;


public class CommandSetWarp implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.setwarp")) { 
            Player player = (Player) src;
            Optional<String> namewarp = ctx.<String> getOne("warp");
            String name = ""; 
            
            if(namewarp.isPresent()) name = namewarp.get().toLowerCase();

            if(WARPS.containsKey(name)) {
                src.sendMessage(WARP_ALREADY_EXIST());
                return CommandResult.empty(); 
            }
            
            String world = player.getWorld().getName();
            int x = player.getLocation().getBlockX();
            int y = player.getLocation().getBlockY();
            int z = player.getLocation().getBlockZ();

            Warp warp = new Warp(name, world, x, y, z,"&3grr...");
            warp.insert();
            commit();
            src.sendMessage(WARP_SET_SUCCESS(player,name));
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
