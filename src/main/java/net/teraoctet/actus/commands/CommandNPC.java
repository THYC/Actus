package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import net.teraoctet.actus.skin.MineSkin;
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

        if(src instanceof Player && src.hasPermission("actus.admin.npc")) {
            Player player = (Player) src;
                    
            String name = ctx.<String> getOne("name").orElse(player.getName());
            String skin = ctx.<String> getOne("skin").orElse(player.getName());
                       
            MineSkin ms = new MineSkin(skin);
            
            Entity ent = player.getWorld().createEntity(EntityTypes.HUMAN, player.getLocation().getPosition());
            //ent.offer(Keys.AI_ENABLED, true);
            ent.offer(Keys.DISPLAY_NAME, Text.of(name));
            //ent.offer(Keys.IS_SITTING,true);
            
            if(ms.getUUID().isPresent()){
                ent.offer(Keys.SKIN_UNIQUE_ID, ms.getUUID().get());
            }
            player.getLocation().getExtent().spawnEntity(ent);
            ent.setLocationAndRotation(player.getLocation(), player.getRotation());
            ent.setScale(player.getScale());
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
