package net.teraoctet.actus.commands.troc;

import java.util.Optional;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.Actus.tm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
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
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.data.key.Keys;

public class CommandTroc implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.troc")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                    
            Optional<Location<World>> loc1 = Optional.empty();
            Optional<Location<World>> loc2 = Optional.empty();
            String locTroc;
            
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()){ 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 
                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)){ 
                    loc1 = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 

            if (loc1.isPresent()){
                if(sm.locDblChest(loc1.get()).isPresent()){
                    loc2 = sm.locDblChest(loc1.get());
                }
                
                if(tm.hasTroc(loc1.get())){
                    locTroc = DeSerialize.location(loc1.get());
                }else{
                    if(tm.hasTroc(loc2.get())){
                        locTroc = DeSerialize.location(loc2.get());
                    }else{
                        Optional<TileEntity> chestBlock = loc1.get().getTileEntity();
                        TileEntity tileChest = chestBlock.get();
                        if(tileChest.get(Keys.DISPLAY_NAME).isPresent()){
                            if(tileChest.get(Keys.DISPLAY_NAME).get().compareTo(MESSAGE("&9TROC")) == 0){
                                locTroc = DeSerialize.location(loc1.get());
                            }else{
                                player.sendMessage(MESSAGE("&eCe coffre n'est pas un coffre pour le Troc !"));
                                return CommandResult.empty();
                            }
                        }else{
                            player.sendMessage(MESSAGE("&eCe coffre n'est pas un coffre pour le Troc !"));
                            return CommandResult.empty();
                        }
                    }
                }
                
                
            }else{
                player.sendMessage(MESSAGE("&eAucun coffre dans le chant de vision, d/351place toi et recommence !"));
            }
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
