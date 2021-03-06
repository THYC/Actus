package net.teraoctet.actus.commands.shop;

import java.io.IOException;
import static java.lang.Math.round;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.action;
import static net.teraoctet.actus.Actus.inputDouble;
import static net.teraoctet.actus.Actus.ism;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.shop.ItemShop;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.DEPOSIT_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import static org.spongepowered.api.item.ItemTypes.EMERALD;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CallBackEconomy {
    
    public Consumer<CommandSource> callBankRetrait(Integer type, double coin) {
        /*----------------------------------------------*/
        /* Confirmation de la transaction bancaire      */
        /*----------------------------------------------*/
	return (CommandSource src) -> {
            Player player = (Player)src;
            switch (type){
                case 1: //retrait de sommes prédéfini
                    inputDouble.put(player.getIdentifier(), coin);
                    src.sendMessage(MESSAGE("&eMaintenant cliques de nouveau sur le panneau pour confirmer le retrait de " + coin + " \351meraude(s)"));
                    src.sendMessage(MESSAGE("&esi tu tiens ta bourse dans ta main, la somme sera vers\351 dessus sinon tu aura des \351meraudes"));
                    break;
                case 2: //retrait saisie par le joueur
                    inputDouble.put(player.getIdentifier(), 0.0);
                    src.sendMessage(MESSAGE("&eTapes la somme voulu :"));
                    break;
            }
        };
    }
    
    public Consumer<CommandSource> callBankDepot(Integer type) {
        /*----------------------------------------------*/
        /* Confirmation de la transaction bancaire      */
        /*----------------------------------------------*/
	return (CommandSource src) -> {
            Player player = (Player)src;
            APlayer aplayer = getAPlayer(player.getIdentifier());
            int nb = 0;
            
            switch (type){
                case 1: //Depot Emeraude en main
                    if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                        if(player.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(EMERALD)){
                            nb = player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity();
                            player.setItemInHand(HandTypes.MAIN_HAND,null);
                        } else {
                            src.sendMessage(MESSAGE("&eTu n'as pas d'Emeraude en main")); 
                            return;
                        }
                    }
                    break;
                    
                case 2: //retrait saisie par le joueur
                    for(Inventory slotInv : player.getInventory().slots()){
                        Optional<ItemStack> peek = slotInv.peek();
                        if(peek.isPresent()){
                            if(peek.get().getType().equals(EMERALD)){
                                nb = nb + peek.get().getQuantity();
                                slotInv.clear(); 
                            }
                        }
                    }

                    if(nb == 0){
                        src.sendMessage(MESSAGE("&eTu n'as pas d'Emeraude dans ton inventaire")); 
                        return;
                    } else {
                        player.sendMessages(MESSAGE("&b" + String.valueOf(nb) + " Emeraude(s) trouv\351e(s)"));
                    }
                    break;
            }
            if(nb != 0){
                aplayer.creditMoney(nb);
                player.sendMessages(DEPOSIT_SUCCESS(String.valueOf(nb)));
            }
        };
    }
    
    public Consumer<CommandSource> callShopSellAll(String uuid) {
        /*----------------------------------------------*/
        /* Achat multiple                               */
        /*----------------------------------------------*/
        return (CommandSource src) -> {
            Player player = (Player)src;
            if(!action.containsKey(player.getIdentifier())){
                player.sendMessages(MESSAGE("&eVous n'avez pas de transaction en cours"));
                player.sendMessages(MESSAGE("&eVous devez d'abord faire un click droit sur le Shop d'achat"));
                return;
            }
            action.remove(player.getIdentifier());
            Optional<ItemShop> itemShop = ism.getItemShop(UUID.fromString(uuid));
            if(itemShop.isPresent()){
                ItemStack is = itemShop.get().getItemStack();
                double price = itemShop.get().getPrice();
                
                int qte = 0;
                for(Inventory slotInv : player.getInventory().slots()){
                    Optional<ItemStack> peek = slotInv.peek();
                    if(peek.isPresent()){
                        if(peek.get().getType().equals(is.getType()) 
                                && sm.getItemID(peek.get()).get().equals(sm.getItemID(is).get())){
                            qte = qte + peek.get().getQuantity();
                            slotInv.clear(); 
                        }
                    }
                }
                
                if(qte != 0){
                    double coin = price*qte*100;
                    coin = round(coin);
                    coin = coin/100;
                    player.sendMessages(MESSAGE("&b" + String.valueOf(qte) + " item x " + String.valueOf(price) + " = &e" + String.valueOf(coin)));
                        
                    // On verifie si le joueur a une bourse dans son inventaire et on lui verse la monaie
                    for(Inventory slotInv : player.getInventory().slots()){
                        Optional<ItemStack> peek = slotInv.peek();
                        if(peek.isPresent()){
                            if(ism.hasCoinPurses(peek.get())){
                                slotInv.clear();
                                slotInv.offer(ism.addCoin(coin, peek.get()).get());
                                player.setItemInHand(HandTypes.MAIN_HAND,null);
                                player.sendMessages(MESSAGE("&ela somme a \351t\351 ajout\351 a votre bourse :)"));
                                return;
                            }
                        }
                    }

                    // si le joueur n'a pas de bourse, on lui en donne une
                    player.getInventory().offer(ism.CoinPurses(player, coin).get());
                } else {
                    player.sendMessage(MESSAGE("&bAucun item ne correspond pas a la demande"));
                }
            } else {
                player.sendMessage(MESSAGE("&eAucun item disponible"));  
            }
        };
    }
    
    public Consumer<CommandSource> callRemoveShop(World world, String uuid) {
	return (CommandSource src) -> {
            Optional<Entity> entity = world.getEntity(UUID.fromString(uuid));                   
            if(entity.isPresent()){
                try {
                    ism.delItemShop(UUID.fromString(uuid));
                    entity.get().remove();
                    src.sendMessage(MESSAGE("&e-------------------------"));
                    src.sendMessage(MESSAGE("&4ItemShop supprim\351"));  
                    src.sendMessage(MESSAGE("&e-------------------------"));
                } catch (IOException ex) {
                    Logger.getLogger(CallBackEconomy.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
    
    public Consumer<CommandSource> callTPShop(World world, String uuid) {
	return (CommandSource src) -> {
            Optional<Entity> entity = world.getEntity(UUID.fromString(uuid));                   
            if(entity.isPresent()){
                Location loc = entity.get().getLocation();
                Player player = (Player)src;
                player.setLocation(loc);
            }
        };
    }
    
    public Consumer<CommandSource> callBankCoinPurses() {
	return (CommandSource src) -> {
            Player player = (Player)src;
            player.getInventory().offer(ism.CoinPurses(player, 0).get());
            src.sendMessage(MESSAGE("&eUne bourse a \351t\351 ajout\351 a ton inventaire"));
            src.sendMessage(MESSAGE("&eElle a un cr\351dit de O"));
            src.sendMessage(MESSAGE("&erecharge la en faisant un retrait banquaire"));
        };
    }
}
