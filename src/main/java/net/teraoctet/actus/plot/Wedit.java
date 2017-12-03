package net.teraoctet.actus.plot;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.Capability;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import com.sk89q.worldedit.session.SessionManager;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class Wedit {
    
    private static WorldEdit WEdit = WorldEdit.getInstance();
    /**
     * retourne True si WorldEdit est actif
     * @return Boolean
     */
    public static boolean WEisActive() {
	return Sponge.getPluginManager().getPlugin("worldedit").isPresent();
    }
        
    public static void setSelection(Player player, PlotSelection plotselection){
        LocalSession curLocSession = getPlayerLocalSession(player);
        com.sk89q.worldedit.world.World w = getWorldByName(plotselection.getWorld().get().getName()).get();
        if(plotselection.minPos.isPresent() && !plotselection.maxPos.isPresent()){
            plotselection.setMaxPos(plotselection.minPos.get());
        }
        if(!plotselection.minPos.isPresent() && plotselection.maxPos.isPresent()){
            plotselection.setMinPos(plotselection.maxPos.get());
        }
        if(plotselection.minPos.isPresent() && plotselection.maxPos.isPresent()){
            Vector minpos = getVestorSponToWe(plotselection.minPos.get());
            Vector maxpos = getVestorSponToWe(plotselection.maxPos.get());
                
            CuboidRegionSelector regionSelector = new CuboidRegionSelector(w, minpos, maxpos);

            curLocSession.setRegionSelector(w, regionSelector);
            try {
                curLocSession.getRegionSelector(w).getIncompleteRegion().shift(new Vector(0, 0, 0));
            } catch (RegionOperationException e) {
                return;
            }
            curLocSession.getRegionSelector(w).learnChanges();
            Sponge.getGame().getCommandManager().process(player, "we cui");
        }

    }
    
    private static Optional<com.sk89q.worldedit.world.World> getWorldByName(String name){
        for (com.sk89q.worldedit.world.World w : WEdit.getPlatformManager().queryCapability(Capability.WORLD_EDITING).getWorlds()) {
            if (w.getName().equals(name)) return Optional.of(w);
        }
        return Optional.empty();
    }
    
    private static Vector getVestorSponToWe(Vector3i v){
        return new Vector(v.getX(), v.getY(), v.getZ());
    }
    
    private static LocalSession getPlayerLocalSession(Player player){
        if (!WEisActive()) WEdit = WorldEdit.getInstance();
        SessionManager WESessionMan = WEdit.getSessionManager();
        LocalSession result = WESessionMan.findByName(player.getName());
        return result;
    }
}
