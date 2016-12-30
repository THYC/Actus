package net.teraoctet.actus.commands.plot;

import com.flowpowered.math.vector.Vector3i;
import java.util.Optional;
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
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_LOADED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_PLOT_SUCCESS;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.World;

public class CommandPlotClaim implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.plot.create")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            PlotManager plotManager = PlotManager.getSett(player);
            
            if(aplayer.getID_guild() == 0) { 
                player.sendMessage(MESSAGE("&eVous devez faire partie d'une Guild pour utiliser cette commande"));
                return CommandResult.empty();
            }
            
            Optional<Vector3i> optionalChunk = Sponge.getServer().getChunkLayout().toChunk(player.getLocation().getBlockPosition());
            if (optionalChunk.isPresent()){
                Vector3i chunk = optionalChunk.get();
                Boolean strict = false;
                String name = "CHUNK" + chunk.getX() + chunk.getZ();
                Location<World> loc1 = new Location(player.getWorld(),chunk.getX()*16,0,chunk.getZ()*16);
                Location<World> loc2 = new Location(player.getWorld(),(chunk.getX()*16)+15,0,(chunk.getZ()*16)+15);
            
                Location[] c = {loc1, loc2};
                int level = 0;

                if(plotManager.plotAllow(loc1, loc2)){
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
                    player.sendMessage(Text.builder("Clique ici pour confirmer la cr\351ation de ta parcelle").onClick(TextActions.executeCallback(callCreate(name,strict,amount,level,loc1,loc2))).color(TextColors.AQUA).build());  
                    return CommandResult.success();
                } else {
                    player.sendMessage(BUYING_COST_PLOT(player,String.valueOf(amount),String.valueOf(aplayer.getMoney())));
                }
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
    
    public Consumer<CommandSource> callCreate(String plotName, Boolean strict, int amount, int level, Location<World> loc1, Location<World> loc2) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Location[] c = {loc1, loc2};

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