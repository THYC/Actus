package net.teraoctet.actus.utils;

import java.util.Collection;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;

public class Permissions {
    
    public Permissions(){ }
    
    public Permissions (Player player){
        //player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "my.perm", Tristate.TRUE);
        
    }	
        
    public static String getPrefix(Player player) {
        Collection<Subject> subjectCol = player.getContainingCollection().getLoadedSubjects();
        for(Subject subject : subjectCol){
            if (subject.getOption("prefix").isPresent()) {
                return subject.getOption("prefix").get();
            }
        }
        return "";
        
        //Subject subject = player.getContainingCollection().getDefaults();
        //return subject.getOption("prefix").orElse("");
    }
	       
    public static String getSuffix(Player player) {
        Collection<Subject> subjectCol = player.getContainingCollection().getLoadedSubjects();
        for(Subject subject : subjectCol){
            if (subject.getOption("suffix").isPresent()) {
                return subject.getOption("suffix").get();
            }
        }
        return "";
        
        //Subject subject = player.getContainingCollection().getDefaults();
        //return subject.getOption("suffix").orElse("");
    }

}
