package net.teraoctet.actus.utils;

import net.teraoctet.actus.plot.Jail;
import net.teraoctet.actus.player.APlayer;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.spongepowered.api.Sponge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import static net.teraoctet.actus.Actus.plugin;
import net.teraoctet.actus.guild.Guild;
import static net.teraoctet.actus.player.PlayerManager.addAPlayer;
import static net.teraoctet.actus.player.PlayerManager.addUUID;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.removeAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.plot.PlotManager;
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.portal.Portal;
import net.teraoctet.actus.ticket.Ticket;
import net.teraoctet.actus.warp.Warp;
import static net.teraoctet.actus.warp.WarpManager.addWarp;
import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.service.sql.SqlService;


public class Data {
	private static SqlService sql;
	public static DataSource datasource;
	public static List<String> queue = new ArrayList<>();
	
        public static DataSource getDataSource(String jdbcUrl) throws SQLException {
            if (sql == null) {
                sql = Sponge.getServiceManager().provide(SqlService.class).get();
            }
            return sql.getDataSource(jdbcUrl);
        }
    
	public static void setup() {
            try {
                if(!Config.MYSQL_USE()) { 
                    datasource = getDataSource("jdbc:h2:./config/actus/actus.db");
                } else {
                        String host = Config.MYSQL_HOST();
                        String port = String.valueOf(Config.MYSQL_PORT());
                        String username = Config.MYSQL_USERNAME();
                        String password = Config.MYSQL_PASSWORD();
                        String database = Config.MYSQL_DATABASE();
                        datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" 
                                + username + "&password=" + password);
                }
                DatabaseMetaData metadata = datasource.getConnection().getMetaData();
                ResultSet resultset = metadata.getTables(null, null, "%", null);
                List<String> tables = new ArrayList<>();		
                while(resultset.next()) { 
                    tables.add(resultset.getString(3));
                }

                if(!tables.contains("JAILS")) {
                        execute("CREATE TABLE JAILS ("
                                + "uuid TEXT, "
                                + "player TEXT, "
                                + "jail TEXT, "
                                + "reason TEXT, "
                                + "time DOUBLE, "
                                + "duration DOUBLE)");
                }

                if(!tables.contains("HOMES")) {
                        execute("CREATE TABLE HOMES ("
                                + "uuid TEXT, "
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x INT, "
                                + "y INT, "
                                + "z INT)");
                }

                if(!tables.contains("APLAYERS")) {
                        execute("CREATE TABLE APLAYERS ("
                                + "uuid TEXT, "
                                + "level INT, "
                                + "name TEXT, "
                                + "godmode TEXT, "
                                + "flymode DOUBLE, "
                                + "mails TEXT, "
                                + "money DOUBLE, "
                                + "lastposition TEXT, "
                                + "lastdeath TEXT, "
                                + "onlinetime DOUBLE, "
                                + "lastonline DOUBLE, "
                                + "jail TEXT, "
                                + "timejail DOUBLE, "
                                + "id_guild INT, "
                                + "guild_rank INT)");
                }
                
                if(!tables.contains("GUILDS")) {
                        execute("CREATE TABLE GUILDS ("
                                + "id_guild INT, "
                                + "name TEXT, "
                                + "world TEXT, "
                                + "X INT, "
                                + "Y INT, "
                                + "Z INT, "
                                + "money DOUBLE, "
                                + "point INT, "
                                + "kill INT, "
                                + "dead INT)");
                }

                if(!tables.contains("TICKETS")) {
                        execute("CREATE TABLE TICKETS ("
                                + "date DOUBLE, "
                                + "uuid TEXT, "
                                + "message TEXT, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "assigned TEXT, "
                                + "priority TEXT, "
                                + "status TEXT)");
                }

                if(!tables.contains("WARPS")) {
                        execute("CREATE TABLE WARPS ("
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "message TEXT)");
                }
                
                if(!tables.contains("TRACE")) {
                        execute("CREATE TABLE TRACE ("
                                + "date DOUBLE, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "uuid TEXT, "
                                + "type TEXT, "
                                + "block TEXT)");
                }
                
                if(!tables.contains("PLOT")) {
                        execute("CREATE TABLE PLOT ("
                                + "plotName TEXT, "
                                + "level INT, "
                                + "world TEXT, "
                                + "X1 INT, "
                                + "Y1 INT, "
                                + "Z1 INT, "
                                + "X2 INT, "
                                + "Y2 INT, "
                                + "Z2 INT, "
                                + "jail BOOLEAN, "
                                + "noEnter BOOLEAN, "
                                + "noFly BOOLEAN, "
                                + "noBuild BOOLEAN, "
                                + "noBreak BOOLEAN, "
                                + "noTeleport BOOLEAN, "
                                + "noInteract BOOLEAN, "
                                + "noFire BOOLEAN, "
                                + "message TEXT, "
                                + "mode INT, "
                                + "noMob BOOLEAN, "
                                + "noTNT BOOLEAN, "
                                + "noCommand BOOLEAN, "
                                + "uuidOwner TEXT, "
                                + "uuidAllowed TEXT, "
                                + "id_guild INT, "
                                + "spawnGrave BOOLEAN,"
                                + "noPVPplayer BOOLEAN,"
                                + "noPVPmonster BOOLEAN,"
                                + "noProjectile BOOLEAN,"
                                + "noLiquidFlow BOOLEAN,"
                                + "autoForest BOOLEAN)");
                }
                
                if(!tables.contains("PLSALE")) {
                        execute("CREATE TABLE PLSALE ("
                                + "plotName TEXT, "
                                + "location TEXT)");
                }
                
                if(!tables.contains("PORTAL")) {
                        execute("CREATE TABLE PORTAL ("
                                + "portalname TEXT, "
                                + "level INT, "
                                + "world TEXT, "
                                + "x1 INT, "
                                + "y1 INT, "
                                + "z1 INT, "
                                + "x2 INT, "
                                + "y2 INT, "
                                + "z2 INT, "
                                + "toworld TEXT, "
                                + "tox INT, "
                                + "toy INT, "
                                + "toz INT, "
                                + "message TEXT, "
                                + "cmd TEXT)");
                }
                
                if(!tables.contains("ITEMSHOP")) {
                        execute("CREATE TABLE ITEMSHOP ("
                                + "portalname TEXT, "
                                + "level INT, "
                                + "world TEXT, "
                                + "x1 INT, "
                                + "y1 INT, "
                                + "z1 INT, "
                                + "x2 INT, "
                                + "y2 INT, "
                                + "z2 INT, "
                                + "toworld TEXT, "
                                + "tox INT, "
                                + "toy INT, "
                                + "toz INT, "
                                + "message TEXT)");
                }
                                
            } catch (SQLException e) {}
	}
	
        @SuppressWarnings("ConvertToTryWithResources")
	public static void load() {
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM JAILS");
                while(rs.next()) {
                    Jail jail = new Jail(
                        rs.getString("uuid"), 
                        rs.getString("player"),
                        rs.getString("jail"),
                        rs.getString("reason"), 
                        rs.getInt("time"), 
                        rs.getInt("duration"));
                    Data.addJail(jail.getUUID(), jail);
                }
                
                rs = s.executeQuery("SELECT * FROM APLAYERS");
                while(rs.next()) {
                    APlayer player = new APlayer(
                        rs.getString("uuid"), 
                        rs.getInt("level"),    
                        rs.getString("name"), 
                        rs.getString("godmode"), 
                        rs.getDouble("flymode"), 
                        rs.getString("mails"), 
                        rs.getDouble("money"),
                        rs.getString("lastposition"), 
                        rs.getString("lastdeath"),
                        rs.getDouble("onlinetime"), 
                        rs.getDouble("lastonline"),
                        rs.getString("jail"),
                        rs.getDouble("timejail"),
                        rs.getInt("id_guild"),
                        rs.getInt("guild_rank"));
                    addAPlayer(player.getUUID(), player);
                    addUUID(player.getName(), player.getUUID());
                }   
                
                rs = s.executeQuery("SELECT * FROM GUILDS");
                while(rs.next()) {
                    Guild guild = new Guild(
                        rs.getInt("id_guild"),
                        rs.getString("name"), 
                        rs.getString("world"),
                        rs.getInt("X"),
                        rs.getInt("Y"),
                        rs.getInt("Z"),
                        rs.getDouble("money"),
                        rs.getInt("point"),
                        rs.getInt("kill"),
                        rs.getInt("dead"));
                    addGuild(guild.getID(), guild);
                }   
                
                rs = s.executeQuery("SELECT * FROM HOMES");
                while(rs.next()) {
                    Home home = new Home(
                        rs.getString("uuid"), 
                        rs.getString("name"), 
                        rs.getString("world"), 
                        rs.getInt("x"),
                        rs.getInt("y"),  
                        rs.getInt("z"));
                        APlayer aplayer = getAPlayer(home.getUUID());
                        aplayer.setHome(home.getName(), home);
                        removeAPlayer(home.getUUID());
                        addAPlayer(home.getUUID(), aplayer);
                }
               
                rs = s.executeQuery("SELECT * FROM PLOT ORDER BY level desc");
                while(rs.next()) {
                    Plot plot = new Plot(
                        rs.getString("plotName"),
                        rs.getInt("level"),
                        rs.getString("world"),
                        rs.getInt("X1"), 
                        rs.getInt("Y1"),
                        rs.getInt("Z1"),
                        rs.getInt("X2"),
                        rs.getInt("Y2"),
                        rs.getInt("Z2"),
                        rs.getBoolean("jail"),
                        rs.getBoolean("noEnter"), 
                        rs.getBoolean("noFly"),
                        rs.getBoolean("noBuild"),
                        rs.getBoolean("noBreak"),
                        rs.getBoolean("noTeleport"),
                        rs.getBoolean("noInteract"), 
                        rs.getBoolean("noFire"),
                        rs.getString("message"),
                        rs.getInt("mode"),
                        rs.getBoolean("noMob"),
                        rs.getBoolean("noTNT"),
                        rs.getBoolean("noCommand"),
                        rs.getString("uuidOwner"), 
                        rs.getString("uuidAllowed"),
                        rs.getInt("id_guild"),   
                        rs.getBoolean("spawnGrave"),
                        rs.getBoolean("noPVPplayer"),
                        rs.getBoolean("noPVPmonster"),
                        rs.getBoolean("noProjectile"),
                        rs.getBoolean("noLiquidFlow"),
                        rs.getBoolean("autoForest"));
                    if(plot.getJail()){PJAILS.add(plot);}
                    else{PLOTS.add(plot);}   
                }
                
                rs = s.executeQuery("SELECT * FROM PORTAL");
                while(rs.next()) {
                    Portal portal = new Portal(
                        rs.getString("portalname"),
                        rs.getInt("level"),
                        rs.getString("world"),
                        rs.getInt("x1"), 
                        rs.getInt("y1"),
                        rs.getInt("z1"),
                        rs.getInt("x2"),
                        rs.getInt("y2"),
                        rs.getInt("z2"),
                        rs.getString("toworld"),
                        rs.getInt("tox"), 
                        rs.getInt("toy"),
                        rs.getInt("toz"),
                        rs.getString("message"),
                        rs.getString("cmd"));
                    PORTALS.add(portal);  
                }
                
                rs = s.executeQuery("SELECT * FROM TICKETS");
                while(rs.next()) {
                    Ticket ticket = new Ticket(
                        rs.getInt("date"), 
                        rs.getString("uuid"), 
                        rs.getString("message"), 
                        rs.getString("world"), 
                        rs.getDouble("x"), 
                        rs.getDouble("y"), 
                        rs.getDouble("z"),  
                        rs.getString("assigned"), 
                        rs.getString("priority"), 
                        rs.getString("status"));
                    //addTicket(ticket.getID(), ticket);
                }
                
                rs = s.executeQuery("SELECT * FROM WARPS");
                while(rs.next()) {
                    Warp warp = new Warp(
                        rs.getString("name"),
                        rs.getString("world"),
                        rs.getDouble("x"), 
                        rs.getDouble("y"), 
                        rs.getDouble("z"), 
                        rs.getString("message"));
                    addWarp(warp.getName(), warp);
                }
                s.close();
                c.close();
            } catch (SQLException e) {
                plugin.getLogger().error(e.getMessage());
            }
	}
	
	public static void execute(String execute) {	
            try {
                try (Connection connection = datasource.getConnection(); Statement statement = connection.createStatement()) {
                    statement.execute(execute);
                }
            } catch (SQLException e) {}
	}
	
	public static void execute(List<String> execute) {	
            try {
                try (Connection connection = datasource.getConnection(); Statement statement = connection.createStatement()) {
                    for(String e : execute) statement.execute(e);
                }
            } catch (SQLException e) {}
	}
	
	public static void commit() {
		if(queue.isEmpty()) return;
		execute(queue);
		queue.clear();
	}
	
	public static void queue(String queue) { Data.queue.add(queue); }
	        
	private static final HashMap<String, Jail> JAILS = new HashMap<>();
	public static void addJail(String uuid, Jail jail) { if(!JAILS.containsKey(uuid)) JAILS.put(uuid, jail); }
	public static void removeJail(String uuid) { if(JAILS.containsKey(uuid)) JAILS.remove(uuid); }
	public static Jail getJail(String uuid) { return JAILS.containsKey(uuid) ? JAILS.get(uuid) : null; }
	public static HashMap<String, Jail> getJails() { return JAILS; }

        private static final HashMap<Integer, Guild> GUILDS = new HashMap<>();
        @SuppressWarnings("element-type-mismatch")
	public static void addGuild(Integer ID, Guild guild) { if(!GUILDS.containsKey(GUILDS)) GUILDS.put(ID, guild); }
	public static void removeGuild(Integer id_guild) { if(GUILDS.containsKey(id_guild)) GUILDS.remove(id_guild); }
	public static Guild getGuild(Integer id_guild) { return GUILDS.containsKey(id_guild) ? GUILDS.get(id_guild) : null; }
        public static HashMap<Integer, Guild> getFactions() { return GUILDS; }
        
        public static HashMap<Player, PlotSelection> plotsel = new HashMap();
        public static final ArrayList<Plot> PLOTS = new ArrayList<>();
        public static final ArrayList<Plot> PJAILS = new ArrayList<>();
        public static void addPlot(Plot plot) { if(!PLOTS.contains(plot)) PLOTS.add(plot); }
        public static void addJail(Plot plot) { if(!PJAILS.contains(plot)) PJAILS.add(plot); }
        public static void removePlot(Plot plot) { if(PLOTS.contains(plot)) PLOTS.remove(plot); }
        public static void removeJail(Plot plot) { if(PJAILS.contains(plot)) PJAILS.remove(plot); }

        public static final ArrayList<Portal> PORTALS = new ArrayList<>();
        public static void addPortal(Portal portal) { if(!PORTALS.contains(portal)) PORTALS.add(portal); } 
        public static void removePortal(Portal portal) { if(PORTALS.contains(portal)) PORTALS.remove(portal); } 
	
	private static HashMap<Integer, Ticket> TICKETS = new HashMap<>();
	public static void addTicket(int id, Ticket ticket) { if(!TICKETS.containsKey(id)) TICKETS.put(id, ticket); }
	public static void removeTicket(int id) { if(TICKETS.containsKey(id)) TICKETS.remove(id); }
	public static Ticket getTicket(int id) { return TICKETS.containsKey(id) ? TICKETS.get(id) : null; }
	public static HashMap<Integer, Ticket> getTickets() { return TICKETS; }
	public static void clearTickets() { TICKETS.clear(); }
}

