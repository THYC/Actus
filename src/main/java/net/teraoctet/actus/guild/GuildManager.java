package net.teraoctet.actus.guild;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.teraoctet.actus.player.APlayer;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.utils.Data.getGuild;
import static net.teraoctet.actus.utils.Data.getAPlayer;
import static net.teraoctet.actus.utils.Data.getPlayers;
import static net.teraoctet.actus.utils.Data.removeGuild;
import org.spongepowered.api.entity.living.player.Player;

public class GuildManager {
    
    public GuildManager(){}
    
    /**
     * Si TRUE le joueur est owner/chef de guild
     * @param player joueur
     * @return Boolean
     */
    public static Boolean isOwner(Player player) {
        APlayer aplayer = getAPlayer(player.getIdentifier());
        return aplayer.getFactionRank() == 1;
    }
    
    /**
     * Si TRUE le joueur est owner/chef de guild
     * @param aplayer Object APlayer
     * @return Boolean
     */
    public static Boolean isOwner(APlayer aplayer) { return aplayer.getFactionRank() == 1; }
    
    /**
     * Si TRUE le joueur est membre d'une guild
     * @param aplayer Object APlayer
     * @return Boolean
     */
    public static Boolean hasAnyGuild(APlayer aplayer) { return aplayer.getID_guild() != 0; }
    
    /**
     * Si TRUE le joueur est membre d'une guild
     * @param player joueur 
     * @return Boolean
     */
    public static Boolean hasAnyGuild(Player player) {
        APlayer aplayer = getAPlayer(player.getIdentifier());
        return aplayer.getFactionRank() == 1;
    }
    
    /**
     * Calcul une nouvelle clé unique pour Guild
     * @return Clé type Int
     */
    public Integer newKey(){
        int key = 1;
        
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_guild() >= key){
               key = getPlayers().get(uuid).getID_guild() + 1;
            }
        }
        return key;
    }
    
    /**
     * Expulse tous les joueurs d'une guild associé à son ID
     * et supprime la guild
     * @param id_guild id de la guild a supprimer
     */
    /*public void removeFaction(int id_guild){
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_guild() == id_guild){
                getPlayers().get(uuid).setID_guild(0);
                getPlayers().get(uuid).setFactionRank(0);
                getPlayers().get(uuid).update();
                Guild gguild = getGuild(id_guild);
                gguild.delete();
                removeGuild(id_guild);
                commit();
            }
        }
        Data.commit();
    }*/
    
    public void removeGuild(int id_guild){
        for(Map.Entry<String,APlayer> p : getPlayers().entrySet()){
            if(p.getValue().getID_guild() == id_guild){
                p.getValue().setFactionRank(0);
                p.getValue().setID_guild(0);
                p.getValue().update();
            }
        }
        Guild gguild = getGuild(id_guild);
        gguild.delete();
        removeGuild(id_guild);
        commit();
    }
    
    /**
     * Retourne la liste de joueur d'une guild associé à son ID
     * @param id_guild id de la guild
     * @return List
     */
    public List<String> getGuildPlayers(int id_guild){
        List<String> listPlayer =  new ArrayList<>() ;
        getPlayers().entrySet().stream().filter((p) -> (p.getValue().getID_guild() == id_guild)).forEach((p) -> {
            listPlayer.add(p.getValue().getName());
        });
        return listPlayer;
    }
    
    /**
     * Retourne la liste de joueur d'une guild associé à son ID et son Rank
     * @param id_guild id de la guild
     * @param rank filtre le rank a retourner
     * @return List
     */
    public List<String> getGuildPlayers(int id_guild, int rank){
        List<String> listPlayer =  new ArrayList<>() ;
        getPlayers().entrySet().stream().filter((p) -> (p.getValue().getID_guild() == id_guild && p.getValue().getFactionRank() == rank)).forEach((p) -> {
            listPlayer.add(p.getValue().getName());
        });
        return listPlayer;
    }
    
    /**
     * Retourne le owner d'une guild
     * @param id_guild id de la guild
     * @return Objet APlayer
     */
    public APlayer getOwner(int id_guild){
        for(Map.Entry<String,APlayer> p : getPlayers().entrySet()){
            if(p.getValue().getID_guild() == id_guild && p.getValue().getFactionRank() == 1){
                return p.getValue();
            }
        }
        return null;
    }
    
    /**
     * Retourne le nom du grade correspondant à son ID
     * @param rank rank a retourner
     * @return String
     */
    public String rankIDtoString(int rank){
        String grade;
        switch(rank) {
            case 1:
                grade = "Chef";
                break;
            case 2:
                grade = "Sous-Chef";
                break;
            case 3:
                grade = "Officier";
                break;
            case 4:
                grade = "Membre";
                break;
            case 5:
                grade = "Recrue";
                break;
            default:
                grade = "ERREUR";
                break;
        }
        return grade;
    }
}
