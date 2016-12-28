package net.teraoctet.actus.world;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Config;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.world.WorldManager.getWorld;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.vehicle.minecart.TNTMinecart;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.BlockSpawnCause;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.Named;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.DIAMOND_AXE;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.explosion.Explosion;

public class WorldListener {
    
    private final List<ChangeBlockEvent.Break> firedEvents = Lists.newArrayList();
    private final List<Vector3i> locDrop = Lists.newArrayList();
    
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
                
    @Listener
    public void onExplode(ExplosionEvent.Detonate event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = plotManager.getPlot(loc);
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
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (!plot.isPresent()){
            if ((event.getCause().first(Creeper.class).isPresent() && Config.CREEPER_DISABLE() == false) ||
                    ((event.getCause().first(PrimedTNT.class).isPresent() || event.getCause().first(TNTMinecart.class).isPresent()) && Config.TNT_DISABLE() == false)){
                List<Transaction<BlockSnapshot>> bs = event.getTransactions();
                Restore restore = new Restore(bs);
                restore.run();
                for(Transaction<BlockSnapshot> b : bs){
                    locDrop.add(b.getFinal().getPosition());
                }
            }
        }
    }
            
    @Listener
    public final void onBlockDrop(final DropItemEvent.Destruct dropItemEvent, @Named(NamedCause.SOURCE) final BlockSpawnCause cause) {
        final BlockSnapshot snapshot = cause.getBlockSnapshot();
        if(locDrop.contains(snapshot.getPosition())){
            locDrop.remove(snapshot.getPosition());
            dropItemEvent.setCancelled(true);
        }
    }
                   
    @Listener
    public void dropSapling(SpawnEntityEvent event) {
        if(!event.getEntities().isEmpty()){
            if(event.getEntities().get(0).getType().equals(EntityTypes.ITEM)){           
                List<Entity> entitys = event.getEntities();
                entitys.stream().forEach((ent) -> {
                    Optional<ItemStackSnapshot> item = ent.get(Keys.REPRESENTED_ITEM);
                    if(item.isPresent()){
                        if (item.get().getType().getBlock().isPresent()) {
                            if (item.get().getType().getBlock().get().equals(BlockTypes.SAPLING)) {
                                Reforestation reforestation = new Reforestation(ent);
                                reforestation.run();
                            }    
                        }
                    }
                });
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
            player.offer(Keys.GAME_MODE, player.getWorld().getProperties().getGameMode());
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
    
    @Listener
    public void blockChange(ChangeBlockEvent.Modify event){
        if(event.getCause().root() instanceof Entity){
            Entity stepper = (Entity)event.getCause().root();
            BlockSnapshot fin = event.getTransactions().get(0).getFinal();
            BlockState bs = fin.getState();
            if(bs.getType() == BlockTypes.STONE_PRESSURE_PLATE || bs.getType() == BlockTypes.WOODEN_PRESSURE_PLATE){
                BlockType under = fin.getLocation().get().sub(0,1,0).getBlock().getType();
                double gg = 6.00;//getLaunchpadPower(under);
                if(gg != -1)
                    stepper.setVelocity(stepper.getVelocity().mul(gg,0,gg*2).add(0,gg/5,0));

            }


        }
    }
}
