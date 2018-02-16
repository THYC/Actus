package net.teraoctet.actus.commands;

import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import net.teraoctet.lightperm.api.Manager;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

public class CommandPromote implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.modo.promote")) {
            
            if(ctx.getOne("user").isPresent() && ctx.getOne("rank").isPresent()) {
                User user = ctx.<User> getOne("user").get();
                String rank = ctx.<String> getOne("rank").get();
                user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "group." + rank, Tristate.TRUE);
                Manager.setRank(user, rank);
                return CommandResult.success();  
            }else{
                src.sendMessage(USAGE("/promote <joueur> <rank>"));
                String groups = "";
                for (String g : Manager.getGroupsName()) {
                    groups = groups + " " + g;
                }
                src.sendMessage(MESSAGE(groups));
            }                   
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
