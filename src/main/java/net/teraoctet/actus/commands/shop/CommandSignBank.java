package net.teraoctet.actus.commands.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.spongepowered.api.command.source.ConsoleSource;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.USAGE;

public class CommandSignBank implements CommandExecutor {
       
    @Override
    @SuppressWarnings("UnusedAssignment")
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.sign.bank")) { 
            Player player = (Player) src;
            
            if(!ctx.getOne("type").isPresent()){
                player.sendMessage(USAGE("/signbank <depot|retrait>"));
                return CommandResult.empty();  
            }
            String type = ctx.<String> getOne("type").get();
            if(type.equalsIgnoreCase("depot")){
                type = "Depot";
            }else if(type.equalsIgnoreCase("retrait")){
                type = "Retrait";
            }else{
                player.sendMessage(USAGE("/signbank <depot|retrait>"));
                return CommandResult.empty();  
            }
            
            Location location = null;
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()) 
            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.WALL_SIGN) || 
                        player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.STANDING_SIGN)) 
                { 
                    location = currentHitRay.getLocation(); 
                    break;
                }                     
            } 

            if (location == null){
                location = player.getLocation();
                location.setBlockType(STANDING_SIGN);  
            }

            Optional<TileEntity> signBlock = location.getTileEntity();
            TileEntity tileSign = signBlock.get();
            Sign sign=(Sign)tileSign;
            Optional<SignData> opSign = sign.getOrCreate(SignData.class);

            SignData signData = opSign.get();
            List<Text> help = new ArrayList<>();
            help.add(MESSAGE("&l&1[BANK]"));
            help.add(MESSAGE("&o&1" + type));
            help.add(MESSAGE("&l&4Clique droit ici"));
            help.add(MESSAGE("&1-------------"));
            signData.set(Keys.SIGN_LINES,help );
            sign.offer(signData);
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