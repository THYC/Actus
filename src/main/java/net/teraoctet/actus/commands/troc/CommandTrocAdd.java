package net.teraoctet.actus.commands.troc;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.tm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.troc.EnumTransactType;
import net.teraoctet.actus.troc.Troc;
import net.teraoctet.actus.utils.Data;
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
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import static org.spongepowered.api.item.ItemTypes.BARRIER;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

public class CommandTrocAdd implements CommandExecutor {
        
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.troc.add")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());    
            Optional<Location<World>> loc1 = Optional.empty();
            String locTroc;
            String ownerTroc;
            int idGuild;
            Optional<Chest> chest;
            Optional<TileEntity> chestBlock;
            
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()){ 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 
                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)){ 
                    loc1 = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 

            if (loc1.isPresent()){
                chestBlock = loc1.get().getTileEntity();
                if(chestBlock.isPresent()){
                    if(chestBlock.get().get(Keys.DISPLAY_NAME).isPresent()){
                        if(chestBlock.get().get(Keys.DISPLAY_NAME).get().toPlain().contains("TROC")){
                            String[] parts = chestBlock.get().get(Keys.DISPLAY_NAME).get().toPlain().split(" ");
                            locTroc = parts[1];
                            ownerTroc = parts[2];
                            idGuild = Integer.valueOf(parts[3]);
                        }else{
                            player.sendMessage(MESSAGE("&eCe coffre n'est pas un coffre pour le Troc !"));
                            return CommandResult.empty();
                        }
                    }else{
                        player.sendMessage(MESSAGE("&eCe coffre n'est pas un coffre pour le Troc !"));
                        return CommandResult.empty();
                    }
                }else{
                    player.sendMessage(MESSAGE("&eCe coffre n'est pas un coffre pour le Troc !"));
                    return CommandResult.empty();
                }
                                                    
                //ici reste du code
                if(!ownerTroc.equalsIgnoreCase(player.getName()) && !ownerTroc.equalsIgnoreCase("LIBRE") && idGuild == 0){
                    player.sendMessage(MESSAGE("&eOperation impossible, ce troc appartient a : " + getAPlayer(ownerTroc)));
                    return CommandResult.empty();
                }
                if(idGuild != 0 && aplayer.getID_guild() != idGuild){
                    player.sendMessage(MESSAGE("&eOperation impossible, ce troc appartient a la guid : " + Data.getGuild(idGuild)));
                    return CommandResult.empty();
                }
                
                chest = Optional.of((Chest)chestBlock.get());
                                        
                /*Optional<String> name = ctx.<String> getOne("name");
                if(!name.isPresent()){
                    name = Optional.of("-");
                }*/
                    
                Optional<EnumTransactType> type = ctx.<EnumTransactType> getOne("type");
                Optional<Double> price = ctx.<Double> getOne("price");
                Optional<Integer> qte = ctx.<Integer> getOne("qte");
                    
                if(!type.isPresent() || !price.isPresent()){
                        return CommandResult.empty();
                }
                if(!qte.isPresent()){
                    qte = Optional.of(1);
                }
                    
                Troc troc;
                
                int index = 0;
                Optional<Inventory> chestTroc;
                if(chest.isPresent()){
                    if(chest.get().getDoubleChestInventory().isPresent()){
                        chestTroc = chest.get().getDoubleChestInventory();
                    }else{
                        chestTroc = Optional.of(chest.get().getInventory());
                    }
                    if(!chestTroc.isPresent()){
                        player.sendMessage(MESSAGE("&4Creation de l'itemTroc impossible !"));
                        return CommandResult.empty();
                    }
                    for (Inventory inv : chestTroc.get().slots()) {
                        player.sendMessage(MESSAGE("-" + inv.peek().get().getType().getName()));
                        if (inv.peek().get().getType().equals(BARRIER)){
                            Optional<ItemStack> is = tm.setItemTroc(player, type.get(),qte.get() , price.get(), locTroc);
                            if(!is.isPresent()){
                                player.sendMessage(MESSAGE("&4Creation de l'itemTroc impossible !"));
                                return CommandResult.empty();
                            }

                            inv.set(is.get());
                            troc = new Troc(locTroc, index, type.get(), qte.get(), price.get(), player.getItemInHand(HandTypes.MAIN_HAND).get().createSnapshot(),
                                        player.getName(),player.getIdentifier(),aplayer.getID_guild());
                            try {
                                tm.save(troc);
                            } catch (IOException | ObjectMappingException ex) {
                                Logger.getLogger(CommandTrocAdd.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            player.sendMessage(MESSAGE("&eItem troc cr\351\351 avec succes !"));
                            break;
                        }

                        index = index + 1;
                        if(index == 9){
                            player.sendMessage(MESSAGE("&eTroc complet "));
                            break;
                        }
                    }
                }
  
            }else{
                player.sendMessage(MESSAGE("&eAucun coffre dans le chant de vision, d\351place toi et recommence !"));
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
