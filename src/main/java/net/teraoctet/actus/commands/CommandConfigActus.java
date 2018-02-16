package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.Actus.config;
import static net.teraoctet.actus.utils.Config.AUTOFOREST;
import static net.teraoctet.actus.utils.Config.CREEPER_DISABLE;
import static net.teraoctet.actus.utils.Config.ENABLE_SHOP_BOOKVIEW;
import static net.teraoctet.actus.utils.Config.ENABLE_SIGN_GRAVE;
import static net.teraoctet.actus.utils.Config.ENABLE_SKULL_GRAVE;
import static net.teraoctet.actus.utils.Config.ENABLE_TREEBREAK;
import static net.teraoctet.actus.utils.Config.TNT_DISABLE;
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
            Optional<Boolean> value = ctx.<Boolean> getOne("value");
            
            if(cmd.isPresent()){
                switch (cmd.get()){
                    case "treebreak":
                        if(value.isPresent()){
                            config.setEnableTreeBreak(value.get());
                            src.sendMessage(MESSAGE("&7TREEBREAK = " + value.get()));
                        }else{
                            if(ENABLE_TREEBREAK()){
                                config.setEnableTreeBreak(false);
                                src.sendMessage(MESSAGE("&7TREEBREAK = FALSE"));
                            }else{
                                config.setEnableTreeBreak(true);
                                src.sendMessage(MESSAGE("&7TREEBREAK = TRUE"));
                            }
                        }
                        break;
                    case "autoforest":
                        if(value.isPresent()){
                            config.setEnableAutoForest(value.get());
                            src.sendMessage(MESSAGE("&7AUTOFOREST = " + value.get()));
                        }else{
                            if(AUTOFOREST()){
                                config.setEnableAutoForest(false);
                                src.sendMessage(MESSAGE("&7AUTOFOREST = FALSE"));
                            }else{
                                config.setEnableAutoForest(true);
                                src.sendMessage(MESSAGE("&7AUTOFOREST = TRUE"));
                            }
                        }
                        break;
                    case "shopbookview":
                        if(value.isPresent()){
                            config.setEnableShopBookview(value.get());
                            src.sendMessage(MESSAGE("&7SHOP_BOOKVIEW = " + value.get()));
                        }else{
                            if(ENABLE_SHOP_BOOKVIEW()){
                                config.setEnableShopBookview(false);
                                src.sendMessage(MESSAGE("&7SHOP_BOOKVIEW = FALSE"));
                            }else{
                                config.setEnableShopBookview(true);
                                src.sendMessage(MESSAGE("&7SHOP_BOOKVIEW = TRUE"));
                            }
                        }
                        break;
                    case "signgrave":
                        if(value.isPresent()){
                            config.setEnableSignGrave(value.get());
                            src.sendMessage(MESSAGE("&7ENABLE_SIGN_GRAVE = " + value.get()));
                        }else{
                            if(ENABLE_SIGN_GRAVE()){
                                config.setEnableSignGrave(false);
                                src.sendMessage(MESSAGE("&7ENABLE_SIGN_GRAVE = FALSE"));
                            }else{
                                config.setEnableSignGrave(true);
                                src.sendMessage(MESSAGE("&7ENABLE_SIGN_GRAVE = TRUE"));
                            }
                        }
                        break;
                    case "skullgrave":
                        if(value.isPresent()){
                            config.setEnableSkullGrave(value.get());
                            src.sendMessage(MESSAGE("&7ENABLE_SKULL_GRAVE = " + value.get()));
                        }else{
                            if(ENABLE_SKULL_GRAVE()){
                                
                                
                                config.setEnableSkullGrave(false);
                                src.sendMessage(MESSAGE("&7ENABLE_SKULL_GRAVE = FALSE"));
                            }else{
                                config.setEnableSkullGrave(true);
                                src.sendMessage(MESSAGE("&7ENABLE_SKULL_GRAVE = TRUE"));
                            }
                        }
                        break;
                    case "creeper":
                        if(value.isPresent()){
                            config.setEnableCreeper(value.get());
                            src.sendMessage(MESSAGE("&7CREEPER_DISABLE = " + value.get()));
                        }else{
                            if(CREEPER_DISABLE()){
                                config.setEnableCreeper(false);
                                src.sendMessage(MESSAGE("&7CREEPER_DISABLE = FALSE"));
                            }else{
                                config.setEnableCreeper(true);
                                src.sendMessage(MESSAGE("&7CREEPER_DISABLE = TRUE"));
                            }
                        }
                        break;
                    case "tnt":
                        if(value.isPresent()){
                            config.setEnableTNT(value.get());
                            src.sendMessage(MESSAGE("&7TNT_DISABLE = " + value.get()));
                        }else{
                            if(TNT_DISABLE()){
                                config.setEnableTNT(false);
                                src.sendMessage(MESSAGE("&7TNT_DISABLE = FALSE"));
                            }else{
                                config.setEnableTNT(true);
                                src.sendMessage(MESSAGE("&7TNT_DISABLE = TRUE"));
                            }
                            break;
                        }
                    default:
                        src.sendMessage(MESSAGE("&e/config &7treebreak;autoforest;shopbookview;skullgrave;signgrave;creeper;tnt; &e[true/false]"));
                }  
            }
            src.sendMessage(MESSAGE("&e/config &7treebreak;autoforest;shopbookview;skullgrave;signgrave;creeper;tnt; &e[true/false]"));
            return CommandResult.success();
        } else {
            src.sendMessage(NO_PERMISSIONS());
        } 
        return CommandResult.empty();
    }
}