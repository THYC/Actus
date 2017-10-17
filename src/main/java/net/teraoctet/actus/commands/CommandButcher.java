package net.teraoctet.actus.commands;

import java.util.Iterator;
import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.BUTCHER;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static net.teraoctet.actus.utils.MessageManager.WRONG_NAME;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class CommandButcher implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.admin.butcher")) {
            Optional<World> world = Optional.empty();
            int entities = 0;
        
            if(ctx.getOne("worldName").isPresent()){
                String worldName = ctx.<String> getOne("worldName").get();
                world = getGame().getServer().getWorld(worldName);
            } else {
                if (src instanceof Player){
                    Player player = (Player) src;
                    world = Optional.of(player.getLocation().getExtent());
                } else {
                    src.sendMessage(USAGE("butcher <world>"));
                }
            }
            
            if(world.isPresent()){
                entities = world.get().getEntities().size();

                for (Iterator<Entity> it = world.get().getEntities().iterator(); it.hasNext();) {
                    Entity entity = it.next();
                    entity.remove();
                }

                src.sendMessage(BUTCHER(String.valueOf(entities)));
                return CommandResult.success();
            } else {
                src.sendMessage(WRONG_NAME());
                src.sendMessage(USAGE("butcher <world>"));
            }

        } else {
            src.sendMessage(NO_PERMISSIONS());
        }         
        return CommandResult.empty();
    }
}
