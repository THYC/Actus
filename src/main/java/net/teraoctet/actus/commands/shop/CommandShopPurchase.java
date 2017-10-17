package net.teraoctet.actus.commands.shop;

import java.util.Optional;
import java.util.UUID;
import static net.teraoctet.actus.Actus.action;
import static net.teraoctet.actus.Actus.itemShopManager;
import net.teraoctet.actus.shop.ItemShop;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.data.type.HandTypes;
import static org.spongepowered.api.item.ItemTypes.EMERALD;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

public class CommandShopPurchase implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.shop")) {
            Player player = (Player) src;
            if(!action.containsKey(player)){
                player.sendMessages(MESSAGE("&eVous n'avez pas de transaction en cours"));
                player.sendMessages(MESSAGE("&eVous devez d'abord faire un click droit sur le Shop de vente"));
                return CommandResult.empty();
            }
            action.remove(player);
            Optional<String> uuid = ctx.<String> getOne("uuid");
            Optional<ItemShop> itemShop = itemShopManager.getItemShop(UUID.fromString(uuid.get()));
            if(itemShop.isPresent()){
                ItemStack is = itemShop.get().getItemStack();
                int price = itemShop.get().getPriceInt();
                
                Inventory items = player.getInventory().query(EMERALD);
                if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                    if(itemShopManager.hasCoinPurses(player.getItemInHand(HandTypes.MAIN_HAND).get())){
                        Optional<ItemStack> coin = itemShopManager.removeCoin(price,player.getItemInHand(HandTypes.MAIN_HAND).get());
                        if(coin.isPresent()){
                            player.setItemInHand(HandTypes.MAIN_HAND,coin.get());
                            player.getInventory().offer(is);
                            return CommandResult.success();
                        } else {
                            player.sendMessage(MESSAGE("&bPas assez de monnaie dans ta bourse"));
                        }
                    }
                }
                if (items.peek(price).filter(stack -> stack.getQuantity() == price).isPresent()) {
                    items.poll(price);
                    player.getInventory().offer(is);
                } else {
                    player.sendMessage(MESSAGE("&eTransaction annul\351e, tu n'as pas le nombre d'emeraudes suffisant sur toi"));  
                }
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
}
