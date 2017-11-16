package net.teraoctet.actus.commands.chest;

import java.util.Optional;;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandChestInfo implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.chest")) {
            Player player = (Player) src;
                    
            Optional<Location> optlocation = Optional.empty();
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()) 
            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)) 
                { 
                    optlocation = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 

            if (optlocation.isPresent()){
                
                Optional<TileEntity> chestBlock = optlocation.get().getTileEntity();
                TileEntity tileChest = chestBlock.get();
                if(tileChest.get(Keys.DISPLAY_NAME).isPresent()){
                    String chestName = tileChest.get(Keys.DISPLAY_NAME).get().toPlain();
                    String players[] = chestName.split(" ");
                    
                    player.sendMessage(MESSAGE("&aOwner : &b" + players[0]));
                    
                    if(players.length > 1){        
                        player.sendMessage(MESSAGE("&aAllowed : "));
                        for(String s : players){
                            player.sendMessage(MESSAGE("&b" + s));
                        }                            
                    }
                    
                }
            }
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
    
    private Optional<Location> locDblChest(Location location) {
	if(location.add(1, 0, 0).getBlockPosition().equals(BlockTypes.CHEST))return Optional.of(location.add(1, 0, 0));
        if(location.add(-1, 0, 0).getBlockPosition().equals(BlockTypes.CHEST))return Optional.of(location.add(-1, 0, 0));
        if(location.add(0, 0, 1).getBlockPosition().equals(BlockTypes.CHEST))return Optional.of(location.add(0, 0, 1));
        if(location.add(0, 0, -1).getBlockPosition().equals(BlockTypes.CHEST))return Optional.of(location.add(0, 0, -1));
        return Optional.empty();
    }
}
