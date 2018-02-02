package net.teraoctet.actus.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import static org.spongepowered.api.item.inventory.query.QueryOperationTypes.INVENTORY_TYPE;

@ConfigSerializable
public final class AInventory {
    
    Player player;
    String uuid;
    String id_inv;
    @Setting Map<Integer, ItemStack> hotbar = new HashMap<>();
    @Setting Map<Integer, ItemStack> equipment = new HashMap<>();
    @Setting Map<Integer, ItemStack> grid = new HashMap<>();
    @Setting double health = 20.0;
    @Setting int food_level = 20;
    @Setting double saturation = 20.0;
    @Setting int experience_level = 0;
    @Setting int total_experience = 0;
    @Setting ItemStack offHand;
    
    public AInventory() {}
    
    public AInventory(Player player, String id_inv) {
        this.player = player;
        this.uuid = player.getIdentifier();
        this.id_inv = id_inv;
        this.health = player.get(Keys.HEALTH).get();
        this.food_level = player.get(Keys.FOOD_LEVEL).get();
        this.saturation = player.get(Keys.SATURATION).get();
        this.experience_level = player.get(Keys.EXPERIENCE_LEVEL).get();
        this.total_experience = player.get(Keys.TOTAL_EXPERIENCE).get();
        
        PlayerInventory playerInv = player.getInventory().query(INVENTORY_TYPE.of(PlayerInventory.class));
        int i = 0;
        for (Inventory item : playerInv.getHotbar().slots()) {
            Slot slot = (Slot) item;
            Optional<ItemStack> peek = slot.peek();
            if (peek.isPresent()) addHotbar(i, peek.get());
            i++;
        }

        i = 0;
        for (Inventory item : playerInv.getMain().slots()) {
            Slot slot = (Slot) item;
            Optional<ItemStack> peek = slot.peek();
            if (peek.isPresent()) addGrid(i, peek.get());
            i++;
        }

        i = 0;
        for (Inventory item : playerInv.getEquipment().slots()) {
            Slot slot = (Slot) item;
            Optional<ItemStack> peek = slot.peek();
            if (peek.isPresent()) addEquipment(i, peek.get());
            i++;
        }
        
        if(player.getItemInHand(HandTypes.OFF_HAND).isPresent()) this.offHand = player.getItemInHand(HandTypes.OFF_HAND).get();
        
    }
    
    public void set() {
        player.getInventory().clear();
	PlayerInventory inv = player.getInventory().query(INVENTORY_TYPE.of(PlayerInventory.class));
        player.offer(Keys.HEALTH, health);
        player.offer(Keys.FOOD_LEVEL, food_level);
        player.offer(Keys.SATURATION, saturation);
        player.offer(Keys.EXPERIENCE_LEVEL, experience_level);
        player.offer(Keys.TOTAL_EXPERIENCE, total_experience);
                
        int i = 0;
        for (Inventory slot : inv.getHotbar().slots()) {
            if (getHotbar().containsKey(i)) slot.set(this.getHotbar().get(i));
                i++;
        }
                
        i = 0;
        for (Inventory slot : inv.getMain().slots()) {
            if (grid.containsKey(i))slot.set(grid.get(i));
            i++;
        }

        i = 0;
        for (Inventory slot : inv.getEquipment().slots()) {
            if (equipment.containsKey(i))slot.set(equipment.get(i));
            i++;
        }
        
        if(offHand != null)player.setItemInHand(HandTypes.OFF_HAND,offHand);
        
    }
    
    public String getIdInv(){ return id_inv;}
	
    public Map<Integer, ItemStack> getHotbar() {return this.hotbar;}

    public Map<Integer, ItemStack> getGrid() {return this.grid;}

    public Map<Integer, ItemStack> getEquipment() {return this.equipment;}

    public void addHotbar(Integer slot, ItemStack itemStack) {this.hotbar.put(slot, itemStack);}
	
    public void addGrid(Integer slot, ItemStack itemStack) {this.grid.put(slot, itemStack);}
	
    public void addEquipment(Integer slot, ItemStack itemStack) {this.equipment.put(slot, itemStack);}
    
    public void setPlayer(Player player){this.player = player; }
		
}