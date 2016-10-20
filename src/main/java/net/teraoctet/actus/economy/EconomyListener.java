package net.teraoctet.actus.economy;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import static net.teraoctet.actus.Actus.action;
import static net.teraoctet.actus.Actus.inputDouble;
import static net.teraoctet.actus.Actus.itemShopManager;
import net.teraoctet.actus.commands.economy.CallBackEconomy;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.DEPOSIT_SUCCESS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.WITHDRAW_SUCCESS;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.ItemTypes;

public class EconomyListener {
    
    private static final CallBackEconomy  cb  = new CallBackEconomy();  
    public EconomyListener() {}

    @Listener
    public void onInteractShopRight(InteractEntityEvent.Secondary event, @First Player player){    
        /*----------------------------------------------*/
        /* Transaction ItemShop                         */
        /*----------------------------------------------*/
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Entity entity = event.getTargetEntity();
        UUID uuid = entity.getUniqueId();
        
        //On verifie si le joueur a cliqué sur un ItemFame ou un ArmorStand
        if(entity.getType().getName().contains("itemframe") || entity.getType().getName().contains("armorstand")){
            
            //Si aucum ItemShop est enregistré a cette coordonnée on propose d'en creer un
            if(!itemShopManager.hasShop(uuid) && player.hasPermission("actus.admin.shop") && aplayer.getLevel()==10){ 
                Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
                if(is.isPresent()){
                    player.sendMessage(MESSAGE("\n\n"));
                    player.sendMessage(MESSAGE("&l&e-------------------------"));
                    player.sendMessage(MESSAGE("&7Voulez vous cr\351er un nouveau ItemShop ?"));
                    player.sendMessage(Text.builder("Clique ici pour lancer la cr\351ation d'un ItemShop").onClick(TextActions.runCommand("/shopcreate " + uuid.toString())).color(TextColors.AQUA).build()); 
                    player.sendMessage(MESSAGE("&l&e-------------------------"));
                    event.setCancelled(true);
                }
            }else{
                if(itemShopManager.hasShop(uuid) && aplayer.getLevel()!=10){
                    Optional<ItemShop> is = itemShopManager.getItemShop(uuid);
                    if(is.isPresent()){
                        ItemStack itemStack = is.get().getItemStack();
                        String name = "";
                        Optional<DisplayNameData> displayData = itemStack.get(DisplayNameData.class);
                        if(displayData.isPresent()){
                            name = displayData.get().displayName().get().toPlain();
                        }else{                          
                            name = Text.builder(itemStack.getTranslation()).build().toPlain();
                        }
                        EnchantmentData enchantmentData = itemStack.getOrCreate(EnchantmentData.class).get();
                        final ListValue<ItemEnchantment> enchantments = enchantmentData.enchantments();
                        String enchantment = "";
                        for (ItemEnchantment e : enchantments) {
                            enchantment = enchantment + e.getEnchantment().getId() + " / ";
                        }
                        enchantment = enchantment.replace("minecraft:", "");
                        Text text = Text.EMPTY;
                        
                        //on affiche le menu de transaction de l'item
                        
                        //--------
                        //  ACHAT 
                        //--------
                        if(is.get().getTransactType().contains("buy")){
                            action.put(player, "shop");
                            text = 
                                Text.builder()  .append(MESSAGE("\n\n\n"))
                                                .append(MESSAGE("&l&b   -----------------\n"))
                                                .append(MESSAGE("&l&b         ItemShop \n"))
                                                .append(MESSAGE("&l&b   -----------------\n"))
                                                .append(MESSAGE("&e&o" + is.get().getTransactType() + " : &l&b" + name + "\n"))
                                                .append(MESSAGE("&e&o(" + itemStack.getItem().getName() + ")\n"))
                                                .append(MESSAGE("&e&oPrix : &b" + is.get().getPrice() + " Emeraude(s)\n")).build().concat(
                                Text.builder()  .append(MESSAGE("&b - Clique ici pour vendre les items de ta main seulemeent\n"))
                                                .onClick(TextActions.runCommand("/shopsell " + uuid)).build()).concat(  
                                Text.builder()  .append(MESSAGE("&b - ici pour vendre tous les items present dans ton stuff\n\n"))
                                                .onClick(TextActions.executeCallback(cb.callShopSellAll(uuid.toString()))).build());
                        
                        //--------
                        //  VENTE 
                        //--------
                        } else {   
                            action.put(player, "shop");
                            text = 
                                Text.builder()  .append(MESSAGE("\n\n\n"))
                                                .append(MESSAGE("&l&b   -----------------\n"))
                                                .append(MESSAGE("&l&b         ItemShop \n"))
                                                .append(MESSAGE("&l&b   -----------------\n"))
                                                .append(MESSAGE("&e&o" + is.get().getTransactType() + " : &l&b" + name + "\n"))
                                                .append(MESSAGE("&e&o(" + itemStack.getItem().getName() + ")\n"))
                                                .append(MESSAGE("&l&eEnchantments :\n"))
                                                .append(MESSAGE("&b" + enchantment + "\n"))
                                                .append(MESSAGE("&e&oPrix : &b" + is.get().getPrice() + " Emeraude(s)\n")).build().concat(
                                Text.builder()  .append(MESSAGE("&b    Cliquer ici pour confirmer la transaction\n"))
                                                .onClick(TextActions.runCommand("/shoppurchase " + uuid.toString())).build());        
                        }
                        
                        player.sendMessage(text);
                        event.setCancelled(true);
                    } 
                }
            }
        } 
    }
    
    @Listener
    public void onInteractShopLeft(InteractEntityEvent.Primary event, @First Player player) throws IOException{     
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Entity entity = event.getTargetEntity();
        UUID uuid = entity.getUniqueId();
        
        if(entity.getType().getName().contains("itemframe") || entity.getType().getName().contains("armorstand")){
            if(itemShopManager.hasShop(uuid)){
                if(aplayer.getLevel() == 10){ 
                    entity.remove();
                    itemShopManager.delItemShop(uuid);
                    player.sendMessage(MESSAGE("&e-------------------------"));
                    player.sendMessage(MESSAGE("&4ItemShop supprim\351"));
                    player.sendMessage(MESSAGE("&e-------------------------")); 
                }else{
                    event.setCancelled(true);
                }
            }
        } 
    }
    
    @Listener
    public void onInteractSignBank(InteractBlockEvent event, @First Player player){ 
        /*----------------------------------------------*/
        /* Gestion de son compte en banque par panneaux */
        /*----------------------------------------------*/
        BlockSnapshot b = event.getTargetBlock();
        if(!b.getLocation().isPresent()){return;}
        
        Location loc = b.getLocation().get();              
        Optional<TileEntity> block = loc.getTileEntity();
        if (block.isPresent()) {
            
            //on verifie si le joueur clique sur un panneau
            TileEntity tile=block.get();
            if (tile instanceof Sign) {
                if (event instanceof InteractBlockEvent.Secondary){
                    
                    //on lis les 2 premières lignes du panneau
                    Sign sign=(Sign)tile;
                    Optional<SignData> optional=sign.getOrCreate(SignData.class);
                    if (optional.isPresent()) {
                        SignData offering = optional.get();
                        Text txt1 = offering.lines().get(0);
                        Text txt2 = offering.lines().get(1);
                        
                        APlayer aplayer = getAPlayer(player.getIdentifier());
                                                
                        //on verifie si le joueur a cliqué sur un panneau BANK
                        if (txt1.equals(MESSAGE("&l&1[BANK]"))){
                            
                            //on verifie si le joueur a cliqué sur un panneau retrait
                            if (txt2.equals(MESSAGE("&o&1Retrait"))){
                                
                                //on verifie si il y a eu une demande de saisie
                                Optional<Double>coin;
                                if(inputDouble.containsKey(player)){
                                    coin = Optional.of(inputDouble.get(player));
                                    if(coin.isPresent()){
                                        
                                        //si une valeur a été saisie précédemment on effectue le versement et on sort
                                        if(coin.get() != 0.0){
                                            int coinInt = coin.get().intValue();
                                            if(aplayer.getMoney()> coinInt){
                                                aplayer.debitMoney(coinInt);
                                                
                                                //on verifie si le joueur a une bourse, si oui on la credite
                                                if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                                                    if(itemShopManager.hasCoinPurses(player.getItemInHand(HandTypes.MAIN_HAND).get())){
                                                        Optional<ItemStack> coinPurse = itemShopManager.addCoin(coin.get(),player.getItemInHand(HandTypes.MAIN_HAND).get());
                                                        if(coinPurse.isPresent()){
                                                            player.setItemInHand(HandTypes.MAIN_HAND,coinPurse.get());
                                                            return;
                                                        }
                                                    }
                                                }
                                                
                                                //si le joueur n'avait pas de bourse, on lui verse des emeraudes
                                                ItemStack is = ItemStack.of(ItemTypes.EMERALD, coinInt);
                                                player.getInventory().offer(is);
                                                inputDouble.remove(player);
                                                player.sendMessages(WITHDRAW_SUCCESS(String.valueOf(coinInt)));
                                                return;
                                            }else{
                                                player.sendMessage(MISSING_BALANCE());
                                            }
                                        }                                    
                                    }
                                }
                                
                                //On affiche le menu bank pour un retrait au joueur
                                Text text = 
                                        Text.builder()  .append(MESSAGE("\n\n\n"))
                                                        .append(MESSAGE("&l&b   -----------------\n"))
                                                        .append(MESSAGE("&l&b     RETRAIT \n"))
                                                        .append(MESSAGE("&l&b   -----------------\n\n"))
                                                        .append(MESSAGE("&b&oClique sur un des 4 choix\n")).build().concat(
                                        Text.builder()  .append(MESSAGE("&e[1] Retrait de 10 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(cb.callBankRetrait(1,10.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[2] Retrait de 20 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(cb.callBankRetrait(1,20.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[3] Retrait de 30 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(cb.callBankRetrait(1,30.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[4] &bje veux saisir la somme\n\n"))
                                                        .onClick(TextActions.executeCallback(cb.callBankRetrait(2,0.0))).build());
                                player.sendMessage(text);
                            }
                        }
                        
                        //on verifie si le joueur a cliqué sur un panneau depot
                        if (txt2.equals(MESSAGE("&o&1Depot"))){
                            
                            //on verifie si le joueur a une bourse en main
                            if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                                if(itemShopManager.hasCoinPurses(player.getItemInHand(HandTypes.MAIN_HAND).get())){
                                    Double depot = itemShopManager.getQteCoin(player.getItemInHand(HandTypes.MAIN_HAND).get());
                                    aplayer.creditMoney(depot);
                                    player.sendMessages(DEPOSIT_SUCCESS(String.valueOf(depot)));
                                    player.setItemInHand(HandTypes.MAIN_HAND,null);
                                    return;
                                }
                            }
         
                            //on affiche le menu bank pour un depot au joueur
                            Text text = 
                                        Text.builder()  .append(MESSAGE("\n\n\n"))
                                                        .append(MESSAGE("&l&b   -----------------\n"))
                                                        .append(MESSAGE("&l&b     DEPOT \n"))
                                                        .append(MESSAGE("&l&b   -----------------\n\n"))
                                                        .append(MESSAGE("&b&oClique sur un des 2 choix\n")).build().concat(
                                        Text.builder()  .append(MESSAGE("&e[1] Depot des \351meraude(s) situ\351 dans ma main\n"))
                                                        .onClick(TextActions.executeCallback(cb.callBankDepot(1))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[2] Depot de toutes les \351meraude(s) situ\351s dans mon stuff\n"))
                                                        .onClick(TextActions.executeCallback(cb.callBankDepot(2))).build());
                            player.sendMessage(text);
                            
                        }
                    }
                } 
            }
        }
    }
}
