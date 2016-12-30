package net.teraoctet.actus.commands;

import net.teraoctet.actus.commands.chest.CommandChestAdd;
import net.teraoctet.actus.commands.chest.CommandChest;
import net.teraoctet.actus.commands.chest.CommandChestInfo;
import net.teraoctet.actus.commands.chest.CommandChestRemove;
import net.teraoctet.actus.commands.economy.CommandBank;
import net.teraoctet.actus.commands.economy.CommandSignBank;
import net.teraoctet.actus.commands.portal.CommandPortalList;
import net.teraoctet.actus.commands.portal.CommandPortalMsg;
import net.teraoctet.actus.commands.portal.CommandPortal;
import net.teraoctet.actus.commands.portal.CommandPortalTPFrom;
import net.teraoctet.actus.commands.portal.CommandPortalRemove;
import net.teraoctet.actus.commands.portal.CommandPortalCreate;
import net.teraoctet.actus.commands.plot.CommandPlotRemove;
import net.teraoctet.actus.commands.plot.CommandPlotCreate;
import net.teraoctet.actus.commands.plot.CommandPlotOwnerset;
import net.teraoctet.actus.commands.plot.CommandPlot;
import net.teraoctet.actus.commands.plot.CommandPlotRemoveplayer;
import net.teraoctet.actus.commands.plot.CommandPlotMsg;
import net.teraoctet.actus.commands.plot.CommandPlotFlag;
import net.teraoctet.actus.commands.plot.CommandPlotExpand;
import net.teraoctet.actus.commands.plot.CommandPlotAddplayer;
import net.teraoctet.actus.commands.plot.CommandPlotList;
import net.teraoctet.actus.commands.plot.CommandPlotFlaglist;
import net.teraoctet.actus.commands.plot.CommandPlotSale;
import net.teraoctet.actus.commands.guild.CommandGuild;
import net.teraoctet.actus.commands.guild.CommandGuildWithdrawal;
import net.teraoctet.actus.commands.guild.CommandGuildDelete;
import net.teraoctet.actus.commands.guild.CommandGuildSetowner;
import net.teraoctet.actus.commands.guild.CommandGuildDeposit;
import net.teraoctet.actus.commands.guild.CommandGuildCreate;
import net.teraoctet.actus.commands.guild.CommandGuildRename;
import net.teraoctet.actus.commands.guild.CommandGuildInvit;
import net.teraoctet.actus.commands.guild.CommandGuildRemoveplayer;
import net.teraoctet.actus.commands.guild.CommandGuildList;
import net.teraoctet.actus.commands.guild.CommandGuildMemberslist;
import net.teraoctet.actus.commands.guild.CommandGuildLeave;
import net.teraoctet.actus.commands.plot.CommandPlotLevel;
import net.teraoctet.actus.commands.plot.CommandPlotTP;
import net.teraoctet.actus.commands.economy.CommandSetName;
import net.teraoctet.actus.commands.economy.CommandShopCreate;
import net.teraoctet.actus.commands.economy.CommandShopList;
import net.teraoctet.actus.commands.economy.CommandShopPurchase;
import net.teraoctet.actus.commands.economy.CommandShopSell;
import net.teraoctet.actus.commands.plot.CommandPlotClaim;
import net.teraoctet.actus.commands.plot.CommandPlotListNameAllowed;
import net.teraoctet.actus.commands.plot.CommandPlotSetLevel;
import net.teraoctet.actus.commands.plot.CommandPlotTag;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;

public class CommandManager {
        
        public CommandSpec CommandActus = CommandSpec.builder()
                .description(Text.of("/actus"))
                .executor(new CommandActus())
                .build();
    
        public CommandSpec CommandSetName = CommandSpec.builder()
                .description(Text.of("/setname <name>"))
                .permission("actus.admin.setname")
                .arguments(GenericArguments.firstParsing(
                    GenericArguments.remainingJoinedStrings(Text.of("arguments"))))
                .executor(new CommandSetName())
                .build();
    
        public CommandSpec CommandKill = CommandSpec.builder()
                .description(Text.of("/kill"))
                .permission("actus.kill")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandKill())
                .build();
        
        public CommandSpec CommandSun = CommandSpec.builder()
                .description(Text.of("/sun"))
                .permission("actus.weather.sun")
                .executor(new CommandSun())
                .build();

        public CommandSpec CommandRain = CommandSpec.builder()
                .description(Text.of("Commande rain pluie"))
                .permission("actus.weather.rain")
                .executor(new CommandRain())
                .build();
        
        public CommandSpec CommandStorm = CommandSpec.builder()
                .description(Text.of("Commande storm orage"))
                .permission("actus.weather.storm")
                .executor(new CommandStorm())
                .build();
        
        public CommandSpec CommandDay = CommandSpec.builder()
                .description(Text.of("Commande day jour"))
                .permission("actus.time.day")
                .executor(new CommandDay())
                .build();
        
        public CommandSpec CommandNight = CommandSpec.builder()
                .description(Text.of("Commande night nuit"))
                .permission("actus.time.nuit")
                .executor(new CommandNight())
                .build();
                
        public CommandSpec CommandPlotCreate = CommandSpec.builder() 
                .description(Text.of("/plot create <name> [strict]")) 
                .permission("actus.plot.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("strict")))))
                .executor(new CommandPlotCreate()) 
                .build(); 
                
        public CommandSpec CommandPlotFlag = CommandSpec.builder()
                .description(Text.of("/plot flag <flag> <0|1> [name]")) 
                .extendedDescription(Text.of("/plot flag <flag> <0|1> [name]"))
                .permission("actus.plot.flag") 
                .arguments(GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("flag"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotFlag()) 
                .build(); 
        
        public CommandSpec CommandPlotFlaglist = CommandSpec.builder()
                .description(Text.of("/plot flaglist [name]")) 
                .permission("actus.plot.flag")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotFlaglist()) 
                .build(); 
        
        public CommandSpec CommandPlotRemove = CommandSpec.builder()
                .description(Text.of("/plot remove [name]")) 
                .permission("actus.plot.remove")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotRemove()) 
                .build(); 
        
        public CommandSpec CommandPlotSale = CommandSpec.builder()
                .description(Text.of("/plot sale <price> [name]")) 
                .permission("actus.plot.sale") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("price"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotSale()) 
                .build(); 
        
        public CommandSpec CommandPlotAddplayer = CommandSpec.builder()
                .description(Text.of("/plot addplayer <player> [name]")) 
                .permission("actus.plot.addplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotAddplayer()) 
                .build();
        
        public CommandSpec CommandPlotRemoveplayer = CommandSpec.builder()
                .description(Text.of("/plot removeplayer <player> [name]")) 
                .permission("actus.plot.removeplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotRemoveplayer()) 
                .build(); 
        
        public CommandSpec CommandPlotOwnerset = CommandSpec.builder()
                .description(Text.of("/plot ownerset <player> [name]")) 
                .permission("actus.plot.ownerset") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotOwnerset()) 
                .build(); 
        
        public CommandSpec CommandPlotList = CommandSpec.builder()
                .description(Text.of("/plot list [player]")) 
                .permission("actus.plot.list") 
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("tplayer"))))
                .executor(new CommandPlotList()) 
                .build(); 
        
        public CommandSpec CommandPlotExpand = CommandSpec.builder()
                .description(Text.of("/plot expand <value>")) 
                .permission("actus.plot.expand") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("point"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("axe")))))
                .executor(new CommandPlotExpand()) 
                .build(); 
        
        public CommandSpec CommandPlotMsg = CommandSpec.builder()
                .description(Text.of("/plot msg [message]"))
                .permission("actus.plot.msg")
                .arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("arguments"))))
                .executor(new CommandPlotMsg())
                .build();
        
        public CommandSpec CommandPlotTP = CommandSpec.builder()
                .description(Text.of("/plot tp <name>"))
                .permission("actus.plot.tp")
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))))
                .executor(new CommandPlotTP())
                .build();
        
        public CommandSpec CommandPlotLevel = CommandSpec.builder()
                .description(Text.of("/plot level [level]")) 
                .permission("actus.plot.level") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotLevel()) 
                .build(); 
        
        public CommandSpec CommandPlotSetLevel = CommandSpec.builder()
                .description(Text.of("/plot level [level]")) 
                .permission("actus.plot.level") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("level")))))
                .executor(new CommandPlotSetLevel()) 
                .build(); 
        
        public CommandSpec CommandPlotTag = CommandSpec.builder()
                .description(Text.of("/plot tag")) 
                .permission("actus.plot.tag") 
                .executor(new CommandPlotTag()) 
                .build(); 
        
        public CommandSpec CommandPlotListNameAllowed = CommandSpec.builder()
                .description(Text.of("/plot allow")) 
                .permission("actus.plot") 
                .executor(new CommandPlotListNameAllowed()) 
                .build(); 
        
        public CommandSpec CommandPlot = CommandSpec.builder()
                .description(Text.of("/plot")) 
                .permission("actus.plot") 
                .child(CommandPlotCreate, "create")
                .child(CommandPlotListNameAllowed, "player", "habitant", "allow", "joueur")
                .child(CommandPlotExpand, "expand")
                .child(CommandPlotList, "list")
                .child(CommandPlotFlag, "flag")
                .child(CommandPlotFlaglist, "flaglist")
                .child(CommandPlotRemove, "remove")
                .child(CommandPlotSale, "sale")
                .child(CommandPlotAddplayer, "addplayer", "add")
                .child(CommandPlotRemoveplayer, "removeplayer", "remp")
                .child(CommandPlotOwnerset, "ownerset")
                .child(CommandPlotMsg, "msg")
                .child(CommandPlotTP, "tp")
                .child(CommandPlotLevel, "level")
                .child(CommandPlotSetLevel, "setlevel")
                .child(CommandPlotTag, "tag", "borne", "balise")
                .executor(new CommandPlot())
                .build();

        public CommandSpec CommandFly = CommandSpec.builder()
                .description(Text.of("/fly"))
                .permission("actus.fly")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandFly())
                .build();
        
        public CommandSpec CommandSetHome = CommandSpec.builder()
                .description(Text.of("/sethome [home]"))
                .permission("actus.sethome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandSetHome())
                .build();
        
        public CommandSpec CommandHome = CommandSpec.builder()
                .description(Text.of("/home [home]"))
                .permission("actus.home")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandHome())
                .build();
        
        public CommandSpec CommandDelhome = CommandSpec.builder()
                .description(Text.of("/delhome [home]"))
                .permission("actus.delhome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandDelhome())
                .build();
        
        public CommandSpec CommandBack = CommandSpec.builder()
                .description(Text.of("/back"))
                .permission("actus.back")
                .executor(new CommandBack())
                .build();
        
        public CommandSpec CommandLevel = CommandSpec.builder()
                .description(Text.of("/level <level> [player]"))
                .permission("actus.admin.level")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(Text.of("level"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("player")))))
                .executor(new CommandLevel())
                .build();
        
        public CommandSpec CommandSkull = CommandSpec.builder()
                .description(Text.of("/skull [name]"))
                .permission("actus.skull")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("head")))))
                .executor(new CommandSkull())
                .build();
                
        public CommandSpec CommandWorldCreate = CommandSpec.builder()
                .description(Text.of("/worldCreate <name> <environment> <gamemode> <difficulty>"))
                .permission("actus.admin.world.worldcreate")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name"))), GenericArguments.flags()
                        .valueFlag(GenericArguments.string(MESSAGE("dimension")),"d")
                        .valueFlag(GenericArguments.string(MESSAGE("generator")),"g")
                        .valueFlag(GenericArguments.string(MESSAGE("modifier")),"m")
                        .valueFlag(GenericArguments.string(MESSAGE("seed")),"s")
                        .valueFlag(GenericArguments.string(MESSAGE("gamemode")),"gm")
                        .valueFlag(GenericArguments.string(MESSAGE("difficulty")),"di")
                        .buildWith(GenericArguments.none()))
                .executor(new CommandWorldCreate())
                .build();
        
        public CommandSpec CommandWorldTP = CommandSpec.builder()
                .description(Text.of("Commande worldtp"))
                .permission("actus.world.worldtp")
                .arguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("worldName"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("target")))))
                .executor(new CommandWorldTP())
                .build();
        
        public CommandSpec CommandClearinventory = CommandSpec.builder()
                .description(Text.of("/clearinventory [player]"))
                .permission("actus.clearinventory")
                .arguments(GenericArguments.optional(GenericArguments.player(Text.of("player"))))
                .executor(new CommandClearinventory())
                .build();
        
        public CommandSpec CommandInvsee = CommandSpec.builder()
                .description(Text.of("/invsee <player>"))
                .permission("actus.invsee")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("target"))))
                .executor(new CommandInvsee())
                .build();
        
        public CommandSpec CommandPlayerinfo = CommandSpec.builder()
                .description(Text.of("/playerinfo [player]"))
                .permission("actus.playerinfo")
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("tplayer"))))
                .executor(new CommandPlayerinfo())
                .build();
        
        public CommandSpec CommandBroadcast = CommandSpec.builder()
                .description(Text.of("/broadcast [hide = 0:1] <message..>"))
                .permission("actus.broadcast")
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags()
                        .flag("-hide", "h")
                        .buildWith(GenericArguments.remainingJoinedStrings(Text.of("message")))))
                .executor(new CommandBroadcast())
                .build();
         
        public CommandSpec CommandFactionCreate = CommandSpec.builder()
                .description(Text.of("/guild create <name>")) 
                .permission("actus.guild.create") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandGuildCreate()) 
                .build();
         
        public CommandSpec CommandFactionDelete = CommandSpec.builder()
                .description(Text.of("/guild delete <name>")) 
                .permission("actus.guild.delete") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandGuildDelete()) 
                .build();
        
        public CommandSpec CommandFactionLeave = CommandSpec.builder()
                .description(Text.of("/guild leave")) 
                .permission("actus.guild.leave") 
                .executor(new CommandGuildLeave()) 
                .build();
        
        public CommandSpec CommandFactionRename = CommandSpec.builder()
                .description(Text.of("/guild rename <name>")) 
                .permission("actus.guild.rename") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandGuildRename()) 
                .build();
         
        public CommandSpec CommandFactionList = CommandSpec.builder()
                .description(Text.of("/guild list")) 
                .permission("actus.guild.list")
                .executor(new CommandGuildList()) 
                .build();
         
        public CommandSpec CommandFactionMemberslist = CommandSpec.builder()
                .description(Text.of("/guild memberslist")) 
                .permission("actus.guild.memberslist")
                .executor(new CommandGuildMemberslist()) 
                .build();
         
        public CommandSpec CommandFactionInvit = CommandSpec.builder()
                .description(Text.of("/guild invit <player>")) 
                .permission("actus.guild.invit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new CommandGuildInvit()) 
                .build();
         
        public CommandSpec CommandFactionRemoveplayer = CommandSpec.builder()
                .description(Text.of("/guild removeplayer <player>")) 
                .permission("actus.guild.removeplayer") 
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
                .executor(new CommandGuildRemoveplayer()) 
                .build();
         
        public CommandSpec CommandFactionSetplayergrade = CommandSpec.builder()
                .description(Text.of("/guild setplayergrade <player> <grade>")) 
                .permission("actus.guild.setplayergrade") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("grade")))))
                .executor(new CommandGuildRemoveplayer()) 
                .build();
        
        public CommandSpec CommandFactionSetowner = CommandSpec.builder()
                .description(Text.of("/guild setowner <player>")) 
                .permission("actus.guild.setowner") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new CommandGuildSetowner()) 
                .build();
        
        public CommandSpec CommandFactionDeposit = CommandSpec.builder()
                .description(Text.of("/guild depot <montant>")) 
                .permission("actus.guild.deposit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount"))))
                .executor(new CommandGuildDeposit()) 
                .build();
        
        public CommandSpec CommandFactionWithdrawal = CommandSpec.builder()
                .description(Text.of("/guild retrait <montant>")) 
                .permission("actus.guild.withdrawal") 
                .arguments(
                    GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount"))))
                .executor(new CommandGuildWithdrawal()) 
                .build();
         
        public CommandSpec CommandFaction = CommandSpec.builder()
                .description(Text.of("Affiche des informations sur votre guild"))
                .permission("actus.guild")
                .child(CommandFactionCreate, "create", "new", "add", "creer")
                .child(CommandFactionDelete, "delete")
                .child(CommandFactionRename, "rename")
                .child(CommandFactionLeave, "leave", "quit", "quitter")
                .child(CommandFactionMemberslist, "memberslist")
                .child(CommandFactionList, "list")
                .child(CommandFactionInvit, "invit", "inviter", "add")
                .child(CommandFactionRemoveplayer, "removeplayer")
                .child(CommandFactionSetplayergrade, "setplayergrade")
                .child(CommandFactionSetowner, "setowner")
                .child(CommandFactionDeposit, "deposit", "depot")
                .child(CommandFactionWithdrawal, "withdraw", "withdrawal", "retrait")
                .arguments(GenericArguments.flags().flag("-displayaction", "a").buildWith(GenericArguments.none()))
                .executor(new CommandGuild())
                .build();
        
        public CommandSpec CommandTest = CommandSpec.builder()
                .description(Text.of("/test [arg0] [args1]"))
                .permission("actus.admin.test")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("arg0"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("arg1")))))
                .executor(new CommandTest())
                .build();
        
        public CommandSpec CommandRocket = CommandSpec.builder() 
                .description(Text.of("Rocket Command")) 
                .permission("actus.rocket.use") 
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags() 
                        .flag("-hard", "h") 
                        .buildWith(GenericArguments.firstParsing(GenericArguments.optional(GenericArguments.player(Text.of("target"))), 
                    GenericArguments.optional(GenericArguments.string(Text.of("targets"))))))) 
                .executor(new CommandRocket()) 
                .build();
        
        public CommandSpec CommandPortalMsg = CommandSpec.builder()
                .description(Text.of("/portal msg <name> [message]"))
                .permission("actus.admin.portal")
                .arguments(GenericArguments.firstParsing(
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                    GenericArguments.remainingJoinedStrings(Text.of("arguments"))))
                .executor(new CommandPortalMsg())
                .build();
        
        public CommandSpec CommandPortalTPFrom = CommandSpec.builder() 
                .description(Text.of("/portal TPFrom <name>")) 
                .permission("actus.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalTPFrom()) 
                .build(); 
        
        public CommandSpec CommandPortalList = CommandSpec.builder() 
                .description(Text.of("/portal list")) 
                .permission("actus.admin.portal") 
                .executor(new CommandPortalList()) 
                .build(); 
        
        public CommandSpec CommandPortalRemove = CommandSpec.builder() 
                .description(Text.of("/portal remove <name>")) 
                .permission("actus.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalRemove()) 
                .build(); 
                
        public CommandSpec CommandPortalCreate = CommandSpec.builder()
                .description(Text.of("/portal create [name]"))
                .permission("actus.admin.portal")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalCreate())
                .build();
        
        public CommandSpec CommandPortal = CommandSpec.builder()
                .description(Text.of("/portal")) 
                .permission("actus.admin.portal") 
                .child(CommandPortalCreate, "create", "add")
                .child(CommandPortalRemove, "remove", "rem", "del")
                .child(CommandPortalList, "list")
                .child(CommandPortalTPFrom, "tpfrom", "tpf")
                .child(CommandPortalMsg, "msg")
                .executor(new CommandPortal())
                .build();
               
        public CommandSpec CommandMagicCompass = CommandSpec.builder()
                .description(Text.of("/mc <Direction> <name> <X> <Y> <Z>"))
                .permission("actus.magiccompass")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("direction"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("x"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("y"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("z")))))
                .executor(new CommandMagicCompass())
                .build();
        
        public CommandSpec CommandSignWrite = CommandSpec.builder()
                .description(Text.of("/write <line> <message>"))
                .permission("actus.sign.write")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(Text.of("line"))), 
                    GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("message")))))
                .executor(new CommandSignWrite())
                .build();
        
        public CommandSpec CommandSignHelp = CommandSpec.builder()
                .description(Text.of("/signhelp <name>"))
                .permission("actus.admin.sign.help")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))))
                .executor(new CommandSignHelp())
                .build();
        
        public CommandSpec CommandSignPost = CommandSpec.builder()
                .description(Text.of("/signpost <name>"))
                .permission("actus.admin.sign.post")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))))
                .executor(new CommandSignPost())
                .build();
        
        public CommandSpec CommandSignCmd = CommandSpec.builder()
                .description(Text.of("/signcmd <cmd>"))
                .permission("actus.admin.sign.cmd")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("cmd"))))
                .executor(new CommandSignCmd())
                .build();
        
        public CommandSpec CommandSignBank = CommandSpec.builder()
                .description(Text.of("/signbank <type>"))
                .permission("actus.admin.sign.bank")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))))
                .executor(new CommandSignBank())
                .build();
        
        public CommandSpec CommandShopCreate = CommandSpec.builder()
                .description(Text.of("/shopcreate <uuid> <transacttype> <price> <qte>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("uuid"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("transacttype"))),
                    GenericArguments.optional(GenericArguments.doubleNum(Text.of("price"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("qte"))))
                .executor(new CommandShopCreate())
                .build();
        
        public CommandSpec CommandShopPurchase = CommandSpec.builder()
                .description(Text.of("/shoppurchase <uuid>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("uuid"))))
                .executor(new CommandShopPurchase())
                .build();
        
        public CommandSpec CommandShopSell = CommandSpec.builder()
                .description(Text.of("/shopsell <uuid>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("uuid"))))
                .executor(new CommandShopSell())
                .build();
        
        public CommandSpec CommandTPA = CommandSpec.builder()
                .description(Text.of("/tpa <player>"))
                .permission("actus.tpa")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandTPA())
                .build();
        
        public CommandSpec CommandTPhere = CommandSpec.builder()
                .description(Text.of("/tphere <player>"))
                .permission("actus.tpa")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandTPhere())
                .build();
        
        public CommandSpec CommandTPaccept = CommandSpec.builder()
                .description(Text.of("/tpaccept"))
                .permission("actus.tpa")
                .executor(new CommandTPaccept())
                .build();
        
        public CommandSpec CommandVanish = CommandSpec.builder()
                .description(Text.of("/vanish [player]"))
                .permission("actus.vanish")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))))
                .executor(new CommandVanish())
                .build();
        
        public CommandSpec CommandInvisible = CommandSpec.builder()
                .description(Text.of("/invisible [player]"))
                .permission("actus.invisible")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))))
                .executor(new CommandInvisible())
                .build();
        
        public CommandSpec CommandEnchant = CommandSpec.builder()
                .description(Text.of("/enchant <enchantement> <level> [player]"))
                .permission("actus.enchant")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("enchantment"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("level"))),
                GenericArguments.optional(GenericArguments.player(Text.of("target"))))
                .executor(new CommandEnchant())
                .build();
        
        public CommandSpec CommandRule = CommandSpec.builder()
                .description(Text.of("/rule"))
                .permission("actus.rule")
                .executor(new CommandRule())
                .build();
        
        public CommandSpec CommandBank = CommandSpec.builder()
                .description(Text.of("/bank"))
                .permission("actus.bank")
                .executor(new CommandBank())
                .build();
        
        public CommandSpec CommandShopList = CommandSpec.builder()
                .description(Text.of("/shoplist"))
                .permission("actus.admin.shoplist")
                .executor(new CommandShopList())
                .build();
        
        public CommandSpec CommandChestAdd = CommandSpec.builder()
                .description(Text.of("/chest add [player]"))
                .permission("actus.chest")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("target")))))
                .executor(new CommandChestAdd())
                .build();
        
        public CommandSpec CommandChestRemove = CommandSpec.builder()
                .description(Text.of("/chest remove [player]"))
                .permission("actus.chest")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("target")))))
                .executor(new CommandChestRemove())
                .build();
        
        public CommandSpec CommandChestInfo = CommandSpec.builder()
                .description(Text.of("/chest info"))
                .permission("actus.chest")
                .executor(new CommandChestInfo())
                .build();
        
        public CommandSpec CommandChest = CommandSpec.builder()
                .description(Text.of("/chest")) 
                .permission("actus.chest") 
                .child(CommandChestAdd, "add", "lock")
                .child(CommandChestRemove, "remove", "sup", "del")
                .child(CommandChestInfo, "info", "inf")
                .executor(new CommandChest())
                .build();
        
        public CommandSpec CommandNPC = CommandSpec.builder()
                .description(Text.of("/npc"))
                .permission("actus.npc")
                .executor(new CommandNPC())
                .build();
        
        public CommandSpec CommandSetSpawn = CommandSpec.builder()
                .description(Text.of("/spawn set"))
                .permission("actus.admin.setspawn")
                .executor(new CommandSetspawn())
                .build();
        
        public CommandSpec CommandSpawn = CommandSpec.builder()
                .description(Text.of("/spawn")) 
                .permission("actus.spawn") 
                .child(CommandSetSpawn, "set")
                .executor(new CommandSpawn())
                .build();
        
        public CommandSpec CommandSetspawn = CommandSpec.builder()
                .description(Text.of("/setspawn"))
                .permission("actus.admin.setspawn")
                .executor(new CommandSetspawn())
                .build();
        
        public CommandSpec CommandButcher = CommandSpec.builder()
                .description(Text.of("/butcher [worldName]"))
                .permission("actus.butcher")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("worldName")))))
                .executor(new CommandButcher())
                .build();
        
        public CommandSpec CommandTPR = CommandSpec.builder()
                .description(Text.of("/tpr")) 
                .permission("actus.tpr") 
                .executor(new CommandTPR())
                .build();
        
        public CommandSpec CommandTPThru = CommandSpec.builder()
                .description(Text.of("/tpthru")) 
                .permission("actus.tpthru") 
                .executor(new CommandTPThru())
                .build();
        
        public CommandSpec CommandDataSave = CommandSpec.builder()
                .description(Text.of("/data save")) 
                .permission("actus.admin.data") 
                .executor(new CommandDataSave()) 
                .build(); 
        
        public CommandSpec CommandDataReload = CommandSpec.builder()
                .description(Text.of("/data reload")) 
                .permission("actus.admin.data") 
                .executor(new CommandDataReload()) 
                .build(); 
        
        public CommandSpec CommandData = CommandSpec.builder()
                .description(Text.of("/data")) 
                .permission("actus.admin.data") 
                .child(CommandDataSave, "save", "sauve", "s")
                .child(CommandDataReload, "reload", "load")
                .executor(new CommandData())
                .build();
        
        public CommandSpec CommandAS = CommandSpec.builder()
                .description(Text.of("/as")) 
                .permission("actus.as") 
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("uuid")))))
                .executor(new CommandAS())
                .build();
        
        public CommandSpec CommandMailBox = CommandSpec.builder()
                .description(Text.of("/mailbox")) 
                .executor(new CommandMailBox())
                .build();
        
        public CommandSpec CommandPlotClaim = CommandSpec.builder() 
                .description(Text.of("/claim")) 
                .permission("actus.plot.create") 
                .executor(new CommandPlotClaim()) 
                .build(); 
}
