package net.teraoctet.actus.world;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.configInv;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.inventory.AInventory;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Config;
import static net.teraoctet.actus.utils.Config.AUTOFOREST;
import net.teraoctet.actus.utils.DeSerialize;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.vehicle.minecart.TNTMinecart;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.entity.spawn.SpawnType;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.DIAMOND_AXE;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.explosion.Explosion;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;

public class WorldListener {
    
    private final List<ChangeBlockEvent.Break> firedEvents = Lists.newArrayList();
    private final List<Vector3i> locDrop = Lists.newArrayList();
    
    @Listener
    public void onEntitySpawn(SpawnEntityEvent event, @First org.spongepowered.api.event.cause.entity.spawn.SpawnType entitySpawnType){
	if(!event.getEntities().isEmpty() && !(entitySpawnType.equals(SpawnTypes.PLACEMENT)) && !(entitySpawnType.equals(SpawnTypes.DROPPED_ITEM)) && !(entitySpawnType.equals(SpawnTypes.CHUNK_LOAD))) {
            List<Entity> entities = event.getEntities();
        
            for (Entity entity : entities) {
                AWorld world = WorldManager.getWorld(entity.getWorld().getName());
                Optional<Plot> plot = ptm.getPlot(entity.getLocation());

                if(world == null) return;
                if(!world.getAnimal() && entity instanceof Animal || entity.getType().equals(EntityTypes.BAT)) { event.setCancelled(true);return;}
                if(!world.getMonster() && entity instanceof Monster) {event.setCancelled(true);return;}

                if(plot.isPresent()){
                    if(plot.get().getNoMob() && entity instanceof Monster) {event.setCancelled(true);return;}
                    if(plot.get().getNoAnimal() && entity instanceof Animal) {event.setCancelled(true);return;}
                }

            }
        }
    }  
                
    @Listener
    public void onExplode(ExplosionEvent.Detonate event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = ptm.getPlot(loc);
        if (!plot.isPresent()){
            if (event.getCause().first(PrimedTNT.class).isPresent() || event.getCause().first(TNTMinecart.class).isPresent()){
                event.setCancelled(Config.TNT_DISABLE());
            }
        }
        if (event.getCause().first(Creeper.class).isPresent()){
            event.setCancelled(Config.CREEPER_DISABLE());
        }
    }
    
    @Listener
    public void onExplosion(final ExplosionEvent.Post event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getExplosion().getWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = ptm.getPlot(loc);
        if (!plot.isPresent()){
            if ((event.getCause().first(Creeper.class).isPresent() && Config.CREEPER_DISABLE() == false) ||
                    ((event.getCause().first(PrimedTNT.class).isPresent() || event.getCause().first(TNTMinecart.class).isPresent()) && Config.TNT_DISABLE() == false)){
                List<Transaction<BlockSnapshot>> bs = event.getTransactions();
                Restore restore = new Restore(bs);
                restore.run();
                bs.stream().forEach((b) -> {
                    locDrop.add(b.getFinal().getPosition());
                });
            }
        }
    }
            
    @Listener
    public final void onBlockDrop(final DropItemEvent.Destruct dropItemEvent) {
        if(!dropItemEvent.getEntities().isEmpty()){
            if(dropItemEvent.getEntities().get(0).getType().equals(EntityTypes.ITEM)){
                if(locDrop.contains(dropItemEvent.getEntities().get(0).getLocation().getBlockPosition())){
                    locDrop.remove(dropItemEvent.getEntities().get(0).getLocation().getBlockPosition());
                    dropItemEvent.setCancelled(true);
                }
            }
        }
    }
                   
    @Listener
    public void dropSapling(SpawnEntityEvent event) {
        if(AUTOFOREST()){
            if(!event.getEntities().isEmpty()){
                if(event.getEntities().get(0).getType().equals(EntityTypes.ITEM)){           
                    List<Entity> entitys = event.getEntities();
                    entitys.stream().forEach((ent) -> {
                        Optional<ItemStackSnapshot> item = ent.get(Keys.REPRESENTED_ITEM);
                        if(item.isPresent()){
                            if (item.get().getType().getBlock().isPresent()) {
                                if (item.get().getType().getBlock().get().equals(BlockTypes.SAPLING)) {
                                    if(!event.getCause().first(Player.class).isPresent()){
                                        Reforestation reforestation = new Reforestation(ent);
                                        reforestation.run();
                                    }
                                }    
                            }
                        }
                    });
                }
            }
        }
    }
    
    @Listener
    public void onTeleport(MoveEntityEvent.Teleport event) {
        if(event.getTargetEntity() instanceof Player){
            Player player = (Player) event.getTargetEntity();
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            aplayer.setLastposition(DeSerialize.location(event.getFromTransform().getLocation()));
            aplayer.update();
            AInventory inv = new AInventory(player, event.getFromTransform().getExtent().getName());
            configInv.save(inv);
            player.offer(Keys.GAME_MODE, player.getWorld().getProperties().getGameMode());
            inv = configInv.load(player, event.getToTransform().getExtent().getName()).get();
            inv.set();
        }
    }
    
    /*@Listener
    public void treeBreak(ChangeBlockEvent.Break breakEvent, @First Player player) throws Exception {
        if (!firedEvents.contains(breakEvent) && 
            !breakEvent.isCancelled() && breakEvent.getTransactions().size() == 1 &&
            TreeDetector.isWood(breakEvent.getTransactions().get(0).getOriginal())) {
            
            Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
            
            if (is.isPresent() && is.get().getItem() == DIAMOND_AXE) {
                TreeDetector tree = new TreeDetector(breakEvent.getTransactions().get(0).getOriginal());
                List<Transaction<BlockSnapshot>> transactions = new ArrayList<>(tree.getWoodLocations().size());
                player.getWorld().playSound(SoundTypes.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, player.getLocation().getPosition(), 2); 
                tree.getWoodLocations().forEach(blockSnapshot -> {
                    if (!blockSnapshot.equals(breakEvent.getTransactions().get(0).getOriginal())) {
                        BlockState newState = BlockTypes.AIR.getDefaultState();
                        BlockSnapshot newSnapshot = blockSnapshot.withState(newState).withLocation(new Location<>(player.getWorld(), blockSnapshot.getPosition()));
                        Transaction<BlockSnapshot> t = new Transaction<>(blockSnapshot, newSnapshot);
                        transactions.add(t);
                    }
                });
                
                transactions.forEach((Transaction<BlockSnapshot> blockSnapshotTransaction) -> {
                    ChangeBlockEvent.Break event = SpongeEventFactory.createChangeBlockEventBreak(breakEvent.getCause(),Lists.newArrayList(blockSnapshotTransaction));
                    firedEvents.add(event);
                    
                    if (!getGame().getEventManager().post(event)) {
                        
                        BlockState bs = blockSnapshotTransaction.getOriginal().getState();
                        ItemStack item = ItemStack.builder().itemType(bs.getType().getDefaultState().getType().getItem().get()).add(Keys.TREE_TYPE, bs.get(Keys.TREE_TYPE).get()).build();
                        Entity entity = player.getWorld().createEntity(EntityTypes.ITEM, blockSnapshotTransaction.getOriginal().getPosition());
                        entity.offer(Keys.REPRESENTED_ITEM, item.createSnapshot());
                        player.getWorld().spawnEntity(entity);
                    }
                    blockSnapshotTransaction.getFinal().getLocation().get().removeBlock();
                });
                firedEvents.clear();
            }
        }
    }*/
    
    @Listener
    public void blockChange(ChangeBlockEvent.Modify event){
        if(event.getCause().root() instanceof Entity){
            Entity stepper = (Entity)event.getCause().root();
            BlockSnapshot fin = event.getTransactions().get(0).getFinal();
            BlockState bs = fin.getState();
            if(bs.getType() == BlockTypes.STONE_PRESSURE_PLATE || bs.getType() == BlockTypes.WOODEN_PRESSURE_PLATE){
                BlockType powerBlock = fin.getLocation().get().sub(0,1,0).getBlock().getType();
                double gg = getPower(powerBlock);
                if(gg != -1)
                    stepper.setVelocity(stepper.getVelocity().mul(gg,0,gg*2).add(0,gg/5,0));

            }


        }
    }
    
    private double getPower(BlockType powerBlock){
        if(powerBlock.equals(BlockTypes.DIAMOND_BLOCK)) return 10.00;
        if(powerBlock.equals(BlockTypes.LAPIS_BLOCK)) return 15.00;
        if(powerBlock.equals(BlockTypes.GOLD_BLOCK)) return 5.00;
        return 0.00;
    }
}
