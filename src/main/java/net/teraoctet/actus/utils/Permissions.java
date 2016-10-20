package net.teraoctet.actus.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

public class Permissions {
    
    public Permissions (Player player){
        player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "my.perm", Tristate.TRUE);
    }	
    
    public static String getPrefix(Player player) {
        Subject subject = player.getContainingCollection().get(player.getIdentifier());
        return subject.getOption("prefix").orElse("");
    }
	       
    public static String getSuffix(Player player) {
        Subject subject = player.getContainingCollection().get(player.getUniqueId().toString());
        return subject.getOption("suffix").orElse("");
    }

}
