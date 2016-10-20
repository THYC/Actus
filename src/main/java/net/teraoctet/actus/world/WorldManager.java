package net.teraoctet.actus.world;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.plugin;

import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.difficulty.Difficulty;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.WorldBorder;

public class WorldManager {
    private static final HashMap<String, AWorld> aworlds = new HashMap<>();
    public static File file = new File("config/actus/worlds.conf");
    public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode worlds = manager.createEmptyNode(ConfigurationOptions.defaults());
    public static void addWorld(String name, AWorld world) { if(!aworlds.containsKey(name)) aworlds.put(name, world); }
    public static void removeWorld(String name) { if(aworlds.containsKey(name)) aworlds.remove(name); }
    public static AWorld getWorld(String name) { return aworlds.containsKey(name) ? aworlds.get(name) : null; }
    public static AWorld getWorldUUID( String uuid) { return aworlds.containsKey(uuid) ? aworlds.get(uuid) : null; }
    public static HashMap<String, AWorld> getWorlds() { return aworlds; }
    
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
	//try {
            if (!file.exists()) {
                try {
                    file.createNewFile();                 
                    getGame().getServer().getWorlds().stream().map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "uuid" }).setValue(world.getUniqueId().toString());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "message" }).setValue("&6" + world.getName());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "prefix" }).setValue("&6[" + world.getName() + "] ");
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "difficulty" }).setValue(world.getProperties().getDifficulty().getName());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "gamemode" }).setValue(world.getProperties().getGameMode().getName());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "monsters" }).setValue(true);
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "animals" }).setValue(true);
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "pvp" }).setValue(world.getProperties().isPVPEnabled());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "spawn", "x" }).setValue(world.getSpawnLocation().getX());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "spawn", "y" }).setValue(world.getSpawnLocation().getY());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "spawn", "z" }).setValue(world.getSpawnLocation().getZ());
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "spawn", "yaw" }).setValue(0);
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "spawn", "pitch" }).setValue(0);
                        return world;
                    }).map((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "border" }).setValue(world.getProperties().getWorldBorderDiameter());
                        return world;
                    }).forEach((world) -> {
                        worlds.getNode(new Object[] { "worlds", world.getName(), "border-damage" }).setValue(world.getProperties().getWorldBorderDamageAmount());
                    });
                    manager.save(worlds);
                } catch (IOException ex) {
                    Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        try {
            worlds = manager.load();
        } catch (IOException ex) {
            Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        if(worlds.getNode("worlds").hasMapChildren()) {
            for(Entry<Object, ? extends ConfigurationNode> e : worlds.getNode("worlds").getChildrenMap().entrySet()){ 
                String worldName = e.getKey().toString(); 
                if(!getGame().getServer().getWorld(worldName).isPresent()) continue;
                World original = getGame().getServer().getWorld(worldName).get();
                String uuid = worlds.getNode("worlds", worldName, "uuid").getString();
                String message = worlds.getNode("worlds", worldName, "message").getString();
                String prefix = worlds.getNode("worlds", worldName, "prefix").getString();
                String nodeDifficulty = worlds.getNode("worlds", worldName, "difficulty").getString().toUpperCase();
                Difficulty difficulty = Difficulties.EASY;

                switch (nodeDifficulty.toUpperCase()){
                    case "PEACEFUL": difficulty = Difficulties.PEACEFUL;break;
                    case "EASY" : difficulty = Difficulties.EASY;break;
                    case "NORMAL" : difficulty = Difficulties.NORMAL;break;   
                    case "HARD" : difficulty = Difficulties.HARD;break;  
                }

                String nodeGameMode = worlds.getNode("worlds", worldName, "gamemode").getString().toUpperCase();
                GameMode gameMode = GameModes.SURVIVAL;

                switch (nodeGameMode){
                    case "SURVIVAL": gameMode = GameModes.SURVIVAL;break;
                    case "CREATIVE" : gameMode = GameModes.CREATIVE;break;
                    case "ADVENTURE" : gameMode = GameModes.ADVENTURE;break;
                    case "SPECTATOR" : gameMode = GameModes.SPECTATOR;break;
                }

                boolean monsters = worlds.getNode("worlds", worldName, "monsters").getBoolean();
                boolean animals = worlds.getNode("worlds", worldName, "animals").getBoolean();
                boolean pvp = worlds.getNode("worlds", worldName, "pvp").getBoolean();
                double x = worlds.getNode("worlds", worldName, "spawn", "x").getDouble();
                double y = worlds.getNode("worlds", worldName, "spawn","y").getDouble();
                double z = worlds.getNode("worlds", worldName, "spawn","z").getDouble();
                Location<World> spawn = new Location<>(original, x, y, z);
                double border = worlds.getNode("worlds", worldName, "border").getDouble();
                double borderdamage = worlds.getNode("worlds", worldName, "border-damage").getDouble();
                AWorld gworld = new AWorld(worldName, uuid, message, prefix, difficulty, gameMode, monsters, animals, pvp, spawn, border, borderdamage);
                addWorld(worldName, gworld);
            }
        }
    }
    
    public void loadChunk(Player p){
        Optional<Chunk> chunk = p.getWorld().getChunk(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
        chunk.get().loadChunk(true);
    }
		
    public static void save(AWorld world) {
        worlds.getNode("worlds", world.getName(), "message").setValue(world.getMessage());
        worlds.getNode("worlds", world.getName(), "prefix").setValue(world.getPrefix());
        worlds.getNode("worlds", world.getName(), "difficulty").setValue(world.getDifficulty().getName());
        worlds.getNode("worlds", world.getName(), "gamemode").setValue(world.getGamemode().getName());
        worlds.getNode("worlds", world.getName(), "monsters").setValue(world.getMonster());
        worlds.getNode("worlds", world.getName(), "animals").setValue(world.getAnimal());
        worlds.getNode("worlds", world.getName(), "pvp").setValue(world.getPVP());
        worlds.getNode("worlds", world.getName(), "spawn", "x").setValue(world.getSpawn().getX());
        worlds.getNode("worlds", world.getName(), "spawn", "y").setValue(world.getSpawn().getY());
        worlds.getNode("worlds", world.getName(), "spawn", "z").setValue(world.getSpawn().getZ());
        worlds.getNode("worlds", world.getName(), "spawn", "yaw").setValue(0);
        worlds.getNode("worlds", world.getName(), "spawn", "pitch").setValue(0);
        worlds.getNode("worlds", world.getName(), "border").setValue(world.getBorder());
        worlds.getNode("worlds", world.getName(), "border-damage").setValue(world.getBorderDamage());
		
        try { manager.save(worlds); worlds = manager.load(); } catch (IOException e) {}
    }  
}
