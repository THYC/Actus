package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayerName;
import static net.teraoctet.actus.player.PlayerManager.isAPlayer;
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

public class CommandPlotAddplayer implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.plot.addplayer")) { 
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
                player.sendMessage(USAGE("/plot addplayer : ajoute un habitant - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot addplayer <nomParcelle> : ajoute un habitant sur la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && 
                    !plot.get().getUuidOwner().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(MESSAGE("&eTu dois etre habitant pour utiliser cette commande"));
                return CommandResult.empty();   
            }

            if(ctx.getOne("player").isPresent()){
                String opttarget = ctx.<String> getOne("player").get();  
                if (!isAPlayer(opttarget)){
                    player.sendMessage(MESSAGE("&e" + opttarget + " &7 n'est pas un joueur, v\351rifie l'orthographe"));
                    return CommandResult.empty();
                }
                Optional<APlayer> target = getAPlayerName(opttarget);
                plot.get().setUuidAllowed(plot.get().getUuidAllowed() + " " + target.get().getUUID());
                plot.get().update();
                player.sendMessage(MESSAGE("&e" + target.get().getName() + " &7 a \351t\351 ajout\351 a la liste des habitants"));
                target.get().sendMessage(MESSAGE("&7Vous \352tes maintenant habitant de &e" + plot.get().getName()));
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