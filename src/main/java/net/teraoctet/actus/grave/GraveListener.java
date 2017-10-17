package net.teraoctet.actus.grave;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configGrave;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.MessageManager.GRAVE;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.GRAVE_BREAK;
import static net.teraoctet.actus.utils.MessageManager.GRAVE_POSITION;
import static net.teraoctet.actus.utils.MessageManager.GRAVE_RIP1;
import static net.teraoctet.actus.utils.MessageManager.GRAVE_RIP2;
import static net.teraoctet.actus.utils.MessageManager.GRAVE_RIP3;
import static net.teraoctet.actus.utils.MessageManager.GRAVE_RIP4;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.*;
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
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import static org.spongepowered.api.item.inventory.InventoryArchetypes.CHEST;
import static org.spongepowered.api.item.inventory.InventoryArchetypes.DOUBLE_CHEST;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.item.inventory.type.TileEntityInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.DimensionTypes;
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

        if (player.hasPermission("actus.player.grave") || aplayer.getLevel() == 10) {
            if(player.getWorld().getDimension().getType().equals(DimensionTypes.NETHER) || player.getWorld().getDimension().getType().equals(DimensionTypes.THE_END)){
                return;
            }
            
            final Location<World> locgrave;
                    
            Optional<Plot> plot = plotManager.getPlot(player.getLocation());
            if(plot.isPresent()){
                if(plot.get().getSpawnGrave() == 0) {
                    return;
                }else{
                    locgrave = player.getLocation();
                }
            }else{
                locgrave = getLocationGrave(player.getLocation().add(0, -2, 0));
            }
                                    
            //creation de la tombe
            BlockState chestBlock = BlockState.builder().blockType(BlockTypes.CHEST).build();
            locgrave.setBlock(chestBlock);
            locgrave.add(0, 0, 1).setBlock(chestBlock);
            TileEntity chest = locgrave.getTileEntity().get();
            TileEntity chest2 = locgrave.add(0, 0, 1).getTileEntity().get();
            chest.offer(Keys.DISPLAY_NAME, MESSAGE("&b[+]").concat(GRAVE(player)));
            chest2.offer(Keys.DISPLAY_NAME, MESSAGE("&b[+]").concat(GRAVE(player)));
            final TileEntityInventory inventory = (TileEntityInventory) chest;
            player.getInventory().slots().forEach((Inventory slot) -> {
                if (slot.peek().isPresent()) {                    
                    if (inventory.offer(slot.peek().get()).getType() != InventoryTransactionResult.Type.SUCCESS) inventory.offer(slot.peek().get());
                }
            });
                        
            Location signgrave = controlBlock(locgrave);
            signgrave.setBlockType(STANDING_SIGN);  
            Optional<TileEntity> signBlock = signgrave.getTileEntity();
            TileEntity tileSign = signBlock.get();
            Sign sign=(Sign)tileSign;
            Optional<SignData> opSign = sign.getOrCreate(SignData.class);

            SignData signData = opSign.get();
            List<Text> rip = new ArrayList<>();
            rip.add(GRAVE_RIP1(player));
            rip.add(GRAVE_RIP2(player));
            rip.add(GRAVE_RIP3(player));
            rip.add(GRAVE_RIP4(player));
            signData.set(Keys.SIGN_LINES,rip );
            sign.offer(signData);
            
            player.getInventory().clear();
            player.sendMessage(GRAVE_POSITION().concat(MESSAGE(
                    locgrave.getBlockX() + " " 
                    + locgrave.getBlockY() + " " 
                    + locgrave.getBlockZ())));
            
            //sauvegarde des parametre de la tombe/grave
            Grave grave = new Grave(player.getIdentifier(), player.getName(), 
                    player.getLocation(), locgrave, signgrave, serverManager.dateToLong(), 
                    locgrave.getBlock(), locgrave.add(0, 0, 1).getBlock(), event.getMessage());
            try {
                configGrave.save(grave);
            } catch (IOException | ObjectMappingException ex) {
                Logger.getLogger(GraveListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    /**
     * retourne l'adresse location de l'emplacement ou doit être le panneau/sign Grave
     * @param location
     * @return 
     */
    public Location controlBlock(Location location){                
        if (location.getBlockType() != AIR){ 
            location = location.add(0,+1,0);
            if (signAllowed(location)){ return location;}
            else { location = location.add(+1,-1,0);
                if (signAllowed(location)) { return location;}
                else { location = location.add(-1,0,+1);
                    if (signAllowed(location)) { return location;}
                    else { location = location.add(-1,0,-1);
                        if (signAllowed(location)) { return location;}
                        else { location = location.add(+1,0,-1);
                            if (signAllowed(location)) { return location;}
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
    
    /**
     * Retourne True si la location est autorisé pour une tombe
     * @param block Parametre Location
     * @return 
     */
    public boolean graveAllowed(Location block){
        return block.getBlockType().equals(DIRT) 
                || block.getBlockType().equals(STONE)
                || block.getBlockType().equals(GRAVEL)
                || block.getBlockType().equals(SAND);
    }
    
    /**
     * Retourne True si la location est vide pour mettre un panneau/sign
     * @param block Parametre Location
     * @return 
     */
    public boolean signAllowed(Location block){
        return block.getBlockType().equals(AIR);
    }
    
    public Location<World> getLocationGrave(Location<World> location){
        if (!graveAllowed(location) || !graveAllowed(location.add(0, 0, 1))){ 
            if(location.getBlockY() < 5){
                return location;
            }
            location = getLocationGrave(location.add(0,-1,0));
        }
        return location;
    }
            
    @Listener
    public void onInteractGraves(InteractInventoryEvent.Close event, @First Player player) throws IOException, ObjectMappingException {
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
                if(loc.isPresent()){
                    if(b.totalItems() > player.getInventory().totalItems()){return;}

                    //la tombe est vide on restaure les block precedants
                    Optional<Grave> grave;
                    String locgrave1 = loc.get().getExtent().getName() + ":" + loc.get().getBlockX() + ":" + loc.get().getBlockY() + ":" + loc.get().getBlockZ();
                    String locgrave2 = loc.get().getExtent().getName() + ":" + loc.get().getBlockX() + ":" + loc.get().getBlockY() + ":" + loc.get().add(0, 0, -1).getBlockZ();

                    grave = configGrave.load(locgrave1);
                    if(!grave.isPresent()){
                        grave = configGrave.load(locgrave2);
                    }

                    if(grave.isPresent()){    
                        BlockState bs1 = grave.get().getBlock1();
                        BlockState bs2 = grave.get().getBlock2();
                        grave.get().getLocationBlock1().get().setBlock(bs1);
                        grave.get().getLocationBlock2().get().setBlock(bs2);
                        configGrave.delGrave(grave.get().getIDgrave());
                    } 

                    //effet sur disparition de la tombe
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
    }
    
    @Listener
    public void onBreakGrave(ChangeBlockEvent.Break event, @First Player player) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> loc = block.getOriginal().getLocation();
        
        if(loc.isPresent()){
            Optional<Grave> grave;
            String locgrave1 = loc.get().getExtent().getName() + ":" + loc.get().getBlockX() + ":" + loc.get().getBlockY() + ":" + loc.get().getBlockZ();
            String locgrave2 = loc.get().getExtent().getName() + ":" + loc.get().getBlockX() + ":" + loc.get().getBlockY() + ":" + loc.get().add(0, 0, -1).getBlockZ();
                
            grave = configGrave.load(locgrave1);
            if(!grave.isPresent()){
                grave = configGrave.load(locgrave2);
            }
            if(grave.isPresent()){
                ParticleEffect effect = ParticleEffect.builder().type(ParticleTypes.EXPLOSION).build();
                player.playSound(SoundTypes.ENTITY_BLAZE_SHOOT, player.getLocation().getPosition(), 6);
                Vector3d origin = new Vector3d(player.getLocation().getPosition().getX(), player.getLocation().getPosition().getY() + 1, player.getLocation().getPosition().getZ());
                for (double incr = 0; incr < 3.14; incr += 0.1) {
                    loc.get().getExtent().spawnParticles(effect, origin.add(Math.cos(incr), 0, Math.sin(incr)), 100);
                    loc.get().getExtent().spawnParticles(effect, origin.add(Math.cos(incr), 0, -Math.sin(incr)), 100);
                    loc.get().getExtent().spawnParticles(effect, origin.add(-Math.cos(incr), 0, Math.sin(incr)), 100);
                    loc.get().getExtent().spawnParticles(effect, origin.add(-Math.cos(incr), 0, -Math.sin(incr)), 100);
                }
                player.sendMessage(GRAVE_BREAK(player));
                event.setCancelled(true);
            } 
        }       
    }
}