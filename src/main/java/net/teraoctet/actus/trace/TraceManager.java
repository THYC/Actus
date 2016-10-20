
package net.teraoctet.actus.trace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static net.teraoctet.actus.Actus.plugin;
import static net.teraoctet.actus.Actus.serverManager;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.utils.Data.commit;
import static net.teraoctet.actus.utils.Data.datasource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class TraceManager {
    public String load(Location<World> loc) {
        String trace = "\n\n&7--------------------------------------\n";
        try {
            Connection c = datasource.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM trace WHERE world = '" + loc.getExtent().getName()
                    + "' AND x = " + loc.getX()
                    + " AND y = " + loc.getY()
                    + " AND z = " + loc.getZ());
            
            if (rs.isBeforeFirst()){
                while (rs.next()){
                    trace = trace + 
                            "&bDate : &e" + serverManager.longToDateString(rs.getDouble("date")) + "\n" +
                            "&bJoueur : &e" + getAPlayer(rs.getString("uuid")).getName() + " &7[" + rs.getString("uuid") + "]\n" +
                            "&bAction : &e" + rs.getString("type") + "\n" +
                            "&bBlock : &e" + rs.getString("block") + "\n" +
                            "&7--------------------------------------\n";
                }
            }else{
                trace = "&eAucun enregistrement sur cette position";
            }
            
            s.close();
            c.close();
            rs.close();
                
        } catch (SQLException e) {
            plugin.getLogger().error(e.getSQLState());
        }
        return trace;
    } 
    
    public void refresh(){
        commit();
    }
}
