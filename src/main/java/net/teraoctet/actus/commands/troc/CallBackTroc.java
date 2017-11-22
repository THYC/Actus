package net.teraoctet.actus.commands.troc;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.tm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.troc.EnumTransactType;
import static net.teraoctet.actus.troc.EnumTransactType.BUY;
import static net.teraoctet.actus.troc.EnumTransactType.SALE;
import net.teraoctet.actus.troc.Troc;
import net.teraoctet.actus.utils.Data;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Commande callback attenante aux TROC
 * @author thierry
 */
public class CallBackTroc {
       
    /**
     * teleporte le joueur sur les coordonnées du troc enregistré
     * @param loctroc coordonnée Location au format String "world:x:y:z"
     * @return 
     */
    public Consumer<CommandSource> callTPTroc(String loctroc) {
	return (CommandSource src) -> {
            Optional<Location<World>> loc = DeSerialize.getLocation(loctroc);
            if(loc.isPresent()){
                if(tm.hasTroc(loc.get())){
                    Player player = (Player)src;
                    player.setLocation(loc.get().add(0, +1, 0));
                }
            }         
        };
    }
    
    /**
     * affiche sur le scoreboard les infos enregistrées sur ce troc
     * @param loctroc coordonnée Location au format String "world:x:y:z"
     * @return 
     */
    public Consumer<CommandSource> callInfoTroc(String loctroc) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            Optional<Location<World>> loc = DeSerialize.getLocation(loctroc);
            if(loc.isPresent()){
                if(tm.hasTroc(loc.get())){
                    if(player.getScoreboard().getObjectives().isEmpty()){
                        tm.displayScoreboard(player,loctroc);
                    }else{
                        player.getScoreboard().getObjectives().clear();
                    }
                }
            }
            
        };
    }
    
    /**
     * affiche dans le GUI le contenu du Troc enregistré
     * @param locTroc
     * @return 
     */
    public Consumer<CommandSource> callOpenTroc(String locTroc) {
	return (CommandSource src) -> {
            Optional<Troc> troc;
            troc = tm.getTroc(locTroc,0);
            if(troc.isPresent()){
                Player player = (Player)src;
                if(tm.getInventory(troc.get()).isPresent()){
                    Inventory inv = tm.getInventory(troc.get()).get();
                    player.openInventory(inv);
                }
            }
        };
    }
    
    /**
     * Ajoute un ItemTroc
     * @param chestTroc Inventory Troc
     * @param slot  Position Slot de l'itemTroc
     * @param type type de transaction
     * @param price prix
     * @param qte qte maximum si transaction = buy/achat
     * @return 
     */
    public Consumer<CommandSource> callAddTroc(Inventory chestTroc, int slot, EnumTransactType type, double price, int qte) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());    
            
            String[] parts = chestTroc.getName().get().split(" ");
            String locTroc = parts[1];
            String ownerTroc = parts[2];
            int idGuild = Integer.valueOf(parts[3]);
            
            if(!ownerTroc.equalsIgnoreCase(player.getName()) && !ownerTroc.equalsIgnoreCase("LIBRE") && idGuild == 0){
                player.sendMessage(MESSAGE("&eOperation impossible, ce troc appartient a : " + getAPlayer(ownerTroc)));
                return;
            }
            if(idGuild != 0 && aplayer.getID_guild() != idGuild){
                player.sendMessage(MESSAGE("&eOperation impossible, ce troc appartient a la guild : " + Data.getGuild(idGuild)));
                return;
            }
        
            Troc troc;
            int index = 0;
            
            for (Inventory inv : chestTroc.slots()) {
                if (slot == index){
                    Optional<ItemStack> is = tm.setItemTroc(player, type, qte, price, locTroc);
                    if(!is.isPresent()){
                        player.sendMessage(MESSAGE("&4Creation de l'itemTroc impossible !"));
                        return;
                    }

                    inv.set(is.get());
                    troc = new Troc(locTroc, index, type, qte, price, player.getItemInHand(HandTypes.MAIN_HAND).get().copy().createSnapshot(),
                        player.getName(),player.getIdentifier(),aplayer.getID_guild());
                    try {
                        tm.save(troc);
                    } catch (IOException | ObjectMappingException ex) {
                        Logger.getLogger(CommandTrocAdd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    player.sendMessage(MESSAGE("&eItem troc cr\351\351 avec succes !"));
                    return;
                }

                index = index + 1;
                if(index == 9){
                    player.sendMessage(MESSAGE("&eTroc complet "));
                } 
            }
        };
    }
    
    public Consumer<CommandSource> callSelectTransactType(Inventory chestTroc, int slot, EnumTransactType type, double price) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            double pricetmp = price;
            if(pricetmp < 0)pricetmp = 0;
            
            BookView.Builder bv = BookView.builder()
            .author(MESSAGE("&bADMIN"))
            .title(MESSAGE("&bTROC"));
                
            Text text = 
                    
                    Text.builder()               
                    .append(MESSAGE("&4Item : &l&1" + player.getItemInHand(HandTypes.MAIN_HAND).get().getType().getType().getTranslation().get() + "\n"))
                    .append(MESSAGE("&r&4type de transaction : &l&1" + type.toString() + "\n\n"))                
                    .append(MESSAGE("&l&4.  ----  Tarif ----\n"))
                    .append(MESSAGE("&r&o&1Clique sur les liens plus bas \n&o&1pour modifier le prix\n\n"))
                    .append(MESSAGE("&r&n&4Prix :  &l&1" + pricetmp + "\n\n"))

                    //+10.00
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| + 10.0  "))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp + 10)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour augmenter de 10"))).build())
                    //-10.00
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| - 10.0  |\n"))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp - 10)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour augmenter de 10"))).build())
                    //+1.00
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| + 1.00  "))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp + 1)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                    .append(MESSAGE("")).build())
                    //-1.00
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| - 1.00  |\n"))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp - 1)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                    .append(MESSAGE("")).build())
                   //+0.01
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| + 0.01  "))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp + 0.01d)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                    .append(MESSAGE("")).build())
                    //-0.01
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| - 0.01  |\n"))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp - 0.01d)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                    .append(MESSAGE("")).build())
                    //+0.10
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| + 0.10  "))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp + 0.10d)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                    .append(MESSAGE("")).build())
                    //-0.01
                    .append(Text.builder()
                    .append(MESSAGE("&r&1| - 0.10  |\n\n"))
                    .onClick(TextActions.executeCallback(callSelectTransactType(chestTroc,slot,type,pricetmp - 0.10d)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                    .append(MESSAGE("")).build())
                    
                    .append(Text.builder()
                    .append(Text.builder()     
                    .append(MESSAGE("&r&l&1Clique ici pour valider ce prix"))
                    .onClick(TextActions.executeCallback(callValidPrice(chestTroc,slot,type,pricetmp,0)))
                    .onHover(TextActions.showText(MESSAGE("&eClique ici pour valider"))).build()).build())

                    .build();
            bv.addPage(text);
            player.sendBookView(bv.build());   
        };
    }
    
    private Consumer<CommandSource> callValidPrice(Inventory chestTroc, int slot, EnumTransactType type, double price, int qte) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString()); 
            
            int qtetmp = qte;
            if(qtetmp < 1)qtetmp = 1;
            
            if(type.equals(BUY)){
            
                BookView.Builder bv = BookView.builder()
                .author(MESSAGE("&bADMIN"))
                .title(MESSAGE("&bTROC"));

                Text text = 
                        Text.builder()               
                        .append(MESSAGE("&4Item : &l&1" + player.getItemInHand(HandTypes.MAIN_HAND).get().getType().getType().getTranslation().get() + "\n"))
                        .append(MESSAGE("&r&4type de transaction : &l&1" + type.toString() + "\n"))                
                        .append(MESSAGE("&r&4prix : &l&1" + price + "\n\n"))
                               
                        .append(MESSAGE("&l&4--  Qte maxi a acheter --\n"))
                        .append(MESSAGE("&r&o&1Clique sur les liens plus bas \n&o&1pour modifier la qte\n\n"))
                        .append(MESSAGE("&r&n&4QTE :  &l&1" + qtetmp + "\n\n"))

                        //+10.00
                        .append(Text.builder()
                        .append(MESSAGE("&r&1| + 10  "))
                        .onClick(TextActions.executeCallback(callValidPrice(chestTroc,slot,type,price,qtetmp + 10)))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour augmenter de 10"))).build())
                        //-10.00
                        .append(Text.builder()
                        .append(MESSAGE("&r&1| - 10  |\n"))
                        .onClick(TextActions.executeCallback(callValidPrice(chestTroc,slot,type,price, qtetmp - 10)))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour augmenter de 10"))).build())
                        //+1.00
                        .append(Text.builder()
                        .append(MESSAGE("&r&1| + 1   "))
                        .onClick(TextActions.executeCallback(callValidPrice(chestTroc,slot,type,price,qtetmp + 1)))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                        .append(MESSAGE("")).build())
                        //-1.00
                        .append(Text.builder()
                        .append(MESSAGE("&r&1| - 1   |\n\n"))
                        .onClick(TextActions.executeCallback(callValidPrice(chestTroc,slot,type,price,qtetmp - 1)))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour diminuer")))
                        .append(MESSAGE("")).build())
                        
                        .append(Text.builder()
                        .append(MESSAGE("&r&l&1Clique ici pour valider cette quantite : &l&1" + qtetmp))
                        .onClick(TextActions.executeCallback(callValidTroc(chestTroc,slot,type,price,qtetmp)))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour valider"))).build())

                        .build();
                bv.addPage(text);
                player.sendBookView(bv.build()); 
            }else{
                callValidTroc(chestTroc,slot,type,price,0).accept(src);
            }
        };
    }
    
    private Consumer<CommandSource> callValidTroc(Inventory chestTroc, int slot, EnumTransactType type, double price, int qte) {
	return (CommandSource src) -> {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString()); 
            
            Text txtQte = MESSAGE("&4achat maxi : &9" + qte + "\n");
            if(type.equals(SALE))txtQte = MESSAGE("");
            
            BookView.Builder bv = BookView.builder()
            .author(MESSAGE("&bADMIN"))
            .title(MESSAGE("&bTROC"));
                
            Text text = Text.builder()                
                    .append(MESSAGE("&4Item : &1" + player.getItemInHand(HandTypes.MAIN_HAND).get().getType().getType().getTranslation().get() + "\n"))                    
                    .append(MESSAGE("&4type de transaction : &1" + type.toString() + "\n")) 
                    .append(MESSAGE("&4tarif : &1" + price + "\n\n"))
                    .append(txtQte)
                    
                    .append(MESSAGE("&l&1Clique ici pour enregistrer"))
                        .onClick(TextActions.executeCallback(callAddTroc(chestTroc,slot,type,price,qte)))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour enregister")))

                    .build();
            bv.addPage(text);
            player.sendBookView(bv.build());   
        };
    }
    
}
