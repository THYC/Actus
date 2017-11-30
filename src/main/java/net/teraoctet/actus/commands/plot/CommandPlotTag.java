package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.ptm;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.plot.Wedit;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.world.extent.Extent;

public class CommandPlotTag implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.player.plot.tag")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            
            Location loc = player.getLocation();
            Optional<Plot> plot = ptm.getPlot(loc);
            
            if(!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }
            
            if(!plot.get().getUuidOwner().equalsIgnoreCase(player.getIdentifier()) && aplayer.getLevel() != 10){
                player.sendMessage(MESSAGE("&7Tu n'est pas propri\351taire de cette parcelle"));
                return CommandResult.empty();
            }
            
            if(ptm.hasTag(plot.get())){
                ptm.remTag(plot.get());
                return CommandResult.empty();
            }
            
            Extent world = plot.get().getLocX1Y1Z1().getExtent();            
            ptm.spawnTag(new Location(world,plot.get().getX1(),plot.get().getYSpawn(plot.get().getX1(), plot.get().getZ1()),plot.get().getZ1()));
            ptm.spawnTag(new Location(world,plot.get().getX1(),plot.get().getYSpawn(plot.get().getX1(), plot.get().getZ2()),plot.get().getZ2()));
            ptm.spawnTag(new Location(world,plot.get().getX2(),plot.get().getYSpawn(plot.get().getX2(), plot.get().getZ1()),plot.get().getZ1()));
            ptm.spawnTag(new Location(world,plot.get().getX2(),plot.get().getYSpawn(plot.get().getX2(), plot.get().getZ2()),plot.get().getZ2()));           
            
            if(Wedit.WEisActive()){
                PlotSelection plotSelect = ptm.getPlotSel(player);
                plotSelect.setMinPos(plot.get().getLocX1Y1Z1().getBlockPosition());
                plotSelect.setMaxPos(plot.get().getLocX2Y2Z2().getBlockPosition());
                plotSelect.setWorld(player.getWorld());
                Wedit.setSelection(player, plotSelect);
            }
                            
            player.sendMessage(MESSAGE("&eTa parcelle a \351t\351 born\351 par des bannieres sur chaque coin"));  
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