package net.teraoctet.actus.plot;

import static net.teraoctet.actus.utils.Config.DEFAULT_TEMP_JAIL_IN_SECONDS;
import net.teraoctet.actus.utils.Data;

public class Jail {
	private String uuid;
	private String player;
        private String jail;
	private String reason;
	private int time;
	private int duration;
	
	public Jail(String uuid, String player, String jail, String reason, int time, int duration) {
		this.uuid = uuid;
		this.player = player;
                this.jail = jail;
		this.reason = reason;
		this.time = time;
		this.duration = duration;
	}
        
        public Jail(String uuid, String player, String jail, String reason, int time) {
		this.uuid = uuid;
		this.player = player;
                this.jail = jail;
		this.reason = reason;
		this.time = time;
		this.duration = DEFAULT_TEMP_JAIL_IN_SECONDS();
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setPlayer(String player) { this.player = player; }
        public void setJail(String jail) { this.jail = jail; }
	public void setReason(String reason) { this.reason = reason; }
	public void setTime(int time) { this.time = time; }
	public void setDuration(int duration) { this.duration = duration; }
	
	public String getUUID() { return uuid; }
	public String getPlayer() { return player; }
        public String getJail() { return jail; }
	public String getReason() { return reason; }
	public int getTime() { return time; }
	public int getDuration() { return duration; }
	
	public void insert() {
		Data.queue("INSERT INTO jail VALUES ('" + uuid + "', '" + player + "', '" + jail + "', '" + reason + "', " + time + ", " + duration + ")");
		Data.addJail(uuid, this);
	}
	
	public void update() {
		Data.queue("UPDATE jail SET sender = '" + player + "', reason = '" + reason + "', time = " + time + ", duration = " + duration + " WHERE uuid = '" + uuid + "'");
		Data.removeJail(uuid);
		Data.addJail(uuid, this);
	}
	
	public void delete() {
		Data.queue("DELETE FROM jail WHERE uuid = '" + uuid + "'");
		Data.removeJail(uuid);
	}
	
}
