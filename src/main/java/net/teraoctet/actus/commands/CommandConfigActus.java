package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.config;
import static net.teraoctet.actus.utils.Config.AUTOFOREST;
import static net.teraoctet.actus.utils.Config.ENABLE_SHOP_BOOKVIEW;
import static net.teraoctet.actus.utils.Config.ENABLE_SIGN_GRAVE;
import static net.teraoctet.actus.utils.Config.ENABLE_SKULL_GRAVE;
import static net.teraoctet.actus.utils.Config.ENABLE_TREEBREAK;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;

public class CommandConfigActus implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.admin.config")) {
            
            Optional<String> cmd = ctx.<String> getOne("cmd");
            
            if(cmd.isPresent()){
                switch (cmd.get()){
                    case "treebreak":
                        if(ENABLE_TREEBREAK()){
                            config.setEnableTreeBreak(false);
                            src.sendMessage(MESSAGE("&7TREEBREAK = FALSE"));
                        }else{
                            config.setEnableTreeBreak(true);
                            src.sendMessage(MESSAGE("&7TREEBREAK = TRUE"));
                        }
                        break;
                    case "autoforest":
                        if(AUTOFOREST()){
                            config.setEnableAutoForest(false);
                            src.sendMessage(MESSAGE("&7AUTOFOREST = FALSE"));
                        }else{
                            config.setEnableAutoForest(true);
                            src.sendMessage(MESSAGE("&7AUTOFOREST = TRUE"));
                        }
                        break;
                    case "shopbookview":
                        if(ENABLE_SHOP_BOOKVIEW()){
                            config.setEnableShopBookview(false);
                            src.sendMessage(MESSAGE("&7SHOP_BOOKVIEW = FALSE"));
                        }else{
                            config.setEnableShopBookview(true);
                            src.sendMessage(MESSAGE("&7SHOP_BOOKVIEW = TRUE"));
                        }
                        break;
                    case "signgrave":
                        if(ENABLE_SIGN_GRAVE()){
                            config.setEnableSignGrave(false);
                            src.sendMessage(MESSAGE("&7ENABLE_SIGN_GRAVE = FALSE"));
                        }else{
                            config.setEnableSignGrave(true);
                            src.sendMessage(MESSAGE("&7ENABLE_SIGN_GRAVE = TRUE"));
                        }
                        break;
                    case "skullgrave":
                        if(ENABLE_SKULL_GRAVE()){
                            config.setEnableSkullGrave(false);
                            src.sendMessage(MESSAGE("&7ENABLE_SKULL_GRAVE = FALSE"));
                        }else{
                            config.setEnableSkullGrave(true);
                            src.sendMessage(MESSAGE("&7ENABLE_SKULL_GRAVE = TRUE"));
                        }
                        break;
                    default:
                        src.sendMessage(MESSAGE("&e/config &7treebreak;autoforest;shopbookview;skullgrave;signgrave;"));
                }  
            }
            src.sendMessage(MESSAGE("&e/config &7treebreak;autoforest;shopbookview;skullgrave;signgrave;"));
            return CommandResult.success();
        } else {
            src.sendMessage(NO_PERMISSIONS());
        } 
        return CommandResult.empty();
    }
}