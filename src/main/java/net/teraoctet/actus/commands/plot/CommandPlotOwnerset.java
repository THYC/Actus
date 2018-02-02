package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayerName;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotOwnerset implements CommandExecutor {
         
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.plot.ownerset")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional<Plot> plot;

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                plot = ptm.getPlot(plotName);                
            } else {
                plot = ptm.getPlot(player.getLocation());
            }

            if (!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot ownerset : change le propri\351taire de la parcelle - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot ownerset <NomParcelle> : change le propri\351taire de la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                player.sendMessage(MESSAGE("&eVous devez \352tre propri\351taire pour taper cette commande"));
                return CommandResult.empty();
            }

            if(ctx.getOne("player").isPresent()){
                Player target = ctx.<Player> getOne("player").get();  
                if (target == null){
                    player.sendMessage(MESSAGE("&e" + target + " &7 doit \352tre connect\351 pour l'ajouter"));
                    return CommandResult.empty();
                } else {
                    plot.get().setUuidOwner(target.getIdentifier());
                    if(!plot.get().getUuidAllowed().contains(target.getIdentifier())){
                        plot.get().setUuidAllowed(plot.get().getUuidAllowed() + " " + target.getIdentifier());
                    }
                    plot.get().update();
                
                    player.sendMessage(MESSAGE("&e" + target.getName() + " &7est maintenant le propri\351taire de &e" + plot.get().getName()));
                    target.sendMessage(MESSAGE("&7Vous \352tes maintenant propri\351taire de &e" + plot.get().getName()));
                    return CommandResult.success();
                }
            } else {
                player.sendMessage(USAGE("/plot ownerset : change le propri\351taire de la parcelle - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot ownerset <NomParcelle> : change le propri\351taire de la parcelle nomm\351e"));
                return CommandResult.empty();
            }    
        } 
        
        else if (src instanceof ConsoleSource){
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();	
    }
}