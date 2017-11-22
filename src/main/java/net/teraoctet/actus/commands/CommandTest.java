package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static jdk.nashorn.internal.codegen.ObjectClassGenerator.pack;
import static net.teraoctet.actus.Actus.getWESelection;
import static net.teraoctet.actus.Actus.plotManager;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.sm;
import static net.teraoctet.actus.Actus.tm;
import net.teraoctet.actus.troc.EnumTransactType;
import net.teraoctet.actus.troc.Troc;
import net.teraoctet.actus.troc.TrocManager;
import net.teraoctet.actus.utils.Data;
import net.teraoctet.actus.utils.DeSerialize;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.world.WorldManager.spawnParticles;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import static org.spongepowered.api.Sponge.getServer;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Beacon;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.event.cause.entity.health.HealthModifier;
import org.spongepowered.api.item.ItemTypes;
import static org.spongepowered.api.item.ItemTypes.COMPASS;
import org.spongepowered.api.item.inventory.ItemStack;
import static org.spongepowered.api.item.inventory.ItemStackBuilderPopulators.data;
import org.spongepowered.api.item.merchant.TradeOffer;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.resourcepack.ResourcePacks;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;



//import com.sk89q.worldedit.LocalSession;
//import com.sk89q.worldedit.sponge.SpongeWorldEdit;


public class CommandTest implements CommandExecutor {
    
    //private SpongeWorldEdit worldedit;
    
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
        try {
            //Data.commit();
            Player player = (Player) src;
            //spawnEntity(player.getLocation(),src);
//Set<Context> context;

//player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "sponge", Tristate.TRUE);
//player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "luckperms", Tristate.TRUE);
//player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT,"prefix","&9-TEST-");
//player.sendMessage(MESSAGE("xx " + player.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT).getAllPermissions().));
URL url = new URL("Equanimity.zip"); //Some instantiated URL object
URI uri = url.toURI();
ResourcePack pack = ResourcePacks.fromUriUnchecked(uri);
player.sendResourcePack(pack);
//Subject subject = player.getContainingCollection().getDefaults();
//player.sendMessage(MESSAGE("xx " + subjectgetOption("prefix").orElse("");




//Optional<Chunk> c = player.getLocation().getExtent().getChunk(player.getLocation().getBlockPosition());

//c.get().unloadChunk();
/*Optional<Location> optlocation = Optional.empty();
BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build();
while (playerBlockRay.hasNext())
{
BlockRayHit<World> currentHitRay = playerBlockRay.next();

if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.CHEST))
{
optlocation = Optional.of(currentHitRay.getLocation());
break;
}
}

if (optlocation.isPresent()){
Optional<TileEntity> chestBlock = optlocation.get().getTileEntity();
TileEntity tileChest = chestBlock.get();
String chestName = "TROC";
if(tileChest.get(Keys.DISPLAY_NAME).get().toPlain().equalsIgnoreCase(chestName)){
if(sm.locDblChest(optlocation.get()).isPresent()){
String loc = DeSerialize.location(optlocation.get());
player.sendMessage(MESSAGE(trocManager.getTroc(loc,0).get().getItemName()));

}
}else{
player.sendMessage(MESSAGE("&bce coffre n'est pas un coffre pour le troc !"));
}
}else{
player.sendMessage(MESSAGE("&bAucun coffre dans la vision !"));
}*/

//plugin.getLogger().info(player.getItemInHand(HandTypes.MAIN_HAND).get().getItem().getName());

//if(!arg2.isPresent())arg2 = Optional.of("1");
//player.sendMessages(MESSAGE("we : " + getWESelection(player).get().getMaxPos()));oc



//tm.displayScroreboard(player);

//Troc troc = new Troc("&9test item",EnumTransactType.BUY, 10, 0.3D,
//player.getItemInHand(HandTypes.MAIN_HAND).get(), player.getName(), player.getIdentifier(), 0);


//if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
//player.setItemInHand(HandTypes.MAIN_HAND, tm.setItemTroc(player,troc).get());
//}


//getGame().getPluginManager().getPlugin("worldedit").ifPresent(worldedit ->  {
//if (worldedit.getInstance().isPresent() || worldedit.getInstance().get() instanceof SpongeWorldEdit) {
//	this.worldedit = (SpongeWorldEdit) worldedit.getInstance().get();
//}
//});
// PluginContainer we = Sponge.getPluginManager().getPlugin("worldedit").get();
// WorldEditPlugin wep = ((WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit"));
//WorldEdit we = wep.getWorldEdit();
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
        } catch (MalformedURLException | URISyntaxException ex) {
            Logger.getLogger(CommandTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

