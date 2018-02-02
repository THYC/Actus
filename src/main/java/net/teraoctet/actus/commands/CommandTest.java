package net.teraoctet.actus.commands;

import java.util.Optional;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import net.teraoctet.actus.plot.Wedit;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
//import static jdk.nashorn.internal.codegen.ObjectClassGenerator.pack;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.entity.living.player.tab.TabListEntry;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;



//import com.sk89q.worldedit.LocalSession;
//import com.sk89q.worldedit.sponge.SpongeWorldEdit;


public class CommandTest implements CommandExecutor {
    
    //private SpongeWorldEdit worldedit;
    //Wedit w = new Wedit();
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
        //Data.commit();
        Player player = (Player) src;
        /*player.sendMessage(NO_PERMISSIONS());
        Optional<Location<World>> locChest1 = Optional.empty();
            BlockRay<World> playerBlockRay = BlockRay.from(player).distanceLimit(10).build(); 
            while (playerBlockRay.hasNext())            { 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

               locChest1 = Optional.of(currentHitRay.getLocation());           
            } 

            if (locChest1.isPresent()){
                
                locChest1.get().setBlock(locChest1.get().getBlock().with(Keys.OPEN, true).get());
           }
            TabList tablist = player.getTabList();*/
            
            //tablist.setHeader(Text.of(TextColors.GOLD, "Crafteur nocturne"));
            //tablist.setFooter(Text.of(TextColors.RED, "The tab list footer"));
            //tablist.
            /*Optional<TabListEntry> entry = tablist.getEntry(player.getUniqueId()); //)TabListEntry.builder()
            entry.get().setDisplayName(MESSAGE("[&l&eAssassin]&r&b" + player.getName()));*/
            //entry.get().setLatency(10000000);
            //entry.get().setGameMode(GameModes.SPECTATOR);

            /*.list(tablist)
                .gameMode(GameModes.SURVIVAL)
                .profile(player.getProfile())
                .build();
            tablist.addEntry(entry);*/
            
            /*TabListEntry entry = TabListEntry.builder()
                .list(tablist)
                .displayName(Text.of("THYC"))
                .latency(0)
                .profile(player.getProfile())
                .gameMode(GameModes.SURVIVAL)
                .build();
            tablist.addEntry(entry);*/

            //Optional<TabListEntry> optional = tablist.getEntry(player.getUniqueId());
            //if (optional.isPresent()) {
                //TabListEntry entry = optional.get();
           // }

//Avec ceci, nous pouvons utiliser les méthodes sur TabListEntry pour modifier le mode de jeu, la latence, et le nom d’affichage de l’entrée :

//entry.setDisplayName(Text.of("Pretender Spongie"));
//entry.setLatency(1000);
//entry.setGameMode(GameModes.SPECTATOR);


        
        
        //player.getWorld().getChunkAtBlock(player.getLocation().getBlockPosition()).get().;
        //spawnEntity(player.getLocation(),src);
//Set<Context> context;

player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "sponge", Tristate.TRUE);
//player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "luckperms", Tristate.TRUE);
player.getSubjectData().setOption(SubjectData.GLOBAL_CONTEXT,"prefix","&9-TEST-");
//player.sendMessage(MESSAGE("xx " + player.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT).getAllPermissions().));

//Subject subject = player.getContainingCollection().getDefaults();
//player.sendMessage(MESSAGE("xx " + subjectgetOption("prefix").orElse("");
//Optional<Chunk> c = player.getLocation().getExtent().getChunk(player.getLocation().getBlockPosition());




        return CommandResult.success();

    }
}