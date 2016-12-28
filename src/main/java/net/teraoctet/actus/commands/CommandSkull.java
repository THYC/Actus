package net.teraoctet.actus.commands;

import java.util.Optional;
import net.teraoctet.actus.skin.MHF;
import net.teraoctet.actus.skin.MineSkin;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.profile.GameProfile;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;

public class CommandSkull implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("actus.head")) { 
            Player player = (Player) src;           
            Optional<String> head = ctx.<String> getOne("head");
                            
            if (head.isPresent()){
                
                MineSkin ms = new MineSkin(head.get());
                Optional<GameProfile> gp = ms.getGameProfile();
                if(gp.isPresent()){
                    ItemStack skull = createSkull(gp.get());
                    player.setItemInHand(HandTypes.MAIN_HAND,skull); 
                    return CommandResult.success();
                }else{
                    player.sendMessages(MESSAGE("&bAucun Skull ne correspond a ce nom !"));
                }
            } else {    
                MineSkin ms = new MineSkin(MHF.PIG.getNameMHF());
                Optional<GameProfile> gp = ms.getGameProfile();
                
                if(gp.isPresent()){
                    ItemStack skull = createSkull(gp.get()/*player.getProfile()*/);
                    player.setItemInHand(HandTypes.MAIN_HAND,skull);
                }
            }  
        } 
        
        else if (src instanceof ConsoleSource){
           src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }

        return CommandResult.empty();
    }
    
    public ItemStack createSkull(GameProfile profile) {
        ItemStack skull = ItemStack.of(ItemTypes.SKULL, 1);
        skull.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);
        skull.offer(Keys.REPRESENTED_PLAYER, profile);
        return skull;
    }
}