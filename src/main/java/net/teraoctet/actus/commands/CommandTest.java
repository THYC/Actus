package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;

import java.util.Map;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.function.Consumer;
import static net.teraoctet.actus.world.WorldManager.spawnParticles;
import org.spongepowered.api.block.tileentity.carrier.Beacon;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.merchant.TradeOffer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;




public class CommandTest implements CommandExecutor {
    
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
 
        Player player = (Player) src;
      spawnEntity(player.getLocation(),src);
                
        //spawnParticles(player.getLocation(),50.0,true);
       
        
        /*ItemStack itemStack = ItemStack.of(ItemTypes.STICK, 1);
        List<Text> lore = Lists.newArrayList(Text.of(TextColors.LIGHT_PURPLE, "This wand emits pure magic."));
        itemStack.offer(Keys.HIDE_ENCHANTMENTS,true);
        itemStack.offer(Keys.ITEM_LORE, lore);
        player.setItemInHand(HandTypes.MAIN_HAND, itemStack);*/
        
        
        //Optional<Map<Statistic,Long>> stat = player.get(Keys.STATISTICS);
        
       // plugin.getLogger().info(player.toContainer().toString());
            
            
            
           /* plugin.getLogger().info(optlocation.get().toContainer().getContainer().toString());*/
            
       
        
        /*Inventory inv = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
        .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Quests: Page " + (1 + 1))))
        .build(plugin);
        
        player.openInventory(inv,Cause.source(inv).build());*/
        
       /* spawnParticles(player.getLocation(),50,true);*/
       
        /*TradeOffer offer = TradeOffer.builder()
            .firstBuyingItem(ItemStack.of(ItemTypes.DIRT, 5))
            .sellingItem(ItemStack.of(ItemTypes.GRASS, 3))
            .uses(0)
            .maxUses(4)
            .canGrantExperience(false)
            .build();*/
        
       
        return CommandResult.success();
    }
    
    public void spawnEntity(Location<World> location, CommandSource src)
	{
		Extent extent = location.getExtent();
		//Entity lightning = extent.createEntity(EntityTypes.LIGHTNING, location.getPosition());
                Entity lightning = extent.createEntity(EntityTypes.AREA_EFFECT_CLOUD, location.getPosition());
                
		extent.spawnEntity(lightning);
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
