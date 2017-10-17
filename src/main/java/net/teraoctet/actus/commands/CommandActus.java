package net.teraoctet.actus.commands;

import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;

public class CommandActus implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
    
        PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
        PaginationList.Builder builder = paginationService.builder();  

        builder
                .title(Text.builder().append(MESSAGE("&eActus 0.1 Beta")).toText())
                .contents(
                        Text.builder().append(MESSAGE("&8/setname <name> &e: Change le nom d'un item ")).toText(),
                        Text.builder().append(MESSAGE("&8/kill &e: se suicide")).toText(),
                        Text.builder().append(MESSAGE("&8/sun &e: met le soleil")).toText(),
                        Text.builder().append(MESSAGE("&8/rain &e: met la pluie")).toText(),
                        Text.builder().append(MESSAGE("&8/storm &e: met l'orage")).toText(),
                        Text.builder().append(MESSAGE("&8/day &e: heure de jour")).toText(),
                        Text.builder().append(MESSAGE("&8/night &e: heure de nuit")).toText(),
                        Text.builder().append(MESSAGE("&8/plot &e: affiche les commandes parcelle")).toText(),
                        Text.builder().append(MESSAGE("&8/fly &e: mode vol")).toText(),
                        Text.builder().append(MESSAGE("&8/home &e: TP au home enregistré")).toText(),
                        Text.builder().append(MESSAGE("&8/sethome &e: déclare un home")).toText(),
                        Text.builder().append(MESSAGE("&8/delhome &e: supprime un home")).toText(),
                        Text.builder().append(MESSAGE("&8/back &e: retourne sur la position precedente")).toText(),
                        Text.builder().append(MESSAGE("&8/level &e: affiche le level du joueur")).toText(),
                        Text.builder().append(MESSAGE("&8/head &e: donne un skull/tete")).toText(),
                        Text.builder().append(MESSAGE("&8/worldcreate &e: creation d'un nouveau monde")).toText(),
                        Text.builder().append(MESSAGE("&8/worldtp &e: teleporte sur un monde")).toText(),
                        Text.builder().append(MESSAGE("&8/clearinventory &e: supprime l'inventaire")).toText(),
                        Text.builder().append(MESSAGE("&8/invsee &e: regarde le contenu d'un inventaire de joueur")).toText(),
                        Text.builder().append(MESSAGE("&8/playerinfo")).toText(),
                        Text.builder().append(MESSAGE("&8/broadcast")).toText(),
                        Text.builder().append(MESSAGE("&8/guild")).toText(),
                        Text.builder().append(MESSAGE("&8/rocket")).toText(),
                        Text.builder().append(MESSAGE("&8/portal")).toText(),
                        Text.builder().append(MESSAGE("&8/mc")).toText(),
                        Text.builder().append(MESSAGE("&8/write")).toText(),
                        Text.builder().append(MESSAGE("&8/signhelp")).toText(),
                        Text.builder().append(MESSAGE("&8/signbank")).toText(),
                        Text.builder().append(MESSAGE("&8/tpa")).toText(),
                        Text.builder().append(MESSAGE("&8/tphere")).toText(),
                        Text.builder().append(MESSAGE("&8/tpaccept")).toText(),
                        Text.builder().append(MESSAGE("&8/vanish")).toText(),
                        Text.builder().append(MESSAGE("&8/enchant")).toText(),
                        Text.builder().append(MESSAGE("&8/rule")).toText(),
                        Text.builder().append(MESSAGE("&8/bank")).toText(),
                        Text.builder().append(MESSAGE("&8/shoplist")).toText(),
                        Text.builder().append(MESSAGE("&8/chest")).toText(),
                        Text.builder().append(MESSAGE("&8/test")).toText())
                .header(Text.builder().append(MESSAGE("&bDevelopped by thyc82 and Votop")).toText())
                .padding(Text.of("-"))
                .sendTo(src); 
        return CommandResult.success();
        
    }
}        
