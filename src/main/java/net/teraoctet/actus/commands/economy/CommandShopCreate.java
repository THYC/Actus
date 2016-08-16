package net.teraoctet.actus.commands.economy;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.inputShop;
import static net.teraoctet.actus.Actus.itemShopManager;
import net.teraoctet.actus.commands.CommandTest;
import net.teraoctet.actus.economy.ItemShop;
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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandShopCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.shop")) {
            Player player = (Player) src;
            Optional<String> locationString = ctx.<String> getOne("locationstring");
            Optional<String> transactType = ctx.<String> getOne("transacttype");
            Optional<Double> price = ctx.<Double> getOne("price");
            Optional<Integer> qte = ctx.<Integer> getOne("qte");
                        
            if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && locationString.isPresent() && !transactType.isPresent()){
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
                        .append(MESSAGE("&eNom de l'item : &b" + name + "\n"))
                        .append(MESSAGE("&eIndiquer le type de transaction :\n\n"))
                        .append(Text.builder().append(MESSAGE("&b- VENTE\n\n"))
                        .onClick(TextActions.runCommand("/shopcreate " + locationString.get() + " sale"))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour un item a la vente"))).build())
                        .append(Text.builder().append(MESSAGE("&b- ACHAT\n"))
                        .onClick(TextActions.runCommand("/shopcreate " + locationString.get() + " buy"))
                        .onHover(TextActions.showText(MESSAGE("&eClique ic pour un rachat d'item"))).build())
                        .build();
                player.sendMessage(text);
                return CommandResult.success();
            }else if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && locationString.isPresent() && transactType.isPresent() && !price.isPresent()){
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
                        inputShop.put(player, "shopcreate " + locationString.get() + " " + transactType.get());
                player.sendMessage(text);
                return CommandResult.success();
            }else if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent() && locationString.isPresent() && transactType.isPresent() && price.isPresent()){
                ItemStack is = player.getItemInHand(HandTypes.MAIN_HAND).get(); 
                ItemShop itemShop = new ItemShop(is,transactType.get(),price.get(),-1);
                try {
                    if(itemShopManager.saveShop(locationString.get(), itemShop)){

                        is.offer(Keys.DISPLAY_NAME, MESSAGE("&b" + transactType.get()+ ": &e" +  price.get() + " E"));
                        player.setItemInHand(HandTypes.MAIN_HAND, is);
                        player.sendMessage(MESSAGE("&6ItemShop cr\351\351 avec succ\350s"));
                        player.sendMessage(MESSAGE("&6Pose maintenant l'item dans le cadre"));
                        //Optional<Location<World>> loc = getLocation(locationString.get());
                        //ItemFrame frame = (ItemFrame)loc.get().getTileEntity().get();
                        //frame.get(Keys.REPRESENTED_ITEM).get().merge(ItemStackSnapshot.class.NONE);
                        //frame.getOrCreate(Keys.REPRESENTED_ITEM);
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
