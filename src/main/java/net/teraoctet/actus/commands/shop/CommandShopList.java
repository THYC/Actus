package net.teraoctet.actus.commands.shop;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.itemShopManager;
import net.teraoctet.actus.shop.ItemShop;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandShopList implements CommandExecutor {
    private static final CallBackEconomy  cb  = new CallBackEconomy(); 
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("actus.admin.shoplist")) {
            Player player = (Player)src;

            if(!itemShopManager.getListItemShop().isEmpty()){
                src.sendMessage(MESSAGE("&e------------------------------"));
                src.sendMessage(MESSAGE("&e     Liste des ItemShop"));
                src.sendMessage(MESSAGE("&e------------------------------"));
                itemShopManager.getListItemShop().stream().forEach((uuid) -> {
                    Optional<Entity> frame = player.getLocation().getExtent().getEntity(UUID.fromString(uuid));
                    if(frame.isPresent()){
                        Location<World> loc = frame.get().getLocation();
                        String worldName = loc.getExtent().getName();
                        String X = String.valueOf(loc.getBlockX());
                        String Y = String.valueOf(loc.getBlockY());
                        String Z = String.valueOf(loc.getBlockZ());
                        Optional<ItemShop> itemShop = itemShopManager.getItemShop(UUID.fromString(uuid));
                        String item = itemShop.get().getItemStack().getItem().getTranslation().get(Locale.FRENCH);
                        String transaction = itemShop.get().getTransactType();
                        Double price = itemShop.get().getPrice();
                        src.sendMessages(Text.builder()
                                .append(MESSAGE("&d[+] "))
                                .onClick(TextActions.runCommand("/as " + uuid))
                                .onHover(TextActions.showText(MESSAGE("&eClick ici pour modifier l'ArmorStand"))).build()
                                .concat(Text.builder()
                                .append(MESSAGE("&a - " + worldName + " : " + X + " : " + Y + " : " + Z + " : "))
                                .onClick(TextActions.executeCallback(cb.callTPShop(player.getWorld(),uuid)))
                                .onHover(TextActions.showText(MESSAGE("&eClick ici pour te TP sur le shop"))).build())
                                .concat(Text.builder()
                                .append(MESSAGE("&e" + transaction + " " + item + " &b" + price))
                                .onClick(TextActions.executeCallback(cb.callRemoveShop(player.getWorld(),uuid)))
                                .onHover(TextActions.showText(MESSAGE("&4Click ici pour supprimer le shop"))).build()));
                    }else{
                        try {
                            itemShopManager.delItemShop(UUID.fromString(uuid));
                        } catch (IOException ex) {
                            Logger.getLogger(CommandShopList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                return CommandResult.success();
                
            }
        }else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
       
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
