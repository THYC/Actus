package net.teraoctet.actus.commands;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static net.teraoctet.actus.utils.MessageManager.WORLD_CREATED;
import static net.teraoctet.actus.utils.MessageManager.WORLD_ALREADY_EXIST;
import static net.teraoctet.actus.utils.MessageManager.WORLD_CREATION_ERROR;
import static net.teraoctet.actus.utils.MessageManager.WORLD_PROPERTIES_ERROR;
import net.teraoctet.actus.world.AWorld;
import net.teraoctet.actus.world.WorldManager;
import static net.teraoctet.actus.world.WorldManager.addWorld;
import org.spongepowered.api.Sponge;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import static org.spongepowered.api.entity.living.player.gamemode.GameModes.SURVIVAL;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandWorldCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if (src instanceof Player && src.hasPermission("actus.admin.world.worldcreate")){
            try {
                Player player = null;
                player = (Player)src;
                if(!ctx.getOne("name").isPresent()) {
                    src.sendMessage(USAGE("/worldcreate <name> [-d <dimension>] [-g <generator>] [-m <modifier>]  [-s <seed>] [-gm <gamemode>] [-di <difficulty>]"));
                    src.sendMessage(MESSAGE("&6<dimension> = &7'overworld', 'nether', 'the_end'"));
                    src.sendMessage(MESSAGE("&6<generator> = &7amplified, debug, default, flat, large_biomes, nether, overworld, the_end  "));
                    src.sendMessage(MESSAGE("&6<modifier> = &7forge:skylands, void"));
                    src.sendMessage(MESSAGE("&6<gamemode> = &7survival, creative, adventure, spectator"));
                    src.sendMessage(MESSAGE("&6<Difficulty> = &7easy, hard, normal, peaceful"));
                    return CommandResult.empty();
                }
                
                String name = ctx.<String> getOne("name").get();
                if(getGame().getServer().getWorld(name).isPresent()) {src.sendMessage(WORLD_ALREADY_EXIST()); return CommandResult.success();}
                
                DimensionType dimension = getGame().getRegistry().getType(DimensionType.class, ctx.<String>getOne("dimension").orElse("OVERWORLD")).get();
                GeneratorType generator = getGame().getRegistry().getType(GeneratorType.class, ctx.<String>getOne("generator").orElse("DEFAULT")).get();
                Optional<Long> seed = ctx.getOne("seed");
                GameMode gamemode = getGame().getRegistry().getType(GameMode.class, ctx.<String>getOne("gamemode").orElse("SURVIVAL")).get();
                Difficulty difficulty = getGame().getRegistry().getType(Difficulty.class, ctx.<String>getOne("difficulty").orElse("NORMAL")).get();
  
                WorldArchetype.Builder worldSettingsBuilder = WorldArchetype.builder().enabled(true)
                        .loadsOnStartup(true)
                        .keepsSpawnLoaded(true)
                        .dimension(dimension)
                        .generator(generator)
                        .difficulty(difficulty)
                        .gameMode(gamemode);
                
                if (ctx.hasAny("modifier")) {
                    String modifier = ctx.<String> getOne("modifier").get();
                    Optional<WorldGeneratorModifier> optionalType = Sponge.getRegistry().getType(WorldGeneratorModifier.class, modifier);
                    if(!optionalType.isPresent()){
			src.sendMessage(MESSAGE("Invalid Modifier Type"));
			return CommandResult.empty();
                    }
                    worldSettingsBuilder.generatorModifiers(optionalType.get());
		}
                
                if (seed.isPresent()) worldSettingsBuilder.seed(seed.get());
                
                src.sendMessage(MESSAGE("&cCr\351ation du monde en cours ..."));
                
                WorldArchetype wa = worldSettingsBuilder.build(name.toLowerCase(), name);
                Optional<WorldProperties> worldProperties = Optional.of(Sponge.getGame().getServer().createWorldProperties(name, wa));
                if (worldProperties.isPresent()) {
                    Optional<World> world = Sponge.getGame().getServer().loadWorld(worldProperties.get());

                    if (world.isPresent()) {
                        world.get().getProperties().setWorldBorderDiameter(60);
                        world.get().getProperties().setWorldBorderDamageAmount(5);

                        
                        AWorld w = new AWorld(
                                name, 
                                world.get().getUniqueId().toString(),
                                "Vous \352tes sur " + name,
                                "&c[" + name + "] ", 
                                difficulty, 
                                SURVIVAL, 
                                true, 
                                true,
                                true, 
                                world.get().getSpawnLocation(), 
                                0, 
                                2);
                            addWorld(name, w);
                            WorldManager.save(w);
                            src.sendMessage(WORLD_CREATED(player,name));
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WORLD_CREATION_ERROR());
                    }
                } else {
                    src.sendMessage(WORLD_PROPERTIES_ERROR());
                }
            } catch (IOException ex) {
                Logger.getLogger(CommandWorldCreate.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
