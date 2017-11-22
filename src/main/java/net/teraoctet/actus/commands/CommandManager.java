package net.teraoctet.actus.commands;

import net.teraoctet.actus.commands.troc.CommandTrocSet;
import net.teraoctet.actus.commands.world.CommandWorldTP;
import net.teraoctet.actus.commands.world.CommandWorldLoad;
import net.teraoctet.actus.commands.world.CommandWorldCreate;
import net.teraoctet.actus.commands.chest.CommandChestAdd;
import net.teraoctet.actus.commands.chest.CommandChest;
import net.teraoctet.actus.commands.chest.CommandChestInfo;
import net.teraoctet.actus.commands.chest.CommandChestRemove;
import net.teraoctet.actus.commands.shop.CommandBank;
import net.teraoctet.actus.commands.shop.CommandSignBank;
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
import net.teraoctet.actus.commands.shop.CommandSetName;
import net.teraoctet.actus.commands.shop.CommandShopCreate;
import net.teraoctet.actus.commands.shop.CommandShopList;
import net.teraoctet.actus.commands.shop.CommandShopPurchase;
import net.teraoctet.actus.commands.shop.CommandShopSell;
import net.teraoctet.actus.commands.grave.CommandGrave;
import net.teraoctet.actus.commands.grave.CommandGraveInfo;
import net.teraoctet.actus.commands.grave.CommandGraveList;
import net.teraoctet.actus.commands.grave.CommandGraveTp;
import net.teraoctet.actus.commands.grave.CommandGraveyard;
import net.teraoctet.actus.commands.grave.CommandGraveyardCreate;
import net.teraoctet.actus.commands.plot.CommandPlotClaim;
import net.teraoctet.actus.commands.plot.CommandPlotListNameAllowed;
import net.teraoctet.actus.commands.plot.CommandPlotSetLevel;
import net.teraoctet.actus.commands.plot.CommandPlotTag;
import net.teraoctet.actus.commands.shop.CommandBankVerse;
import net.teraoctet.actus.commands.troc.CommandTrocAdd;
import net.teraoctet.actus.commands.world.CommandWorld;
import net.teraoctet.actus.commands.world.CommandWorldList;
import net.teraoctet.actus.troc.EnumTransactType;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.difficulty.Difficulty;
import org.spongepowered.api.world.gen.WorldGeneratorModifier;

public class CommandManager {
        
        public CommandSpec CommandActus = CommandSpec.builder()
                .description(MESSAGE("/actus"))
                .executor(new CommandActus())
                .build();
    
        public CommandSpec CommandSetName = CommandSpec.builder()
                .description(MESSAGE("/setname <name>"))
                .permission("actus.admin.setname")
                .arguments(GenericArguments.firstParsing(
                    GenericArguments.remainingJoinedStrings(MESSAGE("arguments"))))
                .executor(new CommandSetName())
                .build();
    
        public CommandSpec CommandKill = CommandSpec.builder()
                .description(MESSAGE("/kill"))
                .permission("actus.player.kill")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player")))))
                .executor(new CommandKill())
                .build();
        
        public CommandSpec CommandSun = CommandSpec.builder()
                .description(MESSAGE("/sun"))
                .permission("actus.fun.weather.sun")
                .executor(new CommandSun())
                .build();

        public CommandSpec CommandRain = CommandSpec.builder()
                .description(MESSAGE("Commande rain pluie"))
                .permission("actus.fun.weather.rain")
                .executor(new CommandRain())
                .build();
        
        public CommandSpec CommandStorm = CommandSpec.builder()
                .description(MESSAGE("Commande storm orage"))
                .permission("actus.fun.weather.storm")
                .executor(new CommandStorm())
                .build();
        
        public CommandSpec CommandDay = CommandSpec.builder()
                .description(MESSAGE("Commande day jour"))
                .permission("actus.fun.time.day")
                .executor(new CommandDay())
                .build();
        
        public CommandSpec CommandNight = CommandSpec.builder()
                .description(MESSAGE("Commande night nuit"))
                .permission("actus.fun.time.night")
                .executor(new CommandNight())
                .build();
                
        public CommandSpec CommandPlotCreate = CommandSpec.builder() 
                .description(MESSAGE("/plot create <name> [strict]")) 
                .permission("actus.player.plot.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(MESSAGE("name"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("strict")))))
                .executor(new CommandPlotCreate()) 
                .build(); 
                
        public CommandSpec CommandPlotFlag = CommandSpec.builder()
                .description(MESSAGE("/plot flag <flag> <0|1> [name]")) 
                .extendedDescription(MESSAGE("/plot flag <flag> <0|1> [name]"))
                .permission("actus.player.plot.flag") 
                .arguments(GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(MESSAGE("flag"))),
                        GenericArguments.optional(GenericArguments.integer(MESSAGE("value"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotFlag()) 
                .build(); 
        
        public CommandSpec CommandPlotFlaglist = CommandSpec.builder()
                .description(MESSAGE("/plot flaglist [name]")) 
                .permission("actus.player.plot.flag")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotFlaglist()) 
                .build(); 
        
        public CommandSpec CommandPlotRemove = CommandSpec.builder()
                .description(MESSAGE("/plot remove [name]")) 
                //.permission("actus.player.plot.remove")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotRemove()) 
                .build(); 
        
        public CommandSpec CommandPlotSale = CommandSpec.builder()
                .description(MESSAGE("/plot sale <price> [name]")) 
                //.permission("actus.player.plot.sale") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(MESSAGE("price"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotSale()) 
                .build(); 
        
        public CommandSpec CommandPlotAddplayer = CommandSpec.builder()
                .description(MESSAGE("/plot addplayer <player> [name]")) 
                //.permission("actus.player.plot.addplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(MESSAGE("player"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotAddplayer()) 
                .build();
        
        public CommandSpec CommandPlotRemoveplayer = CommandSpec.builder()
                .description(MESSAGE("/plot removeplayer <player> [name]")) 
                //.permission("actus.player.plot.removeplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotRemoveplayer()) 
                .build(); 
        
        public CommandSpec CommandPlotOwnerset = CommandSpec.builder()
                .description(MESSAGE("/plot ownerset <player> [name]")) 
                //.permission("actus.player.plot.ownerset") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotOwnerset()) 
                .build(); 
        
        public CommandSpec CommandPlotList = CommandSpec.builder()
                .description(MESSAGE("/plot list [player]")) 
                .permission("actus.player.plot.list") 
                .arguments(GenericArguments.optional(GenericArguments.string(MESSAGE("tplayer"))))
                .executor(new CommandPlotList()) 
                .build(); 
        
        public CommandSpec CommandPlotExpand = CommandSpec.builder()
                .description(MESSAGE("/plot expand <value>")) 
                .permission("actus.player.plot.expand") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(MESSAGE("value"))),
                        GenericArguments.optional(GenericArguments.integer(MESSAGE("point"))),
                        GenericArguments.optional(GenericArguments.string(MESSAGE("axe")))))
                .executor(new CommandPlotExpand()) 
                .build(); 
        
        public CommandSpec CommandPlotMsg = CommandSpec.builder()
                .description(MESSAGE("/plot msg [message]"))
                .permission("actus.player.plot.msg")
                .arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(MESSAGE("arguments"))))
                .executor(new CommandPlotMsg())
                .build();
        
        public CommandSpec CommandPlotTP = CommandSpec.builder()
                .description(MESSAGE("/plot tp <name>"))
                .permission("actus.player.plot.tp")
                .arguments(GenericArguments.optional(GenericArguments.string(MESSAGE("name"))))
                .executor(new CommandPlotTP())
                .build();
        
        public CommandSpec CommandPlotLevel = CommandSpec.builder()
                .description(MESSAGE("&9/plot level [name] &eAffiche le level de la parelle")) 
                .permission("actus.player.plot.level") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPlotLevel()) 
                .build(); 
        
        public CommandSpec CommandPlotSetLevel = CommandSpec.builder()
                .description(MESSAGE("&9/plot setlevel [level] &eChange le level de la parcelle")) 
                .permission("actus.admin.plot.setlevel") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(MESSAGE("level")))))
                .executor(new CommandPlotSetLevel()) 
                .build(); 
        
        public CommandSpec CommandPlotTag = CommandSpec.builder()
                .description(MESSAGE("&9/plot tag &ePose une banniere aux angles de ta parcelle")) 
                .permission("actus.player.plot.tag") 
                .executor(new CommandPlotTag()) 
                .build(); 
        
        public CommandSpec CommandPlotListNameAllowed = CommandSpec.builder()
                .description(MESSAGE("&9/plot allow &eAffiche les habitants de la parcelle")) 
                .permission("actus.player.plot") 
                .executor(new CommandPlotListNameAllowed()) 
                .build(); 
        
        public CommandSpec CommandPlot = CommandSpec.builder()
                .description(MESSAGE("&9/plot &eAfiche toutes les commandes plot")) 
                .permission("actus.player.plot") 
                .child(CommandPlotCreate, "create", "add", "+", "new")
                .child(CommandPlotListNameAllowed, "player", "habitant", "allow", "joueur")
                .child(CommandPlotExpand, "expand", "etend", "agrandi", "++")
                .child(CommandPlotList, "list", "l")
                .child(CommandPlotFlag, "flag", "f")
                .child(CommandPlotFlaglist, "flaglist")
                .child(CommandPlotRemove, "remove", "del", "-")
                .child(CommandPlotSale, "sale", "vend")
                .child(CommandPlotAddplayer, "addplayer", "addp", "+p")
                .child(CommandPlotRemoveplayer, "removeplayer", "remp", "-p")
                .child(CommandPlotOwnerset, "ownerset", "adm")
                .child(CommandPlotMsg, "msg")
                .child(CommandPlotTP, "tp")
                .child(CommandPlotLevel, "level")
                .child(CommandPlotSetLevel, "setlevel", "levelset", "level set")
                .child(CommandPlotTag, "tag", "borne", "balise")
                .executor(new CommandPlot())
                .build();

        public CommandSpec CommandFly = CommandSpec.builder()
                .description(MESSAGE("&9/fly &eActive desactive le vol"))
                .permission("actus.fun.fly")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player")))))
                .executor(new CommandFly())
                .build();
        
        public CommandSpec CommandSetHome = CommandSpec.builder()
                .description(MESSAGE("&9/sethome [home] &eEngistre la position comme ton Home"))
                .permission("actus.player.sethome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("home")))))
                .executor(new CommandSetHome())
                .build();
        
        public CommandSpec CommandHome = CommandSpec.builder()
                .description(MESSAGE("/home [home]"))
                .permission("actus.player.home")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("home")))))
                .executor(new CommandHome())
                .build();
        
        public CommandSpec CommandDelhome = CommandSpec.builder()
                .description(MESSAGE("/delhome [home]"))
                .permission("actus.player.delhome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("home")))))
                .executor(new CommandDelhome())
                .build();
        
        public CommandSpec CommandBack = CommandSpec.builder()
                .description(MESSAGE("/back"))
                .permission("actus.modo.back")
                .executor(new CommandBack())
                .build();
        
        public CommandSpec CommandLevel = CommandSpec.builder()
                .description(MESSAGE("/level <level> [player]"))
                .permission("actus.admin.level")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("level"))),
                    GenericArguments.optional(GenericArguments.player(MESSAGE("player")))))
                .executor(new CommandLevel())
                .build();
        
        public CommandSpec CommandSkull = CommandSpec.builder()
                .description(MESSAGE("/skull [name]"))
                .permission("actus.fun.skull")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.string(MESSAGE("head")))))
                .executor(new CommandSkull())
                .build();
                
        public CommandSpec CommandWorldCreate = CommandSpec.builder()
                .description(MESSAGE("/world create <name> <environment> <gamemode> <difficulty>"))
                .permission("actus.admin.world.worldcreate")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name"))), 
                        GenericArguments.flags()
                        .valueFlag(GenericArguments.catalogedElement(MESSAGE("dimensionType"), DimensionType.class), "d")
    			.valueFlag(GenericArguments.catalogedElement(MESSAGE("generatorType"), GeneratorType.class), "g")
    			.valueFlag(GenericArguments.catalogedElement(MESSAGE("modifier"), WorldGeneratorModifier.class), "m")
                        .valueFlag(GenericArguments.catalogedElement(MESSAGE("gamemode"), GameMode.class),"e")
                        .valueFlag(GenericArguments.catalogedElement(MESSAGE("difficulty"), Difficulty.class),"y")
                        .valueFlag(GenericArguments.string(MESSAGE("seed")),"s")
                        .buildWith(GenericArguments.none()))
                .executor(new CommandWorldCreate())
                .build();
        
        public CommandSpec CommandWorldTP = CommandSpec.builder()
                .description(MESSAGE("/world tp"))
                .permission("actus.modo.world.worldtp")
                .arguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.string(MESSAGE("worldName"))),
                    GenericArguments.optional(GenericArguments.player(MESSAGE("target")))))
                .executor(new CommandWorldTP())
                .build();
        
        public CommandSpec CommandWorldLoad = CommandSpec.builder()
                .description(MESSAGE("/world load"))
                .permission("actus.admin.world.worldload")      
                .arguments(GenericArguments.string(MESSAGE("folderName")))
                .executor(new CommandWorldLoad())
                .build();
        
        public CommandSpec CommandWorldList = CommandSpec.builder()
                .description(MESSAGE("/world list : Liste les mondes actifs"))
                .permission("actus.modo.world.worldlist")      
                //.arguments(GenericArguments.world(MESSAGE("world")), GenericArguments.optional(GenericArguments.bool(MESSAGE("value"))))
                .executor(new CommandWorldList())
                .build();
                
        public CommandSpec CommandWorld = CommandSpec.builder()
                .description(MESSAGE("/world : Gestion des mondes/World"))
                .permission("actus.admin.word")
                .child(CommandWorldCreate, "create", "+")
                .child(CommandWorldList, "list")
                .child(CommandWorldTP, "tp")
                .child(CommandWorldLoad, "load")
                .executor(new CommandWorld())
                .build();
        
        public CommandSpec CommandClearinventory = CommandSpec.builder()
                .description(MESSAGE("/clearinventory [player] : supprimme le contenu de l'inventaire"))
                .permission("actus.player.clearinventory")
                .arguments(GenericArguments.optional(GenericArguments.player(MESSAGE("player"))))
                .executor(new CommandClearinventory())
                .build();
        
        public CommandSpec CommandInvsee = CommandSpec.builder()
                .description(MESSAGE("/invsee <player>"))
                .permission("actus.modo.invsee")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("target"))))
                .executor(new CommandInvsee())
                .build();
        
        public CommandSpec CommandPlayerinfo = CommandSpec.builder()
                .description(MESSAGE("/playerinfo [player]"))
                .permission("actus.player.playerinfo")
                .arguments(GenericArguments.optional(GenericArguments.string(MESSAGE("tplayer"))))
                .executor(new CommandPlayerinfo())
                .build();
        
        public CommandSpec CommandBroadcast = CommandSpec.builder()
                .description(MESSAGE("/broadcast [hide = 0:1] <message..>"))
                .permission("actus.modo.broadcast")
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags()
                        .flag("-hide", "h")
                        .buildWith(GenericArguments.remainingJoinedStrings(MESSAGE("message")))))
                .executor(new CommandBroadcast())
                .build();
         
        public CommandSpec CommandFactionCreate = CommandSpec.builder()
                .description(MESSAGE("/guild create <name>")) 
                .permission("actus.player.guild.create") 
                .arguments(GenericArguments.remainingJoinedStrings(MESSAGE("name")))
                .executor(new CommandGuildCreate()) 
                .build();
         
        public CommandSpec CommandFactionDelete = CommandSpec.builder()
                .description(MESSAGE("/guild delete <name>")) 
                .permission("actus.player.guild.delete") 
                .arguments(GenericArguments.remainingJoinedStrings(MESSAGE("name")))
                .executor(new CommandGuildDelete()) 
                .build();
        
        public CommandSpec CommandFactionLeave = CommandSpec.builder()
                .description(MESSAGE("/guild leave")) 
                .permission("actus.player.guild.leave") 
                .executor(new CommandGuildLeave()) 
                .build();
        
        public CommandSpec CommandFactionRename = CommandSpec.builder()
                .description(MESSAGE("/guild rename <name>")) 
                .permission("actus.player.guild.rename") 
                .arguments(GenericArguments.remainingJoinedStrings(MESSAGE("name")))
                .executor(new CommandGuildRename()) 
                .build();
         
        public CommandSpec CommandFactionList = CommandSpec.builder()
                .description(MESSAGE("/guild list")) 
                .permission("actus.player.guild.list")
                .executor(new CommandGuildList()) 
                .build();
         
        public CommandSpec CommandFactionMemberslist = CommandSpec.builder()
                .description(MESSAGE("/guild memberslist")) 
                .permission("actus.player.guild.memberslist")
                .executor(new CommandGuildMemberslist()) 
                .build();
         
        public CommandSpec CommandFactionInvit = CommandSpec.builder()
                .description(MESSAGE("/guild invit <player>")) 
                .permission("actus.player.guild.invit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player"))))
                .executor(new CommandGuildInvit()) 
                .build();
         
        public CommandSpec CommandFactionRemoveplayer = CommandSpec.builder()
                .description(MESSAGE("/guild removeplayer <player>")) 
                .permission("actus.player.guild.removeplayer") 
                .arguments(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("name"))))
                .executor(new CommandGuildRemoveplayer()) 
                .build();
         
        public CommandSpec CommandFactionSetplayergrade = CommandSpec.builder()
                .description(MESSAGE("/guild setplayergrade <player> <grade>")) 
                .permission("actus.player.guild.setplayergrade") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player"))),
                        GenericArguments.onlyOne(GenericArguments.string(MESSAGE("grade")))))
                .executor(new CommandGuildRemoveplayer()) 
                .build();
        
        public CommandSpec CommandFactionSetowner = CommandSpec.builder()
                .description(MESSAGE("/guild setowner <player>")) 
                .permission("actus.player.guild.setowner") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player"))))
                .executor(new CommandGuildSetowner()) 
                .build();
        
        public CommandSpec CommandFactionDeposit = CommandSpec.builder()
                .description(MESSAGE("/guild depot <montant>")) 
                .permission("actus.player.guild.deposit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.doubleNum(MESSAGE("amount"))))
                .executor(new CommandGuildDeposit()) 
                .build();
        
        public CommandSpec CommandFactionWithdrawal = CommandSpec.builder()
                .description(MESSAGE("/guild retrait <montant>")) 
                .permission("actus.player.guild.withdrawal") 
                .arguments(
                    GenericArguments.onlyOne(GenericArguments.doubleNum(MESSAGE("amount"))))
                .executor(new CommandGuildWithdrawal()) 
                .build();
         
        public CommandSpec CommandFaction = CommandSpec.builder()
                .description(MESSAGE("Affiche des informations sur votre guild"))
                .permission("actus.player.guild")
                .child(CommandFactionCreate, "create", "new", "add", "creer", "+")
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
                .description(MESSAGE("/test [arg0] [args1]"))
                .permission("actus.admin.test")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("arg0"))),
                    GenericArguments.optional(GenericArguments.string(MESSAGE("arg1")))))
                .executor(new CommandTest())
                .build();
        
        public CommandSpec CommandRocket = CommandSpec.builder() 
                .description(MESSAGE("Rocket Command")) 
                .permission("actus.modo.rocket.use") 
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags() 
                        .flag("-hard", "h") 
                        .buildWith(GenericArguments.firstParsing(GenericArguments.optional(GenericArguments.player(MESSAGE("target"))), 
                    GenericArguments.optional(GenericArguments.string(MESSAGE("targets"))))))) 
                .executor(new CommandRocket()) 
                .build();
        
        public CommandSpec CommandPortalMsg = CommandSpec.builder()
                .description(MESSAGE("/portal msg <name> [message]"))
                .permission("actus.admin.portal")
                .arguments(GenericArguments.firstParsing(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name"))),
                    GenericArguments.remainingJoinedStrings(MESSAGE("arguments"))))
                .executor(new CommandPortalMsg())
                .build();
        
        public CommandSpec CommandPortalTPFrom = CommandSpec.builder() 
                .description(MESSAGE("/portal TPFrom <name>")) 
                .permission("actus.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPortalTPFrom()) 
                .build(); 
        
        public CommandSpec CommandPortalList = CommandSpec.builder() 
                .description(MESSAGE("/portal list")) 
                .permission("actus.admin.portal") 
                .executor(new CommandPortalList()) 
                .build(); 
        
        public CommandSpec CommandPortalRemove = CommandSpec.builder() 
                .description(MESSAGE("/portal remove <name>")) 
                .permission("actus.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPortalRemove()) 
                .build(); 
                
        public CommandSpec CommandPortalCreate = CommandSpec.builder()
                .description(MESSAGE("/portal create [name]"))
                .permission("actus.admin.portal")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name")))))
                .executor(new CommandPortalCreate())
                .build();
        
        public CommandSpec CommandPortal = CommandSpec.builder()
                .description(MESSAGE("/portal")) 
                .permission("actus.admin.portal") 
                .child(CommandPortalCreate, "create", "add")
                .child(CommandPortalRemove, "remove", "rem", "del")
                .child(CommandPortalList, "list")
                .child(CommandPortalTPFrom, "tpfrom", "tpf")
                .child(CommandPortalMsg, "msg")
                .executor(new CommandPortal())
                .build();
               
        public CommandSpec CommandMagicCompass = CommandSpec.builder()
                .description(MESSAGE("/mc <Direction> <name> <X> <Y> <Z>"))
                .permission("actus.player.magiccompass")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("direction"))),
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name"))),
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("x"))),
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("y"))),
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("z")))))
                .executor(new CommandMagicCompass())
                .build();
        
        public CommandSpec CommandSignWrite = CommandSpec.builder()
                .description(MESSAGE("/write <line> <message>"))
                .permission("actus.admin.sign.write")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("line"))), 
                    GenericArguments.optional(GenericArguments.remainingJoinedStrings(MESSAGE("message")))))
                .executor(new CommandSignWrite())
                .build();
        
        public CommandSpec CommandSignHelp = CommandSpec.builder()
                .description(MESSAGE("/signhelp <name>"))
                .permission("actus.admin.sign.help")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name"))))
                .executor(new CommandSignHelp())
                .build();
        
        public CommandSpec CommandSignPost = CommandSpec.builder()
                .description(MESSAGE("/signpost <name>"))
                .permission("actus.admin.sign.post")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("name"))))
                .executor(new CommandSignPost())
                .build();
        
        public CommandSpec CommandSignCmd = CommandSpec.builder()
                .description(MESSAGE("/signcmd <cmd>"))
                .permission("actus.admin.sign.cmd")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("cmd"))), 
                    GenericArguments.flags()
                        .valueFlag(GenericArguments.string(MESSAGE("arg1")),"a")
                        .valueFlag(GenericArguments.string(MESSAGE("arg2")),"b")
                        .buildWith(GenericArguments.none()))
                .executor(new CommandSignCmd())
                .build();
                
        public CommandSpec CommandSignBank = CommandSpec.builder()
                .description(MESSAGE("/signbank <type>"))
                .permission("actus.admin.sign.bank")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("type"))))
                .executor(new CommandSignBank())
                .build();
        
        public CommandSpec CommandShopCreate = CommandSpec.builder()
                .description(MESSAGE("/shopcreate <uuid> <transacttype> <price> <qte>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("uuid"))),
                    GenericArguments.optional(GenericArguments.string(MESSAGE("transacttype"))),
                    GenericArguments.optional(GenericArguments.doubleNum(MESSAGE("price"))),
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("qte"))))
                .executor(new CommandShopCreate())
                .build();
        
        public CommandSpec CommandShopPurchase = CommandSpec.builder()
                .description(MESSAGE("/shoppurchase <uuid>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("uuid"))))
                .executor(new CommandShopPurchase())
                .build();
        
        public CommandSpec CommandShopSell = CommandSpec.builder()
                .description(MESSAGE("/shopsell <uuid>"))
                .permission("actus.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("uuid"))))
                .executor(new CommandShopSell())
                .build();
        
        public CommandSpec CommandTPA = CommandSpec.builder()
                .description(MESSAGE("/tpa <player>"))
                .permission("actus.player.tpa")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player")))))
                .executor(new CommandTPA())
                .build();
        
        public CommandSpec CommandTPhere = CommandSpec.builder()
                .description(MESSAGE("/tphere <player>"))
                .permission("actus.player.tpa")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player")))))
                .executor(new CommandTPhere())
                .build();
        
        public CommandSpec CommandTPaccept = CommandSpec.builder()
                .description(MESSAGE("/tpaccept"))
                .permission("actus.player.tpa")
                .executor(new CommandTPaccept())
                .build();
        
        public CommandSpec CommandVanish = CommandSpec.builder()
                .description(MESSAGE("/vanish [player]"))
                .permission("actus.modo.vanish")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("target")))))
                .executor(new CommandVanish())
                .build();
        
        public CommandSpec CommandInvisible = CommandSpec.builder()
                .description(MESSAGE("/invisible [player]"))
                .permission("actus.modo.invisible")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(MESSAGE("target")))))
                .executor(new CommandInvisible())
                .build();
        
        public CommandSpec CommandEnchant = CommandSpec.builder()
                .description(MESSAGE("/enchant <enchantement> <level> [player]"))
                .permission("actus.admin.enchant")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("enchantment"))),
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("level"))),
                GenericArguments.optional(GenericArguments.player(MESSAGE("target"))))
                .executor(new CommandEnchant())
                .build();
        
        public CommandSpec CommandRule = CommandSpec.builder()
                .description(MESSAGE("/rule"))
                .permission("actus.player.rule")
                .executor(new CommandRule())
                .build();
        
        public CommandSpec CommandBankVerse = CommandSpec.builder()
                .description(MESSAGE("&eVerse une somme sur la banque du joueur"))
                .extendedDescription(MESSAGE("&9coin = &esomme a verser \n&9player = &enom du joueur"))
                .permission("actus.admin.bank.verse")
                .arguments(
                    GenericArguments.onlyOne(GenericArguments.doubleNum(MESSAGE("coin"))),
                    GenericArguments.onlyOne(GenericArguments.player(MESSAGE("player"))))
                .executor(new CommandBankVerse())
                .build();
        
        public CommandSpec CommandBank = CommandSpec.builder()
                .description(MESSAGE("&eAffiche le solde de ton compte en banque"))
                .permission("actus.player.bank")
                .executor(new CommandBank())
                .build();
        
        public CommandSpec CommandShopList = CommandSpec.builder()
                .description(MESSAGE("/shoplist"))
                .permission("actus.admin.shoplist")
                .executor(new CommandShopList())
                .build();
        
        public CommandSpec CommandChestAdd = CommandSpec.builder()
                .description(MESSAGE("&eAjoute un droit sur le coffre en visuel"))
                .permission("actus.player.chest.add")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("target")))))
                .executor(new CommandChestAdd())
                .build();
        
        public CommandSpec CommandChestRemove = CommandSpec.builder()
                .description(MESSAGE("&eRetire les droits d'un joueur sur le coffre en visuel"))
                .extendedDescription(MESSAGE("&9Si le parametre [target] est omis le coffre devient public"))
                .permission("actus.player.chest.remove")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("target")))))
                .executor(new CommandChestRemove())
                .build();
        
        public CommandSpec CommandChestInfo = CommandSpec.builder()
                .description(MESSAGE("&eAffiche les droits d'utilisations du coffre en visuel"))
                .permission("actus.player.chest.info")
                .executor(new CommandChestInfo())
                .build();
        
        public CommandSpec CommandChest = CommandSpec.builder()
                .description(MESSAGE("&9/chest &eGestion des s\351curit\351s du coffre")) 
                .permission("actus.player.chest") 
                .child(CommandChestAdd, "add", "lock")
                .child(CommandChestRemove, "remove", "sup", "del")
                .child(CommandChestInfo, "info", "inf")
                .executor(new CommandChest())
                .build();
        
        public CommandSpec CommandNPC = CommandSpec.builder()
                .description(MESSAGE("/npc [skin] [name]"))
                .permission("actus.admin.npc")
                .arguments(
                    GenericArguments.flags()
                        .valueFlag(GenericArguments.string(MESSAGE("skin")),"s")
                        .valueFlag(GenericArguments.string(MESSAGE("name")),"n")
                        .buildWith(GenericArguments.none()))
                .executor(new CommandNPC())
                .build();
        
        public CommandSpec CommandSetSpawn = CommandSpec.builder()
                .description(MESSAGE("&eDefinit le spawn sur la position du joueur"))
                .permission("actus.admin.setspawn")
                .executor(new CommandSetspawn())
                .build();
        
        public CommandSpec CommandSpawn = CommandSpec.builder()
                .description(MESSAGE("&eTeleporte sur le Spawn du monde actif")) 
                .permission("actus.player.spawn") 
                .child(CommandSetSpawn, "set")
                .executor(new CommandSpawn())
                .build();
        
        public CommandSpec CommandSetspawn = CommandSpec.builder()
                .description(MESSAGE("&eDefinit le spawn sur la position du joueur"))
                .permission("actus.admin.setspawn")
                .executor(new CommandSetspawn())
                .build();
        
        public CommandSpec CommandButcher = CommandSpec.builder()
                .description(MESSAGE("&eSupprime toutes les entités du monde nomm\351"))
                .extendedDescription(MESSAGE("&9Prend le monde actif, si le parametre [worldName] est omis"))
                .permission("actus.admin.butcher")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("worldName")))))
                .executor(new CommandButcher())
                .build();
        
        public CommandSpec CommandTPR = CommandSpec.builder()
                .description(MESSAGE("&eT\351l\351porte sur un point al\351atoire de la carte")) 
                .permission("actus.player.tpr") 
                .executor(new CommandTPR())
                .build();
        
        public CommandSpec CommandTPThru = CommandSpec.builder()
                .description(MESSAGE("&eT\351l\351porte sur le point en visuel")) 
                .permission("actus.admin.tpthru") 
                .executor(new CommandTPThru())
                .build();
        
        public CommandSpec CommandDataSave = CommandSpec.builder()
                .description(MESSAGE("/data save")) 
                .permission("actus.admin.data") 
                .executor(new CommandDataSave()) 
                .build(); 
        
        public CommandSpec CommandDataReload = CommandSpec.builder()
                .description(MESSAGE("/data reload")) 
                .permission("actus.admin.data") 
                .executor(new CommandDataReload()) 
                .build(); 
        
        public CommandSpec CommandData = CommandSpec.builder()
                .description(MESSAGE("&eGestion des donn\351e du plugin &bACTUS")) 
                .permission("actus.admin.data") 
                .child(CommandDataSave, "save", "sauve", "s")
                .child(CommandDataReload, "reload", "load")
                .executor(new CommandData())
                .build();
        
        public CommandSpec CommandAS = CommandSpec.builder()
                .description(MESSAGE("&eAffiche les propri\351t\351 de l'armorStand en visuel")) 
                .permission("actus.fun.as") 
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(MESSAGE("uuid")))))
                .executor(new CommandAS())
                .build();
        
        public CommandSpec CommandMailBox = CommandSpec.builder()
                .description(MESSAGE("&9/mailbox &econsulte la boite aux lettres")) 
                .executor(new CommandMailBox())
                .build();
        
        public CommandSpec CommandPlotClaim = CommandSpec.builder() 
                .description(MESSAGE("&eRevendique le chunk sur ta position")) 
                .executor(new CommandPlotClaim()) 
                .build(); 
        
        public CommandSpec CommandGraveList = CommandSpec.builder()
                .description(MESSAGE("/grave list")) 
                .executor(new CommandGraveList()) 
                .build();
        
        public CommandSpec CommandGraveTp = CommandSpec.builder()
                .description(MESSAGE("&eT\351l\351porte sur la tombe")) 
                .permission("actus.admin.grave")
                .executor(new CommandGraveTp()) 
                .build();
        
        public CommandSpec CommandGraveInfo = CommandSpec.builder()
                .description(MESSAGE("&eAffiche les infos de la tombe a proximit\351"))
                .permission("actus.player.grave")
                .executor(new CommandGraveInfo()) 
                .build();
        
        public CommandSpec CommandGrave = CommandSpec.builder()
                .description(MESSAGE("&9/grave &eGestion des tombes"))
                .permission("actus.admin.grave")
                .child(CommandGraveList, "list")
                .child(CommandGraveTp, "tp")
                .child(CommandGraveInfo, "info")
                .arguments(
                    GenericArguments.flags()
                        .valueFlag(GenericArguments.bool(MESSAGE("sign")),"s")
                        .valueFlag(GenericArguments.bool(MESSAGE("skull")),"k")
                        .buildWith(GenericArguments.none()))
                .executor(new CommandGrave())
                .build();
        
        public CommandSpec CommandGraveyardCreate = CommandSpec.builder()
                .description(MESSAGE("&9/graveyard create &eCr\351ation d'une tombe de crypte/cimetiere")) 
                .executor(new CommandGraveyardCreate()) 
                .build();
        
        public CommandSpec CommandGraveyard = CommandSpec.builder()
                .description(MESSAGE("&9/graveyard &eGestion des cryptes/cimetieres")) 
                .permission("actus.admin.grave") 
                //.child(CommandGraveyardList, "list")
                //.child(CommandGraveTp, "tp")
                .child(CommandGraveyardCreate, "create", "add", "ajout", "+")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(MESSAGE("arg")))))
                .executor(new CommandGraveyard())
                .build();
        
        public CommandSpec CommandTrocSet = CommandSpec.builder()
                .description(MESSAGE("&9/troc set &eD\351clare le coffre en visuel comme coffre Troc")) 
                .permission("actus.admin.troc.set")
                .arguments(
                    GenericArguments.flags()
                        .valueFlag(GenericArguments.string(MESSAGE("owner")),"o")
                        .valueFlag(GenericArguments.integer(MESSAGE("idguild")),"g")
                        .buildWith(GenericArguments.none()))
                .executor(new CommandTrocSet())
                .build();
        
        public CommandSpec CommandTrocAdd = CommandSpec.builder()
                .description(MESSAGE("&9/troc add <type> <price> [qte] &eAjoute un itemTroc dans le coffre Troc en visuel"))
                .permission("actus.player.troc.add")
                .arguments(
                    GenericArguments.onlyOne(GenericArguments.enumValue(MESSAGE("type"), EnumTransactType.class)),
                    GenericArguments.onlyOne(GenericArguments.doubleNum(MESSAGE("price"))),
                    GenericArguments.optional(GenericArguments.integer(MESSAGE("qte"))))
                .executor(new CommandTrocAdd()) 
                .build();
        
        public CommandSpec CommandTroc = CommandSpec.builder()
                .description(MESSAGE("&9/troc &eGestion des trocs"))
                //.permission("actus.admin.troc")
                //.child(CommandTrocList, "list")
                //.child(CommandTrocTp, "tp")
                //.child(CommandTrocInfo, "info")
                .child(CommandTrocSet, "set")
                .child(CommandTrocAdd, "add","+")
                //.arguments(
                   // GenericArguments.flags()
                        //.valueFlag(GenericArguments.bool(MESSAGE("sign")),"s")
                        //.valueFlag(GenericArguments.bool(MESSAGE("skull")),"k")
                        //.buildWith(GenericArguments.none()))
                //.executor(new CommandTroc())
                .build();
}
