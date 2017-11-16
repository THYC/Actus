package net.teraoctet.actus.commands.troc;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.Actus.trocManager;
import net.teraoctet.actus.troc.Troc;
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
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class CommandTrocSet implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.troc.set")) {
            Player player = (Player) src;
                                
            Optional<Location<World>> locChest1 = Optional.empty();
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()) 
            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST)) 
                { 
                    locChest1 = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 

            if (locChest1.isPresent()){
                String locTroc = DeSerialize.location(locChest1.get());
                Location<World> locChest2;
                Optional<TileEntity> chestBlock = locChest1.get().getTileEntity();
                TileEntity tileChest = chestBlock.get();
                String chestName = "&9TROC " + locTroc;
                tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                
                if(serverManager.locDblChest(locChest1.get()).isPresent()){
                    locChest2 = serverManager.locDblChest(locChest1.get()).get();
                    Optional<TileEntity> dblchestBlock = locChest2.getTileEntity();
                    TileEntity tiledblChest = dblchestBlock.get();
                    tiledblChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                }
                
                Troc troc;
                Optional<Chest> chest = Optional.of((Chest)chestBlock.get());
                int index = 0;
                Optional<Inventory> chestTroc;
                
                if(chest.isPresent()){
                    if(chest.get().getDoubleChestInventory().isPresent()){
                        chestTroc = chest.get().getDoubleChestInventory();
                    }else{
                        chestTroc = Optional.of(chest.get().getInventory());
                    }
                    if(!chestTroc.isPresent()){
                        player.sendMessage(MESSAGE("&4Creation du TROC impossible ici !"));
                        return CommandResult.empty();
                    }
                                           
                    for (Inventory inv : chestTroc.get().slots()) {
                        if(!inv.peek().isPresent()){
                            inv.offer(getIS());
                        }
                        troc = new Troc(locTroc,index,"",null,0,0d,getIS().createSnapshot(),"LIBRE","LIBRE",0);
                        try {
                            trocManager.save(troc);
                        } catch (IOException | ObjectMappingException ex) {
                            Logger.getLogger(CommandTrocAdd.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        index = index + 1;
                        if(index == 9)break;
                    }
                    player.sendMessage(MESSAGE("&eCr\351ation coffre TROC termin\351"));
                }else{
                    return CommandResult.empty();
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
    
    private ItemStack getIS(){
        ItemStack notroc = ItemStack.builder().itemType(ItemTypes.BARRIER).build();
        LoreData itemData = notroc.getOrCreate(LoreData.class).get();            
        List<Text> itemLore = itemData.lore().get();
        itemLore.add(MESSAGE("&eEmplacement libre"));
        itemLore.add(MESSAGE("&bclique ici avec ton item pour l'ajouter"));
        notroc.offer(Keys.ITEM_LORE, itemLore);
        return notroc;            
    }
}