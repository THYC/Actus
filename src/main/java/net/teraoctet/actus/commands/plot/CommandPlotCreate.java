package net.teraoctet.actus.commands.plot;

import java.util.function.Consumer;
import net.teraoctet.actus.plot.PlotManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Data;
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
import static net.teraoctet.actus.utils.MessageManager.BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_LOADED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_PLOT_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.UNDEFINED_PLOT_ANGLES;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.World;

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
                int level = 0;
                
                if(plotManager.plotAllow(plotManager.getBorder1().get(), plotManager.getBorder2().get())){
                    if(aplayer.getLevel() != 10){
                        player.sendMessage(ALREADY_OWNED_PLOT());
                        return CommandResult.empty();
                    }
                    level = 1;
                }

                int X = (int) Math.round(c[0].getX()-c[1].getX());
                int Z = (int) Math.round(c[0].getZ()-c[1].getZ());
                if(X < 0)X = -X;
                if(Z < 0)Z = -Z;
                int nbBlock =  (X * Z);
                int amount = 1;

                if(nbBlock < 51){ amount = 1;}
                else if(nbBlock < 101){ amount = 2;}
                else if(nbBlock < 201){ amount = 3;}
                else { amount = nbBlock / 60;}

                if(aplayer.getMoney()>= amount || aplayer.getLevel() == 10){
                    if(ctx.getOne("strict").isPresent()){
                        if (ctx.<String> getOne("strict").get().equalsIgnoreCase("strict")) strict = true;
                    }
                    player.sendMessage(MESSAGE("&7Le co\373t de cette transaction est de : &e" + amount + " \351meraudes"));
                    player.sendMessage(Text.builder("Clique ici pour confirmer la cr\351ation de ta parcelle").onClick(TextActions.executeCallback(callCreate(name,strict,amount,level))).color(TextColors.AQUA).build());  
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
    
    public Consumer<CommandSource> callCreate(String plotName, Boolean strict, int amount, int level) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            PlotManager plotManager = PlotManager.getSett(player);
            Location[] c = {plotManager.getBorder1().get(), plotManager.getBorder2().get()};

            String playerUUID = player.getUniqueId().toString();
            if(aplayer.getLevel() == 10){ playerUUID = "ADMIN";} else { aplayer.debitMoney(amount);}

            int y1 = (int)c[0].getY();
            int y2 = (int)c[1].getY();

            if(strict == false) { 
                player.sendMessage(BEDROCK2SKY_PROTECT_PLOT_SUCCESS(player,plotName));
                y1 = 0;
                y2 = 500;
            } else {
                player.sendMessage((ChatType) PROTECT_PLOT_SUCCESS(player,plotName),Text.of(TextColors.GREEN," Y " + y1 + " : " + y2));
            }

            Location <World> world = c[0];
            String worldName = world.getExtent().getName();
            
            int x1 = c[0].getBlockX();
            int z1 = c[0].getBlockZ();
            int x2 = c[1].getBlockX();
            int z2 = c[1].getBlockZ();
                        
            String message = "&b-- SECURISED --";

            Plot plot = new Plot(plotName,level,worldName,x1,y1,z1,x2,y2,z2,0,0,1,1,1,0,1,1,message,0,1,1,1,playerUUID,playerUUID);
            plot.insert();
            Data.commit();
            Data.addPlot(plot);

            player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PLOT(player,plotName));
            player.sendMessage(Text.builder("Clique ici, pour voir les flags de ta parcelle !").onClick(TextActions.runCommand("/p flaglist " + plotName)).color(TextColors.AQUA).build());  
        };
    }
}