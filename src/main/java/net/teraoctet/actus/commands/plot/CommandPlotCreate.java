package net.teraoctet.actus.commands.plot;

import static net.teraoctet.actus.Actus.CB_PLOT;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.plot.PlotManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import static net.teraoctet.actus.utils.MessageManager.BUYING_COST_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.NAME_ALREADY_USED;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.UNDEFINED_PLOT_ANGLES;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotCreate implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.plot.create")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            PlotManager plotManager = PlotManager.getSett(player);

            if(!ctx.getOne("name").isPresent()) { 
                player.sendMessage(USAGE("/plot create <name> [strict] : cr\351ation d'une parcelle"));
                player.sendMessage(MESSAGE("&7option [strict] : protection sur les points d\351clar\351s"));
                return CommandResult.empty();
            }
            
            String name = ctx.<String> getOne("name").get();
            if(name.length() > 10){
                player.sendMessage(MESSAGE("&7autoris\351 10 caract\350re maxi, recommencez"));
                return CommandResult.empty();
            }
            Boolean strict = false;

            if (plotManager.hasPlot(name) == false){
                if(!plotManager.getBorder1().isPresent() || !plotManager.getBorder2().isPresent()){
                    player.sendMessage(UNDEFINED_PLOT_ANGLES());
                    return CommandResult.empty();
                }
                Location[] c = {plotManager.getBorder1().get(), plotManager.getBorder2().get()};
                int level = 1;
                if(plotManager.plotNotAllow(plotManager.getBorder1().get(), plotManager.getBorder2().get())){
                    if(aplayer.getLevel() != 10 && plotManager.hasOwnerPlotParent(player, plotManager.getBorder1().get(), plotManager.getBorder2().get())){
                        player.sendMessage(ALREADY_OWNED_PLOT());
                        return CommandResult.empty();
                    }
                    level = plotManager.getMaxLevelPlotParent(player, plotManager.getBorder1().get(), plotManager.getBorder2().get());
                }

                int X = (int) Math.round(c[0].getX()-c[1].getX());
                int Z = (int) Math.round(c[0].getZ()-c[1].getZ());
                if(X < 0)X = -X;
                if(Z < 0)Z = -Z;
                int nbBlock =  (X * Z);
                int amount;

                if(nbBlock < 51){ amount = 1;}
                else if(nbBlock < 101){ amount = 2;}
                else if(nbBlock < 201){ amount = 3;}
                else { amount = nbBlock / 60;}

                if(aplayer.getMoney()>= amount || aplayer.getLevel() == 10){
                    if(ctx.getOne("strict").isPresent()){
                        if (ctx.<String> getOne("strict").get().equalsIgnoreCase("strict")) strict = true;
                    }
                    player.sendMessage(MESSAGE("&7Le co\373t de cette transaction est de : &e" + amount + " \351meraudes"));
                    player.sendMessage(Text.builder("Clique ici pour confirmer la cr\351ation de ta parcelle").onClick(TextActions.executeCallback(CB_PLOT.callCreate(name,strict,amount,level))).color(TextColors.AQUA).build());  
                    return CommandResult.success();
                } else {
                    player.sendMessage(BUYING_COST_PLOT(player,String.valueOf(amount),String.valueOf(aplayer.getMoney())));
                }
            } else {
                player.sendMessage(NAME_ALREADY_USED());
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