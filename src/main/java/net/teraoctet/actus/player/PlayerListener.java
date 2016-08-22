package net.teraoctet.actus.player;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import static net.teraoctet.actus.Actus.bookManager;

import static net.teraoctet.actus.Actus.inputDouble;
import static net.teraoctet.actus.Actus.inputShop;
import static net.teraoctet.actus.Actus.mapCountDown;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.player.PlayerManager.addAPlayer;
import net.teraoctet.actus.utils.CooldownToTP;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getUUID;
import static net.teraoctet.actus.player.PlayerManager.removeAPlayer;
import static net.teraoctet.actus.player.PlayerManager.removeUUID;
import net.teraoctet.actus.utils.DeSerialize;
import net.teraoctet.actus.utils.SettingCompass;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import static org.spongepowered.api.item.ItemTypes.COMPASS;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.item.inventory.DropItemEvent;

import static net.teraoctet.actus.utils.MessageManager.FIRSTJOIN_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.JOIN_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.EVENT_DISCONNECT_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NAME_CHANGE;
import static net.teraoctet.actus.utils.MessageManager.FIRSTJOIN_BROADCAST_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.EVENT_LOGIN_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.world.WorldManager.getWorld;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.block.BlockTypes.CHEST;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import static org.spongepowered.api.item.ItemTypes.STICK;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;

public class PlayerListener {
    
    public static ArrayList<Inventory> inventorys = new ArrayList<>();
    public PlayerListener() {}
    
    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event) {
        Player player = (Player) event.getTargetUser();
        getGame().getServer().getBroadcastChannel().send(EVENT_LOGIN_MESSAGE(player));
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        
        if(aplayer == null) {
            getGame().getServer().getBroadcastChannel().send(FIRSTJOIN_BROADCAST_MESSAGE(player));
        }
    }
    
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
    	Player player = event.getTargetEntity();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        event.setMessageCancelled(true);
    	
        if(aplayer == null) {
            aplayer = new APlayer(uuid, 0, name, "", 0, "", 20, "", "", 0, serverManager.dateToLong(),"N",0,0,0);
            aplayer.insert();
            commit();
            player.sendMessage(FIRSTJOIN_MESSAGE(player)); 
        } else {
            addAPlayer(aplayer.getUUID(), aplayer);
            player.sendMessage(JOIN_MESSAGE(player));
            player.sendMessage(MESSAGE("&7Derniere connection : " + serverManager.longToDateString(aplayer.getLastonline())));
        }
	PlayerManager.addFirstTime(player.getIdentifier(), serverManager.dateToLong());
        APlayer player_uuid = getAPlayer(uuid);
        
        if(player_uuid != null && getUUID(name) == null) {
            getGame().getServer().getBroadcastChannel().send(NAME_CHANGE(player_uuid.getName(),player.getName()));
            removeAPlayer(player_uuid.getUUID());
            removeUUID(player_uuid.getName());
            player_uuid.setName(name);
            player_uuid.update();
            commit();
        }
    }
    
    @Listener
    public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event) {
        Player player = (Player) event.getTargetEntity();
        APlayer aplayer = getAPlayer(player.getIdentifier());
        long timeConnect = serverManager.dateToLong()- PlayerManager.getFirstTime(player.getIdentifier());
        long onlineTime = (long)aplayer.getOnlinetime() + timeConnect;
        PlayerManager.removeFirstTime(player.getIdentifier());
        aplayer.setLastonline(serverManager.dateToLong());
        aplayer.setOnlinetime(onlineTime);
        aplayer.update();
        event.setMessage(EVENT_DISCONNECT_MESSAGE(player));
    }
        
    //-Credits : 
    //CommandLogger : https://github.com/prism/CommandLogger
    //Author : viveleroi
    @Listener
    public void onSendCommand(final SendCommandEvent event){
        StringBuilder builder = new StringBuilder();

        Optional<Player> optionalPlayer = event.getCause().first(Player.class);
        if (optionalPlayer.isPresent()) {
            builder.append(optionalPlayer.get().getName());

            Location<World> loc = optionalPlayer.get().getLocation();
            builder.append(String.format(" (%s @ %d %d %d) ", loc.getExtent().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        }
        else if(event.getCause().first(ConsoleSource.class).isPresent()) {
            builder.append("console");
        }
        else if(event.getCause().first(CommandBlockSource.class).isPresent()) {
            builder.append("command block");
        }

        builder.append(": /").append(event.getCommand()).append(" ").append(event.getArguments());

        getGame().getServer().getConsole().sendMessage(Text.of(builder.toString()));
    }
    
    @Listener
    public void promptDouble(MessageEvent event, @First Player player) {
        if(inputDouble.containsKey(player)){
            String smessage = event.getOriginalMessage().toPlain();
            smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
            try{
                Double d = Double.valueOf(smessage);
                if(d==0){
                    inputDouble.remove(player);
                    player.sendMessage(MESSAGE("&bL'action a \351t\351 annul\351"));
                    event.clearMessage();
                    return;
                }
                inputDouble.replace(player, d);
                event.setMessage(MESSAGE("&eMaintenant cliques de nouveau sur le panneau pour confirmer/n")
                    .concat(MESSAGE("&esi tu tiens ta bourse dans ta main, la somme sera vers\351 dessus sinon tu aura des \351meraudes")));
            }catch(Exception ex){
                player.sendMessage(MESSAGE("&bTapes uniquement des chiffres ! recommences :"));
                player.sendMessage(MESSAGE("&bTapes 0 pour annuler"));
                event.clearMessage();
            }
        }
        if(inputShop.containsKey(player)){
            String smessage = event.getOriginalMessage().toPlain();
            smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
            try{
                Double d = Double.valueOf(smessage);
                if(d==0){
                    inputShop.remove(player);
                    player.sendMessage(MESSAGE("&bL'action a \351t\351 annul\351"));
                    event.clearMessage();
                    return;
                }
                Sponge.getCommandManager().process(player, inputShop.get(player) + " " + String.valueOf(d));
                event.clearMessage();
            }catch(Exception ex){
                player.sendMessage(MESSAGE("&bTapes uniquement des chiffres ! recommences :"));
                player.sendMessage(MESSAGE("&bTapes 0 pour annuler"));
                event.clearMessage();
            }
        }
    }
    
    @Listener
    public void onMessage(MessageChannelEvent.Chat event, @First Player player) {
        String smessage = event.getMessage().toPlain();
        smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
        Text message = MESSAGE(/*Permissions.getPrefix(player) +*/ "&a[" + player.getName() + "] &7" + smessage);
        Text prefixWorld = MESSAGE(getWorld(player.getWorld().getName()).getPrefix()) ;
        Text newMessage = Text.builder().append(message).build();
        event.setMessage(newMessage);
    }
    
    @Listener
    public void onPlayerCraft(ClickInventoryEvent event)
    {
        List<SlotTransaction> slots = event.filter(Predicate.isEqual(STICK));
        
        for(SlotTransaction slot : slots){
            //getGame().getServer().getConsole().sendMessage(MESSAGE(slot.getSlot().slots().toString()));
        }
        //getGame().getServer().getConsole().sendMessage(MESSAGE("crafting"));
        //if(event.contains(COMPASS)){
            //getGame().getServer().getConsole().sendMessage(MESSAGE("crafting"));
        //}
    }
    
    @Listener
    public void onPlayerCraft2(DropItemEvent.Dispense event, @First Player player)
    {
        player.sendMessage(MESSAGE("onPlayerCraft2 dispense"));
        getGame().getServer().getConsole().sendMessage(MESSAGE("onPlayerCraft2 dispense"));
        
        //getGame().getServer().getConsole().sendMessage(MESSAGE("onPlayerCraft2 : " + event.getEntities().get(0).toString()));
        
    }
    
    /*@Listener
    public void onEntityDeath(DestructEntityEvent.Death event) {

        Living living = event.getTargetEntity();
        if(living instanceof Player) {
            
            
            
            Player player = (Player) living;
            
            
            Location location = player.getLocation().add(0, -2, 0);
            location.setBlockType(BlockTypes.CHEST);
            Optional<TileEntity> chestBlock = location.getTileEntity();
            
            TileEntity tileChest = chestBlock.get();
            
            Chest chest = (Chest) location.getTileEntity().get();
                       
            
            Inventory inv = player.getInventory();
            List<ItemStack> isl = new ArrayList();
            for (Inventory it : inv.slots()) {
                if(it.peek().isPresent()){
                    chest.getInventory().offer(it.peek().get());
                }
                
            }
        }
    }*/
        
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
                Optional<TileEntity> chestBlock = location.getTileEntity();

                TileEntity tileChest = chestBlock.get();
                Chest chest =(Chest)tileChest;

                for(Inventory slotInv : player.getInventory().query(GridInventory.class).slots()){
                    Optional<ItemStack> peek = slotInv.peek();
                    if(peek.isPresent()){
                        chest.getInventory().offer(peek.get());
                        slotInv.clear();
                    }
                }
                
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
    
    @Listener
    public void onInteractLore(InteractBlockEvent event, @First Player player) {
        BlockSnapshot b = event.getTargetBlock();                   
        if(b.get(Keys.DISPLAY_NAME).isPresent()){
            Optional<Text> displayName = b.get(Keys.DISPLAY_NAME);
            if(!displayName.get().toPlain().contains(player.getName())){
                player.sendMessage(MESSAGE("&eoups ! ce coffre est verouill\351 !"));
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onEntityInteract(InteractEntityEvent.Secondary event) {
        Entity entity = event.getTargetEntity();
        if(entity.get(Keys.REPRESENTED_ITEM).isPresent()) {
            plugin.getLogger().info(entity.get(Keys.REPRESENTED_ITEM).get().getType().getName());
        }
    }
                    
    
    @Listener
    public void onCompassInteract(InteractBlockEvent event, @First Player player) {
        Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
        SettingCompass sc = new SettingCompass();
        
        if (is.isPresent()) {
            // Event click droit
            if (event instanceof InteractBlockEvent.Secondary){
                if(is.get().getItem().equals(COMPASS)){  
                    
                    if(is.get().get(Keys.DISPLAY_NAME).isPresent()){
                        if(is.get().get(Keys.ITEM_LORE).isPresent()){
                            Optional<Text> displayName = is.get().get(Keys.DISPLAY_NAME);
                            Optional<List<Text>> ist = is.get().get(Keys.ITEM_LORE);
                            player.sendMessage(displayName.get());
                            player.sendMessage(ist.get().get(0));
                        }
                    }
                        
                    // si interact sur sign "Parcelle a vendre"
                    Optional<Location<World>> loc = event.getTargetBlock().getLocation();
                    if(loc.isPresent()){
                        Optional<TileEntity> block = loc.get().getTileEntity();
                        if (block.isPresent()) {
                            TileEntity tile=block.get();
                            if (tile instanceof Sign) {
                                Sign sign=(Sign)tile;
                                Optional<SignData> optional=sign.getOrCreate(SignData.class);
                                if (optional.isPresent()) {
                                    SignData offering = optional.get();
                                    Text txt1 = offering.lines().get(0);
                                    if (txt1.equals(MESSAGE("&1A VENDRE"))){
                                        if(!plotManager.hasPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain())){
                                            player.sendMessage(MESSAGE("&eCette parcelle n'existe plus"));
                                            event.setCancelled(true);
                                            return;
                                        }else{
                                            String name = offering.getValue(Keys.SIGN_LINES).get().get(1).toPlain();
                                            is = sc.MagicCompass(player,"PLOT:" + name);
                                            player.setItemInHand(HandTypes.MAIN_HAND,is.get());
                                            player.sendMessage(MESSAGE("&2MagicComapss : &eCoordonn\351e enregistr\351e sur votre boussole"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Optional<Vector3d> v3d = sc.getLookLocation(is.get());
                    if(v3d.isPresent())player.lookAt(v3d.get()); 
                }
            }
        }
    }
    
    @Listener
    public void onColorSign(ChangeSignEvent event, @First Player player){
        if (player.hasPermission("actus.sign.color")){
            SignData signData = event.getText();
            if (signData.getValue(Keys.SIGN_LINES).isPresent()){
                String line0 = signData.getValue(Keys.SIGN_LINES).get().get(0).toPlain();
                String line1 = signData.getValue(Keys.SIGN_LINES).get().get(1).toPlain();
                String line2 = signData.getValue(Keys.SIGN_LINES).get().get(2).toPlain();
                String line3 = signData.getValue(Keys.SIGN_LINES).get().get(3).toPlain();

                signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(0, MESSAGE(line0)));
                signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(1, MESSAGE(line1)));
                signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(2, MESSAGE(line2)));
                signData = signData.set(signData.getValue(Keys.SIGN_LINES).get().set(3, MESSAGE(line3)));                
            }
        }
    }
    
    @Listener
    public void OnPlayerAttack(DamageEntityEvent event, @First EntityDamageSource entity){
        if(event.getTargetEntity() instanceof Player){
            Player victim = (Player)event.getTargetEntity();
            if(mapCountDown.containsValue(victim)){
                CooldownToTP tp = mapCountDown.get(victim);
                tp.stopTP();
                mapCountDown.remove(victim);
                victim.sendMessage(ChatTypes.ACTION_BAR,MESSAGE("&l&e*** PVP : TP ANNULE ***"));
            }
            if(entity.getSource() instanceof Player){
                Player  striker = (Player)entity.getSource();
                CooldownToTP tp = mapCountDown.get(striker);
                tp.stopTP();
                mapCountDown.remove(striker);
                striker.sendMessage(ChatTypes.ACTION_BAR,MESSAGE("&l&e*** PVP : TP ANNULE ***"));
            }
        }
    }
    
    @Listener
    public void onInteractSign(InteractBlockEvent event){ 
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();

        BlockSnapshot b = event.getTargetBlock();
        if(!b.getLocation().isPresent()){return;}
        Location loc = b.getLocation().get();              
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
                        if (txt1.equals(MESSAGE("&l&1[?]"))){
                            String tag = Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain();
                            getGame().getServer().getConsole().sendMessage(MESSAGE(tag));
                            player.sendBookView(bookManager.getBookMessage(tag));
                            player.sendMessage(txt1);
                            //event.setCancelled(true);
                            //return;
                        }
                        
                        /*if (txt1.equals(MESSAGE("&l&1[CMD]"))){
                            String tag = Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain();
                            player.sendBookView(bookManager.getBookMessage(tag));
                            event.setCancelled(true);
                            return;
                        }*/
                    }
                } 
            }
        }
    }
    
    @Listener
    public void onTPwithPVP(DamageEntityEvent event){
        if(event.getTargetEntity() instanceof Player){
            Player player = (Player)event.getTargetEntity();
            if(mapCountDown.containsKey(player)){
                mapCountDown.get(player).stopTP();
                mapCountDown.remove(player);
                player.sendMessage(MESSAGE("&eCombat d\351tect\351: T\351l\351portation annul\351e"));
            }
        }  
    }
    
    @Listener
    public void onPlaceChest(ChangeBlockEvent.Place event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        if(block.getFinal().getState().getType().equals(CHEST)){
            Optional<Location> locChest = serverManager.locDblChest(loc);
            if(locChest.isPresent()){
                Optional<TileEntity> chest = locChest.get().getTileEntity();
                if(chest.get().get(Keys.DISPLAY_NAME).isPresent()){
                    String chestName = chest.get().get(Keys.DISPLAY_NAME).get().toPlain();
                    String players[] = chestName.split(" ");
                    if(!players[0].contains(player.getName())){
                        player.sendMessage(MESSAGE("&bImpossible, le coffre \351xistant ne t'appartient pas !"));
                        player.sendMessage(MESSAGE("&bDemande a &e" + players[0] + " &bpour placer ce coffre !"));
                        event.setCancelled(true);
                    }
                    String chestName1 = "&b" + player.getName();
                    Optional<TileEntity> chestBlock = loc.getTileEntity();
                    TileEntity tileChest = chestBlock.get();
                    tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                }
            }else{
                String chestName = "&b" + player.getName();
                Optional<TileEntity> chestBlock = loc.getTileEntity();
                TileEntity tileChest = chestBlock.get();
                tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
            }
            
        }
    }
}