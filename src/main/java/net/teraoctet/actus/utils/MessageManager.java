package net.teraoctet.actus.utils;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class MessageManager {
    private static Text RULES;
    private static Text NO_PERMISSIONS;
    private static Text NO_CONSOLE;
    private static Text JOIN_MESSAGE;
    private static Text EVENT_LOGIN_MESSAGE;
    private static Text FIRSTJOIN_MESSAGE;
    private static Text FIRSTJOIN_BROADCAST_MESSAGE;
    private static Text EVENT_DISCONNECT_MESSAGE;
    private static Text NAME_CHANGE;
    private static Text WRONG_NAME;
    private static Text SUN_MESSAGE;
    private static Text RAIN_MESSAGE;
    private static Text DAY_MESSAGE;
    private static Text NIGHT_MESSAGE;
    private static Text STORM_MESSAGE;
    private static Text GUIDE_GUILD;
    private static Text NO_GUILD;
    private static Text NOT_IN_SAME_GUILD;
    private static Text WRONG_RANK;
    private static Text OWNER_CANNOT_LEAVE;
    private static Text ALREADY_GUILD_MEMBER;
    private static Text GUILD_CREATED_SUCCESS;
    private static Text GUILD_RENAMED_SUCCESS;
    private static Text GUILD_DELETED_SUCCESS;
    private static Text LEAVING_GUILD_SUCCESS;
    private static Text GUILD_MEMBER_REMOVED_SUCCESS;
    private static Text GUILD_RETURNED_BY;
    private static Text GUILD_DELETED_NOTIFICATION;
    private static Text GUILD_NEW_CHEF;
    private static Text GUILD_CHEF_GRADE_GIVEN;
    private static Text GUILD_YOU_ARE_NEW_CHEF;
    private static Text BUYING_COST_PLOT;
    private static Text PROTECT_PLOT_SUCCESS;
    private static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
    private static Text PROTECT_LOADED_PLOT;
    private static Text UNDEFINED_PLOT_ANGLES;
    private static Text ALREADY_OWNED_PLOT;
    private static Text EXPAND_NOT_ALLOWED;
    private static Text NAME_ALREADY_USED;
    private static Text NO_PLOT;
    private static Text PLOT_INFO;
    private static Text TARGET_PLOT_LIST;
    private static Text PLOT_LIST;
    private static Text PLOT_PROTECTED;
    private static Text PLOT_NO_FLY;
    private static Text PLOT_NO_ENTER;
    private static Text PLOT_NO_BREAK;
    private static Text PLOT_NO_BUILD;
    private static Text PLOT_NO_FIRE;
    private static Text PLOT_NO_EXIT;
    private static Text MISSING_BALANCE;
    private static Text DEPOSIT_SUCCESS;
    private static Text HOME_ALREADY_EXIST;
    private static Text HOME_SET_SUCCESS;
    private static Text HOME_DEL_SUCCESS;
    private static Text NB_HOME;
    private static Text NB_ALLOWED_HOME;
    private static Text HOME_NOT_FOUND;
    private static Text ERROR;
    private static Text HOME_TP_SUCCESS;
    private static Text NOT_FOUND;
    private static Text NOT_CONNECTED;
    private static Text DATA_NOT_FOUND;
    private static Text CANNOT_EJECT_OWNER;
    private static Text WORLD_ALREADY_EXIST;
    private static Text WORLD_CREATED;
    private static Text WORLD_CREATION_ERROR;
    private static Text WORLD_PROPERTIES_ERROR;
    private static Text TELEPORTED_TO_WORLD;
    private static Text OTHER_TELEPORTED_TO_WORLD;
    private static Text PROTECT_PORTAL;
    private static Text TP_BACK;
    private static Text INVENTORY_CLEARED;
    private static Text CLEARINVENTORY_SUCCESS;
    private static Text FLY_ENABLED;
    private static Text FLY_DISABLED;
    private static Text FLY_GIVEN;
    private static Text FLY_RETIRED;
    private static Text TP_AT_COORDS;
    private static Text WRONG_CHARACTERS_NUMBER;
    private static Text KILLED_BY;
    private static Text SUICIDE;
    private static Text GUILD_ONHOVER_MOREACTIONS;
    private static Text GUILD_ONHOVER_SETGRADE;
    private static Text GUILD_ONHOVER_RENAME;
    private static Text GUILD_ONHOVER_INVIT;
    private static Text GUILD_ONHOVER_DELETE;
    private static Text GUILD_ONHOVER_REMOVEMEMBER;
    private static Text GUILD_ONHOVER_WITHDRAWAL;
    private static Text GUILD_ONHOVER_DEPOSIT;
    private static Text GUILD_ONHOVER_LEAVE;
    private static Text GUILD_ONHOVER_LIST_LVL10;
    private static Text PI_ONHOVER_HOME;
    private static Text PI_ONHOVER_PLOT;
    private static Text PI_ONHOVER_GUILD;
    private static Text PI_ONHOVER_NOGUILD;
    private static Text PI_ADMIN_ONLINE_ONHOVER_NAME;    
    private static Text PI_ADMIN_OFFLINE_ONHOVER_NAME;
    private static Text PI_ADMIN_ONHOVER_TP;
    private static Text SHOP_SALE;
    private static Text SHOP_BUY;
    private static Text WITHDRAW_SUCCESS;
    private static Text GUILD_MISSING_BALANCE;
    private static Text BUTCHER;
    private static Text GRAVE;
    private static Text CHEST_LOCK;
    private static Text LAST_CONNECT;
    private static Text CLICK_TO_CONFIRM;
    private static Text CHEST_ROTATION;
    private static Text HEAD_ROTATION;
    private static Text LEFT_ARM_ROTATION;
    private static Text RIGHT_ARM_ROTATION;
    private static Text LEFT_LEG_ROTATION;
    private static Text RIGHT_LEG_ROTATION;
    private static Text POSITION_X_AS;
    private static Text POSITION_Y_AS;
    private static Text POSITION_Z_AS;
    private static Text ROTATION_AS;
    private static Text AS_HAS_BASE_PLATE;
    private static Text AS_HAS_GRAVITY;
    private static Text AS_IS_SMALL;
    private static Text AS_NAME_VISIBLE;
    private static Text AS_MARKER;
    private static Text AS_HAS_ARMS;
    private static Text AS_INVISIBLE;
    private static Text GRAVE_BREAK;
    private static Text GRAVE_RIP1;
    private static Text GRAVE_RIP2;
    private static Text GRAVE_RIP3;
    private static Text GRAVE_RIP4;
    private static Text GRAVE_POSITION;
    private static Text WARP_ALREADY_EXIST;
    private static Text WARP_SET_SUCCESS;
    private static Text WARP_DEL_SUCCESS;
        
    public static final File FILE = new File("config/actus/message.conf");
    public static final ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
    public static ConfigurationNode message = MANAGER.createEmptyNode(ConfigurationOptions.defaults());
        
    public static void init() {
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();
             }    
                List<String> msg = new ArrayList<>();
                message = MANAGER.load();
           
                //-------------------------
                // Message général serveur
                //-------------------------
                
                if(message.getNode("SERVER","RULES").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7- Ce serveur est en mode survie et PVP");
                    msg.add("&7- Cel\340 ne veut pas dire que tu peux faire n'importe quoi !");
                    msg.add("&7- Tu dois \352tre courtois, aucune insulte n'est tol\351r\351e");
                    msg.add("&7- Tu ne dois pas d\351truire les constructions des autres joueurs");
                    msg.add("&7- La TNT est autoris\351e uniquement sur ta parcelle");
                    msg.add("&7- Tu ne dois pas utiliser des mods te permettant de tricher");
                    msg.add("&7- N'oublie pas que ce serveur est avant tout un regroupement");
                    msg.add("&7- de joueurs fan du jeu Minecraft dont le but est de jouer");
                    msg.add("&7- dans un esprit de convivialit\351 et d'entraide.");
                    msg.add("&7- Des commandes sont disponibles pour te prot\351ger,");
                    msg.add("&7- utilise-les ! ou ne viens pas te plandre apr\350s !.");
                    msg.add("&7- Commandes consultables ici : &bhttp://craft.teraoctet.net/actus/commandes\n");
                    msg.add("&7- En cas de conflit, c'est le Staff qui tranche.");
                    msg.add("&e- inscris-toi sur notre forum pour suivre les actus &bhttp://craft.teraoctet.net\n");
                    message.getNode("SERVER","RULES").setValue(msg);
                }
                
                if(message.getNode("SERVER","JOIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&6Bienvenue, &e%name%!");
                    msg.add("&6Tu es sur la map &e%world%!\n");
                    message.getNode("SERVER","JOIN_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","EVENT_LOGIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7%name% a rejoint le serveur");
                    message.getNode("SERVER","EVENT_LOGIN_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","FIRSTJOIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eSalut &6%name%&e, c'est visiblement la premi\350re fois que tu viens !");
                    msg.add("&7Assure-toi d'avoir bien lu le r\350glement en tapant &e/rules");
                    msg.add("&7Si tu veux participer \340 la vie du serveur ou te tenir inform\351");
                    msg.add("&7inscris-toi sur notre forum &bhttp://craft.teraoctet.net\n");
                    message.getNode("SERVER","FIRSTJOIN_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","EVENT_DISCONNECT_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7%name% s'est d\351connect\351");
                    message.getNode("SERVER","EVENT_DISCONNECT_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","FIRSTJOIN_BROADCAST_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&9%name% est nouveau sur le serveur !");
                    message.getNode("SERVER","FIRSTJOIN_BROADCAST_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","NAME_CHANGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&8%var1% &7a chang\351 son nom en &8%var2%");
                    message.getNode("SERVER","NAME_CHANGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","INVENTORY_CLEARED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVotre inventaire a \351t\351 \351ffac\351");
                    message.getNode("SERVER","INVENTORY_CLEARED").setValue(msg);
                }
                
                if(message.getNode("SERVER","CLEARINVENTORY_SUCCESS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eL'inventaire de &6%var1% a \351t\351 supprim\351");
                    message.getNode("SERVER","CLEARINVENTORY_SUCCESS").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_ENABLED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eFly activ\351");
                    message.getNode("SERVER","FLY_ENABLED").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_DISABLED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eFly d\351sactiv\351");
                    message.getNode("SERVER","FLY_DISABLED").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_GIVEN").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eVous avez activ\351 le fly de &6%var1%");
                    message.getNode("SERVER","FLY_GIVEN").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_RETIRED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eVous avez d\351sactiv\351 le fly de &6%var1%");
                    message.getNode("SERVER","FLY_RETIRED").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("SERVER","BUTCHER").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&b%var1% &7a Entit\351es ont \351t\351 supprim\351es");
                    message.getNode("SERVER","BUTCHER").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("SERVER","GRAVE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eTombe de %name%");
                    message.getNode("SERVER","GRAVE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("SERVER","LAST_CONNECT").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7Derni\350re connexion : %var1%");
                    message.getNode("SERVER","LAST_CONNECT").setValue(msg);
                    MANAGER.save(message);
                }
                                
                //-------------------------
                // Message Exception/Error
                //-------------------------
                
                if(message.getNode("EXCEPTION","ERROR").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cUne erreur est survenue !");
                    message.getNode("EXCEPTION","ERROR").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","WRONG_NAME").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cCe nom est incorrect !");
                    message.getNode("EXCEPTION","WRONG_NAME").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","NAME_ALREADY_USED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cCe nom est d\351j\340 utilis\351 !");       
                    message.getNode("EXCEPTION","NAME_ALREADY_USED").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","CANNOT_EJECT_OWNER").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVous ne pouvez pas renvoyer le propri\351taire !");
                    message.getNode("EXCEPTION","CANNOT_EJECT_OWNER").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","NOT_FOUND").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&4%var1% &cest introuvable");
                    message.getNode("EXCEPTION","NOT_FOUND").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","NOT_CONNECTED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&4%var1% &cn'est pas connect\351 !");
                    message.getNode("EXCEPTION","NOT_CONNECTED").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","NO_PERMISSIONS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVous n'avez pas la permission pour utiliser cette commande !");
                    message.getNode("EXCEPTION","NO_PERMISSIONS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","NO_CONSOLE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cCette commande ne peut pas s'ex\351cuter sur la console");
                    message.getNode("EXCEPTION","NO_CONSOLE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","DATA_NOT_FOUND").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&4%var1% &cn'est pas enregistr\351 dans la base de donn\351e");
                    message.getNode("EXCEPTION","DATA_NOT_FOUND").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("EXCEPTION","WRONG_CHARACTERS_NUMBER").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cLe nombre de caract\350res doit \352tre entre %var1% et %var2%");
                    message.getNode("EXCEPTION","WRONG_CHARACTERS_NUMBER").setValue(msg);
                    MANAGER.save(message);  
                }
                
                //-------------------------
                // Message DeadMsg
                //-------------------------
               
                if(message.getNode("DEAD_MSG","KILLED_BY").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7%var1% a \351t\351 tu\351 par %var2%");
                    message.getNode("DEAD_MSG","KILLED_BY").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("DEAD_MSG","SUICIDE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7%var1% s'est suicid\351");
                    message.getNode("DEAD_MSG","SUICIDE").setValue(msg);
                    MANAGER.save(message);
                }
                
                //-------------------------
                // Message weather / time
                //-------------------------
                
                if(message.getNode("WEATHER-TIME","SUN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&e%name% &6a programm\351 le beau temps sur &e%world%");
                    message.getNode("WEATHER-TIME","SUN_MESSAGE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("WEATHER-TIME","RAIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&e%name% &6a programm\351 la pluie sur &e%world%");
                    message.getNode("WEATHER-TIME","RAIN_MESSAGE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("WEATHER-TIME","STORM_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&e%name% &6a programm\351 l'orage sur &e%world%");
                    message.getNode("WEATHER-TIME","STORM_MESSAGE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("WEATHER-TIME","DAY_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&e%name% &6a mis le jour sur &e%world%");
                    message.getNode("WEATHER-TIME","DAY_MESSAGE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("WEATHER-TIME","NIGHT_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&e%name% &6a mis la nuit sur &e%world%");
                    message.getNode("WEATHER-TIME","NIGHT_MESSAGE").setValue(msg);
                    MANAGER.save(message);
                }
                
                //-------------------------
                // Message Guild
                //-------------------------
                
                if(message.getNode("GUILD","NO_GUILD").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVous n'\352tes dans aucune guilde !");
                    message.getNode("GUILD","NO_GUILD").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","ALREADY_GUILD_MEMBER").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVous \352tes d\351j\340 dans une guilde !");
                    message.getNode("GUILD","ALREADY_GUILD_MEMBER").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","WRONG_RANK").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVotre rang dans la guilde ne vous permet pas d'utiliser \347a !");
                    message.getNode("GUILD","WRONG_RANK").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","NOT_IN_SAME_GUILD").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&c%var1% ne fait pas partie de votre guilde !");
                    message.getNode("GUILD","NOT_IN_SAME_GUILD").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","OWNER_CANNOT_LEAVE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVous \352tes chef de votre guilde, vous ne pouvez pas la quitter !");
                    msg.add("&cVeuillez c\351der le grade à un autre membre avec : /guild setgrade 1 <player>");
                    message.getNode("GUILD","OWNER_CANNOT_LEAVE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","GUILD_NEW_CHEF").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&9%var1% est le nouveau leader de la guilde \"%var2%&9\" !");
                    message.getNode("GUILD","GUILD_NEW_CHEF").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","GUILD_CHEF_GRADE_GIVEN").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&2Vous avez c\351d\351 votre grade de chef \340 %var1% !");
                    message.getNode("GUILD","GUILD_CHEF_GRADE_GIVEN").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","GUILD_YOU_ARE_NEW_CHEF").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&2Vous \352tes le nouveau leader de votre guilde !");
                    message.getNode("GUILD","GUILD_YOU_ARE_NEW_CHEF").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GUILD","GUIDE_GUILD").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&n&eQu'est-ce que \347a apporte d'\350tre dans une guilde ?");
                    msg.add("&eComing soon ..!");
                    msg.add("&ePlus d'infos sur &bhttp://craft.teraoctet.net\n");
                    message.getNode("GUILD","GUIDE_GUILD").setValue(msg);
                }
                
                msg = new ArrayList<>();
                msg.add("&eVous venez de cr\351er la guilde \"&r%var1%&e\"");
                message.getNode("GUILD","GUILD_CREATED_SUCCESS").setValue(msg);
                MANAGER.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&eVous venez de supprimer la guilde \"&r%var1%&e\"");
                message.getNode("GUILD","GUILD_DELETED_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLa guilde \"&6%var1%&e\" a \351t\351 renomm\351e en \"&6%var2%&e\"");
                message.getNode("GUILD","GUILD_RENAMED_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous venez de quitter la guilde \"&r%var1%&e\"");
                message.getNode("GUILD","LEAVING_GUILD_SUCCESS").setValue(msg);
                MANAGER.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&eVous avez renvoy\351 &6%var1% &ede votre guilde");
                message.getNode("GUILD","GUILD_MEMBER_REMOVED_SUCCESS").setValue(msg);
                MANAGER.save(message);
                     
                msg = new ArrayList<>();
                msg.add("&eVous avez \351t\351 renvoy\351 de votre guilde par &6%var1%");
                message.getNode("GUILD","GUILD_RETURNED_BY").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&1La guild \"&r%var1%&1\" a \351t\351 dissoute !");
                message.getNode("GUILD","GUILD_DELETED_NOTIFICATION").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Affiche un menu pour g\351rer la guilde");
                msg.add("&n&eAccessible par :&r Chef, Sous-chef, Officier");
                message.getNode("GUILD","GUILD_ONHOVER_MOREACTIONS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Shift+Click pour quitter la guilde");
                msg.add("\n&7/guild leave");
                message.getNode("GUILD","GUILD_ONHOVER_LEAVE").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Inviter un joueur \340 rejoindre la guilde");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef, Officier");
                msg.add("\n&7/guild addplayer <player>");
                message.getNode("GUILD","GUILD_ONHOVER_INVIT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Changer le grade d'un membre");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef, Officier");
                msg.add("\n&7/guild setplayergrade <player> <grade>");
                msg.add("&o&n&7Grade :&r&o&7 2 -> Sous-chef | 3 -> Officer | 4 -> Membre | 5 -> Recrue");
                message.getNode("GUILD","GUILD_ONHOVER_SETGRADE").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Renvoyer un joueur de la guilde");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef");
                msg.add("\n&7/guild removeplayer <player>");
                message.getNode("GUILD","GUILD_ONHOVER_REMOVEMEMBER").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Retirer des \351meraudes de la banque de guilde");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef");
                msg.add("\n&7/guild withdraw <montant>");
                message.getNode("GUILD","GUILD_ONHOVER_WITHDRAWAL").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Renommer la guilde");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef");
                msg.add("\n&7/guild rename <nom>");
                message.getNode("GUILD","GUILD_ONHOVER_RENAME").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Supprimer la guilde");
                msg.add("&e&nUtilisable par :&r Chef");
                msg.add("\n&7/guild delete <nom>");
                message.getNode("GUILD","GUILD_ONHOVER_DELETE").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6D\351poser des \351meraudes dans la banque de la guilde");
                msg.add("\n&7/guild depot <montant>");
                message.getNode("GUILD","GUILD_ONHOVER_DEPOSIT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Guilde : &r%var1%");
                msg.add("&e&nChef : &r%var2%");
                msg.add("\n&7&n&oShift+Click :&r &8&o/guild delete <name>");
                message.getNode("GUILD","GUILD_ONHOVER_LIST_LVL10").setValue(msg);
                MANAGER.save(message);
                
                //-------------------------
                // Message Plot / parcelle
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("\n&6Le co\373t pour prot\351ger cette parcelle est de &e%var1% \351meraudes");
                msg.add("&6Vous poss\351dez actuellement &e%var2% \351meraude(s) en banque\n");
                message.getNode("PLOT","BUYING_COST_PLOT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e : ");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/plot flag\n");
                message.getNode("PLOT","PROTECT_PLOT_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e de la bedrock jusqu'au ciel");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/plot flag\n");
                message.getNode("PLOT","BEDROCK2SKY_PROTECT_PLOT_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Parcelle &e%var1% &6: protection activ\351e");
                message.getNode("PLOT","PROTECT_LOADED_PLOT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&cVous n'avez pas d\351fini les angles de votre parcelle");
                msg.add("&cLes angles se d\351finissent en utilisant une pelle en bois :");
                msg.add("&cAngle1 = clic gauche / Angle2 = clic droit\n");
                message.getNode("PLOT","UNDEFINED_PLOT_ANGLES").setValue(msg);
                MANAGER.save(message);
                                
                msg = new ArrayList<>();
                msg.add("&cVous ne pouvez pas cr\351er cette parcelle, il y a d\351j\340 une parcelle prot\351g\351e dans cette s\351lection !");         
                message.getNode("PLOT","ALREADY_OWNED_PLOT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous ne pouvez pas \351tendre cette parcelle dans cette direction, une parcelle existe d\351ja  !");         
                message.getNode("PLOT","EXPAND_NOT_ALLOWED").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cAucune parcelle \340 cette position !");
                message.getNode("PLOT","NO_PLOT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cLe fly est interdit sur cette parcelle");
                message.getNode("PLOT","PLOT_NO_FLY").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&5L'acc\350s \340 cette parcelle est interdit");
                message.getNode("PLOT","PLOT_NO_ENTER").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6Vous \352tes sur une parcelle proteg\351e : &e%plot%");
                msg.add("&6Propri\351taire : &e%owner%");
                msg.add("&6Habitant(s) : &e%allow%");
                message.getNode("PLOT","PLOT_INFO").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&bListe des parcelles vous appartenant :");
                message.getNode("PLOT","PLOT_LIST").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&bListe des parcelles appartenant \340 &3%var1% :");
                message.getNode("PLOT","TARGET_PLOT_LIST").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&5Cette parcelle est prot\351g\351e par un sort magique !");
                message.getNode("PLOT","PLOT_PROTECTED").setValue(msg);
                MANAGER.save(message);
                
                //-------------------------
                // Message ECONOMY
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&eVirement de &6%var1% \351meraudes &eeffectu\351 avec succ\350s !");
                message.getNode("ECONOMY", "DEPOSIT_SUCCESS").setValue(msg);
                
                msg = new ArrayList<>();
                msg.add("&eRerait de &6%var1% \351meraudes &eeffectu\351 avec succ\350s !");
                message.getNode("ECONOMY", "WITHDRAW_SUCCESS").setValue(msg);
                
                msg = new ArrayList<>();
                msg.add("&cVous ne poss\351dez pas assez d'assez d'\351meraudes sur votre compte, tapez /bank pour voir votre solde");
                message.getNode("ECONOMY","MISSING_BALANCE").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVotre guilde ne poss\351de pas assez d'\351meraudes dans ses coffres !");
                message.getNode("ECONOMY","GUILD_MISSING_BALANCE").setValue(msg);
                MANAGER.save(message);
                
                if(message.getNode("ECONOMY","CLICK_TO_CONFIRM").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eMaintenant clique une nouvelle fois sur le panneau pour confirmer/n");
                    message.getNode("ECONOMY","CLICK_TO_CONFIRM").setValue(msg);
                    MANAGER.save(message);
                }
                
                //-------------------------
                // Message TELEPORTATION
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("T\351l\351portation aux coordonn\351es");
                message.getNode("TELEPORTATION","TP_AT_COORDS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Woshhhh ..!");
                message.getNode("TELEPORTATION","TP_BACK").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cHome d\351j\340 d\351fini, veuillez le supprimer avant de pouvoir le red\351finir");
                message.getNode("TELEPORTATION","HOME_ALREADY_EXIST").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLe home &6%var1% &ea \351t\351 cr\351\351 avec succ\350s");
                message.getNode("TELEPORTATION","HOME_SET_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLe home &6%var1% &ea \351t\351 supprim\351 avec succ\350s");
                message.getNode("TELEPORTATION","HOME_DEL_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous poss\351dez actuellement &6%var1% &esur &6%var2% &ehome possible");
                message.getNode("TELEPORTATION","NB_HOME").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous \352tes seulement autoris\351 \340 poss\351der %var1% home");
                message.getNode("TELEPORTATION","NB_ALLOWED_HOME").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cHome introuvable !");
                msg.add("&cVeuillez utiliser la commande /sethome pour le d\351finir");
                message.getNode("TELEPORTATION","HOME_NOT_FOUND").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eT\351l\351portation sur votre home : &6%var1%");
                message.getNode("TELEPORTATION","HOME_TP_SUCCESS").setValue(msg);
                MANAGER.save(message);
                
                if(message.getNode("TELEPORTATION","WARP_ALREADY_EXIST").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cWarp d\351j\340 d\351fini, veuillez le supprimer avant de pouvoir le red\351finir");
                    message.getNode("TELEPORTATION","WARP_ALREADY_EXIST").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("TELEPORTATION","WARP_SET_SUCCESS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eLe warp &6%var1% &ea \351t\351 cr\351\351 avec succ\350s");
                    message.getNode("TELEPORTATION","WARP_SET_SUCCESS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("TELEPORTATION","WARP_SET_SUCCESS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eLe warp &6%var1% &ea \351t\351 supprim\351 avec succ\350s");
                    message.getNode("TELEPORTATION","WARP_DEL_SUCCESS").setValue(msg);
                    MANAGER.save(message);
                }
                
                //-------------------------
                // Message Commande PlayerInfo
                //-------------------------
                msg.add("&l&6Supprimer la guilde");
                msg.add("&e&nUtilisable par :&r Chef");
                
                msg = new ArrayList<>();
                msg.add("&l&6Afficher la liste des home(s)");
                msg.add("&e(WORK IN PROGRESS)\n");
                msg.add("&7&n&oClick :&r &8&o/homelist");
                message.getNode("CMD_PLAYERINFO","PI_ONHOVER_HOME").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Afficher la liste des parcelle(s)\n");
                msg.add("&7&n&oClick :&r &8&o/plot list");
                message.getNode("CMD_PLAYERINFO","PI_ONHOVER_PLOT").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Afficher menu de la Guilde");
                msg.add("&e&nGrade:&r &7%var1%\n");
                msg.add("&7&n&oClick :&r &8&o/guild");
                message.getNode("CMD_PLAYERINFO","PI_ONHOVER_GUILD").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Aucune Guilde");
                msg.add("&eVous êtes membre d'aucune guilde,");
                msg.add("&een cliquant ici vous pouvez en créer une nouvelle\n");
                msg.add("&7&n&oClick :&r &8&o/guild create <nom>");
                message.getNode("CMD_PLAYERINFO","PI_ONHOVER_NOGUILD").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&n&6UUID:&r &l&6%var1%\n");
                msg.add("&7&n&oClick :&r &8&o/kick <player> <raison>");
                msg.add("&7&n&oShift+Click :&r &8&o/ban <player> <raison>");
                message.getNode("CMD_PLAYERINFO","PI_ADMIN_ONLINE_ONHOVER_NAME").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&n&6UUID:&r &l&6%var1%\n");
                msg.add("&7&n&oShift+Click :&r &8&o/ban <player> <raison>");
                message.getNode("CMD_PLAYERINFO","PI_ADMIN_OFFLINE_ONHOVER_NAME").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6T\351l\351portation aux coordonn\351es");
                msg.add("&e(WORK IN PROGRESS)\n");
                msg.add("&7&n&oClick :&r &8&o/tp <coords>");
                message.getNode("CMD_PLAYERINFO","PI_ADMIN_ONHOVER_TP").setValue(msg);
                MANAGER.save(message);
                
                //-------------------------
                // Message WORLD
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&cCe monde existe d\351j\340");
                message.getNode("WORLD","WORLD_ALREADY_EXIST").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&c%var1% a \351t\351 cr\351\351 avec succ\350s");
                message.getNode("WORLD","WORLD_CREATED").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%ERREUR : &cLe monde n'a pas pu \352tre cr\351\351");
                message.getNode("WORLD","WORLD_CREATION_ERROR").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%ERREUR : &cLes propri\351t\351s du monde ne peuvent pas \352tre cr\351\351s");
                message.getNode("WORLD","WORLD_PROPERTIES_ERROR").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eT\351l\351portation sur : &6%world%");
                message.getNode("WORLD","TELEPORTED_TO_WORLD").setValue(msg);
                MANAGER.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6%name% &ea \351t\351 t\351l\351port\351 sur : &6%world%");
                message.getNode("WORLD","OTHER_TELEPORTED_TO_WORLD").setValue(msg);
                MANAGER.save(message);
                
                //-------------------------
                // Message PORTAL
                //-------------------------
                
                if(message.getNode("PORTAL","PROTECT_PORTAL").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cPortail prot\351g\351");
                    message.getNode("PORTAL","PROTECT_PORTAL").setValue(msg);
                    MANAGER.save(message); 
                }
                
                //-------------------------
                // Message SHOP
                //-------------------------
                
                if(message.getNode("SHOP","SHOP_SALE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&bVEND:");
                    message.getNode("SHOP","SHOP_SALE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("SHOP","SHOP_BUY").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&bACHAT:");
                    message.getNode("SHOP","SHOP_BUY").setValue(msg);
                    MANAGER.save(message); 
                }
                
                //-------------------------
                // Message CHEST
                //-------------------------
                
                if(message.getNode("CHEST","CHEST_LOCK").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eCe coffre est verouill\351 !");
                    message.getNode("CHEST","CHEST_LOCK").setValue(msg);
                    MANAGER.save(message);
                }
                
                //-------------------------
                // Message ARMOR STAND
                //-------------------------
                
                if(message.getNode("ARMORSTAND","CHEST_ROTATION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation du corps : ");
                    message.getNode("ARMORSTAND","CHEST_ROTATION").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","HEAD_ROTATION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation de la t\352te : ");
                    message.getNode("ARMORSTAND","HEAD_ROTATION").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","LEFT_ARM_ROTATION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation du bras gauche : ");
                    message.getNode("ARMORSTAND","LEFT_ARM_ROTATION").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","RIGHT_ARM_ROTATION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation du bras droit : ");
                    message.getNode("ARMORSTAND","RIGHT_ARM_ROTATION").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","LEFT_LEG_ROTATION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation de la jambe gauche : ");
                    message.getNode("ARMORSTAND","LEFT_LEG_ROTATION").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","RIGHT_LEG_ROTATION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation de la jambe droite : ");
                    message.getNode("ARMORSTAND","RIGHT_LEG_ROTATION").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","POSITION_X_AS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Position sur l'axe X : ");
                    message.getNode("ARMORSTAND","POSITION_X_AS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","POSITION_Y_AS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Position sur l'axe Y : ");
                    message.getNode("ARMORSTAND","POSITION_Y_AS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","POSITION_Z_AS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Position sur l'axe Z : ");
                    message.getNode("ARMORSTAND","POSITION_Z_AS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","ROTATION_AS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 - Rotation : ");
                    message.getNode("ARMORSTAND","ROTATION_AS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","AS_HAS_BASE_PLATE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + (Des)Activation de la plaque de base ");
                    message.getNode("ARMORSTAND","AS_HAS_BASE_PLATE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","AS_HAS_GRAVITY").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + (Des)Activation de la gravit\351 ");
                    message.getNode("ARMORSTAND","AS_HAS_GRAVITY").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","AS_HAS_ARMS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + (Des)Activation des bras ");
                    message.getNode("ARMORSTAND","AS_HAS_ARMS").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","AS_IS_SMALL").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + Taille petit ou grand ");
                    message.getNode("ARMORSTAND","AS_IS_SMALL").setValue(msg);
                    MANAGER.save(message);
                }
                                
                if(message.getNode("ARMORSTAND","AS_NAME_VISIBLE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + (Des)Affiche le nom ");
                    message.getNode("ARMORSTAND","AS_NAME_VISIBLE").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","AS_MARKER").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + (Des)Affiche le marker ");
                    message.getNode("ARMORSTAND","AS_MARKER").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("ARMORSTAND","AS_INVISIBLE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7 + (In)Visible ");
                    message.getNode("ARMORSTAND","AS_INVISIBLE").setValue(msg);
                    MANAGER.save(message);
                }
                
                //-------------------------
                // Message GRAVE
                //-------------------------
                
                if(message.getNode("GRAVE","GRAVE_BREAK").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&bUne pauvre \342me repose dans ce cercueil, tu ne peux le d\351truire..");
                    message.getNode("GRAVE","GRAVE_BREAK").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GRAVE","GRAVE_RIP1").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&5+++++++++++++");
                    message.getNode("GRAVE","GRAVE_RIP1").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GRAVE","GRAVE_RIP2").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&o&5 Repose en Paix");
                    message.getNode("GRAVE","GRAVE_RIP2").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GRAVE","GRAVE_RIP3").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&5&l%name%");
                    message.getNode("GRAVE","GRAVE_RIP3").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GRAVE","GRAVE_RIP4").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&5+++++++++++++");
                    message.getNode("GRAVE","GRAVE_RIP4").setValue(msg);
                    MANAGER.save(message);
                }
                
                if(message.getNode("GRAVE","GRAVE_POSITION").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eTon \351quipement est situ\351 dans une tombe \340 la position : ");
                    message.getNode("GRAVE","GRAVE_POSITION").setValue(msg);
                    MANAGER.save(message);
                }
                
            message = MANAGER.load();

        } catch (IOException e) {}
		
    }
    
    public static Text formatText(String string){
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(string)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP,nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, Player player){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, Player player, String var1, String var2){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, String var1, String var2){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, Player player, String var1, String var2, String owner, String allow, String plot){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%allow%", allow);
        msg = msg.replaceAll("%owner%", owner);
        msg = msg.replaceAll("%plot%", plot);
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    //-------------------------
    // Message général serveur
    //-------------------------
    
    public static Text RULES(){return format(RULES, "SERVER", "RULES");}
    
    public static Text JOIN_MESSAGE(Player player){return format(JOIN_MESSAGE, "SERVER", "JOIN_MESSAGE", player);}
    
    public static Text EVENT_LOGIN_MESSAGE(Player player){return format(EVENT_LOGIN_MESSAGE, "SERVER", "EVENT_LOGIN_MESSAGE", player);}
        
    public static Text FIRSTJOIN_MESSAGE(Player player){return format(FIRSTJOIN_MESSAGE, "SERVER", "FIRSTJOIN_MESSAGE", player);}
    
    public static Text FIRSTJOIN_BROADCAST_MESSAGE(Player player){return format(FIRSTJOIN_BROADCAST_MESSAGE, "SERVER", "FIRSTJOIN_BROADCAST_MESSAGE", player);}
    
    public static Text EVENT_DISCONNECT_MESSAGE(Player player){return format(EVENT_DISCONNECT_MESSAGE, "SERVER", "EVENT_DISCONNECT_MESSAGE", player);}
    
    public static Text NAME_CHANGE(String oldName, String newName){return format(NAME_CHANGE, "SERVER", "NAME_CHANGE", oldName, newName);}
    
    public static Text INVENTORY_CLEARED(){return format(INVENTORY_CLEARED, "SERVER", "INVENTORY_CLEARED");}
    
    public static Text CLEARINVENTORY_SUCCESS(String target){return format(CLEARINVENTORY_SUCCESS, "SERVER", "CLEARINVENTORY_SUCCESS",target, "");}
    
    public static Text FLY_ENABLED(){return format(FLY_ENABLED, "SERVER", "FLY_ENABLED");}
    
    public static Text FLY_DISABLED(){return format(FLY_DISABLED, "SERVER", "FLY_DISABLED");}
    
    public static Text FLY_GIVEN(String player){return format(FLY_GIVEN, "SERVER", "FLY_GIVEN",player, "");}
    
    public static Text FLY_RETIRED(String player){return format(FLY_RETIRED, "SERVER", "FLY_RETIRED",player, "");}
    
    public static Text BUTCHER(String worldName){return format(BUTCHER, "SERVER", "BUTCHER", worldName, "");}
    
    public static Text GRAVE(Player player){return format(GRAVE, "SERVER", "GRAVE", player);}
    
    public static Text LAST_CONNECT(String lastConnect){return format(LAST_CONNECT, "SERVER", "LAST_CONNECT", lastConnect, "");}
    
    //-------------------------
    // Message EXCEPTION / ERROR
    //-------------------------
    
    public static Text ERROR(){return format(ERROR, "EXCEPTION","ERROR");}
    
    public static Text NO_PERMISSIONS(){return format(NO_PERMISSIONS, "EXCEPTION","NO_PERMISSIONS");}
    
    public static Text NO_CONSOLE(){return format(NO_CONSOLE, "EXCEPTION", "NO_CONSOLE");}
    
    public static Text WRONG_CHARACTERS_NUMBER(String minLength, String maxLength){return format(WRONG_CHARACTERS_NUMBER, "EXCEPTION", "WRONG_CHARACTERS_NUMBER", minLength, maxLength);}
    
    public static Text WRONG_NAME(){return format(WRONG_NAME, "EXCEPTION", "WRONG_NAME");}
    
    public static Text NAME_ALREADY_USED(){return format(NAME_ALREADY_USED, "EXCEPTION", "NAME_ALREADY_USED");}
    
    public static Text NOT_FOUND(String name){return format(NOT_FOUND, "EXCEPTION", "NOT_FOUND",name, "");}
    
    public static Text NOT_CONNECTED(String name){return format(NOT_CONNECTED, "EXCEPTION", "NOT_CONNECTED",name, "");}
    
    public static Text DATA_NOT_FOUND(String player){return format(DATA_NOT_FOUND, "EXCEPTION", "DATA_NOT_FOUND",player, "");}
    
    public static Text CANNOT_EJECT_OWNER(){return format(CANNOT_EJECT_OWNER, "EXCEPTION", "CANNOT_EJECT_OWNER");}
    
    //-------------------------
    // Message DEAD_MSG
    //-------------------------
    
    public static Text KILLED_BY(String player, String killer){return format(KILLED_BY, "DEAD_MSG", "KILLED_BY",player,killer);}
    
    public static Text SUICIDE(String player){return format(SUICIDE, "DEAD_MSG", "SUICIDE",player, "");}
    
    //-------------------------
    // Message weather / time
    //-------------------------
    
    public static Text SUN_MESSAGE(Player player){return format(SUN_MESSAGE, "WEATHER-TIME","SUN_MESSAGE", player);}
    
    public static Text RAIN_MESSAGE(Player player){return format(RAIN_MESSAGE, "WEATHER-TIME","RAIN_MESSAGE", player);}
    
    public static Text DAY_MESSAGE(Player player){return format(DAY_MESSAGE, "WEATHER-TIME","DAY_MESSAGE", player);}
    
    public static Text NIGHT_MESSAGE(Player player){return format(NIGHT_MESSAGE, "WEATHER-TIME","NIGHT_MESSAGE", player);}
    
    public static Text STORM_MESSAGE(Player player){return format(STORM_MESSAGE, "WEATHER-TIME","STORM_MESSAGE", player);}
    
    //-------------------------
    // Message GUILD
    //-------------------------
    
    public static Text NO_GUILD(){return format(NO_GUILD, "GUILD", "NO_GUILD");}
    
    public static Text WRONG_RANK(){return format(WRONG_RANK, "GUILD", "WRONG_RANK");}
    
    public static Text NOT_IN_SAME_GUILD(String targetName){return format(NOT_IN_SAME_GUILD, "GUILD", "NOT_IN_SAME_GUILD", targetName, "");}
    
    public static Text ALREADY_GUILD_MEMBER(){return format(ALREADY_GUILD_MEMBER, "GUILD", "ALREADY_GUILD_MEMBER");}
    
    public static Text OWNER_CANNOT_LEAVE(){return format(OWNER_CANNOT_LEAVE, "GUILD", "OWNER_CANNOT_LEAVE");}
    
    public static Text GUILD_NEW_CHEF(String targetName, String guildName){return format(GUILD_NEW_CHEF, "GUILD", "GUILD_NEW_CHEF", targetName, guildName);}
    
    public static Text GUILD_CHEF_GRADE_GIVEN(String targetName){return format(GUILD_CHEF_GRADE_GIVEN, "GUILD", "GUILD_CHEF_GRADE_GIVEN", targetName, "");}
    
    public static Text GUILD_YOU_ARE_NEW_CHEF(){return format(GUILD_YOU_ARE_NEW_CHEF, "GUILD", "GUILD_YOU_ARE_NEW_CHEF");}
    
    public static Text GUIDE_GUILD(){return format(GUIDE_GUILD, "GUILD", "GUIDE_GUILD");}
    
    public static Text GUILD_CREATED_SUCCESS(String guildName){return format(GUILD_CREATED_SUCCESS, "GUILD", "GUILD_CREATED_SUCCESS", guildName, "");}
    
    public static Text GUILD_RENAMED_SUCCESS(String oldName, String newName){return format(GUILD_RENAMED_SUCCESS, "GUILD", "GUILD_RENAMED_SUCCESS", oldName, newName);}
    
    public static Text GUILD_DELETED_SUCCESS(String guildName){return format(GUILD_DELETED_SUCCESS, "GUILD", "GUILD_DELETED_SUCCESS", guildName, "");}
    
    public static Text LEAVING_GUILD_SUCCESS(String guildName){return format(LEAVING_GUILD_SUCCESS, "GUILD", "LEAVING_GUILD_SUCCESS", guildName, "");}
    
    public static Text GUILD_MEMBER_REMOVED_SUCCESS(String targetName){return format(GUILD_MEMBER_REMOVED_SUCCESS, "GUILD", "GUILD_MEMBER_REMOVED_SUCCESS", targetName, "");}
            
    public static Text GUILD_RETURNED_BY(String src){return format(GUILD_RETURNED_BY, "GUILD", "GUILD_RETURNED_BY", src, "");}
    
    public static Text GUILD_DELETED_NOTIFICATION(String guildName){return format(GUILD_DELETED_NOTIFICATION, "GUILD", "GUILD_DELETED_NOTIFICATION", guildName, "");}
    
    public static Text GUILD_ONHOVER_MOREACTIONS(){return format(GUILD_ONHOVER_MOREACTIONS, "GUILD", "GUILD_ONHOVER_MOREACTIONS");}
    
    public static Text GUILD_ONHOVER_LEAVE(){return format(GUILD_ONHOVER_LEAVE, "GUILD", "GUILD_ONHOVER_LEAVE");}
    
    public static Text GUILD_ONHOVER_DEPOSIT(){return format(GUILD_ONHOVER_DEPOSIT, "GUILD", "GUILD_ONHOVER_DEPOSIT");}
    
    public static Text GUILD_ONHOVER_INVIT(){return format(GUILD_ONHOVER_INVIT, "GUILD", "GUILD_ONHOVER_INVIT");}
    
    public static Text GUILD_ONHOVER_SETGRADE(){return format(GUILD_ONHOVER_SETGRADE, "GUILD", "GUILD_ONHOVER_SETGRADE");}
    
    public static Text GUILD_ONHOVER_REMOVEMEMBER(){return format(GUILD_ONHOVER_REMOVEMEMBER, "GUILD", "GUILD_ONHOVER_REMOVEMEMBER");}
    
    public static Text GUILD_ONHOVER_WITHDRAWAL(){return format(GUILD_ONHOVER_WITHDRAWAL, "GUILD", "GUILD_ONHOVER_WITHDRAWAL");}
    
    public static Text GUILD_ONHOVER_RENAME(){return format(GUILD_ONHOVER_RENAME, "GUILD", "GUILD_ONHOVER_RENAME");}
    
    public static Text GUILD_ONHOVER_DELETE(){return format(GUILD_ONHOVER_DELETE, "GUILD", "GUILD_ONHOVER_DELETE");}
    
    public static Text GUILD_ONHOVER_LIST_LVL10(String guildName, String ownerName){return format(GUILD_ONHOVER_LIST_LVL10, "GUILD", "GUILD_ONHOVER_LIST_LVL10", guildName, ownerName);}
    
    //-------------------------
    // Message PLOT / PARCELLE
    //-------------------------
    
    public static Text BUYING_COST_PLOT(Player player, String var1, String var2){return format(BUYING_COST_PLOT, "PLOT", "BUYING_COST_PLOT", player, var1, var2);}
    
    public static Text PROTECT_PLOT_SUCCESS(Player player, String var1){return format(PROTECT_PLOT_SUCCESS, "PLOT", "PROTECT_PLOT_SUCCESS", player, var1, "");}
    
    public static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS(Player player, String var1){return format(BEDROCK2SKY_PROTECT_PLOT_SUCCESS, "PLOT", "BEDROCK2SKY_PROTECT_PLOT_SUCCESS", player, var1, "");}
    
    public static Text PROTECT_LOADED_PLOT(Player player, String var1){return format(PROTECT_LOADED_PLOT, "PLOT", "PROTECT_LOADED_PLOT", player, var1, "");}
    
    public static Text UNDEFINED_PLOT_ANGLES(){return format(UNDEFINED_PLOT_ANGLES, "PLOT", "UNDEFINED_PLOT_ANGLES");}
        
    public static Text ALREADY_OWNED_PLOT(){return format(ALREADY_OWNED_PLOT, "PLOT", "ALREADY_OWNED_PLOT");}
    
    public static Text EXPAND_NOT_ALLOWED(){return format(EXPAND_NOT_ALLOWED, "PLOT", "EXPAND_NOT_ALLOWED");}
    
    public static Text NO_PLOT(){return format(NO_PLOT, "PLOT", "NO_PLOT");}
    
    public static Text PLOT_INFO(Player player, String owner, String allow, String plot){return format(PLOT_INFO, "PLOT", "PLOT_INFO", player, "","", owner, allow, plot);}
    
    public static Text PLOT_LIST(){return format(PLOT_LIST, "PLOT", "PLOT_LIST");}
     
    public static Text TARGET_PLOT_LIST(String target){return format(TARGET_PLOT_LIST, "PLOT", "TARGET_PLOT_LIST",target,"");}
    
    public static Text PLOT_PROTECTED(){return format(PLOT_PROTECTED, "PLOT", "PLOT_PROTECTED");}
    
    public static Text PLOT_NO_ENTER(){return format(PLOT_NO_ENTER, "PLOT", "PLOT_NO_ENTER");}
    
    public static Text PLOT_NO_FLY(){return format(PLOT_NO_FLY, "PLOT", "PLOT_NO_FLY");}
    
    public static Text PLOT_NO_BREAK(){return format(PLOT_NO_BREAK, "PLOT", "PLOT_NO_BREAK");}
    
    public static Text PLOT_NO_BUILD(){return format(PLOT_NO_BUILD, "PLOT", "PLOT_NO_BUILD");}
    
    public static Text PLOT_NO_FIRE(){return format(PLOT_NO_FIRE, "PLOT", "PLOT_NO_FIRE");}
    
    public static Text PLOT_NO_EXIT(){return format(PLOT_NO_EXIT, "PLOT", "PLOT_NO_EXIT");}
    
    //-------------------------
    // Message ECONOMY
    //-------------------------
    
    public static Text MISSING_BALANCE(){return format(MISSING_BALANCE, "ECONOMY", "MISSING_BALANCE");}
    
    public static Text GUILD_MISSING_BALANCE(){return format(GUILD_MISSING_BALANCE, "ECONOMY", "GUILD_MISSING_BALANCE");}
    
    public static Text WITHDRAW_SUCCESS(String amount){return format(WITHDRAW_SUCCESS, "ECONOMY", "WITHDRAW_SUCCESS",amount, "");}
    
    public static Text DEPOSIT_SUCCESS(String amount){return format(DEPOSIT_SUCCESS, "ECONOMY", "DEPOSIT_SUCCESS",amount, "");}
    
    public static Text CLICK_TO_CONFIRM(){return format(CLICK_TO_CONFIRM, "ECONOMY", "CLICK_TO_CONFIRM");}
    
    //-------------------------
    // Message TELEPORATION
    //-------------------------
    
    public static Text TP_AT_COORDS(){return format(TP_AT_COORDS, "TELEPORTATION", "TP_AT_COORDS");}
    
    public static Text TP_BACK(Player player){return format(TP_BACK, "TELEPORATION", "TP_BACK",player);}
    
    public static Text HOME_ALREADY_EXIST(){return format(HOME_ALREADY_EXIST, "TELEPORTATION","HOME_ALREADY_EXIST");}
    
    public static Text HOME_SET_SUCCESS(Player player, String var1){return format(HOME_SET_SUCCESS, "TELEPORTATION","HOME_SET_SUCCESS",player,var1,"");}
    
    public static Text HOME_DEL_SUCCESS(Player player, String var1){return format(HOME_DEL_SUCCESS, "TELEPORTATION","HOME_DEL_SUCCESS",player,var1,"");}
    
    public static Text NB_HOME(Player player, String var1, String var2){return format(NB_HOME, "TELEPORTATION","NB_HOME",player,var1,var2);}
    
    public static Text NB_ALLOWED_HOME(Player player, String var1){return format(NB_ALLOWED_HOME, "TELEPORTATION","NB_ALLOWED_HOME", player, var1, "");}
    
    public static Text HOME_NOT_FOUND(){return format(HOME_NOT_FOUND, "TELEPORTATION","HOME_NOT_FOUND");}
    
    public static Text HOME_TP_SUCCESS(Player player, String var1){return format(HOME_TP_SUCCESS, "TELEPORTATION","HOME_TP_SUCCESS", player, var1, "");}
    
    public static Text WARP_ALREADY_EXIST(){return format(WARP_ALREADY_EXIST, "TELEPORTATION","WARP_ALREADY_EXIST");}
    
    public static Text WARP_SET_SUCCESS(Player player, String var1){return format(WARP_SET_SUCCESS, "TELEPORTATION","WARP_SET_SUCCESS",player,var1,"");}
    
    public static Text WARP_DEL_SUCCESS(Player player, String var1){return format(WARP_DEL_SUCCESS, "TELEPORTATION","WARP_DEL_SUCCESS",player,var1,"");}
    
    //-------------------------
    // Message Commande PlayerInfo
    //-------------------------

    public static Text PI_ONHOVER_HOME(){return format(PI_ONHOVER_HOME, "CMD_PLAYERINFO","PI_ONHOVER_HOME");}

    public static Text PI_ONHOVER_PLOT(){return format(PI_ONHOVER_PLOT, "CMD_PLAYERINFO","PI_ONHOVER_PLOT");}

    public static Text PI_ONHOVER_GUILD(String grade){return format(PI_ONHOVER_GUILD, "CMD_PLAYERINFO","PI_ONHOVER_GUILD",grade, "");}
    
    public static Text PI_ONHOVER_NOGUILD(){return format(PI_ONHOVER_NOGUILD, "CMD_PLAYERINFO","PI_ONHOVER_NOGUILD");}
    
    public static Text PI_ADMIN_ONLINE_ONHOVER_NAME(String UUID){return format(PI_ADMIN_ONLINE_ONHOVER_NAME, "CMD_PLAYERINFO","PI_ADMIN_ONLINE_ONHOVER_NAME",UUID, "");}
      
    public static Text PI_ADMIN_OFFLINE_ONHOVER_NAME(String UUID){return format(PI_ADMIN_OFFLINE_ONHOVER_NAME, "CMD_PLAYERINFO","PI_ADMIN_OFFLINE_ONHOVER_NAME",UUID, "");}
      
    public static Text PI_ADMIN_ONHOVER_TP(){return format(PI_ADMIN_ONHOVER_TP, "CMD_PLAYERINFO","PI_ADMIN_ONHOVER_TP");}
    
    //-------------------------
    // Message WORLD
    //-------------------------
    
    public static Text WORLD_ALREADY_EXIST(){return format(WORLD_ALREADY_EXIST, "WORLD","WORLD_ALREADY_EXIST");}
    
    public static Text WORLD_CREATED(Player player, String var1){return format(WORLD_CREATED, "WORLD","WORLD_CREATED",player,var1,"");}
    
    public static Text TELEPORTED_TO_WORLD(Player player, String var1){return format(TELEPORTED_TO_WORLD, "WORLD","TELEPORTED_TO_WORLD",player,var1,"");}
    
    public static Text OTHER_TELEPORTED_TO_WORLD(Player player, String var1){return format(OTHER_TELEPORTED_TO_WORLD, "WORLD","OTHER_TELEPORTED_TO_WORLD",player,var1,"");}

    public static Text WORLD_PROPERTIES_ERROR(){return format(WORLD_PROPERTIES_ERROR, "WORLD","WORLD_PROPERTIES_ERROR");}
    
    public static Text WORLD_CREATION_ERROR(){return format(WORLD_CREATION_ERROR, "WORLD","WORLD_CREATION_ERROR");}
    
    //-------------------------
    // Message PORTAL
    //-------------------------
    
    public static Text PROTECT_PORTAL(){return format(PROTECT_PORTAL, "PORTAL","PROTECT_PORTAL");}
    
    //-------------------------
    // Message SHOP
    //-------------------------
    
    public static Text SHOP_SALE(){return format(SHOP_SALE, "SHOP","SHOP_SALE");}
    
    public static Text SHOP_BUY(){return format(SHOP_BUY, "SHOP","SHOP_BUY");}
              
    //-------------------------
    // Message CHEST
    //-------------------------
    
    public static Text CHEST_LOCK(){return format(CHEST_LOCK, "CHEST","CHEST_LOCK");}
    
    //-------------------------
    // Message ARMORSTAND
    //-------------------------
    
    public static Text CHEST_ROTATION(){return format(CHEST_ROTATION, "ARMORSTAND","CHEST_ROTATION");}
    
    public static Text HEAD_ROTATION(){return format(HEAD_ROTATION, "ARMORSTAND","HEAD_ROTATION");}
    
    public static Text LEFT_ARM_ROTATION(){return format(LEFT_ARM_ROTATION, "ARMORSTAND","LEFT_ARM_ROTATION");}
    
    public static Text RIGHT_ARM_ROTATION(){return format(RIGHT_ARM_ROTATION, "ARMORSTAND","RIGHT_ARM_ROTATION");}
    
    public static Text LEFT_LEG_ROTATION(){return format(LEFT_LEG_ROTATION, "ARMORSTAND","LEFT_LEG_ROTATION");}
    
    public static Text RIGHT_LEG_ROTATION(){return format(RIGHT_LEG_ROTATION, "ARMORSTAND","RIGHT_LEG_ROTATION");}
    
    public static Text POSITION_X_AS(){return format(POSITION_X_AS, "ARMORSTAND","POSITION_X_AS");}
    
    public static Text POSITION_Y_AS(){return format(POSITION_Y_AS, "ARMORSTAND","POSITION_Y_AS");}
    
    public static Text POSITION_Z_AS(){return format(POSITION_Z_AS, "ARMORSTAND","POSITION_Z_AS");}
    
    public static Text ROTATION_AS(){return format(ROTATION_AS, "ARMORSTAND","ROTATION_AS");}
    
    public static Text AS_HAS_BASE_PLATE(){return format(AS_HAS_BASE_PLATE, "ARMORSTAND","AS_HAS_BASE_PLATE");}
    
    public static Text AS_HAS_GRAVITY(){return format(AS_HAS_GRAVITY, "ARMORSTAND","AS_HAS_GRAVITY");}
    
    public static Text AS_IS_SMALL(){return format(AS_IS_SMALL, "ARMORSTAND","AS_IS_SMALL");}
    
    public static Text AS_NAME_VISIBLE(){return format(AS_NAME_VISIBLE, "ARMORSTAND","AS_NAME_VISIBLE");}
    
    public static Text AS_MARKER(){return format(AS_MARKER, "ARMORSTAND","AS_MARKER");}
    
    public static Text AS_HAS_ARMS(){return format(AS_HAS_ARMS, "ARMORSTAND","AS_HAS_ARMS");}
    
    public static Text AS_INVISIBLE(){return format(AS_INVISIBLE, "ARMORSTAND","AS_INVISIBLE");}
    
    //-------------------------
    // Message GRAVE
    //-------------------------
    
    public static Text GRAVE_BREAK(Player player){return format(GRAVE_BREAK, "GRAVE","GRAVE_BREAK",player);}
    
    public static Text GRAVE_RIP1(Player player){return format(GRAVE_RIP1, "GRAVE","GRAVE_RIP1",player);}
    
    public static Text GRAVE_RIP2(Player player){return format(GRAVE_RIP2, "GRAVE","GRAVE_RIP2",player);}
    
    public static Text GRAVE_RIP3(Player player){return format(GRAVE_RIP3, "GRAVE","GRAVE_RIP3",player);}
    
    public static Text GRAVE_RIP4(Player player){return format(GRAVE_RIP4, "GRAVE","GRAVE_RIP4",player);}
    
    public static Text GRAVE_POSITION(){return format(GRAVE_POSITION, "GRAVE","GRAVE_POSITION");}
    
    public static Text USAGE(String usage){
        Text USAGE = (Text.of(TextColors.DARK_RED, "Usage: ", TextColors.RED, usage)); 
        return USAGE;
    }
    
    public static Text MESSAGE(List<String> list, Player player, String var){
        Text MessageText;
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var);
        
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
    
    public static Text MESSAGE(String msg, Player player, String var){
        Text MessageText;
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var);
        
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
    
    public static Text MESSAGE(String msg, Player player){
        Text MessageText;
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
    
    public static Text MESSAGE(String msg){
        Text MessageText;       
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
}
