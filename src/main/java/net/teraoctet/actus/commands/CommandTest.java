package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.function.Consumer;

import net.teraoctet.actus.bookmessage.ConfigBook;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.text.BookView;

import org.spongepowered.api.text.Text;



public class CommandTest implements CommandExecutor {
        //@Inject public PluginContainer actus;
    ConfigBook cb = new ConfigBook();
    
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
 
        Player player = (Player) src;
        
        //player.sendMessage(MESSAGE("&eListe de vos messages :"));
        //player.sendMessage(MESSAGE("&e---------------------------"));
        //player.sendMessage(cb.getMailBook(player));
        
        BookView.Builder bv = 
                BookView.builder()
                        .author(MESSAGE("STAFF"))
                        .title(MESSAGE("MAIL"))
                        .addPage(cb.getMailBook(player));
        
        player.sendBookView(bv.build());
        
        
        
        /*ItemStack itemStack = ItemStack.of(ItemTypes.STICK, 1);
        List<Text> lore = Lists.newArrayList(Text.of(TextColors.LIGHT_PURPLE, "This wand emits pure magic."));
        itemStack.offer(Keys.HIDE_ENCHANTMENTS,true);
        itemStack.offer(Keys.ITEM_LORE, lore);
        player.setItemInHand(HandTypes.MAIN_HAND, itemStack);*/
        
        /*Optional<Location> optlocation = Optional.empty();
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext()) 
            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                optlocation = Optional.of(currentHitRay.getLocation()); 
                      
            } 
            DataQuery FLAG = DataQuery.of("Owner");
            optlocation.get().toContainer().set(FLAG, player.getName());
            
            plugin.getLogger().info(optlocation.get().toContainer().getContainer().toString());
            
       /*
        
        Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
        .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Quests: Page " + (1 + 1))))
        .build(plugin);
        
        player.openInventory(inv,Cause.source(inv).build());
        
        spawnParticles(player.getLocation(),50,true);*/
        
        return CommandResult.success();
    }
    


    private Consumer<CommandSource> Cb(Entity entity, boolean sens) {
	return (CommandSource src) -> { 
            Player p = (Player)src;
            if(sens){
                entity.setRotation(entity.getRotation().add(0, 5, 0));
            }else{
                entity.setRotation(entity.getRotation().add(0, -5, 0));
            }
            //plugin.getLogger().info(entity.getRotation().toString());
           
        };
    }
    
    private Consumer<CommandSource> Cb2(Entity entity, boolean sens) {
	return (CommandSource src) -> { 
            Player p = (Player)src;
            ArmorStand armorStand = (ArmorStand)entity;
            //armorStand.offer(Keys.DISPLAY_NAME, is.get(Keys.DISPLAY_NAME).get());
            //armorStand.offer(Keys.ARMOR_STAND_HAS_BASE_PLATE, false);
            //armorStand.offer(Keys.HAS_GRAVITY, false);
            armorStand.offer(Keys.CUSTOM_NAME_VISIBLE, true);
            Map<BodyPart, Vector3d> bodyRotations = armorStand.get(Keys.BODY_ROTATIONS).get();
            //bodyRotations.replace(BodyParts.LEFT_ARM, bodyRotations.values().iterator().next().add(50, 50, 50));
            //bodyRotations.replace(BodyParts.RIGHT_ARM, bodyRotations.values().iterator().next().add(5, 5, 5));
            //bodyRotations.replace(BodyParts.LEFT_LEG, bodyRotations.values().iterator().next().add(50, 50, 50));
            //bodyRotations.replace(BodyParts.RIGHT_LEG, bodyRotations.values().iterator().next().add(5, 5, 5));
            bodyRotations.replace(BodyParts.CHEST, bodyRotations.values().iterator().next().add(-20, -20, -20));
        
            armorStand.tryOffer(Keys.BODY_ROTATIONS, bodyRotations);
           
        };
    }
    
    public void setRotation(ArmorStand armorStand, Vector3d val)
    {
        Map<BodyPart, Vector3d> bodyRotations = armorStand.get(Keys.BODY_ROTATIONS).get();
        bodyRotations.replace(BodyParts.HEAD, val);
        armorStand.tryOffer(Keys.BODY_ROTATIONS, bodyRotations);
        //this.rotation=val;
        //setPosition(position);
        
    }

}
