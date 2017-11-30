package net.teraoctet.actus.troc;

import java.io.IOException;
import static java.lang.Math.abs;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.CB_TROC;
import static net.teraoctet.actus.Actus.TROC;
import static net.teraoctet.actus.Actus.ism;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.Actus.tm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.troc.EnumTransactType.BUY;
import static net.teraoctet.actus.troc.EnumTransactType.SALE;
import static net.teraoctet.actus.utils.Config.ENABLE_TROC_SCOREBOARD;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import static net.teraoctet.actus.utils.Data.getGuild;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import static org.spongepowered.api.item.ItemTypes.AIR;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TrocListener {  
        
    @Listener
    public void onCloseTroc(InteractInventoryEvent.Close event, @First Player player) throws IOException, ObjectMappingException {
        Container b = event.getTargetInventory(); 

        if(b.getName().get().contains("TROC")){
            String[] parts = event.getTargetInventory().getName().get().split(" ");
            String locTroc = parts[1];
            final String loc = locTroc;
            player.setScoreboard(null);

            String ticket = "&3----Total----\n";
            double total = 0;
            
            int tot[] = {0,0,0,0,0,0,0,0,0,0};
            double price[] = {0,0,0,0,0,0,0,0,0};
            String label[] = {"","","","","","","","",""};                  
            
            for(Entry<Integer, ItemTransact> transact : TROC.get(player).entrySet()) {                
                switch(transact.getKey()){
                    case 0:
                        if(transact.getValue().getQte()!=0){
                            tot[0] = abs(transact.getValue().getQte());
                            price[0] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[0] = "&3Prix &l&e: " + price[0] + " &l&5: " + tot[0] + " " 
                                    + tm.getTroc(loc,0).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,0).get().getItem()).get() + "\n";
                        }
                        break;
                    case 1:
                        if(transact.getValue().getQte()!=0){
                            tot[1] = abs(transact.getValue().getQte());
                            price[1] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[1] = "&3Prix &l&e: " + price[1] + " &l&5: " + tot[1] + " " 
                                    + tm.getTroc(loc,1).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,1).get().getItem()).get() + "\n";
                        }
                        break;
                    case 2:
                        if(transact.getValue().getQte()!=0){
                            tot[2] = abs(transact.getValue().getQte());
                            price[2] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[2] = "&3Prix &l&e: " + price[2] + " &l&5: " + tot[2] + " " 
                                    + tm.getTroc(loc,2).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,2).get().getItem()).get() + "\n";
                        }
                        break;
                    case 3:
                        if(transact.getValue().getQte()!=0){
                            tot[3] = abs(transact.getValue().getQte());
                            price[3] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[3] = "&3Prix &l&e: " + price[3] + " &l&5: " + tot[3] + " " 
                                    + tm.getTroc(loc,3).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,3).get().getItem()).get() + "\n";
                        }
                        break;
                    case 4:
                        if(transact.getValue().getQte()!=0){
                            tot[4] = abs(transact.getValue().getQte());
                            price[4] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[4] = "&3Prix &l&e: " + price[4] + " &l&5: " + tot[4] + " " 
                                    + tm.getTroc(loc,4).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,4).get().getItem()).get() + "\n";
                        }
                        break;
                    case 5:
                        if(transact.getValue().getQte()!=0){
                            tot[5] = abs(transact.getValue().getQte());
                            price[5] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[5] = "&3Prix &l&e: " + price[5] + " &l&5: " + tot[5] + " " 
                                    + tm.getTroc(loc,5).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,5).get().getItem()).get() + "\n";
                        }
                        break;
                    case 6:
                        if(transact.getValue().getQte()!=0){
                            tot[6] = abs(transact.getValue().getQte());
                            price[6] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[6] = "&3Prix &l&e: " + price[6] + " &l&5: " + tot[6] + " " 
                                    + tm.getTroc(loc,6).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,6).get().getItem()).get() + "\n";
                        }
                        break;
                    case 7:
                        if(transact.getValue().getQte()!=0){
                            tot[7] = abs(transact.getValue().getQte());
                            price[7] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[7] = "&3Prix &l&e: " + price[7] + " &l&5: " + tot[7] + " " 
                                    + tm.getTroc(loc,7).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,7).get().getItem()).get() + "\n";
                        }
                        break;
                    case 8:
                        if(transact.getValue().getQte()!=0){
                            tot[8] = abs(transact.getValue().getQte());
                            price[8] = (transact.getValue().getQte()*tm.getTroc(loc, transact.getKey()).get().getPrice());
                            label[8] = "&3Prix &l&e: " + price[8] + " &l&5: " + tot[8] + " " 
                                    + tm.getTroc(loc,8).get().getItem().getType().getTranslation().get() 
                                    + ":" + sm.getItemID(tm.getTroc(loc,8).get().getItem()).get() + "\n";
                        }
                        break;
                }
            }
                    
            ticket = ticket + label[0] + label[1] + label[2] + label[3] + label[4] + label[5] + label[6] + label[7] + label[8];
            total = price[0] + price[1] + price[2] + price[3] + price[4] + price[5] + price[6] + price[7] + + price[8];     
            if(!ticket.equalsIgnoreCase("&r&1----Total----\n")){      
                ticket = ticket + "&3---------------\nTotal = &l&e" + total;
                if(total != 0){
                    player.sendMessage(MESSAGE(ticket));
                    if(!ism.addCoin(player.getInventory(),total)){
                        player.getInventory().offer(ism.CoinPurses(player, total).get());
                    }
                }
            }
            TROC.remove(player);
        }
    }
    
    @Listener
    public void onOpenTroc(InteractInventoryEvent.Open event, @First Player player) throws IOException, ObjectMappingException {
        Container b = event.getTargetInventory(); 
        if(b.getName().get().contains("TROC")){
            Map<Integer, ItemTransact> slotItemTransact = new HashMap<>();
            ItemTransact it = new ItemTransact(player,0,0);
            slotItemTransact.put(0, it);
            slotItemTransact.put(1, it);
            slotItemTransact.put(2, it);
            slotItemTransact.put(3, it);
            slotItemTransact.put(4, it);
            slotItemTransact.put(5, it);
            slotItemTransact.put(6, it);
            slotItemTransact.put(7, it);
            slotItemTransact.put(8, it);
            TROC.put(player, slotItemTransact);
        }
    }
    
    @Listener
    public void onPlayerLookTroc(MoveEntityEvent event , @First Player player) {        
        if(ENABLE_TROC_SCOREBOARD()){
            Optional<Location<World>> loc1 = Optional.empty();
            Optional<Location<World>> loc2 = Optional.empty();
            String locTroc;

            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()){ 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 
                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)){ 
                        loc1 = Optional.of(currentHitRay.getLocation()); 
                        break;
                }                    
            } 

            if (loc1.isPresent()){
                if(sm.locDblChest(loc1.get()).isPresent())loc2 = sm.locDblChest(loc1.get());
                if(tm.hasTroc(loc1.get())){
                    locTroc = DeSerialize.location(loc1.get());
                    tm.displayScoreboard(player,locTroc);
                }else{
                    if(loc2.isPresent()){
                        if(tm.hasTroc(loc2.get())){
                            locTroc = DeSerialize.location(loc2.get());
                            tm.displayScoreboard(player,locTroc);
                        }
                    }else{
                        player.setScoreboard(null);
                    }
                }
            }else{
                player.setScoreboard(null);
            }
        }
    }
    
    @Listener
    public void onClick(ClickInventoryEvent event, @First Player player) {
        
        if (event.getTargetInventory().getName().get().contains("TROC")) {
            String[] parts = event.getTargetInventory().getName().get().split(" ");
            String locTroc = parts[1];
            String ownerTroc = parts[2];
            int idGuild = Integer.valueOf(parts[3]);
            final String loc = locTroc;            
            APlayer aplayer = getAPlayer(player.getIdentifier());
            
            List<SlotTransaction> slots = event.getTransactions();
            
            slots.stream().forEach((SlotTransaction slot) -> {
                Integer affectedSlot = slot.getSlot().getProperty(SlotIndex.class, "slotindex").map(SlotIndex::getValue).orElse(-1);
                
                if(affectedSlot < 9){
                    if(ownerTroc.contains("LIBRE") || ownerTroc.equalsIgnoreCase(player.getName()) || aplayer.getLevel() == LEVEL_ADMIN()){
                        if(event.getCursorTransaction().getOriginal().getType().equals(AIR)){
                            Troc troc = new Troc(locTroc,affectedSlot,null,0,0d,tm.getIS().createSnapshot(),"LIBRE","LIBRE",0);
                            tm.save(troc);
                            slot.setCustom(tm.getIS());
                            event.getCursorTransaction().setCustom(ItemStackSnapshot.NONE);
                            if(tm.chestTrocHasEmpty(locTroc))tm.setChestTroc(locTroc, "LIBRE", 0);                     
                        }else{
                            String owner = player.getName();
                            int idGuildPlayer = aplayer.getID_guild();
                            if(ownerTroc.contains("LIBRE") && aplayer.getID_guild() != 0){
                                owner = getGuild(aplayer.getID_guild()).getName();
                            }
                            tm.sendBookSelectTransactType(event.getTargetInventory(), affectedSlot, player,event.getCursorTransaction().getOriginal());
                            event.getCursorTransaction().setCustom(event.getCursorTransaction().getOriginal());
                            tm.setChestTroc(locTroc, owner, idGuildPlayer);
                        }
                    }else{
                        player.sendMessage(MESSAGE("&eEmplacement reserve !"));
                        event.setCancelled(true);
                        return;
                    }
                }   
                
                ItemStackSnapshot isav = slot.getOriginal();
                ItemStackSnapshot isap = slot.getFinal();
                
                if(ownerTroc.equalsIgnoreCase(player.getName())){
                    return;
                }
                if(ownerTroc.equalsIgnoreCase("LIBRE") && idGuild == 0 && affectedSlot < 54){
                    player.sendMessage(MESSAGE("&eCe TROC n'est pas ouvert, si tu souhaites l'ouvrir, "));
                    player.sendMessage(MESSAGE("&eClique avec l'item que tu souahites vendre ou acheter"));
                    player.sendMessage(MESSAGE("&esur une case de la première rangée."));
                    event.setCancelled(true);
                    return;
                }
 
                //Item deja present dans le slot troc
                //-----------------------------------
                if(isav.getType().equals(isap.getType())){
                    
                    //Retrait partiel du troc                 
                    if(affectedSlot < 54 && slot.getOriginal().getQuantity() > slot.getFinal().getQuantity()){
                        int slottroc = tm.getSlot(loc, isav);
                        if(slottroc == -1){
                            event.setCancelled(true);
                            return;
                        }
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();                    
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = tm.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(tm.getTroc(loc, slottroc).get().getTransactType().equals(BUY)){
                            if(total <= -(total - (qteO - qteF))){
                                player.sendMessage(MESSAGE("&eCette item n'est pas a vendre ! uniquement en offre d'achat !"));
                                event.setCancelled(true);
                                return;
                            }else{
                                tm.setBuyQteMax(event.getTargetInventory(),loc, slottroc, qteMax+total);
                            }
                        }    
                        total = total - (qteO - qteF);
                        if(ism.getQteCoin(player.getInventory()) < total){
                            player.sendMessage(MESSAGE("&eTu n'as pas assez de cr\351dit sur toi !"));
                            player.sendMessage(MESSAGE("&eTu dois avoir dans ton inventaire une bourse avec au moins " + total + "e"));
                            player.sendMessage(MESSAGE("&ePour retirer une bourse ou la recharger, rends toi a la banque"));
                            event.setCancelled(true);
                            return;
                        }
                        ItemTransact it = new ItemTransact(player,slottroc,total);
                        TROC.get(player).put(slottroc, it);
                       
                    }
                    
                    //ajout au troc
                    if(affectedSlot < 54 && slot.getOriginal().getQuantity() < slot.getFinal().getQuantity()){
                        int slottroc = tm.getSlot(loc, isav);
                        if(slottroc == -1){
                            event.setCancelled(true);
                            return;
                        }
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = tm.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(tm.getTroc(loc, slottroc).get().getTransactType().equals(SALE)){
                            if(total >= -(total + (qteF - qteO))){
                                player.sendMessage(MESSAGE("&eCette item est a vendre ! il n'est pas en offre d'achat !"));
                                event.setCancelled(true);
                            }
                        } 
                        if(qteMax < (total + (qteF - qteO))){
                            player.sendMessage(MESSAGE("&eLa quantité maximum d'achat a l'offre est de : " + qteMax));
                            event.setCancelled(true);
                        }else{
                            total = total + (qteF - qteO);
                            ItemTransact it = new ItemTransact(player,slottroc,total);
                            tm.setBuyQteMax(event.getTargetInventory(),loc, slottroc, qteMax-total);
                            TROC.get(player).put(slottroc, it);
                        }
                        
                    }
                    
                //deplacement complet sur/d'un slot vide/plein
                //--------------------------------------------
                }else{
                    
                    //Retrait du troc
                    if(affectedSlot < 54 && slot.getFinal().isEmpty()){
                        int slottroc = tm.getSlot(loc, isav);
                        if(slottroc == -1){
                            event.setCancelled(true);
                            return;
                        }
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = tm.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(tm.getTroc(loc, slottroc).get().getTransactType().equals(BUY)){
                            if(total <= -(total - qteO)){
                                player.sendMessage(MESSAGE("&eCette item n'est pas a vendre ! uniquement en offre d'achat !"));
                                event.setCancelled(true);
                                return;
                            }else{
                                tm.setBuyQteMax(event.getTargetInventory(),loc, slottroc, qteMax+total);
                            }
                        }    
                        total = total - qteO;
                        if(ism.getQteCoin(player.getInventory()) < total){
                            player.sendMessage(MESSAGE("&eTu n'as pas assez de cr\351dit sur toi !"));
                            player.sendMessage(MESSAGE("&eTu dois avoir dans ton inventaire une bourse avec au moins " + total + "e"));
                            player.sendMessage(MESSAGE("&ePour retirer une bourse ou la recharger, rends toi a la banque"));
                            event.setCancelled(true);
                            return;
                        }
                        ItemTransact it = new ItemTransact(player,slottroc,total);
                        TROC.get(player).put(slottroc, it);
                    }
                    
                    //ajout au troc
                    if(affectedSlot < 54 && slot.getOriginal().isEmpty()){
                        int slottroc = tm.getSlot(loc, isap);
                        if(slottroc == -1){
                            event.setCancelled(true);
                            return;
                        }
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();  
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = tm.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(tm.getTroc(loc, slottroc).get().getTransactType().equals(SALE)){
                            if(total >= -(total + (total + qteF))){
                                player.sendMessage(MESSAGE("&eCette item est a vendre ! il n'est pas en offre d'achat !"));
                                event.setCancelled(true);
                            }
                        }  
                        if(qteMax < (total + qteF)){
                            player.sendMessage(MESSAGE("&eLa quantité maximum d'achat a l'offre est de : " + qteMax));
                            event.setCancelled(true);
                        }else{
                            total = total + qteF;
                            ItemTransact it = new ItemTransact(player,slottroc,total);
                            tm.setBuyQteMax(event.getTargetInventory(),loc, slottroc, qteMax-total);
                            TROC.get(player).put(slottroc, it);
                        }
                    }
                }
                
            });
        }
    }
    
    @Listener
    public void onClick6(ClickInventoryEvent event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
        if(event instanceof ClickInventoryEvent.Drag){
            //player.sendMessage(MESSAGE("#### drag ####"));
        }
        if(event instanceof ClickInventoryEvent.Pickup){
            //player.sendMessage(MESSAGE("#### pickup ####"));
        }
        if(event instanceof ClickInventoryEvent.Primary){
            //player.sendMessage(MESSAGE("#### prim ####"));
        }
        if(event instanceof ClickInventoryEvent.Drop){
            //player.sendMessage(MESSAGE("#### drop ####"));
            //event.setCancelled(true);
        }
            //player.sendMessage(MESSAGE(event.getClass().getSource().getClass().getCanonicalName()));
        //});
    }
}
