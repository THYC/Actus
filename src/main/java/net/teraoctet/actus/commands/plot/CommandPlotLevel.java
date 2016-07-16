package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
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

        if(src instanceof Player && src.hasPermission("actus.plot.level")) { 
            Player player = (Player) src;
            Optional<Plot> plot = Optional.empty();

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                plot = plotManager.getPlot(plotName);                
            } else {
                plot = plotManager.getPlot(player.getLocation());
            }

            if (plot == null){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }

            if(ctx.getOne("level").isPresent()){
                int level = ctx.<Integer> getOne("level").get();  
                
                plot.get().setLevel(level);
                plot.get().update();
                player.sendMessage(MESSAGE("&e" + plot.get().getName() + " level :&7" + plot.get().getLevel()));
                return CommandResult.success();
            } else {
                player.sendMessage(MESSAGE("&e" + plot.get().getName() + " level :&7" + plot.get().getLevel()));
                player.sendMessage(USAGE("/plot level [level]"));
            }
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