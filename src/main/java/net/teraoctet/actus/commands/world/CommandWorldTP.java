package net.teraoctet.actus.commands.world;

import static net.teraoctet.actus.Actus.wdm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.OTHER_TELEPORTED_TO_WORLD;
import static net.teraoctet.actus.utils.MessageManager.TELEPORTED_TO_WORLD;
import static net.teraoctet.actus.utils.MessageManager.USAGE;
import static net.teraoctet.actus.utils.MessageManager.NOT_FOUND;
import net.teraoctet.actus.world.AWorld;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
    
public class CommandWorldTP implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
	
        if (src.hasPermission("actus.world.worldtp")){ 
            //si le joueur ne tape pas le <world>
            if(!ctx.getOne("worldName").isPresent() && src instanceof Player) { 
                src.sendMessage(USAGE("/worldtp <world> [player]"));
            }

            //si ce n'est pas un joueur (console) et que <world> ou <player> ne sont pas renseignés
            else if((!ctx.getOne("target").isPresent() || !ctx.getOne("worldName").isPresent()) && !(src instanceof Player)) { 
                src.sendMessage(USAGE("/worldtp <world> <player>"));
            }

            //quand la commande est correctement renseignée par la source
            else {
                String worldName = ctx.<String> getOne("worldName").get();
                AWorld aworld = wdm.getWorld(worldName);
            
                //monde introuvable
                if(!getGame().getServer().getWorld(worldName).isPresent()) { 
                    src.sendMessage(NOT_FOUND(worldName));
                } else if(aworld == null) {
                    src.sendMessage(NOT_FOUND(worldName));
                }
                //le monde est correctement trouvé
                else {
                    World world = getGame().getServer().getWorld(worldName).get(); 

                    //si [player] n'est pas renseigné, la source est ciblé (doit être un joueur)
                    if(!ctx.getOne("target").isPresent()) {
                        Player player = (Player)src;
                        Location lastLocation = player.getLocation();
                        player.setLocation(world.getSpawnLocation());
                        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
                        aplayer.setLastposition(DeSerialize.location(lastLocation));
                        aplayer.update();
                        player.offer(Keys.GAME_MODE, aworld.getGamemode());
                        src.sendMessage(TELEPORTED_TO_WORLD(player,worldName));
                        return CommandResult.success();
                    }

                    //lorsque un [player] est ciblé par la commande
                    else if(ctx.getOne("target").isPresent() && src.hasPermission("actus.admin.world.worldtp")) {
                        if(src.hasPermission("actus.admin.world.worldtp")){
                            Player target = ctx.<Player> getOne("target").get();
                            if(target != null) {
                                Location lastLocation = target.getLocation();
                                target.setLocation(world.getSpawnLocation());
                                APlayer aplayer = getAPlayer(target.getUniqueId().toString());
                                aplayer.setLastposition(DeSerialize.location(lastLocation));
                                aplayer.update();
                                target.offer(Keys.GAME_MODE, aworld.getGamemode());
                                src.sendMessage(OTHER_TELEPORTED_TO_WORLD(target,worldName));
                                target.sendMessage(TELEPORTED_TO_WORLD(target,worldName));
                                return CommandResult.success();
                            } else {
                                src.sendMessage(NOT_FOUND(target.getName()));
                            }
                        } else {
                            src.sendMessage(NO_PERMISSIONS());
                        }  
                    }   
                }
            }
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
