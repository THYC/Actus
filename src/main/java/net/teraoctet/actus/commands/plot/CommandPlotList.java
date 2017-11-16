package net.teraoctet.actus.commands.plot;

import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.source.ConsoleSource;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.DATA_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.PLOT_LIST;
import static net.teraoctet.actus.utils.MessageManager.TARGET_PLOT_LIST;

public class CommandPlotList implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("actus.player.plot.list")) {
            Player player = (Player) src;

            if(ctx.getOne("tplayer").isPresent() && src.hasPermission("actus.admin.plot.otherlist")){
                String targetName = ctx.<String> getOne("tplayer").get();
                APlayer aplayer = getAPlayer(targetName);
                // si <tplayer> n'est pas dans la base de donnée
                if(aplayer == null){
                    player.sendMessage(DATA_NOT_FOUND(targetName));
                    return CommandResult.empty();
                } else {    //lorsque <tplayer> est dans la base de donnée
                    Text listPlot = plotManager.getListPlot(aplayer.getUUID());
                    player.sendMessage(TARGET_PLOT_LIST(targetName));
                    player.sendMessage(listPlot); 
                }
            }
            else {
                String playerUUID = player.getUniqueId().toString();
                Text listPlot = plotManager.getListPlot(playerUUID);
                player.sendMessage(PLOT_LIST());
                player.sendMessage(listPlot);
            }
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