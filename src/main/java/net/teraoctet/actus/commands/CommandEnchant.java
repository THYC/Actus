package net.teraoctet.actus.commands;

import java.util.Optional;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;

public class CommandEnchant implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("actus.enchant")){
            Optional<Player> optPlayer = Optional.empty();
            Optional<Player> target = ctx.<Player> getOne("target");
            Optional<String> enchantmentName = ctx.<String> getOne("enchantment");
            Optional<Integer> level = ctx.<Integer> getOne("level");
            
            if(src instanceof ConsoleSource || src instanceof CommandBlockSource){
                if(target.isPresent()){
                    optPlayer = Optional.of(target.get());
                }else{
                    src.sendMessage(USAGE("/enchant <enchantement> <level> [player]"));
                }
            }else if(src instanceof Player){
                if(target.isPresent()){
                    if(src.hasPermission("actus.enchant.others")){
                        optPlayer = Optional.of(target.get());
                    }else{
                        src.sendMessage(NO_PERMISSIONS());
                    }
                }else{
                    optPlayer = Optional.of((Player)src);
                }
            }
            
            if(optPlayer.isPresent()){
                Player player = optPlayer.get();
                if(enchantmentName.isPresent()){
                    Enchantment enchantment = Sponge.getRegistry().getType(Enchantment.class, enchantmentName.get()).orElse(null);
                    if (enchantment == null){
                        src.sendMessage(MESSAGE("&4Erreur: Enchantement non trouv\351 !"));
                        src.sendMessage(MESSAGE("&bArme : sharpness, smite, bane_of_arthropods, knockback, fire_aspect, looting, power, punch, flame, infinity"));
                        return CommandResult.empty();
                    }
                    
                    if(!level.isPresent()){
                        src.sendMessage(MESSAGE("&4Erreur: Level absent!"));
                        src.sendMessage(USAGE("/enchant <enchantement> <level> [player]"));
                        return CommandResult.empty();
                    }
                    
                    if (enchantment.getMaximumLevel() < level.get()){
                        src.sendMessage(MESSAGE("&4Erreur : level trop haut !"));
                        src.sendMessage(MESSAGE("&4max level " + enchantment.getName() + " : " + String.valueOf(enchantment.getMaximumLevel())));
                        return CommandResult.empty();
                    }

                    if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                        ItemStack itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get();
                        if (!enchantment.canBeAppliedToStack(itemInHand)){
                            src.sendMessage(MESSAGE("&4Erreur : Cette enchantement n'est pas applicable sur cette item"));
                            return CommandResult.empty();
                        }

                        EnchantmentData enchantmentData = itemInHand.getOrCreate(EnchantmentData.class).get();
                        ItemEnchantment itemEnchantment = new ItemEnchantment(enchantment, level.get());
                        ItemEnchantment sameEnchantment = null;

                        for (ItemEnchantment ench : enchantmentData.enchantments()){
                            if (ench.getEnchantment().getId().equals(enchantment.getId())){
                                sameEnchantment = ench;
                                break;
                            }
                        }

                        if (sameEnchantment == null){
                            enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
                        }else{
                            enchantmentData.set(enchantmentData.enchantments().remove(sameEnchantment));
                            enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
                        }

                        itemInHand.offer(enchantmentData);
                        player.setItemInHand(HandTypes.MAIN_HAND,itemInHand);
                        player.sendMessage(MESSAGE("&eItem enchant\350 avec succ\350s"));
                    }else{
                        src.sendMessage(MESSAGE("&bAucun item pr\350sent !"));
                    }
                    return CommandResult.success();
                }
                src.sendMessage(USAGE("/enchant <enchantement> <level> [player]"));
            }
        }else{
            src.sendMessage(NO_PERMISSIONS());
        }      
        return CommandResult.empty();
    }
}
