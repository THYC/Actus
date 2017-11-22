package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandPlotMsg implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.plot.msg")) {
            Optional<String> arguments = ctx.<String> getOne("arguments");
                        
            Player player = (Player)src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                        
            // on vérifie que le joueur se situe bien sur une parcelle sinon on sort
            Optional<Plot> plot = plotManager.getPlot(player.getLocation());
            if(!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }
            
            // on vérifie que le joueur est bien le owner de la parcelle
            if(plot.get().getUuidOwner().equals(player.getIdentifier()) || aplayer.getLevel() == 10){
                
                // si le joueur n'a pas tapé d'arguments on affiche le message existant
                if(!ctx.<String> getOne("arguments").isPresent()){
                    Text msg = MESSAGE(plot.get().getMessage());
                    player.sendMessage(msg); 
                    return CommandResult.success();
                    
                // sinon on remplace le message par les arguments
                } else {
                    String[] args = arguments.get().split(" ");
                    String smsg = "";
                    for (String arg : args) {
                        smsg = smsg + arg + " ";
                    }
                    Text msg = MESSAGE(smsg);
                    plot.get().setMessage(sm.quoteToSQL(smsg));
                    plot.get().update();
                    Data.commit();
                    player.sendMessage(MESSAGE("&cVotre nouveau message :"));
                    player.sendMessage(msg);
                    return CommandResult.success();
                }     
            } else {
                player.sendMessage(MESSAGE("Vous n'etes pas le proprietaire de cette parcelle"));
                return CommandResult.empty();
            }   
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
