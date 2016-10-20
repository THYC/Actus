package net.teraoctet.actus.world;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.world.WorldManager.getWorld;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType.Block;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.ConstructEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.DIAMOND_AXE;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

public class WorldListener {
    
    private final List<ChangeBlockEvent.Break> firedEvents = Lists.newArrayList();
    
    @Listener
    public void onEntitySpawn(SpawnEntityEvent event) {
    	
	List<Entity> entities = event.getEntities();
	
        for (Entity entity : entities)
        {
            AWorld world = getWorld(entity.getWorld().getName());
            if(world == null) return;
            if(!world.getAnimal() && entity instanceof Animal || entity.getType().equals(EntityTypes.BAT)) { event.setCancelled(true);return;}
            if(!world.getMonster() && entity instanceof Monster) {event.setCancelled(true);return;}
    	}	
    }  
    
    /*@Listener
    public void onBlockUpdate(NotifyNeighborBlockEvent event) {
        BlockSnapshot source;
        if(event.getCause().first(BlockSnapshot.class).isPresent())
            source = event.getCause().first(BlockSnapshot.class).get();
        else
            return;

        if(isValid(source.getLocation().get())) {
            PoweredProperty poweredProperty = source.getLocation().get().getProperty(PoweredProperty.class).orElse(null);
            updateState(source.getLocation().get(), poweredProperty == null ? false : poweredProperty.getValue());
    }*/
    
    
    
    public static void spawnParticles(Location<World> location, double range, boolean sub){
        
        Random random = new Random(); 
 		 
        for(int i = 0; i < 5; i++){ 
            double v1 = 0.0 + (range - 0.0) * random.nextDouble(); 
            double v2 = 0.0 + (range - 0.0) * random.nextDouble(); 
            double v3 = 0.0 + (range - 0.0) * random.nextDouble(); 


            location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                            .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(v3,v1,v2)); 
            location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                            .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(0,v1,0)); 
            if(sub){ 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().sub(v1,v2,v3)); 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().sub(0,v2,0)); 
            }else{ 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(v3,v1,v1)); 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(v2,v3,v2)); 
            } 
        } 
        
    }
    
    @Listener
    public void onTNTexplode(final ExplosionEvent.Post event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (!plot.isPresent()){
            if (event.getCause().first(PrimedTNT.class).isPresent()){
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onExplosionPlot(final ExplosionEvent.Post event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (!plot.isPresent()){
            if (!event.getCause().first(PrimedTNT.class).isPresent()){
                List<Transaction<BlockSnapshot>> bs = event.getTransactions();
                Restore restore = new Restore(bs);
                restore.run();
            }
        }
    }
                
    @Listener 
    public void creeperItemDrop(DropItemEvent.Destruct event){
        if (event.getCause().first(Creeper.class).isPresent()){
            event.getEntities().clear();
            event.setCancelled(true);
        }
    } 
    
    @Listener 
    public void test(DestructEntityEvent event){
        plugin.getLogger().info("DestructEntityEvent");
    } 
    
    @Listener 
    public void saplingDrop(DropItemEvent.Dispense event){ 
        plugin.getLogger().info("aa");
        if(!event.getEntities().isEmpty()){
            List<Entity> entitys = event.getEntities();
            entitys.stream().forEach((ent) -> {
                Optional<ItemStackSnapshot> item = ent.get(Keys.REPRESENTED_ITEM);
                if (item.get().getType().getBlock().isPresent()) {
                    if (item.get().getType().getBlock().get().equals(BlockTypes.SAPLING)) {
                        plugin.getLogger().info("bb");
                        Reforestation reforestation = new Reforestation(ent);
                        reforestation.run();
                    }    
                }
            });
        }
    }
            
    @Listener
    public void treeBreak(ChangeBlockEvent.Break breakEvent) throws Exception {
        if (!firedEvents.contains(breakEvent) && breakEvent.getCause().first(Player.class).isPresent() && 
                !breakEvent.isCancelled() && breakEvent.getTransactions().size() == 1 &&
                TreeDetector.isWood(breakEvent.getTransactions().get(0).getOriginal())) {
            
            Player player = breakEvent.getCause().first(Player.class).get();
            Optional<ItemStack> inHand = player.getItemInHand(HandTypes.MAIN_HAND);
            
            if (inHand.isPresent() && inHand.get().getItem() == DIAMOND_AXE) {
                TreeDetector tree = new TreeDetector(breakEvent.getTransactions().get(0).getOriginal());
                List<Transaction<BlockSnapshot>> transactions = new ArrayList<>(tree.getWoodLocations().size());
                
                tree.getWoodLocations().forEach(blockSnapshot -> {
                    if (!blockSnapshot.equals(breakEvent.getTransactions().get(0).getOriginal())) {
                        BlockState newState = BlockTypes.AIR.getDefaultState();
                        BlockSnapshot newSnapshot = blockSnapshot.withState(newState).withLocation(new Location<>(player.getWorld(), blockSnapshot.getPosition()));
                        Transaction<BlockSnapshot> t = new Transaction<>(blockSnapshot, newSnapshot);
                        transactions.add(t);
                    }
                });
                
                transactions.forEach((Transaction<BlockSnapshot> blockSnapshotTransaction) -> {
                    ChangeBlockEvent.Break event = SpongeEventFactory.createChangeBlockEventBreak(breakEvent.getCause(),
                            player.getWorld(), Lists.newArrayList(blockSnapshotTransaction));
                    firedEvents.add(event);
                    
                    if (!getGame().getEventManager().post(event)) {
                        if (player.getGameModeData().get(Keys.GAME_MODE).get() != GameModes.CREATIVE) {
                            BlockState bs = blockSnapshotTransaction.getOriginal().getState();
                            Entity entity = player.getWorld().createEntity(EntityTypes.ITEM, blockSnapshotTransaction.getOriginal().getPosition());
                            entity.offer(Keys.REPRESENTED_ITEM, bs.getType().getItem().get().getTemplate().copy());
                            player.getWorld().spawnEntity(entity, breakEvent.getCause());
                        }
                        blockSnapshotTransaction.getFinal().restore(true, BlockChangeFlag.ALL);
                    }
                });
                firedEvents.clear();
            }
        }
    }
}
