package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.player.APlayer;
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

public class CommandPlotAddplayer implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.plot.addplayer")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional<Plot> plot = Optional.empty();

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                plot = plotManager.getPlot(plotName);                
            } else {
                plot = plotManager.getPlot(player.getLocation());
            }

            if (!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot addplayer : ajoute un habitant - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot addplayer <nomParcelle> : ajoute un habitant sur la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();   
            }

            if(ctx.getOne("player").isPresent()){
                Player target = ctx.<Player> getOne("player").get();  
                if (target == null){
                    player.sendMessage(MESSAGE("&e" + target + " &7 doit \352tre connect\351 pour l'ajouter"));
                    return CommandResult.empty();
                }
                plot.get().setUuidAllowed(plot.get().getUuidAllowed() + " " + target.getUniqueId().toString());
                player.sendMessage(MESSAGE("&e" + target.getName() + " &7 a \351t\351 ajout\351 à la liste des habitants"));
                target.sendMessage(MESSAGE("&7Vous \352tes maintenant habitant de &e" + plot.get().getName()));
                return CommandResult.success();
            } else {
                player.sendMessage(USAGE("/plot addplayer <playerName> [nomParcelle]"));
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