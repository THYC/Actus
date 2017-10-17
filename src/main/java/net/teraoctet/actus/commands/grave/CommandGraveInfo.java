package net.teraoctet.actus.commands.grave;

import java.util.Optional;
import static net.teraoctet.actus.Actus.CB_GRAVE;
import static net.teraoctet.actus.Actus.configGrave;
import net.teraoctet.actus.grave.Grave;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandGraveInfo implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.grave")) {
            
            Player player = (Player) src;
            Optional<Location> chest = Optional.empty();

            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(15).build(); 
            while (playerBlockRay.hasNext()){ 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 
                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)){ 
                    chest = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 
       
            if(!chest.isPresent()){
                src.sendMessage(MESSAGE("&eAucune tombe trouv\351, deplace toi un peu et recommence"));
                return CommandResult.empty();
            }
                
            
            Optional<Grave> grave;
            String locgrave1 = player.getWorld().getName() + ":" + chest.get().getBlockX() + ":" + chest.get().getBlockY() + ":" + chest.get().getBlockZ();
            String locgrave2 = player.getWorld().getName() + ":" + chest.get().getBlockX() + ":" + chest.get().getBlockY() + ":" + chest.get().add(0, 0, -1).getBlockZ();

            grave = configGrave.load(locgrave1);
            if(!grave.isPresent()){
                grave = configGrave.load(locgrave2);
            }
            if(grave.isPresent()){                    
                CB_GRAVE.callInfoGrave(grave.get().getIDgrave()).accept(src);
                return CommandResult.success();
            }else{
                src.sendMessage(MESSAGE("&eAucune tombe trouve a proximite, deplace toi un peu et recommence"));
            }               
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
