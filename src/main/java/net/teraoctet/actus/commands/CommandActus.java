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
                        Text.builder().append(MESSAGE("&8/setname <name>")).toText(),
                        Text.builder().append(MESSAGE("&8/kill")).toText(),
                        Text.builder().append(MESSAGE("&8/sun")).toText(),
                        Text.builder().append(MESSAGE("&8/rain")).toText(),
                        Text.builder().append(MESSAGE("&8/storm")).toText(),
                        Text.builder().append(MESSAGE("&8/day")).toText(),
                        Text.builder().append(MESSAGE("&8/night")).toText(),
                        Text.builder().append(MESSAGE("&8/plot")).toText(),
                        Text.builder().append(MESSAGE("&8/fly")).toText(),
                        Text.builder().append(MESSAGE("&8/home")).toText(),
                        Text.builder().append(MESSAGE("&8/sethome")).toText(),
                        Text.builder().append(MESSAGE("&8/delhome")).toText(),
                        Text.builder().append(MESSAGE("&8/back")).toText(),
                        Text.builder().append(MESSAGE("&8/level")).toText(),
                        Text.builder().append(MESSAGE("&8/head")).toText(),
                        Text.builder().append(MESSAGE("&8/worldcreate")).toText(),
                        Text.builder().append(MESSAGE("&8/worldtp")).toText(),
                        Text.builder().append(MESSAGE("&8/clearinventory")).toText(),
                        Text.builder().append(MESSAGE("&8/invsee")).toText(),
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
