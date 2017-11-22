package net.teraoctet.actus.troc;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

@ConfigSerializable
public class Troc {
    
    private @Setting String loc;
    private @Setting int slot;
    private @Setting String itemName = "";
    private @Setting EnumTransactType transactType;
    private @Setting int qteMax;
    private @Setting double price;
    private @Setting ItemStackSnapshot item;
    private @Setting String playerName;
    private @Setting String playerUUID;
    private @Setting int id_guild; 
    
    public Troc(){}
    
    public Troc(EnumTransactType transactType, int qteMax, double price, 
            ItemStackSnapshot item, String playerName, String playerUUID, int id_guild){
        this.loc = "";
        this.slot = 0;
        this.transactType = transactType;
        this.qteMax = qteMax;
        this.price = price;
        this.item = item;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.id_guild = id_guild;
        
    }
    
    public Troc(String loc, int slot, EnumTransactType transactType, int qteMax, double price, 
            ItemStackSnapshot item, String playerName, String playerUUID, int id_guild){
        this.loc = loc;
        this.slot = slot;
        this.transactType = transactType;
        this.qteMax = qteMax;
        this.price = price;
        this.item = item;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.id_guild = id_guild;
        
    }
    
    public String getLoc(){ return loc;}
    public int getSlot(){ return slot;}
    public String getItemName(){ return itemName;}
    public EnumTransactType getTransactType(){ return transactType;}
    public int getQteMax(){ return qteMax;}
    public double getPrice(){ return price;}
    public ItemStackSnapshot getItem(){ return item;}
    public String getPlayerName(){ return playerName;}
    public String getPlayerUUID(){ return playerUUID;}
    public int getId_guild(){ return id_guild;}
    
    public void setLoc(String loc){ this.loc = loc;}
    public void setSlot(int slot){ this.slot = slot;}
    public void setItemName(String itemName){ this.itemName = itemName;}
    public void setTransactType(EnumTransactType transactType){ this.transactType = transactType;}
    public void setQteMax(int qteMax){ this.qteMax = qteMax;}
    public void setPrice(double price){ this.price = price;}
    public void setItem(ItemStackSnapshot item){ this.item = item;}
}

