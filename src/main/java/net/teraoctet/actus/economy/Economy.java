package net.teraoctet.actus.economy;

import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import net.teraoctet.actus.player.APlayer;
import org.spongepowered.api.entity.living.player.Player;

public class Economy {
    public void Credit(Player player, double amount){
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        double solde = aplayer.getMoney() + amount;
        aplayer.setMoney(solde);
        aplayer.update();
    }
    
    public void Debit(Player player, double amount){
        APlayer aplayer = getAPlayer(player.getUniqueId().toString());
        double solde = aplayer.getMoney() - amount;
        aplayer.setMoney(solde);
        aplayer.update();
    }    
}
