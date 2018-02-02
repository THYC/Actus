package net.teraoctet.actus.world;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static net.teraoctet.actus.Actus.plugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import static org.spongepowered.api.block.BlockTypes.TNT;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.world.BlockChangeFlags;

public class Restore{
    
    private final List<Transaction<BlockSnapshot>> bs;
    private Integer i = 0;
    private Task task = null;
    
    public Restore(final List<Transaction<BlockSnapshot>> bs) {
        this.bs = bs;
    }
    
    public void run(){
        Scheduler scheduler = Sponge.getScheduler();
        Task.Builder taskBuilder = scheduler.createTaskBuilder();
            
        task = taskBuilder
                .execute(() -> {
                    try { 
                        if(!bs.get(i).getOriginal().getState().getType().equals(TNT)){
                            bs.get(i).getOriginal().restore(true, BlockChangeFlags.ALL);
                        }
                    } catch(Exception e) {}
                    i++;
                    if(i==bs.size()){ task.cancel();}
        })
                .delay(100, TimeUnit.MILLISECONDS)
                .interval(100, TimeUnit.MILLISECONDS)
                .name("Restore")
                .submit(plugin);

    }
}
