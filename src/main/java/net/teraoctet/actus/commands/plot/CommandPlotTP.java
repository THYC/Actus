package net.teraoctet.actus.commands.plot;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import net.teraoctet.actus.utils.DeSerialize;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.ERROR;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.world.Location;

public class CommandPlotTP implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.player.plot.tp")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional<Plot> plot;

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                plot = plotManager.getPlot(plotName);                

                if (!plot.isPresent()){
                    player.sendMessage(NO_PLOT());
                    return CommandResult.empty();
                } else if (plot.get().getNoTeleport() || aplayer.getLevel() == LEVEL_ADMIN()){
                    Optional<Location> spawn = plot.get().getSpawnPlot();
                    if(spawn.isPresent()){
                        Location lastLocation = player.getLocation();
                        player.transferToWorld(plot.get().getWorld().get(), 
                                new Vector3d(spawn.get().getBlockX(), spawn.get().getBlockY(), spawn.get().getBlockZ()));
                        aplayer.setLastposition(DeSerialize.location(lastLocation));
                        aplayer.update();
                        return CommandResult.success();
                    } else {
                        src.sendMessage(ERROR()); 
                        return CommandResult.empty(); 
                    }
                } else {
                    src.sendMessage(NO_PERMISSIONS());
                    return CommandResult.empty(); 
                }
            } else {
                src.sendMessage(USAGE("/plot tp <NamePlot>"));
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
