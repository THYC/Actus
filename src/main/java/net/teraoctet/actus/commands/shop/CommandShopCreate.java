package net.teraoctet.actus.commands.shop;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.inputShop;
import static net.teraoctet.actus.Actus.ism;
import net.teraoctet.actus.commands.CommandTest;
import net.teraoctet.actus.shop.ItemShop;
import static net.teraoctet.actus.utils.MessageManager.ERROR;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.SHOP_BUY;
import static net.teraoctet.actus.utils.MessageManager.SHOP_SALE;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.hanging.ItemFrame;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandShopCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.shop")) {
            Player player = (Player) src;
            Optional<String> uuid = ctx.<String> getOne("uuid");
            Optional<String> transactType = ctx.<String> getOne("transacttype");
            Optional<Double> price = ctx.<Double> getOne("price");
            Optional<Integer> qte = ctx.<Integer> getOne("qte");
                                    
            if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && uuid.isPresent() && !transactType.isPresent()){
                ItemStack is = player.getItemInHand(HandTypes.MAIN_HAND).get();
                String name = Text.builder(is.getTranslation()).build().toPlain();
                Optional<DisplayNameData> displayData = is.get(DisplayNameData.class);
                if(displayData.isPresent()){
                    name = displayData.get().displayName().get().toPlain();
                }
                int book = 0;
                if(book == 0){
                    Text text = Text.builder()
                            .append(MESSAGE("\n\n\n\n"))
                            .append(MESSAGE("&l&e   -----------------\n"))
                            .append(MESSAGE("&l&b   --- ItemShop ---\n"))
                            .append(MESSAGE("&l&e   -----------------\n\n"))
                            .append(MESSAGE("&eNom de l'item : &b" + name + "\n"))
                            .append(MESSAGE("&eIndiquer le type de transaction :\n\n"))
                            .append(Text.builder().append(MESSAGE("&b- VENTE\n\n"))
                            .onClick(TextActions.runCommand("/shopcreate " + uuid.get() + " sale"))
                            .onHover(TextActions.showText(MESSAGE("&eClique ici pour un item a la vente"))).build())
                            .append(Text.builder().append(MESSAGE("&b- ACHAT\n"))
                            .onClick(TextActions.runCommand("/shopcreate " + uuid.get() + " buy"))
                            .onHover(TextActions.showText(MESSAGE("&eClique ic pour un rachat d'item"))).build())
                            .build();
                    player.sendMessage(text);
                    
                } else {
                    Text text = Text.builder()
                            .append(MESSAGE("&l&8-----------------\n"))
                            .append(MESSAGE("&l&1--- ItemShop ---\n"))
                            .append(MESSAGE("&l&8-----------------\n\n"))
                            .append(MESSAGE("&1Nom de l'item :\n"))
                            .append(MESSAGE("&4 " + name + "\n"))
                            .append(MESSAGE("&1Type de transaction :\n\n"))
                            .append(Text.builder().append(MESSAGE("&4 - VENTE\n\n"))
                            .onClick(TextActions.runCommand("/shopcreate " + uuid.get() + " sale"))
                            .onHover(TextActions.showText(MESSAGE("&2Clique ici pour un item a la vente"))).build())
                            .append(Text.builder().append(MESSAGE("&4 - ACHAT\n"))
                            .onClick(TextActions.runCommand("/shopcreate " + uuid.get() + " buy"))
                            .onHover(TextActions.showText(MESSAGE("&2Clique ic pour un rachat d'item"))).build())
                            .build();

                    BookView bookView = BookView.builder()
                        .title(Text.of("ItemShop"))
                        .author(Text.of("Actus"))
                        .addPage(text)
                        .build();
                    player.sendBookView(bookView);
                }
                return CommandResult.success();
            }else if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && uuid.isPresent() && transactType.isPresent() && !price.isPresent()){
                ItemStack is = player.getItemInHand(HandTypes.MAIN_HAND).get();
                String name = Text.builder(is.getTranslation()).build().toPlain();
                Optional<DisplayNameData> displayData = is.get(DisplayNameData.class);
                if(displayData.isPresent()){
                    name = displayData.get().displayName().get().toPlain();
                }
                Text text = Text.builder()
                        .append(MESSAGE("\n\n\n\n"))
                        .append(MESSAGE("&l&e   -----------------\n"))
                        .append(MESSAGE("&l&b   ---- ItemShop ----\n"))
                        .append(MESSAGE("&l&e   -----------------\n\n"))
                        .append(MESSAGE("&eNom de l'item : &l&b" + name +"\n"))
                        .append(MESSAGE("&eTransaction : &l&b" + transactType.get() +"\n\n"))
                        .append(MESSAGE("&eMaintenant tape le prix souhait\351\n"))
                        .build();
                        inputShop.put(player.getIdentifier(), "shopcreate " + uuid.get() + " " + transactType.get());
                player.sendMessage(text);
                return CommandResult.success();
            }else if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && uuid.isPresent() && transactType.isPresent() && price.isPresent()){
                ItemStack is = player.getItemInHand(HandTypes.MAIN_HAND).get(); 
                ItemShop itemShop = new ItemShop(is,transactType.get(),price.get(),-1);
                try {
                    if(ism.saveShop(UUID.fromString(uuid.get()), itemShop)){
                        Text transact;
                        if(transactType.get().equalsIgnoreCase("sale")){
                            transact = SHOP_SALE();
                        }else{
                            transact = SHOP_BUY();
                        }
                        is.offer(Keys.DISPLAY_NAME, transact.concat(MESSAGE("&e" +  price.get())));
                        player.setItemInHand(HandTypes.MAIN_HAND, is);
                        player.sendMessage(MESSAGE("&6ItemShop cr\351\351 avec succ\350s"));
                        Entity frame = player.getLocation().getExtent().getEntity(UUID.fromString(uuid.get())).get();
                        if(frame instanceof ItemFrame){
                            frame.offer(Keys.REPRESENTED_ITEM, is.createSnapshot()); 
                        }else{
                            ArmorStand armorStand = (ArmorStand)frame;
                            armorStand.offer(Keys.DISPLAY_NAME, is.get(Keys.DISPLAY_NAME).get());
                            armorStand.offer(Keys.ARMOR_STAND_HAS_BASE_PLATE, false);
                            armorStand.offer(Keys.HAS_GRAVITY, false);
                            armorStand.offer(Keys.CUSTOM_NAME_VISIBLE, true);
                                                        
                            if(is.getType().getName().toUpperCase().contains("CHESTPLATE")){
                                armorStand.offer(Keys.ARMOR_STAND_IS_SMALL, false);
                                armorStand.offer(Keys.INVISIBLE, false);
                                armorStand.setChestplate(is);
                            }else if(is.getType().getName().toUpperCase().contains("LEGGINGS")){
                                armorStand.offer(Keys.ARMOR_STAND_IS_SMALL, false);
                                armorStand.offer(Keys.INVISIBLE, false);
                                armorStand.setLeggings(is);
                            }else if(is.getType().getName().toUpperCase().contains("HELMET")){
                                armorStand.offer(Keys.ARMOR_STAND_IS_SMALL, false);
                                armorStand.offer(Keys.INVISIBLE, false);
                                armorStand.setHelmet(is);
                            }else if(is.getType().getName().toUpperCase().contains("BOOTS")){
                                armorStand.offer(Keys.ARMOR_STAND_IS_SMALL, false);
                                armorStand.offer(Keys.INVISIBLE, false);
                                armorStand.setBoots(is);
                            }else{
                                armorStand.setLocation(armorStand.getLocation());
                                armorStand.offer(Keys.INVISIBLE, false);
                                armorStand.offer(Keys.ARMOR_STAND_HAS_ARMS,true);
                                armorStand.offer(Keys.ARMOR_STAND_IS_SMALL, true);
                                armorStand.offer(Keys.RIGHT_ARM_ROTATION,Vector3d.from(-35,700, 8));
                                armorStand.setItemInHand(HandTypes.MAIN_HAND, is);
                            }   
                        }
                        player.setItemInHand(HandTypes.MAIN_HAND, null);
                        inputShop.remove(player.getIdentifier());
                        return CommandResult.success();   
                    } else {
                        player.sendMessage(ERROR());
                        return CommandResult.empty(); 
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CommandTest.class.getName()).log(Level.SEVERE, null, ex);
                    return CommandResult.empty();  
                }
            }else{
                player.sendMessage(MESSAGE("&6Vous devez avoir un item dans la main"));
                return CommandResult.empty();
            }       
                   
        }else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
       
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
