package net.teraoctet.actus.commands.plot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.player.APlayer;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotSale implements CommandExecutor {
       
    @Override
    @SuppressWarnings("UnusedAssignment")
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.plot.sale")) { 
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        
            if(!ctx.getOne("price").isPresent()){
                player.sendMessage(USAGE("/plot sale <price>"));
                player.sendMessage(USAGE("/plot sale <price> [plotName]"));
                return CommandResult.empty();  
            }

            Optional<Plot> plot = plotManager.getPlot(player.getLocation());

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                if (plotManager.hasPlot(plotName)){  
                    plot = plotManager.getPlot(plotName); 
                } else {
                    player.sendMessage(MESSAGE("&7parcelle &e" + plotName + " &7introuvable"));
                    player.sendMessage(USAGE("/plot sale <price> [plotName]"));
                    return CommandResult.empty();
                }       
            } else {
                if (!plot.isPresent()){
                    player.sendMessage(MESSAGE("&7Tu dois renseigner le nom de la parcelle ou \352tre dessus"));
                    player.sendMessage(USAGE("/plot sale <price> [plotName]"));
                    return CommandResult.empty(); 
                }
            }
            
            if(plot.isPresent()){
                if (!plot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                    player.sendMessage(ALREADY_OWNED_PLOT());
                    return CommandResult.empty();
                }
            }

            Optional<Location> optlocation = Optional.empty();

            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()) 
            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.WALL_SIGN) || 
                        player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.STANDING_SIGN)) 
                { 
                    optlocation = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 

            if (!optlocation.isPresent()){
                optlocation = Optional.of(player.getLocation());
                optlocation.get().setBlockType(STANDING_SIGN);  
            }

            Optional<TileEntity> signBlock = optlocation.get().getTileEntity();
            TileEntity tileSign = signBlock.get();
            Sign sign=(Sign)tileSign;
            Optional<SignData> opSign = sign.getOrCreate(SignData.class);
            Integer price = ctx.<Integer> getOne("price").get();

            SignData signData = opSign.get();
            List<Text> sale = new ArrayList<>();
            sale.add(MESSAGE("&1A VENDRE"));
            sale.add(MESSAGE("&1" + plot.get().getName()));
            sale.add(MESSAGE("&4" + String.valueOf(price)));
            sale.add(MESSAGE("&1Emeraudes"));
            signData.set(Keys.SIGN_LINES,sale );
            sign.offer(signData);

            plot.get().addSale(optlocation.get());
            Data.commit();
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