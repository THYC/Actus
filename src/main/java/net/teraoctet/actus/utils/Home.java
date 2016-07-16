package net.teraoctet.actus.utils;

public class Home {
	
	private String uuid;
	private String name;
	private String world;
	private int x;
	private int y;
	private int z;
	
	public Home(String uuid, String name, String world, int x, int y, int z) {
		this.uuid = uuid;
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void insert() {
		Data.queue("INSERT INTO homes VALUES ('" + uuid + "', '" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ")");
	}
	
	public void update() {
		Data.queue("UPDATE homes SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + " WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void delete() {
		Data.queue("DELETE FROM homes WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setWorld(String world) { this.world = world; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setZ(int z) { this.z = z; }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getWorld() { return world; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }

}
