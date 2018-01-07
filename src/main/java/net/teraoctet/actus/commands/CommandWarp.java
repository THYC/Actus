package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.utils.Config;
import net.teraoctet.actus.utils.Data;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import net.teraoctet.actus.warp.Warp;

public class CommandWarp implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {       
                   
        if(src instanceof Player && src.hasPermission("actus.player.warp")) { 
            Player player = (Player) src;           
            Optional<String> warpName = ctx.<String> getOne("warp");
            String name;
            
            if(warpName.isPresent()) { 
                name = warpName.get().toLowerCase();
                Warp warp = Data.getWarp(name);
                sm.teleport(player, warp.getWorld().get().getName(), warp.getX().intValue(), warp.getY().intValue(), warp.getZ().intValue(),MESSAGE(warp.getMessage()));
                src.sendMessage(MESSAGE("&eVous serez TP dans environ " + Config.COOLDOWN_TO_TP()));
                return CommandResult.success();
            }
        } 
        
        else if (src instanceof ConsoleSource){
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage (NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
