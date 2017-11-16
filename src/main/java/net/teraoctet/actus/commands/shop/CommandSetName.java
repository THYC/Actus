package net.teraoctet.actus.commands.shop;

import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
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
import org.spongepowered.api.data.type.HandTypes;

public class CommandSetName implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.setname")) {
            Player player = (Player) src;
            Optional<String> arguments = ctx.<String> getOne("arguments");
            String item;
            
            if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                
                // on récupère l'objet IteemStack contenu dans la main du joeur
                Optional<ItemStack> isOpt = player.getItemInHand(HandTypes.MAIN_HAND);
                if(isOpt.isPresent()){
                    ItemStack is = isOpt.get();
                    // on vérifie que le paramètre name a été renseigné sinon on prends la valeur itemType de l'objet ItemStack
                    if(arguments.isPresent()){
                        String[] args = arguments.get().split(" ");
                        String name = "";
                        for(int i = 0; i < args.length; i++){
                            name = name + args[i] + " ";
                        }
                        item = name;
                    }else{
                        item = "&4" + is.getItem().getName();
                    } 
                    
                    is.offer(Keys.DISPLAY_NAME, MESSAGE(item));

                    player.setItemInHand(HandTypes.MAIN_HAND,is);
                    player.sendMessage(MESSAGE("&6Le nom de votre item a \351t\351 changé\351 en : " + item));
                    return CommandResult.success();
                    
                }else{
                    player.sendMessage(MESSAGE("&6Vous devez avoir un item dans la main"));
                return CommandResult.empty();
                }
            }else{
                player.sendMessage(USAGE("/setname <name> |&8 vous devez avoir un item dan la main"));
                return CommandResult.empty();
            }
        }    
                   
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
       
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
