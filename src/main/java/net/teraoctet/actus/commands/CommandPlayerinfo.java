package net.teraoctet.actus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.actus.Actus.gdm;
import static net.teraoctet.actus.Actus.ptm;
import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getUUID;
import static net.teraoctet.actus.utils.Data.getGuild;
import net.teraoctet.actus.utils.DeSerialize;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.DATA_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.PI_ADMIN_ONLINE_ONHOVER_NAME;
import static net.teraoctet.actus.utils.MessageManager.PI_ADMIN_OFFLINE_ONHOVER_NAME;
import static net.teraoctet.actus.utils.MessageManager.PI_ADMIN_ONHOVER_TP;
import static net.teraoctet.actus.utils.MessageManager.PI_ONHOVER_GUILD;
import static net.teraoctet.actus.utils.MessageManager.PI_ONHOVER_HOME;
import static net.teraoctet.actus.utils.MessageManager.PI_ONHOVER_NOGUILD;
import static net.teraoctet.actus.utils.MessageManager.PI_ONHOVER_PLOT;
import net.teraoctet.actus.utils.ServerManager;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.statistic.Statistics;

public class CommandPlayerinfo implements CommandExecutor 
{
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) 
    {
        if (src instanceof ConsoleSource) 
        {
            src.sendMessage(NO_CONSOLE());
            return CommandResult.empty();
        }
        
        /**********************************************************************/
        /*                      PLAYERINFO JOUEUR                             */
        /**********************************************************************/
        if (!src.hasPermission("actus.modo.playerinfo.others") || !ctx.getOne("tplayer").isPresent())
        {
            List<Text> contents = new ArrayList<>();
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getIdentifier());
            //long seconds = (long)aplayer.getOnlinetime()/1000;
            //long hours = seconds / 3600;
            
            if(GuildManager.hasAnyGuild(aplayer))
            {
                Guild guild = getGuild(aplayer.getID_guild());
                contents.add(Text.builder().append(MESSAGE("&eGuilde: " + guild.getName()))
                                    .onHover(TextActions.showText(PI_ONHOVER_GUILD(gdm.rankIDtoString(aplayer.getGuildRank()))))
                                    .onClick(TextActions.runCommand("/guild"))
                                    .toText());
            } else {
                contents.add(Text.builder().append(MESSAGE("&eGuilde: AUCUNE"))
                        .onHover(TextActions.showText(PI_ONHOVER_NOGUILD()))
                        .onClick(TextActions.suggestCommand("/guild create "))
                        .toText());
            }
            
            contents.add(Text.builder().append(MESSAGE("&eNombre de Home(s): " + aplayer.getHomes().size()))
                                .onHover(TextActions.showText(PI_ONHOVER_HOME()))
                                //.onClick(TextActions.runCommand("/"))
                                .toText());
            contents.add(Text.builder().append(MESSAGE("&eNombre de Parcelle(s): " + ptm.getNbPlot(player.getIdentifier(), 0)))
                                .onHover(TextActions.showText(PI_ONHOVER_PLOT()))
                                .onClick(TextActions.runCommand("/p list"))
                                .toText());           
            contents.add(Text.builder().append(MESSAGE("------------------------------")).toText());
            contents.add(Text.builder().append(MESSAGE("&eTemps de connexion: " +  sm.longToTime((long)aplayer.getOnlinetime()-(3600*1000)))).toText());
            if(player.getStatisticData().get(Statistics.DEATHS).isPresent())
                contents.add(Text.builder().append(MESSAGE("&eNombre de morts: " + player.getStatisticData().get(Statistics.DEATHS).get())).toText());
            if(player.getStatisticData().get(Statistics.PLAYER_KILLS).isPresent())
                contents.add(Text.builder().append(MESSAGE("&eJoueurs tués: " + player.getStatisticData().get(Statistics.PLAYER_KILLS).get())).toText());
            
            //pagination service
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            PaginationList.Builder builder = paginationService.builder(); 

            builder.title(MESSAGE("&6Mes infos: " + player.getName()))
                    .contents(contents)
                    .padding(Text.of('-'))
                    .sendTo(src);

            return CommandResult.success();
        }
        
        /**********************************************************************/
        /*                      PLAYERINFO ADMIN                              */
        /**********************************************************************/
        String targetName = ctx.<String> getOne("tplayer").get();
        String targetUUID = getUUID(targetName);
            
        if(targetUUID == null)
        {
            src.sendMessage(DATA_NOT_FOUND(targetName));
            return CommandResult.empty();
        }
            
        APlayer aplayer = getAPlayer(targetUUID);
        List<Text> contents = new ArrayList<>();
           
        //joueur cible connecté
        if(ServerManager.isOnline(targetName)) 
        {
            Player tPlayer = getGame().getServer().getPlayer(targetName).get();
            
            contents.add(Text.builder().append(MESSAGE("&ePlayer: " + targetName))
                                .onHover(TextActions.showText(PI_ADMIN_ONLINE_ONHOVER_NAME(targetUUID)))
                                .onClick(TextActions.suggestCommand("/kick " + targetName))
                                .onShiftClick(TextActions.insertText("/ban " + targetName))
                                .toText());
            contents.add(Text.builder().append(MESSAGE("&e" + targetName + " est connect\351"))
                                .onHover(TextActions.showText(MESSAGE("IP: " + tPlayer.getConnection().getAddress().toString())))
                                .toText());
            contents.add(Text.builder().append(MESSAGE("&ePosition: World=" + tPlayer.getLocation().getExtent().getName()
                                    + " X=" + tPlayer.getLocation().getBlockX()
                                    + " Y=" + tPlayer.getLocation().getBlockY()
                                    + " Z=" + tPlayer.getLocation().getBlockZ()))
                                .onHover(TextActions.showText(PI_ADMIN_ONHOVER_TP()))
                                .onClick(TextActions.executeCallback(sm.callTP(Optional.empty(), DeSerialize.location(tPlayer.getLocation()))))
                                .toText());
        } 
        
        //joueur cible déconnecté
        else 
        {
            String lastConnection = sm.longToDateString(aplayer.getLastonline());
            
            contents.add(Text.builder().append(MESSAGE("&ePlayer: " + targetName))
                                .onHover(TextActions.showText(PI_ADMIN_OFFLINE_ONHOVER_NAME(targetUUID)))
                                .onShiftClick(TextActions.insertText("/ban " + targetName))
                                .toText());
            contents.add(Text.builder().append(MESSAGE("&e" + targetName + " est d\351connect\351"))
                                .onHover(TextActions.showText(MESSAGE("Derni\350re connexion: " + lastConnection)))
                                .toText());
            contents.add(Text.builder().append(MESSAGE("&eDerni\350re position: " + aplayer.getLastposition()))
                                .onHover(TextActions.showText(PI_ADMIN_ONHOVER_TP()))
                                .onClick(TextActions.executeCallback(sm.callTP(Optional.empty(), aplayer.getLastposition())))
                                .toText());
        }
        contents.add(Text.builder().append(MESSAGE("------------------------------")).toText());

        if(GuildManager.hasAnyGuild(aplayer))
        {
            Guild guild = getGuild(aplayer.getID_guild());
            contents.add(Text.builder().append(MESSAGE("&eGuilde: " + guild.getName()))
                                .onHover(TextActions.showText(PI_ONHOVER_GUILD(gdm.rankIDtoString(aplayer.getGuildRank()))))
                                //.onClick(TextActions.runCommand("/ "))
                                .toText());
        } else {
            contents.add(Text.builder().append(MESSAGE("&eGuilde: AUCUNE")).toText());
        }
        
        contents.add(Text.builder().append(MESSAGE("&eNb de Home(s): " + aplayer.getHomes().size()))
                                .onHover(TextActions.showText(PI_ONHOVER_HOME()))
                                //.onClick(TextActions.runCommand("/ "))
                                .toText());
        contents.add(Text.builder().append(MESSAGE("&eNb de Parcelle(s): "))
                                .onHover(TextActions.showText(PI_ONHOVER_PLOT()))
                                .onClick(TextActions.runCommand("/p list " + targetName))
                                .toText());
        contents.add(Text.builder().append(MESSAGE("------------------------------")).toText());
        contents.add(Text.builder().append(MESSAGE("&eFonds: " + aplayer.getMoney())).toText());
        contents.add(Text.builder().append(MESSAGE("&eLevel: " + aplayer.getLevel())).toText());
        contents.add(Text.builder().append(MESSAGE("&eFly: " + aplayer.getFlymode())).toText());
        
        //pagination service
        PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
        PaginationList.Builder builder = paginationService.builder(); 
        
        builder.title(MESSAGE("&6PlayerInfo - ADMIN"))
                .contents(contents)
                .padding(Text.of('-'))
                .sendTo(src);
        
        return CommandResult.success();
    }
}
