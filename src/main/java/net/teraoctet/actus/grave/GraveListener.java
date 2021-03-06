package net.teraoctet.actus.grave;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configGrave;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.Config.ENABLE_SIGN_GRAVE;
import static net.teraoctet.actus.utils.Config.ENABLE_SKULL_GRAVE;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
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
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Inventory;
import static org.spongepowered.api.item.inventory.InventoryArchetypes.CHEST;
import static org.spongepowered.api.item.inventory.InventoryArchetypes.DOUBLE_CHEST;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
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
        String lastdead = DeSerialize.location(player.getLocation().add(0, 1, 0));
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        aplayer.setLastdeath(lastdead);
        aplayer.setLastposition(lastdead);
        aplayer.update();

        if (player.hasPermission("actus.player.grave") || aplayer.getLevel() == LEVEL_ADMIN()) {
            if(player.getWorld().getDimension().getType().equals(DimensionTypes.NETHER) || player.getWorld().getDimension().getType().equals(DimensionTypes.THE_END)){
                return;
            }
            
            final Location<World> locgrave1;
            final Location<World> locgrave2;
                    
            Optional<Plot> plot = ptm.getPlot(player.getLocation());
            if(plot.isPresent()){
                if(!plot.get().getSpawnGrave()) {
                    return;
                }else{
                    locgrave1 = player.getLocation();
                }
            }else{
                locgrave1 = getLocationGrave(player.getLocation().add(0, -2, 0));
            }
                                 
            //creation de la tombe
            locgrave2 = locgrave1.add(0, 0, 1);
            BlockState bs1 = locgrave1.getBlock();
            BlockState bs2 = locgrave2.add(0, 0, 1).getBlock();
            BlockState chestBlock = BlockState.builder().blockType(BlockTypes.CHEST).build();
            locgrave1.setBlock(chestBlock);
            locgrave2.setBlock(chestBlock);
            
            TileEntity tileEntity1 = locgrave1.getTileEntity().get();
            TileEntity tileEntity2 = locgrave2.getTileEntity().get();
            tileEntity1.offer(Keys.DISPLAY_NAME, MESSAGE("&b[+]").concat(GRAVE(player)));
            tileEntity2.offer(Keys.DISPLAY_NAME, MESSAGE("&b[+]").concat(GRAVE(player)));
            
            Chest chest = (Chest)tileEntity1;
            Optional<Inventory> inv = chest.getDoubleChestInventory();
            
            int i = 1;
            Inventory inventory = inv.get();
            for(Inventory slot : player.getInventory().slots()){
                if (slot.peek().isPresent()) {                    
                    if (inventory.offer(slot.peek().get()).getType() != InventoryTransactionResult.Type.SUCCESS) inventory.offer(slot.peek().get());
                }
                i ++;
            }
            
            //panneau sign de position de la tombe
            Location signgrave = controlBlock(locgrave1);
            if(ENABLE_SIGN_GRAVE()){
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
            }
            
            //skull du joueur de position de la tombe
            Optional<ArmorStand> as;
            String uuidAS = "";
            if(ENABLE_SKULL_GRAVE()){
                ItemStack skull = ItemStack.of(ItemTypes.SKULL, 1);
                skull.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
                skull.offer(Keys.REPRESENTED_PLAYER, player.getProfile());
                
                as = Optional.of((ArmorStand) player.getWorld().createEntity(EntityTypes.ARMOR_STAND, signgrave.getPosition()));
                as.get().setHelmet(skull);
                as.get().offer(Keys.ARMOR_STAND_IS_SMALL, true);
                as.get().offer(Keys.CUSTOM_NAME_VISIBLE, true);
                as.get().offer(Keys.DISPLAY_NAME, MESSAGE("&9++ " + player.getName() + " ++" ));
                as.get().offer(Keys.ARMOR_STAND_HAS_BASE_PLATE, false);
                as.get().offer(Keys.HAS_GRAVITY, false);
                if(ENABLE_SIGN_GRAVE()){
                    as.get().offer(Keys.INVISIBLE, true);
                    as.get().offer(Keys.CUSTOM_NAME_VISIBLE, false);
                }
                as.get().setLocation(as.get().getLocation().setPosition(as.get().getLocation().getPosition().add(0, -0.3, 0)));
                player.getWorld().spawnEntity(as.get());
                uuidAS = as.get().getUniqueId().toString();
            }
                          
            //suppression du stuff joueur
            player.getInventory().clear();
            player.sendMessage(GRAVE_POSITION().concat(MESSAGE(
                    locgrave1.getBlockX() + " " 
                    + locgrave1.getBlockY() + " " 
                    + locgrave1.getBlockZ())));
            
            //sauvegarde des parametre de la tombe/grave
            Grave grave = new Grave(player.getIdentifier(), player.getName(), 
                    player.getLocation(), locgrave1, signgrave, sm.dateToLong(), 
                    bs1, bs2, event.getMessage(), uuidAS);
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
    public void onInteractGraves(InteractInventoryEvent.Close event, @First Player player){
        Container b = event.getTargetInventory(); 
        if(b.getArchetype().equals(CHEST) || b.getArchetype().equals(DOUBLE_CHEST)){
            String displayName = b.getName().get();
            if(displayName.contains("[+]")){
                if(b.parent().totalItems()==0)player.sendMessage(MESSAGE("TOMBE VIDE"));
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
                        try {
                            BlockState bs1 = grave.get().getBlock1();
                            BlockState bs2 = grave.get().getBlock2();
                            grave.get().getLocationBlock1().get().setBlock(bs1);
                            grave.get().getLocationBlock2().get().setBlock(bs2);
                            configGrave.delGrave(grave.get().getIDgrave());
                        } catch (IOException ex) {
                            Logger.getLogger(GraveListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
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