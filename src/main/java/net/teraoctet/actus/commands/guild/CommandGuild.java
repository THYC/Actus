package net.teraoctet.actus.commands.guild;

import java.util.List;
import static net.teraoctet.actus.Actus.gdm;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.guild.GuildManager;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Config.GUILD_MAX_NUMBER_OF_MEMBER;
import static net.teraoctet.actus.utils.Data.getGuild;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;
import static net.teraoctet.actus.utils.MessageManager.GUIDE_GUILD;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_GUILD;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_DELETE;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_DEPOSIT;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_INVIT;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_LEAVE;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_MOREACTIONS;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_REMOVEMEMBER;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_RENAME;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_SETGRADE;
import static net.teraoctet.actus.utils.MessageManager.GUILD_ONHOVER_WITHDRAWAL;
import static net.teraoctet.actus.utils.MessageManager.WRONG_RANK;

public class CommandGuild implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("actus.player.guild")) {
            APlayer aplayer = getAPlayer(src.getIdentifier());
            
            //si le joueur est membre d'une guild
            if(GuildManager.hasAnyGuild(aplayer)) {
                Guild gguild = getGuild(aplayer.getID_guild());
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                PaginationList.Builder builder = paginationService.builder();  

                //Menu des actions, affiché lorsque showActionsMenu
                if(ctx.hasAny("displayaction")){
                    //Si le joueur a un grade suffisant dans la guild pour accéder à ce menu
                    if(aplayer.getGuildRank() <= 3) {
                        builder.header(Text.builder().append(MESSAGE("&2Actions:")).toText())
                                .contents(Text.builder().append(MESSAGE("&2+ &aAjouter un membre"))
                                        .onClick(TextActions.suggestCommand("/guild invit "))    
                                        .onHover(TextActions.showText(GUILD_ONHOVER_INVIT())).toText(),
                                    Text.builder().append(MESSAGE("&2+ &aChanger le grade d'un membre"))
                                        .onClick(TextActions.suggestCommand("/guild setplayergrade "))    
                                        .onHover(TextActions.showText(GUILD_ONHOVER_SETGRADE())).toText(),
                                    Text.builder().append(MESSAGE("&2+ &aRenvoyer un membre"))
                                        .onClick(TextActions.suggestCommand("/guild removeplayer "))    
                                        .onHover(TextActions.showText(GUILD_ONHOVER_REMOVEMEMBER())).toText(),
                                    Text.builder().append(MESSAGE("&2+ &aRetrait bancaire"))
                                        .onClick(TextActions.suggestCommand("/guild withdraw "))    
                                        .onHover(TextActions.showText(GUILD_ONHOVER_WITHDRAWAL())).toText(),
                                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aRenommer la guilde"))
                                        .onClick(TextActions.suggestCommand("/guild rename "))    
                                        .onHover(TextActions.showText(GUILD_ONHOVER_RENAME())).toText(),
                                    Text.builder().append(MESSAGE("&2+ &aSupprimer la guilde"))
                                        .onClick(TextActions.suggestCommand("/guild delete "))    
                                        .onHover(TextActions.showText(GUILD_ONHOVER_DELETE())).toText())
                                .padding(Text.of("-"))
                                .sendTo(src);
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WRONG_RANK());
                    }
                //Menu affiché par défaut   
                } else {
                    int guildSize = gdm.getGuildPlayers(gguild.getID()).size();
                    String playerRank = gdm.rankIDtoString(aplayer.getGuildRank());
                    String guildOwner = gdm.getOwner(gguild.getID()).getName();
                    List listRank2 = gdm.getGuildPlayers(gguild.getID(), 2);
                    List listRank3 = gdm.getGuildPlayers(gguild.getID(), 3);
                    List listRank4 = gdm.getGuildPlayers(gguild.getID(), 4);
                    List listRank5 = gdm.getGuildPlayers(gguild.getID(), 5);
                    
                    builder.title(Text.builder().append(MESSAGE(gguild.getName() + "&r - &2Membres: " + guildSize + " / " + GUILD_MAX_NUMBER_OF_MEMBER())).toText())
                            .contents(Text.builder().append(MESSAGE("&2Vous \352tes \"" + playerRank + "\" de " + gguild.getName()))
                                            .onShiftClick(TextActions.insertText("/guild leave"))
                                            .onHover(TextActions.showText(GUILD_ONHOVER_LEAVE())).toText(),
                                    Text.builder().append(MESSAGE("&2Chef : &a" + guildOwner)).toText(),
                                    Text.builder().append(MESSAGE("&a- Sous-chef (" + listRank2.size() + ")"))
                                            .onClick(TextActions.runCommand("/guild memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Sous-chef(s): " + listRank2))).toText(),
                                    Text.builder().append(MESSAGE("&a- Officier (" + listRank3.size() + ")"))
                                            .onClick(TextActions.runCommand("/guild memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Officier(s): " + listRank3))).toText(),
                                    Text.builder().append(MESSAGE("&a- Membre (" + listRank4.size() + ")"))
                                            .onClick(TextActions.runCommand("/guild memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Membre(s): " + listRank4))).toText(),
                                    Text.builder().append(MESSAGE("&a- Recrue (" + listRank5.size() + ")"))
                                            .onClick(TextActions.runCommand("/guild memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Recrue(s): " + listRank5))).toText(),
                                    Text.builder().append(MESSAGE("&2Bank de Faction : &a" + gguild.getMoney() + " \351meraudes"))
                                            .onClick(TextActions.suggestCommand("/guild depot "))
                                            .onHover(TextActions.showText(GUILD_ONHOVER_DEPOSIT())).toText(),
                                    Text.builder().append(MESSAGE("&2+ Afficher les Actions"))
                                            .onClick(TextActions.runCommand("/guild -a"))
                                            .onHover(TextActions.showText(GUILD_ONHOVER_MOREACTIONS())).toText())
                            .padding(Text.of("-"))
                            .sendTo(src); 
                    
                    return CommandResult.success();
                }
            }
            
            //si le joueur n'est dans aucune guild
            else {
                src.sendMessage(NO_GUILD());
                src.sendMessage(GUIDE_GUILD());
                src.sendMessage(Text.builder().append(MESSAGE("&bCliquez ici pour en cr\351er une !"))
                        .onClick(TextActions.suggestCommand("/guild create "))    
                        .onHover(TextActions.showText(MESSAGE("&eCr\351er une nouvelle guilde\n&f/guild create <name>")))
                        .toText()); 
            }
        }
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}

