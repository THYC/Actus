package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import java.util.function.Consumer;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.player.PlayerManager;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.formatText;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.action.TextActions;

public class CommandPlotListNameAllowed implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("actus.plot")) {
            Player player = (Player) src;

            APlayer aplayer = getAPlayer(player.getIdentifier());
            
            Optional<Plot> plot = plotManager.getPlot(player.getLocation());
            if (!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            } else if (!plot.get().getUuidOwner().equalsIgnoreCase(player.getIdentifier()) && aplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();  
            }
            
            Text list = Text.EMPTY;
            for(String uuid : plot.get().getListUuidAllowed()){
                if(!uuid.equalsIgnoreCase("ADMIN")){
                    list = list.concat(Text.builder().append(MESSAGE("&e" + PlayerManager.getAPlayer(uuid).getName()))
                                        .onClick(TextActions.executeCallback(callRemovePlayer(plot.get(), uuid)))   
                                        .onHover(TextActions.showText(MESSAGE("Click pour Supprimer ce joueur"))).toText());
                }
            }
                
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder();

            builder.title(formatText("&6Plot Joueur habitant la parcelle"))
                    .contents(list.toText())
                    .header(formatText("&ePlot " + plot.get().getName()))
                    .padding(Text.of("-"))
                    .sendTo(src);
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
    
    public Consumer<CommandSource> callRemovePlayer(Plot plot, String uuid) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer target = getAPlayer(uuid);
            
            if(player.getIdentifier().equalsIgnoreCase(uuid)){
                player.sendMessage(MESSAGE("&eVous ne pouvez pas vous retirer des habitants, sinon supprimez la parcelle"));
                return;
            }
            
            String uuidAllowed = plot.getUuidAllowed();
            uuidAllowed = uuidAllowed.replace(uuid, "");
            plot.setUuidAllowed(uuidAllowed);
            player.sendMessage(MESSAGE("&e" + target.getName() + " &7a \351t\351 retir\351 de la liste des habitants"));
            target.sendMessage(MESSAGE("&e" + player.getName() + " &7vous a retir\351 des habitants de &e" + plot.getName()));
            plot.update();
            Data.commit(); 
        };
    }
}