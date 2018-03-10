package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.permlight.api.PermManager;
import net.teraoctet.permlight.api.PermUser;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

public class CommandPromote implements CommandExecutor {
    
    PermManager PM = new PermManager();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.modo.promote")) {
            
            if(ctx.getOne("user").isPresent() && ctx.getOne("rank").isPresent()) {
                User user = ctx.<User> getOne("user").get();
                String rank = ctx.<String> getOne("rank").get();
                user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "group." + rank, Tristate.TRUE);
                Optional<PermUser> permUser = PM.getUser(user.getIdentifier());
                if(permUser.isPresent()){
                    permUser.get().setGroup(rank);
                    PM.saveUser(permUser.get());
                    if(user.getPlayer().isPresent())PM.loadPermissions(user.getPlayer().get());
                }
                return CommandResult.success();  
            }else{
                src.sendMessage(USAGE("/promote <joueur> <rank>"));
            }                   
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
