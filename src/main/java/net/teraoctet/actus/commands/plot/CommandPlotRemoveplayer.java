package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
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
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.User;

public class CommandPlotRemoveplayer implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.plot.removeplayer")) { 
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
                player.sendMessage(USAGE("/plot removeplayer : retire un habitant - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot removeplayer <NomParcelle> : retire un habitant sur la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(MESSAGE("&eTu dois etre habitant pour utiliser cette commande"));
                return CommandResult.empty(); 
            }

            if(ctx.getOne("player").isPresent()){
                Optional<User> target = ctx.<User> getOne("player");  
                if (!target.isPresent()){
                    player.sendMessage(MESSAGE("&eCe joueur ne semble pas etre connu sur ce serveur !"));
                    return CommandResult.empty();
                }

                String uuidAllowed = plot.get().getUuidAllowed();
                uuidAllowed = uuidAllowed.replace(target.get().getUniqueId().toString(), "");
                plot.get().setUuidAllowed(uuidAllowed);
                player.sendMessage(MESSAGE("&e" + target.get().getName() + " &7a \351t\351 retir\351 de la liste des habitants"));
                if(target.get().getPlayer().isPresent())target.get().getPlayer().get().sendMessage(MESSAGE("&e" + player.getName() + " &7vous a retir\351 des habitants de &e" + plot.get().getName()));
                return CommandResult.success();
            } else {
                player.sendMessage(USAGE("/plot removeplayer <playerName> [NomParcelle]"));
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