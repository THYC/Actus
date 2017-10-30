package net.teraoctet.actus.plot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.bookmessage.Book;
import net.teraoctet.actus.commands.plot.CallBackPlot;
import net.teraoctet.actus.utils.Data;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Config.DISPLAY_PLOT_MSG_FOR_OWNER;
import static net.teraoctet.actus.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockSnapshot;
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
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.BlockState;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.BEDROCK;
import static org.spongepowered.api.block.BlockTypes.CHEST;
import static org.spongepowered.api.block.BlockTypes.FIRE;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.data.property.block.MatterProperty;
import org.spongepowered.api.data.property.block.MatterProperty.Matter;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import static org.spongepowered.api.item.ItemTypes.ARROW;
import org.spongepowered.api.event.block.NotifyNeighborBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.filter.cause.Root;
import static org.spongepowered.api.item.ItemTypes.WOODEN_AXE;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.LocatableBlock;

public class PlotListener {
        
    public PlotListener() {}
    
    private static final CallBackPlot  CB  = new CallBackPlot();
    
    @Listener
    //@SuppressWarnings("null")
    public void onInteractBlock(InteractBlockEvent  event, @First Player player){
        
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        BlockSnapshot b = event.getTargetBlock();
        if(!b.getLocation().isPresent()){return;}
        Location loc = b.getLocation().get();
        Optional<ItemStack> itemInHand = player.getItemInHand(HandTypes.MAIN_HAND);
        Optional<Plot> plot = plotManager.getPlot(loc);
        
        // Event click gauche -- saisie angle 1 plot
        if (event instanceof InteractBlockEvent.Primary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem().equals(WOODEN_SHOVEL) || itemInHand.get().getItem().equals(WOODEN_AXE)){
                    if (plot.isPresent()) {
                        player.sendMessage(PLOT_INFO(player,plot.get().getNameOwner(),plot.get().getNameAllowed(),plot.get().getName()));
                        if(player.hasPermission("actus.admin.plot")){
                            PlotManager plotPlayer = PlotManager.getSett(player);
                            if(plotPlayer.getBorder1().isPresent()){
                                if(plotPlayer.getBorder1().get().equals(loc)){
                                    event.setCancelled(true);
                                    return;
                                }        
                            }
                            plotPlayer.setBorder(1, loc);
                            player.sendMessage(MESSAGE("&aNiveau : &e" + plot.get().getLevel()));
                            player.sendMessage(Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        }
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        if(plotPlayer.getBorder1().isPresent()){
                            if(plotPlayer.getBorder1().get().equals(loc)){
                                event.setCancelled(true);
                                return;
                            }
                        }
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
                if(itemInHand.get().getItem() == WOODEN_SHOVEL  || itemInHand.get().getItem() == WOODEN_AXE){
                    if (plot.isPresent()) {
                        player.sendMessage(PLOT_INFO(player,plot.get().getNameOwner(),plot.get().getNameAllowed(),plot.get().getName()));
                        if(player.hasPermission("actus.admin.plot")){
                            PlotManager plotPlayer = PlotManager.getSett(player);
                            if(plotPlayer.getBorder2().isPresent()){
                                if(plotPlayer.getBorder2().get().equals(loc)){
                                    event.setCancelled(true);
                                    return;
                                }       
                            }
                            plotPlayer.setBorder(2, loc);
                            player.sendMessage(MESSAGE("&aNiveau : &e" + plot.get().getLevel()));
                            player.sendMessage(Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        }
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        if(plotPlayer.getBorder2().isPresent()){
                            if(plotPlayer.getBorder2().get().equals(loc)){
                                event.setCancelled(true);
                                return;
                            }
                        }
                        plotPlayer.setBorder(2, loc);
                        player.sendMessage(Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                    }
                    event.setCancelled(true);
                    return;
                } 
            }
        }
        
        // si la boussole est en main, on sort"
        if (itemInHand.isPresent()) {
            if(itemInHand.get().getItem().equals(COMPASS)){  
                return;
            } 
            if(itemInHand.get().getItem().equals(ARROW)){
                event.setCancelled(true);
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
                                b.getLocation().get().removeBlock();
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
                                    Book book = new Book();
                                    book.setAuthor(MESSAGE(player.getName()));
                                    book.setTitle(MESSAGE(vendeur.getName() + "_" + player.getName() + "_" +  serverManager.dateShortToString()));
                                    List<Text> textList = new ArrayList();
                                    textList.add(Text.builder().append(MESSAGE("Votre parcelle a \351t\351 vendu a " + player.getName() + ",\n" + 
                                            String.valueOf(cout) + " \351meraudes ont \351t\351 ajout\351 a votre compte")).build());
                                    book.setPages(textList);
                                    try {
                                        configBook.saveBook(book);
                                    } catch (IOException | ObjectMappingException ex) {
                                        Logger.getLogger(PlotListener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
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
            if(b.getState().getType().equals(CHEST)){
                Optional<TileEntity> chest = b.getLocation().get().getTileEntity();
                if(chest.get().getValue(Keys.DISPLAY_NAME).isPresent()){
                    if(chest.get().getValue(Keys.DISPLAY_NAME).get().get().toPlain().contains("[+]")){
                        return;

                    }
                }
            }
            if(!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && plot.get().getNoInteract() == 1 && aplayer.getLevel() != 10) {
                player.sendMessage(PLOT_PROTECTED());
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
                if(DISPLAY_PLOT_MSG_FOR_OWNER() && plot.get().getUuidAllowed().contains(player.getUniqueId().toString()))
                player.sendMessage(MESSAGE(plot.get().getMessage(),player));
            }
        }
        
        if(fplot.isPresent() && plot.isPresent()) {
            if(!plot.get().equals(fplot.get())){
                if(DISPLAY_PLOT_MSG_FOR_OWNER() && plot.get().getUuidAllowed().contains(player.getUniqueId().toString()))
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
    public void onPlayerSendCommand(SendCommandEvent event, @First Player player) {
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
    public void onBreakBlock(ChangeBlockEvent.Break event, @First Player player) {
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
    public void onPlaceBlock(ChangeBlockEvent.Place event, @First Player player) {
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        Optional<Plot> plot = plotManager.getPlot(loc);
        if (plot.isPresent()){
            if(plot.get().getNoBuild() == 1 && !plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && aplayer.getLevel() != 10){
                player.sendMessage(PLOT_PROTECTED());
                event.setCancelled(true);
            }
        }

        if(block.getFinal().getState().getType().equals(BEDROCK)){
 
            PlotManager plotPlayer = PlotManager.getSett(player);
            plotPlayer.setBorder(1,loc.add(-20, 0, -20));
            plotPlayer.setBorder(2,loc.add(20, 0, 20));
 
            int level = 0;

            if(plotManager.plotNotAllow(plotPlayer.getBorder1().get(), plotPlayer.getBorder2().get())){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return;
            }
            CB.callCreate(player.getName() + serverManager.dateShortToString(),false,0,level).accept(player);
        }
    }
    
    @Listener
    public void onLiquidFlow(ChangeBlockEvent.Place event, @Root BlockSnapshot block) {
        Optional<MatterProperty> matter = block.getState().getProperty(MatterProperty.class);
        if (matter.isPresent() && matter.get().getValue() == Matter.LIQUID) {
            Optional<Location<World>> optLoc = block.getLocation();
            if(!optLoc.isPresent())return;
            Location loc = optLoc.get();
            Optional<Plot> plot = plotManager.getPlot(loc);
            if (plot.isPresent()) {                
                Optional<Player> player = event.getCause().first(Player.class);
                if(player.isPresent()){
                    APlayer aplayer = getAPlayer(player.get().getIdentifier());
                    if(!plot.get().getUuidAllowed().contains(player.get().getUniqueId().toString()) && aplayer.getLevel() != 10){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    
    /*@Listener	
    public void onFireSpread(NotifyNeighborBlockEvent  event, @First BlockSnapshot source){
        BlockState bstate = source.getState();
        if ((bstate.getType().equals(FIRE) || bstate.getType().getName().contains("lava"))){
            Map<Direction, BlockState> dirs = event.getNeighbors();
            for (Direction dir:dirs.keySet()){
                Location<World> loc = source.getLocation().get().add(dir.asOffset());
                Optional<Plot> plot = plotManager.getPlot(loc);
                if(plot.isPresent()){
                    if(plot.get().getNoFire() == 1){
                        Optional<Player> player = event.getCause().last(Player.class);
                        if(player.isPresent()){
                            APlayer aplayer = getAPlayer(player.get().getIdentifier());
                            if(plot.get().getUuidAllowed().contains(player.get().getUniqueId().toString()) || aplayer.getLevel() == 10){
                                event.getNeighbors().clear();
                                return;
                            }
                        }
                        event.getNeighbors().clear();
                        source.getLocation().get().setBlockType(AIR); 
                        event.setCancelled(true);
                    }
                }
            }
        }
    }*/
            
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
    
    @Listener
    public void onDamageEntity(DamageEntityEvent event, @Root DamageSource damage, @First EntityDamageSource entity) {
	Optional<Plot> plot = plotManager.getPlot(event.getTargetEntity().getLocation());
        if (plot.isPresent()) {
            if (event.getTargetEntity() instanceof Player) {
                Entity ent = entity.getSource();
                //Player player = (Player) event.getTargetEntity();
            
                if (damage.getType() == DamageTypes.FIRE || damage.getType() == DamageTypes.MAGMA) {
                    if(plot.get().getNoFire() == 1)event.setCancelled(true);
		} else if (damage.getType() == DamageTypes.EXPLOSIVE) {
                    if (ent.getType() == EntityTypes.DRAGON_FIREBALL || ent.getType() == EntityTypes.ENDER_CRYSTAL
                    || ent.getType() == EntityTypes.SMALL_FIREBALL || ent.getType() == EntityTypes.FIREBALL) {
                        if(plot.get().getNoTNT() == 1)event.setCancelled(true);
                    } else if (ent.getType() == EntityTypes.TNT_MINECART || ent.getType() == EntityTypes.PRIMED_TNT) {
			if(plot.get().getNoTNT() == 1)event.setCancelled(true);
                    }	
		} else if (damage.getType() == DamageTypes.PROJECTILE || damage.getType() == DamageTypes.ATTACK) {
                    if (ent.getType() == EntityTypes.SPECTRAL_ARROW || ent.getType() == EntityTypes.TIPPED_ARROW) {
			Arrow arrow = (Arrow) ent;
			if (arrow.getShooter() instanceof Monster) {
                            if(plot.get().getNoMob() == 1)event.setCancelled(true);
			}
                    }	
                }
  
            } else if(event.getTargetEntity() instanceof Monster) {
		if(plot.get().getNoMob() == 1)event.setCancelled(true);
            } else if(event.getTargetEntity() instanceof Animal) {
		if(plot.get().getNoMob() == 1)event.setCancelled(true);
            } 
	}
    }
	
    @Listener
    public void onDamage(DamageEntityEvent event, @Root DamageSource damage) {
	if (event.getTargetEntity() instanceof Player) {
            Optional<Plot> plot = plotManager.getPlot(event.getTargetEntity().getLocation());
            if (plot.isPresent()) {
                Player player = (Player) event.getTargetEntity();
		if(plot.get().getNoMob() == 1)event.setCancelled(true);
            }
	}
    }
      
    /**
     *
     * @param event
     * @param block
     */
    @Listener
    public void onBurningBlock(ChangeBlockEvent.Pre event, @Root LocatableBlock block) {
        if(block.getBlockState().getType().equals(FIRE) || event.getLocations().get(0).getBlock().equals(FIRE)){
            Optional<Plot> plot = plotManager.getPlot(event.getLocations().get(0));
            if (plot.isPresent()) {
                event.setCancelled(true);
            }
        }
    }
}
