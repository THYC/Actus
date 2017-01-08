package net.teraoctet.actus.commands.world;

import java.io.IOException;
import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static net.teraoctet.actus.utils.MessageManager.WORLD_CREATED;
import static net.teraoctet.actus.utils.MessageManager.WORLD_ALREADY_EXIST;
import static net.teraoctet.actus.utils.MessageManager.WORLD_CREATION_ERROR;
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
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;
import org.spongepowered.api.world.gen.WorldGeneratorModifiers;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandWorldCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if (src instanceof Player && src.hasPermission("actus.admin.world.worldcreate")){
            Player player = null;
            player = (Player)src;
            if(!ctx.getOne("name").isPresent()) {
                src.sendMessage(USAGE("/worldcreate <name> [-d <dimension>] [-g <generator>] [-m <modifier>]  [-s <seed>] [-e <gamemode>] [-y <difficulty>]"));
                src.sendMessage(MESSAGE("&9<dimension>:\n&7 -minecraft:overworld\n -minecraft:nether\n -minecraft:the_end"));
                src.sendMessage(MESSAGE("&9<generator>: &7amplified, debug, default, flat, large_biomes, nether, overworld, the_end  "));
                src.sendMessage(MESSAGE("&9<modifier>:\n&7 -minecraft:forge\n -minecraft:skylands\n -minecraft:void"));
                src.sendMessage(MESSAGE("&9<gamemode>: &7survival, creative, adventure, spectator"));
                src.sendMessage(MESSAGE("&9<Difficulty>:\n&7 -minecraft:easy\n -minecraft:hard\n -minecraft:normal\n minecraft:peaceful"));
                return CommandResult.empty();
            }
            String name = ctx.<String> getOne("name").get();
            if(getGame().getServer().getWorld(name).isPresent()) {src.sendMessage(WORLD_ALREADY_EXIST()); return CommandResult.empty();}
            src.sendMessage(MESSAGE("&cCr\351ation du monde en cours ..."));
            WorldArchetype.Builder builder = WorldArchetype.builder();
            if (ctx.hasAny("dimensionType"))builder.dimension(ctx.<DimensionType> getOne("dimensionType").orElse(DimensionTypes.OVERWORLD));
            if (ctx.hasAny("generatorType"))builder.generator(ctx.<GeneratorType> getOne("generatorType").orElse(GeneratorTypes.DEFAULT));
            if (ctx.hasAny("modifier"))builder.generatorModifiers(ctx.<WorldGeneratorModifier> getOne("modifier").orElse(WorldGeneratorModifiers.VOID));
            if (ctx.hasAny("gamemode"))builder.gameMode(ctx.<GameMode> getOne("gamemode").orElse(GameModes.SURVIVAL));
            if (ctx.hasAny("difficulty"))builder.difficulty(ctx.<Difficulty> getOne("difficulty").orElse(Difficulties.NORMAL));
            if (ctx.hasAny("seed")) {
                String seed = ctx.<String> getOne("seed").get();
                try {
                    Long s = Long.parseLong(seed);
                    builder.seed(s);
                } catch (Exception e) {
                    builder.seed(seed.hashCode());
                }
            }
            WorldArchetype settings = builder
                    .enabled(true)
                    .keepsSpawnLoaded(true)
                    .loadsOnStartup(true)
                    .build(name, name);
            
            try {
                WorldProperties properties  = Sponge.getServer().createWorldProperties(name, settings);
                Sponge.getServer().saveWorldProperties(properties);
                Optional<World> world = Sponge.getGame().getServer().loadWorld(properties);
                
                if(world.isPresent()){
                    AWorld w = new AWorld(
                            name,
                            world.get().getUniqueId(),
                            "Vous \352tes sur " + name,
                            "&c[" + name + "] ",
                            properties.getDifficulty(),
                            properties.getGameMode(),
                            true,
                            true,
                            true,
                            world.get().getSpawnLocation(),
                            0,
                            0,
                            2,
                            8000);
                    addWorld(name, w);
                    WorldManager.save(w);
                    src.sendMessage(WORLD_CREATED(player,name));
                }
            } catch (IOException ex) { 
                src.sendMessage(WORLD_CREATION_ERROR());
            }
            return CommandResult.success();
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
