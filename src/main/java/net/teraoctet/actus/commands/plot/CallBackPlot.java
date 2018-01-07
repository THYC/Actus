package net.teraoctet.actus.commands.plot;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.bookmessage.Book;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_LOADED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_PLOT_SUCCESS;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CallBackPlot {
    
    public Consumer<CommandSource> callChangeFlag(Plot plot, String flag, boolean value) {
	return (CommandSource src) -> {
            Player player = (Player)src;
            switch (flag.toLowerCase()){
                case "noenter":
                    plot.setNoEnter(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoEnter = " + ptm.ValueOf(value)));
                    break;
                case "nofly":
                    plot.setNoFly(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoFly = " + ptm.ValueOf(value)));
                    break;
                case "nobuild":
                    plot.setNoBuild(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoBuild = " + ptm.ValueOf(value)));
                    break;
                case "nobreak":
                    plot.setNoBreak(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoBreak = " + ptm.ValueOf(value)));
                    break;
                case "nointeract":
                    plot.setNoInteract(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoInteract = " + ptm.ValueOf(value)));
                    break;
                case "noteleport":
                    plot.setNoTeleport(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoTeleport = " + ptm.ValueOf(value)));
                    break;
                case "nofire":
                    plot.setNoFire(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoFire = " + ptm.ValueOf(value)));
                    break;
                case "nomob":
                    plot.setNoMob(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoMob = " + ptm.ValueOf(value)));
                    break;
                case "notnt":
                    plot.setNoTNT(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoTNT = " + ptm.ValueOf(value)));
                    break;
                case "nocommand":
                    plot.setNoCommand(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoCommand = " + ptm.ValueOf(value)));
                    break;
                case "spawngrave":
                    plot.setSpawnGrave(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &espawnGrave = " + ptm.ValueOf(value)));
                    break;
                case "noanimal":
                    plot.setNoAnimal(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoAnimal = " + ptm.ValueOf(value)));
                    break;
                case "nopvpmonster":
                    plot.setNoPVPmonster(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoPVPmonster = " + ptm.ValueOf(value)));
                    break;
                case "nopvplayer":
                    plot.setNoPVPplayer(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoPVPplayer = " + ptm.ValueOf(value)));
                    break;
                case "noprojectile":
                    plot.setNoProjectile(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoProjectile = " + ptm.ValueOf(value)));
                    break;
                case "noliquidflow":
                    plot.setNoLiquidFlow(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoLiquidFlow = " + ptm.ValueOf(value)));
                    break;
                case "autoforest":
                    plot.setAutoForest(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &eAutoForest = " + ptm.ValueOf(value)));
                    break;
            }
    
            plot.update();
            Data.commit();
            Sponge.getCommandManager().process(player,"plot flag");
        };
    }
    
    public Consumer<CommandSource> callHelpPlotFlag() {
	return (CommandSource src) -> {
            Player player = (Player)src;
            try {
                Optional<Book> book = configBook.load("help_plot_flag");
                if(book.isPresent()){
                    player.sendBookView(book.get().getBookView());
                }
            } catch (ObjectMappingException | IOException ex) {
                Logger.getLogger(CallBackPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }
    
    public Consumer<CommandSource> callCreate(String plotName, Boolean strict, int amount, int level) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            PlotSelection plotselect = ptm.getPlotSel(player);
            Location[] c = {plotselect.getMinPosLoc().get(), plotselect.getMaxPosLoc().get()};

            String playerUUID = player.getUniqueId().toString();
            if(aplayer.getLevel() == 10){ playerUUID = "ADMIN";} else { aplayer.debitMoney(amount);}

            int y1 = (int)c[0].getY();
            int y2 = (int)c[1].getY();

            if(strict == false) { 
                player.sendMessage(BEDROCK2SKY_PROTECT_PLOT_SUCCESS(player,plotName));
                y1 = 0;
                y2 = 500;
            } else {
                player.sendMessage(PROTECT_PLOT_SUCCESS(player,plotName).concat(Text.of(TextColors.GREEN," Y " + y1 + " : " + y2)));
            }

            Location <World> world = c[0];
            String worldName = world.getExtent().getName();
            
            int x1 = c[0].getBlockX();
            int z1 = c[0].getBlockZ();
            int x2 = c[1].getBlockX();
            int z2 = c[1].getBlockZ();
                        
            String message = "&b-- SECURISED --";

            Plot plot = new Plot(plotName,level,worldName,x1,y1,z1,x2,y2,z2,playerUUID,playerUUID);
            plot.insert();
            Data.commit();
            Data.addPlot(plot);
            
            player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PLOT(player,plotName));
            player.sendMessage(Text.builder("Clique ici, pour voir les flags de ta parcelle !").onClick(TextActions.runCommand("/p flaglist " + plotName)).color(TextColors.AQUA).build());  
        };
    }
    
    public Consumer<CommandSource> callCreate(String plotName, Boolean strict, int amount, int level, Location<World> loc1, Location<World> loc2) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Location[] c = {loc1, loc2};

            String playerUUID = player.getUniqueId().toString();
            if(aplayer.getLevel() == 10){ playerUUID = "ADMIN";} else { aplayer.debitMoney(amount);}

            int y1 = (int)c[0].getY();
            int y2 = (int)c[1].getY();

            if(strict == false) { 
                player.sendMessage(BEDROCK2SKY_PROTECT_PLOT_SUCCESS(player,plotName));
                y1 = 0;
                y2 = 500;
            } else {
                player.sendMessage((ChatType) PROTECT_PLOT_SUCCESS(player,plotName),Text.of(TextColors.GREEN," Y " + y1 + " : " + y2));
            }

            Location <World> world = c[0];
            String worldName = world.getExtent().getName();
            
            int x1 = c[0].getBlockX();
            int z1 = c[0].getBlockZ();
            int x2 = c[1].getBlockX();
            int z2 = c[1].getBlockZ();
                        
            String message = "&b-- SECURISED --";

            Plot plot = new Plot(plotName,level,worldName,x1,y1,z1,x2,y2,z2,playerUUID,playerUUID);
            plot.insert();
            Data.commit();
            Data.addPlot(plot);

            player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PLOT(player,plotName));
            player.sendMessage(Text.builder("Clique ici, pour voir les flags de ta parcelle !").onClick(TextActions.runCommand("/p flaglist " + plotName)).color(TextColors.AQUA).build());  
        };
    }
}
