package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;
import static java.lang.String.format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import net.teraoctet.actus.economy.ItemShop;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import org.spongepowered.api.entity.Entity;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.guildManager;
import static net.teraoctet.actus.Actus.itemShopManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.player.PlayerManager;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.Data;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.message;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.PassengerData;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.EntityTypes;
import static org.spongepowered.api.entity.EntityTypes.BAT;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.Bat;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
 
        Player player = (Player) src;

        APlayer aplayer = getAPlayer(player.getIdentifier());
        double seconds = aplayer.getOnlinetime()/1000;
        long timeConnect = serverManager.dateToLong()- PlayerManager.getFirstTime(player.getIdentifier());
        seconds = seconds + (timeConnect / 1000);
        double h = Math.floor(seconds / 3600);
        double m = Math.floor((seconds - (h * 3600)) / 60);
        double s = seconds - (h * 3600) - (m * 60);
        String time = String.valueOf((int)h) + " heure(s) \n" + String.valueOf((int)m) + " minute(s) \n" + String.valueOf((int)s) + " seconde(s)";
        player.sendMessages(MESSAGE("&aTotal temps de connexion : \n" + time));
        return CommandResult.success();
    }
    


    private Consumer<CommandSource> CommandBank(Player player) {
	return (CommandSource src) -> {            APlayer gp = getAPlayer(player.getIdentifier());
            player.sendMessage(MESSAGE(String.valueOf(gp.getMoney())));
            src.sendMessage(MESSAGE("PROUuuuT ! pardon .."));
            //pages.sendTo(src);
           
        };
    }

}
