package net.teraoctet.actus.commands;

import java.util.Optional;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.command.spec.CommandExecutor;

public class CommandTPThru implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if (src instanceof Player && src.hasPermission("actus.admin.tpthru")){
            Player player = (Player) src;
            
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(30).build(); 
            Optional<BlockRayHit<World>> finalHitRay = Optional.empty();
            Optional<Location<World>> location = Optional.empty();

            while (playerBlockRay.hasNext()){
		BlockRayHit<World> currentHitRay = playerBlockRay.next();
                                
		if (finalHitRay.isPresent() && !player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR)){
                    location = Optional.of(currentHitRay.getLocation());
                    break;
		}else{
                    finalHitRay = Optional.of(currentHitRay);
		}
            }

            if (finalHitRay.isPresent() && location.isPresent()){
		if (player.setLocationSafely(location.get())){
                    player.sendMessage(Text.of(TextColors.LIGHT_PURPLE, "Whoosh!"));
		}else{
                    player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No free spot ahead of you found."));
		}
            }else if (!finalHitRay.isPresent()){
		player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You are not facing a wall."));
            }else{
		player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No free spot ahead of you found."));
            }
	}else{
            src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /thru!"));
	}
        return CommandResult.success();
    }

}
