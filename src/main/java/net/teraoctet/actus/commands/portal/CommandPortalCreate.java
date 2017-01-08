package net.teraoctet.actus.commands.portal;

import java.util.function.Consumer;
import static net.teraoctet.actus.Actus.portalManager;
import net.teraoctet.actus.plot.PlotManager;
import net.teraoctet.actus.portal.Portal;
import net.teraoctet.actus.portal.PortalManager;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.NAME_ALREADY_USED;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_LOADED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.UNDEFINED_PLOT_ANGLES;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.World;

public class CommandPortalCreate implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
          
        if(src instanceof Player && src.hasPermission("actus.admin.portal")) { 
            Player player = (Player) src;
            PlotManager plotManager = PlotManager.getSett(player);
        
            if(!ctx.getOne("name").isPresent()) { 
               player.sendMessage(USAGE("/portal create <name> : cr\351ation d'un portail au point d\351clar\351"));
               return CommandResult.empty();
           }

           String name = ctx.<String> getOne("name").get();

           if (portalManager.hasPortal(name) == false){
               if(!plotManager.getBorder1().isPresent() || !plotManager.getBorder2().isPresent()){
                    player.sendMessage(UNDEFINED_PLOT_ANGLES());
                    return CommandResult.empty();
                }
               Location[] c = {plotManager.getBorder1().get(), plotManager.getBorder2().get()};
               
               player.sendMessage(Text.builder("Clique ici pour confirmer la cr\351ation du portail").onClick(TextActions.executeCallback(callCreate(name))).color(TextColors.AQUA).build()); 
               return CommandResult.success();
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
    
    public Consumer<CommandSource> callCreate(String portalName) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            PlotManager plotManager = PlotManager.getSett(player);
            PortalManager portalManager = new PortalManager();
        
            if(portalManager.hasPortal(portalName)){
                player.sendMessage(MESSAGE("&bce portail existe d\351ja"));
                return;
            }

            Location[] c = {plotManager.getBorder1().get(), plotManager.getBorder2().get()};
            Location <World> world = c[0];
            String worldName = world.getExtent().getName();
            int x1 = c[0].getBlockX();
            int y1 = c[0].getBlockY();
            int z1 = c[0].getBlockZ();
            int x2 = c[1].getBlockX();
            int y2 = c[1].getBlockY();
            int z2 = c[1].getBlockZ();
            String message = "&c.. vers l''infini et au del\340 ...";

            Portal portal = new Portal(portalName,0,worldName,x1,y1,z1,x2,y2,z2,message);
            portal.insert();
            Data.commit();
            Data.addPortal(portal);

            player.sendMessage(Text.builder().append(MESSAGE("Clique ici pour lire le message par d\351faut du portail")).onClick(TextActions.runCommand("/portal msg " + portalName )).color(TextColors.AQUA).build());
            player.sendMessage(Text.builder().append(MESSAGE("Tape /portal msg <message> &bpour pour modifier le message par d\351faut")).onClick(TextActions.suggestCommand("/portal msg " + portalName + " 'remplace ce texte par ton message'")).color(TextColors.AQUA).build());
            player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PLOT(player,portalName));
        };
    }
}