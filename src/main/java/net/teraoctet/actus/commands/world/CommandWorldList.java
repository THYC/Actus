package net.teraoctet.actus.commands.world;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.worldManager;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.World;
    
public class CommandWorldList implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
	
        if (src.hasPermission("actus.world.worldList") && src instanceof Player){ 
            List<Text> texts = new ArrayList<>();
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder();
            builder.title(MESSAGE("&eWORLDS"));
            
            worldManager.getListAWorld().stream().forEach((worldName) -> {
                Optional<World> optWorld = Sponge.getServer().getWorld(worldName);
                if(optWorld.isPresent()) {
                    World world = optWorld.get();
                    Builder textBuilder = Text.builder()
                            .append(MESSAGE("&9" + world.getName() + ": &7" + Lists.newArrayList(world.getLoadedChunks()).size() + world.getEntities().size() + " Entities"))
                            .onHover(TextActions.showText(MESSAGE("&eClick pour rejoindre ce monde")))
                            .onClick(TextActions.runCommand("/world tp " + world.getName()));
                    texts.add(textBuilder.build());
                } else {
                    Builder textBuilder = Text.builder()
                            .append(MESSAGE("&9" + worldName + ":  &4Unloaded"))
                            .onHover(TextActions.showText(MESSAGE("&eClick to load world")))
                            .onClick(TextActions.runCommand("/world load " + worldName));
                    texts.add(textBuilder.build());
                }
            });
            builder.contents(texts);
            builder.padding(MESSAGE("&9-"))
                   .sendTo(src);

            return CommandResult.success();
                
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
