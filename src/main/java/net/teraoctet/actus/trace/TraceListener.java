package net.teraoctet.actus.trace;

import java.util.Optional;
import static net.teraoctet.actus.Actus.traceManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.ARROW;
import static org.spongepowered.api.item.ItemTypes.FLINT_AND_STEEL;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TraceListener {
    @Listener
    public void onBreakBlock(ChangeBlockEvent.Break event, @First Player player) {
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location<World> loc = optLoc.get();              
        if(aplayer.getLevel() != 10){
            Trace trace = new Trace(player, loc, "BREAK",block.getOriginal().getState().getId());  
            trace.insert();
        }
    }
    
    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Place event, @First Player player) {
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location<World> loc = optLoc.get();              
        if(aplayer.getLevel() != 10){
            Trace trace = new Trace(player, loc, "PLACE",block.getFinal().getState().getId());  
            trace.insert();
        }
    }
    
    @Listener
    public void onBurnBlock(InteractBlockEvent event, @First Player player) {
        BlockSnapshot b = event.getTargetBlock(); 
        Optional<ItemStack> item = player.getItemInHand(HandTypes.MAIN_HAND);
        if(item.isPresent()){
            if(item.get().getItem().equals(FLINT_AND_STEEL)){
                Trace trace = new Trace(player, event.getTargetBlock().getLocation().get(), "BURNING",event.getTargetBlock().getState().getId());  
                trace.insert();
            }
            if(item.get().getItem().equals(ItemTypes.LAVA_BUCKET)){
                Trace trace = new Trace(player, event.getTargetBlock().getLocation().get(), "LAVA",event.getTargetBlock().getState().getId());  
                trace.insert();
            }
        }
    }
    
    @Listener
    public void onInteractBlock(InteractBlockEvent.Secondary event, @First Player player) {
        BlockSnapshot b = event.getTargetBlock(); 
        Optional<ItemStack> item = player.getItemInHand(HandTypes.MAIN_HAND);
        if(item.isPresent()){
            if(item.get().getItem().equals(ARROW)){
                String trace = traceManager.load(b.getLocation().get());
                player.sendMessage(MESSAGE(trace));
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onEntityInteract(InteractEntityEvent.Secondary event, @First Player player){    
        Entity entity = event.getTargetEntity();
        Optional<ItemStack> item = player.getItemInHand(HandTypes.MAIN_HAND);
        if(item.isPresent()){
            if(item.get().getItem().equals(ARROW)){
                String trace = traceManager.load(entity.getLocation());
                player.sendMessage(MESSAGE(trace));
            }
        }
    }
}
