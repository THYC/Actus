package net.teraoctet.actus.commands;

import net.teraoctet.actus.commands.economy.CommandSignBank;
import net.teraoctet.actus.commands.portal.CommandPortalList;
import net.teraoctet.actus.commands.portal.CommandPortalMsg;
import net.teraoctet.actus.commands.portal.CommandPortalCreateOK;
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
import net.teraoctet.actus.commands.plot.CommandPlotCreateOK;
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
import net.teraoctet.actus.commands.economy.CommandShopPurchase;
import net.teraoctet.actus.commands.economy.CommandShopSell;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {
        
        public CommandSpec CommandActus = CommandSpec.builder()
                .description(Text.of("/actus"))
                .executor(new CommandActus())
                .build();
    
        public CommandSpec CommandSetName = CommandSpec.builder()
                .description(Text.of("/setname <name>"))
                .permission("actus.admin.setname")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
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
        
        public CommandSpec CommandPlotCreateOK = CommandSpec.builder() 
                .description(Text.of("use /plot create <name> [strict]")) 
                .permission("actus.plot.create") 
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))),
                        GenericArguments.onlyOne(GenericArguments.bool(Text.of("strict")))))
                .executor(new CommandPlotCreateOK()) 
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
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
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
                        GenericArguments.optional(GenericArguments.string(Text.of("naem"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("level")))))
                .executor(new CommandPlotLevel()) 
                .build(); 
        
        public CommandSpec CommandPlot = CommandSpec.builder()
                .description(Text.of("/plot")) 
                .permission("actus.plot") 
                .child(CommandPlotCreate, "create")
                .child(CommandPlotCreateOK, "createok")
                .child(CommandPlotExpand, "expand")
                .child(CommandPlotList, "list")
                .child(CommandPlotFlag, "flag")
                .child(CommandPlotFlaglist, "flaglist")
                .child(CommandPlotRemove, "remove")
                .child(CommandPlotSale, "sale")
                .child(CommandPlotAddplayer, "addplayer")
                .child(CommandPlotRemoveplayer, "removeplayer")
                .child(CommandPlotOwnerset, "ownerset")
                .child(CommandPlotMsg, "msg")
                .child(CommandPlotTP, "tp")
                .child(CommandPlotLevel, "level")
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
        
        public CommandSpec CommandHead = CommandSpec.builder()
                .description(Text.of("/head [head]"))
                .permission("actus.head")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("head")))))
                .executor(new CommandHead())
                .build();
        
        public CommandSpec CommandWorldCreate = CommandSpec.builder()
                .description(Text.of("/worldCreate <name> <environment> <gamemode> <difficulty>"))
                .permission("actus.admin.world.worldcreate")
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("environment"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("gamemode"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("difficulty")))))
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
        
        public CommandSpec CommandPortalCreateOK = CommandSpec.builder() 
                .description(Text.of("/portal create <name>")) 
                .permission("actus.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalCreateOK()) 
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
                .child(CommandPortalCreateOK, "createok")
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
        
        public CommandSpec CommandSignBank = CommandSpec.builder()
                .description(Text.of("/signbank <type>"))
                .permission("actus.admin.sign.bank")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))))
                .executor(new CommandSignBank())
                .build();
        
        public CommandSpec CommandShopCreate = CommandSpec.builder()
                .description(Text.of("/shopcreate <locationstring> <transacttype> <price> <qte>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("locationstring"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("transacttype"))),
                    GenericArguments.optional(GenericArguments.doubleNum(Text.of("price"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("qte"))))
                .executor(new CommandShopCreate())
                .build();
        
        public CommandSpec CommandShopPurchase = CommandSpec.builder()
                .description(Text.of("/shopcreate <locationstring> <transacttype> <price> <qte>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("locationstring"))))
                .executor(new CommandShopPurchase())
                .build();
        
        public CommandSpec CommandShopSell = CommandSpec.builder()
                .description(Text.of("/shopcreate <locationstring> <transacttype> <price> <qte>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("locationstring"))))
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
}
