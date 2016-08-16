package net.teraoctet.actus.commands;

import java.util.Optional;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.DeSerialize.getLocation;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.actus.utils.SettingCompass;
import org.spongepowered.api.data.type.HandTypes;
import static org.spongepowered.api.item.ItemTypes.COMPASS;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandMagicCompass implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.magiccompass")) {
            Player player = (Player)src;
            
            Optional<String> direction = ctx.<String> getOne("direction");
            if(direction.isPresent()){
                Optional<String> nameOpt = ctx.<String> getOne("name");
                SettingCompass sc = new SettingCompass();
                String name = "";
                Optional<ItemStack> is = Optional.empty();
                
                switch(direction.get().toLowerCase()){
                    case "home":
                        if(nameOpt.isPresent()){
                            name = nameOpt.get();
                        }else{
                            name = "default";
                        }
                        is = sc.MagicCompass(player,"HOME:" + name);
                        break;
                    case "plot":
                        if(nameOpt.isPresent()){
                            name = nameOpt.get();
                            is = sc.MagicCompass(player,"PLOT:" + name);
                        }else{
                            player.sendMessage(MESSAGE("vous devez indiquer le nom de votre parcelle"));
                            player.sendMessage(USAGE("/mc plot NomDeLaParcelle"));
                            return CommandResult.empty();
                        }
                        break;
                    case "guild":
                        player.sendMessage(MESSAGE("Cette commande ne fonctionne pas encore sur guild"));
                        break;
                    case "grave":
                        name = "GRAVE";
                        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                        Optional<Location<World>> location = getLocation(aplayer.getLastdeath());
                        if(!location.isPresent()){
                            int x = location.get().getBlockX();
                            int y = location.get().getBlockY();
                            int z = location.get().getBlockZ();
                            
                            String loc = String.valueOf(x) + ":" + String.valueOf(y) + ":" + String.valueOf(z);
                            is = sc.MagicCompass(player,name,loc);
                        }else{
                            player.sendMessage(MESSAGE("&eAucun point enregistr\351 pour ta tombe"));
                            return CommandResult.empty();
                        }
                        break;
                    case "xyz":
                        if(nameOpt.isPresent()){
                            name = nameOpt.get();
                            Optional<Integer> x = ctx.<Integer> getOne("x");
                            Optional<Integer> y = ctx.<Integer> getOne("y");
                            Optional<Integer> z = ctx.<Integer> getOne("z");
                            if(x.isPresent() && y.isPresent() && z.isPresent()){
                                String loc = x.get().toString() + ":" + y.get().toString() + ":" + z.get().toString();
                                is = sc.MagicCompass(player,name,loc);
                            }
                            
                        }else{
                            player.sendMessage(MESSAGE("&evous devez indiquer un nom pour ce point"));
                            player.sendMessage(USAGE("/mc xyz UnNomDeVotreChoix <X> <Y< <Z>"));
                            return CommandResult.empty();
                        }
                        break;
                    default:
                        player.sendMessage(USAGE("/mc [Direction] [name] [X] [Y] [Z]"));
                        player.sendMessage(MESSAGE("&7Direction : HOME/PLOT/GUILD/GRAVE/XYZ"));
                        player.sendMessage(MESSAGE("&7Name : le nom de votre home, de votre parcelle ..."));
                        player.sendMessage(MESSAGE("&7X Y Z : uniquement si Direction = XYZ"));
                        player.sendMessage(MESSAGE("&7ex : /xyz maMine 100 30 -150"));
                        return CommandResult.empty();
                } 
                
                if(is.isPresent()){ 
                    if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                        if(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().equals(COMPASS)){
                            player.setItemInHand(HandTypes.MAIN_HAND, is.get()); 
                        }else{
                            player.sendMessage(MESSAGE("&eVous devez avoir une boussole dans votre main"));
                        }
                    }else{
                        if(src.hasPermission("actus.magiccompass.create")) {
                            player.setItemInHand(HandTypes.MAIN_HAND, is.get());
                        }
                    } 
                }
                return CommandResult.success();
            }else{
                player.sendMessage(USAGE("/mc [Direction] [name] [X] [Y] [Z]"));
                        player.sendMessage(MESSAGE("&7Direction : HOME/PLOT/GUILD/GRAVE/XYZ"));
                        player.sendMessage(MESSAGE("&7Name : le nom de votre home, de votre parcelle ..."));
                        player.sendMessage(MESSAGE("&7X Y Z : uniquement si Direction = XYZ"));
                        player.sendMessage(MESSAGE("&7ex : /xyz maMine 100 30 -150"));
                return CommandResult.empty();
            }
        } else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }       
        return CommandResult.empty();
    }
}
