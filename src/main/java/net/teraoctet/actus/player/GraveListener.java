package net.teraoctet.actus.player;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.GRAVE;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.LAVA;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Container;
import static org.spongepowered.api.item.inventory.InventoryArchetypes.CHEST;
import static org.spongepowered.api.item.inventory.InventoryArchetypes.DOUBLE_CHEST;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.type.TileEntityInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class GraveListener {
    
    public GraveListener() {}
                            
    @Listener
    public void onDeath(DestructEntityEvent.Death event, @Getter("getTargetEntity") Player player) {
        String lastdead = DeSerialize.location(player.getLocation());
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        aplayer.setLastdeath(lastdead);
        aplayer.setLastposition(lastdead);
        aplayer.update();
        
        if (player.hasPermission("actus.grave") || aplayer.getLevel() == 10) {
            final Location<World> grave = getLocationGrave(player.getLocation()).add(0, -1, 0);
            BlockState chestBlock = BlockState.builder().blockType(BlockTypes.CHEST).build();
            grave.setBlock(chestBlock, Cause.of(NamedCause.source(plugin)));
            grave.add(0, 0, 1).setBlock(chestBlock, Cause.of(NamedCause.source(plugin)));
            TileEntity chest = grave.getTileEntity().get();
            TileEntity chest2 = grave.add(0, 0, 1).getTileEntity().get();
            chest.offer(Keys.DISPLAY_NAME, MESSAGE("&b[+]").concat(GRAVE(player)));
            chest2.offer(Keys.DISPLAY_NAME, MESSAGE("&b[+]").concat(GRAVE(player)));

            player.getInventory().slots().forEach(slot -> {
                if (slot.peek().isPresent()) {
                    TileEntityInventory inventory = (TileEntityInventory) chest;
                    if (inventory.offer(slot.peek().get()).getType() != InventoryTransactionResult.Type.SUCCESS) {
                        inventory = (TileEntityInventory) grave.getTileEntity().get();
                        inventory.offer(slot.peek().get());
                    }
                }
            });
            
            Location signgrave = controlBlock(grave);
            signgrave.setBlockType(STANDING_SIGN,Cause.of(NamedCause.source(plugin)));  
            Optional<TileEntity> signBlock = signgrave.getTileEntity();
            TileEntity tileSign = signBlock.get();
            Sign sign=(Sign)tileSign;
            Optional<SignData> opSign = sign.getOrCreate(SignData.class);

            SignData signData = opSign.get();
            List<Text> rip = new ArrayList<>();
            rip.add(MESSAGE("&5+++++++++++++"));
            rip.add(MESSAGE("&o&5 Repose En Paix"));
            rip.add(MESSAGE("&5&l" + player.getName()));
            rip.add(MESSAGE("&5+++++++++++++"));
            signData.set(Keys.SIGN_LINES,rip );
            sign.offer(signData);
            
            player.getInventory().clear();
            player.sendMessage(MESSAGE("&eTon Stuff est situ\351 dans ta tombe a la postion : " 
                    + grave.getBlockX() + " " 
                    + grave.getBlockY() + " " 
                    + grave.getBlockZ()));
        }
    } 
           
    public Location controlBlock(Location location){                
        if (location.getBlockType() != AIR){ 
            location = location.add(0,+1,0);
            if (location.getBlockType() == AIR){ return location;}
            else { location = location.add(+1,-1,0);
                if (location.getBlockType() == AIR) { return location;}
                else { location = location.add(-1,0,+1);
                    if (location.getBlockType() == AIR) { return location;}
                    else { location = location.add(-1,0,-1);
                        if (location.getBlockType() == AIR) { return location;}
                        else { location = location.add(+1,0,-1);
                            if (location.getBlockType() == AIR) { return location;}
                            else { location = location.add(0,+1,+1);
                                location = controlBlock(location);
                                return location;
                            }
                        }
                    }
                }
            }
        }
        return location;
    }
    
    public Location<World> getLocationGrave(Location<World> location){
        if (location.getBlockType().equals(AIR)){ 
            location = getLocationGrave(location.add(0,-1,0));
            return location;
        }
        if(location.getBlockType().equals(LAVA)) {
            return location;      
        }
        return location;
    }
            
    @Listener
    public void onInteractGraves(InteractInventoryEvent.Close event, @First Player player) {
        Container b = event.getTargetInventory(); 
        if(b.getArchetype().equals(CHEST) || b.getArchetype().equals(DOUBLE_CHEST)){
            String displayName = b.getName().get();
            if(displayName.contains("[+]")){
                Optional<Location<World>> loc = Optional.empty();
                BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
                while (playerBlockRay.hasNext()){ 
                    BlockRayHit<World> currentHitRay = playerBlockRay.next(); 
                    if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)){ 
                        loc = Optional.of(currentHitRay.getLocation()); 
                        break;
                    }                     
                } 
                if(b.totalItems() > player.getInventory().totalItems()){return;}
                loc.get().getExtent().setBlockType(loc.get().getBlockPosition(), AIR, Cause.of(NamedCause.source(plugin)));  
                Optional<Location> locChest = serverManager.locDblChest(loc.get());
                if(locChest.isPresent()){ locChest.get().getExtent().setBlockType(locChest.get().getBlockPosition(), AIR, Cause.of(NamedCause.source(plugin)));}

                ParticleEffect effect = ParticleEffect.builder().type(ParticleTypes.EXPLOSION).build();
                player.playSound(SoundTypes.ENTITY_BLAZE_SHOOT, player.getLocation().getPosition(), 6);
                Vector3d origin = new Vector3d(player.getLocation().getPosition().getX(), player.getLocation().getPosition().getY() + 1, player.getLocation().getPosition().getZ());
                for (double incr = 0; incr < 3.14; incr += 0.1) {
                    loc.get().getExtent().spawnParticles(effect, origin.add(Math.cos(incr), 0, Math.sin(incr)), 100);
                    loc.get().getExtent().spawnParticles(effect, origin.add(Math.cos(incr), 0, -Math.sin(incr)), 100);
                    loc.get().getExtent().spawnParticles(effect, origin.add(-Math.cos(incr), 0, Math.sin(incr)), 100);
                    loc.get().getExtent().spawnParticles(effect, origin.add(-Math.cos(incr), 0, -Math.sin(incr)), 100);
                }
            }
        }
    }
    
    @Listener
    public void onBreakGrave(ChangeBlockEvent.Break event, @First Player player) {
        Transaction<BlockSnapshot> b = event.getTransactions().get(0);
        if(b.getOriginal().get(Keys.DISPLAY_NAME).isPresent()){
            Optional<Text> displayName = b.getOriginal().get(Keys.DISPLAY_NAME);
  
            if(displayName.get().toPlain().contains("[+]")){     
                event.getTransactions().get(0)
                    .setCustom(BlockSnapshot.builder().from(b.getOriginal().getLocation().get()).blockState(BlockState.builder().blockType(BlockTypes.DIRT).build()).build());
                
                Optional<Location> locChest = serverManager.locDblChest(b.getOriginal().getLocation().get());
                if(locChest.isPresent()){ locChest.get().getExtent().setBlockType(locChest.get().getBlockPosition(), BlockTypes.DIRT, Cause.of(NamedCause.source(plugin)));}
                player.playSound(SoundTypes.ENTITY_BLAZE_SHOOT, player.getLocation().getPosition(), 6);
            }
        }
 
    }
}