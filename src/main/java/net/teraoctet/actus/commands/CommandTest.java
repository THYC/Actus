package net.teraoctet.actus.commands;

import com.flowpowered.math.vector.Vector3d;
import com.sk89q.worldedit.WorldVector;
import java.awt.Container;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Map;
import java.util.Optional;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.teraoctet.actus.Actus;
import static net.teraoctet.actus.Actus.ptm;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.plot.Wedit;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.block.BlockTypes;
//import static jdk.nashorn.internal.codegen.ObjectClassGenerator.pack;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.resourcepack.ResourcePacks;
import org.spongepowered.api.statistic.Statistics;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;



//import com.sk89q.worldedit.LocalSession;
//import com.sk89q.worldedit.sponge.SpongeWorldEdit;


public class CommandTest implements CommandExecutor {
    
    //private SpongeWorldEdit worldedit;
    Wedit w = new Wedit();
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
        //Data.commit();
        Player player = (Player) src;
        
        String temp = sm.longToTime(player.getStatisticData().get(Statistics.TIME_PLAYED).get());
        Long dead = player.getStatisticData().get(Statistics.DEATHS).get();
        player.sendMessage(MESSAGE("Temps : " + temp));
        player.sendMessage(MESSAGE("Dead : " + dead));
        Optional<Location<World>> optlocation = Optional.empty();
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
            player.getMortalData().lastAttacker().get().get();//.resetBlockChange(optlocation.get().getBlockPosition());
        //spawnEntity(player.getLocation(),src);
//Set<Context> context;

//player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "sponge", Tristate.TRUE);
//player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "luckperms", Tristate.TRUE);
//player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT,"prefix","&9-TEST-");
//player.sendMessage(MESSAGE("xx " + player.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT).getAllPermissions().));

//Subject subject = player.getContainingCollection().getDefaults();
//player.sendMessage(MESSAGE("xx " + subjectgetOption("prefix").orElse("");
//Optional<Chunk> c = player.getLocation().getExtent().getChunk(player.getLocation().getBlockPosition());




        return CommandResult.success();

    }
}