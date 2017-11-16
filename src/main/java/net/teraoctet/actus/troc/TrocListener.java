package net.teraoctet.actus.troc;

import java.io.IOException;
import static java.lang.Math.abs;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import static net.teraoctet.actus.Actus.TROC;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.Actus.trocManager;
import static net.teraoctet.actus.troc.EnumTransactType.BUY;
import static net.teraoctet.actus.troc.EnumTransactType.SALE;
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

            String ticket = "&9----Total----\n";
            double total = 0;
            
            int tot[] = {0,0,0,0,0,0,0,0,0,0};
            double price[] = {0,0,0,0,0,0,0,0,0};
            String label[] = {"","","","","","","","",""};                  
            
            for(Entry<Integer, ItemTransact> transact : TROC.get(player).entrySet()) {                
                switch(transact.getKey()){
                    case 0:
                        if(transact.getValue().getQte()!=0){
                            tot[0] = abs(transact.getValue().getQte());
                            price[0] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[0] = "&9Prix &e: " + price[0] + " &c: " + tot[0] + " " 
                                    + trocManager.getTroc(loc,0).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,0).get().getItem()).get() + "\n";
                        }
                        break;
                    case 1:
                        if(transact.getValue().getQte()!=0){
                            tot[1] = abs(transact.getValue().getQte());
                            price[1] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[1] = "&9Prix &e: " + price[1] + " &c: " + tot[1] + " " 
                                    + trocManager.getTroc(loc,1).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,1).get().getItem()).get() + "\n";
                        }
                        break;
                    case 2:
                        if(transact.getValue().getQte()!=0){
                            tot[2] = abs(transact.getValue().getQte());
                            price[2] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[2] = "&9Prix &e: " + price[2] + " &9: " + tot[2] + " " 
                                    + trocManager.getTroc(loc,2).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,2).get().getItem()).get() + "\n";
                        }
                        break;
                    case 3:
                        if(transact.getValue().getQte()!=0){
                            tot[3] = abs(transact.getValue().getQte());
                            price[3] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[3] = "&9Prix &e: " + price[3] + " &9: " + tot[3] + " " 
                                    + trocManager.getTroc(loc,3).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,3).get().getItem()).get() + "\n";
                        }
                        break;
                    case 4:
                        if(transact.getValue().getQte()!=0){
                            tot[4] = abs(transact.getValue().getQte());
                            price[4] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[4] = "&9Prix &e: " + price[4] + " &9: " + tot[4] + " " 
                                    + trocManager.getTroc(loc,4).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,4).get().getItem()).get() + "\n";
                        }
                        break;
                    case 5:
                        if(transact.getValue().getQte()!=0){
                            tot[5] = abs(transact.getValue().getQte());
                            price[5] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[5] = "&9Prix &e: " + price[5] + " &9: " + tot[5] + " " 
                                    + trocManager.getTroc(loc,5).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,5).get().getItem()).get() + "\n";
                        }
                        break;
                    case 6:
                        if(transact.getValue().getQte()!=0){
                            tot[6] = abs(transact.getValue().getQte());
                            price[6] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[6] = "&9Prix &e: " + price[6] + " &9: " + tot[6] + " " 
                                    + trocManager.getTroc(loc,6).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,6).get().getItem()).get() + "\n";
                        }
                        break;
                    case 7:
                        if(transact.getValue().getQte()!=0){
                            tot[7] = abs(transact.getValue().getQte());
                            price[7] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[7] = "&9Prix &e: " + price[7] + " &9: " + tot[7] + " " 
                                    + trocManager.getTroc(loc,7).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,7).get().getItem()).get() + "\n";
                        }
                        break;
                    case 8:
                        if(transact.getValue().getQte()!=0){
                            tot[8] = abs(transact.getValue().getQte());
                            price[8] = abs(transact.getValue().getQte()*trocManager.getTroc(loc, transact.getKey()).get().getPrice());
                            label[8] = "&9Prix &e: " + price[8] + " &9: " + tot[8] + " " 
                                    + trocManager.getTroc(loc,8).get().getItem().getType().getTranslation().get() 
                                    + ":" + serverManager.getItemID(trocManager.getTroc(loc,8).get().getItem()).get() + "\n";
                        }
                        break;
                }
            }
                    
            ticket = ticket + label[0] + label[1] + label[2] + label[3] + label[4] + label[5] + label[6] + label[7] + label[8];
            total = price[0] + price[1] + price[2] + price[3] + price[4] + price[5] + price[6] + price[7] + + price[8];     
            if(!ticket.equalsIgnoreCase("&9----Total----\n")){      
                ticket = ticket + "\n" + "---- \nTotal = " + total;
                player.sendMessage(MESSAGE(ticket));
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
            if(serverManager.locDblChest(loc1.get()).isPresent())loc2 = serverManager.locDblChest(loc1.get());
            if(trocManager.hasTroc(loc1.get())){
                locTroc = DeSerialize.location(loc1.get());
                trocManager.displayScoreboard(player,locTroc);
            }else{
                if(loc2.isPresent()){
                    if(trocManager.hasTroc(loc2.get())){
                        locTroc = DeSerialize.location(loc2.get());
                        trocManager.displayScoreboard(player,locTroc);
                    }
                }else{
                    player.setScoreboard(null);
                }
            }
        }else{
            player.setScoreboard(null);
        }
    }
    
    @Listener
    public void onClick(ClickInventoryEvent event, @First Player player) {
        
        if (event.getTargetInventory().getName().get().contains("TROC")) {
            String[] parts = event.getTargetInventory().getName().get().split(" ");
            String locTroc = parts[1];
            final String loc = locTroc;
            
            List<SlotTransaction> slots = event.getTransactions();
            
            slots.stream().forEach((SlotTransaction slot) -> {
                Integer affectedSlot = slot.getSlot().getProperty(SlotIndex.class, "slotindex").map(SlotIndex::getValue).orElse(-1);
                
                if(affectedSlot < 9){
                    player.sendMessage(MESSAGE("&eEmplacement reserve !"));
                    event.setCancelled(true);
                    return;
                }   
                
                ItemStackSnapshot isav = slot.getOriginal();
                ItemStackSnapshot isap = slot.getFinal();
 
                //Item deja present dans le slot troc
                //-----------------------------------
                if(isav.getType().equals(isap.getType())){
                    
                    //Retrait partiel du troc                 
                    if(affectedSlot < 54 && slot.getOriginal().getQuantity() > slot.getFinal().getQuantity()){
                        int slottroc = trocManager.getSlot(loc, isav);
                        if(slottroc == -1)event.setCancelled(true);
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();                    
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = trocManager.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(trocManager.getTroc(loc, slottroc).get().getTransactType().equals(BUY)){
                            if(total <= -(total - (qteO - qteF))){
                                player.sendMessage(MESSAGE("&eCette item n'est pas a vendre ! uniquement en offre d'achat !"));
                                event.setCancelled(true);
                            }else{
                                trocManager.setBuyQteMax(loc, slottroc, qteMax+total);
                            }
                        }    
                        total = total - (qteO - qteF);
                        ItemTransact it = new ItemTransact(player,slottroc,total);
                        TROC.get(player).put(slottroc, it);
                       
                    }
                    
                    //ajout au troc
                    if(affectedSlot < 54 && slot.getOriginal().getQuantity() < slot.getFinal().getQuantity()){
                        int slottroc = trocManager.getSlot(loc, isav);
                        if(slottroc == -1)event.setCancelled(true);
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = trocManager.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(trocManager.getTroc(loc, slottroc).get().getTransactType().equals(SALE)){
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
                            trocManager.setBuyQteMax(loc, slottroc, qteMax-total);
                            TROC.get(player).put(slottroc, it);
                        }
                        
                    }
                    
                //deplacement complet sur/d'un slot vide/plein
                //--------------------------------------------
                }else{
                    
                    //Retrait du troc
                    if(affectedSlot < 54 && slot.getFinal().isEmpty()){
                        int slottroc = trocManager.getSlot(loc, isav);
                        if(slottroc == -1)event.setCancelled(true);
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = trocManager.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(trocManager.getTroc(loc, slottroc).get().getTransactType().equals(BUY)){
                            if(total <= -(total - qteO)){
                                player.sendMessage(MESSAGE("&eCette item n'est pas a vendre ! uniquement en offre d'achat !"));
                                event.setCancelled(true);
                            }else{
                                trocManager.setBuyQteMax(loc, slottroc, qteMax+total);
                            }
                        }    
                        total = total - qteO;
                        ItemTransact it = new ItemTransact(player,slottroc,total);
                        TROC.get(player).put(slottroc, it);
                    }
                    
                    //ajout au troc
                    if(affectedSlot < 54 && slot.getOriginal().isEmpty()){
                        int slottroc = trocManager.getSlot(loc, isap);
                        if(slottroc == -1)event.setCancelled(true);
                        int qteO = slot.getOriginal().getQuantity();
                        int qteF = slot.getFinal().getQuantity();                       
                        int total = TROC.get(player).get(slottroc).getQte();
                        int qteMax = trocManager.getTroc(loc, slottroc).get().getQteMax();
                        
                        if(trocManager.getTroc(loc, slottroc).get().getTransactType().equals(SALE)){
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
                            trocManager.setBuyQteMax(loc, slottroc, qteMax-total);
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
