package net.teraoctet.actus.player;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import static net.teraoctet.actus.Actus.configBook;
import static net.teraoctet.actus.Actus.configInv;

import static net.teraoctet.actus.Actus.inputDouble;
import static net.teraoctet.actus.Actus.inputShop;
import static net.teraoctet.actus.Actus.mapCountDown;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.bookmessage.Book;
import net.teraoctet.actus.inventory.AInventory;
import static net.teraoctet.actus.player.PlayerManager.addAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayerName;
import net.teraoctet.actus.utils.CooldownToTP;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.player.PlayerManager.getUUID;
import static net.teraoctet.actus.player.PlayerManager.removeAPlayer;
import static net.teraoctet.actus.player.PlayerManager.removeUUID;
import static net.teraoctet.actus.utils.MessageManager.CHEST_LOCK;
import static net.teraoctet.actus.utils.MessageManager.CLICK_TO_CONFIRM;
import net.teraoctet.actus.utils.SettingCompass;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
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
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import static org.spongepowered.api.item.ItemTypes.COMPASS;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.data.type.HandTypes;

import static net.teraoctet.actus.utils.MessageManager.FIRSTJOIN_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.JOIN_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.EVENT_DISCONNECT_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NAME_CHANGE;
import static net.teraoctet.actus.utils.MessageManager.FIRSTJOIN_BROADCAST_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.EVENT_LOGIN_MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.LAST_CONNECT;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import net.teraoctet.actus.utils.Permissions;
import net.teraoctet.actus.world.WorldManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.block.BlockTypes.CHEST;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.WRITABLE_BOOK;
import static org.spongepowered.api.item.ItemTypes.WRITTEN_BOOK;

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
            aplayer = new APlayer(uuid, 0, name, "", 0, "", 20, "", "", 0, sm.dateToLong(),"N",0,0,0);
            aplayer.insert();
            commit();
            player.sendMessage(FIRSTJOIN_MESSAGE(player)); 
        } else {
            addAPlayer(aplayer.getUUID(), aplayer);
            player.sendMessage(JOIN_MESSAGE(player));
            player.sendMessage(LAST_CONNECT(sm.longToDateString(aplayer.getLastonline())));
        }
	PlayerManager.addFirstTime(player.getIdentifier(), sm.dateToLong());
        APlayer player_uuid = getAPlayer(uuid);
        
        if(player_uuid != null && getUUID(name) == null) {
            getGame().getServer().getBroadcastChannel().send(NAME_CHANGE(player_uuid.getName(),player.getName()));
            removeAPlayer(player_uuid.getUUID());
            removeUUID(player_uuid.getName());
            player_uuid.setName(name);
            player_uuid.update();
            commit();
        }
        if(configBook.getCountMessageBook(player) > 0)configBook.OpenListBookMessage(player);
    }
    
    @Listener
    public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event) {
        Player player = (Player) event.getTargetEntity();
        APlayer aplayer = getAPlayer(player.getIdentifier());
        long timeConnect = sm.dateToLong()- PlayerManager.getFirstTime(player.getIdentifier());
        long onlineTime = (long)aplayer.getOnlinetime() + timeConnect;
        PlayerManager.removeFirstTime(player.getIdentifier());
        aplayer.setLastonline(sm.dateToLong());
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
        }else if(event.getCause().first(CommandBlockSource.class).isPresent()) {
            builder.append("command block");
        }
        Optional.of(builder.append(": /").append(event.getCommand()).append(" ").append(event.getArguments()));
        getGame().getServer().getConsole().sendMessage(Text.of(builder.toString()));
    }
    
    @Listener
    public void promptDouble(MessageChannelEvent.Chat event, @First Player player) {
        if(inputDouble.containsKey(player)){
            if(inputDouble.get(player) > 0d)return;
            String smessage = event.getOriginalMessage().toPlain();
            smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
            Scanner scanner = new Scanner(smessage);
            
            if(scanner.hasNextDouble()){
                double d = scanner.nextDouble();
                if(d==0){
                    inputDouble.remove(player);
                    player.sendMessage(MESSAGE("&bL'action a \351t\351 annul\351"));
                    event.clearMessage();
                    return;
                }
                inputDouble.replace(player, d);
                player.sendMessage(CLICK_TO_CONFIRM()
                    .concat(MESSAGE("&esi tu tiens ta bourse dans ta main, la somme sera vers\351 dessus sinon tu aura des \351meraudes")));
                event.clearMessage();
            }else{
                
                player.sendMessage(MESSAGE("&bTapes uniquement des chiffres ! recommences :")
                .concat(MESSAGE("&bTapes 0 pour annuler la transaction")));
            }
        }
        if(inputShop.containsKey(player)){
            String smessage = event.getOriginalMessage().toPlain();
            smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
            Scanner scanner = new Scanner(smessage);
            
            if(scanner.hasNextDouble()){
                double d = scanner.nextDouble();
                if(d==0){
                    inputShop.remove(player);
                    player.sendMessage(MESSAGE("&bL'action a \351t\351 annul\351"));
                    event.clearMessage();
                    return;
                }
                Sponge.getCommandManager().process(player, inputShop.get(player) + " " + String.valueOf(d));
                event.clearMessage();
            }else{                
                player.sendMessage(MESSAGE("&bTapes uniquement des chiffres ! recommences :")
                .concat(MESSAGE("&bTapes 0 pour annuler")));
            }
        }
    }
    
    @Listener
    public void onMessage(MessageChannelEvent.Chat event, @First Player player) {
        String smessage = event.getMessage().toPlain();
        smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
        Text message = MESSAGE(Permissions.getPrefix(player) + "&a[" + player.getName() + "] &7" + smessage + Permissions.getSuffix(player));
        Text prefixWorld = MESSAGE(WorldManager.getWorld(player.getWorld().getName()).getPrefix()) ;
        Text newMessage = Text.builder().append(prefixWorld).append(message).build();
        event.setMessage(newMessage);
    }
                    
    @Listener
    public void onInteractChest(InteractBlockEvent event, @First Player player) {
        BlockSnapshot b = event.getTargetBlock();                   
        if(b.get(Keys.DISPLAY_NAME).isPresent()){
            Optional<Text> displayName = b.get(Keys.DISPLAY_NAME);
            APlayer aplayer = getAPlayer(player.getIdentifier());
            if(!displayName.get().toPlain().contains(player.getName()) && !displayName.get().toPlain().contains("[+]") && aplayer.getLevel() != 10){
                player.sendMessage(CHEST_LOCK());
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
    @SuppressWarnings("UnusedAssignment")
    public void onColorSign(ChangeSignEvent event, @First Player player){
        if (player.hasPermission("actus.fun.sign.color")){
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
    @SuppressWarnings("element-type-mismatch")
    public void OnPlayerAttack(DamageEntityEvent event){
        if(event.getTargetEntity() instanceof Player){
            Player victim = (Player)event.getTargetEntity();
            if(mapCountDown.containsValue(victim)){
                CooldownToTP tp = mapCountDown.get(victim);
                tp.stopTP();
                mapCountDown.remove(victim);
                victim.sendMessage(ChatTypes.ACTION_BAR,MESSAGE("&l&e*** tu recois des d\351gats, ta demande de TP est annul\351 ***"));
            }
            
            Optional<EntityDamageSource> entityDamage = event.getCause().first(EntityDamageSource.class);
            if(entityDamage.isPresent()){
                if(entityDamage.get().getSource() instanceof Player){
                    Player  striker = (Player)entityDamage.get().getSource();
                    if(mapCountDown.containsValue(striker)){
                        CooldownToTP tp = mapCountDown.get(striker);
                        tp.stopTP();
                        mapCountDown.remove(striker);
                        striker.sendMessage(ChatTypes.ACTION_BAR,MESSAGE("&l&e*** Combat d\351tect\351, ta demande de TP est annul\351 "));
                    }
                }
            }
            
            if(victim.get(Keys.HEALTH).get() == 0d){
                victim.sendMessage(ChatTypes.ACTION_BAR,MESSAGE("&l&e*** T mort ***"));
            }
        }
    }
    
    @Listener
    public void onInteractSign(InteractBlockEvent event) throws ObjectMappingException, IOException{ 
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
                            Optional<Book> book = configBook.load(tag);
                            if(book.isPresent()){
                                player.sendBookView(book.get().getBookView());
                            }
                        }
                        
                        if (txt1.equals(MESSAGE("&l&1[CMD]"))){
                            String tag = Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain();
                            CommandManager cmdService = Sponge.getGame().getCommandManager();
                            String[] args = tag.split(" ");                          
                            if (player.hasPermission("actus.use.commandsign")){
                                String command = args[0];
                                if(args.length > 1)command = command + " " + args[1];
                                if(args.length > 2)command = command + " " + args[2];
                                if(args.length > 3)command = command + " " + args[3]; 
                                cmdService.process(player, command);
                            }
                        }
                        
                        if (txt1.equals(MESSAGE("&l&8[POST]"))){
                            String dest = Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain();
                            Optional<APlayer> aplayer = getAPlayerName(dest);
                            ItemStack writableBook = ItemStack.builder().itemType(ItemTypes.WRITABLE_BOOK).build();
                            if(aplayer.isPresent()){
                                Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
                                if(is.isPresent()){
                                    if(dest.equalsIgnoreCase(player.getName())){
                                        configBook.OpenListBookMessage(player);
                                        return;
                                    }
                                    if(is.get().getItem().equals(WRITTEN_BOOK) || is.get().getItem().equals(WRITABLE_BOOK)){
                                        Book book = new Book();
                                        if(is.get().get(Keys.BOOK_AUTHOR).isPresent()){
                                            book.setAuthor(is.get().get(Keys.BOOK_AUTHOR).get());
                                        }else{
                                            book.setAuthor(MESSAGE(player.getName()));
                                        }
         
                                        book.setTitle(MESSAGE(dest + "_" + player.getName() + "_" + sm.dateShortToString()));
                                        String tmp;
                                        List<Text> pages = new ArrayList();
                                        if(!is.get().get(Keys.BOOK_PAGES).isPresent()){
                                            player.sendMessage(MESSAGE("&dEnvoi impossible, ton livre ne contient aucun message !"));
                                            return;
                                        }
                                        for(Text text : is.get().get(Keys.BOOK_PAGES).get()){
                                            tmp = text.toString();
                                            tmp = tmp.replace("\\\\", "\\");
                                            tmp = tmp.replace("Text{{", "");
                                            tmp = tmp.replace("Text{", "");
                                            tmp = tmp.replace("}}", "");
                                            tmp = tmp.replace("}", "");
                                            tmp = tmp.replace("\"", "");
                                            tmp = tmp.replace("text:", "");
                                            tmp = tmp.replace("\\n", "\n");
                                            pages.add(MESSAGE(tmp));
                                        }
                                        book.setPages(pages);
                                        configBook.saveBook(book);
                                        player.sendMessage(MESSAGE("&dTa lettre a bien ete poste !"));
                                        player.setItemInHand(HandTypes.MAIN_HAND, writableBook);
                                    }else{
                                        player.sendMessage(MESSAGE("&dTu dois ecrire ton message sur un livre a ecrire et le tenir dans ta main"));
                                    }
                                }else{
                                    if(dest.equalsIgnoreCase(player.getName())){
                                        configBook.OpenListBookMessage(player);
                                    }
                                }
                            }else{
                                if(dest.equalsIgnoreCase("[======]")){
                                    Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
                                    if(is.isPresent()){
                                        if(is.get().getItem().equals(WRITTEN_BOOK)){
                                            Book book = new Book();
                                            if(is.get().get(Keys.BOOK_AUTHOR).isPresent()){
                                                book.setAuthor(is.get().get(Keys.BOOK_AUTHOR).get());
                                                dest = is.get().get(Keys.DISPLAY_NAME).get().toPlain();
                                                aplayer = getAPlayerName(dest);
                                                if(!aplayer.isPresent()){
                                                    dest = player.getName();
                                                    player.sendMessage(MESSAGE("&dErreur sur le nom du destinataire"));
                                                    player.sendMessage(MESSAGE("&dTu recevra le courrier dans ta boite"));
                                                }
                                            }else{
                                                player.sendMessage(MESSAGE("&dEnvoi impossible !\n" +
                                                        "tu dois signer ton livre en mettant le &lnom&r \n" +
                                                        "du destinataire sur &lle titre du livre !"));
                                            }
                                            
                                            book.setTitle(MESSAGE(dest + "_" + player.getName() + "_" + sm.dateShortToString()));
                                            String tmp;
                                            List<Text> pages = new ArrayList();
                                            if(!is.get().get(Keys.BOOK_PAGES).isPresent()){
                                                player.sendMessage(MESSAGE("&dEnvoi impossible, ton livre ne contient aucun message !"));
                                                return;
                                            }
                                            for(Text text : is.get().get(Keys.BOOK_PAGES).get()){
                                                tmp = text.toString();
                                                tmp = tmp.replace("\\\\", "\\");
                                                tmp = tmp.replace("Text{{", "");
                                                tmp = tmp.replace("Text{", "");
                                                tmp = tmp.replace("}}", "");
                                                tmp = tmp.replace("}", "");
                                                tmp = tmp.replace("\"", "");
                                                tmp = tmp.replace("text:", "");
                                                tmp = tmp.replace("\\n", "\n");
                                                pages.add(MESSAGE(tmp));
                                            }
                                            book.setPages(pages);
                                            configBook.saveBook(book);
                                            player.sendMessage(MESSAGE("&eTa lettre a bien ete poste !"));
                                            player.setItemInHand(HandTypes.MAIN_HAND, writableBook);
                                        }else{
                                            player.sendMessage(MESSAGE("&dTu dois ecrire ton message sur un livre a ecrire et\n" +
                                                    "le tenir dans ta main, puis le signer en mettant dans le titre\n" +
                                                    "le nom du destinataire"));
                                        }
                                    }else{
                                        if(dest.equalsIgnoreCase(player.getName())){
                                            configBook.OpenListBookMessage(player);
                                        }
                                    }
                                }else{
                                    List<Text> help = new ArrayList<>();
                                    help.add(MESSAGE("&l&8[POST]"));
                                    help.add(MESSAGE("&o&8Boite aux lettres de :"));
                                    help.add(MESSAGE("&l&4" + player.getName()));
                                    help.add(MESSAGE("&8Clique droit ici"));
                                    offering.set(Keys.SIGN_LINES,help );
                                    sign.offer(offering);
                                    player.sendMessage(MESSAGE("&eCette boite aux lettres est maintenant à ton nom"));
                            
                                }
                            }
                        }
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
            Optional<Location> locChest = sm.locDblChest(loc);
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
                    Optional<TileEntity> chestBlock = loc.getTileEntity();
                    TileEntity tileChest = chestBlock.get();
                    tileChest.offer(Keys.DISPLAY_NAME, chest.get().get(Keys.DISPLAY_NAME).get());
                }
            }else{
                String chestName = "&b" + player.getName();
                Optional<TileEntity> chestBlock = loc.getTileEntity();
                TileEntity tileChest = chestBlock.get();
                tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
            }
            
        }
    }
    
    @Listener
    public void onRespawnPlayer(RespawnPlayerEvent event, @First Player player) {
        
        AInventory invFrom = new AInventory(player, event.getFromTransform().getExtent().getName());
        AInventory invTO;//= new AInventory(player, event.getToTransform().getExtent().getName());
        configInv.save(invFrom);
        
        if(configInv.load(player, event.getToTransform().getExtent().getName()).isPresent()){
            invTO = configInv.load(player, event.getToTransform().getExtent().getName()).get();
            invTO.set();
        }
        
        player.getOrCreate(PotionEffectData.class).ifPresent(potionEffectData -> {
            PotionEffectData data = potionEffectData.addElement(PotionEffect.of(PotionEffectTypes.INVISIBILITY, 1, 2));
            DataTransactionResult result = player.offer(data);
        });
    }
}