package net.teraoctet.actus.utils;

import java.util.concurrent.TimeUnit;
import static net.teraoctet.actus.Actus.ATPA;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.utils.MessageManager.MESSAGE;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

public class TaskTP {
    
    Task taskTP = null;
    private final Player player;
    private final int index;
    private boolean result;
    
    public TaskTP(Player player, int index){
        this.player = player;
        this.index = index;
    }
    
    public void run(){
        this.taskTP = Sponge.getScheduler().createTaskBuilder()
                .delay(30, TimeUnit.SECONDS)
                .execute(() -> {
                    ATPA.remove(index);
                    player.sendMessage(MESSAGE("&eTemps d'attente dépass\351 ..."));
                    this.result = true;
        }).submit(plugin);
    }
        
    public boolean getResult(){
        return this.result;
    }

    public void stopTP(){
        taskTP.cancel();
    }
}
