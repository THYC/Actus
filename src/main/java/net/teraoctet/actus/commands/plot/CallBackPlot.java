package net.teraoctet.actus.commands.plot;

import java.util.function.Consumer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
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
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoEnter = " + value));
                    break;
                case "nofly":
                    plot.setNoFly(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoFly = " + value));
                    break;
                case "nobuild":
                    plot.setNoBuild(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoBuild = " + value));
                    break;
                case "nobreak":
                    plot.setNoBreak(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoBreak = " + value));
                    break;
                case "nointeract":
                    plot.setNoInteract(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoInteract = " + value));
                    break;
                case "noteleport":
                    plot.setNoTeleport(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoTeleport = " + value));
                    break;
                case "nofire":
                    plot.setNoFire(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoFire = " + value));
                    break;
                case "nomob":
                    plot.setNoMob(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoMob = " + value));
                    break;
                case "notnt":
                    plot.setNoTNT(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoTNT = " + value));
                    break;
                case "nocommand":
                    plot.setNoCommand(value);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&7Flag enregistr\351 : &enoCommand = " + value));
                    break;
            }
            plot.update();
            Data.commit();
        };
    }
}
