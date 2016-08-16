package net.teraoctet.actus.commands.economy;

import java.util.Locale;
import java.util.Optional;
import static net.teraoctet.actus.Actus.itemShopManager;
import net.teraoctet.actus.economy.ItemShop;
import static net.teraoctet.actus.utils.DeSerialize.getLocation;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandShopList implements CommandExecutor {
    private static final CallBackEconomy  cb  = new CallBackEconomy(); 
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.admin.shoplist")) {
            if(!itemShopManager.getListItemShop().isEmpty()){
                src.sendMessage(MESSAGE("&e------------------------------"));
                src.sendMessage(MESSAGE("&e     Liste des ItemShop"));
                src.sendMessage(MESSAGE("&e------------------------------"));
                for(String s : itemShopManager.getListItemShop()){
                    Optional<Location<World>> loc = getLocation(s);
                    if(loc.isPresent()){
                        String worldName = loc.get().getExtent().getName();
                        String X = String.valueOf(loc.get().getBlockX());
                        String Y = String.valueOf(loc.get().getBlockX());
                        String Z = String.valueOf(loc.get().getBlockX());
                        Optional<ItemShop> itemShop = itemShopManager.getItemShop(s);
                        String item = itemShop.get().getItemStack().getItem().getTranslation().get(Locale.FRENCH);
                        String transaction = itemShop.get().getTransactType();
                        Double price = itemShop.get().getPrice();
                        src.sendMessages(Text.builder()
                                .append(MESSAGE("&8 - " + worldName + " XYZ: " + X + " " + Y + " " + Z + " ! &e" + transaction + " " + item + " &b" + price))
                                .onClick(TextActions.executeCallback(cb.callRemoveShop(s)))
                                .onHover(TextActions.showText(MESSAGE("&4Click ici pour supprimer le shop"))).build());
                    }
                }
                return CommandResult.success();
            }
        }                    
        return CommandResult.empty();
    }
}
