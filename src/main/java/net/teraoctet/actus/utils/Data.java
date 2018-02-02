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
import net.teraoctet.actus.plot.PlotSelection;
import net.teraoctet.actus.portal.Portal;
import net.teraoctet.actus.ticket.Ticket;
import net.teraoctet.actus.warp.Warp;
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
                    plugin.getLogger().info("connection jdbc:h2 ...");
                    datasource = getDataSource("jdbc:h2:./config/actus/actus.db");
                } else {
                    plugin.getLogger().info("connection jdbc:mysql ...");
                    String host = Config.MYSQL_HOST();
                    String port = String.valueOf(Config.MYSQL_PORT());
                    String username = Config.MYSQL_USERNAME();
                    String password = Config.MYSQL_PASSWORD();
                    String database = Config.MYSQL_DATABASE();
                    datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" 
                            + username + "&password=" + password);
                }
                DatabaseMetaData metadata = datasource.getConnection().getMetaData();
                ResultSet resultset = metadata.getTables(null, null, "%", null);
                List<String> tables = new ArrayList<>();		
                while(resultset.next()) { 
                    tables.add(resultset.getString(3));
                }
                plugin.getLogger().info("connection ok ...");
                if(!tables.contains("JAILS")) {
                    plugin.getLogger().info("creation table JAILS ...");
                    execute("CREATE TABLE JAILS ("
                            + "uuid VARCHAR(50) NOT NULL, "
                            + "player VARCHAR(50) NOT NULL, "
                            + "jail VARCHAR(50) NOT NULL, "
                            + "reason VARCHAR(255), "
                            + "time DOUBLE NOT NULL, "
                            + "duration DOUBLE NOT NULL, "
                            + "UNIQUE (uuid))");
                }

                if(!tables.contains("HOMES")) {
                    plugin.getLogger().info("creation table HOMES ...");
                    execute("CREATE TABLE HOMES ("
                            + "uuid VARCHAR(50) NOT NULL, "
                            + "name VARCHAR(50) NOT NULL, "
                            + "world VARCHAR(50) NOT NULL, "
                            + "x INT, "
                            + "y INT, "
                            + "z INT, "
                            + "UNIQUE (name))");
                }

                if(!tables.contains("APLAYERS")) {
                    plugin.getLogger().info("creation table APLAYERS ...");
                    execute("CREATE TABLE APLAYERS ("
                            + "uuid VARCHAR(50) NOT NULL, "
                            + "level INT, "
                            + "name VARCHAR(50), "
                            + "godmode VARCHAR(50), "
                            + "flymode DOUBLE, "
                            + "mails VARCHAR(50), "
                            + "money DOUBLE, "
                            + "lastposition VARCHAR(50), "
                            + "lastdeath VARCHAR(50), "
                            + "onlinetime DOUBLE, "
                            + "lastonline DOUBLE, "
                            + "jail VARCHAR(50), "
                            + "timejail DOUBLE, "
                            + "id_guild INT, "
                            + "guild_rank INT, "
                            + "KEY (uuid), UNIQUE (uuid))");
                }
                
                if(!tables.contains("GUILDS")) {
                    plugin.getLogger().info("creation table GUILDS ...");
                    execute("CREATE TABLE GUILDS ("
                            + "id_guild INT AUTO_INCREMENT, "
                            + "name VARCHAR(50), "
                            + "world VARCHAR(50), "
                            + "X INT, "
                            + "Y INT, "
                            + "Z INT, "
                            + "money DOUBLE, "
                            + "point INT, "
                            + "kills INT, "
                            + "dead INT, "
                            + "PRIMARY KEY (id_guild))");
                }

                if(!tables.contains("TICKETS")) {
                    plugin.getLogger().info("creation table TICKETS ...");
                    execute("CREATE TABLE TICKETS ("
                            + "date DOUBLE, "
                            + "uuid VARCHAR(50), "
                            + "message VARCHAR(255), "
                            + "world VARCHAR(50), "
                            + "x DOUBLE, "
                            + "y DOUBLE, "
                            + "z DOUBLE, "
                            + "assigned VARCHAR(50), "
                            + "priority VARCHAR(50), "
                            + "status VARCHAR(50), "
                            + "UNIQUE (date))");
                }

                if(!tables.contains("WARPS")) {
                    plugin.getLogger().info("creation table WARPS ...");
                    execute("CREATE TABLE WARPS ("
                            + "name VARCHAR(50), "
                            + "world VARCHAR(50), "
                            + "x DOUBLE, "
                            + "y DOUBLE, "
                            + "z DOUBLE, "
                            + "message VARCHAR(255), "
                            + "UNIQUE (name))");
                }
                
                if(!tables.contains("TRACE")) {
                    plugin.getLogger().info("creation table TRACE ...");
                    execute("CREATE TABLE TRACE ("
                            + "date DOUBLE, "
                            + "world VARCHAR(50), "
                            + "x DOUBLE, "
                            + "y DOUBLE, "
                            + "z DOUBLE, "
                            + "uuid VARCHAR(50), "
                            + "type VARCHAR(50), "
                            + "block VARCHAR(50))");
                }
                
                if(!tables.contains("PLOT")) {
                    plugin.getLogger().info("creation table PLOT ...");
                    execute("CREATE TABLE PLOT ("
                            + "plotName VARCHAR(50), "
                            + "level INT, "
                            + "world VARCHAR(50), "
                            + "X1 INT, "
                            + "Y1 INT, "
                            + "Z1 INT, "
                            + "X2 INT, "
                            + "Y2 INT, "
                            + "Z2 INT, "
                            + "jail BOOLEAN DEFAULT FALSE, "
                            + "noEnter BOOLEAN DEFAULT FALSE, "
                            + "noFly BOOLEAN DEFAULT FALSE, "
                            + "noBuild BOOLEAN DEFAULT FALSE, "
                            + "noBreak BOOLEAN DEFAULT FALSE, "
                            + "noTeleport BOOLEAN DEFAULT FALSE, "
                            + "noInteract BOOLEAN DEFAULT FALSE, "
                            + "noFire BOOLEAN DEFAULT TRUE, "
                            + "message TEXT DEFAULT NULL, "
                            + "mode INT DEFAULT 0, "
                            + "noMob BOOLEAN DEFAULT FALSE, "
                            + "noAnimal BOOLEAN DEFAULT FALSE, "
                            + "noTNT BOOLEAN DEFAULT TRUE, "
                            + "noCommand BOOLEAN DEFAULT FALSE, "
                            + "uuidOwner TEXT DEFAULT NULL, "
                            + "uuidAllowed TEXT DEFAULT NULL, "
                            + "id_guild INT DEFAULT 0, "
                            + "spawnGrave BOOLEAN DEFAULT TRUE,"
                            + "noPVPplayer BOOLEAN DEFAULT FALSE,"
                            + "noPVPmonster BOOLEAN DEFAULT FALSE,"
                            + "noProjectile BOOLEAN DEFAULT FALSE,"
                            + "noLiquidFlow BOOLEAN DEFAULT FALSE,"
                            + "autoForest BOOLEAN DEFAULT TRUE, "
                            + "noMsg BOOLEAN DEFAULT FALSE, "
                            + "KEY (plotName), UNIQUE (plotName))");
                }
                
                if(!tables.contains("PLSALE")) {
                    plugin.getLogger().info("creation table PLSALE ...");
                    execute("CREATE TABLE PLSALE ("
                            + "plotName VARCHAR(50), "
                            + "location VARCHAR(50))");
                }
                
                if(!tables.contains("PORTAL")) {
                    plugin.getLogger().info("creation table PORTAL ...");
                    execute("CREATE TABLE PORTAL ("
                            + "portalname VARCHAR(50), "
                            + "level INT, "
                            + "world VARCHAR(50), "
                            + "x1 INT, "
                            + "y1 INT, "
                            + "z1 INT, "
                            + "x2 INT, "
                            + "y2 INT, "
                            + "z2 INT, "
                            + "toworld VARCHAR(50), "
                            + "tox INT, "
                            + "toy INT, "
                            + "toz INT, "
                            + "message VARCHAR(255), "
                            + "cmd VARCHAR(255), "
                            + "KEY(portalname))");
                }
                
                if(!tables.contains("ITEMSHOP")) {
                    plugin.getLogger().info("creation table ITEMSHOP ...");
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
                        rs.getInt("kills"),
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
                        rs.getBoolean("noAnimal"),
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
                        rs.getBoolean("autoForest"),
                        rs.getBoolean("noMsg"));
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
                    addWarp(warp);
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
                    for (String e : execute) {
                        statement.execute(e);
                    }
                    
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

        public static final HashMap<Integer, Guild> GUILDS = new HashMap<>();
        @SuppressWarnings("element-type-mismatch")
	public static void addGuild(Integer ID, Guild guild) { if(!GUILDS.containsKey(GUILDS)) GUILDS.put(ID, guild); }
	public static void removeGuild(Integer id_guild) { if(GUILDS.containsKey(id_guild)) GUILDS.remove(id_guild); }
	public static Guild getGuild(Integer id_guild) { return GUILDS.containsKey(id_guild) ? GUILDS.get(id_guild) : null; }
        
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
        
        public static HashMap<String, Warp> WARPS = new HashMap<>();
        public static void addWarp(Warp warp) { if(!WARPS.containsValue(warp)) WARPS.put(warp.getName(), warp); } 
        public static void removeWarp(Warp warp) { if(WARPS.containsValue(warp)) WARPS.remove(warp.getName()); } 
        public static Warp getWarp(String name) { return WARPS.containsKey(name) ? WARPS.get(name) : null; }
	
	private static final HashMap<Integer, Ticket> TICKETS = new HashMap<>();
	public static void addTicket(int id, Ticket ticket) { if(!TICKETS.containsKey(id)) TICKETS.put(id, ticket); }
	public static void removeTicket(int id) { if(TICKETS.containsKey(id)) TICKETS.remove(id); }
	public static Ticket getTicket(int id) { return TICKETS.containsKey(id) ? TICKETS.get(id) : null; }
	public static HashMap<Integer, Ticket> getTickets() { return TICKETS; }
	public static void clearTickets() { TICKETS.clear(); }
}

