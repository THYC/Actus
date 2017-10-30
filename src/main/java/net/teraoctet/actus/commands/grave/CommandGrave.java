package net.teraoctet.actus.commands.grave;

import static net.teraoctet.actus.Actus.config;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class CommandGrave implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.admin.grave")) {
            
            if(ctx.getOne("sign").isPresent() || ctx.getOne("skull").isPresent()){
                if(ctx.getOne("sign").isPresent()){
                    boolean sign = ctx.<Boolean> getOne("sign").get();
                    config.setEnableSignGrave(sign);
                    src.sendMessage(MESSAGE("&bSign : " + sign));
                }
                if(ctx.getOne("skull").isPresent()){
                    boolean skull = ctx.<Boolean> getOne("skull").get();
                    config.setEnableSkullGrave(skull);
                    src.sendMessage(MESSAGE("&bSkull : " + skull));
                }
                return CommandResult.success();
            }
            
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder();  

            builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Grave")).toText())
                .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/grave info : &7Information sur la tombe")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/grave [-s true/false] [-k true/false] : &7Active le panneau et/ou skull")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/grave list : &7liste les tombes actives")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/grave del : &7Supprime la tombe")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/grave move : &7d\351place la tombe dans une crypte")).toText())
                .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&eUsage:")).toText())
                .padding(Text.of("-"))
                .sendTo(src); 
            
            return CommandResult.success();
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
