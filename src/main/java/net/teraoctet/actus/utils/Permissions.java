package net.teraoctet.actus.utils;

import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.player.PlayerManager;
import static net.teraoctet.actus.utils.Config.LEVEL_ADMIN;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.SubjectData;

public class Permissions {
    
    public Permissions(){ }
    
    public Permissions (Player player){
        //player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "my.perm", Tristate.TRUE);
        
    }	
        
    public static String getPrefix(Player player) {
        APlayer aplayer = PlayerManager.getAPlayer(player.getIdentifier());
        if(aplayer.getID_guild() != 0){
            Guild guild = Data.getGuild(aplayer.getID_guild());
            return guild.getName();
        }
        if(player.getOption(SubjectData.GLOBAL_CONTEXT, "prefix").isPresent()){
            return player.getOption(SubjectData.GLOBAL_CONTEXT, "prefix").get();
        }
        return "";
    }
	       
    public static String getSuffix(Player player) {
        APlayer aplayer = PlayerManager.getAPlayer(player.getIdentifier());
        if(aplayer.getLevel() == LEVEL_ADMIN()){
            return " &l&7[L" + LEVEL_ADMIN() + "]";
        }
        
        if(player.getOption(SubjectData.GLOBAL_CONTEXT, "suffix").isPresent()){
            return player.getOption(SubjectData.GLOBAL_CONTEXT, "suffix").get();
        }
        return "";
    }

}
