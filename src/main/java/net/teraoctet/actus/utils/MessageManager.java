package net.teraoctet.actus.utils;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class MessageManager {
    private static List<String> RULES;
    private static List<String> NO_PERMISSIONS;
    private static List<String> NO_CONSOLE;
    private static List<String> JOIN_MESSAGE;
    private static List<String> EVENT_LOGIN_MESSAGE;
    private static List<String> FIRSTJOIN_MESSAGE;
    private static List<String> FIRSTJOIN_BROADCAST_MESSAGE;
    private static List<String> EVENT_DISCONNECT_MESSAGE;
    private static List<String> NAME_CHANGE;
    private static List<String> WRONG_NAME;
    private static List<String> SUN_MESSAGE;
    private static List<String> RAIN_MESSAGE;
    private static List<String> DAY_MESSAGE;
    private static List<String> NIGHT_MESSAGE;
    private static List<String> STORM_MESSAGE;
    private static List<String> GUIDE_GUILD;
    private static List<String> NO_GUILD;
    private static List<String> NOT_IN_SAME_GUILD;
    private static List<String> WRONG_RANK;
    private static List<String> OWNER_CANNOT_LEAVE;
    private static List<String> ALREADY_GUILD_MEMBER;
    private static List<String> GUILD_CREATED_SUCCESS;
    private static List<String> GUILD_RENAMED_SUCCESS;
    private static List<String> GUILD_DELETED_SUCCESS;
    private static List<String> LEAVING_GUILD_SUCCESS;
    private static List<String> GUILD_MEMBER_REMOVED_SUCCESS;
    private static List<String> GUILD_RETURNED_BY;
    private static List<String> GUILD_DELETED_NOTIFICATION;
    private static List<String> GUILD_NEW_CHEF;
    private static List<String> GUILD_CHEF_GRADE_GIVEN;
    private static List<String> GUILD_YOU_ARE_NEW_CHEF;
    private static List<String> BUYING_COST_PLOT;
    private static List<String> PROTECT_PLOT_SUCCESS;
    private static List<String> BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
    private static List<String> PROTECT_LOADED_PLOT;
    private static List<String> UNDEFINED_PLOT_ANGLES;
    private static List<String> ALREADY_OWNED_PLOT;
    private static List<String> EXPAND_NOT_ALLOWED;
    private static List<String> NAME_ALREADY_USED;
    private static List<String> NO_PLOT;
    private static List<String> PLOT_INFO;
    private static List<String> TARGET_PLOT_LIST;
    private static List<String> PLOT_LIST;
    private static List<String> PLOT_PROTECTED;
    private static List<String> PLOT_NO_FLY;
    private static List<String> PLOT_NO_ENTER;
    private static List<String> PLOT_NO_BREAK;
    private static List<String> PLOT_NO_BUILD;
    private static List<String> PLOT_NO_FIRE;
    private static List<String> PLOT_NO_EXIT;
    private static List<String> MISSING_BALANCE;
    private static List<String> DEPOSIT_SUCCESS;
    private static List<String> HOME_ALREADY_EXIST;
    private static List<String> HOME_SET_SUCCESS;
    private static List<String> HOME_DEL_SUCCESS;
    private static List<String> NB_HOME;
    private static List<String> NB_ALLOWED_HOME;
    private static List<String> HOME_NOT_FOUND;
    private static List<String> ERROR;
    private static List<String> HOME_TP_SUCCESS;
    private static List<String> NOT_FOUND;
    private static List<String> NOT_CONNECTED;
    private static List<String> DATA_NOT_FOUND;
    private static List<String> CANNOT_EJECT_OWNER;
    private static List<String> WORLD_ALREADY_EXIST;
    private static List<String> WORLD_CREATED;
    private static List<String> WORLD_CREATION_ERROR;
    private static List<String> WORLD_PROPERTIES_ERROR;
    private static List<String> TELEPORTED_TO_WORLD;
    private static List<String> OTHER_TELEPORTED_TO_WORLD;
    private static List<String> PROTECT_PORTAL;
    private static List<String> TP_BACK;
    private static List<String> INVENTORY_CLEARED;
    private static List<String> CLEARINVENTORY_SUCCESS;
    private static List<String> FLY_ENABLED;
    private static List<String> FLY_DISABLED;
    private static List<String> FLY_GIVEN;
    private static List<String> FLY_RETIRED;
    private static List<String> TP_AT_COORDS;
    private static List<String> WRONG_CHARACTERS_NUMBER;
    private static List<String> KILLED_BY;
    private static List<String> SUICIDE;
    private static List<String> GUILD_ONHOVER_MOREACTIONS;
    private static List<String> GUILD_ONHOVER_SETGRADE;
    private static List<String> GUILD_ONHOVER_RENAME;
    private static List<String> GUILD_ONHOVER_INVIT;
    private static List<String> GUILD_ONHOVER_DELETE;
    private static List<String> GUILD_ONHOVER_REMOVEMEMBER;
    private static List<String> GUILD_ONHOVER_WITHDRAWAL;
    private static List<String> GUILD_ONHOVER_DEPOSIT;
    private static List<String> GUILD_ONHOVER_LEAVE;
    private static List<String> GUILD_ONHOVER_LIST_LVL10;
    private static List<String> PI_ONHOVER_HOME;
    private static List<String> PI_ONHOVER_PLOT;
    private static List<String> PI_ONHOVER_GUILD;
    private static List<String> PI_ONHOVER_NOGUILD;
    private static List<String> PI_ADMIN_ONLINE_ONHOVER_NAME;    
    private static List<String> PI_ADMIN_OFFLINE_ONHOVER_NAME;
    private static List<String> PI_ADMIN_ONHOVER_TP;
    private static List<String> SHOP_SALE;
    private static List<String> SHOP_BUY;
    private static List<String> WITHDRAW_SUCCESS;
    private static List<String> GUILD_MISSING_BALANCE;
    private static List<String> BUTCHER;
    private static List<String> GRAVE;
    private static List<String> CHEST_LOCK;
    private static List<String> LAST_CONNECT;
    private static List<String> CLICK_TO_CONFIRM;
    private static List<String> CHEST_ROTATION;
    private static List<String> HEAD_ROTATION;
    private static List<String> LEFT_ARM_ROTATION;
    private static List<String> RIGHT_ARM_ROTATION;
    private static List<String> LEFT_LEG_ROTATION;
    private static List<String> RIGHT_LEG_ROTATION;
    private static List<String> POSITION_X_AS;
    private static List<String> POSITION_Y_AS;
    private static List<String> POSITION_Z_AS;
    private static List<String> ROTATION_AS;
    private static List<String> AS_HAS_BASE_PLATE;
    private static List<String> AS_HAS_GRAVITY;
    private static List<String> AS_IS_SMALL;
    private static List<String> AS_NAME_VISIBLE;
    private static List<String> AS_MARKER;
    private static List<String> AS_HAS_ARMS;
    private static List<String> AS_INVISIBLE;
    private static List<String> GRAVE_BREAK;
    private static List<String> GRAVE_RIP1;
    private static List<String> GRAVE_RIP2;
    private static List<String> GRAVE_RIP3;
    private static List<String> GRAVE_RIP4;
    private static List<String> GRAVE_POSITION;
    private static List<String> WARP_ALREADY_EXIST;
    private static List<String> WARP_SET_SUCCESS;
    private static List<String> WARP_DEL_SUCCESS;
           
    public static void init() {
        File FILE = new File("config/actus/message.conf");
        ConfigurationLoader<?> MANAGER = HoconConfigurationLoader.builder().setFile(FILE).build();
        ConfigurationNode message;
        
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();
             }    
                
            List<String> msg;
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
                message.getNode("EXCEPTION","NO_PERMISSIONS").setValue(TypeToken.of(TextTemplate.class), TextTemplate.of(msg));
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
                
            RULES = message.getNode("SERVER", "RULES").getList(TypeToken.of(String.class));
            JOIN_MESSAGE = message.getNode("SERVER", "JOIN_MESSAGE").getList(TypeToken.of(String.class));
            EVENT_LOGIN_MESSAGE = message.getNode("SERVER", "EVENT_LOGIN_MESSAGE").getList(TypeToken.of(String.class));
            FIRSTJOIN_MESSAGE = message.getNode("SERVER", "FIRSTJOIN_MESSAGE").getList(TypeToken.of(String.class));
            FIRSTJOIN_BROADCAST_MESSAGE = message.getNode("SERVER", "FIRSTJOIN_BROADCAST_MESSAGE").getList(TypeToken.of(String.class));
            EVENT_DISCONNECT_MESSAGE = message.getNode("SERVER", "EVENT_DISCONNECT_MESSAGE").getList(TypeToken.of(String.class));
            NAME_CHANGE = message.getNode("SERVER", "NAME_CHANGE").getList(TypeToken.of(String.class));
            INVENTORY_CLEARED = message.getNode("SERVER", "INVENTORY_CLEARED").getList(TypeToken.of(String.class));
            CLEARINVENTORY_SUCCESS = message.getNode("SERVER", "CLEARINVENTORY_SUCCESS").getList(TypeToken.of(String.class));
            FLY_ENABLED = message.getNode("SERVER", "FLY_ENABLED").getList(TypeToken.of(String.class));
            FLY_DISABLED = message.getNode("SERVER", "FLY_DISABLED").getList(TypeToken.of(String.class));
            FLY_GIVEN = message.getNode("SERVER", "FLY_GIVEN").getList(TypeToken.of(String.class));
            FLY_RETIRED = message.getNode("SERVER", "FLY_RETIRED").getList(TypeToken.of(String.class));
            BUTCHER = message.getNode("SERVER", "BUTCHER").getList(TypeToken.of(String.class));
            GRAVE = message.getNode("SERVER", "GRAVE").getList(TypeToken.of(String.class));
            LAST_CONNECT = message.getNode("SERVER", "LAST_CONNECT").getList(TypeToken.of(String.class));

            //-------------------------
            // Message EXCEPTION / ERROR
            //-------------------------

            ERROR = message.getNode("EXCEPTION","ERROR").getList(TypeToken.of(String.class));
            NO_PERMISSIONS = message.getNode("EXCEPTION","NO_PERMISSIONS").getList(TypeToken.of(String.class));
            NO_CONSOLE = message.getNode("EXCEPTION", "NO_CONSOLE").getList(TypeToken.of(String.class));
            WRONG_CHARACTERS_NUMBER = message.getNode("EXCEPTION", "WRONG_CHARACTERS_NUMBER").getList(TypeToken.of(String.class));
            WRONG_NAME = message.getNode("EXCEPTION", "WRONG_NAME").getList(TypeToken.of(String.class));
            NAME_ALREADY_USED = message.getNode("EXCEPTION", "NAME_ALREADY_USED").getList(TypeToken.of(String.class));
            NOT_FOUND = message.getNode("EXCEPTION", "NOT_FOUND").getList(TypeToken.of(String.class));
            NOT_CONNECTED = message.getNode("EXCEPTION", "NOT_CONNECTED").getList(TypeToken.of(String.class));
            DATA_NOT_FOUND = message.getNode("EXCEPTION", "DATA_NOT_FOUND").getList(TypeToken.of(String.class));
            CANNOT_EJECT_OWNER = message.getNode("EXCEPTION", "CANNOT_EJECT_OWNER").getList(TypeToken.of(String.class));
    
            //-------------------------
            // Message DEAD_MSG
            //-------------------------

            KILLED_BY = message.getNode("DEAD_MSG", "KILLED_BY").getList(TypeToken.of(String.class));
            SUICIDE = message.getNode("DEAD_MSG", "SUICIDE").getList(TypeToken.of(String.class));

            //-------------------------
            // Message weather / time
            //-------------------------

            SUN_MESSAGE = message.getNode("WEATHER-TIME","SUN_MESSAGE").getList(TypeToken.of(String.class));
            RAIN_MESSAGE = message.getNode("WEATHER-TIME","RAIN_MESSAGE").getList(TypeToken.of(String.class));
            DAY_MESSAGE = message.getNode("WEATHER-TIME","DAY_MESSAGE").getList(TypeToken.of(String.class));
            NIGHT_MESSAGE = message.getNode("WEATHER-TIME","NIGHT_MESSAGE").getList(TypeToken.of(String.class));
            STORM_MESSAGE = message.getNode("WEATHER-TIME","STORM_MESSAGE").getList(TypeToken.of(String.class));
    
            //-------------------------
            // Message GUILD
            //-------------------------

            NO_GUILD = message.getNode("GUILD", "NO_GUILD").getList(TypeToken.of(String.class));
            WRONG_RANK = message.getNode("GUILD", "WRONG_RANK").getList(TypeToken.of(String.class));
            NOT_IN_SAME_GUILD = message.getNode("GUILD", "NOT_IN_SAME_GUILD").getList(TypeToken.of(String.class));
            ALREADY_GUILD_MEMBER = message.getNode("GUILD", "ALREADY_GUILD_MEMBER").getList(TypeToken.of(String.class));
            OWNER_CANNOT_LEAVE = message.getNode("GUILD", "OWNER_CANNOT_LEAVE").getList(TypeToken.of(String.class));
            GUILD_NEW_CHEF = message.getNode("GUILD", "GUILD_NEW_CHEF").getList(TypeToken.of(String.class));
            GUILD_CHEF_GRADE_GIVEN = message.getNode("GUILD", "GUILD_CHEF_GRADE_GIVEN").getList(TypeToken.of(String.class));
            GUILD_YOU_ARE_NEW_CHEF = message.getNode("GUILD", "GUILD_YOU_ARE_NEW_CHEF").getList(TypeToken.of(String.class));
            GUIDE_GUILD = message.getNode("GUILD", "GUIDE_GUILD").getList(TypeToken.of(String.class));
            GUILD_CREATED_SUCCESS = message.getNode("GUILD", "GUILD_CREATED_SUCCESS").getList(TypeToken.of(String.class));
            GUILD_RENAMED_SUCCESS = message.getNode("GUILD", "GUILD_RENAMED_SUCCESS").getList(TypeToken.of(String.class));
            GUILD_DELETED_SUCCESS = message.getNode("GUILD_DELETED_SUCCESS").getList(TypeToken.of(String.class));
            LEAVING_GUILD_SUCCESS = message.getNode("GUILD", "LEAVING_GUILD_SUCCESS").getList(TypeToken.of(String.class));
            GUILD_MEMBER_REMOVED_SUCCESS = message.getNode("GUILD", "GUILD_MEMBER_REMOVED_SUCCESS").getList(TypeToken.of(String.class));   
            GUILD_RETURNED_BY = message.getNode("GUILD", "GUILD_RETURNED_BY").getList(TypeToken.of(String.class));
            GUILD_DELETED_NOTIFICATION = message.getNode("GUILD", "GUILD_DELETED_NOTIFICATION").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_MOREACTIONS = message.getNode("GUILD", "GUILD_ONHOVER_MOREACTIONS").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_LEAVE = message.getNode("GUILD", "GUILD_ONHOVER_LEAVE").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_DEPOSIT = message.getNode("GUILD", "GUILD_ONHOVER_DEPOSIT").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_INVIT = message.getNode("GUILD", "GUILD_ONHOVER_INVIT").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_SETGRADE = message.getNode("GUILD", "GUILD_ONHOVER_SETGRADE").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_REMOVEMEMBER = message.getNode("GUILD", "GUILD_ONHOVER_REMOVEMEMBER").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_WITHDRAWAL = message.getNode("GUILD", "GUILD_ONHOVER_WITHDRAWAL").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_RENAME = message.getNode("GUILD", "GUILD_ONHOVER_RENAME").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_DELETE = message.getNode("GUILD", "GUILD_ONHOVER_DELETE").getList(TypeToken.of(String.class));
            GUILD_ONHOVER_LIST_LVL10 = message.getNode("GUILD", "GUILD_ONHOVER_LIST_LVL10").getList(TypeToken.of(String.class));

            //-------------------------
            // Message PLOT / PARCELLE
            //-------------------------

            BUYING_COST_PLOT = message.getNode("PLOT", "BUYING_COST_PLOT").getList(TypeToken.of(String.class));
            PROTECT_PLOT_SUCCESS = message.getNode("PLOT", "PROTECT_PLOT_SUCCESS").getList(TypeToken.of(String.class));
            BEDROCK2SKY_PROTECT_PLOT_SUCCESS = message.getNode("PLOT", "BEDROCK2SKY_PROTECT_PLOT_SUCCESS").getList(TypeToken.of(String.class));
            PROTECT_LOADED_PLOT = message.getNode("PLOT", "PROTECT_LOADED_PLOT").getList(TypeToken.of(String.class));
            UNDEFINED_PLOT_ANGLES = message.getNode("PLOT", "UNDEFINED_PLOT_ANGLES").getList(TypeToken.of(String.class));
            ALREADY_OWNED_PLOT = message.getNode("PLOT", "ALREADY_OWNED_PLOT").getList(TypeToken.of(String.class));
            EXPAND_NOT_ALLOWED = message.getNode("PLOT", "EXPAND_NOT_ALLOWED").getList(TypeToken.of(String.class));
            NO_PLOT = message.getNode("PLOT", "NO_PLOT").getList(TypeToken.of(String.class));
            PLOT_INFO = message.getNode("PLOT", "PLOT_INFO").getList(TypeToken.of(String.class));
            PLOT_LIST = message.getNode("PLOT", "PLOT_LIST").getList(TypeToken.of(String.class));
            TARGET_PLOT_LIST = message.getNode("PLOT", "TARGET_PLOT_LIST").getList(TypeToken.of(String.class));
            PLOT_PROTECTED = message.getNode("PLOT", "PLOT_PROTECTED").getList(TypeToken.of(String.class));
            PLOT_NO_ENTER = message.getNode("PLOT", "PLOT_NO_ENTER").getList(TypeToken.of(String.class));
            PLOT_NO_FLY = message.getNode("PLOT", "PLOT_NO_FLY").getList(TypeToken.of(String.class));
            PLOT_NO_BREAK = message.getNode("PLOT", "PLOT_NO_BREAK").getList(TypeToken.of(String.class));
            PLOT_NO_BUILD = message.getNode("PLOT", "PLOT_NO_BUILD").getList(TypeToken.of(String.class));
            PLOT_NO_FIRE = message.getNode("PLOT", "PLOT_NO_FIRE").getList(TypeToken.of(String.class));
            PLOT_NO_EXIT = message.getNode("PLOT", "PLOT_NO_EXIT").getList(TypeToken.of(String.class));

            //-------------------------
            // Message ECONOMY
            //-------------------------

            MISSING_BALANCE = message.getNode("ECONOMY", "MISSING_BALANCE").getList(TypeToken.of(String.class));
            GUILD_MISSING_BALANCE = message.getNode("ECONOMY", "GUILD_MISSING_BALANCE").getList(TypeToken.of(String.class));
            WITHDRAW_SUCCESS = message.getNode("ECONOMY", "WITHDRAW_SUCCESS").getList(TypeToken.of(String.class));
            DEPOSIT_SUCCESS = message.getNode("ECONOMY", "DEPOSIT_SUCCESS").getList(TypeToken.of(String.class));
            CLICK_TO_CONFIRM = message.getNode("ECONOMY", "CLICK_TO_CONFIRM").getList(TypeToken.of(String.class));
    
            //-------------------------
            // Message TELEPORATION
            //-------------------------

            TP_AT_COORDS = message.getNode("TELEPORTATION", "TP_AT_COORDS").getList(TypeToken.of(String.class));
            TP_BACK = message.getNode("TELEPORATION", "TP_BACK").getList(TypeToken.of(String.class));
            HOME_ALREADY_EXIST = message.getNode("TELEPORTATION","HOME_ALREADY_EXIST").getList(TypeToken.of(String.class));
            HOME_SET_SUCCESS = message.getNode("TELEPORTATION","HOME_SET_SUCCESS").getList(TypeToken.of(String.class));
            HOME_DEL_SUCCESS = message.getNode("TELEPORTATION","HOME_DEL_SUCCESS").getList(TypeToken.of(String.class));
            NB_HOME = message.getNode("TELEPORTATION","NB_HOME").getList(TypeToken.of(String.class));
            NB_ALLOWED_HOME = message.getNode("TELEPORTATION","NB_ALLOWED_HOME").getList(TypeToken.of(String.class));
            HOME_NOT_FOUND = message.getNode("TELEPORTATION","HOME_NOT_FOUND").getList(TypeToken.of(String.class));
            HOME_TP_SUCCESS = message.getNode("TELEPORTATION","HOME_TP_SUCCESS").getList(TypeToken.of(String.class));
            WARP_ALREADY_EXIST = message.getNode("TELEPORTATION","WARP_ALREADY_EXIST").getList(TypeToken.of(String.class));
            WARP_SET_SUCCESS = message.getNode("TELEPORTATION","WARP_SET_SUCCESS").getList(TypeToken.of(String.class));
            WARP_DEL_SUCCESS = message.getNode("TELEPORTATION","WARP_DEL_SUCCESS").getList(TypeToken.of(String.class));

            //-------------------------
            // Message Commande PlayerInfo
            //-------------------------

            PI_ONHOVER_HOME = message.getNode("CMD_PLAYERINFO","PI_ONHOVER_HOME").getList(TypeToken.of(String.class));
            PI_ONHOVER_PLOT = message.getNode("CMD_PLAYERINFO","PI_ONHOVER_PLOT").getList(TypeToken.of(String.class));
            PI_ONHOVER_GUILD = message.getNode("CMD_PLAYERINFO","PI_ONHOVER_GUILD").getList(TypeToken.of(String.class));
            PI_ONHOVER_NOGUILD = message.getNode("CMD_PLAYERINFO","PI_ONHOVER_NOGUILD").getList(TypeToken.of(String.class));
            PI_ADMIN_ONLINE_ONHOVER_NAME = message.getNode("CMD_PLAYERINFO","PI_ADMIN_ONLINE_ONHOVER_NAME").getList(TypeToken.of(String.class));
            PI_ADMIN_OFFLINE_ONHOVER_NAME = message.getNode("CMD_PLAYERINFO","PI_ADMIN_OFFLINE_ONHOVER_NAME").getList(TypeToken.of(String.class));
            PI_ADMIN_ONHOVER_TP = message.getNode("CMD_PLAYERINFO","PI_ADMIN_ONHOVER_TP").getList(TypeToken.of(String.class));

            //-------------------------
            // Message WORLD
            //-------------------------

            WORLD_ALREADY_EXIST = message.getNode("WORLD","WORLD_ALREADY_EXIST").getList(TypeToken.of(String.class));
            WORLD_CREATED = message.getNode("WORLD","WORLD_CREATED").getList(TypeToken.of(String.class));
            TELEPORTED_TO_WORLD = message.getNode("WORLD","TELEPORTED_TO_WORLD").getList(TypeToken.of(String.class));
            OTHER_TELEPORTED_TO_WORLD = message.getNode("WORLD","OTHER_TELEPORTED_TO_WORLD").getList(TypeToken.of(String.class));
            WORLD_PROPERTIES_ERROR = message.getNode("WORLD","WORLD_PROPERTIES_ERROR").getList(TypeToken.of(String.class));
            WORLD_CREATION_ERROR = message.getNode("WORLD","WORLD_CREATION_ERROR").getList(TypeToken.of(String.class));

            //-------------------------
            // Message PORTAL
            //-------------------------

            PROTECT_PORTAL = message.getNode("PORTAL","PROTECT_PORTAL").getList(TypeToken.of(String.class));
    
            //-------------------------
            // Message SHOP
            //-------------------------

            SHOP_SALE = message.getNode("SHOP","SHOP_SALE").getList(TypeToken.of(String.class));
            SHOP_BUY = message.getNode("SHOP","SHOP_BUY").getList(TypeToken.of(String.class));

            //-------------------------
            // Message CHEST
            //-------------------------

            CHEST_LOCK = message.getNode("CHEST","CHEST_LOCK").getList(TypeToken.of(String.class));

            //-------------------------
            // Message ARMORSTAND
            //-------------------------
    
            CHEST_ROTATION = message.getNode("ARMORSTAND","CHEST_ROTATION").getList(TypeToken.of(String.class));
            HEAD_ROTATION = message.getNode("ARMORSTAND","HEAD_ROTATION").getList(TypeToken.of(String.class));
            LEFT_ARM_ROTATION = message.getNode("ARMORSTAND","LEFT_ARM_ROTATION").getList(TypeToken.of(String.class));
            RIGHT_ARM_ROTATION = message.getNode("ARMORSTAND","RIGHT_ARM_ROTATION").getList(TypeToken.of(String.class));
            LEFT_LEG_ROTATION = message.getNode("ARMORSTAND","LEFT_LEG_ROTATION").getList(TypeToken.of(String.class));
            RIGHT_LEG_ROTATION = message.getNode("ARMORSTAND","RIGHT_LEG_ROTATION").getList(TypeToken.of(String.class));
            POSITION_X_AS = message.getNode("ARMORSTAND","POSITION_X_AS").getList(TypeToken.of(String.class));
            POSITION_Y_AS = message.getNode("ARMORSTAND","POSITION_Y_AS").getList(TypeToken.of(String.class));
            POSITION_Z_AS = message.getNode("ARMORSTAND","POSITION_Z_AS").getList(TypeToken.of(String.class));
            ROTATION_AS = message.getNode("ARMORSTAND","ROTATION_AS").getList(TypeToken.of(String.class));
            AS_HAS_BASE_PLATE = message.getNode("ARMORSTAND","AS_HAS_BASE_PLATE").getList(TypeToken.of(String.class));
            AS_HAS_GRAVITY = message.getNode("ARMORSTAND","AS_HAS_GRAVITY").getList(TypeToken.of(String.class));
            AS_IS_SMALL = message.getNode("ARMORSTAND","AS_IS_SMALL").getList(TypeToken.of(String.class));
            AS_NAME_VISIBLE = message.getNode("ARMORSTAND","AS_NAME_VISIBLE").getList(TypeToken.of(String.class));
            AS_MARKER = message.getNode("ARMORSTAND","AS_MARKER").getList(TypeToken.of(String.class));
            AS_HAS_ARMS = message.getNode("ARMORSTAND","AS_HAS_ARMS").getList(TypeToken.of(String.class));
            AS_INVISIBLE = message.getNode("ARMORSTAND","AS_INVISIBLE").getList(TypeToken.of(String.class));

            //-------------------------
            // Message GRAVE
            //-------------------------

            GRAVE_BREAK = message.getNode("GRAVE","GRAVE_BREAK").getList(TypeToken.of(String.class));
            GRAVE_RIP1 = message.getNode("GRAVE","GRAVE_RIP1").getList(TypeToken.of(String.class));
            GRAVE_RIP2 = message.getNode("GRAVE","GRAVE_RIP2").getList(TypeToken.of(String.class));
            GRAVE_RIP3 = message.getNode("GRAVE","GRAVE_RIP3").getList(TypeToken.of(String.class));
            GRAVE_RIP4 = message.getNode("GRAVE","GRAVE_RIP4").getList(TypeToken.of(String.class));
            GRAVE_POSITION = message.getNode("GRAVE","GRAVE_POSITION").getList(TypeToken.of(String.class));
            
        } catch (IOException e) {} catch (ObjectMappingException ex) {
            Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
		
    }
        
    private static Text format(List<String> list){
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
    
    private static Text format(List<String> list, Player player){
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
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
    
    private static Text format(List<String> list, Player player, String var1, String var2){
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%player%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
    
    private static Text format(List<String> list, String var1, String var2){
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
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
    
    private static Text format(List<String> list, Player player, String var1, String var2, String owner, String allow, String plot){
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
        return Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
    }
    
    //-------------------------
    // Message général serveur
    //-------------------------
    
    public static Text RULES(){return format(RULES);}
    public static Text JOIN_MESSAGE(Player player){return format(JOIN_MESSAGE, player);}
    public static Text EVENT_LOGIN_MESSAGE(Player player){return format(EVENT_LOGIN_MESSAGE, player);}
    public static Text FIRSTJOIN_MESSAGE(Player player){return format(FIRSTJOIN_MESSAGE, player);}
    public static Text FIRSTJOIN_BROADCAST_MESSAGE(Player player){return format(FIRSTJOIN_BROADCAST_MESSAGE, player);}
    public static Text EVENT_DISCONNECT_MESSAGE(Player player){return format(EVENT_DISCONNECT_MESSAGE, player);}
    public static Text NAME_CHANGE(String oldName, String newName){return format(NAME_CHANGE, oldName, newName);}
    public static Text INVENTORY_CLEARED(){return format(INVENTORY_CLEARED);}
    public static Text CLEARINVENTORY_SUCCESS(String target){return format(CLEARINVENTORY_SUCCESS,target, "");}
    public static Text FLY_ENABLED(){return format(FLY_ENABLED);}
    public static Text FLY_DISABLED(){return format(FLY_DISABLED);}
    public static Text FLY_GIVEN(String player){return format(FLY_GIVEN,player, "");}
    public static Text FLY_RETIRED(String player){return format(FLY_RETIRED,player, "");}
    public static Text BUTCHER(String worldName){return format(BUTCHER, worldName, "");}
    public static Text GRAVE(Player player){return format(GRAVE, player);}
    public static Text LAST_CONNECT(String lastConnect){return format(LAST_CONNECT, lastConnect, "");}
    
    //-------------------------
    // Message EXCEPTION / ERROR
    //-------------------------
    
    public static Text ERROR(){return format(ERROR);}
    public static Text NO_PERMISSIONS(){return format(NO_PERMISSIONS);}
    public static Text NO_CONSOLE(){return format(NO_CONSOLE);}
    public static Text WRONG_CHARACTERS_NUMBER(String minLength, String maxLength){return format(WRONG_CHARACTERS_NUMBER, minLength, maxLength);}
    public static Text WRONG_NAME(){return format(WRONG_NAME);}
    public static Text NAME_ALREADY_USED(){return format(NAME_ALREADY_USED);}
    public static Text NOT_FOUND(String name){return format(NOT_FOUND,name, "");}
    public static Text NOT_CONNECTED(String name){return format(NOT_CONNECTED,name, "");}
    public static Text DATA_NOT_FOUND(String player){return format(DATA_NOT_FOUND,player, "");}
    public static Text CANNOT_EJECT_OWNER(){return format(CANNOT_EJECT_OWNER);}
    
    //-------------------------
    // Message DEAD_MSG
    //-------------------------
    
    public static Text KILLED_BY(String player, String killer){return format(KILLED_BY,player,killer);}
    public static Text SUICIDE(String player){return format(SUICIDE,player, "");}
    
    //-------------------------
    // Message weather / time
    //-------------------------
    
    public static Text SUN_MESSAGE(Player player){return format(SUN_MESSAGE, player);}
    public static Text RAIN_MESSAGE(Player player){return format(RAIN_MESSAGE, player);}
    public static Text DAY_MESSAGE(Player player){return format(DAY_MESSAGE, player);}
    public static Text NIGHT_MESSAGE(Player player){return format(NIGHT_MESSAGE, player);}
    public static Text STORM_MESSAGE(Player player){return format(STORM_MESSAGE, player);}
    
    //-------------------------
    // Message GUILD
    //-------------------------
    
    public static Text NO_GUILD(){return format(NO_GUILD);}
    public static Text WRONG_RANK(){return format(WRONG_RANK);}
    public static Text NOT_IN_SAME_GUILD(String targetName){return format(NOT_IN_SAME_GUILD, targetName, "");}
    public static Text ALREADY_GUILD_MEMBER(){return format(ALREADY_GUILD_MEMBER);}
    public static Text OWNER_CANNOT_LEAVE(){return format(OWNER_CANNOT_LEAVE);}
    public static Text GUILD_NEW_CHEF(String targetName, String guildName){return format(GUILD_NEW_CHEF, targetName, guildName);}
    public static Text GUILD_CHEF_GRADE_GIVEN(String targetName){return format(GUILD_CHEF_GRADE_GIVEN, targetName, "");}
    public static Text GUILD_YOU_ARE_NEW_CHEF(){return format(GUILD_YOU_ARE_NEW_CHEF);}
    public static Text GUIDE_GUILD(){return format(GUIDE_GUILD);}
    public static Text GUILD_CREATED_SUCCESS(String guildName){return format(GUILD_CREATED_SUCCESS, guildName, "");}
    public static Text GUILD_RENAMED_SUCCESS(String oldName, String newName){return format(GUILD_RENAMED_SUCCESS, oldName, newName);}
    public static Text GUILD_DELETED_SUCCESS(String guildName){return format(GUILD_DELETED_SUCCESS, guildName,"");}
    public static Text LEAVING_GUILD_SUCCESS(String guildName){return format(LEAVING_GUILD_SUCCESS, guildName, "");}
    public static Text GUILD_MEMBER_REMOVED_SUCCESS(String targetName){return format(GUILD_MEMBER_REMOVED_SUCCESS, targetName, "");}
    public static Text GUILD_RETURNED_BY(String src){return format(GUILD_RETURNED_BY, src, "");}
    public static Text GUILD_DELETED_NOTIFICATION(String guildName){return format(GUILD_DELETED_NOTIFICATION, guildName, "");}
    public static Text GUILD_ONHOVER_MOREACTIONS(){return format(GUILD_ONHOVER_MOREACTIONS);}
    public static Text GUILD_ONHOVER_LEAVE(){return format(GUILD_ONHOVER_LEAVE);}
    public static Text GUILD_ONHOVER_DEPOSIT(){return format(GUILD_ONHOVER_DEPOSIT);}
    public static Text GUILD_ONHOVER_INVIT(){return format(GUILD_ONHOVER_INVIT);}
    public static Text GUILD_ONHOVER_SETGRADE(){return format(GUILD_ONHOVER_SETGRADE);}
    public static Text GUILD_ONHOVER_REMOVEMEMBER(){return format(GUILD_ONHOVER_REMOVEMEMBER);}
    public static Text GUILD_ONHOVER_WITHDRAWAL(){return format(GUILD_ONHOVER_WITHDRAWAL);}
    public static Text GUILD_ONHOVER_RENAME(){return format(GUILD_ONHOVER_RENAME);}
    public static Text GUILD_ONHOVER_DELETE(){return format(GUILD_ONHOVER_DELETE);}
    public static Text GUILD_ONHOVER_LIST_LVL10(String guildName, String ownerName){return format(GUILD_ONHOVER_LIST_LVL10, guildName, ownerName);}
    
    //-------------------------
    // Message PLOT / PARCELLE
    //-------------------------
    
    public static Text BUYING_COST_PLOT(Player player, String var1, String var2){return format(BUYING_COST_PLOT, player, var1, var2);}
    public static Text PROTECT_PLOT_SUCCESS(Player player, String var1){return format(PROTECT_PLOT_SUCCESS, player, var1, "");}
    public static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS(Player player, String var1){return format(BEDROCK2SKY_PROTECT_PLOT_SUCCESS, player, var1, "");}
    public static Text PROTECT_LOADED_PLOT(Player player, String var1){return format(PROTECT_LOADED_PLOT, player, var1, "");}
    public static Text UNDEFINED_PLOT_ANGLES(){return format(UNDEFINED_PLOT_ANGLES);}
    public static Text ALREADY_OWNED_PLOT(){return format(ALREADY_OWNED_PLOT);}
    public static Text EXPAND_NOT_ALLOWED(){return format(EXPAND_NOT_ALLOWED);}
    public static Text NO_PLOT(){return format(NO_PLOT);}
    public static Text PLOT_INFO(Player player, String owner, String allow, String plot){return format(PLOT_INFO, player, "","", owner, allow, plot);}
    public static Text PLOT_LIST(){return format(PLOT_LIST);}
    public static Text TARGET_PLOT_LIST(String target){return format(TARGET_PLOT_LIST,target,"");}
    public static Text PLOT_PROTECTED(){return format(PLOT_PROTECTED);}
    public static Text PLOT_NO_ENTER(){return format(PLOT_NO_ENTER);}
    public static Text PLOT_NO_FLY(){return format(PLOT_NO_FLY);}
    public static Text PLOT_NO_BREAK(){return format(PLOT_NO_BREAK);}
    public static Text PLOT_NO_BUILD(){return format(PLOT_NO_BUILD);}
    public static Text PLOT_NO_FIRE(){return format(PLOT_NO_FIRE);}
    public static Text PLOT_NO_EXIT(){return format(PLOT_NO_EXIT);}
    
    //-------------------------
    // Message ECONOMY
    //-------------------------
    
    public static Text MISSING_BALANCE(){return format(MISSING_BALANCE);}
    public static Text GUILD_MISSING_BALANCE(){return format(GUILD_MISSING_BALANCE);}
    public static Text WITHDRAW_SUCCESS(String amount){return format(WITHDRAW_SUCCESS,amount, "");}
    public static Text DEPOSIT_SUCCESS(String amount){return format(DEPOSIT_SUCCESS,amount, "");}
    public static Text CLICK_TO_CONFIRM(){return format(CLICK_TO_CONFIRM);}
    
    //-------------------------
    // Message TELEPORATION
    //-------------------------
    
    public static Text TP_AT_COORDS(){return format(TP_AT_COORDS);}
    public static Text TP_BACK(Player player){return format(TP_BACK,player);}
    public static Text HOME_ALREADY_EXIST(){return format(HOME_ALREADY_EXIST);}
    public static Text HOME_SET_SUCCESS(Player player, String var1){return format(HOME_SET_SUCCESS,player,var1,"");}
    public static Text HOME_DEL_SUCCESS(Player player, String var1){return format(HOME_DEL_SUCCESS,player,var1,"");}
    public static Text NB_HOME(Player player, String var1, String var2){return format(NB_HOME,player,var1,var2);}
    public static Text NB_ALLOWED_HOME(Player player, String var1){return format(NB_ALLOWED_HOME, player, var1, "");}
    public static Text HOME_NOT_FOUND(){return format(HOME_NOT_FOUND);}
    public static Text HOME_TP_SUCCESS(Player player, String var1){return format(HOME_TP_SUCCESS, player, var1, "");}
    public static Text WARP_ALREADY_EXIST(){return format(WARP_ALREADY_EXIST);}
    public static Text WARP_SET_SUCCESS(Player player, String var1){return format(WARP_SET_SUCCESS,player,var1,"");}
    public static Text WARP_DEL_SUCCESS(Player player, String var1){return format(WARP_DEL_SUCCESS,player,var1,"");}
    
    //-------------------------
    // Message Commande PlayerInfo
    //-------------------------

    public static Text PI_ONHOVER_HOME(){return format(PI_ONHOVER_HOME);}
    public static Text PI_ONHOVER_PLOT(){return format(PI_ONHOVER_PLOT);}
    public static Text PI_ONHOVER_GUILD(String grade){return format(PI_ONHOVER_GUILD,grade, "");}
    public static Text PI_ONHOVER_NOGUILD(){return format(PI_ONHOVER_NOGUILD);}
    public static Text PI_ADMIN_ONLINE_ONHOVER_NAME(String UUID){return format(PI_ADMIN_ONLINE_ONHOVER_NAME,UUID, "");}
    public static Text PI_ADMIN_OFFLINE_ONHOVER_NAME(String UUID){return format(PI_ADMIN_OFFLINE_ONHOVER_NAME,UUID, "");}
    public static Text PI_ADMIN_ONHOVER_TP(){return format(PI_ADMIN_ONHOVER_TP);}
    
    //-------------------------
    // Message WORLD
    //-------------------------
    
    public static Text WORLD_ALREADY_EXIST(){return format(WORLD_ALREADY_EXIST);}
    public static Text WORLD_CREATED(Player player, String var1){return format(WORLD_CREATED,player,var1,"");}
    public static Text TELEPORTED_TO_WORLD(Player player, String var1){return format(TELEPORTED_TO_WORLD,player,var1,"");}
    public static Text OTHER_TELEPORTED_TO_WORLD(Player player, String var1){return format(OTHER_TELEPORTED_TO_WORLD,player,var1,"");}
    public static Text WORLD_PROPERTIES_ERROR(){return format(WORLD_PROPERTIES_ERROR);}
    public static Text WORLD_CREATION_ERROR(){return format(WORLD_CREATION_ERROR);}
    
    //-------------------------
    // Message PORTAL
    //-------------------------
    
    public static Text PROTECT_PORTAL(){return format(PROTECT_PORTAL);}
    
    //-------------------------
    // Message SHOP
    //-------------------------
    
    public static Text SHOP_SALE(){return format(SHOP_SALE);}
    public static Text SHOP_BUY(){return format(SHOP_BUY);}
              
    //-------------------------
    // Message CHEST
    //-------------------------
    
    public static Text CHEST_LOCK(){return format(CHEST_LOCK);}
    
    //-------------------------
    // Message ARMORSTAND
    //-------------------------
    
    public static Text CHEST_ROTATION(){return format(CHEST_ROTATION);}
    public static Text HEAD_ROTATION(){return format(HEAD_ROTATION);}
    public static Text LEFT_ARM_ROTATION(){return format(LEFT_ARM_ROTATION);}
    public static Text RIGHT_ARM_ROTATION(){return format(RIGHT_ARM_ROTATION);}
    public static Text LEFT_LEG_ROTATION(){return format(LEFT_LEG_ROTATION);}
    public static Text RIGHT_LEG_ROTATION(){return format(RIGHT_LEG_ROTATION);}
    public static Text POSITION_X_AS(){return format(POSITION_X_AS);}
    public static Text POSITION_Y_AS(){return format(POSITION_Y_AS);}
    public static Text POSITION_Z_AS(){return format(POSITION_Z_AS);}
    public static Text ROTATION_AS(){return format(ROTATION_AS);}
    public static Text AS_HAS_BASE_PLATE(){return format(AS_HAS_BASE_PLATE);}
    public static Text AS_HAS_GRAVITY(){return format(AS_HAS_GRAVITY);}
    public static Text AS_IS_SMALL(){return format(AS_IS_SMALL);}
    public static Text AS_NAME_VISIBLE(){return format(AS_NAME_VISIBLE);}
    public static Text AS_MARKER(){return format(AS_MARKER);}
    public static Text AS_HAS_ARMS(){return format(AS_HAS_ARMS);}
    public static Text AS_INVISIBLE(){return format(AS_INVISIBLE);}
    
    //-------------------------
    // Message GRAVE
    //-------------------------
    
    public static Text GRAVE_BREAK(Player player){return format(GRAVE_BREAK,player);}
    public static Text GRAVE_RIP1(Player player){return format(GRAVE_RIP1,player);}
    public static Text GRAVE_RIP2(Player player){return format(GRAVE_RIP2,player);}
    public static Text GRAVE_RIP3(Player player){return format(GRAVE_RIP3,player);}
    public static Text GRAVE_RIP4(Player player){return format(GRAVE_RIP4,player);}
    public static Text GRAVE_POSITION(){return format(GRAVE_POSITION);}
    
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
