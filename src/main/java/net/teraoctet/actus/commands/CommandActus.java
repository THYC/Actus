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
                        Text.builder().append(MESSAGE("&9/setname <name> &e: Change le nom d'un item ")).toText(),
                        Text.builder().append(MESSAGE("&9/kill &e: se suicide")).toText(),
                        Text.builder().append(MESSAGE("&9/sun &e: met le soleil")).toText(),
                        Text.builder().append(MESSAGE("&9/rain &e: met la pluie")).toText(),
                        Text.builder().append(MESSAGE("&9/storm &e: met l'orage")).toText(),
                        Text.builder().append(MESSAGE("&9/day &e: heure de jour")).toText(),
                        Text.builder().append(MESSAGE("&9/night &e: heure de nuit")).toText(),
                        Text.builder().append(MESSAGE("&9/plot &e: affiche les commandes parcelle")).toText(),
                        Text.builder().append(MESSAGE("&9/fly &e: mode vol")).toText(),
                        Text.builder().append(MESSAGE("&9/home &e: TP au home enregistré")).toText(),
                        Text.builder().append(MESSAGE("&9/sethome &e: déclare un home")).toText(),
                        Text.builder().append(MESSAGE("&9/delhome &e: supprime un home")).toText(),
                        Text.builder().append(MESSAGE("&9/back &e: retourne sur la position precedente")).toText(),
                        Text.builder().append(MESSAGE("&9/level &e: affiche le level du joueur")).toText(),
                        Text.builder().append(MESSAGE("&9/head &e: donne un skull/tete")).toText(),
                        Text.builder().append(MESSAGE("&9/worldcreate &e: creation d'un nouveau monde")).toText(),
                        Text.builder().append(MESSAGE("&9/worldtp &e: teleporte sur un monde")).toText(),
                        Text.builder().append(MESSAGE("&9/clearinventory &e: supprime l'inventaire")).toText(),
                        Text.builder().append(MESSAGE("&9/invsee &e: regarde le contenu d'un inventaire de joueur")).toText(),
                        Text.builder().append(MESSAGE("&9/playerinfo")).toText(),
                        Text.builder().append(MESSAGE("&9/broadcast")).toText(),
                        Text.builder().append(MESSAGE("&9/guild")).toText(),
                        Text.builder().append(MESSAGE("&9/rocket")).toText(),
                        Text.builder().append(MESSAGE("&9/portal")).toText(),
                        Text.builder().append(MESSAGE("&9/mc")).toText(),
                        Text.builder().append(MESSAGE("&9/write")).toText(),
                        Text.builder().append(MESSAGE("&9/signhelp")).toText(),
                        Text.builder().append(MESSAGE("&9/signbank")).toText(),
                        Text.builder().append(MESSAGE("&9/tpa")).toText(),
                        Text.builder().append(MESSAGE("&9/tphere")).toText(),
                        Text.builder().append(MESSAGE("&9/tpaccept")).toText(),
                        Text.builder().append(MESSAGE("&9/vanish")).toText(),
                        Text.builder().append(MESSAGE("&9/enchant")).toText(),
                        Text.builder().append(MESSAGE("&9/rule")).toText(),
                        Text.builder().append(MESSAGE("&9/bank")).toText(),
                        Text.builder().append(MESSAGE("&9/shoplist")).toText(),
                        Text.builder().append(MESSAGE("&9/chest")).toText(),
                        Text.builder().append(MESSAGE("&9/test")).toText())
                .header(Text.builder().append(MESSAGE("&bDevelopped by thyc82 and Votop")).toText())
                .padding(Text.of("-"))
                .sendTo(src); 
        return CommandResult.success();
        
    }
}        
