package net.teraoctet.actus.commands.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.CB_PLOT;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
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
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.action.TextActions;

public class CommandPlotFlag implements CommandExecutor {
       
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.player.plot.flag")) { 
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
                player.sendMessage(USAGE("/plot flag <flag> <false|true> : modifie la valeur Oui = true Non = false, vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot flag <flag> <false|true> [NomParcelle]: modifie la valeur d'un flag Oui = true Non = false"));
                player.sendMessage(USAGE("/plot flag> : liste les flags de la parcelle, vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot flaglist <NomParcelle> : liste les flags de la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!plot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();   
            }

            if(!ctx.getOne("flag").isPresent()){
                List<Text> flagList = new ArrayList<>();
                
                if(aplayer.getLevel() == LEVEL_ADMIN()){
                    flagList.add(Text.builder().append(MESSAGE("&e[ " + ptm.ValueOf(plot.get().getNoEnter()) + " ] &7Interdiction d'entrer sur la parcelle"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noenter", getValue(plot.get().getNoEnter()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    flagList.add(Text.builder().append(MESSAGE("&e[ " + ptm.ValueOf(plot.get().getNoLiquidFlow()) + " ] &7Les fluides ne couleront pas"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noliquidflow", getValue(plot.get().getNoLiquidFlow()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    flagList.add(Text.builder().append(MESSAGE("&e[ " + ptm.ValueOf(plot.get().getNoAnimal()) + " ] &7Les animaux ne spawnent pas"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noanimal", getValue(plot.get().getNoAnimal()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    flagList.add(Text.builder().append(MESSAGE("&e[ " + ptm.ValueOf(plot.get().getNoPVPmonster()) + " ] &7Les monstres ne font pas de dommages"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nopvpmonster", getValue(plot.get().getNoPVPmonster()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    flagList.add(Text.builder().append(MESSAGE("&e[ " + ptm.ValueOf(plot.get().getNoPVPplayer()) + " ] &7Les joueurs ne font pas de dommages"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nopvpplayer", getValue(plot.get().getNoPVPplayer()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    flagList.add(Text.builder().append(MESSAGE("&e[ " + ptm.ValueOf(plot.get().getNoProjectile()) + " ] &7Les projectiles ne font pas de dommages"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noprojectile", getValue(plot.get().getNoProjectile()))))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    flagList.add(Text.builder().append(MESSAGE("&e--------------------------------------"))
                                    .toText());
                }
                
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoFly()) + " ] &7Fly interdit sur la parcelle"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nofly", getValue(plot.get().getNoFly()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoBuild()) + " ] &7Interdiction de construire"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nobuild", getValue(plot.get().getNoBuild()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoBreak()) + " ] &7Interdiction de casser"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nobreak", getValue(plot.get().getNoBreak()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());                
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoInteract()) + " ] &7Interdiction d'ouvrir portes,coffres..."))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nointeract", getValue(plot.get().getNoInteract()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());            
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoTeleport()) + " ] &7Interdiction de se t\351l\351porter"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "noteleport", getValue(plot.get().getNoTeleport()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoFire()) + " ] &7Interdiction de mettre le feu"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nofire", getValue(plot.get().getNoFire()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoMob()) + " ] &7Les mob ne spawneront pas"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nomob", getValue(plot.get().getNoMob()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());                  
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoTNT()) + " ] &7TNT d\351sactiv\351e sur la parcelle"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "notnt", getValue(plot.get().getNoTNT()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());                   
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoCommand()) + " ] &7Interdiction de taper des commandes"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nocommand", getValue(plot.get().getNoCommand()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getSpawnGrave()) + " ] &7Une tombe apparaît à la mort d'un joueur "))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "spawngrave", getValue(plot.get().getSpawnGrave()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());                  
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getAutoForest()) + " ] &7Les racines d'arbres se replantent"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "autoforest", getValue(plot.get().getAutoForest()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                flagList.add(Text.builder().append(MESSAGE("&9[ " + ptm.ValueOf(plot.get().getNoMsg()) + " ] &7D\351sactive le message d'accueil"))
                                .onClick(TextActions.executeCallback(CB_PLOT.callChangeFlag(plot.get(), "nomsg", getValue(plot.get().getNoMsg()))))    
                                .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText());
                    
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                Builder builder = paginationService.builder();
                player.sendMessage(MESSAGE("\n\n\n\n"));
                builder.title(MESSAGE("&ePlot " + plot.get().getName() ))
                    .contents(flagList)
                    .header(MESSAGE("&o&eListe des droits accord\351s aux autres joueurs :"))
                    .footer(Text.builder().append(MESSAGE("&o&9 Besoin d'aide ?"))
                                    .onClick(TextActions.executeCallback(CB_PLOT.callHelpPlotFlag()))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour afficher l'aide"))).toText())
                    .padding(MESSAGE("&9-"))
                    .sendTo(src);
	
            } else {
                if(!ctx.getOne("value").isPresent()){
                    player.sendMessage(USAGE("/plot flag <flag> <false|true> [NomParcelle]: modifie la valeur d'un flag Oui = true Non = false"));
                    return CommandResult.empty();
                }

                String flag = ctx.<String> getOne("flag").get();
                boolean value = ctx.<Boolean> getOne("value").get(); 
                               
                switch (flag.toLowerCase()){
                    case "noenter":
                        plot.get().setNoEnter(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoEnter = " + value));
                        break;
                    case "nofly":
                        plot.get().setNoFly(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoFly = " + value));
                        break;
                    case "nobuild":
                        plot.get().setNoBuild(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoBuild = " + value));
                        break;
                    case "nobreak":
                        plot.get().setNoBreak(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoBreak = " + value));
                        break;
                    case "nointeract":
                        plot.get().setNoInteract(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoInteract = " + value));
                        break;
                    case "noteleport":
                        plot.get().setNoTeleport(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoTeleport = " + value));
                        break;
                    case "nofire":
                        plot.get().setNoFire(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoFire = " + value));
                        break;
                    case "nomob":
                        plot.get().setNoMob(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoMob = " + value));
                        break;
                    case "noanimal":
                        plot.get().setNoAnimal(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoAnimal = " + value));
                        break;
                    case "notnt":
                        plot.get().setNoTNT(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoTNT = " + value));
                        break;
                    case "nocommand":
                        plot.get().setNoCommand(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoCommand = " + value));
                        break;
                    case "spawngrave":
                        plot.get().setSpawnGrave(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &espawnGrave = " + value));
                        break;
                    case "nopvpplayer":
                        if(player.hasPermission("actus.admin.plot.flag")){
                            plot.get().setNoPVPplayer(value);
                            player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoPVPplayer = " + value));
                        }
                        break; 
                    case "nopvpmonster":
                        if(player.hasPermission("actus.admin.plot.flag")){
                            plot.get().setNoPVPmonster(value);
                            player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoPVPmonster = " + value));
                        }
                        break; 
                    case "noprojectile":
                        if(player.hasPermission("actus.admin.plot.flag")){
                            plot.get().setNoProjectile(value);
                            player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoProjectile = " + value));
                        }
                        break; 
                    case "noliquidflow":
                        if(player.hasPermission("actus.admin.plot.flag")){
                            plot.get().setNoLiquidFlow(value);
                            player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoLiquidFlow = " + value));
                        }
                        break; 
                    case "autoforest":
                        plot.get().setAutoForest(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &eAutoForest = " + value));
                        break; 
                    case "nomsg":
                        plot.get().setNoMsg(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &eNoMsg = " + value));
                        break; 
                }
                plot.get().update();
                Data.commit();
                return CommandResult.success();
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
    
    private boolean getValue(boolean value){
        return !value;
    }
}
