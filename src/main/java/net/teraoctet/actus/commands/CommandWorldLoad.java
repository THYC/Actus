package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandWorldLoad implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {

        if(src instanceof Player && src.hasPermission("actus.")) {

	
            WorldProperties properties = ctx.<WorldProperties> getOne("world").get();

            if (Sponge.getServer().getWorld(properties.getUniqueId()).isPresent()) {
		throw new CommandException(Text.of(TextColors.RED, properties.getWorldName(), " is already loaded"), false);
            }

            //WorldData worldData = new WorldData(properties.getWorldName());

            //if (!worldData.exists()) {
		//throw new CommandException(Text.of(TextColors.RED, properties.getWorldName(), " does not exist"), false);
            //}

		//SpongeData spongeData = new SpongeData(properties.getWorldName());

		/*if (!spongeData.exists()) {
			src.sendMessage(Text.of(TextColors.RED, "Foriegn world detected"));
			src.sendMessage(Text.builder().color(TextColors.YELLOW).onHover(TextActions.showText(Text.of("Click command for more information "))).onClick(TextActions.runCommand("/pjw:world import")).append(Text.of(" /world import")).build());
			return CommandResult.success();
		}*/
		
		src.sendMessage(Text.of(TextColors.YELLOW, "Preparing spawn area. This may take a minute."));

		Sponge.getScheduler().createTaskBuilder().name("PJW" + properties.getWorldName()).delayTicks(20).execute(new Runnable() {

			@Override
			public void run() {
				Optional<World> load = Sponge.getServer().loadWorld(properties);

				if (!load.isPresent()) {
					src.sendMessage(Text.of(TextColors.RED, "Could not load ", properties.getWorldName()));
					return;
				}

				//if (CMDCreate.worlds.contains(properties.getWorldName())) {
					//Utils.createPlatform(load.get().getSpawnLocation().getRelative(Direction.DOWN));
					//CMDCreate.worlds.remove(properties.getWorldName());
				//}

				//src.sendMessage(Text.of(TextColors.DARK_GREEN, properties.getWorldName(), " loaded successfully"));
			}

		}).submit(plugin);

            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
