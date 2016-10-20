package net.teraoctet.actus.commands;

import java.util.Optional;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.function.Consumer;
import net.teraoctet.actus.Actus;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import net.teraoctet.actus.world.Reforestation;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import static org.spongepowered.api.text.channel.MessageChannel.world;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;
import org.spongepowered.api.world.WorldBorder.ChunkPreGenerate;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
 
        Player player = (Player) src;
        World w = player.getWorld();
        
        //Chunk chunk = player.getLocation().getExtent().getChunkAtBlock(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()).get();
        //chunk.restoreSnapshot(0, 0, 0, BlockSnapshot.NONE, true, BlockChangeFlag.ALL, Cause.of(plugin));
       //layer.sendMessage(MESSAGE(w.getDimension().toString()));
        //player.sendMessage(MESSAGE(w.getDimension().toString()));
        
        WorldBorder border = w.getWorldBorder();
        border.setDiameter(30000000);
        
        

        ChunkPreGenerate generator = border.newChunkPreGenerate(w).owner(plugin);
        //generator.logger(plugin.instance().getLog());
        
        //generator.tickInterval(100);
        generator.start();
        
        return CommandResult.success();
    }
    


    private Consumer<CommandSource> CommandBank(Player player) {
	return (CommandSource src) -> {            APlayer gp = getAPlayer(player.getIdentifier());
            player.sendMessage(MESSAGE(String.valueOf(gp.getMoney())));
            src.sendMessage(MESSAGE("PROUuuuT ! pardon .."));
            //pages.sendTo(src);
           
        };
    }

}
