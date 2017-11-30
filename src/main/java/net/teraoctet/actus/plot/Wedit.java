package net.teraoctet.actus.plot;

import com.flowpowered.math.vector.Vector3i;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.Capability;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import com.sk89q.worldedit.session.SessionManager;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class Wedit {
    
    private static WorldEdit WEdit = WorldEdit.getInstance();
    /**
     * retourne True si WorldEdit est actif
     * @return Boolean
     */
    public static boolean WEisActive() {
	return Sponge.getPluginManager().getPlugin("worldedit").isPresent();
    }
    
    /**
     * Retourne la region selectionné par le joueur sur WorldEdit
     * @param player
     * @return PlotSelection
     */
    public static Optional<PlotSelection> getWESelection(Player player)
    {
        PlotSelection plotSelect;
        LocalSession localSession = getLocalSession(player);
        com.sk89q.worldedit.world.World w = localSession.getSelectionWorld();
        if (w == null) return Optional.empty();
        RegionSelector regionSelector = localSession.getRegionSelector(w);
        Region sel;
        if (!regionSelector.isDefined()) return Optional.empty();
        try {
            sel = regionSelector.getRegion();
            com.sk89q.worldedit.world.World weWorld = sel.getWorld();
            if (weWorld==null) return Optional.empty();
            Optional<World> world = Sponge.getGame().getServer().getWorld(weWorld.getName());
            if(!world.isPresent())return Optional.empty();
            plotSelect = new PlotSelection(getVestorWeToSpon(sel.getMinimumPoint()),getVestorWeToSpon(sel.getMaximumPoint()),world.get());
        } catch (IncompleteRegionException e) {
            return Optional.empty();
        }
        return Optional.of(plotSelect);
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
    
    private static LocalSession getLocalSession(Player player){
        //if (WEdit == null) WEdit = WorldEdit.getInstance();
        SessionManager sessionManager = WEdit.getSessionManager();
        LocalSession localSession = sessionManager.findByName(player.getName());
        return localSession;
    }
    
    private static Vector3i getVestorWeToSpon(Vector v){
        return new Vector3i(v.getX(), v.getY(), v.getZ());
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
