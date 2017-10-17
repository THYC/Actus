package net.teraoctet.actus.commands.grave;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.actus.Actus.configGrave;
import static net.teraoctet.actus.Actus.serverManager;
import net.teraoctet.actus.grave.Grave;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Commande callback attenante aux tombes/Grave
 * @author thierry
 */
public class CallBackGrave {
    
    /**
     * Supprime la tombe enregistré du fichier grave.conf et restaure 
     * les blocks d'origine a l'emplacement de la tombe.
     * @param locGrave
     * @return 
     */
    public Consumer<CommandSource> callDelGrave(String locGrave) {
	return (CommandSource src) -> {
            
            try {
                Optional<Grave> grave = configGrave.getGrave(locGrave);
                if(grave.isPresent()){
                    
                    configGrave.delGrave(locGrave);
                    src.sendMessage(MESSAGE("&e-------------------------"));
                    src.sendMessage(MESSAGE("&4ITombe supprim\351e"));  
                    src.sendMessage(MESSAGE("&e-------------------------"));
                }else{
                    src.sendMessage(MESSAGE("&eAucune tombe correspondante"));
                }
            } catch (IOException ex) {
                Logger.getLogger(CallBackGrave.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        };
    }
    
    /**
     * teleporte le joueur sur ls coordonnées de la tombe enregistré
     * @param locGrave
     * @return 
     */
    public Consumer<CommandSource> callTPGrave(String locGrave) {
	return (CommandSource src) -> {
            Optional<Grave> grave = configGrave.getGrave(locGrave);
            if(grave.isPresent()){
                Player player = (Player)src;
                player.setLocation(grave.get().getDeadLocation().get());
            }
            
        };
    }
    
    /**
     * affiche sur le tchat les infos enregistrées sur cette tombe
     * @param locGrave
     * @return 
     */
    public Consumer<CommandSource> callInfoGrave(String locGrave) {
	return (CommandSource src) -> {
            Optional<Grave> grave = configGrave.getGrave(locGrave);
            if(grave.isPresent()){
                src.sendMessage(MESSAGE("&a&b++++- Ici git " + grave.get().getName() + "  -++++"));
                src.sendMessage(MESSAGE("&eDecede le :" + serverManager.longToDateString(grave.get().getGraveTime())));
                src.sendMessage(MESSAGE("&eCause :").concat(grave.get().getDeadMessage()));
            }
            
        };
    }
    
    /**
     * affiche dans le GUI le contenu de la tombe enregistré
     * @param locGrave
     * @return 
     */
    public Consumer<CommandSource> callOpenGrave(String locGrave) {
	return (CommandSource src) -> {
            Optional<Grave> grave;
            grave = configGrave.getGrave(locGrave);
            if(grave.isPresent()){
                Player player = (Player)src;
                configGrave.openGrave(player, grave.get());
            }
        };
    }
    
    /**
     * Deplace la tombe enregistré dans un caveau du cimetière
     * @param locGrave
     * @return 
     */
    public Consumer<CommandSource> callMoveGrave(String locGrave) {
	return (CommandSource src) -> {
            Optional<Grave> grave;
            grave = configGrave.getGrave(locGrave);
            if(grave.isPresent()){
                Player player = (Player)src;
                configGrave.moveGrave(grave.get());
                player.sendMessage(MESSAGE("&eDeplacement termin\351"));            }
        };
    }
}
