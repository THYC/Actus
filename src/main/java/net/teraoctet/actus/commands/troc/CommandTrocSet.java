package net.teraoctet.actus.commands.troc;

import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.Actus.tm;
import net.teraoctet.actus.troc.Troc;
import static net.teraoctet.actus.utils.Data.getGuild;
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
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.BARRIER;
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
            while (playerBlockRay.hasNext())            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST))                { 
                    locChest1 = Optional.of(currentHitRay.getLocation()); 
                    break;
                }                     
            } 

            if (locChest1.isPresent()){
                String owner = ctx.<String> getOne("owner").orElse("LIBRE");
                int idGuild = ctx.<Integer> getOne("guild").orElse(0);
                
                String locTroc = DeSerialize.location(locChest1.get());
                Location<World> locChest2;
                Optional<TileEntity> chestBlock = locChest1.get().getTileEntity();
                TileEntity tileChest = chestBlock.get();
                String chestName = "&l&3TROC&r " + locTroc + " " + owner + " " + idGuild;
                if(owner.equalsIgnoreCase("LIBRE")){
                    chestName = "&l&aTROC&r " + locTroc + " " + owner + " " + idGuild;
                }
                tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                
                if(sm.locDblChest(locChest1.get()).isPresent()){
                    locChest2 = sm.locDblChest(locChest1.get()).get();
                    Optional<TileEntity> dblchestBlock = locChest2.getTileEntity();
                    TileEntity tiledblChest = dblchestBlock.get();
                    tiledblChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                }
                
                String ownerSign = owner;
                if(idGuild != 0)ownerSign = getGuild(idGuild).getName();
                tm.writeToChestSign(locTroc,ownerSign,Optional.empty());
                
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
                            troc = new Troc(locTroc,index,null,0,0d,getIS().createSnapshot(),"LIBRE","LIBRE",0);
                            tm.save(troc);
                        }else{
                            if(inv.peek().get().getType().equals(BARRIER)){
                                troc = new Troc(locTroc,index,null,0,0d,getIS().createSnapshot(),"LIBRE","LIBRE",0);
                                tm.save(troc);
                            }
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