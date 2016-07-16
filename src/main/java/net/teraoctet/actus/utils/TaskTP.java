package net.teraoctet.actus.utils;

import java.util.concurrent.TimeUnit;
import static net.teraoctet.actus.Actus.Atpa;
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
        this.taskTP = Sponge.getScheduler().createTaskBuilder().execute(() -> {
            Atpa.remove(index);
            player.sendMessage(MESSAGE("&eTemps d'attente dépass\351 ..."));
            this.result = true;
             
        }).delay(30, TimeUnit.SECONDS).submit(plugin);
    }
        
    public boolean getResult(){
        return this.result;
    }

    public void stopTP()
    {
        taskTP.cancel();
    }
}
