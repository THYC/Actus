package net.teraoctet.actus.troc;

import org.spongepowered.api.entity.living.player.Player;

public class ItemTransact {
    
    private final Player player;
    private final int slot;
    private int qte;
    
    public ItemTransact(Player player, int slot, int qte){
        this.player = player;
        this.slot = slot;
        this.qte = qte;
    }
    
    public Player getPlayer(){return player;}
    public int getSlot(){return slot;}
    public int getQte(){return qte;}
    
    public void setQte(int qte){this.qte = qte;}

}
