package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.plot.Plot;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotLevel implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.plot.level")) { 
            Player player = (Player) src;
            Optional<Plot> plot;

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                plot = ptm.getPlot(plotName);                
            } else {
                plot = ptm.getPlot(player.getLocation());
            }

            if (plot == null){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }

            player.sendMessage(MESSAGE("&ePlot : &7" + plot.get().getName() + " &elevel :&7" + plot.get().getLevel()));
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