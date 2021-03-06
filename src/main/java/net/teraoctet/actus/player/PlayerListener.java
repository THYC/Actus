package net.teraoctet.actus.player;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configBook;

import static net.teraoctet.actus.Actus.inputDouble;
import static net.teraoctet.actus.Actus.inputShop;
import static net.teraoctet.actus.Actus.mapCountDown;
import static net.teraoctet.actus.Actus.ptm;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.bookmessage.Book;
import static net.teraoctet.actus.player.PlayerManager.addAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayerName;
import net.teraoctet.actus.utils.CooldownToTP;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.player.PlayerManager.getUUID;
import static net.teraoctet.actus.player.PlayerManager.removeAPlayer;
import static net.teraoctet.actus.player.PlayerManager.removeUUID;
import static net.teraoctet.actus.utils.Config.AUTO_LOCKCHEST;
import static net.teraoctet.actus.utils.Config.ENABLE_LOCKCHEST;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import static net.teraoctet.actus.utils.MessageManager.CHEST_LOCK;
import static net.teraoctet.actus.utils.MessageManager.CLICK_TO_CONFIRM;
import net.teraoctet.actus.utils.SettingCompass;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
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
import net.teraoctet.lightperm.api.Manager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.block.BlockTypes.CHEST;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.filter.type.Exclude;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.WRITABLE_BOOK;
import static org.spongepowered.api.item.ItemTypes.WRITTEN_BOOK;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.util.Tristate;

public class PlayerListener {
    
    public static ArrayList<Inventory> inventorys = new ArrayList<>();
    public PlayerListener() {}
        
    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event, @First User user){
        Player player = user.getPlayer().get();
        getGame().getServer().getBroadcastChannel().send(EVENT_LOGIN_MESSAGE(player));
        APlayer aplayer = getAPlayer(player.getIdentifier());
        
        if(aplayer == null) {
            getGame().getServer().getBroadcastChannel().send(FIRSTJOIN_BROADCAST_MESSAGE(player));
        }
    }
    
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @First Player player){
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
        
        if(ptm.getListPlot(player.getIdentifier()).isPresent()){
            if(Manager.getPlayer(player.getUniqueId()).isPresent()){
                if(Manager.getPlayer(player.getUniqueId()).get().getGroup().getName().equalsIgnoreCase("vagabon")){
                    player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "group.citoyen", Tristate.TRUE);
                    Manager.setRank(player, "citoyen");
                }
            }
        }
    }
    
    @Listener
    public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event, @First Player player){
        APlayer aplayer = getAPlayer(player.getIdentifier());
        long timeConnect = sm.dateToLong()- PlayerManager.getFirstTime(player.getIdentifier());
        long onlineTime = (long)aplayer.getOnlinetime() + timeConnect;
        PlayerManager.removeFirstTime(player.getIdentifier());
        aplayer.setLastonline(sm.dateToLong());
        aplayer.setOnlinetime(onlineTime);
        aplayer.update();    
        event.setMessage(EVENT_DISCONNECT_MESSAGE(player));
    }
        
    @Listener
    public void onSendCommand(final SendCommandEvent event, @First Player player){
        if(!event.getCommand().contains("sponge:callback")){
            StringBuilder builder = new StringBuilder();
            builder.append(player.getName());
            Location<World> loc = player.getLocation();
            builder.append(String.format(" (%s @ %d %d %d) ", loc.getExtent().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));    
            builder.append(": /").append(event.getCommand()).append(" ").append(event.getArguments());
            getGame().getServer().getConsole().sendMessage(Text.of(builder.toString()));
        }
    }
    
    @Listener
    public void promptDouble(MessageChannelEvent.Chat event, @First Player player) {
        if(inputDouble.containsKey(player.getIdentifier())){
            if(inputDouble.get(player.getIdentifier()) > 0d)return;
            
            String smessage = event.getOriginalMessage().toPlain();
            smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
            Scanner scanner = new Scanner(smessage);
            
            if(scanner.hasNextDouble()){
                double d = scanner.nextDouble();
                if(d==0){
                    inputDouble.remove(player.getIdentifier());
                    player.sendMessage(MESSAGE("&bL'action a \351t\351 annul\351"));
                    event.clearMessage();
                    return;
                }
                inputDouble.replace(player.getIdentifier(), d);
                player.sendMessage(CLICK_TO_CONFIRM()
                    .concat(MESSAGE("&esi tu tiens ta bourse dans ta main, la somme sera vers\351 dessus sinon tu aura des \351meraudes")));
            }else{
                
                player.sendMessage(MESSAGE("&bTapes uniquement des chiffres ! recommences :")
                .concat(MESSAGE("&bTapes 0 pour annuler la transaction")));
            }
            event.clearMessage();
            return;
        }
        if(inputShop.containsKey(player.getIdentifier())){
            String smessage = event.getOriginalMessage().toPlain();
            smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
            Scanner scanner = new Scanner(smessage);
            
            if(scanner.hasNextDouble()){
                double d = scanner.nextDouble();
                if(d==0){
                    inputShop.remove(player.getIdentifier());
                    player.sendMessage(MESSAGE("&bL'action a \351t\351 annul\351"));
                    event.clearMessage();
                    return;
                }
                Sponge.getCommandManager().process(player, inputShop.get(player.getIdentifier()) + " " + String.valueOf(d));
            }else{                
                player.sendMessage(MESSAGE("&bTapes uniquement des chiffres ! recommences : ")
                .concat(MESSAGE("&bTapes 0 pour annuler")));
            }
            event.clearMessage();
            return;
        }
        
        String smessage = event.getMessage().toPlain();
        smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
        Text message = MESSAGE(Permissions.getPrefix(player) + "&l&7" + player.getName() + ": &r&7" + smessage + Permissions.getSuffix(player));
        Text prefixWorld = MESSAGE(WorldManager.getWorld(player.getWorld().getName()).getPrefix()) ;
        Text newMessage = Text.builder().append(prefixWorld).append(message).build();
        event.setMessage(newMessage);
    }
    
    @Listener
    public void onInteractChest(InteractInventoryEvent.Open event, @First Player player){
        if(!event.getTargetInventory().getArchetype().getClass().equals(Chest.class))return;
        if(ENABLE_LOCKCHEST()){
            APlayer aplayer = getAPlayer(player.getIdentifier());
            if(!event.getTargetInventory().getName().get().contains(player.getName()) && 
                    !event.getTargetInventory().getName().get().contains("[+]") && 
                    !event.getTargetInventory().getName().get().contains("Chest") &&
                    !event.getTargetInventory().getName().get().contains("TROC") &&
                    aplayer.getLevel() != LEVEL_ADMIN()){
                player.sendMessage(CHEST_LOCK());
                event.setCancelled(true);     
            }
        }
    }
    
    @Listener
    public void onBreakChest(final ChangeBlockEvent.Break.Pre event, @Root Player player) {
        if(!event.getLocations().get(0).getBlock().getType().equals(CHEST))return;
        if(ENABLE_LOCKCHEST()){   
            if(event.getLocations().get(0).getTileEntity().isPresent()){
                Location<World> loc = event.getLocations().get(0);
                TileEntity chest = loc.getTileEntity().get();

                if(chest.get(Keys.DISPLAY_NAME).isPresent()){
                    String name = chest.get(Keys.DISPLAY_NAME).get().toPlain();         
                    if(!name.contains(player.getName()) && !name.contains("[+]") && !name.contains("TROC")){
                        player.sendMessage(CHEST_LOCK());
                        event.setCancelled(true);  
                    }
                }
            }
        }
    }
    
    @Listener
    @Exclude(InteractBlockEvent.Primary.class)
    public void onCompassInteract(InteractBlockEvent event, @First Player player) {
        Optional<ItemStack> is = player.getItemInHand(HandTypes.MAIN_HAND);
                
        if (is.isPresent()) {          
            if(is.get().getType().equals(COMPASS)){   
                SettingCompass sc = new SettingCompass();
                                        
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
                                    if(!ptm.hasPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain())){
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
            
            //if(victim.get(Keys.HEALTH).get() == 0d){
                //victim.sendMessage(ChatTypes.ACTION_BAR,MESSAGE("&l&e*** T mort ***"));
            //}
        }
    }
    
    @Listener
    @Exclude(InteractBlockEvent.Primary.class)
    public void onInteractSign(InteractBlockEvent event, @First Player player){     
        BlockSnapshot b = event.getTargetBlock();
        if(!b.getLocation().isPresent()){return;}
        Location loc = b.getLocation().get();              
        Optional<TileEntity> block = loc.getTileEntity();
        if (block.isPresent()) {
            TileEntity tile=block.get();
            if (tile instanceof Sign) {
                Sign sign=(Sign)tile;
                Optional<SignData> optional=sign.getOrCreate(SignData.class);
                if (optional.isPresent()) {
                    SignData offering = optional.get();
                    Text txt1 = offering.lines().get(0);

                    if (txt1.equals(MESSAGE("&l&1[?]"))){
                        try {
                            String tag = Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain();
                            Optional<Book> book = configBook.load(tag);
                            if(book.isPresent()){
                                player.sendBookView(book.get().getBookView());
                                return;
                            }
                        } catch (ObjectMappingException | IOException ex) {
                            Logger.getLogger(PlayerListener.class.getName()).log(Level.SEVERE, null, ex);
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
                            return;
                        }
                    }
                        
                    if (txt1.toPlain().contains("[TROC]")){
                        try {
                            Optional<Book> book = configBook.load("TROC");
                            if(book.isPresent()){
                                player.sendBookView(book.get().getBookView());
                                return;
                            }
                        } catch (ObjectMappingException | IOException ex) {
                            Logger.getLogger(PlayerListener.class.getName()).log(Level.SEVERE, null, ex);
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
                                if(is.get().getType().equals(WRITTEN_BOOK) || is.get().getType().equals(WRITABLE_BOOK)){
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
                                    if(is.get().getType().equals(WRITTEN_BOOK)){
                                        Book book = new Book();
                                        if(is.get().get(Keys.BOOK_AUTHOR).isPresent()){
                                            book.setAuthor(is.get().get(Keys.BOOK_AUTHOR).get());
                                            dest = is.get().get(Keys.DISPLAY_NAME).get().toPlain();

                                            Optional<ProviderRegistration<UserStorageService>> optprov = Sponge.getServiceManager().getRegistration(UserStorageService.class);

                                            if(optprov.isPresent()) {
                                                ProviderRegistration<UserStorageService> provreg = optprov.get();
                                                UserStorageService uss = provreg.getProvider();

                                                Optional<User> usr = uss.get(dest);
                                                if(!usr.isPresent()) {
                                                    dest = player.getName();
                                                    player.sendMessage(MESSAGE("&dErreur sur le nom du destinataire"));
                                                    player.sendMessage(MESSAGE("&dTu recevra le courrier dans ta boite"));
                                                }
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
    
    @Listener
    public void onTPwithPVP(DamageEntityEvent event){
        if(event.getTargetEntity() instanceof Player){
            Player player = (Player)event.getTargetEntity();
            if(mapCountDown.containsKey(player.getIdentifier())){
                mapCountDown.get(player.getIdentifier()).stopTP();
                mapCountDown.remove(player.getIdentifier());
                player.sendMessage(MESSAGE("&eCombat d\351tect\351: T\351l\351portation annul\351e"));
            }
        }  
    }
                    
    @Listener
    public void onPlaceChest(ChangeBlockEvent.Place event, @First Player player){      
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
                if(AUTO_LOCKCHEST()){
                    String chestName = "&b" + player.getName();
                    Optional<TileEntity> chestBlock = loc.getTileEntity();
                    TileEntity tileChest = chestBlock.get();
                    tileChest.offer(Keys.DISPLAY_NAME, MESSAGE(chestName));
                }
            }
            
        }
    }
    
    //@Listener
    //public void onRespawnPlayer(RespawnPlayerEvent event, @First Player player) {
        
        //AInventory invFrom = new AInventory(player, event.getFromTransform().getExtent().getName());
        //AInventory invTO;
        //configInv.save(invFrom);
        
        //if(configInv.load(player, event.getToTransform().getExtent().getName()).isPresent()){
            //invTO = configInv.load(player, event.getToTransform().getExtent().getName()).get();
            //invTO.set();
        //}
        
        //player.getOrCreate(PotionEffectData.class).ifPresent(potionEffectData -> {
            //PotionEffectData data = potionEffectData.addElement(PotionEffect.of(PotionEffectTypes.INVISIBILITY, 1, 2));
            //DataTransactionResult result = player.offer(data);
        //});
    //}
    
    //@Listener
    //public void onPlayerAchievement(ChangeStatisticEvent.TargetPlayer event, @First Player player){
        //plugin.getLogger().info(event.getTargetEntity().getName() + " : " + event.getStatistic().getType().getId());
        //if(event.getStatistic().getType().equals(StatisticTypes.ITEMS_CRAFTED)){
            //player.sendMessage(MESSAGE("&3 : " + player.getStatisticData().get(Statistics.ITEMS_CRAFTED)));
        //}
        //player.sendMessage(MESSAGE("&3appel villageois : " + player.getStatisticData().get(StatisticTypes.ITEMS_DROPPED)));
    //}
        
    //@Listener
    //public void onPlayerAchievement(GrantAchievementEvent.TargetPlayer e){
        //plugin.getLogger().info("DEBUG");
    //}
    
}