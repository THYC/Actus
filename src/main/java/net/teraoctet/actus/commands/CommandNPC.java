package net.teraoctet.actus.commands;

import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandNPC implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer("Votop");
            
            Entity ent = player.getWorld().createEntity(EntityTypes.HUMAN, player.getLocation().getPosition());
            ent.offer(Keys.AI_ENABLED, true);
            ent.offer(Keys.DISPLAY_NAME, Text.of(player.getName()));
            ent.offer(Keys.IS_SITTING,true);

            ent.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
            player.getLocation().getExtent().spawnEntity(ent);
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
