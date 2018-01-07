package net.teraoctet.actus.commands.chest;

import java.util.Optional;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.player.PlayerManager.isAPlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
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

public class CommandChestSet implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.chest")) {
            Player player = (Player) src;
            
            Optional<String> target = ctx.<String> getOne("target");
            if(!target.isPresent()){
                player.sendMessage(USAGE("/chestset <player> : Change le Owner du coffre"));
                return CommandResult.empty();
            }
            
            if(!isAPlayer(target.get())){
                player.sendMessage(MESSAGE("&eCe joueur est inconnu, v\351rifie l'ortographe"));
                return CommandResult.empty();
            }
        
            Optional<Location<World>> optlocation = Optional.empty();
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
                String chestName = "&b" + target.get();              
                tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                
                if(sm.locDblChest(optlocation.get()).isPresent()){
                    Location loc = sm.locDblChest(optlocation.get()).get();
                    Optional<TileEntity> dblchestBlock = loc.getTileEntity();
                    TileEntity tiledblChest = dblchestBlock.get();
                    tiledblChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                    player.sendMessage(MESSAGE("&e" + target.get() + " &best maintenant Owner de ce coffre !"));
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
    
}
