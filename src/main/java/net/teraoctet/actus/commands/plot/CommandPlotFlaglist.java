package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static net.teraoctet.actus.utils.MessageManager.formatText;
import net.teraoctet.actus.player.APlayer;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotFlaglist implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
               
        if(src instanceof Player && src.hasPermission("actus.plot.flag")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional <Plot> plot = Optional.empty();

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                plot = plotManager.getPlot(plotName);                
            } else {
                plot = plotManager.getPlot(player.getLocation());
            }

            if (plot == null){
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot flag> : liste les flags de la parcelle, vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot flaglist <NomParcelle> : liste les flags de la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();  
            }

            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();

            builder.title(formatText("&6Flag Plot"))
                .contents(  formatText("&e/plot flag <flag> <0|1> : &7modifie la valeur d'un flag"),
                            formatText("&enoEnter : &b" + plot.get().getNoEnter() + " &7ils peuvent entrer sur la parcelle"),
                            formatText("&enoFly : &b" + plot.get().getNoFly() + " &7ils peuvent pas voler au dessus"),
                            formatText("&enoBuild : &b" + plot.get().getNoBuild() + " &7ils peuvent construirent"),
                            formatText("&enoBreak : &b" + plot.get().getNoBreak() + " &7ils peuvent casser"),
                            formatText("&enoInteract : &b" + plot.get().getNoInteract() + " &7ils peuvent ouvrir les portes,coffres..."),
                            formatText("&enoTeleport : &b" + plot.get().getNoTeleport() + " &7ils peuvent se t\351l\351porter"),
                            formatText("&enoFire : &b" + plot.get().getNoFire() + " &7mettre le feu"),
                            formatText("&enoMob : &b" + plot.get().getNoMob() + " &7les monstres spawn"),
                            formatText("&enoTNT : &b" + plot.get().getNoTNT() + " &7activation de la TNT"),
                            formatText("&enoCommand : &b" + plot.get().getNoCommand() + " &7ils peuvent taper des commandes"))
                .header(formatText("&ePlot " + plot.get().getName() + " : &7Droits accord\351s aux autres joueurs, 0 = Oui, 1 = Non"))
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
}