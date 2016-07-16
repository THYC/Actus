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
                .title(Text.builder().append(MESSAGE("&eActus")).toText())
                .contents(
                        Text.builder().append(MESSAGE("&4Vs 0.1 Beta")).toText(),
                        Text.builder().append(MESSAGE("")).toText(),
                        Text.builder().append(MESSAGE("")).toText())
                .header(Text.builder().append(MESSAGE("&bBy thyc82")).toText())
                .padding(Text.of("-"))
                .sendTo(src); 
        return CommandResult.success();
        
    }
}
