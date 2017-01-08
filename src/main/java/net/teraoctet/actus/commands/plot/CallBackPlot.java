package net.teraoctet.actus.commands.plot;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;
import static net.teraoctet.actus.Actus.inputShop;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.bookmessage.Book;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class CallBackPlot {
    
    public Consumer<CommandSource> callChangeFlag(Plot plot, String flag, Integer value) {
	return (CommandSource src) -> {
            Player player = (Player)src;
            switch (flag.toLowerCase()){
                case "noenter":
                    plot.setNoEnter(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoEnter = " + plotManager.ValueOf(value)));
                    break;
                case "nofly":
                    plot.setNoFly(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoFly = " + plotManager.ValueOf(value)));
                    break;
                case "nobuild":
                    plot.setNoBuild(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoBuild = " + plotManager.ValueOf(value)));
                    break;
                case "nobreak":
                    plot.setNoBreak(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoBreak = " + plotManager.ValueOf(value)));
                    break;
                case "nointeract":
                    plot.setNoInteract(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoInteract = " + plotManager.ValueOf(value)));
                    break;
                case "noteleport":
                    plot.setNoTeleport(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoTeleport = " + plotManager.ValueOf(value)));
                    break;
                case "nofire":
                    plot.setNoFire(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoFire = " + plotManager.ValueOf(value)));
                    break;
                case "nomob":
                    plot.setNoMob(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoMob = " + plotManager.ValueOf(value)));
                    break;
                case "notnt":
                    plot.setNoTNT(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoTNT = " + plotManager.ValueOf(value)));
                    break;
                case "nocommand":
                    plot.setNoCommand(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoCommand = " + plotManager.ValueOf(value)));
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
}
