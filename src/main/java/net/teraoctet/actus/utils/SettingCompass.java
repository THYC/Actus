package net.teraoctet.actus.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.ptm;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.DeSerialize.getVector3d;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;

public class SettingCompass {
    
    public Optional<Vector3d> getLookLocation(ItemStack magicCompass){
        if(magicCompass.getOrCreate(LoreData.class).isPresent()){
            LoreData loreMCompass = magicCompass.getOrCreate(LoreData.class).get();
            if(loreMCompass.get(3).isPresent()){
                Vector3d location = getVector3d(loreMCompass.get(3).get().toPlain());
                return Optional.of(location);
            }
        }
        return Optional.empty();
    }
    
    public Optional<ItemStack> MagicCompass(Player player){
        APlayer aplayer = getAPlayer(player.getIdentifier());
        ItemStack magicCompass = ItemStack.builder().itemType(ItemTypes.COMPASS).build();
	LoreData loreMCompass = magicCompass.getOrCreate(LoreData.class).get();
        magicCompass.offer(Keys.DISPLAY_NAME, MESSAGE("&4Magic &5Compass"));
                 
	List<Text> newLore = loreMCompass.lore().get();
        newLore.add(MESSAGE("&l&n&2Owner: &e" + player.getName()));
        newLore.add(MESSAGE("&8 Fait un clik droit pour prendre la direction"));
        newLore.add(MESSAGE("&4Direction: GRAVE "));
        newLore.add(MESSAGE("&8" + aplayer.getLastdeath()));
	DataTransactionResult dataTransactionResult = magicCompass.offer(Keys.ITEM_LORE, newLore);

	if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(magicCompass);
        } else {
            return Optional.empty();
        }				
    }
    
    public Optional<ItemStack> MagicCompass(Player player, String direction){
        APlayer aplayer = getAPlayer(player.getIdentifier());
        ItemStack magicCompass = ItemStack.builder().itemType(ItemTypes.COMPASS).build();
	LoreData loreMCompass = magicCompass.getOrCreate(LoreData.class).get();
        String arg[] = direction.split(":");
        magicCompass.offer(Keys.DISPLAY_NAME, MESSAGE("&4Magic &5Compass &e" + arg[0]));
        String loc = "";
        switch(arg[0]){
            case "HOME":
                String homeName = "default";
                if(arg.length == 2){ homeName = arg[1];}
                if(aplayer.getHome(homeName).isPresent()){
                loc =   String.valueOf(aplayer.getHome(homeName).get().getX()) + ":" +
                        String.valueOf(aplayer.getHome(homeName).get().getY()) + ":" +
                        String.valueOf(aplayer.getHome(homeName).get().getZ());
                }else{
                    player.sendMessage(HOME_NOT_FOUND());
                    return Optional.empty();
                }
                break;
            case "GRAVE":
                loc = aplayer.getLastdeath();
                break;
            case "PLOT": 
                if(arg.length == 1){return Optional.empty();}
                Optional<Plot> plot = ptm.getPlot(arg[1]);
                if(plot.isPresent()){
                    loc = DeSerialize.location(plot.get().getSpawnPlot().get());
                }else{
                    player.sendMessage(MESSAGE("&eParcelle inexistante"));
                    return Optional.empty();
                }   
                break;
            default:
            
        }
	List<Text> newLore = loreMCompass.lore().get();
        newLore.add(MESSAGE("&l&n&2Owner: &e" + player.getName()));
        newLore.add(MESSAGE("&8 Fait un clik droit voir prendre la direction"));
        newLore.add(MESSAGE("&4Direction:&8 " + arg[0] + " " + arg[1] ));
        newLore.add(MESSAGE("&8" + loc));
	DataTransactionResult dataTransactionResult = magicCompass.offer(Keys.ITEM_LORE, newLore);

	if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(magicCompass);
        } else {
            return Optional.empty();
        }				
    }
    
    public Optional<ItemStack> MagicCompass(Player player, String direction, String locationString){
        ItemStack magicCompass = ItemStack.builder().itemType(ItemTypes.COMPASS).build();
	LoreData loreMCompass = magicCompass.getOrCreate(LoreData.class).get();
        magicCompass.offer(Keys.DISPLAY_NAME, MESSAGE("&4Magic &5Compass &e" + direction));
                 
	List<Text> newLore = loreMCompass.lore().get();
        newLore.add(MESSAGE("&l&n&2Owner: &e" + player.getName()));
        newLore.add(MESSAGE("&8 Fait un clik droit voir prendre la direction"));
        newLore.add(MESSAGE("&4Direction:&8 " + direction));
        newLore.add(MESSAGE("&8" + locationString));
	DataTransactionResult dataTransactionResult = magicCompass.offer(Keys.ITEM_LORE, newLore);

	if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(magicCompass);
        } else {
            return Optional.empty();
        }				
    }
    
    public void lookDirection(Player player, Vector3d direction){
        player.lookAt(direction);
    }
}
