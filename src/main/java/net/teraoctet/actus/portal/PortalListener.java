package net.teraoctet.actus.portal;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import java.util.Optional;
import static net.teraoctet.actus.Actus.plm;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.PROTECT_PORTAL;
import net.teraoctet.actus.world.AWorld;
import net.teraoctet.actus.world.WorldManager;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.action.TextActions;

public class PortalListener {
              
    public PortalListener() {}
    
    @Listener
    public void onPlayerMovePortal(MoveEntityEvent event, @First Player player) {
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Location locTo = event.getToTransform().getLocation();
        Location locFrom = event.getFromTransform().getLocation();
        
        Optional<Portal> optPortal = plm.getPortal(locTo);
        Optional<Portal> optPortalFrom = plm.getPortal(locFrom);
           
        if (optPortal.isPresent()){   
            Portal portal = optPortal.get();
            if(optPortalFrom.isPresent()){
                if(portal.equals(optPortalFrom.get()))return;
                plugin.getLogger().info("OK1");
            }
            if(player.hasPermission("actus.portal." + portal.getName()) || aplayer.getLevel() == LEVEL_ADMIN())
            {
                if(portal.gettoworld().equalsIgnoreCase("DISABLED") && portal.getCMD().equalsIgnoreCase("DISABLED")){
                    player.sendMessage(MESSAGE("&aPoint de spawn du portail non configur\351, aller au point d'apparition souhait\351 ")
                            .concat(MESSAGE("&aet cliquer ici : &e/portal tpfrom " + portal.getName()).toBuilder()
                                    .onClick(TextActions.runCommand("/portal tpfrom " + portal.getName())).build())); 
                    return;
                }
                if(!portal.getCMD().equalsIgnoreCase("DISABLED")){
                    CommandManager cmdService = Sponge.getGame().getCommandManager();
                    cmdService.process(player, portal.getCMD());
                    if(portal.gettoworld().equalsIgnoreCase("DISABLED"))return;
                }
                                
                aplayer.setLastposition(DeSerialize.location(event.getFromTransform().getLocation()));
                aplayer.update();
                
                AWorld aworld = WorldManager.getWorld(portal.gettoworld()); 
                Optional<World> world = getGame().getServer().getWorld(portal.gettoworld());
                Location loc = new Location(world.get(), new Vector3d(portal.gettoX(), portal.gettoY(), portal.gettoZ()));
                player.setLocation(loc);

                player.sendTitle(Title.of(Text.of(TextColors.DARK_GREEN, aworld.getName()),MESSAGE(portal.getMessage(),player))); 
                if (aplayer.getLevel() == LEVEL_ADMIN()){
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&o&7" + portal.getName()));
                }
                
                ParticleEffect effect = ParticleEffect.builder().type(ParticleTypes.PORTAL).build();
                player.playSound(SoundTypes.ENTITY_BLAZE_SHOOT, player.getLocation().getPosition(), 2);
                                
                Vector3d origin = new Vector3d(player.getLocation().getPosition().getX(), player.getLocation().getPosition().getY() + 1, player.getLocation().getPosition().getZ());
                for (double incr = 0; incr < 3.14; incr += 0.1) {
                    player.getLocation().getExtent().spawnParticles(effect, origin.add(Math.cos(incr), 0, Math.sin(incr)), 500);
                    player.getLocation().getExtent().spawnParticles(effect, origin.add(Math.cos(incr), 0, -Math.sin(incr)), 500);
                    player.getLocation().getExtent().spawnParticles(effect, origin.add(-Math.cos(incr), 0, Math.sin(incr)), 500);
                    player.getLocation().getExtent().spawnParticles(effect, origin.add(-Math.cos(incr), 0, -Math.sin(incr)), 500);
                }
                player.offer(Keys.GAME_MODE, player.getWorld().getProperties().getGameMode());
            }
        }
    }
    
    @Listener
    public void onEntityMovePortal(MoveEntityEvent event, @First Entity entity) {
        Location locTo = event.getToTransform().getLocation();
        Location locFrom = event.getFromTransform().getLocation();
        
        Optional<Portal> optPortal = plm.getPortal(locTo);
        Optional<Portal> optPortalFrom = plm.getPortal(locFrom);
        
        if(entity instanceof Player == false){
            if (optPortal.isPresent()){   
                Portal portal = optPortal.get(); 
                if(optPortalFrom.isPresent()){
                    if(portal.equals(optPortalFrom.get()))return;
                    //plugin.getLogger().info("OK2");
                }
                if(!portal.gettoworld().equalsIgnoreCase("DISABLED")){
                    Optional<World> world = getGame().getServer().getWorld(portal.gettoworld());                    
                    Location<World> loc = new Location(world.get(), new Vector3d(portal.gettoX(), portal.gettoY(), portal.gettoZ()));
                    //Vector3i chunkPos = loc.getChunkPosition();                    
                    entity.setLocation(loc);
                    //loc.getExtent().loadChunk(chunkPos, true);
                }
            }
        }
    }
        
    @Listener
    public void onBreakBlock(ChangeBlockEvent.Break event, @First Player player){
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
    
        Optional<Portal> optPortal = plm.getPortal(loc);
        if (optPortal.isPresent() && aplayer.getLevel() != LEVEL_ADMIN()){
            player.sendMessage(ChatTypes.CHAT,PROTECT_PORTAL());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Place event, @First Player player){
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        Optional<Portal> optPortal = plm.getPortal(loc);
        if (optPortal.isPresent() && aplayer.getLevel() != LEVEL_ADMIN()){
            player.sendMessage(ChatTypes.CHAT,PROTECT_PORTAL());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onFluidBlock(ChangeBlockEvent.Modify event, @First Player player){

        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        Optional<Portal> optPortal = plm.getPortal(loc);
        if (optPortal.isPresent() && aplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PROTECT_PORTAL());
            event.setCancelled(true);
        }
    }
            
    @Listener
    public void onExplosion(ExplosionEvent.Pre event) {
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        
        Optional<Portal> optPortal = plm.getPortal(loc);
        if (optPortal.isPresent()){event.setCancelled(true);}
    }
}
