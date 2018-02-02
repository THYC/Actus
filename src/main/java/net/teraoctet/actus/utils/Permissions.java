package net.teraoctet.actus.utils;

import java.util.Collection;
import java.util.Optional;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.guild.Guild;
import net.teraoctet.actus.player.APlayer;
import net.teraoctet.actus.player.PlayerManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;

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
        Collection<Subject> subjectCol = player.getContainingCollection().getLoadedSubjects();
        for (Subject subject : subjectCol) {
            if (subject.getOption("prefix").isPresent())return subject.getOption("prefix").get();
        }
        return "";
    }
    
    public static String getSuffix(Player player) {
    	Optional<Subject> subject = player.getContainingCollection().getSubject(player.getIdentifier());
	if(subject.isPresent()){
            
            return subject.get().getOption("suffix").orElse("");
		}
		return "";
}
	       
    public static String getSuffix2(Player player) {
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
