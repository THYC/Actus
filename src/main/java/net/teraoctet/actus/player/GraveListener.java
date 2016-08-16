package net.teraoctet.actus.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.text.Text;

public class GraveListener {
    
    public GraveListener() {}
                
    @Listener
    public void onPlayerDead(DestructEntityEvent.Death event) {
        Living living = event.getTargetEntity();
        if(living instanceof Player) {  
            Player player = (Player) living;

            String lastdead = DeSerialize.location(player.getLocation());
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            aplayer.setLastdeath(lastdead);
            aplayer.setLastposition(lastdead);
            aplayer.update();

            if (player.hasPermission("actus.grave")) {

                Location location = player.getLocation().add(0, -2, 0);
                location.setBlockType(BlockTypes.CHEST,Cause.of(NamedCause.source(player)));
                Chest chest = (Chest)location.getTileEntity().get();
                TileEntity realChest = (TileEntity) chest;
                DataHolder dh;
                for(Inventory slotInv : player.getInventory().query(GridInventory.class).slots()){
                    Optional<org.spongepowered.api.item.inventory.ItemStack> peek = slotInv.peek();
                    if(peek.isPresent()){
                        //dh.copyFrom(player.getInventory().)
                        //realChest.getContainers().;
                        slotInv.clear();
                    }
                }
                
                
                
                //""""""""""""""""""""""""""""""""""""""""""""""""""""""

                location = controlBlock(location);
                location.setBlockType(STANDING_SIGN,Cause.of(NamedCause.source(player)));  
                Optional<TileEntity> signBlock = location.getTileEntity();
                TileEntity tileSign = signBlock.get();
                Sign sign=(Sign)tileSign;
                Optional<SignData> opSign = sign.getOrCreate(SignData.class);

                SignData signData = opSign.get();
                List<Text> rip = new ArrayList<>();
                rip.add(MESSAGE("&5+++++++++++++"));
                rip.add(MESSAGE("&o&5 Repose En Paix"));
                rip.add(MESSAGE("&5&l" + player.getName()));
                rip.add(MESSAGE("&5+++++++++++++"));
                signData.set(Keys.SIGN_LINES,rip );
                sign.offer(signData);

                /*actus.game.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        nb.getLocation().getBlock().setType(Material.AIR);
                        player.sendMessage(formatMsg.format(conf.getStringYAML("messages.yml", "graveDespawn"),player));
                        if (GraveListener.inventorys.size() > 0)
                        {
                            inventorys.remove(0);
                        }
                    }
                }*/
            }
        
        }
    }
    
    public Location controlBlock(Location location){                
        if (location.getBlockType() != AIR){ location = location.add(0,+1,0);
            if (location.getBlockType() == AIR){ return location;}
            else { location = location.add(+1,-1,0);
                if (location.getBlockType() == AIR) { return location;}
                else { location = location.add(-1,0,+1);
                    if (location.getBlockType() == AIR) { return location;}
                    else { location = location.add(-1,0,-1);
                        if (location.getBlockType() == AIR) { return location;}
                        else { location = location.add(+1,0,-1);
                            if (location.getBlockType() == AIR) { return location;}
                            else { location = location.add(0,+1,+1);
                                location = controlBlock(location);
                                return location;
                            }
                        }
                    }
                }
            }
        }
        return location;
    }
}