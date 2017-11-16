package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.source.ConsoleSource;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;

public class CommandPlotSetLevel implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.plot.setlevel")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional<Plot> plot;

            plot = plotManager.getPlot(player.getLocation());
            
            if (!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }

            if(ctx.getOne("level").isPresent()){
                if(!plot.get().getUuidOwner().equalsIgnoreCase(player.getIdentifier()) && aplayer.getLevel() != 10){
                    player.sendMessage(MESSAGE("&eVous devez \352tre propri\351taire pour taper cette commande"));
                    return CommandResult.empty();
                }
                int level = ctx.<Integer> getOne("level").get();  
                plot.get().setLevel(level);
                plot.get().update();
                player.sendMessage(MESSAGE("&e" + plot.get().getName() + " level :&7" + plot.get().getLevel()));
                return CommandResult.success();
            } else {
                player.sendMessage(USAGE("/plot setlevel <level>"));
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