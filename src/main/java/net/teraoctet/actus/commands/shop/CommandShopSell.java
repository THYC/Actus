package net.teraoctet.actus.commands.shop;

import static java.lang.Math.round;
import java.util.Optional;
import java.util.UUID;
import static net.teraoctet.actus.Actus.action;
import static net.teraoctet.actus.Actus.ism;
import static net.teraoctet.actus.Actus.sm;
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
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

public class CommandShopSell implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.shop")) {
            Player player = (Player) src;
            if(!action.containsKey(player.getIdentifier())){
                player.sendMessages(MESSAGE("&eVous n'avez pas de transaction en cours"));
                player.sendMessages(MESSAGE("&eVous devez d'abord faire un click droit sur le Shop d'achat"));
                return CommandResult.empty();
            }
            action.remove(player.getIdentifier());
            Optional<String> uuid = ctx.<String> getOne("uuid");
            Optional<ItemShop> itemShop = ism.getItemShop(UUID.fromString(uuid.get()));
            if(itemShop.isPresent()){
                ItemStack is = itemShop.get().getItemStack();
                double price = itemShop.get().getPrice();
                
                if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                    if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(is.getType()) 
                            && sm.getItemID(player.getItemInHand(HandTypes.MAIN_HAND).get()).get().equals(sm.getItemID(is).get())){
                        int qte = player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity();
                        double coin = price*qte*100;
                        coin = round(coin);
                        coin = coin/100;
                        
                        // On verifie si le joueur a une bourse dans son inventaire et on lui verse la monaie
                        for(Inventory slotInv : player.getInventory().slots()){
                            Optional<ItemStack> peek = slotInv.peek();
                            if(peek.isPresent()){
                                if(ism.hasCoinPurses(peek.get())){
                                    slotInv.clear();
                                    slotInv.offer(ism.addCoin(coin, peek.get()).get());
                                    player.setItemInHand(HandTypes.MAIN_HAND,null);
                                    player.sendMessages(MESSAGE("&ela somme a \351t\351 ajout\351 a votre bourse :)"));
                                    return CommandResult.success();
                                }
                            }
                        }

                        // si le joueur n'a pas de bourse, on lui en donne une
                        player.setItemInHand(HandTypes.MAIN_HAND,ism.CoinPurses(player, coin).get());
                        return CommandResult.success();
                    } else {
                        player.sendMessage(MESSAGE("&bTon item ne correspond pas a la demande"));
                        return CommandResult.success();
                    }
                } else {
                    player.sendMessage(MESSAGE("&eAucun item disponible"));  
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
