package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;


import static net.teraoctet.actus.Actus.playerManager;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.bookmessage.BookManager;
import net.teraoctet.actus.bookmessage.ConfigBook;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.skin.MineSkin;
import net.teraoctet.actus.utils.MessageManager;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import net.teraoctet.actus.world.Reforestation;
import static net.teraoctet.actus.world.WorldManager.spawnParticles;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.persistence.DataTranslators;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
//import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.action.TextActions;
import static org.spongepowered.api.text.channel.MessageChannel.world;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;
import org.spongepowered.api.world.WorldBorder.ChunkPreGenerate;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.statistic.Statistics;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.EntityUniverse;

public class CommandTest implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
 
        Player player = (Player) src;
              
       
        
        /*entity.offer(Keys.DISPLAY_NAME, MESSAGE("&bROBERT"));
        entity.offer(Keys.AI_ENABLED, true);
        entity.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
        //entity.offer(Keys.INVISIBLE, player.getUniqueId());
        player.getLocation().getExtent().spawnEntity(entity, Cause.of(NamedCause.source(plugin)));
        entity.setVelocity(Vector3d.createDirectionDeg(50, 5));
        entity.setVelocity(Vector3d.createDirectionDeg(10, 10));
        entity.setVelocity(Vector3d.createDirectionDeg(10, 60));*/
        
        //ConfigBook book = new ConfigBook("newbie");
        /*try {
            book.init();
        } catch (IOException ex) {
            Logger.getLogger(CommandTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectMappingException ex) {
            Logger.getLogger(CommandTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/

     
        ConfigBook configBook = new ConfigBook();
        
        BookView bookView = configBook.book;
            
        player.sendBookView(bookView);
        
        //plugin.getLogger().info(entity.toContainer().toString());
        //Text t = Text.builder().append(MESSAGE("&enoCommand_: &b &7Interdiction de taper des commandes"))
                                    //.onClick(TextActions.executeCallback(Cb(entity)))    
                                    //.onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText();
        /*BookView.Builder bv = 
                BookView.builder()
                        .author(Text.of(TextColors.GOLD, "actus"))
                        .title(Text.of(TextColors.GOLD, "ItemShop"));
        Text text = Text.builder().append(MESSAGE("&enoEnter___: &b[] &7Interdiction d'entrer sur la parcelle"))
                                    .onClick(TextActions.suggestCommand("sun"))    
                                    .onHover(TextActions.showText(MESSAGE("Click pour changer la valeur"))).toText();
        bv.addPage(text);
        BookManager bm = new BookManager();
        try {
            bm.saveBook(bv.build());
        } catch (IOException ex) {
            Logger.getLogger(CommandTest.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        player.sendBookView(bv.build());*/
        
        
        
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
