package net.teraoctet.actus.utils;

import java.util.UUID;
import static net.teraoctet.actus.Actus.sm;
import org.spongepowered.api.entity.living.player.Player;

public class TPAH {
    private final String fromUUID;
    private final String toUUID;
    private final String Type;
    
    public TPAH(Player fromPlayer, Player toPlayer, String Type){
        this.fromUUID = fromPlayer.getIdentifier();
        this.toUUID = toPlayer.getIdentifier();
        this.Type = Type;
    }
    
    public Player getFromPlayer(){
        return sm.getPlayer(UUID.fromString(fromUUID)).get();
    }
    
    public Player getToPlayer(){
        return sm.getPlayer(UUID.fromString(toUUID)).get();
    }
    
    public String getType(){
        return Type;
    }
}
