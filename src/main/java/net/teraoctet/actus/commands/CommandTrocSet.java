package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
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

public class CommandTrocSet implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.troc")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                    
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
                String chestName = "TROC";
                tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                if(serverManager.locDblChest(optlocation.get()).isPresent()){
                    Location loc = serverManager.locDblChest(optlocation.get()).get();
                    Optional<TileEntity> dblchestBlock = loc.getTileEntity();
                    TileEntity tiledblChest = dblchestBlock.get();
                    tiledblChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                    player.sendMessage(MESSAGE("&eCr\351ation coffre TROC termin\351"));
                }
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
