package net.teraoctet.actus.commands;

import static net.teraoctet.actus.Actus.sm;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.getUUID;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import static net.teraoctet.actus.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.actus.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.actus.utils.MessageManager.DATA_NOT_FOUND;
import static net.teraoctet.actus.utils.MessageManager.ONHOVER_PI_NAME;
import static net.teraoctet.actus.utils.MessageManager.TP_AT_COORDS;
import net.teraoctet.actus.utils.ServerManager;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.statistic.Statistics;

public class CommandPlayerinfo implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        //partie de la commande qui cible un <player>, exécuté que si la source a la permission et qu'elle a rempli l'argument <player>
        if(src instanceof Player && ctx.getOne("tplayer").isPresent() && src.hasPermission("actus.modo.playerinfo.others")) {
            String targetName = ctx.<String> getOne("tplayer").get();
            String targetUUID = getUUID(targetName);
            
            if(targetUUID == null){
                src.sendMessage(DATA_NOT_FOUND(targetName));
            } else { 
                APlayer aplayer = getAPlayer(targetUUID);
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                PaginationList.Builder builder = paginationService.builder(); 

                builder.title(MESSAGE("&8PlayerInfo"))
                        .contents(Text.builder().append(MESSAGE("&8Player: " + targetName))
                                .onHover(TextActions.showText(ONHOVER_PI_NAME(targetUUID)))
                                .onClick(TextActions.suggestCommand("/kick " + targetName))
                                .onShiftClick(TextActions.insertText("/ban " + targetName))
                                .toText(),
                            Text.builder().append(MESSAGE("AAAAAAA")).toText())
                        .padding(Text.of("-"))
                        .sendTo(src);

                /*src.sendMessage(Text.builder("----" + targetName + "----")
                            .onHover(TextActions.showText(Text.builder("UUID: " + targetUUID).build()))
                            .color(TextColors.DARK_GRAY)
                            .build());*/
                                        
                if(ServerManager.isOnline(targetName)){
                    Player tPlayer = getGame().getServer().getPlayer(targetName).get();
                    src.sendMessage(Text.builder(targetName + " est connect\351")
                            .onHover(TextActions.showText(Text.builder("IP: " + tPlayer.getConnection().getAddress().toString()).build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                    src.sendMessage(Text.builder("Position : World=" + tPlayer.getLocation().getExtent().getName() + " X=" + tPlayer.getLocation().getBlockX() + " Y=" + tPlayer.getLocation().getBlockY() + " Z=" + tPlayer.getLocation().getBlockZ())
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(TP_AT_COORDS()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                } else {
                    //String lastConnection = sm.dateShortToString(aplayer.getLastonline());
                    src.sendMessage(Text.builder(targetName + " est d\351connect\351")
                            //.onHover(TextActions.showText(Text.builder("Derni\350re connexion: " + lastConnection.toString()).build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                    src.sendMessage(Text.builder("Derni\350re position : " + aplayer.getLastposition())
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(TP_AT_COORDS()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                }

                src.sendMessage(MESSAGE("&9--------------------"));
                
                src.sendMessage(MESSAGE("&eNb de Home(s) : " + aplayer.getHomes().size()));
                src.sendMessage(MESSAGE("&eBank: " + aplayer.getMoney() + " \351meraudes"));
                src.sendMessage(Text.builder("Droits suppl\351mentaires accordés : ")
                            .onHover(TextActions.showText(Text.builder("Points accumul\351s : ").build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                src.sendMessage(MESSAGE("&9--------------------"));
                src.sendMessage(Text.builder("> Statistiques minage <")
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(Text.builder("Affiche les statistiques des blocs min\351s").build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                src.sendMessage(MESSAGE("&9--------------------"));
                return CommandResult.success();
            }
        } 
        
        //si la source est un joueur qui n'a pas rempli l'argument <player>, affiche les infos de la source
        else if (src instanceof Player && src.hasPermission("actus.player.playerinfo")) {
            Player player = (Player) src;
            APlayer aplayer = getAPlayer(player.getIdentifier());
            long seconds = (long)aplayer.getOnlinetime()/1000;
            long hours = seconds / 3600;

            
            src.sendMessage(MESSAGE("&9--------------------"));
            src.sendMessage(MESSAGE("&3Mes infos : " + player.getName()));
            src.sendMessage(MESSAGE("&9--------------------"));
            src.sendMessage(MESSAGE("&3Temps de connexion : " + sm.longToTime((long)aplayer.getOnlinetime()-(3600*1000))));
            src.sendMessage(MESSAGE("&3Nombre de points accumulés : "));
            src.sendMessage(MESSAGE("&3Droits suppl\351mentaires accordés : "));
            src.sendMessage(MESSAGE("&9--------------------"));
            src.sendMessage(MESSAGE("&3Nb Desces : " + player.getStatisticData().get(Statistics.DEATHS).get()));
            src.sendMessage(MESSAGE("&3Nb Mobs tu\351s : " + player.getStatisticData().get(Statistics.MOB_KILLS).get()));
            src.sendMessage(MESSAGE("&3Nb Kills : " + player.getStatisticData().get(Statistics.PLAYER_KILLS).get()));
            src.sendMessage(MESSAGE("&9--------------------"));
            src.sendMessage(MESSAGE("&3Nb Troc villageois : " + player.getStatisticData().get(Statistics.TRADED_WITH_VILLAGER).get()));
            src.sendMessage(MESSAGE("&3Nb disque jou\351 : " + player.getStatisticData().get(Statistics.RECORD_PLAYED).get()));
            src.sendMessage(MESSAGE("&3Nb disque jou\351 : " + player.getStatisticData().get(Statistics.RECORD_PLAYED).get()));
            Long dead = player.getStatisticData().get(Statistics.DEATHS).get();
            //playerPlots(player.getIdentifier());
            return CommandResult.success();
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
