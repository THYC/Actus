package net.teraoctet.actus.utils;

import net.teraoctet.actus.plot.Jail;
import net.teraoctet.actus.player.APlayer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import net.teraoctet.actus.guild.Guild;
import static net.teraoctet.actus.player.PlayerManager.addAPlayer;
import static net.teraoctet.actus.player.PlayerManager.addUUID;
import static net.teraoctet.actus.player.PlayerManager.getAPlayer;
import static net.teraoctet.actus.player.PlayerManager.removeAPlayer;
import net.teraoctet.actus.plot.Plot;
import net.teraoctet.actus.plot.PlotManager;
import net.teraoctet.actus.portal.Portal;
import net.teraoctet.actus.ticket.Ticket;
import net.teraoctet.actus.warp.Warp;
import static net.teraoctet.actus.warp.WarpManager.addWarp;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.service.sql.SqlService;

public class Data {
	public static SqlService sql;
	public static DataSource datasource;
	public static List<String> queue = new ArrayList<>();
	
	public static void setup() {
            try {
                sql = getGame().getServiceManager().provide(SqlService.class).get();
                
                if(!Config.MYSQL_USE()) { 
                    datasource = sql.getDataSource("jdbc:sqlite:config/actus/actus.db");
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
                while(resultset.next()) { tables.add(resultset.getString(3));}

                if(!tables.contains("jails")) {
                        execute("CREATE TABLE jails ("
                                + "uuid TEXT, "
                                + "player TEXT, "
                                + "jail TEXT, "
                                + "reason TEXT, "
                                + "time DOUBLE, "
                                + "duration DOUBLE)");
                }

                if(!tables.contains("homes")) {
                        execute("CREATE TABLE homes ("
                                + "uuid TEXT, "
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x INT, "
                                + "y INT, "
                                + "z INT)");
                }

                if(!tables.contains("aplayers")) {
                        execute("CREATE TABLE aplayers ("
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
                
                if(!tables.contains("guilds")) {
                        execute("CREATE TABLE guilds ("
                                + "id_guild, "
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

                if(!tables.contains("tickets")) {
                        execute("CREATE TABLE tickets ("
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

                if(!tables.contains("warps")) {
                        execute("CREATE TABLE warps ("
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "message TEXT)");
                }
                
                if(!tables.contains("trace")) {
                        execute("CREATE TABLE trace ("
                                + "date DOUBLE, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "uuid TEXT, "
                                + "type TEXT, "
                                + "block TEXT)");
                }
                
                if(!tables.contains("plot")) {
                        execute("CREATE TABLE plot ("
                                + "plotName TEXT, "
                                + "level INT, "
                                + "world TEXT, "
                                + "X1 INT, "
                                + "Y1 INT, "
                                + "Z1 INT, "
                                + "X2 INT, "
                                + "Y2 INT, "
                                + "Z2 INT, "
                                + "jail INT, "
                                + "noEnter INT, "
                                + "noFly INT, "
                                + "noBuild INT, "
                                + "noBreak INT, "
                                + "noTeleport INT, "
                                + "noInteract INT, "
                                + "noFire INT, "
                                + "message TEXT, "
                                + "mode INT, "
                                + "noMob INT, "
                                + "noTNT INT, "
                                + "noCommand INT, "
                                + "uuidOwner TEXT, "
                                + "uuidAllowed TEXT)");
                }
                
                if(!tables.contains("plsale")) {
                        execute("CREATE TABLE plsale ("
                                + "plotName TEXT, "
                                + "location TEXT)");
                }
                
                if(!tables.contains("portal")) {
                        execute("CREATE TABLE portal ("
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
                
                if(!tables.contains("itemshop")) {
                        execute("CREATE TABLE itemshop ("
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
                ResultSet rs = s.executeQuery("SELECT * FROM jail");
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
                s.close();
                c.close();
            } catch (SQLException e) {}

            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM aplayers");

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
            } catch (SQLException e) {}
            
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM guilds");
                
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
                    Data.addGuild(guild.getID(), guild);
                }   
            } catch (SQLException e) {}
		
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM homes");
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
                s.close();
                c.close();
            } catch (SQLException e) {}
                
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM plot ORDER BY level asc");
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
                        rs.getInt("jail"),
                        rs.getInt("noEnter"), 
                        rs.getInt("noFly"),
                        rs.getInt("noBuild"),
                        rs.getInt("noBreak"),
                        rs.getInt("noTeleport"),
                        rs.getInt("noInteract"), 
                        rs.getInt("noFire"),
                        rs.getString("message"),
                        rs.getInt("mode"),
                        rs.getInt("noMob"),
                        rs.getInt("noTNT"),
                        rs.getInt("noCommand"),
                        rs.getString("uuidOwner"), 
                        rs.getString("uuidAllowed"));
                    if(plot.getJail()==1){jails.add(plot);}
                    else{plots.add(plot);}   
                }
                s.close();
                c.close();
            } catch (SQLException e) {}
            
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM portal");
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
                        rs.getString("message"));
                    portals.add(portal);  
                }
                s.close();
                c.close();
            } catch (SQLException e) {}
				
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM tickets");
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
                s.close();
                c.close();
            } catch (SQLException e) {}
		
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM warps");
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
			e.printStackTrace();
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
	        
	private static final HashMap<String, Jail> gjails = new HashMap<>();
	public static void addJail(String uuid, Jail gjail) { if(!gjails.containsKey(uuid)) gjails.put(uuid, gjail); }
	public static void removeJail(String uuid) { if(gjails.containsKey(uuid)) gjails.remove(uuid); }
	public static Jail getJail(String uuid) { return gjails.containsKey(uuid) ? gjails.get(uuid) : null; }
	public static HashMap<String, Jail> getBans() { return gjails; }

        private static final HashMap<Integer, Guild> guilds = new HashMap<>();
        @SuppressWarnings("element-type-mismatch")
	public static void addGuild(Integer ID, Guild guild) { if(!guilds.containsKey(guilds)) guilds.put(ID, guild); }
	public static void removeGuild(Integer id_guild) { if(guilds.containsKey(id_guild)) guilds.remove(id_guild); }
	public static Guild getGuild(Integer id_guild) { return guilds.containsKey(id_guild) ? guilds.get(id_guild) : null; }
        public static HashMap<Integer, Guild> getFactions() { return guilds; }
        
        public static HashMap<String, PlotManager> setts = new HashMap();
        public static final ArrayList<Plot> plots = new ArrayList<>();
        public static final ArrayList<Plot> jails = new ArrayList<>();
        public static void addPlot(Plot plot) { if(!plots.contains(plot)) plots.add(plot); }
        public static void addJail(Plot plot) { if(!jails.contains(plot)) jails.add(plot); }
        public static void removePlot(Plot plot) { if(plots.contains(plot)) plots.remove(plot); }
        public static void removeJail(Plot plot) { if(jails.contains(plot)) jails.remove(plot); }

        public static final ArrayList<Portal> portals = new ArrayList<>();
        public static void addPortal(Portal portal) { if(!portals.contains(portal)) portals.add(portal); } 
        public static void removePortal(Portal portal) { if(portals.contains(portal)) portals.remove(portal); } 
	
	private static HashMap<Integer, Ticket> tickets = new HashMap<>();
	public static void addTicket(int id, Ticket ticket) { if(!tickets.containsKey(id)) tickets.put(id, ticket); }
	public static void removeTicket(int id) { if(tickets.containsKey(id)) tickets.remove(id); }
	public static Ticket getTicket(int id) { return tickets.containsKey(id) ? tickets.get(id) : null; }
	public static HashMap<Integer, Ticket> getTickets() { return tickets; }
	public static void clearTickets() { tickets.clear(); }
	
	
        

}

