package net.teraoctet.actus.commands.chest;

import static net.teraoctet.actus.Actus.config;
import static net.teraoctet.actus.utils.Config.AUTO_LOCKCHEST;
import static net.teraoctet.actus.utils.Config.ENABLE_LOCKCHEST;
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
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

public class CommandChest implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.player.chest")) {
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder();  
            Text infmodchest;
            if(ENABLE_LOCKCHEST()){
                if(AUTO_LOCKCHEST()){
                    infmodchest = MESSAGE(" [AutoLock activ\351]");
                }else{
                    infmodchest = MESSAGE(" [AutoLock d\351sactiv\351]");
                }
            }else{
                infmodchest = MESSAGE(" &4[Le module LockChest est d\351sactiv\351]");
            }
            
            if(ctx.getOne("enable").isPresent() || ctx.getOne("auto").isPresent()){
                if(ctx.getOne("enable").isPresent()){
                    boolean enable = ctx.<Boolean> getOne("enable").get();
                    config.setEnableLockChest(enable);
                    src.sendMessage(MESSAGE("&bChest actif : " + enable));
                }
                if(ctx.getOne("auto").isPresent()){
                    boolean auto = ctx.<Boolean> getOne("auto").get();
                    config.setEnableAutoLockChest(auto);
                    src.sendMessage(MESSAGE("&bAuto lock actif : " + auto));
                }
                return CommandResult.success();
            }
            
            builder.title(MESSAGE("&6Chest").concat(infmodchest))
                .contents(
                        MESSAGE("&e/chest add &b<joueur> : &7Ajoute les droits d'utilisation a un joueur"),
                        MESSAGE("&e/chest remove &b<joueur> : &7Retire les droits d'un joueur"),
                        MESSAGE("&e/chest remove : &7Rend le coffre public"),
                        MESSAGE("&e/chest [-e true/false] [-a true/false] : &7Active LockChest et/ou AutoLock"),
                        MESSAGE("&e/chest info : &7Liste les utilisateurs du coffre"))
                .header(MESSAGE("&eUsage:"))
                .padding(Text.of("-"))
                .sendTo(src); 
            
            return CommandResult.success();
            
        }else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }else {
            src.sendMessage(NO_PERMISSIONS());
        }               
        return CommandResult.empty();
    }
}
