package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import static net.teraoctet.actus.Actus.itemShopManager;
import static net.teraoctet.actus.Actus.plotManager;
import net.teraoctet.actus.shop.ItemShop;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.plot.Plot;
import static net.teraoctet.actus.utils.MessageManager.AS_HAS_ARMS;
import static net.teraoctet.actus.utils.MessageManager.AS_HAS_BASE_PLATE;
import static net.teraoctet.actus.utils.MessageManager.AS_HAS_GRAVITY;
import static net.teraoctet.actus.utils.MessageManager.AS_INVISIBLE;
import static net.teraoctet.actus.utils.MessageManager.AS_IS_SMALL;
import static net.teraoctet.actus.utils.MessageManager.AS_MARKER;
import static net.teraoctet.actus.utils.MessageManager.AS_NAME_VISIBLE;
import static net.teraoctet.actus.utils.MessageManager.CHEST_ROTATION;
import static net.teraoctet.actus.utils.MessageManager.HEAD_ROTATION;
import static net.teraoctet.actus.utils.MessageManager.LEFT_ARM_ROTATION;
import static net.teraoctet.actus.utils.MessageManager.LEFT_LEG_ROTATION;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.POSITION_X_AS;
import static net.teraoctet.actus.utils.MessageManager.POSITION_Y_AS;
import static net.teraoctet.actus.utils.MessageManager.POSITION_Z_AS;
import static net.teraoctet.actus.utils.MessageManager.RIGHT_ARM_ROTATION;
import static net.teraoctet.actus.utils.MessageManager.RIGHT_LEG_ROTATION;
import static net.teraoctet.actus.utils.MessageManager.ROTATION_AS;
import static net.teraoctet.actus.utils.MessageManager.formatText;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.entity.Entity;
import static org.spongepowered.api.entity.EntityTypes.ARMOR_STAND;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.extent.EntityUniverse;

public class CommandAS implements CommandExecutor {
    
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("actus.fun.as")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getUniqueId().toString());
            Entity as = null;

            Optional<String> id = ctx.<String> getOne("uuid");
            if (id.isPresent()) {
                Optional<Entity> entShop = player.getWorld().getEntity(UUID.fromString(id.get()));                   
                if(entShop.isPresent()) as = entShop.get();
            }else{
                as = player.getWorld().getIntersectingEntities(player, 15).stream()
                    .filter(e -> e.getEntity().getType().equals(ARMOR_STAND)).findFirst()  
                    .map(EntityUniverse.EntityHit::getEntity).orElse(null);
            }
            
            if(as == null){
                player.sendMessage(MESSAGE("&bAucun ArmorStand dans la vision"));
                return CommandResult.empty();
            }

            Optional<Plot> plot = plotManager.getPlot(as.getLocation());
            if (plot.isPresent()){ 
                if(!plot.get().getUuidOwner().equalsIgnoreCase(player.getIdentifier()) && aplayer.getLevel() != 10){
                    src.sendMessage(NO_PERMISSIONS());
                    return CommandResult.empty(); 
                }
            }

            UUID uuid = as.getUniqueId();
            if((itemShopManager.hasShop(uuid) && aplayer.getLevel() == 10) || !itemShopManager.hasShop(uuid)){  
                String titre = "ArmorStand";
                if(itemShopManager.hasShop(uuid)){
                    Optional<ItemShop> shop = itemShopManager.getItemShop(uuid);
                    if(shop.isPresent()) titre = "Shop : " + shop.get().getItemStack().getItem().getName();
                }

                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                    PaginationList.Builder builder = paginationService.builder();

                    builder.title(formatText("&e" + titre ))
                            .contents(                                        
                                Text.builder().append(CHEST_ROTATION()).toText().concat(
                                Text.builder().append(MESSAGE("&a[ X ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.CHEST, Axis.X, false))).toText().concat(
                                Text.builder().append(MESSAGE("&b[ Y ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.CHEST, Axis.Y, false))).toText().concat(
                                Text.builder().append(MESSAGE("&c[ Z ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.CHEST, Axis.Z, false))).toText()))).toText(),
                                                                                                
                                Text.builder().append(HEAD_ROTATION()).toText().concat(
                                Text.builder().append(MESSAGE("&a[ X ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.HEAD, Axis.X, false))).toText().concat(
                                Text.builder().append(MESSAGE("&b[ Y ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.HEAD, Axis.Y, false))).toText().concat(
                                Text.builder().append(MESSAGE("&c[ Z ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.HEAD, Axis.Z, false))).toText()))).toText(),
                                
                                Text.builder().append(LEFT_ARM_ROTATION()).toText().concat(
                                Text.builder().append(MESSAGE("&a[ X ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.LEFT_ARM, Axis.X, false))).toText().concat(
                                Text.builder().append(MESSAGE("&b[ Y ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.LEFT_ARM, Axis.Y, false))).toText().concat(
                                Text.builder().append(MESSAGE("&c[ Z ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.LEFT_ARM, Axis.Z, false))).toText()))).toText(),
                                
                                Text.builder().append(RIGHT_ARM_ROTATION()).toText().concat(
                                Text.builder().append(MESSAGE("&a[ X ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.RIGHT_ARM, Axis.X, false))).toText().concat(
                                Text.builder().append(MESSAGE("&b[ Y ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.RIGHT_ARM, Axis.Y, false))).toText().concat(
                                Text.builder().append(MESSAGE("&c[ Z ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.RIGHT_ARM, Axis.Z, false))).toText()))).toText(),
                                
                                Text.builder().append(LEFT_LEG_ROTATION()).toText().concat(
                                Text.builder().append(MESSAGE("&a[ X ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.LEFT_LEG, Axis.X, false))).toText().concat(
                                Text.builder().append(MESSAGE("&b[ Y ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.LEFT_LEG, Axis.Y, false))).toText().concat(
                                Text.builder().append(MESSAGE("&c[ Z ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.LEFT_LEG, Axis.Z, false))).toText()))).toText(),
                                
                                Text.builder().append(RIGHT_LEG_ROTATION()).toText().concat(
                                Text.builder().append(MESSAGE("&a[ X ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.RIGHT_LEG, Axis.X, false))).toText().concat(
                                Text.builder().append(MESSAGE("&b[ Y ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.RIGHT_LEG, Axis.Y, false))).toText().concat(
                                Text.builder().append(MESSAGE("&c[ Z ] ")).onClick(TextActions.executeCallback(callBodyRotation(as, BodyParts.RIGHT_LEG, Axis.Z, false))).toText()))).toText(),
                                                                
                                Text.builder().append(POSITION_X_AS()).toText().concat(
                                Text.builder().append(MESSAGE("&e[ + ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.X,true,0.02))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ - ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.X,false,0.02))).toText())).concat(
                                Text.builder().append(MESSAGE("&e[ ++ ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.X,true,0.2))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ -- ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.X,false,0.2))).toText())),
                                
                                Text.builder().append(POSITION_Y_AS()).toText().concat(
                                Text.builder().append(MESSAGE("&e[ + ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Y,true,0.02))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ - ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Y,false,0.02))).toText())).concat(
                                Text.builder().append(MESSAGE("&e[ ++ ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Y,true,0.2))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ -- ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Y,false,0.2))).toText())),
                                
                                Text.builder().append(POSITION_Z_AS()).toText().concat(
                                Text.builder().append(MESSAGE("&e[ + ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Z,true,0.02))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ - ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Z,false,0.02))).toText())).concat(
                                Text.builder().append(MESSAGE("&e[ ++ ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Z,true,0.2))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ -- ] ")).onClick(TextActions.executeCallback(callPosition(as,Axis.Z,false,0.2))).toText())),
                                
                                Text.builder().append(ROTATION_AS()).toText().concat(
                                Text.builder().append(MESSAGE("&e[ + ] ")).onClick(TextActions.executeCallback(callRotation(as, 2))).toText().concat(
                                Text.builder().append(MESSAGE("&a[ ++ ] ")).onClick(TextActions.executeCallback(callRotation(as, 10))).toText())),
                                
                                Text.builder().append(MESSAGE("&9----------------------")).toText(),
                                
                                Text.builder().append(AS_HAS_BASE_PLATE())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.ARMOR_STAND_HAS_BASE_PLATE))).toText(),
                                
                                Text.builder().append(AS_HAS_GRAVITY())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.HAS_GRAVITY))).toText(),
                                
                                Text.builder().append(AS_IS_SMALL())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.ARMOR_STAND_IS_SMALL))).toText(),
                                
                                Text.builder().append(AS_NAME_VISIBLE())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.CUSTOM_NAME_VISIBLE))).toText(),
                                
                                Text.builder().append(AS_MARKER())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.ARMOR_STAND_MARKER))).toText(),
                                
                                Text.builder().append(AS_HAS_ARMS())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.ARMOR_STAND_HAS_ARMS))).toText(),
                                
                                Text.builder().append(AS_INVISIBLE())
                                    .onClick(TextActions.executeCallback(callProperty(as, Keys.INVISIBLE))).toText())  

                            .padding(MESSAGE("&9-"))
                            .sendTo(src);
            
            }else{
                src.sendMessage(MESSAGE("&cVous devez etre Level 10 pour modifier un ItemShop"));
                return CommandResult.empty();
            }
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
    
    private Consumer<CommandSource> callPosition(Entity entity, Axis axe, Boolean up, double value) {
	return (CommandSource src) -> { 
            ArmorStand armorStand = (ArmorStand)entity;
            armorStand.offer(Keys.HAS_GRAVITY, false);
            double val = - value;
            if(up == true)val = value;
            switch(axe){
                case X: armorStand.setLocation(armorStand.getLocation().setPosition(armorStand.getLocation().getPosition().add(val, 0, 0)));break;
                case Y: armorStand.setLocation(armorStand.getLocation().setPosition(armorStand.getLocation().getPosition().add(0, val, 0)));break;
                case Z: armorStand.setLocation(armorStand.getLocation().setPosition(armorStand.getLocation().getPosition().add(0, 0, val)));break;
            }
        };
    }
    
    private Consumer<CommandSource> callRotation(Entity entity, int x) {
	return (CommandSource src) -> { 
            ArmorStand armorStand = (ArmorStand)entity;
            armorStand.setRotation(armorStand.getRotation().add(x, x, x));
        };
    }
    
    private Consumer<CommandSource> callProperty(Entity entity,Key key) {
	return (CommandSource src) -> { 
            Player player = (Player)src;
            ArmorStand armorStand = (ArmorStand)entity;
            
            if(armorStand.get(key).isPresent()){
                if(armorStand.get(key).get().equals(true)){
                    armorStand.offer(key, false);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&d[" + key.getName() + "] Desactiv\351"));
                }else{
                    armorStand.offer(key, true);
                    player.sendMessage(ChatTypes.ACTION_BAR, MESSAGE("&d[" + key.getName() + "] Activ\351"));
                }
            }
        };
    }
    
    private Consumer<CommandSource> callBodyRotation(Entity entity,BodyPart bodyPart, Axis axe, Boolean reverse) {
	return (CommandSource src) -> { 
            ArmorStand armorStand = (ArmorStand)entity;
            Double val = 5.00;
            if(reverse)val = - 5.00;
            
            Map<BodyPart, Vector3d> bodyRotations = armorStand.get(Keys.BODY_ROTATIONS).get();
            switch(axe){
                case X: bodyRotations.replace(bodyPart, bodyRotations.get(bodyPart).add(5, 0, 0)); break;
                case Y: bodyRotations.replace(bodyPart, bodyRotations.get(bodyPart).add(0, 5, 0)); break;
                case Z: bodyRotations.replace(bodyPart, bodyRotations.get(bodyPart).add(0, 0, 5)); break;
            }
            armorStand.tryOffer(Keys.BODY_ROTATIONS, bodyRotations);
        };
    }
    
    public enum Axis {
        X,
        Y,
        Z;    
    }
}
