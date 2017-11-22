package net.teraoctet.actus.utils;

import java.util.Collection;
import java.util.Optional;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;

public class Permissions {
    
    private LuckPermsApi luckPermsApi;
    
    public Permissions(){
        Optional<LuckPermsApi> luckPermsApiOptional = LuckPerms.getApiSafe();
        if(luckPermsApiOptional.isPresent()) {
            luckPermsApi = luckPermsApiOptional.get();
        }
    }
    
    public Permissions (Player player){
        //player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "my.perm", Tristate.TRUE);
        
    }	
        
    public static String getPrefix(Player player) {
        //Subject subject = player.getContainingCollection().getDefaults();
        //player.getSubjectData().getPermission(SubjectData.GLOBAL_CONTEXT, pw, Tristate.FALSE);
        
        Collection<Subject> subjectCol = player.getContainingCollection().getLoadedSubjects();
        for(Subject subject : subjectCol){
         if (subject.getOption("prefix").isPresent()) {
             return subject.getOption("prefix").get();
            }
         }
        return "";
         
        //return player.getSubjectData().getOptions(SubjectData.GLOBAL_CONTEXT).get("prefix");
        //return subject.getOption("prefix").orElse("");
    }
	       
    public static String getSuffix(Player player) {
        Subject subject = player.getContainingCollection().getDefaults();
        return subject.getOption("suffix").orElse("");
    }

}
