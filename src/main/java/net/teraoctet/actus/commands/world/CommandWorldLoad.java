package net.teraoctet.actus.commands.world;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import net.teraoctet.actus.world.WorldManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.WorldArchetypes;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandWorldLoad implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {

        if(src instanceof Player && src.hasPermission("actus.")) {
            Optional<String> folderName = ctx.<String> getOne("folderName");
            
            if(folderName.isPresent()){
                final WorldProperties properties;
                try {
                    properties = Sponge.getServer().createWorldProperties(folderName.get(), WorldArchetypes.OVERWORLD);
                    Sponge.getServer().loadWorld(properties);
                    WorldManager.init();
                    src.sendMessage(MESSAGE("&e" + folderName + " charg\351 avec succes"));
                } catch (IOException ex) {Logger.getLogger(CommandWorldLoad.class.getName()).log(Level.SEVERE, null, ex);}
                return CommandResult.success();
            }
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
