package net.teraoctet.actus.commands.plot;

import java.util.Optional;
import static net.teraoctet.actus.Actus.CB_PLOT;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PLOT;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandPlotFlaglist implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
               
        if(src instanceof Player && src.hasPermission("actus.player.plot.flag")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Optional <Plot> plot;
            String plotName = "";
            
            if(ctx.getOne("name").isPresent()){
                plotName = ctx.<String> getOne("name").get();
                plot = ptm.getPlot(plotName);                
            } else {
                plot = ptm.getPlot(player.getLocation());
            }

            if (!plot.isPresent()){
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot flag> : liste les flags de la parcelle, vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot flaglist <NomParcelle> : liste les flags de la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidAllowed().equalsIgnoreCase(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(MESSAGE("&dVous devez faire partie des habitants de '" + plotName + "' pour utiliser cette commande"));
                return CommandResult.empty();  
            }

            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();

            builder.title(MESSAGE("&ePlot " + plot.get().getName() ))
                    .contents(  
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoEnter()) + " ] &7Interdiction d'entrer sur la parcelle"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noenter", getValue(plot.get().getNoEnter()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoFly()) + " ] &7Fly interdit sur la parcelle"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nofly", getValue(plot.get().getNoFly()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoBuild()) + " ] &7Interdiction de construire"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nobuild", getValue(plot.get().getNoBuild()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoBreak()) + " ] &7Interdiction de casser"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nobreak", getValue(plot.get().getNoBreak()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),                               
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoInteract()) + " ] &7Interdiction d'ouvrir portes,coffres..."))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nointeract", getValue(plot.get().getNoInteract()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),                             
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoTeleport()) + " ] &7Interdiction de se t\351l\351porter"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noteleport", getValue(plot.get().getNoTeleport()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),                                
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoFire()) + " ] &7Interdiction de mettre le feu"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nofire", getValue(plot.get().getNoFire()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),                                
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoMob()) + " ] &7Les mob ne spawnerons pas"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nomob", getValue(plot.get().getNoMob()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),                              
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoTNT()) + " ] &7TNT d\351sactiv\351 sur la parcelle"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "notnt", getValue(plot.get().getNoTNT()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),                              
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoCommand()) + " ] &7Interdiction de taper des commandes"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nocommand", getValue(plot.get().getNoCommand()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getSpawnGrave()) + " ] &7Une tombe apparait à la mort d'un joueur "))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "spawngrave", getValue(plot.get().getSpawnGrave()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText(),
                                Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getAutoForest()) + " ] &7Les racines d'arbres se replantes"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "autoforest", getValue(plot.get().getAutoForest()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText())
  
                    
                .header(MESSAGE("&o&eListe des droits accord\351s aux autres joueurs :"))
                .footer(Text.builder().append(MESSAGE("&o&9 Besoin d'aide ?"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callHelpPlotFlag()))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour afficher l'aide"))).toText())
                .padding(MESSAGE("&9-"))
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
    
    private boolean getValue(boolean value){
        return !value;
    }
}