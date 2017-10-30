package net.teraoctet.actus.troc;

import java.io.IOException;
import java.util.List;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.text.Text;

public class trocListener {
    
    Inventory inv;
    
    @Listener
    public void onCloseGraves(InteractInventoryEvent.Close event, @First Player player) throws IOException, ObjectMappingException {
        Container b = event.getTargetInventory(); 
        //player.sendMessage(MESSAGE(b.getName().get()));
        if(b.getName().get().equals("TROC")){
            int c = b.totalItems()-inv.totalItems();
            player.sendMessage(MESSAGE("--troc--" + c));
        }
    }
    
    @Listener
    public void onOpenGraves(InteractInventoryEvent.Open event, @First Player player) throws IOException, ObjectMappingException {
        Container b = event.getTargetInventory(); 
        if(b.getName().get().equals("TROC")){
            //Optional<Book> optbook;
            //try {
                //optbook = configBook.load("test");
                //optbook.get().
                //if(optbook.isPresent()){
                    //if(!b.getViewers().iterator().equals(player)){
                    //player.sendBookView(optbook.get().getBookView());
                    //player.openInventory(b);
                    //}
                    
                //}
            //} catch (ObjectMappingException | IOException ex) {Logger.getLogger(CallBackBook.class.getName()).log(Level.SEVERE, null, ex);}         
                
            //CB_BOOK.callHelpBook("test").accept(player);
            
            inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
            .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Quests: Page " + (1 + 1))))
            .build(plugin);
            
            inv = player.getInventory();
            player.sendMessage(MESSAGE("--troc--" + (b.totalItems()-inv.totalItems())));
            //player.sendMessage(MESSAGE("--tmp--" + inv.totalItems()));
            //player.sendMessage(MESSAGE("----" + inv.));
            //player.openInventory(inv);
            
        }
    }
    
    @Listener
    //@Exclude(ClickInventoryEvent.Shift.class)
    public void onClick(ClickInventoryEvent event, @First Player player) {
        
        //player.sendMessage(MESSAGE(event.getTargetInventory().getName().get()));
        if (event.getTargetInventory().getName().get().equals("TROC")) {               
            List<SlotTransaction> slots = event.getTransactions();
            slots.stream().forEach((slot) -> {

                Integer affectedSlot = slot.getSlot().getProperty(SlotIndex.class, "slotindex").map(SlotIndex::getValue).orElse(-1);
                player.sendMessage(MESSAGE("-----------------"));
                player.sendMessage(MESSAGE("avant: " + slot.getOriginal().getQuantity() + " " + slot.getOriginal().getType().getName()));

                
                player.sendMessage(MESSAGE("apres: " + slot.getFinal().getQuantity() + " " + slot.getFinal().getType().getName()));
                
                player.sendMessage(MESSAGE("CLICK : " + affectedSlot));

                player.sendMessage(MESSAGE("-----------------"));

            });
        }
        
    }
    
    @Listener
    public void onClick1(ClickInventoryEvent.Transfer event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
        player.sendMessage(MESSAGE("transfer"));
        //});
    }
    
    @Listener
    public void onClick2(ClickInventoryEvent.Close event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
            player.sendMessage(MESSAGE("close"));
        //});
    }
    
    @Listener
    public void onClick3(ClickInventoryEvent.Double event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
            player.sendMessage(MESSAGE("double"));
        //});
    }
    
    @Listener
    public void onClick4(ClickInventoryEvent.NumberPress event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
            player.sendMessage(MESSAGE("numpress"));
        //});
    }
    
    @Listener
    public void onClick5(ClickInventoryEvent.Pickup event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
            player.sendMessage(MESSAGE("pickup"));
        //});
    }
    
    @Listener
    public void onClick6(ClickInventoryEvent event, @First Player player) {
        //List<SlotTransaction> slots = event.getTransactions();
        //slots.stream().forEach((slot) -> {
        if(event instanceof ClickInventoryEvent.Drag){
            player.sendMessage(MESSAGE("#### drag ####"));
        }
        if(event instanceof ClickInventoryEvent.Pickup){
            player.sendMessage(MESSAGE("#### pickup ####"));
        }
        if(event instanceof ClickInventoryEvent.Primary){
            player.sendMessage(MESSAGE("#### prim ####"));
        }
        if(event instanceof ClickInventoryEvent.Drop){
            player.sendMessage(MESSAGE("#### drop ####"));
            event.setCancelled(true);
        }
            //player.sendMessage(MESSAGE(event.getClass().getSource().getClass().getCanonicalName()));
        //});
    }
}
