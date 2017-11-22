package net.teraoctet.actus.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static net.teraoctet.actus.Actus.game;
import static net.teraoctet.actus.Actus.worldManager;
import static net.teraoctet.actus.world.WorldManager.addWorld;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import static org.spongepowered.api.Sponge.getGame;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulty;

@ConfigSerializable
public final class AWorld {

    @Setting String name;
    @Setting UUID uuid;
    @Setting String worldInventory;
    @Setting String message;
    @Setting String prefix;
    @Setting Difficulty difficulty;
    @Setting GameMode gamemode;
    @Setting boolean monsters;
    @Setting boolean animals;
    @Setting boolean pvp;
    @Setting Location<World> spawn;
    //@Setting double borderCenterX;
    //@Setting double borderCenterY;
    //@Setting double borderDamage;
    //@Setting double borderDiameter;
    
    public AWorld() {}
    
    public AWorld(String name, UUID uuid, String message, String prefix, Difficulty difficulty, GameMode gamemode, boolean monsters, boolean animals, boolean pvp, Location<World> spawn/*, 
            double borderCenterX, double borderCenterY, double borderDamage, double borderDiameter*/) {
        this.name = name;
        this.uuid = uuid;
        this.worldInventory = this.name;
        this.message = message;
        this.prefix = prefix;
        this.difficulty = difficulty;
        this.gamemode = gamemode;
        this.monsters = monsters;
        this.animals = animals;
        this.pvp = pvp;
        this.spawn = spawn;
        //this.borderCenterX = borderCenterX;
        //this.borderCenterY = borderCenterY;
        //this.borderDamage = borderDamage;
        //this.borderDiameter = borderDiameter;
    }

    public void update() {
        addWorld(name, this);
        WorldManager.save(this);
        if(!getGame().getServer().getWorld(name).isPresent()) return;
        World world = getGame().getServer().getWorld(name).get();
        world.getProperties().setDifficulty(difficulty);
        world.getProperties().setGameMode(gamemode);
        world.getProperties().setSpawnPosition(spawn.getBlockPosition());
        //world.getWorldBorder().setCenter(borderCenterX,borderCenterY);
        //world.getWorldBorder().setDamageAmount(borderDamage);
        //world.getWorldBorder().setDiameter(borderDiameter);
        world.getProperties().setPVPEnabled(pvp);
        game.getServer().saveWorldProperties(world.getProperties());		
    }
    
    public void setMessage(String message) { this.message = message; update(); }
    public void setWorldInventory(String worldInventory) { this.worldInventory = worldInventory; update(); }
    public void setPefix(String prefix) { this.prefix = prefix; update(); }
    public void setSpawn(Location<World> spawn) { this.spawn = spawn; update(); }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; update(); }
    public void setGamemode(GameMode gamemode) { this.gamemode = gamemode; update(); }
    //public void setBorderDiameter(double border) { this.borderDiameter = border; update(); }
    //public void setBorderDamage(double damage) { this.borderDamage = damage; update(); }
    public void allowMonster(boolean state) { this.monsters = state; update(); }
    public void allowAnimal(boolean state) { this.animals = state; update(); }
    public void allowPVP(boolean state) { this.pvp = state; update(); }
    
    public String getName() { return name; }
    public UUID getUUID() { return uuid; }
    public String getWorldInventory() { return worldInventory; }
    public String getMessage() { return message; }
    public String getPrefix() { return prefix; }
    public Location<World> getSpawn() { return spawn; }
    public Difficulty getDifficulty() { return difficulty; }
    public GameMode getGamemode() { return gamemode; }
    public boolean getMonster() { return monsters; }
    public boolean getAnimal() { return animals; }
    public boolean getPVP() { return pvp; }
    //public double getBorder() { return borderDiameter; }
    //public double getBorderDamage() { return borderDamage; }

    public void toSpawn(Player player) {
            player.setLocation(spawn);
    }
    public List<Player> getPlayers() { 
        List<Player> players = new ArrayList<>();
        getGame().getServer().getOnlinePlayers().stream().filter((player) -> !(!player.getWorld().getName().equalsIgnoreCase(name))).forEach((player) -> {
                players.add(player);
        });
        return players;
    }
}
