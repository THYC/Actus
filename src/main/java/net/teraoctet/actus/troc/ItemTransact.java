package net.teraoctet.actus.troc;

import java.util.UUID;
import static net.teraoctet.actus.Actus.sm;
import org.spongepowered.api.entity.living.player.Player;

public class ItemTransact {
    
    private final String playerUUID;
    private final int slot;
    private int qte;
    
    public ItemTransact(Player player, int slot, int qte){
        this.playerUUID = player.getIdentifier();
        this.slot = slot;
        this.qte = qte;
    }
    
    public ItemTransact(String playerUUID, int slot, int qte){
        this.playerUUID = playerUUID;
        this.slot = slot;
        this.qte = qte;
    }
    
    public Player getPlayer(){return sm.getPlayer(UUID.fromString(playerUUID)).get() ;}
    public int getSlot(){return slot;}
    public int getQte(){return qte;}
    
    public void setQte(int qte){this.qte = qte;}

}
