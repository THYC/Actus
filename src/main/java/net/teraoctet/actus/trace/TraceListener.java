package net.teraoctet.actus.trace;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static net.teraoctet.actus.Actus.tem;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import static org.spongepowered.api.item.ItemTypes.ARROW;
import static org.spongepowered.api.item.ItemTypes.FLINT_AND_STEEL;
import static org.spongepowered.api.item.ItemTypes.LAVA_BUCKET;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TraceListener {
    //static final Map<Player,Location<World>> TMPTRACE = new HashMap<>();
    
    /*@Listener
    public void onBreakBlock(ChangeBlockEvent.Break event, @First Player player) {
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        if(aplayer.getLevel() != LEVEL_ADMIN() && aplayer.getLevel() != 1){
            Transaction<BlockSnapshot> block = event.getTransactions().get(0);
            Optional<Location<World>> optLoc = block.getOriginal().getLocation();
            Location<World> loc = optLoc.get(); 
        
            Trace trace = new Trace(player, loc, "BREAK",block.getOriginal().getState().getId());  
            trace.insert();
        }
    }*/
    
    /*@Listener
    public void onPlaceBlock(ChangeBlockEvent.Place event, @First Player player) {
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        if(aplayer.getLevel() != LEVEL_ADMIN() && aplayer.getLevel() != 1){
            Transaction<BlockSnapshot> block = event.getTransactions().get(0);
            Optional<Location<World>> optLoc = block.getOriginal().getLocation();
            Location<World> loc = optLoc.get(); 
            Trace trace = new Trace(player, loc, "PLACE",block.getFinal().getState().getId());  
            trace.insert();
        }
    }*/
    
    /*@Listener
    public void onBurnBlock(InteractBlockEvent event, @First Player player) {
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        if(aplayer.getLevel() != LEVEL_ADMIN() && aplayer.getLevel() != 1){
            BlockSnapshot b = event.getTargetBlock(); 
            Optional<ItemStack> item = player.getItemInHand(HandTypes.MAIN_HAND);
            Optional<Location<World>> loc = event.getTargetBlock().getLocation();
            if(item.isPresent() && loc.isPresent()){
                if(item.get().getItem().equals(FLINT_AND_STEEL)){
                    Trace trace = new Trace(player, loc.get(), "BURNING",event.getTargetBlock().getState().getId());  
                    trace.insert();
                }
                if(item.get().getItem().equals(LAVA_BUCKET)){
                    Trace trace = new Trace(player, loc.get(), "LAVA",event.getTargetBlock().getState().getId());  
                    trace.insert();
                }
            }
        }
    }*/
    
    /*@Listener
    public void onInteractBlock(InteractBlockEvent.Secondary event, @First Player player) {
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        if(aplayer.getLevel() != LEVEL_ADMIN() && aplayer.getLevel() != 1){
            BlockSnapshot b = event.getTargetBlock(); 
            Optional<ItemStack> item = player.getItemInHand(HandTypes.MAIN_HAND);
            Optional<Location<World>> loc = event.getTargetBlock().getLocation();
            if(item.isPresent() && loc.isPresent()){
                if(item.get().getItem().equals(ARROW)){
                    if(TMPTRACE.containsKey(player) && TMPTRACE.containsValue(loc.get())) return;
                    tem.refresh();
                    String trace = tem.load(loc.get());
                    player.sendMessage(MESSAGE(trace));
                    TMPTRACE.remove(player);
                    TMPTRACE.put(player, loc.get());
                }
            }
        }
    }*/
}
