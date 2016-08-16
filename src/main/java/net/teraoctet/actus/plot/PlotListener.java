package net.teraoctet.actus.plot;

import java.util.Optional;
import net.teraoctet.actus.Actus;

import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.DISPLAY_PLOT_MSG_FOR_OWNER;
import org.spongepowered.api.block.BlockSnapshot;
import static org.spongepowered.api.block.BlockTypes.FIRE;
import static org.spongepowered.api.block.BlockTypes.FLOWING_LAVA;
import static org.spongepowered.api.block.BlockTypes.FLOWING_WATER;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.WOODEN_SHOVEL;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;
import static org.spongepowered.api.Sponge.getGame;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import static org.spongepowered.api.block.BlockTypes.WALL_SIGN;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import static org.spongepowered.api.item.ItemTypes.COMPASS;

import static net.teraoctet.actus.utils.MessageManager.PLOT_INFO;
import static net.teraoctet.actus.utils.MessageManager.PLOT_PROTECTED;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_ENTER;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_FLY;
import static net.teraoctet.actus.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.block.BlockTypes.LAVA;
import static org.spongepowered.api.block.BlockTypes.WATER;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.IgniteEntityEvent;
import static net.teraoctet.actus.utils.MessageManager.PLOT_INFO;
import static net.teraoctet.actus.utils.MessageManager.PLOT_PROTECTED;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_ENTER;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_FLY;
import static net.teraoctet.actus.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.PLOT_INFO;
import static net.teraoctet.actus.utils.MessageManager.PLOT_PROTECTED;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_ENTER;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_FLY;
import static net.teraoctet.actus.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.PLOT_INFO;
import static net.teraoctet.actus.utils.MessageManager.PLOT_PROTECTED;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_ENTER;
import static net.teraoctet.actus.utils.MessageManager.PLOT_NO_FLY;
import static net.teraoctet.actus.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;

public class PlotListener {
        
    public PlotListener() {}

    @Listener
    @SuppressWarnings("null")
    public void onInteractBlock(InteractBlockEvent  event){
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        BlockSnapshot b = event.getTargetBlock();
        if(!b.getLocation().isPresent()){return;}
        Location loc = b.getLocation().get();
        Optional<ItemStack> itemInHand = player.getItemInHand(HandTypes.MAIN_HAND);
        Optional<Plot> plot = plotManager.getPlot(loc);
        
        // Event click gauche -- saisie angle 1 plot
        if (event instanceof InteractBlockEvent.Primary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (plot.isPresent()) {
                        player.sendMessage(PLOT_INFO(player,plot.get().getNameOwner(),plot.get().getNameAllowed(),plot.get().getName()));
                        if(player.hasPermission("actus.admin.plot")){
                            PlotManager plotPlayer = PlotManager.getSett(player);
                            plotPlayer.setBorder(1, loc);
                            player.sendMessage(MESSAGE("&aNiveau : &e" + plot.get().getLevel()));
                            player.sendMessage(Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        }
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        plotPlayer.setBorder(1, loc);
                        player.sendMessage(Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                    }
                    event.setCancelled(true);
                    return;
                } 
            }
        }
        
        // Event click droit -- saisie angle 2 plot
	if (event instanceof InteractBlockEvent.Secondary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (plot.isPresent()) {
                        player.sendMessage(PLOT_INFO(player,plot.get().getNameOwner(),plot.get().getNameAllowed(),plot.get().getName()));
                        if(player.hasPermission("actus.admin.plot")){
                            PlotManager plotPlayer = PlotManager.getSett(player);
                            plotPlayer.setBorder(2, loc);
                            player.sendMessage(MESSAGE("&aNiveau : &e" + plot.get().getLevel()));
                            player.sendMessage(Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        }
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        plotPlayer.setBorder(2, loc);
                        player.sendMessage(Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                    }
                    event.setCancelled(true);
                    return;
                } 
            }
        }
        
        // si la boussole est en main, on sort"
        Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
        if (is.isPresent()) {
            if(is.get().getItem().equals(COMPASS)){  
                return;
            }   
        }
        
        // Interact sur sign "Parcelle a vendre"
        Optional<TileEntity> block = loc.getTileEntity();
        if (block.isPresent()) {
            TileEntity tile=block.get();
            if (tile instanceof Sign) {
                if (event instanceof InteractBlockEvent.Secondary){
                    Sign sign=(Sign)tile;
                    Optional<SignData> optional=sign.getOrCreate(SignData.class);
                    if (optional.isPresent()) {
                        SignData offering = optional.get();
                        Text txt1 = offering.lines().get(0);
                        if (txt1.equals(MESSAGE("&1A VENDRE"))){
                            int cout = Integer.valueOf(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain());
                            if(!plotManager.hasPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain())){
                                player.sendMessage(MESSAGE("&eCette parcelle n'existe plus"));
                                b.getLocation().get().removeBlock(Cause.of(NamedCause.source(player)));
                                event.setCancelled(true);
                                return;
                            }
                            plot = plotManager.getPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain());
                            if(plot.isPresent()){
                                if (aplayer.getMoney()< cout && !plot.get().getUuidOwner().contains(player.getIdentifier())){
                                    player.sendMessage(ChatTypes.CHAT,MISSING_BALANCE());
                                    event.setCancelled(true);
                                }
                                if (!plot.get().getUuidOwner().contains("ADMIN") && !plot.get().getUuidOwner().contains(player.getIdentifier())){
                                    APlayer vendeur = getAPlayer(plot.get().getUuidOwner());
                                    vendeur.creditMoney(cout);
                                    vendeur.sendMessage(MESSAGE("&6" + player.getName() + " &7vient d'acheter votre parcelle"));
                                    vendeur.sendMessage(MESSAGE("&6" + cout + " emeraudes &7ont ete ajoute a votre compte"));
                                    vendeur.sendMessage(MESSAGE("&6/bank &7pour consulter votre compte"));  
                                }
                                plot.get().delSale();
                                if(!plot.get().getUuidOwner().contains(player.getIdentifier())){
                                    aplayer.debitMoney(cout);
                                    plot.get().setUuidOwner(aplayer.getUUID());
                                    plot.get().setUuidAllowed(aplayer.getUUID());
                                    plot.get().update();
                                    Data.commit();
                                    player.sendMessage(MESSAGE("&eVous etes maintenant le nouveau proprietaire de cette parcelle"));
                                } else {
                                    player.sendMessage(MESSAGE("&eVous avez annule la vente de votre parcelle"));
                                }
                            }
                            return;
                        } 
                    }
                }
            } 
        }
        
        // Interact sur autre block
        if (plot.isPresent()){
            if(!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && plot.get().getNoInteract() == 1 && aplayer.getLevel() != 10) {
                player.sendMessage(PLOT_PROTECTED());
                player.sendMessage(MESSAGE(String.valueOf(plot.get().getNoInteract())));
                event.setCancelled(true);
            }else{
                if(plotManager.hasTag(loc, plot.get())){
                    plotManager.remTag(plot.get());
                }
            }
        }
    }
        
    @Listener
    public void onPlayerMovePlot(MoveEntityEvent event , @First Player player) {
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transform<World> to = event.getToTransform();
        Transform<World> from = event.getFromTransform();
        String world = player.getWorld().getName();
        Optional<Plot> plot = plotManager.getPlot(to.getLocation());
        Optional<Plot> jail = plotManager.getPlot(from.getLocation(),true);
        Optional<Plot> fplot = plotManager.getPlot(from.getLocation());

        if(!fplot.isPresent()) {
            if(plot.isPresent()){
                if(DISPLAY_PLOT_MSG_FOR_OWNER() || !plot.get().getUuidAllowed().contains(player.getUniqueId().toString()))
                player.sendMessage(MESSAGE(plot.get().getMessage(),player));
            }
        }

        if(plot.isPresent()){
            if(!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                if (player.get(Keys.CAN_FLY).isPresent()) { 
                    if(player.get(Keys.CAN_FLY).get() == true && plot.get().getNoFly() == 1) {
                        player.offer(Keys.IS_FLYING, false); 
                        player.offer(Keys.CAN_FLY, false); 
                        player.sendMessage(PLOT_NO_FLY());
                        event.setCancelled(true);
                    }
                }

                if(!plotManager.getPlot(from.getLocation()).isPresent()) {
                    player.sendMessage(MESSAGE(plot.get().getMessage(),player));
                    if(plot.get().getNoEnter() == 1 && !player.hasPermission("actus.plot.enter")) {
                        player.transferToWorld(getGame().getServer().getWorld(world).get(), from.getPosition());
                        player.sendMessage(PLOT_NO_ENTER());
                        event.setCancelled(true);
                    }
                }
            }
        }

        if (jail.isPresent()){
            if(!plotManager.getPlot(to.getLocation(), true).isPresent() && aplayer.getJail().equalsIgnoreCase(jail.get().getName())) {
                player.transferToWorld(getGame().getServer().getWorld(world).get(), from.getPosition());
                player.sendMessage(ChatTypes.CHAT,PLOT_NO_ENTER());
            }
        }
    }
    
    @Listener
    public void onPlayerSendCommand(SendCommandEvent event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Location loc = player.getLocation();
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (plot.isPresent()){
            if(!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10 && plot.get().getNoCommand() == 1){
                player.sendMessage(PLOT_PROTECTED());
                event.setCancelled(true);
            }
        }
        
    }
    
    @Listener
    public void onBreakBlock(ChangeBlockEvent.Break event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
                       
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (plot.isPresent()){
            if(plot.get().getNoBreak() == 1 && !plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && 
                    aplayer.getLevel() != 10){
                player.sendMessage(PLOT_PROTECTED());
                event.setCancelled(true);
            }else{
                if(plotManager.hasTag(loc, plot.get())){
                    event.setCancelled(true);
                    plotManager.remTag(plot.get());
                }
            }
        }
    }
    
    @Listener
    public void onBreakSignSale(ChangeBlockEvent.Break event) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        if (block.getOriginal().getState().equals(STANDING_SIGN) || block.getOriginal().getState().equals(WALL_SIGN)){
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Place event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (plot.isPresent()){
            if(plot.get().getNoBuild() == 1 && !plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                if(block.getOriginal().getState().equals(FIRE))return;
                player.sendMessage(PLOT_PROTECTED());
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onFluidBlock(ChangeBlockEvent.Modify event) {
        //Actus.plugin.getLogger().info(event.getTransactions().toString());
        if(event.getTransactions().contains(FLOWING_WATER) || event.getTransactions().contains(WATER)
                || event.getTransactions().contains(FLOWING_LAVA) || event.getTransactions().contains(LAVA)){
            //Optional<Player> optPlayer = event.getCause().first(Player.class);
            //if (optPlayer.isPresent()) {
                //Player player = optPlayer.get();
               // APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                Transaction<BlockSnapshot> block = event.getTransactions().get(0);
            //}
            Optional<Location<World>> optLoc = block.getOriginal().getLocation();
            Location loc = optLoc.get();

            Optional<Plot> plot = plotManager.getPlot(loc);
            if (plot.isPresent()){
                //if(!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                    //player.sendMessage(PLOT_PROTECTED());
                    Actus.plugin.getLogger().info("modify");
                    block.setCustom(block.getOriginal());
                    event.setCancelled(true);
                //}
            }
        }
    }
    
    @Listener
    public void onBlockStartBurn(IgniteEntityEvent e){
                
                Entity b = e.getTargetEntity();
                Cause ignit = e.getCause(); 
                if (b == null){
                               return;
                }
                
                plugin.getLogger().info("Is BlockIgniteEvent event. Canceled? " + e.isCancelled());
                
                if (ignit.first(Player.class).isPresent()){
                        Player p = ignit.first(Player.class).get();                                   
                        e.setCancelled(true);
                }else{
                    e.setCancelled(true);
                }
    }
    
    @Listener
    public void onBurningBlock(ChangeBlockEvent.Post event) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (plot.isPresent()){   
            if(block.getFinal().getState().getType().equals(FIRE) && plot.get().getNoFire() == 1){    
                Actus.plugin.getLogger().info("stop");
                Actus.plugin.getLogger().info(block.getOriginal().getState().getType().getName());
                if(block.getOriginal().getState().getType().equals(FIRE)){    
                    Actus.plugin.getLogger().info("stop2");
                    event.getTransactions().stream().forEach((bt) -> {bt.setCustom(BlockSnapshot.NONE);});
                    event.setCancelled(true);
                }else{
                    event.getTransactions().stream().forEach((bt) -> {bt.setCustom(bt.getOriginal());});
                    event.setCancelled(true);
                }
            }
            
                        
            /*Optional<Player> optPlayer = event.getCause().first(Player.class);
            Optional<Explosive> optExplosive = event.getCause().first(Explosive.class);
            
            if(block.getFinal().getState().getType().equals(AIR) && !block.getOriginal().getState().getType().equals(FLOWING_LAVA) && 
            !block.getOriginal().getState().getType().equals(FLOWING_WATER) && !optPlayer.isPresent() && !optExplosive.isPresent() && plot.get().getNoFire() == 1){
                Actus.plugin.getLogger().info("fire2");
                for (Transaction<BlockSnapshot> bt:event.getTransactions()){
                    bt.setCustom(bt.getOriginal());                    
                }
            }*/
        }
    }
        
    @Listener
    public void onExplosion(ExplosionEvent.Pre event) {
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (plot.isPresent()){
            if (plot.get().getNoTNT() == 1){ event.setCancelled(true);}
        }
    }
    
    @Listener
    public void onPVP(DamageEntityEvent event, @First EntityDamageSource source){
        getGame().getServer().getConsole().sendMessage(MESSAGE(source.getSource().getType().getName())); //attaquant
        getGame().getServer().getConsole().sendMessage(MESSAGE(event.getTargetEntity().getType().getName())); //victime
    }
}
