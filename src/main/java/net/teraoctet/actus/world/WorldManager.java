package net.teraoctet.actus.world;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.plugin;

import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.world.World;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.WorldBorder;

public class WorldManager {
    
    private static final TypeToken<AWorld> TOKEN_CONFIG = TypeToken.of(AWorld.class);
    private static final HashMap<String, AWorld> aworlds = new HashMap<>();
    private static final File file = new File("config/actus/worlds.conf");
    private static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    private static ConfigurationNode worlds = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void addWorld(String name, AWorld world) { if(!aworlds.containsKey(name)) aworlds.put(name, world); }
    public static void removeWorld(String name) { if(aworlds.containsKey(name)) aworlds.remove(name); }
    public static AWorld getWorld(String name) { return aworlds.containsKey(name) ? aworlds.get(name) : null; }
    public static AWorld getWorldUUID( String uuid) { return aworlds.containsKey(uuid) ? aworlds.get(uuid) : null; }
    public static HashMap<String, AWorld> getWorlds() { return aworlds; }
    
    public WorldManager(){}
     
    public static void load(){
        for(World world : getGame().getServer().getWorlds()){

            world.getProperties().setGameMode(getGame().getRegistry().getType(GameMode.class,worlds.getNode("worlds", world.getName(), "gamemode").getString()).get());
            world.getProperties().setPVPEnabled(worlds.getNode("worlds", world.getName(), "pvp").getBoolean());
            world.getWorldBorder().setCenter(worlds.getNode("worlds", world.getName(), "spawn.x").getDouble(), worlds.getNode("worlds", world.getName(), "spawn.z").getDouble());
            world.getWorldBorder().setDiameter(worlds.getNode("worlds", world.getName(), "border").getDouble());
            world.getWorldBorder().setDamageAmount(worlds.getNode("worlds", world.getName(), "border-damage").getDouble());
            WorldBorder border = world.getWorldBorder();
            WorldBorder.ChunkPreGenerate generator = border.newChunkPreGenerate(world).owner(plugin);
            generator.start();
        }
    }
        
    public static void init() {
        try {
            if (!file.exists())file.createNewFile();     
            worlds = manager.load();

            for(World world : getGame().getServer().getWorlds()){ 
                if(worlds.getNode("worlds",world.getName()).isVirtual()){
                    AWorld aworld = new AWorld(
                            world.getName(), 
                            world.getUniqueId(),
                            world.getName(), 
                            "&7[" + world.getName() + "] ", 
                            world.getDifficulty(), 
                            world.getProperties().getGameMode(), 
                            true, 
                            true, 
                            true, 
                            world.getSpawnLocation(),
                            world.getWorldBorder().getCenter().getX(),
                            world.getWorldBorder().getCenter().getY(),
                            world.getWorldBorder().getDamageAmount(),
                            world.getWorldBorder().getDiameter());
                    
                    worlds.getNode("worlds",world.getName()).setValue(TOKEN_CONFIG, aworld);
                    manager.save(worlds);
                }              
            }
            
        } catch (IOException | ObjectMappingException ex) {Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);}
                            
        if(worlds.getNode("worlds").hasMapChildren()) {
            for(Entry<Object, ? extends ConfigurationNode> worldnode : worlds.getNode("worlds").getChildrenMap().entrySet()){ 
                String worldName = worldnode.getKey().toString(); 
                if(getGame().getServer().getWorld(worldName).isPresent()){
                    try {
                        AWorld aworld = new AWorld();
                        aworld = worlds.getNode("worlds",worldName).getValue(TOKEN_CONFIG);
                        addWorld(worldName,aworld);
                    } catch (ObjectMappingException ex) {}
                }
            }
        }
    }
    
    public void loadChunk(Player p){
        Optional<Chunk> chunk = p.getWorld().getChunk(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
        chunk.get().loadChunk(true);
    }
		
    public static void save(AWorld aworld) {	
        try { 
            worlds.getNode("worlds", aworld.name).setValue(TOKEN_CONFIG, aworld);
            manager.save(worlds);
        } catch (ObjectMappingException | IOException ex) {Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    public static void spawnParticles(Location<World> location, double range, boolean sub){
        Random random = new Random();	 
        for(int i = 0; i < 5; i++){ 
            double v1 = 0.0 + (range - 0.0) * random.nextDouble(); 
            double v2 = 0.0 + (range - 0.0) * random.nextDouble(); 
            double v3 = 0.0 + (range - 0.0) * random.nextDouble(); 
            
            ParticleEffect effect = ParticleEffect.builder().type(ParticleTypes.PORTAL).build();
            location.getExtent().spawnParticles(effect, location.getPosition().add(v3,v1,v2)); 
            location.getExtent().spawnParticles(effect, location.getPosition().add(0,v1,0)); 
            if(sub){ 
                    location.getExtent().spawnParticles(effect, location.getPosition().sub(v1,v2,v3)); 
                    location.getExtent().spawnParticles(effect, location.getPosition().sub(0,v2,0)); 
            }else{ 
                    location.getExtent().spawnParticles(effect, location.getPosition().add(v3,v1,v1)); 
                    location.getExtent().spawnParticles(effect, location.getPosition().add(v2,v3,v2)); 
            } 
        } 
        
    }
    
    public List<String> getListAWorld(){
        List<String>aworldList = new ArrayList();
        if(worlds.getNode("worlds").hasMapChildren()) {
            worlds.getNode("worlds").getChildrenMap().entrySet().stream().map((worldnode) -> worldnode.getKey().toString()).forEach((worldName) -> { 
                aworldList.add(worldName);
            });
        }
        return aworldList;
    }
}
